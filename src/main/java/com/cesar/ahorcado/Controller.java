package com.cesar.ahorcado;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Controller {
    @FXML
    ImageView mainImage;

    @FXML
    Label hiddenWord;

    @FXML
    Button sendGuess;
    @FXML
    Button reset;

    @FXML
    TextField guessLetter;

    @FXML
    ListView<String> letterChecks;
    @FXML
    ListView<String> listWords;
    @FXML
    ListView<String> listTries;

    MainDB dictDB = new MainDB("dict");

    List<Image> imageList = new ArrayList<>();

    String currentWord;

    Random random = new Random();

    @FXML
    private void initialize(){
        String imagePath = "src/main/resources/com/cesar/ahorcado/img/";
        for (int i = 0; i < 8; i++) {
            try {
                imageList.add(new Image(new FileInputStream(new File(imagePath + i + ".png").toString())));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        int r = random.nextInt(581);
        try {
            currentWord = dictDB.executeQuery("SELECT PALABRA FROM PALABRA WHERE ID = " + r).getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(currentWord);
    }

    @FXML
    private void setReset(){
    }
    @FXML
    private void onSendGuess(){

    }

    /*

        Estructura de dict.db:
        dict -> palabra

     */
}