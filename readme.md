<h1 align="center">
    <img alt="Star Wars Ms" src="https://github.com/brunograna/star-wars-ms/blob/master/star-wars-logo.png" width="300px" />
</h1>

<h3 align="center">
  Projeto: Star Wars Ms
</h3>

<p align="center">

  <img alt="License: MIT" src="https://img.shields.io/badge/license-MIT-%2304D361">
  <img alt="Language: Java" src="https://img.shields.io/badge/language-java-green">
  <img alt="Database: Mongodb" src="https://img.shields.io/badge/database-mongodb-green">
  <img alt="Version: 1.0" src="https://img.shields.io/badge/version-1.0-yellowgreen">
  
</p>

## :rocket: Features do Projeto

* Listar planetas com paginação e a possibilidade de filtro por nome
* Criar um planeta
* Buscar um planeta por **id**
* Deletar um planeta por **id**
    
:mag: Baixe o projeto e teste você mesmo.

## :dart: Objetivos do desenvolvimento

- Utilizar o Java 11 com Spring Boot 2.4.0-M2 e JUnit 5
- Desenvolver uma *api rest* utilizando a Arquitetura Hexagonal (*Ports and Adapters Architecture*)
- Consumir uma [api externa](https://swapi.dev/about) para resgatar a quantidade de aparições em filmes de um planeta

## :file_folder: Resources

**Base Url**

```
${HOST_URL}/star-wars/v1
```

## /planets

**Endpoint**

```
${HOST_URL}/star-wars/v1/planets
```

**Json Schema Definition:**

```
type: object
properties:
  name:
    type: string
    description: nome do planeta
  ground:
    type: string
    description: descrição do solo do planeta
  climate:
    type: string
    description: descrição do clima do planeta
  filmAppearances:
    type: number
    description: quantidade de aparições deste planeta em um filme
```

---

Desenvolvido por Bruno Garcia :wave: [Conheça mais sobre o meu trabalho no Linkedin](https://www.linkedin.com/in/dev-brunogarcia/)