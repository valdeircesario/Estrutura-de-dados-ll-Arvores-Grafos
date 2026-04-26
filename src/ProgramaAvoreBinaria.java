import java.util.Scanner;

/**
 * Programa Árvore Binária de Busca interativa
 * Baseada em estudos sobre a aula de estrutura de dados:
 * IFG - Câmpus Luziânia
 *
 * Funcionalidades:
 *  - Inserir valores (com propriedade: esq <= raiz < dir)
 *  - Remover valores (3 casos: folha, um filho, dois filhos com SUCESSOR)
 *  - Pesquisar / Localizar um valor
 *  - Listar em Pré-ordem, Em-ordem e Pós-ordem
 *  - Verificar árvore vazia
 *  - Mínimo e Máximo
 */
public class ProgramaAvoreBinaria {

    // =========================================================
    //  CLASSE NÓ
    // =========================================================
    static class No {
        int valor;
        No sae;   // sub-árvore esquerda
        No sad;   // sub-árvore direita

        No(int valor) {
            this.valor = valor;
            this.sae   = null;
            this.sad   = null;
        }
    }

    // =========================================================
    //  CLASSE ÁRVORE  –  encapsula a raiz e todos os métodos
    // =========================================================
    static class ArvoreBinaria {

        private No raiz;

        // ---- 1. arvoreVazia ----
        public boolean arvoreVazia() {
            return raiz == null;
        }

        // ---- 2. insere ----
        //  Propriedade BST:  esq <= pai  |  dir > pai
        public void insere(int valor) {
            raiz = inserirRec(raiz, valor);
        }

        private No inserirRec(No no, int valor) {
            if (no == null) return new No(valor);   // posição encontrada

            if (valor <= no.valor) {
                no.sae = inserirRec(no.sae, valor); // vai para esquerda
            } else {
                no.sad = inserirRec(no.sad, valor); // vai para direita
            }
            return no;
        }

        // ---- 3. pesquisar ----
        //  Retorna true se o valor existe na árvore
        public boolean pesquisar(int valor) {
            return pesquisarRec(raiz, valor);
        }

        private boolean pesquisarRec(No no, int valor) {
            if (no == null)         return false;   // não encontrou
            if (valor == no.valor)  return true;    // encontrou!
            if (valor < no.valor)   return pesquisarRec(no.sae, valor);
            return                         pesquisarRec(no.sad, valor);
        }

        // ---- 4. localizar  (retorna o nó ou null) ----
        public No localizar(int valor) {
            return localizarRec(raiz, valor);
        }

        private No localizarRec(No no, int valor) {
            if (no == null)        return null;
            if (valor == no.valor) return no;
            if (valor < no.valor)  return localizarRec(no.sae, valor);
            return                        localizarRec(no.sad, valor);
        }

        // ---- 5. remover ----
        //  Caso 1: nó é folha          → apaga diretamente
        //  Caso 2: nó tem um filho     → conecta pai ao filho
        //  Caso 3: nó tem dois filhos  → substitui pelo SUCESSOR em ordem
        //          (menor valor da sub-árvore direita)
        public boolean remover(int valor) {
            if (!pesquisar(valor)) return false;  // não existe
            raiz = removerRec(raiz, valor);
            return true;
        }

        private No removerRec(No no, int valor) {
            if (no == null) return null;

            if (valor < no.valor) {
                no.sae = removerRec(no.sae, valor);

            } else if (valor > no.valor) {
                no.sad = removerRec(no.sad, valor);

            } else {
                // --- nó encontrado ---

                // CASO 1: nó folha (sem filhos)
                if (no.sae == null && no.sad == null) {
                    System.out.println("   → Caso 1: nó folha removido diretamente.");
                    return null;
                }

                // CASO 2a: só tem filho à direita
                if (no.sae == null) {
                    System.out.println("   → Caso 2: nó com apenas filho à direita. Filho sobe.");
                    return no.sad;
                }

                // CASO 2b: só tem filho à esquerda
                if (no.sad == null) {
                    System.out.println("   → Caso 2: nó com apenas filho à esquerda. Filho sobe.");
                    return no.sae;
                }

                // CASO 3: dois filhos → localiza o SUCESSOR em ordem
                //          (o menor nó da sub-árvore DIREITA)
                No sucessor = minimoNo(no.sad);
                System.out.println("   → Caso 3: dois filhos. Sucessor em ordem = " + sucessor.valor);
                no.valor = sucessor.valor;                     // copia o valor do sucessor
                no.sad   = removerRec(no.sad, sucessor.valor); // remove o sucessor da sub-árvore
            }
            return no;
        }

