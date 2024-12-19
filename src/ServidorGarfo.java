import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ServidorGarfo {
    private static final int PORTA = 12345;
    private final ExecutorService executor;
    private final Garfo[] garfos;
    private final Map<Integer, InfoFilosofo> mapaFilosofos;
    private final Map<Integer, RegistroFilosofo> mapaRegistros;
    private final Queue<Socket> listaEspera;
    private final int espacoMesa;
    public int contadorFilosofos = 0;

    public ServidorGarfo(int quantidadeGarfos) {
        garfos = new Garfo[quantidadeGarfos];
        for (int i = 0; i < quantidadeGarfos; i++) {
            garfos[i] = new Garfo("Garfo " + (i + 1));
        }
        executor = Executors.newCachedThreadPool();
        mapaFilosofos = new ConcurrentHashMap<>();
        mapaRegistros = new ConcurrentHashMap<>();
        listaEspera = new LinkedList<>();
        espacoMesa = quantidadeGarfos - 1;
    }

    public void iniciar() {
        try (ServerSocket servidor = new ServerSocket(PORTA)) {
            System.out.println("Servidor rodando na porta " + PORTA + "...");
            while (true) {
                Socket socket = servidor.accept();
                synchronized (listaEspera) {
                    if (mapaFilosofos.size() >= espacoMesa) {
                        System.out.println("Mesa cheia. Adicionando novo filosofo na lista de espera.");
                        listaEspera.add(socket);
                    } else {
                        executor.submit(new ManipuladorFilosofo(this, socket)); // Passando a instância do servidor
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Exibe os registros de todos os filósofos
    public void exibirRegistros() {
        System.out.println("Relatórios de todos os filósofos:");
        for (RegistroFilosofo registro : mapaRegistros.values()) {
            System.out.println(registro);
        }
    }

    public static void main(String[] args) {
        ServidorGarfo servidor = new ServidorGarfo(5);  // Criando servidor com 5 garfos
        servidor.iniciar();
    }

    // Métodos getters para acesso dos garfos e mapas
    public Garfo[] getGarfos() {
        return garfos;
    }

    public Map<Integer, InfoFilosofo> getMapaFilosofos() {
        return mapaFilosofos;
    }

    public Map<Integer, RegistroFilosofo> getMapaRegistros() {
        return mapaRegistros;
    }

    public Queue<Socket> getListaEspera() {
        return listaEspera;
    }
}
