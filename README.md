# PlatformBuilders Test
Projeto baseado em Docker, possuindo 4 containers, sendo eles:

* pb-database-server: container responsável pela base de dados com PostgreSQL
* pb-customer-service: serviço de clientes
* zup-prometheus-service: serviço de gerenciamento de métricas
* zup-grafana-service: serviço de apresentação das métricas

### Estrutura de pastas
Entendendo a estrutura do projeto:

* **./applications**: local destinado aos serviços (neste caso temos apenas um). Visualize o README interno do serviço para maiores detalhes.
* **./metrics**: local onde se encontra os arquivos de configurações dos serviços de métricas (prometheus e grafana).
* **./storage**: local onde se encontra a configuração do banco (Dockerfile + init.sql) e o mapeamento do Volume (Banco de Dados).
* **./.env**: arquivo contendo as variáveis de ambiente.
* **./Builders.postman_collection.json**: arquivo com a collection do Postman para teste da aplicação.
* **./docker-compose.yml**: docker-compose, para iniciar o projeto execute `docker-compose up`.

### Start do Projeto
Antes de iniciar o projeto verifique o **README** interno do serviço de cliente que possui maiores detalhes do serviço, encontrado em **/applications/customer-service**.

Primeiro é necessário fazer o build do serviço:
```sh
$ cd ./applications/customer-service
$ mvn clean install
```
Feito isto, basta subir os containers através do **docker-compose**:
```sh
$ docker-compose up
```

OBS: antes de subir verificar se as portas não estão ocupadas: 5432 (postgres), 8000 (customer service), 8200 (prometheus), 8300 (grafana).
