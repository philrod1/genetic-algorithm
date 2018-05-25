package model;

import java.awt.*;
import java.awt.image.BufferedImage;

public class World implements Drawable {
    private final BufferedImage world;
    private final Population population;

    public World(BufferedImage world, int populationSize, int brainSize) {
        this.world = world;
        population = new Population(populationSize, brainSize, 700, 700); // TODO: Get x and y from image
    }

    public boolean go() {
        return population.update(world);
    }

    public void draw(Graphics2D g) {
        g.drawImage(world, 0, 0, null);
        population.draw(g);
    }

    public void reset() {
        population.reset();
    }

    public void nextGeneration(double mutationRate) {
        population.nextGeneration(mutationRate);
    }
}
