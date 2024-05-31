package org.doc.com.service;

import org.doc.com.entity.Document;
import org.doc.com.enums.Department;
import org.doc.com.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public interface DocumentService {

    Document create(Document entity);

    Document update(Long id, Document document);

    List<Document> search(String serialNumber, String title, Status status, Department department,
                          LocalDateTime createdAt, LocalDateTime updatedAt);

    Document changeStatus(Long id, Status newStatus);

    List<Document> getAll();

    Document findById(Long id);

    void delete(Long id);
}