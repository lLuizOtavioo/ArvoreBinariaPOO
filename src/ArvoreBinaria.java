public class ArvoreBinaria {
    private No raiz;
    private int quantidade;                   // total de nós na árvore
    private int[] ultimoCaminho = new int[0]; // ids visitados na última operação (busca ou inserção)
    private int totalVisitados;               // quantos nós foram visitados na última operação
    private Integer ultimoPai;                // pai do último nó inserido (null se não houve pai)
    private String ultimoLado = "";           // "raiz", "esquerdo" ou "direito" na última inserção

    public No getRaiz() {
        return raiz;
    }

    public int getQuantidade() {
        return quantidade;
    }

    // ------------------------------------------------------------------
    // Inserção e busca
    // ------------------------------------------------------------------

    // Insere um cliente usando o id como chave.
    // Regra: chave menor vai para a esquerda, chave maior vai para a direita.
    // Retorna false se o objeto não for Cliente ou se o id já existir.
    // Também registra os ids visitados, o pai e o lado onde o nó foi inserido.
    public boolean inserir(Object objeto) {
        if (!(objeto instanceof Cliente)) {
            return false;
        }
        int chave = ((Cliente) objeto).getId();
        No novo = new No(chave, objeto);
        iniciarRegistro();

        if (raiz == null) {
            raiz = novo;
            quantidade++;
            ultimoLado = "raiz";
            return true;
        }

        No atual = raiz;
        while (true) {
            registrarVisita(atual);
            if (chave == atual.getChave()) {
                return false; // id duplicado
            }
            if (chave < atual.getChave()) {
                if (atual.getEsquerda() == null) {
                    atual.setEsquerda(novo);
                    quantidade++;
                    ultimoPai = atual.getChave();
                    ultimoLado = "esquerdo";
                    return true;
                }
                atual = atual.getEsquerda();
            } else {
                if (atual.getDireita() == null) {
                    atual.setDireita(novo);
                    quantidade++;
                    ultimoPai = atual.getChave();
                    ultimoLado = "direito";
                    return true;
                }
                atual = atual.getDireita();
            }
        }
    }

    // Localiza o objeto pelo id. Retorna null se não encontrar.
    // Registra os ids visitados em um vetor, com um contador.
    public Object buscar(int id) {
        iniciarRegistro();

        No atual = raiz;
        while (atual != null) {
            registrarVisita(atual);
            if (id == atual.getChave()) {
                return atual.getDado();
            }
            atual = (id < atual.getChave()) ? atual.getEsquerda() : atual.getDireita();
        }
        return null;
    }

    // Zera o registro da operação anterior.
    // No máximo "quantidade" nós existentes podem ser visitados.
    private void iniciarRegistro() {
        ultimoCaminho = new int[quantidade];
        totalVisitados = 0;
        ultimoPai = null;
        ultimoLado = "";
    }

    private void registrarVisita(No no) {
        ultimoCaminho[totalVisitados] = no.getChave();
        totalVisitados++;
    }

    // ------------------------------------------------------------------
    // Dados da última operação
    // ------------------------------------------------------------------

    public int getTotalVisitados() {
        return totalVisitados;
    }

    // Retorna os ids visitados na última operação, na ordem, ex.: "8 -> 12 -> 10 -> 11"
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

    public Integer getUltimoPai() {
        return ultimoPai;
    }

    public String getUltimoLado() {
        return ultimoLado;
    }

    // ------------------------------------------------------------------
    // Consultas sobre a estrutura (não alteram o registro da última operação)
    // ------------------------------------------------------------------

    // Altura = maior profundidade (em arestas). Árvore vazia retorna -1.
    public int altura() {
        return alturaDe(raiz);
    }

    private int alturaDe(No no) {
        if (no == null) {
            return -1;
        }
        return 1 + Math.max(alturaDe(no.getEsquerda()), alturaDe(no.getDireita()));
    }

    // Profundidade = número de arestas entre a raiz e o nó. Retorna -1 se o id não existir.
    public int profundidade(int id) {
        No atual = raiz;
        int arestas = 0;
        while (atual != null) {
            if (id == atual.getChave()) {
                return arestas;
            }
            atual = (id < atual.getChave()) ? atual.getEsquerda() : atual.getDireita();
            arestas++;
        }
        return -1;
    }

    private No localizarNo(int id) {
        No atual = raiz;
        while (atual != null && id != atual.getChave()) {
            atual = (id < atual.getChave()) ? atual.getEsquerda() : atual.getDireita();
        }
        return atual;
    }

    // Id do pai do nó informado. Retorna null se o nó for a raiz ou não existir.
    public Integer getIdDoPai(int id) {
        No pai = null;
        No atual = raiz;
        while (atual != null && id != atual.getChave()) {
            pai = atual;
            atual = (id < atual.getChave()) ? atual.getEsquerda() : atual.getDireita();
        }
        if (atual == null || pai == null) {
            return null;
        }
        return pai.getChave();
    }

    public Integer getIdFilhoEsquerdo(int id) {
        No no = localizarNo(id);
        return (no == null || no.getEsquerda() == null) ? null : no.getEsquerda().getChave();
    }

    public Integer getIdFilhoDireito(int id) {
        No no = localizarNo(id);
        return (no == null || no.getDireita() == null) ? null : no.getDireita().getChave();
    }

    // Ids dos nós folha, em ordem crescente.
    public String listarFolhas() {
        return coletar(raiz, true);
    }

    // Ids dos nós que possuem dois filhos, em ordem crescente.
    public String listarComDoisFilhos() {
        return coletar(raiz, false);
    }

    // Percurso em ordem (esquerda, nó, direita) selecionando folhas ou nós com dois filhos.
    private String coletar(No no, boolean folhas) {
        if (no == null) {
            return "";
        }
        String esquerda = coletar(no.getEsquerda(), folhas);
        String direita = coletar(no.getDireita(), folhas);
        boolean selecionado = folhas ? no.ehFolha() : no.temDoisFilhos();
        String meio = selecionado ? String.valueOf(no.getChave()) : "";
        return juntar(juntar(esquerda, meio), direita);
    }

    private String juntar(String a, String b) {
        if (a.isEmpty()) {
            return b;
        }
        if (b.isEmpty()) {
            return a;
        }
        return a + ", " + b;
    }

    // Desenho da árvore "deitada": a raiz fica à esquerda, o ramo direito em cima
    // e o ramo esquerdo embaixo. Cada nível avança 4 espaços.
    public String desenhar() {
        return desenhar(raiz, 0);
    }

    private String desenhar(No no, int nivel) {
        if (no == null) {
            return "";
        }
        String recuo = "";
        for (int i = 0; i < nivel; i++) {
            recuo += "    ";
        }
        return desenhar(no.getDireita(), nivel + 1)
                + recuo + no.getChave() + "\n"
                + desenhar(no.getEsquerda(), nivel + 1);
    }
}