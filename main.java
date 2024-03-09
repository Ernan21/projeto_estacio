import java.util.Scanner;  // Import the Scanner class

public class Main {
  public static void main(String[] args) {
    /* Função para coletar o nome do usuario */
    Scanner obj_username = new Scanner(System.in);
    System.out.println("Ola, seja bem vindo \n qual o seu nome?");
    String username = obj_username.nextLine();
    
    /* Fazendo codigo para calcular a nota do aluno em sistema */
    System.out.println("Qual foi sua nota no 1 bimestre?");
    float nota1 = obj_username.nextFloat();
    System.out.println("Qual foi sua nota no 2 bimestre?");
    float nota2 = obj_username.nextFloat();
    System.out.println("Qual foi sua nota no 3 bimestre?");
    float nota3 = obj_username.nextFloat();
    System.out.println("Qual foi sua nota no 4 bimestre?");
    float nota4 = obj_username.nextFloat();

    float media = (nota1 + nota2 + nota3 + nota4)/4;

    System.out.println("Ola " + username + " sua media é " + media);
  }
}