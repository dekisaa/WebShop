package com.ws.WebShop.service;

import com.ws.WebShop.model.Category;
import com.ws.WebShop.model.Department;
import com.ws.WebShop.model.Image;
import com.ws.WebShop.model.Product;
import com.ws.WebShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageService imageService;

    public void save(Product product){
        productRepository.save(product);
    }

    public void delete(Long id){
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + id + " does not exist!");
        productRepository.delete(product.get());
    }

    public Product findById(Long id){
        Optional<Product> product = productRepository.findById(id);
        if(!product.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id " + id + " does not exist!");
        return product.get();
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public List<Image> findAllImages(Long id){
        Product product = findById(id);
        return product.getImages();
    }

    public void setCategory(Long productId, Long categoryId){
        Product product = findById(productId);
        Category category = categoryService.findById(categoryId);

        if(category.getProductList().contains(product))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category with id " + categoryId + " already contains product with id " + productId);

        if(product.getCategory() != null){
            product.getCategory().getProductList().remove(product);
            categoryService.save(product.getCategory());
        }

        category.getProductList().add(product);
        product.setCategory(category);

        categoryService.save(category);
        save(product);
    }
}
