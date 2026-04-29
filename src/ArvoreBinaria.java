
public class ArvoreBinaria {


    public static class No {

        private No sae;          // sub-árvore esquerda
        private No sad;          // sub-árvore direita
        private Integer valor;   // chave / dado do nó


        public No(int valor) {
            this.valor = valor;
            this.sae   = null;
            this.sad   = null;
        }

        // ---------- Getters e Setters ----------
        public No getSae()            { return sae; }
        public void setSae(No sae)    { this.sae = sae; }

        public No getSad()            { return sad; }
        public void setSad(No sad)    { this.sad = sad; }

        public Integer getValor()          { return valor; }
        public void setValor(Integer valor){ this.valor = valor; }

        // =========================================================
        //  1. arvoreVazia()
        //     Retorna true se este nó NÃO possui valor (raiz nula).
        //     Chamado sobre a raiz da árvore.
        // =========================================================
        public boolean arvoreVazia() {
            return this.valor == null;
        }

        // =========================================================
        //  2. insere(int valor)
        //     Propriedade de Árvore Binária de Busca:
        //       - y.chave <= x.chave  → vai para a esquerda
        //       - y.chave >  x.chave  → vai para a direita
        //     (slide "Inserindo um Nó na Árvore")
        // =========================================================
        public void insere(int novoValor) {
            if (novoValor <= this.valor) {
                // Deve ir para a sub-árvore esquerda
                if (this.sae == null) {
                    this.sae = new No(novoValor);
                } else {
                    this.sae.insere(novoValor);
                }
            } else {
                // Deve ir para a sub-árvore direita
                if (this.sad == null) {
                    this.sad = new No(novoValor);
                } else {
                    this.sad.insere(novoValor);
                }
            }
        }

        // =========================================================
        //  3. pesquisar(int valor)
        //     Localiza um nó comparando a chave a partir da raiz.
        //     (slide "Localizando um Nó na Árvore")
        // =========================================================
        public boolean pesquisar(int chave) {
            if (chave == this.valor) {
                return true;                          // encontrou
            } else if (chave < this.valor) {
                if (this.sae == null) return false;   // não existe
                return this.sae.pesquisar(chave);     // busca à esquerda
            } else {
                if (this.sad == null) return false;   // não existe
                return this.sad.pesquisar(chave);     // busca à direita
            }
        }

        // =========================================================
        //  4. listarPreOrdem()
        //     Ordem: VISITA → esquerda → direita
        //     (slide "Travessia em Pré-Ordem")
        //     Resultado para a árvore do exercício:
        //       41 27 07 02 11 38 33 81 67 62 84 82 92
        // =========================================================
        public void listarPreOrdem() {
            System.out.print(this.valor + " ");   // visita o nó
            if (this.sae != null) this.sae.listarPreOrdem();
            if (this.sad != null) this.sad.listarPreOrdem();
        }

        // =========================================================
        //  5. listarInOrdem()
        //     Ordem: esquerda → VISITA → direita
        //     (slide "Travessia em Ordem")
        //     Visita todos os nós em ordem ASCENDENTE.
        //     Resultado para a árvore do exercício:
        //       02 07 11 27 33 38 41 62 67 81 82 84 92
        // =========================================================
        public void listarInOrdem() {
            if (this.sae != null) this.sae.listarInOrdem();
            System.out.print(this.valor + " ");   // visita o nó
            if (this.sad != null) this.sad.listarInOrdem();
        }

        // =========================================================
        //  6. listarPosOrdem()
        //     Ordem: esquerda → direita → VISITA
        //     (slide "Travessia em Pós-Ordem")
        //     Resultado para a árvore do exercício:
        //       02 11 07 33 38 27 62 67 82 92 84 81 41
        // =========================================================
        public void listarPosOrdem() {
            if (this.sae != null) this.sae.listarPosOrdem();
            if (this.sad != null) this.sad.listarPosOrdem();
            System.out.print(this.valor + " ");   // visita o nó
        }

        // =========================================================
        //  EXTRA: altura()
        //     Comprimento do caminho mais longo da raiz até uma folha.
        //     Árvore vazia → -1 | Nó folha → 0
        //     (slide "Árvores Binárias" – propriedade de altura)
        // =========================================================
        public int altura() {
            int altEsq = (this.sae == null) ? -1 : this.sae.altura();
            int altDir = (this.sad == null) ? -1 : this.sad.altura();
            return 1 + Math.max(altEsq, altDir);
        }

