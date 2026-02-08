package com.customersu.dashapi.cases.metas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaRepository extends JpaRepository<MetaEntity, Long> {

    boolean existsByGerente_IdAndProduto_IdAndStatus(Long gerenteId, Long produtoId, EnumStatusMeta status);
}
