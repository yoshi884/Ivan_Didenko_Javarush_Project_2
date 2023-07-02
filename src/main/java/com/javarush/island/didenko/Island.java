package com.javarush.island.didenko;

import com.javarush.island.didenko.inhabitants.Animal;
import com.javarush.island.didenko.inhabitants.Entity;
import com.javarush.island.didenko.inhabitants.Livable;
import com.javarush.island.didenko.inhabitants.Plant;
import lombok.Getter;


import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.Phaser;

public class Island {

    private static final int DAYS_WITHOUT_FOOD = 4;
    private static final String MOVING_MASSAGE = " -after moving";

    private static final String SATURATION_MASSAGE = " -after death via starvation and grown plants";
    private static final String HUNTING_MASSAGE = " -after hunting";
    private static final String MULTIPLY_MASSAGE = " -after multiplying";
    private static final int DIRECTIONS = 4;
    private static final int DIRECTIONS_UP = 0;
    private static final int DIRECTIONS_RIGHT = 1;
    private static final int DIRECTIONS_DOWN = 2;
    private static final int DIRECTIONS_LEFT = 3;
    private static final int HUNT_POSSIBILITY = 101;
    @Getter


    private final Map<Livable, List<Livable>>[][] cells;


    private final int lifeTime;
    @Getter
    private final int length;
    @Getter
    private final int width;

    public void startSimulation() throws
            NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        islandInit();

        Statistics statistics = new Statistics(this);
        System.out.println("Island after init");
        statistics.printFullStat();
        System.out.println();

