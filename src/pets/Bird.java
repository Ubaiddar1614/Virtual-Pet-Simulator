package pets;
import exceptions.*;
import java.awt.Color;

public class Bird extends Pet {
    public Bird(String name) {
        super(name, PetType.BIRD);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " says: Tweet tweet!");
    }

    @Override
    public String getEmoji() {
        return "🐦";
    }

    @Override
    public Color getPrimaryColor() {
        return new Color(66, 105, 225);
    }

    public void fly() throws LowEnergyException {
        if (energy < 4) {
            throw new LowEnergyException(name + " is too tired to fly! 💤");
        }
        energy = Math.max(0, energy - 4);
        happiness = Math.min(10, happiness + 4);
    }
}