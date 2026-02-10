package com.customersu.dashapi.cases.enderecos;

import com.customersu.dashapi.cases.pessoas.PessoaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "enderecos")
@Inheritance(strategy = InheritanceType.JOINED)
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @OneToOne(mappedBy = "endereco")
    private PessoaEntity pessoa;

    @Column(name = "pais", length = 100)
    private String pais;

    @Column(name = "uf", length = 2)
    private String uf;

    @Column(name = "cidade", length = 100)
    private String cidade;

    @Column(name = "bairro", length = 100)
    private String bairro;

    @Column(name = "cep", length = 20)
    private String cep;

    @Column(name = "logradouro", length = 200)
    private String logradouro;

    @Column(name = "numero", length = 20)
    private String numero;

    @Column(name = "complemento", length = 100)
    private String complemento;

    @Column(name = "observacao", length = 500)
    private String observacao;

    @PrePersist
    protected void onCreate() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    public EnderecoEntity(EnderecoDtoRequest dtoRequest) {
        setId(dtoRequest.getId());
        setAtivo(dtoRequest.getAtivo());
        setPais(dtoRequest.getPais());
        setUf(dtoRequest.getUf());
        setCidade(dtoRequest.getCidade());
        setBairro(dtoRequest.getBairro());
        setCep(dtoRequest.getCep());
        setLogradouro(dtoRequest.getLogradouro());
        setNumero(dtoRequest.getNumero());
        setComplemento(dtoRequest.getComplemento());
        setObservacao(dtoRequest.getObservacao());
    }
}
