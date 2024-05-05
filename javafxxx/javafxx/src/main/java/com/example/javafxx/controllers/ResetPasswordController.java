package com.example.javafxx.controllers;

import com.example.javafxx.entities.Utilisateur; // Replace with your actual User entity class
import com.example.javafxx.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class ResetPasswordController {

    @FXML
    private TextField emailField;
    @FXML
    private Button back_btn;

    private final String FROM_EMAIL = "faresfelhi2@gmail.com";
    private final String FROM_PASSWORD = "pnwb ozcb vsji eqhw";

    private final UserService userService = new UserService(); // Assuming you have a UserService class





    public void handleSendReset(ActionEvent actionEvent) {
        String toEmail = emailField.getText();

        // Retrieve the user from the database
        Utilisateur user = userService.getUserByEmail(toEmail);

        if (user != null) {
            String password = user.getPassword();

            // Send password reset email
            boolean emailSent = sendPasswordResetEmail(toEmail, password);

            // Show alert based on email sending status
            if (emailSent) {
                showAlert("Password Reset", "Password sent to " + toEmail);
            } else {
                showAlert("Error", "Failed to send password reset email.");
            }
        } else {
            showAlert("Error", "User not found.");
        }
    }

    private boolean sendPasswordResetEmail(String toEmail, String password) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
            }
        };

        Session session = Session.getInstance(properties, authenticator);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Password Reset");
            message.setText("Your  password is: " + password);

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void handleBack(ActionEvent event) {
        // Load the signIn.fxml file
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/javafxx/signIn.fxml"));
            Stage stage = (Stage) back_btn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign In");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
