package org.doc.com.service;

import org.doc.com.entity.Document;
import org.doc.com.enums.Status;
import org.doc.com.enums.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DocumentService {

    Document create (Document entity, String transactionToken);

    Document update (Long id, Document document);

    List<Document> search(String serialNumber, String title, Status status, Type type,
                          LocalDateTime createdAt, LocalDateTime updatedAt);

    List<Document> getAll();

    Document findById(long id);

    void delete(long id);
}