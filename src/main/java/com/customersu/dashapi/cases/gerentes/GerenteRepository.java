package com.customersu.dashapi.cases.gerentes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GerenteRepository extends JpaRepository<GerenteEntity, Long> {

    Optional<GerenteEntity> findByPessoaId(Long pessoaId);
    boolean existsByPessoaId(Long pessoaId);

    void deleteByPessoaId(Long pessoaId);

}
