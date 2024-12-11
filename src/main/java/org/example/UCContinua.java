package org.example;

class UCContinua extends UC {

    public UCContinua(double notaFinal) {
        super(notaFinal);
        if (avaliations.size() != 3) {
            throw new IllegalArgumentException("UCContinua must have exactly 3 Avaliations.");
        }
    }

    @Override
    public void addAvaliation(Avaliation avaliation) {
        if (avaliations.size() < 3) {
            super.addAvaliation(avaliation);
        } else {
            throw new IllegalStateException("UCContinua can only have 3 Avaliations.");
        }
    }
}
