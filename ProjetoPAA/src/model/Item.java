package model;

public class Item {
    private int peso;
    private int valor;
    private double densidade;

    //Construtor
    public Item(int peso, int valor) {
        this.peso = peso;
        this.valor = valor;
        this.densidade = (double) valor / peso;
    }

    // Gets and Sets
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
        return String.format("Item(peso=%d, valor=%d, densidade=%.2f)",
                             peso, valor, densidade);
    }
}
