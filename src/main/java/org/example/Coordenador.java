package org.example;

import java.time.LocalDateTime;

public class Coordenador extends Usuario {
    private String curso;
    private ExamScheduler examScheduler;

    public Coordenador(String usuario, String senha, String curso, ExamScheduler examScheduler) {
        super(usuario, senha);
        this.curso = curso;
        this.examScheduler = examScheduler;
    }

    public void gereCurso() {
        System.out.println("Curso a ser gerenciado: " + this.curso);
    }

    public void scheduleExams(LocalDateTime dateTime, int numStudents, boolean needComputer) {
        examScheduler.scheduleExam(this, dateTime, numStudents, needComputer);
    }
}