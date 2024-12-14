package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Admin extends Usuario {
    private String departamento;
    private Calendario calendario;
    private ExamScheduler examScheduler;

    public Admin(String usuario, String senha, String departamento, ExamScheduler examScheduler) {
        super(usuario, senha);
        this.departamento = departamento;
        this.examScheduler = examScheduler;
    }

    public void gereDepartamento() {
        System.out.println("Departamento a ser gerenciado: " + this.departamento);
    }

    public void definirCalendarioAcademico(LocalDate inicioSemestre, LocalDate fimSemestre, LocalDate inicioEpocaExames, LocalDate fimEpocaExames) {
        this.calendario = new Calendario(inicioSemestre, fimSemestre, inicioEpocaExames, fimEpocaExames);
        System.out.println("Calendário acadêmico definido com sucesso!");
    }

    public void exibirCalendarioAcademico() {
        if (calendario != null) {
            calendario.exibirCalendario();
        } else {
            System.out.println("Calendário acadêmico não foi definido.");
        }
    }

    public void scheduleExams(LocalDateTime dateTime, int numStudents, boolean needComputer) {
        if (calendario == null) {
            throw new IllegalStateException("Calendario is not defined.");
        }
        examScheduler.scheduleExam(this, dateTime, numStudents, needComputer);
    }

    public void schedulePresentation(LocalDate date, int numStudents, boolean needComputer) {
        if (calendario == null) {
            throw new IllegalStateException("Calendario is not defined.");
        }
        examScheduler.schedulePresentation(this, date, numStudents, needComputer);
    }

    public void appointCalendar() {
        definirCalendarioAcademico(LocalDate.now(), LocalDate.now().plusMonths(6), LocalDate.now().plusMonths(3), LocalDate.now().plusMonths(4));
    }
}