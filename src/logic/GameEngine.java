// GameEngine.java
package logic;
import pets.*;
import exceptions.*;
import javax.swing.Timer;
import java.util.concurrent.ScheduledExecutorService;



public class GameEngine {
    private Pet currentPet;
    private Timer decayTimer;

    public GameEngine() {
        setupDecayTimer();
    }

    private void setupDecayTimer() {
        decayTimer = new Timer(30000, (e) -> { // Every 30 seconds
            if (currentPet != null) {
                currentPet.hunger = Math.min(10, currentPet.getHunger() + 1);
                currentPet.happiness = currentPet.getHappiness() - 1;
                if (currentPet.happiness < 3) {
                    currentPet.happiness = 3; // Minimum happiness
                }
            }
        });
        decayTimer.start();
    }

    public void setCurrentPet(Pet pet) {
        this.currentPet = pet;
    }

    public void feedPet() throws InvalidActionException {
        if (currentPet == null) throw new InvalidActionException("No pet selected!");
        currentPet.eat();
    }

    public void playWithPet() throws LowEnergyException, InvalidActionException {
        if (currentPet == null) throw new InvalidActionException("No pet selected!");
        currentPet.play();
    }

    public void putPetToSleep() {
        if (currentPet != null) currentPet.sleep();
    }

    public void movePet(int x, int y) throws OutOfBoundsException, LowEnergyException, InvalidActionException {
        if (currentPet == null) throw new InvalidActionException("No pet selected!");
        currentPet.move(x, y);
    }

    public void performSpecialAction() throws LowEnergyException {
        if (currentPet == null) return;

        if (currentPet instanceof Dog) ((Dog) currentPet).fetch();
        else if (currentPet instanceof Bird) ((Bird) currentPet).fly();
        else if (currentPet instanceof Rabbit) ((Rabbit) currentPet).hop();
        else if (currentPet instanceof Fish) ((Fish) currentPet).swim();
    }
}
