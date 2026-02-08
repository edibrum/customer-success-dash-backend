package com.customersu.dashapi.cases.contas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContaRepository extends JpaRepository<ContaEntity, Long> {

    boolean existsByAgenciaAndNumeroAndDigito(String agencia, String numero, String digito);
}