        for (int i = 0; i < lifeTime; i++) {
            System.out.println("Day №" + i);

            Phaser phaser = new Phaser(1);

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < length; y++) {
                    MultiThreading multiThreading = new MultiThreading(phaser, this, x, y);
                    new Thread(multiThreading).start();

                }
            }
            phaser.arriveAndAwaitAdvance(); // UpdateSaturation
            statistics.printFullStat();
            System.out.println(SATURATION_MASSAGE);

            phaser.arriveAndAwaitAdvance(); // AnimalsMultiply
            statistics.printShortStat();
            System.out.println(MULTIPLY_MASSAGE);

            phaser.arriveAndAwaitAdvance(); //Hunt
            statistics.printShortStat();
            System.out.println(HUNTING_MASSAGE);


            phaser.arriveAndAwaitAdvance();
            phaser.arriveAndDeregister();
            statistics.printShortStat();
            System.out.println(MOVING_MASSAGE);
            System.out.println();

        }
    }

    public Island(int lifeTime, int length, int width) {
        this.lifeTime = lifeTime;
        this.length = length;
        this.width = width;

        cells = new HashMap[width][length];

        System.out.printf("Island was created with next parameters: lifetime %d , length %d , width %d %n", lifeTime, length, width);

    }

    private void islandInit() {
        Creator creator = new Creator();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                cells[i][j] = new HashMap<>();

                for (Livable typeLive : creator.dictionaryEntities) {

                    var entity = (Entity) typeLive;
                    var maxAmount = entity.getMaxAtOneCell();
                    var amountCreatedEntity = new Random().nextInt(maxAmount);

                    List<Livable> entities = new ArrayList<>();
                    for (int k = 0; k < amountCreatedEntity; k++) {
                        entities.add((Livable) entity.clone());


                    }
                    cells[i][j].put(entity, entities);
                }
            }
        }
    }

    void updateSaturation(int x, int y) {

        for (Map.Entry entry : cells[x][y].entrySet()) {
            var list = (List) entry.getValue();

            Iterator it = list.iterator();

            if (entry.getKey() instanceof Animal) {
                while (it.hasNext()) {
                    var animal = (Animal) it.next();
                    animal.setSaturation(animal.getSaturation() - (animal.getFullSaturation() / DAYS_WITHOUT_FOOD));
                    if (animal.getSaturation() <= 0) {
                        it.remove();
                    }
                }
            } else if (entry.getKey() instanceof Plant plant) {
                var maxAmount = plant.getMaxAtOneCell();
                var amountCreatedEntity = new Random().nextInt(maxAmount);
                growing:
                for (int k = 0; k < amountCreatedEntity; k++) {

                    if (list.size() < plant.getMaxAtOneCell()) {
                        list.add(plant.clone());


                    } else {
                        break growing;
                    }
                }
            }
        }
    }

    synchronized void move(int x, int y) {

        for (Livable typeLive : cells[x][y].keySet()) {
            var list = cells[x][y].get(typeLive);
            Iterator it = list.iterator();
            while (it.hasNext()) {

                if (it.next() instanceof Animal animal) {
                    int cellsToGo = animal.getSpeed();
                    int currentPositionX = x;
                    int currentPositionY = y;

                    for (int k = 0; k < cellsToGo; k++) {
                        int direction = new Random().nextInt(DIRECTIONS);
                        switch (direction) {
                            case DIRECTIONS_UP -> currentPositionY--;
                            case DIRECTIONS_DOWN -> currentPositionY++;
                            case DIRECTIONS_RIGHT -> currentPositionX++;
                            case DIRECTIONS_LEFT -> currentPositionX--;
                        }
                        currentPositionX = checkBorders(currentPositionX, width);
                        currentPositionY = checkBorders(currentPositionY, length);

                    }

                    if (hasAvailableSpace(typeLive, animal, currentPositionX, currentPositionY)
                            && hasNewPosition(x, y, currentPositionX, currentPositionY)) {
                        it.remove();

                        cells[currentPositionX][currentPositionY].get(typeLive).add(animal);
                    }
                }
            }
        }
    }


    private boolean hasAvailableSpace(Livable typeLive, Animal animal, int currentPositionX, int currentPositionY) {
        return cells[currentPositionX][currentPositionY].get(typeLive).size() < animal.getMaxAtOneCell();
    }

    private boolean hasNewPosition(int i, int j, int currentPositionX, int currentPositionY) {
        return !(currentPositionX == i && currentPositionY == j);
    }

    private int checkBorders(int currentPositionX, int border) {
        if (currentPositionX < 0) {
            currentPositionX = 0;
        }
        if (currentPositionX > border - 1) {
            currentPositionX = border - 1;
        }
        return currentPositionX;
    }


    void hunt(int x, int y) {
        Map<Livable, Map<Livable, Integer>> foodTable = new FoodData().getFoodTable();
        List<Livable> hunters = new ArrayList<>(foodTable.keySet());

        for (Livable entity : cells[x][y].keySet()) {
            if (entity instanceof Animal animal) {

                var huntersPool = cells[x][y].get(entity);
                if (huntersPool.isEmpty()) {
                    continue;
                }

                for (int k = 0; k < huntersPool.size(); k++) {

                    var hunter = (Animal) huntersPool.get(new Random().nextInt(huntersPool.size()));

                    if (hunter.getSaturation() == hunter.getFullSaturation()) {
                        continue;
                    }

                    var victimsType = randomKey(FoodData.getFoodTable().get(Creator.fetchAnimal(hunter.getClass())));
                    var victimPool = cells[x][y].get(victimsType);
                    if (victimPool.isEmpty()) {
                        continue;
                    }

                    var victim = victimPool.get(new Random().nextInt(victimPool.size()));

                    if (new Random().nextInt(HUNT_POSSIBILITY) < FoodData.fetchHuntChance(hunter, victim)) {
                        victimEating(hunter, (Entity) victim);
                        victimPool.remove(victim);
                    }
                }
            }
        }
    }


    private void victimEating(Animal hunter, Entity victim) {
        double hunterSat = hunter.getSaturation();
        double newSat = hunterSat + victim.getWeight();
        hunter.setSaturation(Math.min(newSat, hunter.getFullSaturation()));
    }

    private <K, V> K randomKey(Map<K, V> map) {
        List<K> keysAsArray = new ArrayList<K>(map.keySet());
        Random r = new Random();
        return keysAsArray.get(r.nextInt(keysAsArray.size()));
    }

    void animalsMultiply(int x, int y) {


        for (Livable typeLive : cells[x][y].keySet()) {
            var list = cells[x][y].get(typeLive);


            int kids = list.size() / 2;

            borning:
            for (int k = 0; k < kids; k++) {
                if (typeLive instanceof Animal animal) {
                    if (list.size() < animal.getMaxAtOneCell()) {
                        list.add((Livable) animal.clone());


                    } else {
                        break borning;
                    }
                }
            }
        }
    }
}

