package org.doc.com.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.doc.com.entity.Document;
import org.doc.com.entity.TransactionToken;
import org.doc.com.enums.Status;
import org.doc.com.enums.Type;
import org.doc.com.exceptions.DocumentExistsException;
import org.doc.com.exceptions.DocumentNotFoundException;
import org.doc.com.repository.DocumentJpaRepository;
import org.doc.com.repository.TransactionTokenJpaRepository;
import org.doc.com.specifications.DocumentSpecifications;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentJpaRepository repository;
    private final TransactionTokenJpaRepository tokenRepository;

    @Override
    public Document create(Document document, String transactionToken) {
        if (tokenRepository.existsByToken(transactionToken)) {
            throw new IllegalStateException("This transaction has already been processed");
        }
        if (repository.existsBySerialNumber(document.getSerialNumber())) {
            throw new DocumentExistsException("Document with this number already exists");
        }
        repository.save(document);
        tokenRepository.save(new TransactionToken(transactionToken, LocalDateTime.now()));
        return document;
    }

    @Transactional
    @Override
    public Document update(Long id, Document document) {
        Document existingDocument = repository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document with this id: "
                        + id + " not found"));
        existingDocument.setSerialNumber(document.getSerialNumber());
        existingDocument.setTitle(document.getTitle());
        existingDocument.setType(document.getType());
        existingDocument.setUpdatedAt(LocalDateTime.now());
        existingDocument.setStatus(Status.UPDATED);
        return repository.save(existingDocument);
    }

    @Override
    public List<Document> search(String serialNumber, String title, Status status,
                                 Type type, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return repository.findAll(DocumentSpecifications
                .withDynamicQuery(serialNumber, title, status, type, createdAt, updatedAt));
    }

    @Override
    public List<Document> getAll() {
        return repository.findAll();
    }

    @Override
    public Document findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Document with this id: "
                        + id + " not found"));
    }

    @Transactional
    @Override
    public void delete(long id) {
        if (!repository.existsById(id)) {
            throw new DocumentNotFoundException("Document not found");
        }
        repository.deleteById(id);
    }
}