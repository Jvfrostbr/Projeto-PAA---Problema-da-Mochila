package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Knapsack {
    private int totalValue;
    private int capacity;
    private List<Item> chosenItems;

    // Construtor
    public Knapsack(int capacity) {
        this.chosenItems = new ArrayList<>();
        this.capacity = capacity;
    }

    // Métodos
    public void solveDP(List<Item> items) {
        int n = items.size();
        int[][] dp = new int[n + 1][capacity + 1];

        // Preenche a matriz DP
        for (int i = 1; i <= n; i++) {
            Item item = items.get(i - 1);

            for (int w = 1; w <= capacity; w++) {
                if (item.getPeso() <= w) {
                    dp[i][w] = Math.max(
                            item.getValor() + dp[i - 1][w - item.getPeso()],
                            dp[i - 1][w]
                    );
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Valor máximo
        this.totalValue = dp[n][capacity];

        // Backtracking
        chosenItems.clear();
        int w = capacity;
        int valueTracker = totalValue;

        for (int i = n; i > 0 && valueTracker > 0; i--) {
            if (valueTracker != dp[i - 1][w]) {
                Item item = items.get(i - 1);
                chosenItems.add(item);
                valueTracker -= item.getValor();
                w -= item.getPeso();
            }
        }
    }

    public void solveGreedy(List<Item> items) {
        chosenItems.clear();
        totalValue = 0;

        // Ordena por densidade
        List<Item> sorted = new ArrayList<>(items);
        sorted.sort(Comparator.comparingDouble(Item::getDensidade).reversed());

        int currentWeight = 0;
        for (Item item : sorted) {
            if (currentWeight + item.getPeso() <= capacity) {
                chosenItems.add(item);
                currentWeight += item.getPeso();
                totalValue += item.getValor();
            }
        }
    }

    // Gets
    public int getTotalValue() {
        return totalValue;
    }

    public List<Item> getChosenItems() {
        return chosenItems;
    }
}
