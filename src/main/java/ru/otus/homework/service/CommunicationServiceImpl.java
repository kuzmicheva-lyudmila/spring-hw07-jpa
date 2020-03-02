package ru.otus.homework.service;

import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class CommunicationServiceImpl implements CommunicationService {

    private static final String CHARSET_NAME = "UTF-8";

    @Override
    public String getUserInputString(String message, String errorMessage, List<String> dictionary) throws UnsupportedEncodingException {
        Scanner sc = new Scanner(System.in);
        PrintStream printStream = new PrintStream(System.out, true, CHARSET_NAME);
        boolean isFirstInput = true;
        Optional<String> elementDictionary;
        do {
            if (isFirstInput) {
                printStream.println(message + ": ");
                isFirstInput = false;
            } else {
                printStream.println(errorMessage + ": ");
            }

            String resultString = sc.nextLine();
            elementDictionary = dictionary.stream()
                    .filter(e -> e.toLowerCase().equals(resultString.toLowerCase()))
                    .findAny();
        } while (elementDictionary.isEmpty());
        return elementDictionary.get();
    }

    @Override
    public String getUserInputString(String message, String errorMessage, String template) throws UnsupportedEncodingException {
        Scanner sc = new Scanner(System.in);
        PrintStream printStream = new PrintStream(System.out, true, CHARSET_NAME);
        String resultString;
        boolean isFirstInput = true;
        do {
            if (isFirstInput) {
                printStream.println(message + ": ");
                isFirstInput = false;
            } else {
                printStream.println(errorMessage + ": ");
            }
            resultString = sc.nextLine();
        } while (!resultString.matches(template));
        return resultString.trim();
    }

    @Override
    public void showMessage(String messageString) throws UnsupportedEncodingException {
        PrintStream printStream = new PrintStream(System.out, true, CHARSET_NAME);
        printStream.println(messageString);
    }
}
