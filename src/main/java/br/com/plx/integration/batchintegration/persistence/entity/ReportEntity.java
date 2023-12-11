package br.com.plx.integration.batchintegration.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "report")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "insert_date")
    private LocalDate insetDate;

    @Column(name = "processed")
    private Long registerProcessed;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "report")
    private List<FileProcessedInfoEntity> files;

}
