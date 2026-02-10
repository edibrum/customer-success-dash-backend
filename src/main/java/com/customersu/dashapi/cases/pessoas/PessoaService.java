package com.customersu.dashapi.cases.pessoas;

import com.customersu.dashapi.cases.enderecos.EnderecoDtoRequest;
import com.customersu.dashapi.cases.enderecos.EnderecoDtoResponse;
import com.customersu.dashapi.cases.enderecos.EnderecoEntity;
import com.customersu.dashapi.cases.pfs.PfDtoRequest;
import com.customersu.dashapi.cases.pfs.PfDtoResponse;
import com.customersu.dashapi.cases.pfs.PfEntity;
import com.customersu.dashapi.cases.pfs.PfService;
import com.customersu.dashapi.cases.pjs.PjDtoRequest;
import com.customersu.dashapi.cases.pjs.PjDtoResponse;
import com.customersu.dashapi.cases.pjs.PjEntity;
import com.customersu.dashapi.cases.pjs.PjService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    private final PfService pfService;

    private final PjService pjService;

// MAPPER MANUAL SIMPLES:
//  Entity para DtoResponse:
    public PessoaDtoResponse toDtoResponse(PessoaEntity entity) {
        return PessoaDtoResponse.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .email(entity.getEmail())
                .telefone(entity.getTelefone())
                .ativo(entity.getAtivo())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .endereco(toEnderecoDtoResponse(entity.getEndereco()))
                .build();
    }

    @SuppressWarnings("unchecked")
    public static <T extends PessoaDtoResponse> T toDtoResponsePoliformPfPj(PessoaEntity pessoa) {
        // Força o Hibernate a entregar a instância real (PF ou PJ)
        Object realEntity = Hibernate.unproxy(pessoa);

        if (realEntity instanceof PfEntity pf) {
            return (T) PfDtoResponse.builder()
                    .id(pf.getId())
                    .nome(pf.getNome())
                    .email(pf.getEmail())
                    .telefone(pf.getTelefone())
                    .ativo(pf.getAtivo())
                    .dataCriacao(pf.getDataCriacao())
                    .dataAtualizacao(pf.getDataAtualizacao())
                    .endereco(toEnderecoDtoResponse(pf.getEndereco()))
                    .cpf(pf.getCpf())
                    .rg(pf.getRg())
                    .nascimento(pf.getNascimento())
                    .sexo(pf.getSexo())
                    .nomeSocial(pf.getNomeSocial())
                    .artigoPreferencia(pf.getArtigoPreferencia())
                    .build();
        } else if (realEntity instanceof PjEntity pj) {
            return (T) PjDtoResponse.builder()
                    .id(pj.getId())
                    .nome(pj.getNome())
                    .email(pj.getEmail())
                    .telefone(pj.getTelefone())
                    .ativo(pj.getAtivo())
                    .dataCriacao(pj.getDataCriacao())
                    .dataAtualizacao(pj.getDataAtualizacao())
                    .endereco(toEnderecoDtoResponse(pj.getEndereco()))
                    .cnpj(pj.getCnpj())
                    .nomeFantasia(pj.getNomeFantasia())
                    .inscEstadual(pj.getInscEstadual())
                    .inscMunicipal(pj.getInscMunicipal())
                    .abertura(pj.getAbertura())
                    .tipoEmpresa(pj.getTipoEmpresa())
                    .build();
        } else {
            throw new IllegalStateException("Tipo de DtoResponse desconhecido.");
        }
    }

    @SuppressWarnings("unchecked")
    public static <T extends PessoaDtoRequest> T toDtoRequestPoliformPfPj(PessoaEntity pessoa) {
        if (pessoa instanceof PfEntity pf) {
            return (T) PfDtoRequest.builder()
                    .id(pf.getId())
                    .nome(pf.getNome())
                    .email(pf.getEmail())
                    .telefone(pf.getTelefone())
                    .ativo(pf.getAtivo())
                    .endereco(toEnderecoDtoRequest(pf.getEndereco()))
                    .cpf(pf.getCpf())
                    .rg(pf.getRg())
                    .nascimento(pf.getNascimento())
                    .sexo(pf.getSexo())
                    .nomeSocial(pf.getNomeSocial())
                    .artigoPreferencia(pf.getArtigoPreferencia())
                    .build();
        } else if (pessoa instanceof PjEntity pj) {
            return (T) PjDtoRequest.builder()
                    .id(pj.getId())
                    .nome(pj.getNome())
                    .email(pj.getEmail())
                    .telefone(pj.getTelefone())
                    .ativo(pj.getAtivo())
                    .endereco(toEnderecoDtoRequest(pj.getEndereco()))
                    .cnpj(pj.getCnpj())
                    .nomeFantasia(pj.getNomeFantasia())
                    .inscEstadual(pj.getInscEstadual())
                    .inscMunicipal(pj.getInscMunicipal())
                    .abertura(pj.getAbertura())
                    .tipoEmpresa(pj.getTipoEmpresa())
                    .build();
        } else {
            throw new IllegalStateException("Tipo de DtoRequest desconhecido");
        }
    }


    public static PessoaEntity toEntityPoliformPfPj(PessoaDtoRequest pessoaDto) {
        if (pessoaDto instanceof PfDtoRequest pfDto) {
            PfEntity pf = new PfEntity();
            pf.setNome(pfDto.getNome());
            pf.setEmail(pfDto.getEmail());
            pf.setTelefone(pfDto.getTelefone());
            pf.setAtivo(pfDto.getAtivo() != null ? pfDto.getAtivo() : true);
            pf.setEndereco(pfDto.getEndereco() != null ? saveEndereco(pfDto.getEndereco()) : null);
            // específicos PF
            pf.setCpf(pfDto.getCpf());
            pf.setRg(pfDto.getRg());
            pf.setNascimento(pfDto.getNascimento());
            pf.setSexo(pfDto.getSexo());
            pf.setNomeSocial(pfDto.getNomeSocial());
            pf.setArtigoPreferencia(pfDto.getArtigoPreferencia());

            return pf;

        } else if (pessoaDto instanceof PjDtoRequest pjDto) {
            PjEntity pj = new PjEntity();
            pj.setNome(pjDto.getNome());
            pj.setEmail(pjDto.getEmail());
            pj.setTelefone(pjDto.getTelefone());
            pj.setAtivo(pjDto.getAtivo() != null ? pjDto.getAtivo() : true);
            pj.setEndereco(pjDto.getEndereco() != null ? saveEndereco(pjDto.getEndereco()) : null);
            // específicos PJ
            pj.setCnpj(pjDto.getCnpj());
            pj.setNomeFantasia(pjDto.getNomeFantasia());
            pj.setInscEstadual(pjDto.getInscEstadual());
            pj.setInscMunicipal(pjDto.getInscMunicipal());
            pj.setAbertura(pjDto.getAbertura());
            pj.setTipoEmpresa(pjDto.getTipoEmpresa());

            return pj;

        } else {
            throw new IllegalArgumentException("Tipo desconhecido." + pessoaDto.getClass().getName());
        }
    }

