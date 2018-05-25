package model;

import java.util.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Population implements Drawable {

    private final Dot[] dots;
    private final Random rng = new Random();

    public Population(int size, int brainSize, int x, int y) {
        dots = new Dot[size];
        for (int i = 0 ; i < size ; i ++) {
            dots[i] = new Dot(brainSize, x, y);
        }
    }

    public boolean update(BufferedImage world) {
        boolean done = false;
        for (Dot dot : dots) {
            dot.move();
            if((world.getRGB(dot.getX(), dot.getY()) & 0x00FFFFFF) == 0) {
                dot.dead();
            } else if ((world.getRGB(dot.getX(), dot.getY()) & 0x00FFFFFF) == 0x0000FF00) {
                dot.goal();
            }
            done |= dot.finished();
        }
        return done;
    }

    public void draw(Graphics2D g) {
        for (Dot dot : dots) {
            dot.draw(g);
        }
    }

    public void reset() {
        for (Dot dot : dots) {
            dot.reset();
        }
    }

    public void nextGeneration(double mutationRate) {
        double totalFitness = 0;
        Dot bestDot = dots[0];
        int bestFitness = bestDot.fitness();
        for (int i = 0 ; i < dots.length ; i++) {
            Dot dot = dots[i];
            dot.setBest(false);
            int dotFitness = dot.fitness();
            if (dotFitness > bestFitness) {
                bestFitness = dotFitness;
                bestDot = dot;
            }
            totalFitness += dotFitness;
        }
        bestDot.setBest(true);
        System.out.println((long)totalFitness);

        // Natural selection
        Set<Dot> parents = new HashSet<>();
        List<Dot> children = new LinkedList<>();
        while (children.size() < dots.length) {
            Dot dot = dots[rng.nextInt(dots.length)];
            double chance = dot.fitness() / totalFitness;
            if (chance >= rng.nextDouble()) {
//                if (parents.size() < dots.length / 10) {
//                    parents.add(dot);
//                }
                children.add(dot.getChild(mutationRate));
            }
        }
        int index = 0;
//        for (Dot dot : parents) {
//            dots[index++] = dot;
//        }
        for (Dot dot : children) {
            dots[index++] = dot;
        }
    }
}
