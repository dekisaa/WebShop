package com.ws.WebShop.service;

import com.ws.WebShop.model.Category;
import com.ws.WebShop.model.Department;
import com.ws.WebShop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private DepartmentService departmentService;

    public void save(Category category){
        categoryRepository.save(category);
    }

    public void update(Category category){
        Category origCat = findById(category.getId());
        origCat.setDescription(category.getDescription());
        origCat.setName(category.getName());
        save(origCat);
    }

    public void delete(Long id){
        Category category =findById(id);
        categoryRepository.delete(category);
    }

    public Category findById(Long id){
        Optional<Category> category = categoryRepository.findById(id);
        if(!category.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category with id " + id + " does not exist!");
        return category.get();
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public void setDepartment(Long departmentId, Long categoryId){
        Department department = departmentService.findById(departmentId);
        Category category = findById(categoryId);

        if(department.getCategoryList().contains(category))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department with id " + departmentId + " already contains category with id " + categoryId);

        if(category.getDepartment() != null){
            category.getDepartment().getCategoryList().remove(category);
            departmentService.save(category.getDepartment());
        }

        department.getCategoryList().add(category);
        category.setDepartment(department);

        departmentService.save(department);
        save(category);
    }

}
