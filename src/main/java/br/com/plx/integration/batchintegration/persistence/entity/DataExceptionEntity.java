package br.com.plx.integration.batchintegration.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "register_exception")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DataExceptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "error_message")
    private String erroMessage;

    @Column(name = "payload")
    private String payload;

    @Column(name = "line_error")
    private long lineError;

    @Column(name = "skiped", columnDefinition =  "boolean default false")
    private Boolean skiped;

    @Column(name = "insert_date")
    private LocalDate insetDate;
}
