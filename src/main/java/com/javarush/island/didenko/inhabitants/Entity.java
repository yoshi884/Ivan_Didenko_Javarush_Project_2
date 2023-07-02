package com.javarush.island.didenko.inhabitants;

import com.javarush.island.didenko.Settings;
import lombok.Getter;


@Getter
public abstract class Entity implements Livable, Cloneable {
    private double weight;
    private int maxAtOneCell;
    @Getter
    private Settings settings;

    public Entity() {
        settings = new Settings();
        this.weight = settings.fetchDoubleProperties(this.getClass().getSimpleName() + "Weight");
        this.maxAtOneCell = settings.fetchIntProperties(this.getClass().getSimpleName() + "MAC");
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
