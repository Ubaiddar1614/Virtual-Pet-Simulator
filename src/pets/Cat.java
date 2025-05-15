package pets;
import exceptions.*;
import java.awt.Color;

public class Cat extends Pet {
    public Cat(String name) {
        super(name, PetType.CAT);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " says: Meow meow!");
    }

    @Override
    public String getEmoji() {
        return "🐈";
    }

    @Override
    public Color getPrimaryColor() {
        return new Color(192, 192, 192);
    }

    public void hunt() throws LowEnergyException {
        if (energy < 3) {
            throw new LowEnergyException(name + " is too tired to hunt! 💤");
        }
        energy = Math.max(0, energy - 3);
        hunger = Math.max(0, hunger - 1);
    }
}