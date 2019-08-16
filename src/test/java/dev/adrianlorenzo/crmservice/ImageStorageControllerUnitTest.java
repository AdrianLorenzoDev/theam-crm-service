package dev.adrianlorenzo.crmservice;

import dev.adrianlorenzo.crmservice.controller.ImageStorageController;
import dev.adrianlorenzo.crmservice.resourceExceptions.InvalidResourceException;
import dev.adrianlorenzo.crmservice.resourceExceptions.StorageException;
import dev.adrianlorenzo.crmservice.services.StorageService;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ImageStorageController.class)
public class ImageStorageControllerUnitTest {
    //TODO
    // - Create tests (controller already tested)

    static final String IMAGE_PATH = "/Users/adrianlorenzo/Desktop/example/example.JPG";

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public StorageService storageService;


    private Resource resource;

    /**
     @Before public void initializeResources() throws MalformedURLException {
     resource = new UrlResource(Paths.get(IMAGE_PATH).toUri());
     }
     */

    /**
     * @Test
     * @DisplayName("Download image and image was found")
     * public void downloadImage_imageFound() throws Exception, ResourceNotFoundException {
     * given(storageService.loadAsResource("example_image")).willReturn(resource);
     * <p>
     * mockMvc.perform(get("/api/images/" + "example.JPG").contentType(MediaType.APPLICATION_JSON))
     * .andExpect(status().isOk());
     * }
     */

    @Test
    @DisplayName("Upload image")
    public void uploadImage() throws Exception, StorageException, InvalidResourceException {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "example.JPG",
                "example.JPG",
                "image/jpg",
                Files.readAllBytes(Paths.get(IMAGE_PATH)));

        given(storageService.store(multipartFile)).willReturn("example.JPG");

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/images")
                .file(multipartFile))
                .andExpect(status().isOk())
                .andExpect(content().string("example.JPG"));
    }
}
