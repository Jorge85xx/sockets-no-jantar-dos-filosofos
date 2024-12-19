public class InfoFilosofo {
  private final int id;
  private int refeicoes;
  private int pensarSemComer;
  private boolean pensou;

  public InfoFilosofo(int id) {
      this.id = id;
      this.refeicoes = 0;
      this.pensarSemComer = 0;
      this.pensou = false;
  }

  public void incrementarRefeicoes() {
      refeicoes++;
  }

  public void incrementarPensarSemComer() {
      pensarSemComer++;
  }

  public void resetarPensarSemComer() {
      pensarSemComer = 0;
  }

  public int getPensarSemComer() {
      return pensarSemComer;
  }

  public void setPensou(boolean pensou) {
      this.pensou = pensou;
  }

  public boolean jaPensou() {
      return pensou;
  }

  public int getRefeicoes() {
      return refeicoes;
  }
}