// CRUD:
//  C - CREATE
    public PessoaDtoResponse criar(PessoaDtoRequest dto) {

        if (pessoaRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        PessoaEntity entity = PessoaEntity.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .telefone(dto.getTelefone())
                .ativo(dto.getAtivo() != null ? dto.getAtivo() : true)
                .endereco(dto.getEndereco() != null ? saveEndereco(dto.getEndereco()) : null)
                .build();

        PessoaEntity salva = pessoaRepository.save(entity);

        return toDtoResponse(salva);
    }

//  R - READ (por ID e todos)
    public PessoaDtoResponse buscarPorId(Long id) {
        PessoaEntity entity = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
        return toDtoResponse(entity);
    }

    public PessoaEntity buscarEntityPorId(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
    }

    public List<PessoaDtoResponse> listarTodos() {
        return pessoaRepository.findAll()
                .stream()
                .map(this::toDtoResponse)
                .collect(Collectors.toList());
    }

    public Page<PessoaDtoResponse> listarPaginado(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return pessoaRepository.findAll(pageable)
                .map(this::toDtoResponse);
    }

//  U - UPDATE
    public PessoaDtoResponse atualizar(Long id, PessoaDtoRequest dto) {
        PessoaEntity entity = pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));

        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setTelefone(dto.getTelefone());
        entity.setAtivo(dto.getAtivo());
        //ENDERECO
            EnderecoEntity endereco = saveEndereco(dto.getEndereco());
            entity.setEndereco(endereco);

        PessoaEntity atualizada = pessoaRepository.save(entity);

        return toDtoResponse(atualizada);
    }

