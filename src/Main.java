import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ArvoreAVLBalanceada arvore = new ArvoreAVLBalanceada();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║        ÁRVORE AVL BALANCEADA             ║");
        System.out.println("║  Instituto Federal Goiás – Luziânia      ║");
        System.out.println("╚══════════════════════════════════════════╝");

        do {
            System.out.println("\n─────────────── MENU ───────────────");
            System.out.println("  1. Inserir valor");
            System.out.println("  2. Remover valor");
            System.out.println("  3. Buscar valor");
            System.out.println("  4. Listar Em Ordem");
            System.out.println("  5. Listar Pré-Ordem");
            System.out.println("  6. Listar Pós-Ordem");
            System.out.println("  7. Exibir estrutura da árvore");
            System.out.println("  8. Todas as listagens");
            System.out.println("  0. Sair");
            System.out.println("────────────────────────────────────");
            System.out.print("Escolha uma opção: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Entrada inválida. Digite um número: ");
                scanner.next();
            }
            opcao = scanner.nextInt();

            switch (opcao) {

                case 1:
                    System.out.print("Digite o valor a inserir: ");
                    if (scanner.hasNextInt()) {
                        int v = scanner.nextInt();
                        arvore.inserir(v);
                        arvore.imprimirArvore();
                    } else {
                        System.out.println("[ERRO] Valor inválido.");
                        scanner.next();
                    }
                    break;

                case 2:
                    if (arvore.estaVazia()) {
                        System.out.println("[AVISO] A árvore está vazia.");
                        break;
                    }
                    System.out.print("Digite o valor a remover: ");
                    if (scanner.hasNextInt()) {
                        int v = scanner.nextInt();
                        arvore.remover(v);
                        arvore.imprimirArvore();
                    } else {
                        System.out.println("[ERRO] Valor inválido.");
                        scanner.next();
                    }
                    break;

                case 3:
                    if (arvore.estaVazia()) {
                        System.out.println("[AVISO] A árvore está vazia.");
                        break;
                    }
                    System.out.print("Digite o valor a buscar: ");
                    if (scanner.hasNextInt()) {
                        int v = scanner.nextInt();
                        boolean encontrado = arvore.buscar(v);
                        System.out.println(encontrado
                            ? "  ✔ Valor " + v + " ENCONTRADO na árvore."
                            : "  ✘ Valor " + v + " NÃO encontrado na árvore.");
                    } else {
                        System.out.println("[ERRO] Valor inválido.");
                        scanner.next();
                    }
                    break;

                case 4:
                    if (arvore.estaVazia()) {
                        System.out.println("[AVISO] A árvore está vazia.");
                    } else {
                        arvore.listarEmOrdem();
                    }
                    break;

                case 5:
                    if (arvore.estaVazia()) {
                        System.out.println("[AVISO] A árvore está vazia.");
                    } else {
                        arvore.listarPreOrdem();
                    }
                    break;

                case 6:
                    if (arvore.estaVazia()) {
                        System.out.println("[AVISO] A árvore está vazia.");
                    } else {
                        arvore.listarPosOrdem();
                    }
                    break;

                case 7:
                    arvore.imprimirArvore();
                    break;

                case 8:
                    if (arvore.estaVazia()) {
                        System.out.println("[AVISO] A árvore está vazia.");
                    } else {
                        arvore.imprimirArvore();
                        arvore.listarEmOrdem();
                        arvore.listarPreOrdem();
                        arvore.listarPosOrdem();
                        System.out.println("Altura total da árvore: "
                                           + arvore.alturaTotal());
                    }
                    break;

                case 0:
                    System.out.println("\nEncerrando o programa. Até logo!");
                    break;

                default:
                    System.out.println("[ERRO] Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);

        scanner.close();
    }
}
