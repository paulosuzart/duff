## Duff

Esse projeto tem como objetivo expor uma API REST para realizar o CRUD de cervejas e da temperatura ideal para o consumo. O projeto foi desenvolvido utilizando **Java 8**, **Spring Boot 2**, **MongoDB 3** e **Apache Maven**.

## Instalação

### Compilação

Para compilar o projeto basta utilizar o comando **mvn clean install** na raiz do projeto ou utilizar o alias mvnw (para usuários de Linux) ou mvnw.cmd (para usuários de Windows). Exemplo:

```sh
$ mvn clean install # Utilizando Maven instalado no sistema operacional
```

```
$ ./mvnw clean install # Utilizando o alias mvnw para a compilação
```

Lembrando que o projeto utiliza Java 8. Recomendo utilizar o **OpenJDK 64-Bit Server VM (Zulu 8.31.0.1-linux64)**, mas você pode utilizar a versão do Java 8 que desejar.

Caso você utilize sistema operacional **Linux**, recomendo fortemente utilizar o [SDKMan!](https://sdkman.io/) para instalar o Maven e o Java 8.

### Dependências

Esse projeto utiliza o banco de dados NoSQL [MongoDB 3.4.10](https://www.mongodb.com/download-center/v2/community). Caso você possua [Docker](https://www.docker.com/) e o [Docker-Compose](https://docs.docker.com/compose/install/#install-compose) (espero que sim!!!), utilize o comando docker-compose up para iniciar o container. Exemplo:

```sh
$ docker-compose up
```

O MongoDB é executado na porta padrão, **27017**. Caso você não utilize o **Docker** e o **Docker-Compose**, será necessário instalar o MongoDB 3.4.10+ na sua máquina local.

### Execução

Você pode iniciar o projeto de duas formas. A primeirá é executando o **uberjar** gerado pela compilação do projeto que se localiza em web/target da seguinte forma:


```sh
$ cd web/target && java -jar duff-web-1.0.0-SNAPSHOT.jar
```

Ou utilizar o plugin do Spring Boot no diretório web. Exemplo:

```sh
$ cd web && mvn spring-boot:run # Com maven instalado no sistema operacional
```

```sh
$ cd web && ../mvnw spring-boot:run # Com o alias mvnw
```

E voilà!!! A aplicação executa na porta **8080** e o contexto base é **/**.

## Endpoints REST

### Health Check

Nesse projeto é utilizado o **Spring Boot Actuator**, então o endereço do health check é:

```sh
$ curl -X GET 'http://localhost:8080/actuator/health'
```

### CRUD de Beers

Os serviços REST do CRUD de beers ficam em **/beers**. Veja alguns exemplos das operações:

#### Salvando uma nova cerveja
```sh
$ curl -X POST -H 'Content-Type: application/json' --data '{ "beerStyle": "Guardians of Galaxy", "min": 10, "max": 18 }' 'http://localhost:8080/beers'
```

API retorna o ID dessa cerveja:

```js
{"id":"5bba0ed7af5b8f6e2c8e350b"}
```

#### Alterando a cerveja

````sh
$ curl -X PUT -H 'Content-Type: application/json' --data '{ "id":"5bba0ed7af5b8f6e2c8e350b", "beerStyle": "Guardians of Galaxy", "min": -7, "max": 7 }' 'http://localhost:8080/beers'
````

Novamente retorna o ID da cerveja alterada:

```js
{"id":"5bba0ed7af5b8f6e2c8e350b"}
```

#### Buscar cerveja por ID

```sh
$ curl -X GET 'http://localhost:8080/beers/5bba0ed7af5b8f6e2c8e350b'
```

A cerveja:

```js
{"id":"5bba0ed7af5b8f6e2c8e350b","beerStyle":"Guardians of Galaxy","min":-7,"max":7}
```

#### Buscando todas as cervejas cadastradas

```sh
$ curl -X GET 'http://localhost:8080/beers'
```

Todas as cervejas:

```js
[{"id":"5bb928342527c839c30b6472","beerStyle":"Weissbier","min":-1,"max":3},{"id":"5bb928342527c839c30b6473","beerStyle":"Pilsens","min":-2,"max":4},{"id":"5bb928342527c839c30b6474","beerStyle":"Weizenbier","min":-4,"max":6},{"id":"5bb928342527c839c30b6475","beerStyle":"Red ale","min":-5,"max":5},{"id":"5bb928342527c839c30b6476","beerStyle":"India pale ale","min":-6,"max":7},{"id":"5bb928342527c839c30b6477","beerStyle":"IPA","min":-7,"max":10},{"id":"5bb928342527c839c30b6478","beerStyle":"Dunkel","min":-8,"max":2},{"id":"5bb928342527c839c30b6479","beerStyle":"Imperial Stouts","min":-10,"max":13},{"id":"5bb928342527c839c30b647a","beerStyle":"Brown ale","min":0,"max":14},{"id":"5bba0ed7af5b8f6e2c8e350b","beerStyle":"Guardians of Galaxy","min":-7,"max":7}]
```

#### Apagando cerveja por ID

```sh
$ curl -X DELETE 'http://localhost:8080/beers/5bba0ed7af5b8f6e2c8e350b'
```

Retorna um HTTP 200.

### Uma amostra de cervejas

Se você quer uma amostra de cervejas no seu banco de dados, execute o script **beers.sh** (somente para Linux :D) que lá existem comandos prontos para inserir via a API REST algumas cervejas. Não esqueça de iniciar o MongoDB e a aplicação antes de executar esse script. Exemplo:

```sh
$ ./beers.sh
```


### Pedindo a opinião do DuffMan

Para saber qual é a cerveja ideal segundo uma temperatura e também uma playlist do **Spotify** baseada nessa informação basta utilizar o serviço REST /duffman. É um HTTP POST que pode ser realizado dessa forma:

```sh
$ curl -X POST -H 'Content-Type: application/json' --data '{"temperature": -2 }' 'http://localhost:8080/duffman'
```

Existe um script chamado **duffman.sh** (Linux) que contém alguns exemplos de chamadas para o DuffMan!!!, mas você pode chamar do jeito que você quiser. É só executar dessa forma:

```sh
$ ./duffman.sh
```

#### Preciso sobrescrever uma configuração da aplicação... E agora?

É fácil... basta passar uma System Property com o novo valor. Digamos que vamos mudar o endereço do MongoDB, o client_id e o client_secret utilizados pela aplicação. Basta fazer isso ao iniciar a aplicação:

```sh
$ java -Dspring.data.mongodb.uri=mongodb://localhost:27018/xablau -Dspotify.clientId=foo -Dspotify.clientSecret=bar -jar duff-web-1.0.0-SNAPSHOT.jar
```

Você pode verificar as propriedades existentes no arquivo **application.properties** dentro do módulo web, em src/main/resources.

#### Gerando imagem Docker da aplicação

Você pode gerar a imagem Docker da aplicação utilizando o seguinte comando (após ter compilado a aplicação):

```sh
$ cd web && mvn docker:build
```

E para executar o container, basta:

```sh
$ docker run -e SPRING_DATA_MONGODB_URI=mongodb://172.17.0.1:27017/duff -p 8080:8080 -it duff-web:latest
```

E é isso aí!!!!

## Desenvolvedor

Alexandre Arcanjo de Queiroz

## Licença

Apache 2
