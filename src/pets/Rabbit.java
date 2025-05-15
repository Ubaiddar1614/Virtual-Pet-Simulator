package pets;
import exceptions.*;
import java.awt.Color;

public class Rabbit extends Pet {
    public Rabbit(String name) {
        super(name, PetType.RABBIT);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " makes a soft purring sound!");
    }

    @Override
    public String getEmoji() {
        return "🐇";
    }

    @Override
    public Color getPrimaryColor() {
        return new Color(245, 222, 179);
    }

    public void hop() throws LowEnergyException {
        if (energy < 1) {
            throw new LowEnergyException(name + " is too tired to hop! 💤");
        }
        energy = Math.max(0, energy - 1);
        happiness = Math.min(10, happiness + 1);
    }
}