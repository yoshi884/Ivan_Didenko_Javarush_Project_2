package com.javarush.island.didenko;

import lombok.SneakyThrows;

import java.util.concurrent.Phaser;

public class MultiThreading implements Runnable {
    private Phaser phaser;

    private Island island;
    private int x;

    private int y;


    public MultiThreading(Phaser phaser, Island island, int x, int y) {
        this.phaser = phaser;
        this.island = island;
        this.x = x;
        this.y = y;
        phaser.register();
    }

    @SneakyThrows
    @Override
    public void run() {
        island.updateSaturation(x, y);
        phaser.arriveAndAwaitAdvance();
        Thread.sleep(2);

        island.animalsMultiply(x, y);
        phaser.arriveAndAwaitAdvance();
        Thread.sleep(2);

        island.hunt(x, y);
        phaser.arriveAndAwaitAdvance();
        Thread.sleep(2);

        island.move(x, y);
        phaser.arriveAndDeregister();

    }
}
