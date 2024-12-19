public class RegistroFilosofo {
  private final int id;
  private int totalPensar;
  private int totalRefeicoes;

  public RegistroFilosofo(int id) {
      this.id = id;
      this.totalPensar = 0;
      this.totalRefeicoes = 0;
  }

  public void incrementarPensar() {
      totalPensar++;
  }

  public void incrementarRefeicoes() {
      totalRefeicoes++;
  }

  @Override
  public String toString() {
      return "Filosofo " + id + " - Pensou: " + totalPensar + " vezes, Comeu: " + totalRefeicoes + " vezes";
  }
}
