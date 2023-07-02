package com.javarush.island.didenko;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Getter
@Setter
public class Dialog {
    private static final String WELCOME_MASSAGE = "Welcome to simulation \"ISLAND LIVE\"";
    private static final String REQUEST_WIDTH = "Enter width of island: ";
    private static final String REQUEST_LENGTH = "Enter length of island: ";
    private static final String REQUEST_LIFE_TIME = "Enter lifetime of island: ";
    private int lifeTime;
    private int length;
    private int width;

    private PrintStream output;
    private InputStream input;

    public Dialog(PrintStream output, InputStream input) {
        this.output = output;
        this.input = input;
    }

    public void dialogStart() {
        try (Scanner scanner = new Scanner(input)) {
            output.println(WELCOME_MASSAGE);
            output.println(REQUEST_WIDTH);
            this.setWidth(scanner.nextInt());
            output.println(REQUEST_LENGTH);
            this.setLength(scanner.nextInt());
            output.println(REQUEST_LIFE_TIME);
            this.setLifeTime(scanner.nextInt());
        }
    }
}
