package com.sharing.doc_sharing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sharing.doc_sharing.model.Category;
import com.sharing.doc_sharing.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category save(Category subject) {
        return categoryRepository.save(subject);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
