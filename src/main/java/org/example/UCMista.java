package org.example;

class UCMista extends UC {

    public UCMista(double notaFinal) {
        super(notaFinal);
        if (avaliations.size() != 2) {
            throw new IllegalArgumentException("UCMista must have exactly 2 Avaliations.");
        }
    }

    @Override
    public void addAvaliation(Avaliation avaliation) {
        if (avaliations.size() < 2) {
            super.addAvaliation(avaliation);
        } else {
            throw new IllegalStateException("UCMista can only have 2 Avaliations.");
        }
    }
}