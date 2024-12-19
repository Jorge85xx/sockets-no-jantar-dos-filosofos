
# Protocolo de Comunicação entre Filósofos (Cliente) e Servidor

Este documento descreve o protocolo de comunicação entre filósofos (clientes) e o servidor em um sistema simulado de filósofos que comem e pensam.

## 1. Início de Conexão e Autenticação

O cliente (filósofo) deve primeiro enviar uma mensagem de autenticação para o servidor. O servidor então verifica se o filósofo já foi registrado ou se é a primeira vez que ele está se conectando.

### Cliente (filósofo):
- Envia: `Já esteve logado? (sim/nao)`
  
### Resposta do Servidor:
- Se o filósofo não estiver logado:
  - Responde: `Não, seu id é 2`
- Se o filósofo já estiver logado:
  - Responde: `Sim, você já está logado. Seu ID é [ID].`

## 2. Recebendo Comandos

Depois que o filósofo foi autenticado, ele pode enviar comandos, como "comer" ou "sair". O servidor processa o comando enviado.

### Cliente (filósofo):
- Envia: `comer` ou `sair`

### Resposta do Servidor:
- Responde com o status atual da ação solicitada.

## 3. Execução de Ações

Quando o comando enviado for "comer", o servidor simula o comportamento do filósofo ao comer e pensar. O tempo de espera é em milissegundos, e o filósofo pode pedir os garfos e realizar as ações necessárias.

### Servidor:
- Responde com o status de "pensando", "comendo", e "esperando garfos", com o respectivo tempo em milissegundos.
  
Exemplo:
- "Pensando 0ms"
- "Comendo"
- "Comer concluído"
- "Pensando 6ms"
- "Esperando garfos"
- "Pensando 3ms"
- "Comendo"
- "Comer concluído"
- "Pensando 5ms"

## 4. Limitação de Refeições

O servidor mantém o controle da quantidade de refeições realizadas pelo filósofo. Caso o número de refeições atinja um limite, o filósofo será informado de que não poderá mais comer.

### Servidor:
- Quando o número de refeições atingir o limite:
  - Responde: "Você já fez 10 refeições. Não poderá mais comer aqui."

## 5. Finalização de Sessão

Se o filósofo optar por sair, o cliente envia o comando "sair", e o servidor confirma o término da sessão.

### Cliente (filósofo):
- Envia: `sair`

