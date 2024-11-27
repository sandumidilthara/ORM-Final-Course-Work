package lk.ijse.util;

import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(TextFieldType fieldType, String text) {
        String patternString = "";


        switch (fieldType) {
            case NAME:
                patternString = "^[A-z\\s]{3,}$";
                break;
            case CONTACT:
                patternString = "^([+]94|0)([1-9]{2})([0-9]{7})$";
                break;

            case ADDRESS:
                patternString = "^([A-z0-9]|[-/,.@+]|\\s){4,}$";
                break;
            case FEE:
                patternString = "^[0-9]+(\\.[0-9]+)?$";
                break;
//
            case PASSWORD:
                patternString = "^.*\\d.*\\d.*\\d.*$";
                break;

            case DURATION:
                patternString = "^(\\d+)\\s*(days?|weeks?|months?|hours?)\\s*(\\d+\\s*(days?|weeks?|months?|hours?))?$\n";
                break;
            case ROLE:
                patternString = "^[A-z\\s]{3,}$";
                break;
            case STATUS:
                patternString = "^(completed|not completed)$\n";
                break;


        }

        if (text == null || text.trim().isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);

        return matcher.matches();
    }

    public static boolean setTextColor(TextFieldType fieldType, TextField textField) {
        if (isTextFieldValid(fieldType, textField.getText())) {
            textField.setStyle(" -fx-text-box-border: #34d734;\n" +
                    "    -fx-focus-color: #12dc12;\n" +
                    "    -fx-faint-focus-color: rgba(246,68,68,0);;");

            return true;
        } else {
            textField.setStyle(" -fx-text-box-border: #f10000;\n" +
                    "    -fx-focus-color: #ff0000;\n" +
                    "    -fx-faint-focus-color: rgba(246,68,68,0);;");
            return false;
        }
    }
}
