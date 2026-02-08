package com.customersu.dashapi.cases.clientes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    Optional<ClienteEntity> findByPessoaId(Long pessoaId);
    boolean existsByPessoaId(Long pessoaId);

    void deleteByPessoaId(Long pessoaId);

}
