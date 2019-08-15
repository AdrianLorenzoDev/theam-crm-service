package dev.adrianlorenzo.crmservice.services;

import dev.adrianlorenzo.crmservice.resourceExceptions.InvalidResourceException;
import dev.adrianlorenzo.crmservice.resourceExceptions.ResourceNotFoundException;
import dev.adrianlorenzo.crmservice.resourceExceptions.StorageException;
import dev.adrianlorenzo.crmservice.properties.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class MockStorageService implements StorageService {

    private final Path fileStorageLocation;

    @Autowired
    public MockStorageService(StorageProperties storageProperties) throws StorageException {
        fileStorageLocation = Paths.get(storageProperties.getUploadDir()).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new StorageException();
        }
    }

    @Override
    public String store(MultipartFile file) throws InvalidResourceException, StorageException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw new InvalidResourceException();
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new StorageException();
        }
    }

    @Override
    public Resource loadAsResource(String fileName) throws ResourceNotFoundException {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new ResourceNotFoundException();
            }
        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public void delete(String fileName) throws ResourceNotFoundException {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            } else {
                throw new ResourceNotFoundException();
            }
        } catch (IOException e) {
            throw new ResourceNotFoundException();
        }
    }
}