        // ---- auxiliar: nó com menor valor (mais à esquerda) ----
        private No minimoNo(No no) {
            while (no.sae != null) no = no.sae;
            return no;
        }

        // ---- 6. mínimo e máximo ----
        public int minimo() {
            if (arvoreVazia()) throw new RuntimeException("Árvore vazia!");
            return minimoNo(raiz).valor;
        }

        public int maximo() {
            if (arvoreVazia()) throw new RuntimeException("Árvore vazia!");
            No cur = raiz;
            while (cur.sad != null) cur = cur.sad;
            return cur.valor;
        }

        // ---- 7. altura ----
        public int altura() {
            return alturaRec(raiz);
        }

        private int alturaRec(No no) {
            if (no == null) return -1;
            return 1 + Math.max(alturaRec(no.sae), alturaRec(no.sad));
        }

        // ---- 8. total de nós ----
        public int totalNos() {
            return totalRec(raiz);
        }

        private int totalRec(No no) {
            if (no == null) return 0;
            return 1 + totalRec(no.sae) + totalRec(no.sad);
        }

        // ---- 9. Travessias ----

        // PRÉ-ORDEM: visita → esquerda → direita
        public void listarPreOrdem() {
            if (arvoreVazia()) { System.out.println("Árvore vazia!"); return; }
            System.out.print("Pré-ordem  : ");
            preOrdemRec(raiz);
            System.out.println();
        }

        private void preOrdemRec(No no) {
            if (no == null) return;
            System.out.print(no.valor + " ");
            preOrdemRec(no.sae);
            preOrdemRec(no.sad);
        }

        // EM-ORDEM: esquerda → visita → direita  (ordem ascendente)
        public void listarInOrdem() {
            if (arvoreVazia()) { System.out.println("Árvore vazia!"); return; }
            System.out.print("Em-ordem   : ");
            inOrdemRec(raiz);
            System.out.println();
        }

        private void inOrdemRec(No no) {
            if (no == null) return;
            inOrdemRec(no.sae);
            System.out.print(no.valor + " ");
            inOrdemRec(no.sad);
        }

        // PÓS-ORDEM: esquerda → direita → visita
        public void listarPosOrdem() {
            if (arvoreVazia()) { System.out.println("Árvore vazia!"); return; }
            System.out.print("Pós-ordem  : ");
            posOrdemRec(raiz);
            System.out.println();
        }

        private void posOrdemRec(No no) {
            if (no == null) return;
            posOrdemRec(no.sae);
            posOrdemRec(no.sad);
            System.out.print(no.valor + " ");
        }

        // ---- 10. Imprimir todas as travessias de uma vez ----
        public void listarTodas() {
            listarPreOrdem();
            listarInOrdem();
            listarPosOrdem();
        }

        // ---- 11. Verificar se é estritamente binária ----
        //  Todo nó tem 0 ou 2 filhos (nunca exatamente 1)
        public boolean ehEstritamenteBinaria() {
            return estritaRec(raiz);
        }

        private boolean estritaRec(No no) {
            if (no == null) return true;
            boolean temEsq = (no.sae != null);
            boolean temDir = (no.sad != null);
            if (temEsq != temDir) return false;          // exatamente 1 filho → viola
            return estritaRec(no.sae) && estritaRec(no.sad);
        }

        // ---- 12. Resumo da árvore ----
        public void exibirResumo() {
            if (arvoreVazia()) {
                System.out.println("  Árvore está VAZIA.");
                return;
            }
            System.out.println("  Total de nós : " + totalNos());
            System.out.println("  Altura        : " + altura());
            System.out.println("  Mínimo        : " + minimo());
            System.out.println("  Máximo        : " + maximo());
            System.out.println("  Estritamente binária? " + ehEstritamenteBinaria());
        }
    }

    // =========================================================
    //  UTILITÁRIOS DE INTERFACE
    // =========================================================
    static void linha()  { System.out.println("-------------------------------------------"); }
    static void linhaDupla() { System.out.println("==========================================="); }

    static void exibirMenu() {
        linhaDupla();
        System.out.println("  ÁRVORE BINÁRIA DE BUSCAS");
        linhaDupla();
        System.out.println("  [1] Inserir número");
        System.out.println("  [2] Remover número");
        System.out.println("  [3] Pesquisar / Localizar número");
        System.out.println("  [4] Listar em Pré-ordem");
        System.out.println("  [5] Listar em Em-ordem (crescente)");
        System.out.println("  [6] Listar em Pós-ordem");
        System.out.println("  [7] Listar nas 3 ordens");
        System.out.println("  [8] Mínimo e Máximo");
        System.out.println("  [9] Resumo da árvore");
        System.out.println("  [0] Sair");
        linhaDupla();
        System.out.print("  Escolha uma opção: ");
    }

