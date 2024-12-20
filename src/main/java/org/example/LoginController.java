package org.example;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginController {
    private static Map<String, Usuario> usuarios = new HashMap<>();
    private static Usuario currentUser;
    private static ExamScheduler examScheduler;
    private static RoomManager roomManager;
    private static final String USERS_FILE = "usuarios.ser";

    static {
        loadUsuarios();
        examScheduler = ExamScheduler.loadSchedule();
    }

    public static void setRoomManager(RoomManager rm) {
        roomManager = rm;
        if (examScheduler == null) {
            examScheduler = new ExamScheduler(new Calendario(), roomManager);
        } else {
            examScheduler.roomManager = roomManager;
        }
    }

    public static void loginUsuario(String username, String password, Label messageLabel, Stage primaryStage) {
        Usuario usuario = usuarios.get(username);

        if (usuario != null && usuario.getSenha().equals(password)) {
            usuario.login();
            currentUser = usuario;
            messageLabel.setText("Login successful!");
            showMainWindow(primaryStage);
        } else {
            messageLabel.setText("Incorrect username or password.");
        }
    }

    public static void registerUsuario(String username, String password, Label messageLabel) {
        if (usuarios.containsKey(username)) {
            messageLabel.setText("Username already exists.");
        } else {
            usuarios.put(username, new Usuario(username, password));
            saveUsuarios();
            messageLabel.setText("User registered successfully!");
        }
    }

    private static void showMainWindow(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Label welcomeLabel = new Label("Welcome, " + currentUser.getUsuario());
        grid.add(welcomeLabel, 0, 0);

        Label dateLabel = new Label("Date (yyyy-mm-dd):");
        grid.add(dateLabel, 0, 1);

        TextField dateTextField = new TextField();
        grid.add(dateTextField, 1, 1);

        Label timeLabel = new Label("Time (HH:mm):");
        grid.add(timeLabel, 0, 2);

        TextField timeTextField = new TextField();
        grid.add(timeTextField, 1, 2);

        Label numStudentsLabel = new Label("Number of Students:");
        grid.add(numStudentsLabel, 0, 3);

        TextField numStudentsTextField = new TextField();
        grid.add(numStudentsTextField, 1, 3);

        CheckBox computerCheckBox = new CheckBox("Need Computer");
        grid.add(computerCheckBox, 1, 4);

        Button scheduleExamsButton = new Button("Schedule Exams");
        grid.add(scheduleExamsButton, 1, 5);

        Button schedulePresentationButton = new Button("Schedule Presentation");
        grid.add(schedulePresentationButton, 1, 6);

        Button appointCalendarButton = new Button("Appoint Calendar");
        grid.add(appointCalendarButton, 1, 7);

        Button showScheduledButton = new Button("Show Scheduled Exams and Presentations");
        grid.add(showScheduledButton, 1, 8);

        Button logoutButton = new Button("Logout");
        grid.add(logoutButton, 1, 9);

        Label messageLabel = new Label();
        grid.add(messageLabel, 1, 10);

        scheduleExamsButton.setOnAction(e -> {
            LocalDate date = LocalDate.parse(dateTextField.getText());
            LocalTime time = LocalTime.parse(timeTextField.getText());
            LocalDateTime dateTime = LocalDateTime.of(date, time);
            int numStudents = Integer.parseInt(numStudentsTextField.getText());
            boolean needComputer = computerCheckBox.isSelected();
            scheduleExams(dateTime, numStudents, needComputer, messageLabel);
        });

        schedulePresentationButton.setOnAction(e -> {
            LocalDate date = LocalDate.parse(dateTextField.getText());
            int numStudents = Integer.parseInt(numStudentsTextField.getText());
            boolean needComputer = computerCheckBox.isSelected();
            schedulePresentation(date, numStudents, needComputer, messageLabel);
        });

        appointCalendarButton.setOnAction(e -> showAppointCalendarWindow());

        showScheduledButton.setOnAction(e -> showScheduledExamsAndPresentations());

        logoutButton.setOnAction(e -> {
            currentUser = null;
            primaryStage.close();
            Stage loginStage = new Stage();
            new LoginApp().start(loginStage);
        });

        if (currentUser instanceof Admin) {
            appointCalendarButton.setVisible(true);
        } else {
            appointCalendarButton.setVisible(false);
        }

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void showAppointCalendarWindow() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Appoint Calendar");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Label startDateLabel = new Label("Start Date (yyyy-mm-dd):");
        grid.add(startDateLabel, 0, 0);

        TextField startDateTextField = new TextField();
        grid.add(startDateTextField, 1, 0);

        Label endDateLabel = new Label("End Date (yyyy-mm-dd):");
        grid.add(endDateLabel, 0, 1);

        TextField endDateTextField = new TextField();
        grid.add(endDateTextField, 1, 1);

        Button appointButton = new Button("Appoint");
        grid.add(appointButton, 1, 2);

        Label messageLabel = new Label();
        grid.add(messageLabel, 1, 3);

        appointButton.setOnAction(e -> {
            LocalDate startDate = LocalDate.parse(startDateTextField.getText());
            LocalDate endDate = LocalDate.parse(endDateTextField.getText());
            appointCalendar(startDate, endDate, messageLabel);
        });

        Scene scene = new Scene(grid, 300, 200);
        window.setScene(scene);
        window.showAndWait();
    }

    private static void scheduleExams(LocalDateTime dateTime, int numStudents, boolean needComputer, Label messageLabel) {
        List<Room> selectedRooms;
        selectedRooms = examScheduler.scheduleExam(currentUser, dateTime, numStudents, needComputer);
        if (selectedRooms != null && !selectedRooms.isEmpty()) {
            StringBuilder roomsInfo = new StringBuilder("Exams scheduled successfully in rooms: ");
            for (Room room : selectedRooms) {
                roomsInfo.append("\n").append(room.getNum()).append(" (Capacity: ").append(room.getCapacity()).append(")");
            }
            messageLabel.setText(roomsInfo.toString());
        } else {
            messageLabel.setText("Error scheduling exam.");
        }
    }

    private static void schedulePresentation(LocalDate date, int numStudents, boolean needComputer, Label messageLabel) {
        List<Room> selectedRooms;
        selectedRooms = examScheduler.schedulePresentation(currentUser, date, numStudents, needComputer);
        if (selectedRooms != null && !selectedRooms.isEmpty()) {
            StringBuilder roomsInfo = new StringBuilder("Presentations scheduled successfully in rooms: ");
            for (Room room : selectedRooms) {
                roomsInfo.append("\n").append(room.getNum()).append(" (Capacity: ").append(room.getCapacity()).append(")");
            }
            messageLabel.setText(roomsInfo.toString());
        } else {
            messageLabel.setText("Error scheduling presentation.");
        }
    }

    private static void showScheduledExamsAndPresentations() {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Scheduled Exams and Presentations");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Label examsLabel = new Label("Scheduled Exams:");
        grid.add(examsLabel, 0, 0);

        StringBuilder examsInfo = new StringBuilder();
        for (Map.Entry<Date, List<Room>> entry : examScheduler.getScheduledExams(currentUser).entrySet()) {
            examsInfo.append("Date: ").append(entry.getKey()).append("\nRooms: ");
            for (Room room : entry.getValue()) {
                examsInfo.append(room.getNum()).append(" (Capacity: ").append(room.getCapacity()).append("), ");
            }
            examsInfo.append("\n\n");
        }

        Label examsInfoLabel = new Label(examsInfo.toString());
        grid.add(examsInfoLabel, 0, 1);

        Label presentationsLabel = new Label("Scheduled Presentations:");
        grid.add(presentationsLabel, 0, 2);

        StringBuilder presentationsInfo = new StringBuilder();
        for (Map.Entry<Date, List<Room>> entry : examScheduler.getScheduledPresentations(currentUser).entrySet()) {
            presentationsInfo.append("Date: ").append(entry.getKey()).append("\nRooms: ");
            for (Room room : entry.getValue()) {
                presentationsInfo.append(room.getNum()).append(" (Capacity: ").append(room.getCapacity()).append("), ");
            }
            presentationsInfo.append("\n\n");
        }

        Label presentationsInfoLabel = new Label(presentationsInfo.toString());
        grid.add(presentationsInfoLabel, 0, 3);

        Scene scene = new Scene(grid, 400, 400);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void appointCalendar(LocalDate startDate, LocalDate endDate, Label messageLabel) {
        if (currentUser instanceof Admin) {
            Calendario calendario = new Calendario();
            calendario.setInicioEpocaExames(startDate);
            calendario.setFimEpocaExames(endDate);
            ((Admin) currentUser).definirCalendarioAcademico(startDate, endDate, startDate.plusMonths(3), endDate.plusMonths(1));
            examScheduler = new ExamScheduler(calendario, roomManager);
            examScheduler.saveSchedule();
            messageLabel.setText("Calendar appointed successfully!");
        }
    }

    private static void saveUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createAndPersistAdmin(String username, String password, String departamento) {
        Admin admin = new Admin(username, password, departamento, new ExamScheduler());
        usuarios.put(username, admin);
        saveUsuarios();
    }

    private static void loadUsuarios() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            usuarios = (Map<String, Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Users file not found, starting with an empty user list.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}