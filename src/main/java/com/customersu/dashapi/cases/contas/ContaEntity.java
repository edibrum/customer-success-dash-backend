package com.customersu.dashapi.cases.contas;

import com.customersu.dashapi.cases.clientes.ClienteEntity;
import com.customersu.dashapi.cases.gerentes.GerenteEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "contas")
@Inheritance(strategy = InheritanceType.JOINED)
public class ContaEntity {

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
    @Column(name = "tipo", nullable = false, length = 50)
    private EnumTipoConta tipo;

    @Column(name = "banco", nullable = false, length = 100)
    private String banco;

    @Column(name = "agencia", nullable = false, length = 100)
    private String agencia;

    @Column(name = "numero", nullable = false, length = 100)
    private String numero;

    @Column(name = "digito", nullable = false, length = 3)
    private String digito;

    @Column(name = "saldo", precision = 19, scale = 2)
    private BigDecimal saldo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity titular;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "gerente_id", nullable = false)
    private GerenteEntity gerente;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}
