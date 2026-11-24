import java.util.Scanner;

public class Main {

    // Busca um cliente e imprime no formato pedido na atividade.
    static void buscarEImprimir(ArvoreBinaria arvore, int id) {
        Object resultado = arvore.buscar(id);
        if (resultado != null) {
            System.out.println("Cliente encontrado:");
            System.out.println(resultado);
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    // Insere um cliente e imprime tudo o que as questões pedem sobre a inserção.
    static void inserirEImprimir(ArvoreBinaria arvore, Cliente cliente) {
        boolean inserido = arvore.inserir(cliente);
        System.out.println("Inserir ID " + cliente.getId() + " -> retorno: " + inserido);
        System.out.println("IDs visitados: " + arvore.getCaminhoComoTexto());
        if (inserido) {
            System.out.println("Pai do novo nó: " + arvore.getUltimoPai()
                    + " (filho " + arvore.getUltimoLado() + ")");
        }
        System.out.println("Quantidade de clientes: " + arvore.getQuantidade()
                + " | Altura: " + arvore.altura());
    }

    // Monta uma árvore inserindo os clientes na ordem em que aparecem no vetor.
    static ArvoreBinaria construir(Cliente[] clientes) {
        ArvoreBinaria arvore = new ArvoreBinaria();
        for (Cliente c : clientes) {
            arvore.inserir(c);
        }
        return arvore;
    }

    // Devolve uma cópia do vetor ordenada pelo id (insertion sort).
    static Cliente[] ordenarPorId(Cliente[] clientes, boolean crescente) {
        Cliente[] copia = clientes.clone();
        for (int i = 1; i < copia.length; i++) {
            Cliente atual = copia[i];
            int j = i - 1;
            while (j >= 0 && (crescente
                    ? copia[j].getId() > atual.getId()
                    : copia[j].getId() < atual.getId())) {
                copia[j + 1] = copia[j];
                j--;
            }
            copia[j + 1] = atual;
        }
        return copia;
    }

    static void titulo(String texto) {
        System.out.println("\n=== " + texto + " ===");
    }

    // Imprime os dados usados nas respostas das questões da atividade.
    static void relatorio(Cliente[] clientes, ArvoreBinaria original) {
        ArvoreBinaria crescente = construir(ordenarPorId(clientes, true));
        ArvoreBinaria decrescente = construir(ordenarPorId(clientes, false));

        titulo("Questão 1 - Árvore original");
        System.out.print(original.desenhar());
        System.out.println("Nós folha: " + original.listarFolhas());
        System.out.println("Nós com dois filhos: " + original.listarComDoisFilhos());
        System.out.println("Pai do nó 11: " + original.getIdDoPai(11));
        System.out.println("Filhos do nó 12: esquerdo = " + original.getIdFilhoEsquerdo(12)
                + ", direito = " + original.getIdFilhoDireito(12));

        titulo("Questão 2 - Altura e profundidade");
        System.out.println("Altura (ordem original): " + original.altura());
        int[] ids = {8, 12, 11};
        for (int id : ids) {
            System.out.println("Profundidade do nó " + id + ": " + original.profundidade(id));
        }
        System.out.println("Altura (ordem crescente): " + crescente.altura());

        titulo("Questão 3 - Posição do ID 9");
        original.buscar(9);
        System.out.println("Caminho até o 9: " + original.getCaminhoComoTexto());
        Integer pai = original.getIdDoPai(9);
        String lado = Integer.valueOf(9).equals(original.getIdFilhoEsquerdo(pai)) ? "esquerdo" : "direito";
        System.out.println("O 9 é filho " + lado + " do nó " + pai);

        titulo("Questão 4 - Inserção de ID duplicado (6)");
        inserirEImprimir(original, new Cliente(6, "Outro cliente", "outro@example.com"));
        System.out.println("Busca do ID 6 após a tentativa:");
        buscarEImprimir(original, 6);

        titulo("Questão 5 - Duas cópias independentes da árvore original");
        ArvoreBinaria copia1 = construir(clientes);
        ArvoreBinaria copia2 = construir(clientes);
        inserirEImprimir(copia1, new Cliente(0, "Novo zero", "zero@example.com"));
        System.out.println();
        inserirEImprimir(copia2, new Cliente(16, "Novo dezesseis", "dezesseis@example.com"));

        titulo("Questão 6 - Tabela de buscas (árvore original)");
        String formato = "%-13s %-24s %-9s %s%n";
        System.out.printf(formato, "ID procurado", "IDs visitados", "Qtd nós", "Encontrado?");
        int[] procurados = {8, 7, 11, 15, 0, 20};
        for (int id : procurados) {
            Object resultado = original.buscar(id);
            System.out.printf(formato, id, original.getCaminhoComoTexto(),
                    original.getTotalVisitados(), resultado != null ? "Sim" : "Não");
        }

        titulo("Questão 7 - Original x crescente (nós visitados)");
        int[] comparados = {1, 8, 15};
        for (int id : comparados) {
            original.buscar(id);
            crescente.buscar(id);
            System.out.println("ID " + id + ": original = " + original.getTotalVisitados()
                    + " | crescente = " + crescente.getTotalVisitados());
        }

        titulo("Questão 8 - Ordem decrescente");
        System.out.print(decrescente.desenhar());
        Cliente raiz = (Cliente) decrescente.getRaiz().getDado();
        System.out.println("Raiz: ID " + raiz.getId() + " (" + raiz.getNome() + ")");
        System.out.println("Altura: " + decrescente.altura());
        int[] extremos = {1, 15};
        for (int id : extremos) {
            decrescente.buscar(id);
            crescente.buscar(id);
            System.out.println("ID " + id + ": decrescente = " + decrescente.getTotalVisitados()
                    + " | crescente = " + crescente.getTotalVisitados());
        }
    }

    public static void main(String[] args) {
        Cliente[] clientes = {
                new Cliente(8, "Ana", "ana@example.com"),
                new Cliente(4, "Bruno", "bruno@example.com"),
                new Cliente(12, "Carla", "carla@example.com"),
                new Cliente(2, "Diego", "diego@example.com"),
                new Cliente(6, "Elisa", "elisa@example.com"),
                new Cliente(10, "Felipe", "felipe@example.com"),
                new Cliente(14, "Gabriela", "gabriela@example.com"),
                new Cliente(1, "Hugo", "hugo@example.com"),
                new Cliente(3, "Isabela", "isabela@example.com"),
                new Cliente(5, "Joao", "joao@example.com"),
                new Cliente(7, "Karen", "karen@example.com"),
                new Cliente(9, "Lucas", "lucas@example.com"),
                new Cliente(11, "Mariana", "mariana@example.com"),
                new Cliente(13, "Nicolas", "nicolas@example.com"),
                new Cliente(15, "Olivia", "olivia@example.com")
        };

        ArvoreBinaria arvore = construir(clientes);

        // Busca interativa (exemplos de execução do PDF)
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o ID do cliente: ");
        int id = sc.nextInt();
        buscarEImprimir(arvore, id);

        // Dados para responder as questões
        System.out.print("\nExibir o relatório das questões? (s/n): ");
        if (sc.next().equalsIgnoreCase("s")) {
            relatorio(clientes, arvore);
        }
        sc.close();
    }
}