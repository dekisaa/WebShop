package com.ws.WebShop.service;

import com.ws.WebShop.model.Category;
import com.ws.WebShop.model.Department;
import com.ws.WebShop.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private CategoryService categoryService;

    //get all deparments from database
    public List<Department> getDepartments(){
        return departmentRepository.findAll();
    }

    //find one deparmnet by id sent from front
    public Department findById(Long id){
        Optional<Department> department = departmentRepository.findById(id);
        if(!department.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Department with id " + id + " does not exists");
        return department.get();
    }

    public List<Category> findAllCategoriesForSpecificDepartment(Long id){
        Department department = findById(id);
        return department.getCategoryList();
    }

    public void save(Department department){
        departmentRepository.save(department);
    }

    public void delete(Long id){
        Department department = findById(id);
        departmentRepository.delete(department);
    }

    //service for adding category to department and vicevrs
    public void addDepartmentToCategory(Category category, Department department){
        department.getCategoryList().add(category);
        category.setDepartment(department);
        departmentRepository.save(department);
        categoryService.save(category);
    }
}
