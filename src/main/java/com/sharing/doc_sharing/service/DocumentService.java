package com.sharing.doc_sharing.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sharing.doc_sharing.repository.DocumentRepository;

import lombok.RequiredArgsConstructor;
import com.sharing.doc_sharing.model.Document;
import com.sharing.doc_sharing.model.User;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;

    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    public Document findById(Long id) {
        return documentRepository.findById(id).orElseThrow();
    }

    public List<Document> findBySubjectIdAndStatus(Long subjectId, String status) {
        return documentRepository.findBySubjectIdAndStatus(subjectId, status);
    } 
    public List<Document> findByCategoryIdAndStatus(Long subjectId, String status) {
        return documentRepository.findByCategoryIdAndStatus(subjectId, status);
    } 

    public void save(Document document) {
        documentRepository.save(document);
    }

    public void delete(Long id) {
        documentRepository.deleteById(id);
    }

    public void updateStatus(Long id, String status, User approver) {
        Document doc = findById(id);
        doc.setStatus(status);
        doc.setApprovedBy(approver);
        documentRepository.save(doc);
    }
}

