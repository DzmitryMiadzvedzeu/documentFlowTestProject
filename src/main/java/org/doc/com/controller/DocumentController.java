package org.doc.com.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.doc.com.api_errors.ApiError;
import org.doc.com.dto.DocumentCreateDto;
import org.doc.com.dto.DocumentCreateDtoWithToken;
import org.doc.com.dto.DocumentDto;
import org.doc.com.entity.Document;
import org.doc.com.enums.Status;
import org.doc.com.enums.Type;
import org.doc.com.mapper.DocumentMapper;
import org.doc.com.service.DocumentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


//http://localhost:8080/v1/documents (for postman)
@RestController
@RequestMapping("/v1/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService service;
    private final DocumentMapper mapper;

//    @PostMapping
//    public ResponseEntity<DocumentDto> create(@Valid @RequestBody DocumentCreateDto documentCreateDto) {
//        Document document = mapper.documentCreateDtoToEntity(documentCreateDto);
//        Document createdDocument = service.create(document);
//        return ResponseEntity.ok(mapper.toDto(createdDocument));
//    }

    @PostMapping
    public ResponseEntity<DocumentDto> create(@Valid @RequestBody DocumentCreateDtoWithToken documentCreateDtoWithToken) {
        if (documentCreateDtoWithToken.getTransactionToken() == null || documentCreateDtoWithToken.getTransactionToken().isEmpty()) {
            throw new IllegalArgumentException("Transaction token is required");
        }
        Document document = mapper.documentCreateWithToken(documentCreateDtoWithToken);
        Document createdDocument = service.create(document, documentCreateDtoWithToken.getTransactionToken());
        return ResponseEntity.ok(mapper.toDto(createdDocument));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentDto> update(@PathVariable Long id,
                                              @Valid @RequestBody DocumentCreateDto documentCreateDto) {
        Document document = mapper.documentCreateDtoToEntity(documentCreateDto);
        Document updatedDocument = service.update(id, document);
        return ResponseEntity.ok(mapper.toDto(updatedDocument));
    }

    @GetMapping("/search")
    public ResponseEntity<List<DocumentDto>> search(
            @RequestParam(required = false) String serialNumber,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Type type,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime createdAt,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime updatedAt) {
        List<Document> documents = service.search(serialNumber, title, status, type,
                createdAt, updatedAt);
        List<DocumentDto> documentDtos = documents.stream()
                .map(mapper :: toDto).collect(Collectors.toList());
        return ResponseEntity.ok(documentDtos);
    }

    @GetMapping
    public ResponseEntity<List<DocumentDto>> getAll() {
        List<DocumentDto> documentDtos = service.getAll().stream()
                .map(mapper :: toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(documentDtos);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDto(service.findById(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}