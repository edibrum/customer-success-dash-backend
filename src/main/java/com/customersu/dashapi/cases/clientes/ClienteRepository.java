package com.customersu.dashapi.cases.clientes;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    // sobrescrevemos o findById padrão para carregar a pessoa e evitar proxies
    @Override
    @EntityGraph(attributePaths = {"pessoa"})
    Optional<ClienteEntity> findById(Long id);

    @EntityGraph(attributePaths = {"pessoa"})
    Optional<ClienteEntity> findByPessoaId(Long pessoaId);

    boolean existsByPessoaId(Long pessoaId);

}
