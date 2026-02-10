package com.customersu.dashapi.cases.pfs;

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
@Table(name = "pfs")
@DiscriminatorValue("PF")
public class PfEntity extends PessoaEntity {

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "rg", length = 20)
    private String rg;

    @Column(name = "nascimento")
    private LocalDate nascimento;

    @Column(name = "sexo", length = 10)
    private String sexo;

    @Column(name = "nome_social", length = 150)
    private String nomeSocial; //* obs.: inclusão

    @Column(name = "artigo_preferencia", length = 10)
    private String artigoPreferencia; //* obs.: inclusão

}
