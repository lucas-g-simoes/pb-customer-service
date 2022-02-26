# Customer Service
Serviço responsável pelo gerenciamento de clientes.

### Tecnologias utilizadas:

* **Spring:** Spring Boot, Spring Web, Spring Data JPA.
* **Flyway:** versionamento de dados (migrations).
* **Maven:**  gerenciamento do projeto.
* **PostgresJDBC:**  driver para conexão com banco.
* **Swagger:** documentação da API.
* **Actuator:** healh checker e metricas.
* **Micrometer:** exportação de métricas.
* **Mapper:** mapper DTO/Entity.
* **Docker:** container do serviço.
* **JUnit:** execução de testes (unitátios e integrados).
* **TestContainer, RestAssured, Hamcrest, JUnit:** suite de testes integrados, permitindo ter um teste mais próximo ao ambiente de produção, executando da entrada (endpoint API) até etapa final (banco de dados).
* **Jacoco:** responsável por garantir a cobertura dos testes.
* **PITest:** responsável por garantir a qualidade dos testes através dos testes de mutação (abaixo mais detalhes de como executar).

### Build
Para realizar o build do serviço execute:
```sh
$ mvn clean install
```
O build só é feito com sucesso caso a cobertura de testes esteja conforme especificado e os testes (unitários e integrados) sem erros.


### Testes Automatizados (Unitários, Integrados e Mutação)
O serviço possui testes que garantem o funcionamento de algumas regras e validações.

#### Cobertura de Testes
Foi adicionado o plugin **jacoco** que faz uma analise sobre a cobertura de testes. É possivel ver
após o `mvn clean install` na pasta **./target/site/jacoco/index.html**

#### Testes Integrados
A suite de testes integrados é composta pelas seguintes tecnologias:
* **TestContainers**: responsável por subir containers docker para execução da bateria de testes integrados, ao fim de todos os testes os containers são encerrados.
* **RestAssured**: permite a execução dos endpoints da API's para realizar o teste de ponta a ponta.
* **Hamcrest**: responsável pelas asserções dos testes.
* **JUnit**: execução dos testes.

#### PITest: Mutation Test (qualidade dos testes)
Para gerar um relatório que mensure a qualidade dos testes escritos execute:
```sh
$ mvn org.pitest:pitest-maven:mutationCoverage
```
O teste de mutação consiste em alterações de código no momento de compilação para execução dos testes, isso faz com que leve em torno de 2 à 3 minutos para rodar.


### Flyway
Toda criação/alteração na estrutura do projeto é versionada. O projeto possui dois scripts que são executados de forma sequencial:

* **V1611969758888__init.sql:** responsável pela criação da estrutura de tabelas do serviço.
* **V1611969783944__initSetup.sql:** responsável por um "boot" inicial de dados do serviço.

### Swagger
Após o serviço estar no ar, a documentação da API pode ser vista clicando [aqui](http://localhost:8000/swagger-ui.html)

[Screenshot](https://drive.google.com/file/d/1dnnTcvsiP4LTxzInnRsRhD7v8VA0dYPm/view?usp=sharing)

### Micrometer + Prometheus + Grafana
Através destas tecnologias é possível trabalhar com métricas de serviço.

Para visualizar primeiro é preciso subir os serviços no **docker-compose.yml**. Após isso, acessar o grafana através do endereço **http://localhost:8300**.

O painel do grafana apresentará uma area para login, digital admin/admin para logar.

Feito isso, é preciso adicionar o "datasource" do prometheus que já está coletando as metricas expostas pelo micrometer.
[veja aqui](https://drive.google.com/file/d/1OiUAXd9D1dtYnvb6fgIyOKGDyplYZl08/view?usp=sharing) como fazer isso.

Para este caso usaremos um dashboard padrão do Spring Boot, então com a conexão efetuada com sucesso clique em importar Dashboard no menu a esquerda.

No campo **Grafana.com Dashboard** digite o ID: **6756**.
Em poucos segundos você será redirecionado para uma tela como [esta](https://drive.google.com/file/d/1omLrq2BC747jclQz6ZDZYTykdvSb3_4i/view?usp=sharing)

No campo **"Prometheus"** selecione nosso serviço **"Customer Service"** e clique em **Import**.

No topo do dashboard, em **"Instance"** coloque o caminho do container do **"Customer Service"**: pb-customer-service:8000 (conforme o host docker).

Com isso o dashboard sera apresentado, conforme a [imagem](https://drive.google.com/file/d/1tb7yY3Rxk2-HTCKRLe_QdSJ8xbLFzRff/view?usp=sharing).

### Debug Remoto
Para debugar remotamente atribuir ao JAVA_OPTS no em **pb-customer-service** do docker-compose o seguinte valor:
```sh
 - JAVA_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8000,suspend=y # REMOTE DEBUG
```
Com isso, ao executar `docker-compose up` o container ficará aguardando a conexão remota para iniciar.