    static int lerInteiro(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Valor inválido. Digite um número inteiro.");
            }
        }
    }

    // =========================================================
    //  MAIN – Loop do programa
    // =========================================================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArvoreBinaria arvore = new ArvoreBinaria();

        // ---- Boas-vindas e carga inicial ----
        linhaDupla();
        System.out.println("  Bem-vindo ao programa de Árvore Binária!");
        System.out.println("  Aluno: Valdeir cesario da silva: - IFG LUZIANIA -GO");
        linhaDupla();
        System.out.println("  Vamos construir sua árvore do ZERO ?");
        System.out.println("  Digite 0 (Zero), e ENTER para ir ao menu inicial");
        System.out.println("  Para finalizar o programa, digite 0 (zero). Sair ");
        linha();

        while (true) {
            int v = lerInteiro(sc, "  Valor (0 para terminar inserção): ");
            if (v == 0) break;
            arvore.insere(v);
            System.out.println("  ✓ " + v + " inserido na árvore.");
        }

        if (!arvore.arvoreVazia()) {
            System.out.println();
            System.out.println("  Árvore construída! Visão geral:");
            linha();
            arvore.exibirResumo();
            linha();
            arvore.listarTodas();
        } else {
            System.out.println("  Nenhum valor inserido. Árvore vazia.");
        }

        // ---- Loop principal do menu ----
        boolean rodando = true;
        while (rodando) {
            System.out.println();
            exibirMenu();
            int opcao;
            try {
                opcao = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            System.out.println();

            switch (opcao) {

                // ---- INSERIR ----
                case 1: {
                    int val = lerInteiro(sc, "  Número a inserir: ");
                    arvore.insere(val);
                    System.out.println("  ✓ " + val + " inserido com sucesso.");
                    System.out.println("  Em-ordem atual:");
                    arvore.listarInOrdem();
                    break;
                }

                // ---- REMOVER ----
                case 2: {
                    if (arvore.arvoreVazia()) {
                        System.out.println("  [!] Árvore está vazia. Nada para remover.");
                        break;
                    }
                    int val = lerInteiro(sc, "  Número a remover: ");
                    System.out.println("  Removendo " + val + "...");
                    boolean removido = arvore.remover(val);
                    if (removido) {
                        System.out.println("  ✓ " + val + " removido com sucesso.");
                        if (!arvore.arvoreVazia()) {
                            System.out.println("  Em-ordem atual:");
                            arvore.listarInOrdem();
                        } else {
                            System.out.println("  A árvore ficou VAZIA após a remoção.");
                        }
                    } else {
                        System.out.println("  [!] Valor " + val + " não encontrado na árvore.");
                    }
                    break;
                }

                // ---- PESQUISAR ----
                case 3: {
                    if (arvore.arvoreVazia()) {
                        System.out.println("  [!] Árvore está vazia.");
                        break;
                    }
                    int val = lerInteiro(sc, "  Número a pesquisar: ");
                    boolean achou = arvore.pesquisar(val);
                    if (achou) {
                        System.out.println("  ✓ Valor " + val + " ENCONTRADO na árvore.");
                    } else {
                        System.out.println("  ✗ Valor " + val + " NÃO encontrado na árvore.");
                    }
                    break;
                }

                // ---- PRÉ-ORDEM ----
                case 4: {
                    arvore.listarPreOrdem();
                    break;
                }

                // ---- EM-ORDEM ----
                case 5: {
                    arvore.listarInOrdem();
                    break;
                }

                // ---- PÓS-ORDEM ----
                case 6: {
                    arvore.listarPosOrdem();
                    break;
                }

                // ---- TODAS AS ORDENS ----
                case 7: {
                    arvore.listarTodas();
                    break;
                }

                // ---- MÍNIMO E MÁXIMO ----
                case 8: {
                    if (arvore.arvoreVazia()) {
                        System.out.println("  [!] Árvore está vazia.");
                    } else {
                        System.out.println("  Mínimo : " + arvore.minimo());
                        System.out.println("  Máximo : " + arvore.maximo());
                    }
                    break;
                }

                // ---- RESUMO ----
                case 9: {
                    arvore.exibirResumo();
                    break;
                }

                // ---- SAIR ----
                case 0: {
                    System.out.println(" Até logo!");
                    rodando = false;
                    break;
                }

                default: {
                    System.out.println("  [!] Opção inválida. Digite um número de 0 a 9.");
                }
            }
        }

        sc.close();
    }
}