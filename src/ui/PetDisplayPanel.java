package ui;
import pets.Pet;
import javax.swing.*;
        import java.awt.*;

public class PetDisplayPanel extends JPanel {
    private Pet pet;
    private boolean moving = false;
    private Timer animationTimer;
    private int animationStep = 0;

    public PetDisplayPanel() {
        setBackground(new Color(230, 230, 255));
        setPreferredSize(new Dimension(300, 200));
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    public void setPet(Pet pet) {
        this.pet = pet;
        setBackground(new Color(
                pet.getPrimaryColor().getRed() / 2 + 127,
                pet.getPrimaryColor().getGreen() / 2 + 127,
                pet.getPrimaryColor().getBlue() / 2 + 127
        ));
        repaint();
    }

    public void animateMovement() {
        if (pet == null) return;
        moving = true;
        animationStep = 0;

        if (animationTimer != null) {
            animationTimer.stop();
        }

        animationTimer = new Timer(100, e -> {
            animationStep++;
            if (animationStep > 10) {
                moving = false;
                animationTimer.stop();
            }
            repaint();
        });
        animationTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pet == null) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw grid
        int gridSize = 20;
        g2d.setColor(new Color(220, 220, 220));
        for (int i = 0; i <= 10; i++) {
            g2d.drawLine(i * gridSize, 0, i * gridSize, 10 * gridSize);
            g2d.drawLine(0, i * gridSize, 10 * gridSize, i * gridSize);
        }

        // Draw pet
        Font emojiFont = new Font("Segoe UI Emoji", Font.PLAIN, 30);
        g2d.setFont(emojiFont);

        int x = pet.getPosX() * gridSize - 5;
        int y = pet.getPosY() * gridSize + 20;

        if (moving) {
            y -= Math.abs(5 * Math.sin(animationStep * 0.6));
            x += animationStep % 2 == 0 ? 1 : -1;
        }

        g2d.drawString(pet.getEmoji(), x, y);

        // Draw pet name
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.setColor(pet.getPrimaryColor().darker());
        g2d.drawString(pet.getName(), 10, getHeight() - 10);

        // Draw mood
        String mood = getMoodEmoji();
        g2d.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        g2d.drawString(mood, getWidth() - 30, getHeight() - 10);
    }

    private String getMoodEmoji() {
        if (pet.getHappiness() >= 8) return "😄";
        if (pet.getHunger() >= 8) return "😋";
        if (pet.getEnergy() <= 2) return "😴";
        if (pet.getHappiness() <= 3) return "😢";
        return "😊";
    }
}