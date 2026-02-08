package com.customersu.dashapi.cases.pjs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PjRepository extends JpaRepository<PjEntity, Long> {

    Optional<PjEntity> findByCnpj(String cnpj);

    boolean existsByCnpj(String cpf);
}
