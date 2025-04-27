package com.sharing.doc_sharing.repository;

import com.sharing.doc_sharing.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    long count();
}
