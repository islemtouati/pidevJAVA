package com.LibraMarket.libra.controllers;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class Recaptcha {

    public DefaultKaptcha kaptcha;

    public Recaptcha() {
        kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "40");
        Config config = new Config(properties);
        kaptcha.setConfig(config);
    }

    public String generateCaptcha() {
        return kaptcha.createText();
    }

    public boolean  showCaptchaDialog(String captchaText) {
        Label captchaLabel = new Label(captchaText);
        TextField captchaTextField = new TextField();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Captcha Verification");
        alert.setHeaderText("Veuillez saisir le Captcha ci-dessous :");
        alert.getDialogPane().setContent(new VBox(captchaLabel, captchaTextField));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String userInput = captchaTextField.getText();
            if (userInput.equals(captchaText)) {
                System.out.println("Captcha correct !");
                return true;
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Erreur");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Captcha incorrect !");
                errorAlert.showAndWait();
            }
        }
        return false;
    }

    public BufferedImage generateCaptchaImage(String captchaText) {
        return kaptcha.createImage(captchaText);
    }

    public byte[] convertImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
