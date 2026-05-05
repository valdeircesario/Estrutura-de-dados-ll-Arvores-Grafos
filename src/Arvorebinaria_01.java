import Elemento.Elemento;

public class Arvorebinaria_01 <TIPO extends Comparable>{

    private Elemento<TIPO> raiz;

    public Arvorebinaria_01(){
        this.raiz = null;
    }
    public void adicionar(TIPO valor){
        Elemento<TIPO> novoElemento = new Elemento<TIPO>(valor);
        if ( raiz == null){
            this.raiz = novoElemento;
        }else {
            Elemento<TIPO> atual = this.raiz;
            while (true){
                if (novoElemento.getValor().compareTo(atual.getValor()) == -1){
                   if (atual.getEsquerda() != null){
                       atual = atual.getEsquerda();
                   }else {
                       atual.setEsquerda(novoElemento);
                       break;
                   }
                }else {
                    if (atual.getDireita() != null){
                        atual = atual.getDireita();
                    }else {
                        atual.setDireita(novoElemento);
                        break;
                    }

                }
            }
        }
    }
    public Elemento<TIPO> getRaiz(){
        return raiz;
    }
    public void emOrdem(Elemento<TIPO> atual){
        if (atual != null){
            emOrdem(atual.getEsquerda());
            System.out.print(atual.getValor() + " ");
            emOrdem(atual.getDireita());

        }


    }
    public void preOrdem(Elemento<TIPO> atual){
        if (atual != null){
            System.out.print(atual.getValor() + " ");
            preOrdem(atual.getEsquerda());
            preOrdem(atual.getDireita());

        }


    }
    public void posOrdem(Elemento<TIPO> atual){
        if (atual != null){
            posOrdem(atual.getEsquerda());
            posOrdem(atual.getDireita());
            System.out.print(atual.getValor() + " ");

        }


    }
    public void remover(TIPO valor) {
        this.raiz = remover(this.raiz, valor);
    }

    private Elemento<TIPO> remover(Elemento<TIPO> atual, TIPO valor) {
        if (atual == null) {
            return null;
        }

        if (valor.compareTo(atual.getValor()) == -1) {
            atual.setEsquerda(remover(atual.getEsquerda(), valor));
        } else if (valor.compareTo(atual.getValor()) == 1) {
            atual.setDireita(remover(atual.getDireita(), valor));
        } else {
            // 🔸 Nó encontrado

            // Caso 1: sem filhos
            if (atual.getEsquerda() == null && atual.getDireita() == null) {
                return null;
            }

            // Caso 2: um filho
            if (atual.getEsquerda() == null) {
                return atual.getDireita();
            } else if (atual.getDireita() == null) {
                return atual.getEsquerda();
            }

            // Caso 3: dois filhos
            Elemento<TIPO> sucessor = menorValor(atual.getDireita());
            atual.setValor(sucessor.getValor());
            atual.setDireita(remover(atual.getDireita(), sucessor.getValor()));
        }

        return atual;
    }

    // 🔹 encontra o menor valor da subárvore
    private Elemento<TIPO> menorValor(Elemento<TIPO> elemento) {
        Elemento<TIPO> atual = elemento;
        while (atual.getEsquerda() != null) {
            atual = atual.getEsquerda();
        }
        return atual;
    }


}
