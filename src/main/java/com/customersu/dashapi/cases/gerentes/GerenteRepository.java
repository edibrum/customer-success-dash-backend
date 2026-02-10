package com.customersu.dashapi.cases.gerentes;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GerenteRepository extends JpaRepository<GerenteEntity, Long> {

    // sobrescrevemos o findById padrão para carregar a pessoa e evitar proxies
    @Override
    @EntityGraph(attributePaths = {"pessoa"})
    Optional<GerenteEntity> findById(Long id);

    @EntityGraph(attributePaths = {"pessoa"})
    Optional<GerenteEntity> findByPessoaId(Long pessoaId);

    boolean existsByPessoaId(Long pessoaId);

}
