package com.sharing.doc_sharing.repository;

import com.sharing.doc_sharing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);
    long count();
}
