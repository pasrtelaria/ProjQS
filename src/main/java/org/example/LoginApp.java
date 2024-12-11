package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginApp extends Application {
    private static RoomManager roomManager;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Label userLabel = new Label("Username:");
        grid.add(userLabel, 0, 0);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 0);

        Label pwLabel = new Label("Password:");
        grid.add(pwLabel, 0, 1);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 1);

        Button loginButton = new Button("Login");
        grid.add(loginButton, 1, 2);

        Label messageLabel = new Label();
        grid.add(messageLabel, 1, 3);

        Label registerLabel = new Label("Register a new user:");
        grid.add(registerLabel, 0, 4);

        TextField newUserTextField = new TextField();
        newUserTextField.setPromptText("New Username");
        grid.add(newUserTextField, 1, 4);

        PasswordField newPwBox = new PasswordField();
        newPwBox.setPromptText("New Password");
        grid.add(newPwBox, 1, 5);

        Button registerButton = new Button("Register");
        grid.add(registerButton, 1, 6);

        loginButton.setOnAction(e -> {
            String username = userTextField.getText();
            String password = pwBox.getText();
            LoginController.loginUsuario(username, password, messageLabel, primaryStage);
        });

        registerButton.setOnAction(e -> {
            String newUsername = newUserTextField.getText();
            String newPassword = newPwBox.getText();
            LoginController.registerUsuario(newUsername, newPassword, messageLabel);
        });

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        roomManager = new RoomManager();
        if (roomManager.getRoot() == null && roomManager.getPcRoot() == null) {
            roomManager.addRoom(false, 101, 84);
            roomManager.addRoom(false, 102, 96);
            roomManager.addRoom(false, 103, 126);
            roomManager.addRoom(false, 104, 168);
            roomManager.addRoom(false, 105, 239);
            roomManager.addRoom(false, 201, 88);
            roomManager.addRoom(false, 202, 84);
            roomManager.addRoom(false, 203, 84);
            roomManager.addRoom(false, 204, 84);
            roomManager.addRoom(false, 205, 60);
            roomManager.addRoom(false, 206, 60);
            roomManager.addRoom(false, 207, 60);
            roomManager.addRoom(false, 208, 86);
            roomManager.addRoom(false, 209, 60);
            roomManager.addRoom(false, 210, 118);
            roomManager.addRoom(false, 301, 104);
            roomManager.addRoom(false, 302, 58);
            roomManager.addRoom(false, 303, 58);
            roomManager.addRoom(false, 304, 98);
            roomManager.addRoom(false, 305, 94);
            roomManager.addRoom(false, 306, 60);
            roomManager.addRoom(false, 307, 60);
            roomManager.addRoom(false, 308, 60);
            roomManager.addRoom(false, 309, 90);
            roomManager.addRoom(false, 310, 60);
            roomManager.addRoom(false, 311, 58);
            roomManager.addRoom(false, 313, 18);
            roomManager.addRoom(false, 316, 108);
            roomManager.addRoom(false, 317, 12);
            roomManager.addRoom(false, 401, 28);
            roomManager.addRoom(false, 402, 22);
            roomManager.addRoom(true, 403, 24);
            roomManager.addRoom(true, 404, 24);
            roomManager.addRoom(false, 405, 0);
            roomManager.addRoom(false, 406, 0);
            roomManager.addRoom(false, 407, 0);
            roomManager.addRoom(true, 408, 28);
            roomManager.addRoom(false, 409, 0);
            roomManager.addRoom(true, 410, 46);
            roomManager.addRoom(true, 411, 24);
            roomManager.addRoom(true, 412, 24);
            roomManager.addRoom(true, 413, 24);
            roomManager.addRoom(true, 414, 60);
            roomManager.addRoom(true, 415, 24);
            roomManager.addRoom(true, 416, 36);
            roomManager.addRoom(false, 418, 12);
            roomManager.addRoom(false, 419, 12);
            roomManager.addRoom(true, 421, 74);
            roomManager.addRoom(false, 422, 18);
            roomManager.addRoom(false, 509, 36);
            roomManager.addRoom(false, 510, 38);
            roomManager.addRoom(false, 511, 40);
            roomManager.addRoom(false, 512, 30);
            roomManager.addRoom(false, 751, 68);
            roomManager.addRoom(false, 752, 90);
            roomManager.addRoom(false, 753, 4);
            roomManager.addRoom(false, 751, 158); // Combined 751/752
            roomManager.addRoom(false, 754, 150); // Combined 754/755
            roomManager.addRoom(false, 754, 82);
            roomManager.addRoom(false, 755, 68);
            roomManager.addRoom(false, 756, 26);
            roomManager.addRoom(false, 757, 14);
            roomManager.addRoom(false, 758, 20);
            roomManager.addRoom(false, 759, 20);
            roomManager.addRoom(false, 760, 20);

        }

        LoginController.setRoomManager(roomManager);
        LoginController.createAndPersistAdmin("admin", "123", "Informática");

        launch(args);
    }
}