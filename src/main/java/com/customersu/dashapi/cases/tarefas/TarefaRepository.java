package com.customersu.dashapi.cases.tarefas;

import com.customersu.dashapi.cases.contas.ContaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<TarefaEntity, Long>, JpaSpecificationExecutor<TarefaEntity> {

}
