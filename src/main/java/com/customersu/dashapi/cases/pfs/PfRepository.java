package com.customersu.dashapi.cases.pfs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PfRepository extends JpaRepository<PfEntity, Long> {

    Optional<PfEntity> findByCpf(String cpf);

    boolean existsByCpf(String cpf);
}