//  D - DELETE
    public void deletar(Long id) {
        if (!pessoaRepository.existsById(id)) {
            throw new RuntimeException("Registro não encontrado.");
        }
        pessoaRepository.deleteById(id);
    }


// AUXILIAR - para criar/atualizar regsitros de Gerentes/Clientes:
    public PessoaEntity criarOuAtualizarRegistroPessoa(PessoaDtoRequest dto) {
        PessoaEntity pessoaEntity = toEntityPoliformPfPj(dto);
        PessoaDtoResponse dtoResp;

        if(pessoaEntity instanceof PfEntity pf) {
            //é PF:
            PfDtoRequest pfDtoReq = toDtoRequestPoliformPfPj(pf);
            if(dto.getId() != null && pfService.buscarPorId(dto.getId()) != null){
                //registro de PF já existe - atualizar:
                dtoResp = pfService.atualizar(dto.getId(), pfDtoReq);
                pessoaEntity.setId(dtoResp.getId());
            } else {
                dtoResp = pfService.criar(pfDtoReq);
                pessoaEntity.setId(dtoResp.getId());
            }
        } else if (pessoaEntity instanceof PjEntity pj) {
            //é PJ:
            PjDtoRequest pjDtoReq = toDtoRequestPoliformPfPj(pj);
            if(dto.getId() != null && pjService.buscarPorId(dto.getId()) != null){
                //registro de PJ já existe - atualizar:
                dtoResp = pjService.atualizar(dto.getId(), pjDtoReq);
                pessoaEntity.setId(dtoResp.getId());
            } else {
                dtoResp = pjService.criar(pjDtoReq);
                pessoaEntity.setId(dtoResp.getId());
            }
        } else {
            throw new RuntimeException("Tipo de Pessoa(PF/PJ) não identificado.");
        }

        return buscarEntityPorId(pessoaEntity.getId());
    }

    //ENDERECO
    private static EnderecoEntity saveEndereco(EnderecoDtoRequest dtoEnderecoRequest) {
        return EnderecoEntity.builder()
                .pais(dtoEnderecoRequest.getPais())
                .uf(dtoEnderecoRequest.getUf())
                .cidade(dtoEnderecoRequest.getCidade())
                .bairro(dtoEnderecoRequest.getBairro())
                .cep(dtoEnderecoRequest.getCep())
                .logradouro(dtoEnderecoRequest.getLogradouro())
                .numero(dtoEnderecoRequest.getNumero())
                .complemento(dtoEnderecoRequest.getComplemento())
                .observacao(dtoEnderecoRequest.getObservacao())
                .ativo(dtoEnderecoRequest.getAtivo() != null ? dtoEnderecoRequest.getAtivo() : true)
                .build();
    }

    public static EnderecoDtoResponse toEnderecoDtoResponse(EnderecoEntity enderecoEntity) {
        return EnderecoDtoResponse.builder()
                .id(enderecoEntity.getId())
                .ativo(enderecoEntity.getAtivo())
                .dataCriacao(enderecoEntity.getDataCriacao())
                .dataAtualizacao(enderecoEntity.getDataAtualizacao())
                .logradouro(enderecoEntity.getLogradouro())
                .bairro(enderecoEntity.getBairro())
                .cep(enderecoEntity.getCep())
                .numero(enderecoEntity.getNumero())
                .cidade(enderecoEntity.getCidade())
                .uf(enderecoEntity.getUf())
                .pais(enderecoEntity.getPais())
                .complemento(enderecoEntity.getComplemento())
                .observacao(enderecoEntity.getObservacao())
                .build();
    }

    public static EnderecoDtoRequest toEnderecoDtoRequest(EnderecoEntity enderecoEntity) {
        return EnderecoDtoRequest.builder()
                .id(enderecoEntity.getId())
                .ativo(enderecoEntity.getAtivo())
                .logradouro(enderecoEntity.getLogradouro())
                .bairro(enderecoEntity.getBairro())
                .cep(enderecoEntity.getCep())
                .numero(enderecoEntity.getNumero())
                .cidade(enderecoEntity.getCidade())
                .uf(enderecoEntity.getUf())
                .pais(enderecoEntity.getPais())
                .complemento(enderecoEntity.getComplemento())
                .observacao(enderecoEntity.getObservacao())
                .build();
    }

}
