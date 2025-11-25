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
    public void solveDP(List<Item> items, boolean mostrarMatriz) {
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

        // Se o usuário pediu pra mostrar a matriz DP, imprime ela também
        if (mostrarMatriz) {
            imprimirMatrizDP(dp, items);
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

    private void imprimirMatrizDP(int[][] dp, List<Item> items) {

        System.out.println("\n============== MATRIZ DP ==============\n");

        int colWidth = 5;  // largura fixa das colunas

        // Cabeçalho
        System.out.printf("%-10s", "");
        for (int w = 0; w <= capacity; w++) {
            System.out.printf("%" + colWidth + "d", w);
        }
        System.out.println();

        // Linha superior
        System.out.print("-----------");
        for (int w = 0; w <= capacity; w++) {
            System.out.print("-----");
        }
        System.out.println();

        // Linhas da matriz
        for (int i = 0; i < dp.length; i++) {

            // Nome do item na linha
            if (i == 0) {
                System.out.printf("%-10s", "0");
            } else {
                String nome = items.get(i - 1).getNome();

                // se o nome for maior que 10 caracteres, corta o nome e completa com reticências
                if (nome.length() > 10) {
                    nome = nome.substring(0, 7) + "...";
                }

                System.out.printf("%-10s", nome);
            }

            // Conteúdo da linha
            for (int j = 0; j <= capacity; j++) {
                System.out.printf("%" + colWidth + "d", dp[i][j]);
            }

            System.out.println();
        }

        System.out.println("\n====================================================================================================================\n");
    }



    // Gets
    public int getTotalValue() {
        return totalValue;
    }

    public List<Item> getChosenItems() {
        return chosenItems;
    }
}
