# Filosofos e Garfos - Simulação do Problema dos Filósofos

Essa prova é uma implementação do **Problema dos Filósofos**, uma simulação clássica em sistemas distribuídos que trata de como vários filósofos podem compartilhar recursos de forma segura sem causar deadlocks. O problema é resolvido utilizando um modelo de **servidor e cliente**, onde cada filósofo (cliente) interage com um servidor para obter garfos (recursos) e realizar o ciclo de pensar e comer.

## Requisitos

- **Java 8 ou superior** (usei o 21)

## Componentes Principais

1. **ServidorGarfo (Servidor de Garfos)**:
   - **Responsabilidade**: Gerenciar os garfos e os filósofos conectados.
   - Controla a quantidade de garfos disponíveis e a lógica de compartilhamento entre filósofos.
   - Armazena informações sobre os filósofos conectados, como número de refeições e tempo de espera.

2. **ManipuladorFilosofo**:
   - **Responsabilidade**: Representar cada filósofo individualmente.
   - Cada instância é responsável por conectar um novo filósofo ao servidor, gerenciar sua interação com os garfos (pensar e comer), e desconectar quando necessário.
   - A classe simula o ciclo de pensar e comer de cada filósofo, realizando as operações de espera e uso dos garfos compartilhados de forma segura.

3. **Garfo**:
   - **Responsabilidade**: Representa um garfo compartilhado entre dois filósofos.
   - Cada garfo pode ser "pegado" ou "largado" por filósofos, e é controlado para evitar condições de corrida.

4. **InfoFilosofo**:
   - **Responsabilidade**: Armazenar o estado atual de cada filósofo (número de refeições, se já pensou ou não, etc.).

5. **RegistroFilosofo**:
   - **Responsabilidade**: Armazenar um relatório das atividades realizadas por cada filósofo (quantas refeições ele fez, quantas vezes pensou, etc.).

## Como Executar

### 1. Iniciando o Servidor

Para este projeto, **não é necessário iniciar o servidor manualmente**. O servidor (`ServidorGarfo`) é **gerenciado automaticamente** pelo código do manipulador de filósofos. Ao iniciar um cliente, ele vai se conectar automaticamente ao servidor. Não há necessidade de configurar ou rodar o servidor separadamente.

### 2. Executando o Cliente (Filósofo)

- **Passo 1**: Compile os arquivos Java.

- **Passo 2**: Execute o código do servidor

- **Passo 2**: conecte no telnet usando a porta 12345
- comando: telnet localhost 12345

# Simulação de Filósofos - Leitura e Execução

## Passo 3: Conexão do Filósofo

O filósofo será conectado automaticamente ao servidor quando vc entrar no telnet caso haja espaco na mesa, caso nao ele te colocara em uma lista de espera, antes de se conectar ele perguntara se o usuario ja possui id caso nao ele criara um id novo e começará a simulação, podendo escolher entre as opções:


## 3. Comandos Disponíveis no Cliente

- **comer**: O filósofo pensa e depois realiza uma refeição  e repete isso 10 vezes(pega dois garfos, come e depois os larga).
- **sair**: O filósofo será desconectado do servidor.

## 4. Observando os Registros

O servidor armazena registros de cada filósofo, que podem ser acessados ao final da execução para visualizar informações como:

- Número de refeições feitas.
- Tempo gasto pensando.


## Explicação do Fluxo de Execução

### Conexão de um Filósofo:

- O cliente (filósofo) se conecta ao servidor. Se houver espaço disponível (a mesa não estiver cheia), ele é registrado e começa a interagir.
- Caso a mesa esteja cheia, o filósofo é colocado em uma fila de espera.

### Pensar e Comer:

- Cada filósofo alterna entre os estados de pensar e comer. Ao pensar, ele não usa garfos. Ao comer, ele precisa pegar dois garfos, um à esquerda e outro à direita.
- Os garfos são compartilhados entre filósofos de forma sincronizada para evitar conflitos e garantir que não haja deadlocks.

### Desconectar:

- Ao finalizar a execução, o filósofo se desconecta do servidor e seus registros são exibidos.

---

## Estrutura do Código

```plaintext
src/
├── ServidorGarfo.java
├── ManipuladorFilosofo.java
├── Garfo.java
├── InfoFilosofo.java
└── RegistroFilosofo.java
