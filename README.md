# E-commerce

## Descrição
Este é o backend de um e-commerce, responsável por gerenciar a criação, edição, visualização e exclusão de usuários, produtos e categorias.

## Versão
1.0

## Funcionalidades
- **Cadastro de Produtos**: Permite adicionar, editar e remover produtos, com opção de upload de imagens e descrição detalhada.
- **Gerenciamento de Categorias**: Criação e organização de produtos por categorias.
- **Autenticação de Usuários**: Registro e login de usuários, com opção de recuperação de senha.
- **Dashboard Administrativo**: Interface para gerenciar produtos, categorias e usuários.

## Tecnologias Utilizadas
- **Backend**: Java, Spring Boot
- **Segurança**: JWT, Spring Security
- **Banco de Dados**: MySql
- **Controle de Versão**: Git e GitHub

## Pré-requisitos
Antes de iniciar o projeto, certifique-se de ter as seguintes ferramentas instaladas:
- **JDK 21**
- **Maven**
- **MySql** ou outra instância de banco de dados configurada

## Instalação
1. Clone o repositório:
    ```bash
    git clone git@github.com:GuiCavalcanteDev/ecommerce.git
    ```
2. Navegue até o diretório do projeto e instale as dependências do backend:
    ```bash
    cd backend
    mvn install
    ```
3. Configure o banco de dados no arquivo `application.properties` ou em variáveis de ambiente:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
    spring.datasource.username=seu-usuario
    spring.datasource.password=sua-senha
    ```
4. Inicie o servidor backend:
    ```bash
    cd backend
    mvn spring-boot:run
    ```

## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests para melhorar o projeto.

## Contato
Para mais informações, entre em contato:
- **Nome**: Guilherme Cavalcante
- **Email**: guicavalcante59@gmail.com
