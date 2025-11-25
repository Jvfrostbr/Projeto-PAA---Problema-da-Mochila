package model;

public class Item {
    private String nome;
    private int peso;
    private int valor;
    private double densidade;

    // Construtor
    public Item(String nome, int peso, int valor) {
        this.nome = nome;
        this.peso = peso;
        this.valor = valor;
        this.densidade = (double) valor / peso;
    }

    // Getters
    public String getNome() {
        return nome;
    }

    public int getPeso() {
        return peso;
    }

    public int getValor() {
        return valor;
    }

    public double getDensidade() {
        return densidade;
    }

    @Override
    public String toString() {
        return String.format(
                "%-12s | peso: %2d | valor: %3d | densidade: %5.2f",
                nome, peso, valor, densidade
        );
    }

}
