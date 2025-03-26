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
    Label loseLabel;
    @FXML
    Label winLabel;

    @FXML
    Button guess;
    @FXML
    Button solve;
    @FXML
    Button reset;

    @FXML
    TextField guessText;

    @FXML
    ListView<String> checks = new ListView<>();

    @FXML
    ListView<String> listWords = new ListView<>();

    @FXML
    ListView<String> listTries = new ListView<>();

    MainDB dictDB = new MainDB("dict");

    List<Image> imageList = new ArrayList<>();

    String currentWord;

    Random random = new Random();

    int failedTries = 0;

    boolean endRun = false;

    boolean win = false;

    @FXML
    private void initialize(){
        //Cargar las imagenes en la lista de imagenes disponibles
        String imagePath = "src/main/resources/com/cesar/ahorcado/img/";
        for (int i = 0; i < 8; i++) {
            try {
                imageList.add(new Image(new FileInputStream(new File(imagePath + i + ".png").toString())));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        loadImage();
        getNewWord();
    }

    @FXML
    private void setReset(){
        endRun = false;
        guessText.setEditable(true);
        guess.setDisable(false);
        winLabel.setVisible(false);
        loseLabel.setVisible(false);
        checks.getItems().clear();

        listWords.getItems().add(currentWord);
        listTries.getItems().add(win ? failedTries + " fallos" : "Perdiste");

        win = false;
        failedTries = 0;
        loadImage();
        getNewWord();
    }

    @FXML
    private void onSolve(){
        String triedWord = guessText.getText();
        if (!endRun){
            if (!triedWord.isEmpty()){
                if (triedWord.toLowerCase().equals(currentWord)){
                    StringBuilder temp = new StringBuilder();
                    for (int i = 0; i < triedWord.length(); i++) {
                        temp.append(triedWord.charAt(i)).append(" ");
                    }

                    hiddenWord.setText(temp.toString().toLowerCase());

                    endRun = true;
                    guessText.setEditable(false);
                    guess.setDisable(true);
                    winLabel.setVisible(true);
                    win = true;
                }else{
                    failedTries++;
                    loadImage();

                    checks.getItems().add(triedWord + " no es la palabra");

                    if (failedTries == 7){
                        endRun = true;
                        guessText.setEditable(false);
                        guess.setDisable(true);
                        loseLabel.setVisible(true);
                    }
                }
            }
        }
    }

    @FXML
    private void onSendGuess(){
        if (!endRun){
            if (!guessText.getText().isEmpty()){
                StringBuilder temp = new StringBuilder(hiddenWord.getText());
                List<Integer> indexes = new ArrayList<>();
                char letter = guessText.getText().toLowerCase().charAt(0);
                boolean appears;

                for (int i = 0; i < temp.length()/2; i++) {
                    if (currentWord.charAt(i) == letter){
                        indexes.add(i);
                    }
                }

                if (!indexes.isEmpty()){
                    for (int index : indexes){
                        temp.setCharAt(index*2, letter);
                    }

                    appears = true;
                    hiddenWord.setText(temp.toString());

                    if (hiddenWord.getText().replace(" ", "").equals(currentWord)){
                        endRun = true;
                        guessText.setEditable(false);
                        guess.setDisable(true);
                        winLabel.setVisible(true);
                        win = true;
                    }
                }else {
                    failedTries++;
                    loadImage();
                    appears = false;

                    if (failedTries == 7){
                        endRun = true;
                        guessText.setEditable(false);
                        guess.setDisable(true);
                        loseLabel.setVisible(true);
                    }
                }

                checks.getItems().add(letter + " " + (appears ? "aparece" : "no aparece"));

                guessText.setText("");
            }
        }
    }

    private void loadImage(){
        mainImage.setImage(imageList.get(failedTries));
    }

    private void getNewWord(){
        int r = random.nextInt(581);
        try {
            currentWord = dictDB.executeQuery("SELECT PALABRA FROM PALABRA WHERE ID = " + r).getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        hiddenWord.setText("_ ".repeat(currentWord.length()));
    }

    /*

        Estructura de dict.db:
        dict -> palabra

     */
}