package com.sharing.doc_sharing.repository;

import com.sharing.doc_sharing.model.Document;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    // List<Document> findBySubjectIdAndStatus(Long subjectId, String status);
    @Query("SELECT d FROM Document d WHERE d.subject.id = :subjectId AND d.status = :status")
    List<Document> findBySubjectIdAndStatus(@Param("subjectId") Long subjectId, @Param("status") String status);

    List<Document> findByCategoryIdAndStatus(Long categoryId, String status);
    long count();

}
