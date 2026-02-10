package com.customersu.dashapi.cases.tarefas;

import com.customersu.dashapi.cases.clientes.ClienteEntity;
import com.customersu.dashapi.cases.gerentes.GerenteEntity;
import com.customersu.dashapi.cases.metas.MetaEntity;
import com.customersu.dashapi.cases.produtos.ProdutoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tarefas")
@Inheritance(strategy = InheritanceType.JOINED)
public class TarefaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private EnumStatusTarefa status;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private EnumTipoTarefa tipo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "observacao", columnDefinition = "TEXT")
    private String observacao;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "gerente_id", nullable = false)
    private GerenteEntity gerente;

    @Column(name = "meta_id", nullable = true)
    private Long metaId;

    @Column(name = "cliente_id", nullable = true)
    private Long clienteId;

    @Column(name = "contrato_id", nullable = true)
    private Long contratoId;

    @Column(name = "produto_id", nullable = true)
    private Long produtoId;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
        this.status = EnumStatusTarefa.CRIADA;
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}
