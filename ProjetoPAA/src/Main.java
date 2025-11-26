import model.Item;
import model.Knapsack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        menu();
    }

    private static void menu() {

        // Exemplo de itens
        List<Item> items = new ArrayList<>();
        items.add(new Item("Livro", 2, 10));
        items.add(new Item("Lanterna", 3, 20));
        items.add(new Item("Rádio", 4, 40));
        items.add(new Item("Corda", 5, 50));
        items.add(new Item("Barraca", 6, 60));
        items.add(new Item("Kit Médico", 7, 70));
        items.add(new Item("Celular", 8, 85));
        items.add(new Item("Notebook", 9, 90));

        int knapsack_capacity = 10;
        Knapsack knapsack = new Knapsack(knapsack_capacity);

        int option = -1;

        menu(option, knapsack, knapsack_capacity, items);
    }

    private static void printTable(List<Item> items, int totalValue) {
        System.out.println("\nItem                Peso   Valor   Densidade");
        System.out.println("------------------------------------------------");

        for (Item item : items) {
            System.out.printf(
                    "%-18s %5d %7d %10.2f%n",
                    item.getNome(),
                    item.getPeso(),
                    item.getValor(),
                    item.getDensidade()
            );
        }

        System.out.println("\nValor total: " + totalValue);
    }

    private static void menu(int option, Knapsack knapsack,int knapsack_capacity, List<Item> items){
        Scanner scanner = new Scanner(System.in);

        while (option != 0) {
            System.out.println("\n====== MENU MOCHILA ======");
            System.out.println("Capacidade da mochila: " + knapsack_capacity);
            System.out.println("1 - Resolver usando Programação Dinâmica");
            System.out.println("2 - Resolver usando Algoritmo Guloso");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            option = scanner.nextInt();

            switch (option) {
                case 1:
                    submenuDP(knapsack, items);
                    break;

                case 2:
                    System.out.println("\n====== Mochila (Guloso) ======");
                    knapsack.solveGreedy(items);
                    printTable(knapsack.getChosenItems(), knapsack.getTotalValue());
                    break;

                case 0:
                    System.out.println("\nEncerrando...");
                    break;

                default:
                    System.out.println("\nOpção inválida! Tente novamente.");
            }
        }
    }

    private static void submenuDP(Knapsack knapsack, List<Item> items) {
        Scanner scanner = new Scanner(System.in);
        int option;

        System.out.println("\n====== Programação Dinâmica ======");
        System.out.println("1 - Resolver e mostrar matriz DP");
        System.out.println("2 - Resolver sem mostrar matriz");
        System.out.println("0 - Voltar");
        System.out.print("Escolha: ");
        option = scanner.nextInt();

        switch (option) {
            case 1:
                System.out.println("\n====== Mochila (Programação Dinâmica) ======");
                knapsack.solveDP(items, true);
                printTable(knapsack.getChosenItems(), knapsack.getTotalValue());
                break;

            case 2:
                System.out.println("\n====== Mochila (Programação Dinâmica) ======");
                knapsack.solveDP(items, false);
                printTable(knapsack.getChosenItems(), knapsack.getTotalValue());
                break;

            case 0:
                return;

            default:
                System.out.println("\nOpção inválida!");
        }
    }

}
