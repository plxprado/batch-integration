package br.com.plx.integration.batchintegration.persistence.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "file_processed_info")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FileProcessedInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "processed_lines")
    private Integer processedLines;

    @Column(name = "total_erros")
    private Integer totalErrors;

    @Column(name = "total_lines")
    private Integer totalLines;

    @ManyToOne
    @JoinColumn(name = "file_info_id")
    private ReportEntity report;


}
