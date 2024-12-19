import java.io.*;
import java.net.*;
import java.util.*;

public class ManipuladorFilosofo implements Runnable {
    private final ServidorGarfo servidor;  // Instância do servidor
    private final Socket socket;
    private BufferedReader entrada;
    private PrintWriter saida;
    private int idFilosofo;

    public ManipuladorFilosofo(ServidorGarfo servidor, Socket socket) {
        this.servidor = servidor;  // Recebe a instância do servidor
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            saida = new PrintWriter(socket.getOutputStream(), true);

            // Pergunta se o filósofo já esteve logado anteriormente
            saida.println("Ja esteve logado? (sim/nao)");
            String resposta = entrada.readLine().trim().toLowerCase();
            if (resposta.equals("sim")) {
                saida.println("Informe seu id:");
                try {
                    int id = Integer.parseInt(entrada.readLine());
                    // Verifica se o ID já existe
                    if (servidor.getMapaFilosofos().containsKey(id)) {
                        idFilosofo = id;
                        saida.println("Bem-vindo de volta, filosofo " + idFilosofo);
                    } else {
                        saida.println("ID nao encontrado. Criando novo ID...");
                        criarNovoFilosofo();
                    }
                } catch (NumberFormatException e) {
                    saida.println("ID invalido. Criando novo ID...");
                    criarNovoFilosofo();
                }
            } else {
                criarNovoFilosofo();
            }

            saida.println("Comandos: sair, comer");

            while (true) {
                String comando = entrada.readLine().trim().toLowerCase();
                if (comando.equals("sair")) {
                    saida.println("Desconectando...");
                    break;
                } else if (comando.equals("comer")) {
                    iniciarCicloPensarComer();
                } else {
                    saida.println("Comando invalido. Comandos disponiveis: sair, comer.");
                }
            }

            desconectar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void criarNovoFilosofo() {
        idFilosofo = servidor.contadorFilosofos++;
        servidor.getMapaFilosofos().put(idFilosofo, new InfoFilosofo(idFilosofo));
        servidor.getMapaRegistros().put(idFilosofo, new RegistroFilosofo(idFilosofo));
        saida.println("Seu id e " + idFilosofo);
        System.out.println("Novo filosofo conectado: " + idFilosofo);
    }

    private void iniciarCicloPensarComer() {
        boolean iniciou = false;

        while (true) {
            try {
                if (!iniciou) {
                    pensar();
                    comer();
                    iniciou = true;
                } else {
                    Thread.sleep(1000); 
                    pensar();
                    comer();
                }

                InfoFilosofo info = servidor.getMapaFilosofos().get(idFilosofo);
                if (info.getRefeicoes() >= 10) {
                    saida.println("Voce ja fez 10 refeicoes. nao podera mais comer aqui.");
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void pensar() {
        InfoFilosofo info = servidor.getMapaFilosofos().get(idFilosofo);

        if (info.getPensarSemComer() >= 6) {
            saida.println("Voce pensou 5 vezes sem comer e morreu.");
            desconectar();
            return;
        }

        int tempoPensar = Math.max(0, (int) (new Random().nextGaussian() * 3 + 5));
        saida.println("Pensando " + tempoPensar + "ms");
        try {
            Thread.sleep(tempoPensar);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        info.setPensou(true);
        info.incrementarPensarSemComer();
    }

    private void comer() {
        InfoFilosofo info = servidor.getMapaFilosofos().get(idFilosofo);

        if (info.getRefeicoes() >= 10) {
            saida.println("Voce ja fez 10 refeicoes, aguarde o comando comer novamente.");
            return;
        }

        if (!info.jaPensou()) {
            pensar();
        }

        Garfo garfoEsquerdo = servidor.getGarfos()[idFilosofo % servidor.getGarfos().length];
        Garfo garfoDireito = servidor.getGarfos()[(idFilosofo + 1) % servidor.getGarfos().length];

        synchronized (servidor.getGarfos()) {
            if (!garfoEsquerdo.estaEmUso() && !garfoDireito.estaEmUso()) {
                garfoEsquerdo.pegar();
                garfoDireito.pegar();
                saida.println("Comendo");
                System.out.println("Filosofo " + idFilosofo + " comendo.");
            } else {
                saida.println("Esperando garfos");
                return;
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (servidor.getGarfos()) {
            garfoEsquerdo.largar();
            garfoDireito.largar();
        }

        info.incrementarRefeicoes();
        info.resetarPensarSemComer();
        info.setPensou(false);
        saida.println("Comer concluido");
    }

    private void desconectar() {
        System.out.println("Filosofo " + idFilosofo + " desconectado.");

        RegistroFilosofo registro = servidor.getMapaRegistros().get(idFilosofo);
        if (registro != null) {
            System.out.println("Relatorio do Filosofo " + idFilosofo + ": " + registro);
        }

        synchronized (servidor.getListaEspera()) {
            if (!servidor.getListaEspera().isEmpty()) {
                Socket proximoFilosofoSocket = servidor.getListaEspera().poll();
                new ManipuladorFilosofo(servidor, proximoFilosofoSocket).run();
            }
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
