package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class ExamScheduler implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SCHEDULE_FILE = "schedule.ser";
    private List<Date> examDates = new ArrayList<>();
    private List<Date> presentationDates = new ArrayList<>();
    private Calendario calendario;
    RoomManager roomManager;
    private Map<Usuario, Map<Date, List<Room>>> userScheduledExams = new HashMap<>();
    private Map<Usuario, Map<Date, List<Room>>> userScheduledPresentations = new HashMap<>();

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

    public List<Room> scheduleExam(Usuario user, LocalDateTime dateTime, int numStudents, boolean needComputer) {
        Date date = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        if (isDateWithinExamPeriod(date) && isDateAvailable(date) && isDateAvailableWith24hGap(date)) {
            Room root = needComputer ? roomManager.getPcRoot() : roomManager.getRoot();
            List<Room> rooms = roomManager.searchRoom(root, numStudents);
            if (rooms != null) {
                addExamDate(date);
                userScheduledExams.computeIfAbsent(user, k -> new HashMap<>()).put(date, rooms);
                saveSchedule();
                return rooms;
            } else {
                System.out.println("No available room for the specified number of students.");
            }
        } else {
            System.out.println("Date not available for exam scheduling.");
        }
        return Collections.emptyList();
    }

    public List<Room> schedulePresentation(Usuario user, LocalDate date, int numStudents, boolean needComputer) {
        LocalDateTime dateTime = date.atStartOfDay();
        Date presentationDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
        if (isDateWithinExamPeriod(presentationDate) && isPresentationDateAvailable(presentationDate)) {
            Room root = needComputer ? roomManager.getPcRoot() : roomManager.getRoot();
            List<Room> rooms = roomManager.searchRoom(root, numStudents);
            if (rooms != null) {
                addPresentationDate(presentationDate);
                userScheduledPresentations.computeIfAbsent(user, k -> new HashMap<>()).put(presentationDate, rooms);
                saveSchedule();
                return rooms;
            } else {
                System.out.println("No available room for the specified number of students.");
            }
        } else {
            System.out.println("Date not available for presentation scheduling.");
        }
        return Collections.emptyList();
    }

    public Map<Date, List<Room>> getScheduledExams(Usuario user) {
        return userScheduledExams.getOrDefault(user, Collections.emptyMap());
    }

    public Map<Date, List<Room>> getScheduledPresentations(Usuario user) {
        return userScheduledPresentations.getOrDefault(user, Collections.emptyMap());
    }

    public void saveSchedule() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SCHEDULE_FILE))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ExamScheduler loadSchedule() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SCHEDULE_FILE))) {
            return (ExamScheduler) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Schedule file not found, starting with a new schedule.");
            return new ExamScheduler();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ExamScheduler();
        }
    }
}