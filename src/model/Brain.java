package model;

import com.sun.javafx.geom.Vec2d;

import java.util.Arrays;

public class Brain {

    private final Vec2d[] directions;

    public Brain(int size) {
        directions = new Vec2d[size];
        for (int i = 0; i < directions.length; i++) {
            directions[i] = new Vec2d(Math.random() - 0.5, Math.random() - 0.5);
        }
    }

    private Brain(Vec2d[] directions) {
        this.directions = new Vec2d[directions.length];
        for (int i = 0; i < directions.length; i++) {
            this.directions[i] = new Vec2d(directions[i].x, directions[i].y);
        }
    }

    protected Brain clone() {
        return new Brain(directions);
    }

    public void mutate(double mutationRate) {
        for (int i = 0; i < directions.length; i++) {
            if (Math.random() < mutationRate) {
                double randomAngle = Math.random() * 2 * Math.PI;
                directions[i].set(Math.cos(randomAngle), Math.sin(randomAngle));
            }
        }
    }

    public Vec2d getDirection(int step) {
        if (step >= directions.length || step < 0) {
            return null;
        }
        return directions[step];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brain brain = (Brain) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(directions, brain.directions);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(directions);
    }
}
