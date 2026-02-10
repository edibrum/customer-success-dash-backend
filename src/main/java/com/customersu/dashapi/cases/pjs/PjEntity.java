package com.customersu.dashapi.cases.pjs;

import com.customersu.dashapi.cases.pessoas.PessoaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "pjs")
@DiscriminatorValue("PJ")
public class PjEntity extends PessoaEntity {

    @Column(name = "cnpj", nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(name = "nome_fantasia", length = 150)
    private String nomeFantasia;

    @Column(name = "insc_estadual", length = 20)
    private String inscEstadual;

    @Column(name = "insc_municipal", length = 20)
    private String inscMunicipal;

    @Column(name = "abertura")
    private LocalDate abertura;

    @Column(name = "tipo_empresa", length = 50)
    private EnumTipoEmpresa tipoEmpresa;

}
