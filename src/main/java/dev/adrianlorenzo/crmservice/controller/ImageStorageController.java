package dev.adrianlorenzo.crmservice.controller;

import dev.adrianlorenzo.crmservice.resourceExceptions.FileNotAnImageException;
import dev.adrianlorenzo.crmservice.resourceExceptions.InvalidResourceException;
import dev.adrianlorenzo.crmservice.resourceExceptions.ResourceNotFoundException;
import dev.adrianlorenzo.crmservice.resourceExceptions.StorageException;
import dev.adrianlorenzo.crmservice.model.UploadedImageResponse;
import dev.adrianlorenzo.crmservice.services.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(ImageStorageController.BASE_URL)
public class ImageStorageController {
    static final String BASE_URL = "/api/images";
    private final StorageService service;

    @Autowired
    public ImageStorageController(StorageService service) {
        this.service = service;
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> imageDownload(@PathVariable String filename, HttpServletRequest request)
            throws ResourceNotFoundException, FileNotAnImageException {

        Resource image = service.loadAsResource(filename);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(image.getFile().getAbsolutePath());
        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "File type could not be determined", e);
        }

        RestPreconditions.checkIfImage(contentType);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"")
                .body(image);
    }

    @PostMapping
    public UploadedImageResponse imageUpload(@RequestParam("file") MultipartFile file)
            throws FileNotAnImageException, StorageException, InvalidResourceException {

        RestPreconditions.checkIfImage(file.getContentType());
        String fileName = service.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(BASE_URL + "/")
                .path(fileName)
                .toUriString();

        return new UploadedImageResponse(fileName, uri, file.getContentType(), file.getSize());
    }

    @DeleteMapping("/{filename:.+}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteImage(@PathVariable String filename) throws ResourceNotFoundException {
        service.delete(filename);
    }
}
