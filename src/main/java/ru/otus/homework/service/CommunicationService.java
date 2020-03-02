package ru.otus.homework.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface CommunicationService {
    String getUserInputString(String message, String errorMessage, List<String> dictionary) throws UnsupportedEncodingException;
    String getUserInputString(String message, String errorMessage, String template) throws UnsupportedEncodingException;
    void showMessage(String messageString) throws UnsupportedEncodingException;
}
