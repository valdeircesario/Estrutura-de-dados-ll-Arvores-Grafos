import java.util.Scanner;

public class PilhaPalindromo {
    private char[] pilha;
    private int topo;

    public PilhaPalindromo(int tamanho) {
        pilha = new char[tamanho];
        topo = -1;
    }
    public void push(char c) {
        if (topo < pilha.length - 1) {
            pilha[++topo] = c;
        }
    }
    public char pop() {
        if (topo >= 0) {
            return pilha[topo--];
        }
        return '\0';
    }
    public char peek() {
        if (topo >= 0) {
            return pilha[topo];
        }
        return '\0';
    }
    public static boolean ehPalindromo(String str) {
        str = str.replaceAll("\\s+", "").toLowerCase();
        PilhaPalindromo p = new PilhaPalindromo(str.length());
        for (int i = 0; i < str.length(); i++) {
            p.push(str.charAt(i));
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != p.pop()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Digite uma palavra ou frase: ");
        String entrada = sc.nextLine();

        if (ehPalindromo(entrada)) {
            System.out.println("É palíndromo");
        } else {
            System.out.println("Não é palíndromo");
        }
        sc.close();
    }
}