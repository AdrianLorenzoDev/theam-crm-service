package dev.adrianlorenzo.crmservice.controllers;

import dev.adrianlorenzo.crmservice.resourceExceptions.InvalidResourceException;
import dev.adrianlorenzo.crmservice.resourceExceptions.ResourceNotFoundException;
import dev.adrianlorenzo.crmservice.resourceExceptions.StorageException;
import dev.adrianlorenzo.crmservice.services.StorageService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.MalformedURLException;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ImageStorageControllerUnitTest {

    private static final String IMAGE_PATH = "/Users/adrianlorenzo/Desktop/example/example.JPG";

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    public StorageService storageService;


    private Resource resource;

    @Before
    public void initializeResources() throws MalformedURLException {
        resource = new UrlResource(Paths.get(IMAGE_PATH).toUri());
    }

    @WithMockUser
    @Test
    public void downloadImageAndImageFound() throws Exception, ResourceNotFoundException {
        given(storageService.loadAsResource("example_image.jpg")).willReturn(resource);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/images/example_image.jpg");

        mockMvc.perform(builder).andExpect(status().isOk())
        .andExpect(content().contentType("image/jpeg"));
    }

    @WithMockUser
    @Test
    public void downloadImageAndImageNotFound() throws Exception, ResourceNotFoundException {
        given(storageService.loadAsResource("example_image.jpg")).willThrow(new ResourceNotFoundException());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/images/example_image.jpg");

        mockMvc.perform(builder).andExpect(status().isNotFound());
    }


    @WithMockUser
    @Test
    public void uploadImage() throws Exception, StorageException, InvalidResourceException {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "example.JPG",
                "image/jpg",
                Files.readAllBytes(Paths.get(IMAGE_PATH)));

        given(storageService.store(multipartFile)).willReturn("example.JPG");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/images")
                        .file(multipartFile).contentType("multiform/form-data");

        mockMvc.perform(builder).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void uploadInvalidFile() throws Exception, StorageException, InvalidResourceException {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "example.txt",
                "text/plain",
                "I am an error, nice to meet you".getBytes());

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("/api/images")
                .file(multipartFile).contentType("multiform/form-data");

        mockMvc.perform(builder).andExpect(status().isForbidden());
    }
}
