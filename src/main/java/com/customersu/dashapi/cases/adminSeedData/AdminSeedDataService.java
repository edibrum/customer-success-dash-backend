package com.customersu.dashapi.cases.adminSeedData;

import com.customersu.dashapi.cases.clientes.ClienteDtoRequest;
import com.customersu.dashapi.cases.clientes.ClienteService;
import com.customersu.dashapi.cases.contas.ContaDtoRequest;
import com.customersu.dashapi.cases.contas.ContaService;
import com.customersu.dashapi.cases.contratos.ContratoDtoRequest;
import com.customersu.dashapi.cases.contratos.ContratoService;
import com.customersu.dashapi.cases.gerentes.GerenteDtoRequest;
import com.customersu.dashapi.cases.gerentes.GerenteService;
import com.customersu.dashapi.cases.produtos.ProdutoDtoRequest;
import com.customersu.dashapi.cases.produtos.ProdutoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.type.TypeReference;

import java.util.List;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class AdminSeedDataService {

    private final ObjectMapper objectMapper;
    private final ClienteService clienteService;
    private final GerenteService gerenteService;

    private final ProdutoService produtoService;

    private final ContaService contaService;

    private final ContratoService contratoService;

    @Transactional
    public String popularSeedDataBase() {
        if(!clienteService.listarTodos().isEmpty()) {
            return "O banco de dados já está populado!";
            //throw new RuntimeException("Banco de dados já está populado!");
        }

        System.out.println("Trabalhando na carga de dados inicial para o projeto...");

        try {
        System.out.println("####################################\n... Produtos ...");
        criar2Produtos();
        System.out.println("####################################\n... Gerente ...");
        criar1Gerente();
        System.out.println("####################################\n... Clientes PJ ...");
        criar50ClientesPJ();
        System.out.println("####################################\n... Clientes PF ...");
        criar90ClientesPF();
        System.out.println("####################################\n... Contas CORRENTE ...");
        criar130ContasCORRENTE();
        System.out.println("####################################\n... Contratos ...");
        criar108Contratos();

            return "Registros iniciais cadastrados com sucesso no banco de dados. Aproveite a aplicação.";
        } catch (Exception e) {
            return "Infelizmente ocorreu um erro ao tentar registrar dados no banco. Tente novamente mais tarde.";
            //throw new RuntimeException("####################################\n Falha ao popular banco de dados: " + e.getMessage());
        }
    }

    @Transactional
    public void criar2Produtos() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/data/new2produtos.json");
            List<ProdutoDtoRequest> dtos = objectMapper.readValue(inputStream, new TypeReference<List<ProdutoDtoRequest>>(){});
            dtos.forEach(dto -> {
                try {
                    produtoService.criar(dto);
                } catch (Exception e) {
                    System.out.println("Erro ao registrar produto: " + dto.getCodigo() + " - " + dto.getDescricao());
                }
            });

            System.out.println("Produtos registrados com sucesso!");
        } catch (Exception e) {
            //throw new RuntimeException("Falha ao popular banco de dados: " + e.getMessage());
        }
    }

    @Transactional
    public void criar1Gerente() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/data/new1gerente.json");
            List<GerenteDtoRequest> dtos = objectMapper.readValue(inputStream, new TypeReference<List<GerenteDtoRequest>>(){});
            dtos.forEach(dto -> {
                try {
                    gerenteService.criar(dto);
                } catch (Exception e) {
                    System.out.println("Erro ao registrar gerente: " + dto.getPessoa().getNome());
                }
            });

            System.out.println("Gerente registrado com sucesso!");
        } catch (Exception e) {
            //throw new RuntimeException("Falha ao popular banco de dados: " + e.getMessage());
        }
    }

    @Transactional
    public void criar50ClientesPJ() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/data/new50clientesPJ.json");
            List<ClienteDtoRequest> dtos = objectMapper.readValue(inputStream, new TypeReference<List<ClienteDtoRequest>>(){});
            dtos.forEach(dto -> {
                try {
                    clienteService.criar(dto);
                } catch (Exception e) {
                    System.out.println("Erro ao registrar cliente PJ: " + dto.getPessoa().getNome());
                }
            });
            System.out.println("Clientes PJ registrados com sucesso!");
        } catch (Exception e) {
            //throw new RuntimeException("Falha ao popular banco de dados: " + e.getMessage());
        }
    }

    @Transactional
    public void criar90ClientesPF() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/data/new90clientesPF.json");
            List<ClienteDtoRequest> dtos = objectMapper.readValue(inputStream, new TypeReference<List<ClienteDtoRequest>>(){});
            dtos.forEach(dto -> {
                try {
                    clienteService.criar(dto);
                } catch (Exception e) {
                    System.out.println("Erro ao registrar cliente PF: " + dto.getPessoa().getNome());
                }
            });
            System.out.println("Clientes PF registrados com sucesso!");
        } catch (Exception e) {
            //throw new RuntimeException("Falha ao popular banco de dados: " + e.getMessage());
        }
    }

    @Transactional
    public void criar130ContasCORRENTE() {
            try {
                InputStream inputStream = getClass().getResourceAsStream("/data/new130contas.json");
                List<ContaDtoRequest> dtos = objectMapper.readValue(inputStream, new TypeReference<List<ContaDtoRequest>>(){});
                dtos.forEach(dto -> {
                    try {
                        contaService.criar(dto);
                    } catch (Exception e) {
                        System.out.println("Erro ao registrar conta: " + dto.getNumero() + " - " + dto.getDigito());
                    }
                });
                System.out.println("Contas registradas com sucesso!");
            } catch (Exception e) {
                //throw new RuntimeException("Falha ao popular banco de dados: " + e.getMessage());
            }
    }

    @Transactional
    public void criar108Contratos() {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/data/new108contratos.json");
            List<ContratoDtoRequest> dtos = objectMapper.readValue(inputStream, new TypeReference<List<ContratoDtoRequest>>(){});
            dtos.forEach(dto -> {
                try {
                    contratoService.criar(dto);
                } catch (Exception e) {
                    System.out.println("Erro ao registrar contrato: contaId(" + dto.getContaId() + ") E produtoId(" + dto.getProdutoId() + ")");
                }
            });
            System.out.println("Contratos registrados com sucesso!");
        } catch (Exception e) {
            //throw new RuntimeException("Falha ao popular banco de dados: " + e.getMessage());
        }
    }

}
