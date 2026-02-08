package com.customersu.dashapi.cases.pfs;

import com.customersu.dashapi.cases.pessoas.PessoaDtoRequest;
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
public class PfDtoRequest extends PessoaDtoRequest {
    private String cpf;
    private String rg;
    private LocalDate nascimento;
    private String sexo;
    private String nomeSocial; //* obs.: inclusão
    private String artigoPreferencia; //* obs.: inclusão
    //OBS.: consideremos o campo "nome" no PessoaDtoRequest como a "nome completo da pessoa física" neste caso aqui
}
