package com.cesar.ahorcado;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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

    @FXML
    private void setReset(){}
    @FXML
    private void onSendGuess(){

    }

}