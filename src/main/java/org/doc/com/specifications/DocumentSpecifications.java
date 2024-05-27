package org.doc.com.specifications;

import org.doc.com.entity.Document;
import org.doc.com.enums.Status;
import org.doc.com.enums.Type;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DocumentSpecifications {

    public static Specification<Document> withDynamicQuery(final String serialNumber,
                                                           final String title, final Status status,
                                                           final Type type,
                                                           final LocalDateTime createdAt,
                                                           final LocalDateTime updatedAt) {
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (serialNumber != null){
                predicates.add(cb.equal(root.get("serialNumber"), serialNumber));
            }
            if (title != null) {
                predicates.add(cb.like(root.get("title"), "%" + title + "%"));
            }
            if (status != null){
                predicates.add(cb.equal(root.get("status"), status));
            }
            if (type != null){
                predicates.add(cb.equal(root.get("type"), type));
            }
            if (createdAt != null){
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), createdAt));
            }
            if (updatedAt != null){
                predicates.add(cb.lessThanOrEqualTo(root.get("updatedAt"), updatedAt));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}