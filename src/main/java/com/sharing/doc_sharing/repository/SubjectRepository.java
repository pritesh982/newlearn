package com.sharing.doc_sharing.repository;

import com.sharing.doc_sharing.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    long count();
}
