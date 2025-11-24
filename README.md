Trade Viewer
============

O **Trade Viewer** é uma aplicação web projetada para centralizar informações do mercado financeiro de forma clara, simples e acessível. O sistema reúne dados de ações, commodities e câmbio em um dashboard intuitivo e de fácil navegação, permitindo análises rápidas por parte de investidores iniciantes e estudantes.

A plataforma utiliza dados em tempo real através da API **Brapi.dev**, exibindo gráficos, listagens e informações essenciais para consulta imediata.

Funcionalidades
------------------

### Autenticação e Usuários

*   Cadastro de novos usuários.
    
*   Login com validação de credenciais.
    
*   Armazenamento de dados seguros em banco H2.
    

### Dashboard Geral

*   Visão consolidada de:
    
    *   Ações
        
    *   Commodities
        
    *   Câmbio
        
*   Interface moderna em **tema escuro**.
    
*   Cards com dados essenciais.
    
*   Acesso rápido ao detalhamento dos ativos.
    

### Detalhamento de Ativos

*   Página individual para cada ativo selecionado.
    
*   Gráfico histórico de preços.
    
*   Indicadores fundamentais básicos.
    
*   Dados atualizados via API externa.
    

### Commodities

*   Listagem completa das commodities disponíveis.
    
*   Filtro para facilitar consultas.
    

### Integração com API Externa

*   Consumo da API **Brapi.dev** para:
        
    *   Históricos de preços.
        
    *   Metadados dos ativos.
      
*   Consumo da API **Awesome API** para:
  
    *   Cotações de Moedas em tempo real
        

Público-alvo
---------------

O Trade Viewer é destinado a:

*   Estudantes de economia e finanças.
    
*   Investidores iniciantes.
    
*   Usuários que desejam visualizar dados do mercado de forma rápida, direta e sem complexidade.
    

Tecnologias Utilizadas
--------------------------

### Back-end
  *   **Linguagem:** Java 21
      
  *   **Framework:** Spring Boot 3.5.7
      
  *   **APIs Externas:**
      
      *   [Brapi.dev](https://brapi.dev/) 
          
      *   [AwesomeAPI](https://docs.awesomeapi.com.br/) 
    

### Banco de Dados

*   **H2 Database** (em modo arquivo)
    

### Front-end

*   **Thymeleaf (Template Engine), HTML, CSS e JavaScript**
    

### Arquitetura

*   **MVC** (Model View Controller)
    
*   Comunicação direta entre back-end, API externa e banco de dados.

Como Executar o Projeto
--------------------------

### Pré-requisitos

*   Java 21
    
*   Maven
    
*   Navegador web atualizado
    
*   Conexão com internet (obrigatória para consulta à API Brapi.dev)
    

### Passo a passo

1.  git clone \[https://github.com/GDHAF/Projeto\_Analise-Ativos.git\](https://github.com/GDHAF/Projeto\_Analise-Ativos.git)
    
2.  cd Projeto\_Analise-Ativos
    
3.  mvn spring-boot:run
    
4.  **Acesse no navegador:**Abra o seu navegador preferido e vá para:[http://localhost:8080/home/index](https://www.google.com/search?q=http://localhost:8080/home/index)
    

### Primeiros Passos no Sistema

1.  Na tela inicial, clique no botão **Login**.
    
2.  Selecione a opção para **Cadastrar um novo usuário** (o login só funciona após o cadastro prévio).
    
3.  Preencha os dados e finalize o cadastro.
    
4.  Retorne à tela de login e insira suas credenciais.
    
5.  Você será redirecionado ao dashboard principal.
