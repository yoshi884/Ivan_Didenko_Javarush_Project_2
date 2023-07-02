package com.javarush.island.didenko;



import com.javarush.island.didenko.inhabitants.Entity;
import com.javarush.island.didenko.inhabitants.Livable;
import com.javarush.island.didenko.inhabitants.carnivore.*;
import com.javarush.island.didenko.inhabitants.herbivore.*;
import com.javarush.island.didenko.inhabitants.plants.Banana;
import com.javarush.island.didenko.inhabitants.plants.Grass;

import java.util.ArrayList;
import java.util.List;

public class Creator {
    public static List<Livable>  dictionaryEntities;
    public Creator() {

        this.dictionaryEntities = new ArrayList<>();
        dictionaryEntities.add(new Wolf());
        dictionaryEntities.add(new Fox());
        dictionaryEntities.add(new Bear());
        dictionaryEntities.add(new Python());
        dictionaryEntities.add(new Eagle());
        dictionaryEntities.add(new Boar());
        dictionaryEntities.add(new Buffalo());
        dictionaryEntities.add(new Deer());
        dictionaryEntities.add(new Duck());
        dictionaryEntities.add(new Goat());
        dictionaryEntities.add(new Horse());
        dictionaryEntities.add(new Mouse());
        dictionaryEntities.add(new Rabbit());
        dictionaryEntities.add(new Sheep());
        dictionaryEntities.add(new Grass());
        dictionaryEntities.add(new Banana());
    }

    public static Entity fetchAnimal(Class clazz){
        for (Livable livable: dictionaryEntities) {
            if(livable.getClass().equals(clazz)){
                return (Entity) livable;
            }
        }
        return null;
    }
}
