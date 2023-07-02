package com.javarush.island.didenko;



import com.javarush.island.didenko.inhabitants.Livable;
import com.javarush.island.didenko.inhabitants.carnivore.*;
import com.javarush.island.didenko.inhabitants.herbivore.*;
import com.javarush.island.didenko.inhabitants.plants.*;
import lombok.Getter;


import java.util.HashMap;
import java.util.Map;

import static com.javarush.island.didenko.Creator.fetchAnimal;


public class FoodData {


    @Getter
    private static final Map<Livable, Map<Livable, Integer>> foodTable = new HashMap<>();

    {
        Map<Livable, Integer> wolfVictims = new HashMap<>() {
            {
                put(fetchAnimal(Horse.class), 10);
                put(fetchAnimal(Deer.class), 15);
                put(fetchAnimal(Rabbit.class), 60);
                put(fetchAnimal(Mouse.class), 80);
                put(fetchAnimal(Goat.class), 60);
                put(fetchAnimal(Sheep.class), 70);
                put(fetchAnimal(Boar.class), 15);
                put(fetchAnimal(Buffalo.class), 10);
                put(fetchAnimal(Duck.class), 40);
            }
        };
        foodTable.put(fetchAnimal(Wolf.class), wolfVictims);

        Map<Livable, Integer> pythonVictims = new HashMap<>() {
            {
                put(fetchAnimal(Fox.class), 15);
                put(fetchAnimal(Rabbit.class), 20);
                put(fetchAnimal(Mouse.class), 40);
                put(fetchAnimal(Duck.class), 40);
            }
        };
        foodTable.put(fetchAnimal(Python.class), pythonVictims);

        Map<Livable, Integer> foxVictims = new HashMap<>() {
            {
                put(fetchAnimal(Rabbit.class), 70);
                put(fetchAnimal(Mouse.class), 90);
                put(fetchAnimal(Duck.class), 60);

            }
        };
        foodTable.put(fetchAnimal(Fox.class), foxVictims);

        Map<Livable, Integer> bearVictims = new HashMap<>() {
            {
                put(fetchAnimal(Python.class), 80);
                put(fetchAnimal(Horse.class), 40);
                put(fetchAnimal(Deer.class), 80);
                put(fetchAnimal(Rabbit.class), 80);
                put(fetchAnimal(Mouse.class), 90);
                put(fetchAnimal(Goat.class), 70);
                put(fetchAnimal(Sheep.class), 70);
                put(fetchAnimal(Boar.class), 50);
                put(fetchAnimal(Buffalo.class), 20);
                put(fetchAnimal(Duck.class), 10);
            }
        };
        foodTable.put(fetchAnimal(Bear.class), bearVictims);

        Map<Livable, Integer> eagleVictims = new HashMap<>() {
            {
                put(fetchAnimal(Fox.class), 10);
                put(fetchAnimal(Rabbit.class), 90);
                put(fetchAnimal(Mouse.class), 90);
                put(fetchAnimal(Duck.class), 80);
            }
        };
        foodTable.put(fetchAnimal(Eagle.class), eagleVictims);

        Map<Livable, Integer> herbivoreVictims = new HashMap<>() {
            {
                put(fetchAnimal(Banana.class), 100);
                put(fetchAnimal(Grass.class), 100);
            }
        };
        foodTable.put(fetchAnimal(Horse.class), herbivoreVictims);
        foodTable.put(fetchAnimal(Deer.class), herbivoreVictims);
        foodTable.put(fetchAnimal(Rabbit.class), herbivoreVictims);
        foodTable.put(fetchAnimal(Mouse.class), herbivoreVictims);
        foodTable.put(fetchAnimal(Goat.class), herbivoreVictims);
        foodTable.put(fetchAnimal(Sheep.class), herbivoreVictims);
        foodTable.put(fetchAnimal(Buffalo.class), herbivoreVictims);
        foodTable.put(fetchAnimal(Duck.class), herbivoreVictims);

        Map<Livable, Integer> boarVictims = new HashMap<>() {
            {
                put(fetchAnimal(Grass.class), 100);
                put(fetchAnimal(Mouse.class), 50);
                put(fetchAnimal(Banana.class), 100);

            }
        };
        foodTable.put(fetchAnimal(Boar.class), boarVictims);
    }


    public static Integer fetchHuntChance(Livable hunter, Livable victim) {

        var innerMap = foodTable.get(fetchAnimal(hunter.getClass()));
        var tem = innerMap.keySet();

        for (var currentVictim : tem) {
            if (currentVictim.getClass().isAssignableFrom(victim.getClass())) {
                return innerMap.get(currentVictim);
            }
        }

        return 0;
    }
}
