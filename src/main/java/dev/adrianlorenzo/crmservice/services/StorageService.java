package dev.adrianlorenzo.crmservice.services;

import dev.adrianlorenzo.crmservice.resourceExceptions.InvalidResourceException;
import dev.adrianlorenzo.crmservice.resourceExceptions.ResourceNotFoundException;
import dev.adrianlorenzo.crmservice.resourceExceptions.StorageException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String store(MultipartFile file) throws InvalidResourceException, StorageException;

    Resource loadAsResource(String fileName) throws ResourceNotFoundException;

    void delete(String fileName) throws ResourceNotFoundException;
}
