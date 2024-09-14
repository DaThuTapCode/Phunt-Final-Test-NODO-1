package com.trongphu.finalintern1.controller;

import com.trongphu.finalintern1.util.exception.ResourceNotFoundException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Trong Phu on 14/09/2024 13:47
 *
 * @author Trong Phu
 */
@RestController
@RequestMapping(value = "api/images")
public class ImageController {
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path imagePath = Paths.get("uploads").resolve(filename);
            Resource image = new FileSystemResource(imagePath.toFile());
            if (image.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(image);
            } else {
                throw new ResourceNotFoundException("error.resource_not_found", filename);
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("error.resource_not_found", filename);
        }
    }

}
