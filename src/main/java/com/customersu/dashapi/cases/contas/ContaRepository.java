package com.customersu.dashapi.cases.contas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<ContaEntity, Long>, JpaSpecificationExecutor<ContaEntity> {

    boolean existsByAgenciaAndNumeroAndDigito(String agencia, String numero, String digito);
}
