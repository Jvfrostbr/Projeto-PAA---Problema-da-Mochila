import model.Item;
import model.Knapsack;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // ---- EXEMPLO DE ITENS ----
        List<Item> items = new ArrayList<>();
        items.add(new Item(2, 10));
        items.add(new Item(3, 20));
        items.add(new Item(4, 40));
        items.add(new Item(5, 50));
        items.add(new Item(6, 60));
        items.add(new Item(7, 70));
        items.add(new Item(8, 85));
        items.add(new Item(9, 90));
        int knapsack_capacity = 20;

        Knapsack knapsack = new Knapsack(knapsack_capacity);

        // PROGRAMACAO DINAMICA
        System.out.println("\n====== Mochila (Programação Dinâmica) ======");
        knapsack.solveDP(items);
        System.out.println("Valor máximo: " + knapsack.getTotalValue());
        System.out.println("Itens escolhidos:");
        for (Item item : knapsack.getChosenItems()) {
            System.out.println(" - " + item);
        }

        // GULOSO
        System.out.println("\n====== Mochila (Guloso) ======");
        knapsack.solveGreedy(items);
        System.out.println("Valor obtido: " + knapsack.getTotalValue());
        System.out.println("Itens escolhidos:");
        for (Item item : knapsack.getChosenItems()) {
            System.out.println(" - " + item);
        }
    }
}
