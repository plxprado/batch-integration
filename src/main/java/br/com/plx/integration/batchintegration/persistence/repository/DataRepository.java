package br.com.plx.integration.batchintegration.persistence.repository;

import br.com.plx.integration.batchintegration.persistence.entity.DataEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends CrudRepository<DataEntity, Long> {
}
