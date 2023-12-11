package br.com.plx.integration.batchintegration.persistence.repository;

import br.com.plx.integration.batchintegration.persistence.entity.ReportEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends CrudRepository<ReportEntity, Long> {
}
