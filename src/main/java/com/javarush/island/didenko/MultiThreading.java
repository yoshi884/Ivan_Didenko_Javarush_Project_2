package com.javarush.island.didenko;

import java.util.concurrent.Phaser;

public class MultiThreading implements Runnable {
    private final Phaser phaser;

    private final Island island;
    private final int x;

    private final int y;


    public MultiThreading(Phaser phaser, Island island, int x, int y) {
        this.phaser = phaser;
        this.island = island;
        this.x = x;
        this.y = y;
        phaser.register();
    }

    @Override
    public void run() {

        island.updateSaturation(x, y);
        phaser.arriveAndAwaitAdvance();
        try {

            Thread.sleep(2);
        }catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        island.animalsMultiply(x, y);
        phaser.arriveAndAwaitAdvance();
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        island.hunt(x, y);
        phaser.arriveAndAwaitAdvance();
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        island.move(x, y);
        phaser.arriveAndDeregister();

    }
}
