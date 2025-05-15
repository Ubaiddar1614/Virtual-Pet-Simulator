package pets;
import exceptions.*;
import java.awt.Color;

public class Dog extends Pet {
    public Dog(String name) {
        super(name, PetType.DOG);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " says: Woof woof!");
    }

    @Override
    public String getEmoji() {
        return "🐕";
    }

    @Override
    public Color getPrimaryColor() {
        return new Color(168, 113, 57);
    }

    public void fetch() throws LowEnergyException {
        if (energy < 2) {
            throw new LowEnergyException(name + " is too tired to fetch! 💤");
        }
        energy = Math.max(0, energy - 2);
        happiness = Math.min(10, happiness + 3);
    }
}