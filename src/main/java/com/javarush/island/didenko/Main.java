package com.javarush.island.didenko;
import java.lang.reflect.InvocationTargetException;


public class Main {
    public static void main(String[] args) throws  NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Dialog dialog = new Dialog(System.out , System.in);
        dialog.dialogStart();

        Island island = new Island(dialog.getLifeTime() , dialog.getLength() , dialog.getWidth());
        island.startSimulation();
    }
}
