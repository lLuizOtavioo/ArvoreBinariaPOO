import java.util.Scanner;

public class Main {

    static void buscarEImprimir(ArvoreBinaria arvore, int id) {
        Object resultado = arvore.buscar(id);
        if (resultado != null) {
            System.out.println("Cliente encontrado:");
            System.out.println(resultado);
        } else {
            System.out.println("Cliente nao encontrado.");
        }
        System.out.println("IDs visitados: " + arvore.getCaminhoComoTexto());
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

        ArvoreBinaria arvore = new ArvoreBinaria();
        for (Cliente c : clientes) {
            arvore.inserir(c);
        }

        // Testes exigidos na entrega
        System.out.println("=== Teste: busca pelo ID 8 ===");
        buscarEImprimir(arvore, 8);
        System.out.println("\n=== Teste: busca pelo ID 11 ===");
        buscarEImprimir(arvore, 11);
        System.out.println("\n=== Teste: busca pelo ID 20 ===");
        buscarEImprimir(arvore, 20);

        System.out.println("\n=== Teste: insercao de ID ja cadastrado (8) ===");
        boolean inserido = arvore.inserir(new Cliente(8, "Duplicado", "dup@example.com"));
        System.out.println("Retorno da insercao: " + inserido);

        // Arvore em ordem crescente (questao 5)
        ArvoreBinaria crescente = new ArvoreBinaria();
        for (int id = 1; id <= 15; id++) {
            for (Cliente c : clientes) {
                if (c.getId() == id) {
                    crescente.inserir(c);
                }
            }
        }
        System.out.println("\n=== Busca pelo ID 15 nas duas arvores ===");
        arvore.buscar(15);
        System.out.println("Arvore original : " + arvore.getTotalVisitados() + " nos (" + arvore.getCaminhoComoTexto() + ")");
        crescente.buscar(15);
        System.out.println("Arvore crescente: " + crescente.getTotalVisitados() + " nos (" + crescente.getCaminhoComoTexto() + ")");

        // Busca interativa
        System.out.println();
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite o ID do cliente: ");
        int id = sc.nextInt();
        buscarEImprimir(arvore, id);
        sc.close();
    }
}