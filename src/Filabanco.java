import java.util.Scanner;
public class Filabanco {

    private String[] fila;
    private int inicio;
    private int fim;
    private int tamanho;
    public Filabanco(int capacidade) {
        fila = new String[capacidade];
        inicio = 0;
        fim = -1;
        tamanho = 0;
    }
    // enqueue (entrar na fila)
    public void enqueue(String nome) {
        if (tamanho < fila.length) {
            fim = (fim + 1) % fila.length;
            fila[fim] = nome;
            tamanho++;
            System.out.println(nome + " entrou na fila.");
        } else {
            System.out.println("Fila cheia!");
        }
    }
    // dequeue (atender/remover)
    public String dequeue() {
        if (tamanho > 0) {
            String atendido = fila[inicio];
            inicio = (inicio + 1) % fila.length;
            tamanho--;
            return atendido;
        }
        return null;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Filabanco fila = new Filabanco(5);
        int opcao;
        do {
            System.out.println("\n1 - Entrar na fila");
            System.out.println("2 - Atender próximo");
            System.out.println("3 - Sair");
            System.out.print("Escolha: ");
            opcao = sc.nextInt();
            sc.nextLine();
            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    fila.enqueue(nome);
                    break;
                case 2:
                    String atendido = fila.dequeue();
                    if (atendido != null) {
                        System.out.println(atendido + " foi atendido.");
                    } else {
                        System.out.println("Fila vazia!");
                    }
                    break;
            }
        } while (opcao != 3);
        sc.close();
    }
}