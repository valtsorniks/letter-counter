package lv.valts.orniks.letter.counter.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import lv.valts.orniks.letter.counter.service.CountResult;
import lv.valts.orniks.letter.counter.service.LetterCountingService;
import lv.valts.orniks.letter.counter.utils.FileWriter;

import java.io.File;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

public class MainController {

    @FXML
    private Button uploadButton;
    @FXML
    private Button saveButton;
    @FXML
    private TextArea textField;
    @FXML
    private Label uploadLabel;
    @FXML
    private Label errorMessage;
    @FXML
    private Label totalCharacters;
    @FXML
    private CheckBox reverseOrder;
    @FXML
    private CheckBox ignoreEmpty;
    @FXML
    private CheckBox combineCases;
    @FXML
    private RadioButton orderByKey;
    @FXML
    private RadioButton orderByValue;


    private CountResult countResult;

    private FileChooser fileChooser;

    public void uploadFile(ActionEvent actionEvent) {
        errorMessage.setText("");

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            uploadLabel.setText(file.getName());

            try {
                LetterCountingService letterCountingService = new LetterCountingService(LetterCountingService.ENG_ALPHABET);
                countResult = letterCountingService.countFile(file);
                setOptionsDisabled(false);
                updateResult(null);
                totalCharacters.setText("Kopā: " + countResult.getTotal());

            } catch (Exception e) {
                errorMessage.setText("Neizdevās apstrādāt failu!");
                setOptionsDisabled(true);
            }
        }
    }

    public void saveResult(ActionEvent actionEvent){
        errorMessage.setText("");

        File targetFile = fileChooser.showSaveDialog(null);
        if (targetFile != null){
            if (!FileWriter.writeStringToFile(getCurrentResultAsText(), targetFile)){
                errorMessage.setText("Neizdevās saglabāt failu!");
            }
        }
    }

    public void updateResult(ActionEvent actionEvent) {

        if (countResult == null){
            return;
        }

        Comparator<Map.Entry<Character, Integer>> orderByComparator;
        if (orderByKey.isSelected()){
            orderByComparator = CountResult.getOrderedByKeyComparator(reverseOrder.isSelected());
        }
        else {
            orderByComparator = CountResult.getOrderedByValueComparator(reverseOrder.isSelected());
        }

        countResult.setOrdered(orderByComparator,ignoreEmpty.isSelected(),combineCases.isSelected());
        textField.setText(getCurrentResultAsText());
    }


    public String getCurrentResultAsText(){
        LinkedHashMap<Character, Integer> map = countResult.getMap();
        return FileWriter.getMapAsString(map);
    }

    private void setOptionsDisabled(Boolean isDisabled){
        orderByKey.setDisable(isDisabled);
        orderByValue.setDisable(isDisabled);
        reverseOrder.setDisable(isDisabled);
        ignoreEmpty.setDisable(isDisabled);
        combineCases.setDisable(isDisabled);
        saveButton.setDisable(isDisabled);
    }



    {
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        FileChooser.ExtensionFilter extensionFilter =
                new FileChooser.ExtensionFilter("Text files", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
    }
}
