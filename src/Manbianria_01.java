public class Manbianria_01 {

    public static void main(String[] args) {
        Arvorebinaria_01<Integer> arvore = new Arvorebinaria_01<Integer>();
        System.out.println("Inserindo: 10");
        arvore.adicionar(10);
        System.out.println("Inserindo: 8");
        arvore.adicionar(8);
        System.out.println("Inserindo: 5");
        arvore.adicionar(5);
        System.out.println("Inserindo: 9");
        arvore.adicionar(9);
        System.out.println("Inserindo: 7");
        arvore.adicionar(7);
        System.out.println("Inserindo: 18");
        arvore.adicionar(18);
        System.out.println("Inserindo: 13");
        arvore.adicionar(13);
        System.out.println("Inserindo: 20");
        arvore.adicionar(20);
        System.out.println("---------------------");
        System.out.println("Em Ordem:");
        arvore.emOrdem(arvore.getRaiz());
        System.out.println(" ");
        System.out.println("---------------------");
        System.out.println("Pre Ordem:");
        arvore.preOrdem(arvore.getRaiz());
        System.out.println(" ");
        System.out.println("---------------------");
        System.out.println("Pos Ordem:");
        arvore.posOrdem(arvore.getRaiz());
        System.out.println(" ");
        System.out.println("---------------------");
        System.out.println("Apos remoção do 9 e 18:");
        arvore.remover(9);
        arvore.remover(18);
        arvore.emOrdem(arvore.getRaiz());
    }
}