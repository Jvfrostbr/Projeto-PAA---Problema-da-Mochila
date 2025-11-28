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

            for (int j = 1; j <= capacity; j++) {
                // Se o item atual couber na mochila
                if (item.getPeso() <= j) {

                    // Max: (Com item) vs (Sem item)
                    dp[i][j] = Math.max(
                            item.getValor() + dp[i - 1][j - item.getPeso()],
                            dp[i - 1][j]
                    );
                } else {
                    dp[i][j] = dp[i - 1][j]; // Mantém o anterior
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
        int val = totalValue;

        // Começa do fim (último item, capacidade total)
        for (int i = n; i > 0 && val > 0; i--) {
            // Se o valor mudou em relação à linha de cima...
            if (val != dp[i - 1][w]) {
                // Significa que este item foi escolhido
                Item item = items.get(i - 1);
                chosenItems.add(item);
                // Subtrai valor e peso para continuar voltando
                val -= item.getValor();
                w -= item.getPeso();
            }
        }
    }

    public void solveGreedy(List<Item> items, boolean mostrarTabela) {
        chosenItems.clear();
        totalValue = 0;

        // Ordena por densidade
        List<Item> sorted = new ArrayList<>(items);
        sorted.sort(Comparator.comparingDouble(Item::getDensidade).reversed());

        // CHAMA A NOVA FUNÇÃO AQUI
        if (mostrarTabela) {
            printTableGredy("cabeçalho",false, 0, null);;
        }
        
        int currentWeight = 0;

        // Itera sobre a lista já ordenada
        for (Item item : sorted) {
            boolean cabe = (currentWeight + item.getPeso() <= capacity);
            
            if (mostrarTabela) {
                printTableGredy("meio",cabe, currentWeight, item);
            }

            // Se cabe atualiza os itens, o peso atual e o valor total contido na mochila
            if (cabe) {
                chosenItems.add(item);
                currentWeight += item.getPeso();
                totalValue += item.getValor();
            }
        }

        if (mostrarTabela) {
            printTableGredy("fim",false, currentWeight, null);
        }
    }

    private void printTableGredy(String partTablePrinted , boolean cabe, int currentWeight, Item item){

        switch (partTablePrinted) {
            case "cabeçalho":
                System.out.printf("\n%-13s %-7s %-6s %-12s %-10s %-20s%n", 
                    "Item", "Peso", "Valor", "Densidade", "Decisão", "Motivo");
                System.out.println("-------------------------------------------------------------------------");
            break;
            
            case "meio":
                String decisao = cabe ? "ENTROU" : "NÃO CABE";
                    
                String motivo = String.format("%d + %d = %d %s %d", 
                        currentWeight, 
                        item.getPeso(), 
                        (currentWeight + item.getPeso()), 
                        (cabe ? "<=" : ">"), 
                        capacity);

                System.out.printf("%-13s %-7d %-6d %-12.2f %-10s %-20s%n",
                        formatName(item.getNome()),
                        item.getPeso(),
                        item.getValor(),
                        item.getDensidade(),
                        decisao,
                        motivo
                );
            break;

            case "fim":
                System.out.println("-------------------------------------------------------------------------");
                System.out.println("Peso Final: " + currentWeight + " / " + capacity);
                System.out.println("=========================================================================\n");
             break;
        }
    }

    private String formatName(String name) {
        if (name.length() > 10) return name.substring(0, 9) + ".";
        return name;
    }

    private void imprimirMatrizDP(int[][] dp, List<Item> items) {

        System.out.println("\n============== MATRIZ DP ==============\n");

        int colWidth = 5; // largura fixa

        // Cabeçalho
        System.out.printf("%-6s %-12s", "Peso", "Item");
        for (int w = 0; w <= capacity; w++) {
            System.out.printf("%" + colWidth + "d", w);
        }
        System.out.println();

        // Linha separadora
        System.out.print("----------------------------------------------------------------");
        for (int w = 0; w <= capacity; w++) {
            System.out.print("---");
        }
        System.out.println();

        // Linhas da matriz
        for (int i = 0; i < dp.length; i++) {

            if (i == 0) {
                // linha 0: sem item, sem peso
                System.out.printf("%-6s %-12s", "0", "0");
            } else {
                Item item = items.get(i - 1);
                String nome = item.getNome();

                // Limita nome a 12 chars
                if (nome.length() > 12) {
                    nome = nome.substring(0, 9) + "...";
                }

                System.out.printf("%-6d %-12s", item.getPeso(), nome);
            }

            // Conteúdo da linha
            for (int j = 0; j <= capacity; j++) {
                System.out.printf("%" + colWidth + "d", dp[i][j]);
            }

            System.out.println();
        }

        System.out.println("\n===================================================================================================\n");
    }




    // Gets
    public int getTotalValue() {
        return totalValue;
    }

    public List<Item> getChosenItems() {
        return chosenItems;
    }
}
