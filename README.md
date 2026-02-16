# customer-success-dash-backend
Spring Boot API for a Customer Success Dashboard with KPIs (Key Performance Indicators), product gap analysis and task auditing. [🇧🇷 Português]: API Spring Boot para um painel "Sucesso do Cliente" com KPIs (indicadores-chave de desempenho), gap de produtos e auditoria de tarefas.

---------------------------------------------------------------------------  [por Edi]  ---------------------------------------------------------------

<H3>Conexão com Banco de Dados PostgreSQL</H3>
  - crie o database com nome de sua preferência (usei o nome "customersudashdb");
  - configure as credenciais/porta definidas em seu postgres (em /resources/application.properties, usei o padrão "5432/postgres/postgres");
  - rode o projeto Java e a conexão será realizada, gerando no schema public todas as tabelas;
  - utilizei o PgAdmin para visualizar e conferir;

<H3>Rotas da API - Coleção Postman</H3>
Será possível testar as rotas antes através de collection já montada no Postman [link](https://edibrbr-4184552.postman.co/workspace/Edi-Brbr's-Workspace~2b009128-0d97-4c4f-87c1-c2c4d6d5ab89/collection/52212305-08116c40-0bbb-40b5-91da-613e508ba804?action=share&creator=52212305&active-environment=52212305-d89a90a4-879e-4c19-a522-31576d949c40);
  - observe o valor {{baseUrl}} sugerido;
  - observe a subpasta "RUNNER_INICIAL" - para iterações por meio de coleções (arquivos JSON já preparados);

<H3>A carga inicial dos dados também ficou facilitada através de uma rota dedicada somente para isso</H3>
Ao rodar o projeto Java, note os arquivos .json em /resources/data/, cujos nomes explicitam seu uso - são os arquivos utilizados para a carga inicial de dados. O controller "AdminSeedData" é dedicado a esta ideia (apenas para facilitar o primeiro contato com o projeto). Será possível testar via Postman ou também ao rodar o front-end junto.

<H3>Projeto em evolução...</H3>
Você perceberá que o projeto está em desenvolvimento... Sugestões, feedbacks, ideias novas são bem-vindas!





