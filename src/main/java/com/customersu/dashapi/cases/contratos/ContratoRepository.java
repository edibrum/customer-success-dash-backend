package com.customersu.dashapi.cases.contratos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoRepository extends JpaRepository<ContratoEntity, Long> {

    boolean existsByProduto_IdAndConta_Id(Long produtoId, Long contaId);
}
