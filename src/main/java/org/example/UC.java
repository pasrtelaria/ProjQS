package org.example;

import java.util.ArrayList;

public abstract class UC {
    protected double notaFinal;
    protected ArrayList<Avaliation> avaliations;

    public UC(double notaFinal) {
        this.notaFinal = notaFinal;
        this.avaliations = new ArrayList<>();
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    public ArrayList<Avaliation> getAvaliations() {
        return avaliations;
    }

    public void addAvaliation(Avaliation avaliation) {
        avaliations.add(avaliation);
    }

    @Override
    public String toString() {
        return "UC{" +
                "notaFinal=" + notaFinal +
                ", avaliations=" + avaliations +
                '}';
    }
}
