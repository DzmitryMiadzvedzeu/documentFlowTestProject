package org.doc.com.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.doc.com.entity.Document;
import org.doc.com.entity.User;
import org.doc.com.enums.Status;
import org.doc.com.enums.Department;
import org.doc.com.exceptions.DocumentExistsException;
import org.doc.com.exceptions.DocumentNotFoundException;
import org.doc.com.repository.DocumentJpaRepository;
import org.doc.com.specifications.DocumentSpecifications;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentJpaRepository repository;
    private final UserService userService;

    @Transactional
    @Override
    public Document create(Document document) {
        if (repository.existsBySerialNumber(document.getSerialNumber())) {
            throw new DocumentExistsException("Document with this number already exists");
        }
        Long userId = userService.getCurrentUserId();
        User user = userService.findById(userId);
        document.setUser(user);
        repository.save(document);
        return document;
    }

    @Transactional
    @Override
    public Document update(Long id, Document document) {
        Document existingDocument = repository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document with this id: "
                        + id + " not found"));
        if (existingDocument.getStatus() != Status.DRAFT &&
                existingDocument.getStatus() != Status.REJECTED) {
            throw new IllegalStateException("Only documents in DRAFT or REJECTED status can be edited");
        }
        if (existingDocument.getStatus() == Status.REJECTED) {
            existingDocument.setStatus(Status.DRAFT);
        }
        existingDocument.setSerialNumber(document.getSerialNumber());
        existingDocument.setTitle(document.getTitle());
        existingDocument.setDepartment(document.getDepartment());
        existingDocument.setUpdatedAt(LocalDateTime.now());
        existingDocument.setText(document.getText());
        return repository.save(existingDocument);
    }

    @Transactional
    @Override
    public Document changeStatus(Long id, Status newStatus) {
        Document document = repository.findById(id).orElseThrow(
                () -> new DocumentNotFoundException("Document with this id: " + id + " not found"));
        if (isValidStatusTransition(document.getStatus(), newStatus)) {
            throw new IllegalArgumentException("Invalid status transition from "
                    + document.getStatus() + " to " + newStatus);
        }
        document.setStatus(newStatus);
        document.setUpdatedAt(LocalDateTime.now());
        return repository.save(document);
    }

    private boolean isValidStatusTransition(Status currentStatus, Status newStatus) {
        if (currentStatus == Status.DRAFT &&
                (newStatus == Status.UNDER_REVIEW || newStatus == Status.DRAFT)) {
            return true;
        } else if (currentStatus == Status.UNDER_REVIEW &&
                (newStatus == Status.APPROVED || newStatus == Status.REJECTED)) {
            return true;
        } else if (currentStatus == Status.REJECTED && newStatus == Status.DRAFT) {
            return true;
        }
        return false;
    }

    @Override
    public List<Document> search(String serialNumber, String title, Status status,
                                 Department department, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return repository.findAll(DocumentSpecifications
                .withDynamicQuery(serialNumber, title, status, department, createdAt, updatedAt));
    }

    @Override
    public List<Document> getAll() {
        return repository.findAll();
    }

    @Override
    public Document findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document with this id: "
                        + id + " not found"));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new DocumentNotFoundException("Document not found");
        }
        repository.deleteById(id);
    }
}