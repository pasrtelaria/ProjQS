package org.example;

import java.time.LocalDate;

public class Calendario {
    private LocalDate inicioSemestre;
    private LocalDate fimSemestre;
    private LocalDate inicioEpocaExames;
    private LocalDate fimEpocaExames;

    // Construtor
    public Calendario(LocalDate inicioSemestre, LocalDate fimSemestre, LocalDate inicioEpocaExames, LocalDate fimEpocaExames) {
        this.inicioSemestre = inicioSemestre;
        this.fimSemestre = fimSemestre;
        this.inicioEpocaExames = inicioEpocaExames;
        this.fimEpocaExames = fimEpocaExames;
    }

    public Calendario() {
    }

    // Getters e setters
    public LocalDate getInicioSemestre() {
        return inicioSemestre;
    }

    public void setInicioSemestre(LocalDate inicioSemestre) {
        this.inicioSemestre = inicioSemestre;
    }

    public LocalDate getFimSemestre() {
        return fimSemestre;
    }

    public void setFimSemestre(LocalDate fimSemestre) {
        this.fimSemestre = fimSemestre;
    }

    public LocalDate getInicioEpocaExames() {
        return inicioEpocaExames;
    }

    public void setInicioEpocaExames(LocalDate inicioEpocaExames) {
        this.inicioEpocaExames = inicioEpocaExames;
    }

    public LocalDate getFimEpocaExames() {
        return fimEpocaExames;
    }

    public void setFimEpocaExames(LocalDate fimEpocaExames) {
        this.fimEpocaExames = fimEpocaExames;
    }

    public void exibirCalendario() {
        System.out.println("Início do semestre: " + inicioSemestre);
        System.out.println("Fim do semestre: " + fimSemestre);
        System.out.println("Início da época de exames: " + inicioEpocaExames);
        System.out.println("Fim da época de exames: " + fimEpocaExames);
    }
}

