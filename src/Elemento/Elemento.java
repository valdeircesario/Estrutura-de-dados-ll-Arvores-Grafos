package Elemento;

public class Elemento<TIPO> {

    private TIPO valor;
    private Elemento<TIPO> esquerda;
    private Elemento<TIPO> direita;

    public TIPO getValor() {
        return valor;
    }

    public Elemento<TIPO> getEsquerda() {
        return esquerda;
    }

    public void setEsquerda(Elemento<TIPO> esquerda) {
        this.esquerda = esquerda;
    }

    public Elemento<TIPO> getDireita() {
        return direita;
    }

    public void setDireita(Elemento<TIPO> direita) {
        this.direita = direita;
    }

    public void setValor(TIPO novoValor) {
        this.valor = novoValor;
    }

    public Elemento(TIPO valor) {
        this.valor = valor;
        this.esquerda = null;
        this.direita = null;
    }

}
