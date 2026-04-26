public class ArvoreAVLBalanceada {

    private No raiz;

    // ─────────────────────────────────────────
    //  Utilitários de altura e fator de balanc.
    // ─────────────────────────────────────────

    private int altura(No no) {
        return (no == null) ? 0 : no.altura;
    }

    private void atualizarAltura(No no) {
        if (no != null) {
            no.altura = 1 + Math.max(altura(no.esquerda), altura(no.direita));
        }
    }

    private int fatorBalanceamento(No no) {
        return (no == null) ? 0 : altura(no.esquerda) - altura(no.direita);
    }

    // ─────────────────────────────────────────
    //  Rotações
    // ─────────────────────────────────────────

    /** Rotação Simples à Direita (caso LL) */
    private No rotacaoDireita(No y) {
        No x   = y.esquerda;
        No T2  = x.direita;

        x.direita   = y;
        y.esquerda  = T2;

        atualizarAltura(y);
        atualizarAltura(x);

        System.out.println("  [AVL] Rotação SIMPLES À DIREITA no nó " + y.valor
                           + " → nova raiz local: " + x.valor);
        return x;
    }

    /** Rotação Simples à Esquerda (caso RR) */
    private No rotacaoEsquerda(No x) {
        No y   = x.direita;
        No T2  = y.esquerda;

        y.esquerda  = x;
        x.direita   = T2;

        atualizarAltura(x);
        atualizarAltura(y);

        System.out.println("  [AVL] Rotação SIMPLES À ESQUERDA no nó " + x.valor
                           + " → nova raiz local: " + y.valor);
        return y;
    }

    // ─────────────────────────────────────────
    //  Balanceamento geral de um nó
    // ─────────────────────────────────────────

    private No balancear(No no) {
        atualizarAltura(no);
        int fb = fatorBalanceamento(no);

        // Caso LL – rotação simples à direita
        if (fb > 1 && fatorBalanceamento(no.esquerda) >= 0) {
            System.out.println("  [AVL] Desbalanceamento LL detectado no nó " + no.valor
                               + " (FB=" + fb + ")");
            return rotacaoDireita(no);
        }

        // Caso LR – rotação dupla esquerda-direita
        if (fb > 1 && fatorBalanceamento(no.esquerda) < 0) {
            System.out.println("  [AVL] Desbalanceamento LR detectado no nó " + no.valor
                               + " (FB=" + fb + ")");
            no.esquerda = rotacaoEsquerda(no.esquerda);
            return rotacaoDireita(no);
        }

        // Caso RR – rotação simples à esquerda
        if (fb < -1 && fatorBalanceamento(no.direita) <= 0) {
            System.out.println("  [AVL] Desbalanceamento RR detectado no nó " + no.valor
                               + " (FB=" + fb + ")");
            return rotacaoEsquerda(no);
        }

        // Caso RL – rotação dupla direita-esquerda
        if (fb < -1 && fatorBalanceamento(no.direita) > 0) {
            System.out.println("  [AVL] Desbalanceamento RL detectado no nó " + no.valor
                               + " (FB=" + fb + ")");
            no.direita = rotacaoDireita(no.direita);
            return rotacaoEsquerda(no);
        }

        return no; // já está balanceado
    }

    // ─────────────────────────────────────────
    //  Inserção
    // ─────────────────────────────────────────

    public void inserir(int valor) {
        System.out.println("\n>>> Inserindo: " + valor);
        raiz = inserirRecursivo(raiz, valor);
        System.out.println("    Altura da árvore: " + altura(raiz)
                           + " | FB da raiz: " + fatorBalanceamento(raiz));
    }

    private No inserirRecursivo(No no, int valor) {
        if (no == null) {
            return new No(valor);
        }

        if (valor < no.valor) {
            no.esquerda = inserirRecursivo(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = inserirRecursivo(no.direita, valor);
        } else {
            System.out.println("  [AVISO] Valor " + valor
                               + " já existe. Duplicatas não são permitidas.");
            return no;
        }

        return balancear(no);
    }

    // ─────────────────────────────────────────
    //  Remoção
    // ─────────────────────────────────────────

    public void remover(int valor) {
        if (raiz == null) {
            System.out.println("\n>>> Árvore vazia. Nada a remover.");
            return;
        }
        System.out.println("\n>>> Removendo: " + valor);
        raiz = removerRecursivo(raiz, valor);
        if (raiz != null) {
            System.out.println("    Altura da árvore: " + altura(raiz)
                               + " | FB da raiz: " + fatorBalanceamento(raiz));
        } else {
            System.out.println("    Árvore ficou vazia após a remoção.");
        }
    }

    private No removerRecursivo(No no, int valor) {
        if (no == null) {
            System.out.println("  [AVISO] Valor " + valor + " não encontrado na árvore.");
            return null;
        }

        if (valor < no.valor) {
            no.esquerda = removerRecursivo(no.esquerda, valor);
        } else if (valor > no.valor) {
            no.direita = removerRecursivo(no.direita, valor);
        } else {
            // Nó encontrado
            if (no.esquerda == null || no.direita == null) {
                // Nó com 0 ou 1 filho
                No temp = (no.esquerda != null) ? no.esquerda : no.direita;
                if (temp == null) {
                    System.out.println("  Removendo folha: " + no.valor);
                    return null;
                } else {
                    System.out.println("  Removendo nó " + no.valor
                                       + " com 1 filho, substituindo por " + temp.valor);
                    no = temp;
                }
            } else {
                // Nó com 2 filhos → busca o sucessor in-order (menor da subárvore direita)
                No sucessor = menorNo(no.direita);
                System.out.println("  Nó " + no.valor
                                   + " tem 2 filhos. Substituindo pelo sucessor: "
                                   + sucessor.valor);
                no.valor = sucessor.valor;
                no.direita = removerRecursivo(no.direita, sucessor.valor);
            }
        }

        return balancear(no);
    }

    private No menorNo(No no) {
        No atual = no;
        while (atual.esquerda != null) {
            atual = atual.esquerda;
        }
        return atual;
    }

    // ─────────────────────────────────────────
    //  Busca
    // ─────────────────────────────────────────

    public boolean buscar(int valor) {
        return buscarRecursivo(raiz, valor);
    }

    private boolean buscarRecursivo(No no, int valor) {
        if (no == null) return false;
        if (valor == no.valor) return true;
        return (valor < no.valor)
               ? buscarRecursivo(no.esquerda, valor)
               : buscarRecursivo(no.direita, valor);
    }

    // ─────────────────────────────────────────
    //  Travessias
    // ─────────────────────────────────────────

    /** Em Ordem: esquerda → raiz → direita (resultado sempre ordenado) */
    public void listarEmOrdem() {
        System.out.print("\nEm Ordem    : ");
        emOrdem(raiz);
        System.out.println();
    }

    private void emOrdem(No no) {
        if (no != null) {
            emOrdem(no.esquerda);
            System.out.print(no.valor
                             + "(FB=" + fatorBalanceamento(no) + ") ");
            emOrdem(no.direita);
        }
    }

    /** Pré-Ordem: raiz → esquerda → direita */
    public void listarPreOrdem() {
        System.out.print("Pré-Ordem   : ");
        preOrdem(raiz);
        System.out.println();
    }

    private void preOrdem(No no) {
        if (no != null) {
            System.out.print(no.valor
                             + "(FB=" + fatorBalanceamento(no) + ") ");
            preOrdem(no.esquerda);
            preOrdem(no.direita);
        }
    }

    /** Pós-Ordem: esquerda → direita → raiz */
    public void listarPosOrdem() {
        System.out.print("Pós-Ordem   : ");
        posOrdem(raiz);
        System.out.println();
    }

    private void posOrdem(No no) {
        if (no != null) {
            posOrdem(no.esquerda);
            posOrdem(no.direita);
            System.out.print(no.valor
                             + "(FB=" + fatorBalanceamento(no) + ") ");
        }
    }

    // ─────────────────────────────────────────
    //  Impressão visual da árvore no console
    // ─────────────────────────────────────────

    public void imprimirArvore() {
        System.out.println("\n========== Estrutura da Árvore AVL ==========");
        if (raiz == null) {
            System.out.println("  (árvore vazia)");
        } else {
            imprimirRecursivo(raiz, "", true);
        }
        System.out.println("==============================================");
    }

    private void imprimirRecursivo(No no, String prefixo, boolean ehDireita) {
        if (no != null) {
            imprimirRecursivo(no.direita,
                              prefixo + (ehDireita ? "│   " : "    "), true);

            System.out.println(prefixo
                               + (ehDireita ? "└── " : "┌── ")
                               + no.valor
                               + " [h=" + no.altura
                               + "|FB=" + fatorBalanceamento(no) + "]");

            imprimirRecursivo(no.esquerda,
                              prefixo + (ehDireita ? "    " : "│   "), false);
        }
    }

    // ─────────────────────────────────────────
    //  Informações gerais
    // ─────────────────────────────────────────

    public boolean estaVazia() {
        return raiz == null;
    }

    public int alturaTotal() {
        return altura(raiz);
    }
}
