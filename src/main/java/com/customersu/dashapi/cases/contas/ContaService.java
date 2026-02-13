package com.customersu.dashapi.cases.contas;

import com.customersu.dashapi.cases.clientes.ClienteService;
import com.customersu.dashapi.cases.gerentes.GerenteService;
import com.customersu.dashapi.cases.pfs.PfEntity;
import com.customersu.dashapi.cases.pjs.PjEntity;
import jakarta.persistence.criteria.Join;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;

    private final ClienteService clienteService;

    private final GerenteService gerenteService;

// MAPPER MANUAL SIMPLES:
//  Entity para DtoResponse:
    public ContaDtoResponse toDtoResponse(ContaEntity entity) {
        return ContaDtoResponse.builder()
                .id(entity.getId())
                .ativo(entity.getAtivo())
                .dataCriacao(entity.getDataCriacao())
                .dataAtualizacao(entity.getDataAtualizacao())
                .tipo(entity.getTipo())
                .banco(entity.getBanco())
                .agencia(entity.getAgencia())
                .numero(entity.getNumero())
                .digito(entity.getDigito())
                .saldo(entity.getSaldo())
                .titular(clienteService.toDtoResponse(entity.getTitular()))
                .gerente(gerenteService.toDtoResponse(entity.getGerente()))
                .build();
    }

// CRUD:
//  C - CREATE
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ContaDtoResponse criar(ContaDtoRequest dto) {

        if (contaRepository.existsByAgenciaAndNumeroAndDigito(dto.getAgencia(), dto.getNumero(), dto.getDigito())) {
            throw new RuntimeException("Conta já cadastrada com mesmos dados (Agência, Número e Dígito).");
        }

        ContaEntity entity = ContaEntity.builder()
                .ativo(dto.getAtivo() != null ? dto.getAtivo() : true)
                .tipo(dto.getTipo())
                .banco(dto.getBanco())
                .agencia(dto.getAgencia())
                .numero(dto.getNumero())
                .digito(dto.getDigito())
                .saldo(dto.getSaldo())
                .titular(clienteService.buscarEntityPorId(dto.getTitularId()))
                .gerente(gerenteService.buscarEntityPorId(dto.getGerenteId()))
                .build();

        ContaEntity salva = contaRepository.save(entity);

        return toDtoResponse(salva);
    }

//  R - READ (por ID e todos)
    public ContaDtoResponse buscarPorId(Long id) {
        ContaEntity entity = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
        return toDtoResponse(entity);
    }

    public ContaEntity buscarEntityPorId(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
    }

    public List<ContaDtoResponse> listarTodos() {
        return contaRepository.findAll()
                .stream()
                .map(this::toDtoResponse)
                .collect(Collectors.toList());
    }

    public Page<ContaDtoResponse> listarPaginado(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return contaRepository.findAll(pageable)
                .map(this::toDtoResponse);
    }

    public Page<ContaDtoResponse> listarComFiltros(Long gerenteId, String tipoConta, String tipoPessoa, LocalDate inicio, LocalDate fim, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // Inicia com uma spec válida (sempre verdadeira)
        Specification<ContaEntity> spec = (root, query, cb) -> cb.conjunction();

        if (tipoConta != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("tipo"), EnumTipoConta.fromString(tipoConta)));
        }

        if (gerenteId != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("gerente").get("id"), gerenteId));
        }

        if (tipoPessoa != null) {
            spec = spec.and((root, query, cb) -> {
                Join<?, ?> pessoaJoin = root.join("titular").join("pessoa");

                if (tipoPessoa.equalsIgnoreCase("PF")) {
                    return cb.equal(pessoaJoin.type(), PfEntity.class);
                } else if (tipoPessoa.equalsIgnoreCase("PJ")) {
                    return cb.equal(pessoaJoin.type(), PjEntity.class);
                } else {
                    return cb.conjunction();
                }
            });
        }

        if (inicio != null && fim != null) {
            // Ajustado para 'dataCriacao' e conversão de data para hora
            spec = spec.and((root, query, cb) -> cb.between(root.get("dataCriacao"), inicio.atStartOfDay(), fim.atTime(23, 59, 59)));
        }

        return contaRepository.findAll(spec, pageable).map(this::toDtoResponse);
    }

//  U - UPDATE
    public ContaDtoResponse atualizar(Long id, ContaDtoRequest dto) {
        ContaEntity entity = contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado."));
        entity.setTipo(dto.getTipo());
        entity.setBanco(dto.getBanco());
        entity.setAgencia(dto.getAgencia());
        entity.setNumero(dto.getNumero());
        entity.setDigito(dto.getDigito());
        entity.setSaldo(dto.getSaldo());
        entity.setTitular(clienteService.buscarEntityPorId(dto.getTitularId()));
        entity.setGerente(gerenteService.buscarEntityPorId(dto.getGerenteId()));
        entity.setAtivo(dto.getAtivo());

        ContaEntity atualizada = contaRepository.save(entity);

        return toDtoResponse(atualizada);
    }

//  D - DELETE
    public void deletar(Long id) {
        if (!contaRepository.existsById(id)) {
            throw new RuntimeException("Registro não encontrado.");
        }
        contaRepository.deleteById(id);
    }

}
