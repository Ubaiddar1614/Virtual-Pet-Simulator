package pets;
import exceptions.*;
import java.awt.Color;

public class Fish extends Pet {
    public Fish(String name) {
        super(name, PetType.FISH);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " makes bubble sounds!");
    }

    @Override
    public String getEmoji() {
        return "🐠";
    }

    @Override
    public Color getPrimaryColor() {
        return new Color(64, 224, 208);
    }

    public void swim() throws LowEnergyException {
        if (energy < 1) {
            throw new LowEnergyException(name + " is too tired to swim fast! 💤");
        }
        energy = Math.max(0, energy - 1);
        happiness = Math.min(10, happiness + 1);
    }
}