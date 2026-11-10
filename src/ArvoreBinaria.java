public class ArvoreBinaria {
    private No raiz;
    private int quantidade;          // total de nos na arvore
    private int[] ultimoCaminho = new int[0];
    private int totalVisitados;      // quantos nos foram visitados na ultima busca

    public No getRaiz() {
        return raiz;
    }

    // Insere um cliente usando o id como chave.
    // Retorna false se o objeto nao for Cliente ou se o id ja existir.
    public boolean inserir(Object objeto) {
        if (!(objeto instanceof Cliente)) {
            return false;
        }
        int chave = ((Cliente) objeto).getId();
        No novo = new No(chave, objeto);

        if (raiz == null) {
            raiz = novo;
            quantidade++;
            return true;
        }

        No atual = raiz;
        while (true) {
            if (chave == atual.getChave()) {
                return false; // id duplicado
            }
            if (chave < atual.getChave()) {
                if (atual.getEsquerda() == null) {
                    atual.setEsquerda(novo);
                    quantidade++;
                    return true;
                }
                atual = atual.getEsquerda();
            } else {
                if (atual.getDireita() == null) {
                    atual.setDireita(novo);
                    quantidade++;
                    return true;
                }
                atual = atual.getDireita();
            }
        }
    }

    // Localiza o objeto pelo id. Retorna null se nao encontrar.
    // Registra os ids visitados em um vetor, com um contador.
    public Object buscar(int id) {
        ultimoCaminho = new int[quantidade]; // no maximo "quantidade" nos podem ser visitados
        totalVisitados = 0;

        No atual = raiz;
        while (atual != null) {
            ultimoCaminho[totalVisitados] = atual.getChave();
            totalVisitados++;
            if (id == atual.getChave()) {
                return atual.getDado();
            }
            atual = (id < atual.getChave()) ? atual.getEsquerda() : atual.getDireita();
        }
        return null;
    }

    public int getTotalVisitados() {
        return totalVisitados;
    }

    // Retorna os ids visitados na ultima busca, na ordem, ex.: "8 -> 12 -> 10 -> 11"
    public String getCaminhoComoTexto() {
        String texto = "";
        for (int i = 0; i < totalVisitados; i++) {
            if (i > 0) {
                texto += " -> ";
            }
            texto += ultimoCaminho[i];
        }
        return texto;
    }
}