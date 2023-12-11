package br.com.plx.integration.batchintegration.persistence.repository;

import br.com.plx.integration.batchintegration.persistence.entity.DataExceptionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataExceptionRepository extends CrudRepository<DataExceptionEntity, Long> {
}
