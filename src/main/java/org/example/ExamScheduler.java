package org.example;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class ExamScheduler {
    private List<Date> examDates = new ArrayList<>();
    private List<Date> presentationDates = new ArrayList<>();
    private Calendario calendario;
    private RoomManager roomManager;

    public ExamScheduler(Calendario calendario, RoomManager roomManager) {
        this.calendario = calendario;
        this.roomManager = roomManager;
    }
    public ExamScheduler() {
    }

    public void addExamDate(Date date) {
        examDates.add(date);
    }

    public void addPresentationDate(Date date) {
        presentationDates.add(date);
    }

    public boolean isDateWithinExamPeriod(Date date) {
        if (calendario == null || calendario.getInicioEpocaExames() == null || calendario.getFimEpocaExames() == null) {
            throw new IllegalStateException("Calendario or its exam period dates are not initialized.");
        }
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return !localDateTime.toLocalDate().isBefore(calendario.getInicioEpocaExames()) && !localDateTime.toLocalDate().isAfter(calendario.getFimEpocaExames());
    }

    public boolean isDateAvailable(Date date) {
        return !examDates.contains(date);
    }

    public boolean isDateAvailableWith24hGap(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, -1);
        Date previousDay = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, 2);
        Date nextDay = cal.getTime();

        return !examDates.contains(previousDay) && !examDates.contains(nextDay);
    }

    public boolean isPresentationDateAvailable(Date date) {
        return !presentationDates.contains(date);
    }

    public List<Room> scheduleExam(LocalDateTime dateTime, int numStudents, boolean needComputer) {
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        if (isDateWithinExamPeriod(date) && isDateAvailable(date) && isDateAvailableWith24hGap(date)) {
            Room root = needComputer ? roomManager.getPcRoot() : roomManager.getRoot();
            List<Room> rooms = roomManager.searchRoom(root, numStudents);
            if (rooms != null) {
                addExamDate(date);
                return rooms;
            } else {
                System.out.println("No available room for the specified number of students.");
            }
        } else {
            System.out.println("Date not available for exam scheduling.");
        }
        return Collections.emptyList();
    }

    public void schedulePresentation(Date date) {
        if (isPresentationDateAvailable(date)) {
            addPresentationDate(date);
        } else {
            System.out.println("Date not available for presentation scheduling.");
        }
    }
}