        // =========================================================
        //  EXTRA: ehEstritamenteBinaria()
        //     Verifica se a árvore é ESTRITAMENTE BINÁRIA:
        //     cada nó tem ZERO ou DOIS filhos.
        //     Nós internos sempre têm dois filhos.
        //     (slide "Árvore Estritamente Binária")
        // =========================================================
        public boolean ehEstritamenteBinaria() {
            boolean temEsq = (this.sae != null);
            boolean temDir = (this.sad != null);

            // Nó com exatamente um filho → viola a regra
            if (temEsq != temDir) return false;

            // Verifica recursivamente os filhos (se existirem)
            if (temEsq && !this.sae.ehEstritamenteBinaria()) return false;
            if (temDir && !this.sad.ehEstritamenteBinaria()) return false;

            return true;
        }

        // =========================================================
        //  EXTRA: contarNos()
        //     Conta o total de nós na árvore.
        //     Útil para verificar a fórmula N = 2^(h-1) das ABCs.
        // =========================================================
        public int contarNos() {
            int total = 1;
            if (this.sae != null) total += this.sae.contarNos();
            if (this.sad != null) total += this.sad.contarNos();
            return total;
        }

        // =========================================================
        //  EXTRA: minimo()
        //     Percorre sempre à esquerda até não haver mais filhos.
        //     (slide "Encontrar Valores Máximo e Mínimo")
        // =========================================================
        public int minimo() {
            if (this.sae == null) return this.valor;
            return this.sae.minimo();
        }

        // =========================================================
        //  EXTRA: maximo()
        //     Percorre sempre à direita até não haver mais filhos.
        //     (slide "Encontrar Valores Máximo e Mínimo")
        // =========================================================
        public int maximo() {
            if (this.sad == null) return this.valor;
            return this.sad.maximo();
        }

    } // fim class No



    public static void main(String[] args) {

        System.out.println("===========================================");
        System.out.println("  ÁRVORE BINÁRIA – IFG Câmpus Luziânia");
        System.out.println("  Aluno : Valdeir cesario da silva");
        System.out.println("===========================================\n");

        // --- Construção da árvore do exercício ---
        No raiz = new No(52);
        raiz.insere(41);
        raiz.insere(30);
        raiz.insere(16);
        raiz.insere(9);
        raiz.insere(45);
        raiz.insere(34);
        raiz.insere(20);
        raiz.insere(62);
        raiz.insere(57);
        raiz.insere(40);
        raiz.insere(60);
        raiz.insere(61);

        // --- Informações gerais ---
        System.out.println("Total de nós : " + raiz.contarNos());
        System.out.println("Altura       : " + raiz.altura());
        System.out.println("Estritamente binária? " + raiz.ehEstritamenteBinaria());
        System.out.println("Mínimo : " + raiz.minimo());
        System.out.println("Máximo : " + raiz.maximo());
        System.out.println();

        // --- Travessias ---
        System.out.print("Pré-ordem  : ");
        raiz.listarPreOrdem();
        System.out.println();

        System.out.print("Em ordem   : ");
        raiz.listarInOrdem();
        System.out.println();

        System.out.print("Pós-ordem  : ");
        raiz.listarPosOrdem();
        System.out.println();

        System.out.println();

        // --- Pesquisa ---
        int[] buscas = {16, 57, 60, 40};
        System.out.println("--- Pesquisas ---");
        for (int v : buscas) {
            System.out.println("Pesquisar(" + v + ") → " +
                    (raiz.pesquisar(v) ? "ENCONTRADO" : "NÃO encontrado"));
        }

        System.out.println();

        // --- Verificação: árvore vazia ---
        System.out.println("Árvore vazia? " + raiz.arvoreVazia());

        System.out.println();

        // --- Demonstração: árvore estritamente binária ---
        System.out.println("--- Árvore Estritamente Binária (nós com 0 ou 2 filhos) ---");
        No estrita = new No(16);
        estrita.insere(5);
        estrita.insere(57);
        estrita.insere(60);
        estrita.insere(45);

        System.out.print("  Pré-ordem da árvore estrita: ");
        estrita.listarPreOrdem();
        System.out.println();
        System.out.println("  É estritamente binária? " + estrita.ehEstritamenteBinaria());

        // Árvore COM apenas um filho (viola a regra)
        No naoEstrita = new No(10);
        naoEstrita.insere(5);   // só filho esquerdo em 10 → viola
        System.out.println("  Árvore com nó de um filho é estritamente binária? "
                + naoEstrita.ehEstritamenteBinaria());

        System.out.println("\n===========================================");
        System.out.println("  FIM DA DEMONSTRAÇÃO");
        System.out.println("===========================================");
    }
}