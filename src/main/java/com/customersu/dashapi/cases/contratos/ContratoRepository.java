package com.customersu.dashapi.cases.contratos;

import com.customersu.dashapi.cases.contas.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratoRepository extends JpaRepository<ContratoEntity, Long>, JpaSpecificationExecutor<ContratoEntity> {

    boolean existsByProduto_IdAndConta_Id(Long produtoId, Long contaId);
}
