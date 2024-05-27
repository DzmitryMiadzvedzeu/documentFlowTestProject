package org.doc.com.repository;

import org.doc.com.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentJpaRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {

    boolean existsBySerialNumber(String serialNumber);
}