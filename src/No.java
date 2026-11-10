public class No {
    private int chave;
    private Object dado;
    private No esquerda;
    private No direita;

    public No(int chave, Object dado) {
        this.chave = chave;
        this.dado = dado;
    }

    public int getChave() {
        return chave;
    }

    public Object getDado() {
        return dado;
    }

    public No getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(No esquerda) {
        this.esquerda = esquerda;
    }

    public No getDireita() {
        return direita;
    }

    public void setDireita(No direita) {
        this.direita = direita;
    }
}