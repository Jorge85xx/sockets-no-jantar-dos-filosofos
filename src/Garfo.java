public class Garfo {
  private final String nome;
  private boolean emUso;

  public Garfo(String nome) {
      this.nome = nome;
      this.emUso = false;
  }

  public synchronized void pegar() {
      this.emUso = true;
  }

  public synchronized void largar() {
      this.emUso = false;
  }

  public synchronized boolean estaEmUso() {
      return emUso;
  }
}
