package model;

import com.sun.javafx.geom.Vec2d;

import java.awt.*;

public class Dot implements Drawable {

    private final Brain brain;

    private final double radius = 2.5;

    private Vec2d start;
    private Vec2d previous;
    private Vec2d position;
    private Vec2d velocity = new Vec2d(0,0);
    private Vec2d acceleration = new Vec2d(0,0);

    private boolean dead = false;
    private boolean goal = false;
    private boolean best = false;

    private int step = 0;
    private double distance = 0;

    public Dot(int brainSize, int x, int y) {
        brain = new Brain(brainSize);
        position = new Vec2d(x, y);
        previous = new Vec2d(x, y);
        start = new Vec2d(x, y);
    }

    private Dot(Brain brain, double x, double y) {
        this.brain = brain.clone();
        position = new Vec2d(x, y);
        start = new Vec2d(x, y);
        previous = new Vec2d(x, y);
    }

    public void move() {
        if (dead || goal) {
            return;
        }
        acceleration = brain.getDirection(step++);
        if (acceleration == null) {
            acceleration = new Vec2d(0,0);
            dead = true;
            return;
        }
        add(velocity, acceleration);
        limit(velocity, 3);
        previous = new Vec2d(position);
        add(position, velocity);
        distance += previous.distance(position);
    }

    public int getX() {
        return (int) position.x;
    }

    public int getY() {
        return (int) position.y;
    }

    public void dead() {
        dead = true;
    }

    public void goal() {
        goal = true;
    }

    public boolean finished() {
        return !(dead || goal);
    }

    private void add(Vec2d a, Vec2d b) {
        a.set(a.x + b.x, a.y + b.y);
    }

    private void limit(Vec2d a, double limit) {
        double magnitudeSquared = a.x * a.x + a.y * a.y;
        if (magnitudeSquared > limit * limit) {
            double magnitude = Math.sqrt(magnitudeSquared);
            if (magnitude == 0 || magnitude == 1) {
                return;
            }
            a.set(a.x / magnitude, a.y / magnitude);
            a.set(a.x * limit, a.y * limit);
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(best ? Color.GREEN : Color.LIGHT_GRAY);
        g.fillOval((int) (position.x - radius), (int) (position.y - radius), (int)(2 * radius), (int)(2 * radius));
        g.setColor(Color.BLACK);
        g.drawOval((int) (position.x - radius), (int) (position.y - radius), (int)(2 * radius), (int)(2 * radius));
    }

    public void reset() {
        dead = false;
        goal = false;
        position.set(start);
        step = 0;
        velocity.set(0, 0);
        acceleration.set(0, 0);
        distance = 0;
    }

    public int fitness() {
        if (dead) {
            return (int) Math.pow(distance-step, 2);
        }
        int inv = 1000 - step;
        return (int) Math.pow(inv, 3);
    }

    public void mutate(double mutationRate) {
        brain.mutate(mutationRate);
    }

    public Dot clone() {
        return new Dot(brain, start.x, start.y);
    }

    public Dot getChild(double mutationRate) {
        Dot child = this.clone();
        child.mutate(mutationRate);
        return child;
    }

    public void setBest(boolean best) {
        this.best = best;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dot dot = (Dot) o;

        return brain.equals(dot.brain);
    }

    @Override
    public int hashCode() {
        return brain.hashCode();
    }
}
