package com.ws.WebShop.service;

import com.google.common.net.HttpHeaders;
import com.ws.WebShop.configuration.UploadFileResponse;
import com.ws.WebShop.model.Image;
import com.ws.WebShop.model.Product;
import com.ws.WebShop.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ProductService productService;

    public void save(Image image){
        imageRepository.save(image);
    }

    public void delete(Long id) throws IOException {
        Image image = findById(id);
        fileStorageService.deleteFileAsResource(image.getPath());
        imageRepository.delete(image);
    }

    public Image findById(Long id){
        Optional<Image> image = imageRepository.findById(id);
        if(!image.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Image with id " + id + " does not exists");
        return image.get();
    }

    public UploadFileResponse uploadImage(MultipartFile file, Long productId){
        Product product = productService.findById(productId);
        Image image = new Image();
        save(image);
        String name = image.getId().toString() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = fileStorageService.storeFile(file, name);

        image.setProduct(product);
        product.getImages().add(image);
        productService.save(product);


        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/image/downloadFile/")
                .path(fileName)
                .toUriString();
        image.setPath(fileDownloadUri);
        save(image);

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    public List<UploadFileResponse> uploadMultipleFiles(MultipartFile[] files, Long productId){
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadImage(file, productId))
                .collect(Collectors.toList());
    }

    public ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request){
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            System.out.println("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public List<Image> getAllProductImages(Long productId){
        Product product = productService.findById(productId);
        return product.getImages();
    }
}
