package com.ws.WebShop.controller;

import com.ws.WebShop.configuration.UploadFileResponse;
import com.ws.WebShop.model.Image;
import com.ws.WebShop.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value ="remove", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestParam(value = "id") Long id) throws IOException {
        imageService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value ="/all", method = RequestMethod.GET)
    public ResponseEntity<List<Image>> getAllProductImages(@RequestParam(value = "id") Long id){
        return new ResponseEntity<>(imageService.getAllProductImages(id), HttpStatus.OK);
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("productId") Long productId) {
        return imageService.uploadImage(file,productId);
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files, @RequestParam("productId") Long productId) {
        return imageService.uploadMultipleFiles(files,productId);
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        System.out.println(fileName);
        return imageService.downloadFile(fileName,request);
    }
}
