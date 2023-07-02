package com.javarush.island.didenko.inhabitants;

import lombok.Getter;
import lombok.Setter;


@Getter
public abstract class Animal extends Entity {
    private int speed;
    @Setter
    private double saturation;
    private double fullSaturation;


    public Animal() {

        super();

        this.speed = super.getSettings().fetchIntProperties(this.getClass().getSimpleName() + "Moving");
        this.saturation = super.getSettings().fetchDoubleProperties(this.getClass().getSimpleName() + "Saturation");
        this.fullSaturation = super.getSettings().fetchDoubleProperties(this.getClass().getSimpleName() + "Saturation");
    }
}
