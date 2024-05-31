//package org.doc.com.entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "TransactionTokens")
//@Data
//@NoArgsConstructor
//public class TransactionToken {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotBlank(message = "Token must to be")
//    private String token;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private LocalDateTime createdAt = LocalDateTime.now();
//
//    public TransactionToken(String token, LocalDateTime now) {
//        this.token = token;
//        this.createdAt = LocalDateTime.now();
//    }
//}