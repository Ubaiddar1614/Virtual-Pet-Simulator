package pets;
import exceptions.*;

import java.awt.*;

public abstract class Pet {
    protected String name;
     public int hunger = 5;
    public int happiness = 5;
    public int energy = 10;
    protected int posX = 5, posY = 5;
    protected PetType type;

    public enum PetType {
        DOG, CAT, RABBIT, BIRD, FISH
    }

    public Pet(String name, PetType type) {
        this.name = name;
        this.type = type;
    }

    public abstract void makeSound();
    public abstract String getEmoji();
    public abstract Color getPrimaryColor();

    public void eat() throws InvalidActionException {
        if (hunger <= 0) {
            throw new InvalidActionException(name + " is already full! 🥗");
        }
        hunger = Math.max(0, hunger - 2);
        energy = Math.min(10, energy + 1);
    }

    public void play() throws LowEnergyException {
        if (energy < 3) {
            throw new LowEnergyException(name + " is too tired to play! 💤");
        }
        energy = Math.max(0, energy - 3);
        happiness = Math.min(10, happiness + 2);
        hunger = Math.min(10, hunger + 1);
    }

    public void sleep() {
        energy = 10;
        hunger = Math.min(10, hunger + 1);
    }

    public void move(int x, int y) throws OutOfBoundsException, LowEnergyException {
        if (x < 0 || x > 10 || y < 0 || y > 10) {
            throw new OutOfBoundsException("Can't move outside the allowed area! ❌");
        }
        if (energy <= 0) {
            throw new LowEnergyException(name + " is too tired to move! 💤");
        }
        posX = x;
        posY = y;
        energy = Math.max(0, energy - 1);
    }

    // Getters
    public String getName() { return name; }
    public int getHunger() { return hunger; }
    public int getHappiness() { return happiness; }
    public int getEnergy() { return energy; }
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public PetType getType() { return type; }
}