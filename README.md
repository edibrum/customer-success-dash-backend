# customer-success-dash-backend
Spring Boot API for a Customer Success Dashboard with KPIs (Key Performance Indicators), product gap analysis and task auditing. [🇧🇷 Português]: API Spring Boot para um painel "Sucesso do Cliente" com KPIs (indicadores-chave de desempenho), gap de produtos e auditoria de tarefas.

Conexão Inicial com banco de dados PostgreSQL
  - crie o database com nome de sua preferência (usei o nome "customersudashdb");
  - configure as credenciais/porta definidas em seu postgres (em /resources/application.properties, usei o padrão "5432/postgres/postgres");
  - rode o projeto Java e a conexão será realizada, gerando no schema public todas as tabelas;
  - utilizei o PgAdmin para visualizar e conferir;
