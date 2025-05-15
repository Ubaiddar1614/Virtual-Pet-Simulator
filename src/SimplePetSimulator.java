import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SimplePetSimulator extends JFrame {
    private Pet currentPet;
    private JPanel petDisplayPanel;
    private JTextArea messageArea;
    private Map<PetType, JButton> petButtons = new HashMap<>();
    private Random random = new Random();

    private JProgressBar hungerBar;
    private JProgressBar happinessBar;
    private JProgressBar energyBar;

    // Enum for pet types
    public enum PetType {
        DOG, CAT, RABBIT, BIRD, FISH
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new SimplePetSimulator();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Failed to start application: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public SimplePetSimulator() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println("Failed to set system look and feel");
        }

        setTitle("Virtual Pet Simulator");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeUI();
        createPet(PetType.DOG, "Buddy");
        setVisible(true);
    }

    private void initializeUI() {
        // Main layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create header
        JPanel headerPanel = createHeaderPanel();

        // Create pet display area
        petDisplayPanel = createPetDisplayPanel();

        // Create action buttons
        JPanel actionPanel = createActionPanel();

        // Create status panel
        JPanel statusPanel = createStatusPanel();

        // Create pet selection panel
        JPanel petSelectionPanel = createPetSelectionPanel();

        // Message area
        messageArea = new JTextArea(4, 40);
        messageArea.setEditable(false);
        messageArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        JScrollPane messageScroll = new JScrollPane(messageArea);
        messageScroll.setBorder(BorderFactory.createTitledBorder("Messages"));

        // Layout panels
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(petDisplayPanel, BorderLayout.CENTER);
        centerPanel.add(actionPanel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(statusPanel, BorderLayout.NORTH);
        rightPanel.add(petSelectionPanel, BorderLayout.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(messageScroll, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(100, 149, 237)); // Cornflower blue

        JLabel titleLabel = new JLabel("Virtual Pet Simulator");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        return headerPanel;
    }

    private JPanel createPetDisplayPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawPet(g);
            }
        };
        panel.setPreferredSize(new Dimension(400, 300));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.setBackground(Color.WHITE); // Clean white background
        return panel;
    }

    private void drawPet(Graphics g) {
        if (currentPet == null) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = petDisplayPanel.getWidth() / 2;
        int centerY = petDisplayPanel.getHeight() / 2;

        // Draw appropriate animal shape based on type
        g2d.setColor(currentPet.color);

        switch (currentPet.type) {
            case DOG -> drawDog(g2d, centerX, centerY);
            case CAT -> drawCat(g2d, centerX, centerY);
            case RABBIT -> drawRabbit(g2d, centerX, centerY);
            case BIRD -> drawBird(g2d, centerX, centerY);
            case FISH -> drawFish(g2d, centerX, centerY);
        }

        // Draw pet name
        g2d.setFont(new Font("SansSerif", Font.BOLD, 14));
        g2d.drawString(currentPet.name, centerX - 25, centerY + 50);
    }

    // Basic animal drawings
    private void drawDog(Graphics2D g, int x, int y) {
        // Body
        g.fillOval(x - 30, y - 20, 60, 40);

        // Head
        g.fillOval(x - 40, y - 30, 40, 40);

        // Ears
        g.fillOval(x - 45, y - 45, 20, 20);
        g.fillOval(x - 15, y - 45, 20, 20);

        // Tail
        g.fillRect(x + 28, y - 10, 20, 10);

        // Eyes
        g.setColor(Color.BLACK);
        g.fillOval(x - 35, y - 25, 8, 8);
        g.fillOval(x - 15, y - 25, 8, 8);

        // Nose
        g.fillOval(x - 25, y - 15, 10, 5);
    }

    private void drawCat(Graphics2D g, int x, int y) {
        // Body
        g.fillOval(x - 30, y - 15, 60, 35);

        // Head
        g.fillOval(x - 25, y - 35, 50, 45);

        // Ears
        int[] xPoints = {x - 15, x - 25, x - 5};
        int[] yPoints = {y - 65, y - 35, y - 35};
        g.fillPolygon(xPoints, yPoints, 3);

        int[] xPoints2 = {x + 15, x + 25, x + 5};
        int[] yPoints2 = {y - 65, y - 35, y - 35};
        g.fillPolygon(xPoints2, yPoints2, 3);

        // Tail
        g.fillRect(x + 30, y - 10, 25, 8);

        // Eyes
        g.setColor(Color.BLACK);
        g.fillOval(x - 15, y - 30, 8, 12);
        g.fillOval(x + 7, y - 30, 8, 12);
    }

    private void drawRabbit(Graphics2D g, int x, int y) {
        // Body
        g.fillOval(x - 25, y - 10, 50, 40);

        // Head
        g.fillOval(x - 20, y - 30, 40, 30);

        // Ears
        g.fillRect(x - 15, y - 60, 10, 40);
        g.fillRect(x + 5, y - 60, 10, 40);

        // Eyes
        g.setColor(Color.BLACK);
        g.fillOval(x - 15, y - 25, 6, 6);
        g.fillOval(x + 9, y - 25, 6, 6);

        // Nose
        g.fillOval(x - 3, y - 20, 6, 4);
    }

    private void drawBird(Graphics2D g, int x, int y) {
        // Body
        g.fillOval(x - 25, y - 15, 50, 30);

        // Head
        g.fillOval(x + 15, y - 25, 30, 30);

        // Wing
        g.fillOval(x - 30, y - 25, 40, 20);

        // Beak
        g.setColor(Color.ORANGE);
        int[] xPoints = {x + 45, x + 60, x + 45};
        int[] yPoints = {y - 15, y - 10, y - 5};
        g.fillPolygon(xPoints, yPoints, 3);

        // Eye
        g.setColor(Color.BLACK);
        g.fillOval(x + 30, y - 20, 5, 5);
    }

    private void drawFish(Graphics2D g, int x, int y) {
        // Body
        g.fillOval(x - 35, y - 20, 70, 40);

        // Tail
        int[] xPoints = {x - 35, x - 55, x - 35};
        int[] yPoints = {y - 20, y, y + 20};
        g.fillPolygon(xPoints, yPoints, 3);

        // Fin
        int[] xPointsFin = {x, x + 10, x - 10};
        int[] yPointsFin = {y - 20, y - 40, y - 40};
        g.fillPolygon(xPointsFin, yPointsFin, 3);

        // Eye
        g.setColor(Color.BLACK);
        g.fillOval(x + 15, y - 10, 8, 8);
    }

    private JPanel createActionPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 5, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        String[] actions = {"Feed", "Play", "Sleep", "Pet"};
        for (String action : actions) {
            JButton button = new JButton(action);
            button.addActionListener(e -> handleAction(action));
            panel.add(button);
        }

        return panel;
    }

    private JPanel createStatusPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 1, 0, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Pet Status"));

        hungerBar = createProgressBar("Hunger");
        happinessBar = createProgressBar("Happiness");
        energyBar = createProgressBar("Energy");

        panel.add(createLabeledProgressBar("Hunger:", hungerBar));
        panel.add(createLabeledProgressBar("Happiness:", happinessBar));
        panel.add(createLabeledProgressBar("Energy:", energyBar));

        return panel;
    }

    private JProgressBar createProgressBar(String name) {
        JProgressBar bar = new JProgressBar(0, 10);
        bar.setValue(5);
        bar.setStringPainted(true);
        return bar;
    }

    private JPanel createLabeledProgressBar(String labelText, JProgressBar bar) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.add(new JLabel(labelText), BorderLayout.WEST);
        panel.add(bar, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createPetSelectionPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 0, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Choose Pet"));

        for (PetType type : PetType.values()) {
            JButton button = new JButton(type.toString());
            button.addActionListener(e -> promptAndCreatePet(type));
            petButtons.put(type, button);
            panel.add(button);
        }

        return panel;
    }

    private void promptAndCreatePet(PetType type) {
        String name = JOptionPane.showInputDialog(this,
                "Name your " + type.toString().toLowerCase() + ":",
                "New Pet", JOptionPane.PLAIN_MESSAGE);
        createPet(type, name);
    }

    private void createPet(PetType type, String name) {
        if (name == null || name.trim().isEmpty()) {
            showMessage("Pet creation cancelled - no name provided");
            return;
        }

        currentPet = new Pet(name, type);
        updateStatusBars();
        showMessage("Created new " + type.toString().toLowerCase() + ": " + name);
        petDisplayPanel.repaint();

        // Highlight selected pet button
        for (JButton button : petButtons.values()) {
            button.setBackground(null);
        }
        petButtons.get(type).setBackground(new Color(200, 230, 200));
    }

    private void handleAction(String action) {
        if (currentPet == null) {
            showMessage("No pet selected! Please choose a pet first.");
            return;
        }

        try {
            switch (action) {
                case "Feed" -> {
                    currentPet.feed();
                    showMessage(currentPet.name + " is eating.");
                }
                case "Play" -> {
                    currentPet.play();
                    showMessage(currentPet.name + " is playing!");
                }
                case "Sleep" -> {
                    currentPet.sleep();
                    showMessage(currentPet.name + " is sleeping.");
                }
                case "Pet" -> {
                    currentPet.pet();
                    showMessage("You pet " + currentPet.name + ".");
                }
            }
            updateStatusBars();
            petDisplayPanel.repaint();
        } catch (Exception ex) {
            showMessage("Action failed: " + ex.getMessage());
        }
    }

    private void updateStatusBars() {
        if (currentPet != null) {
            hungerBar.setValue(currentPet.hunger);
            happinessBar.setValue(currentPet.happiness);
            energyBar.setValue(currentPet.energy);

            // Set colors based on values
            hungerBar.setForeground(hungerBar.getValue() < 3 ? Color.RED :
                    hungerBar.getValue() > 7 ? Color.GREEN : Color.ORANGE);
            happinessBar.setForeground(happinessBar.getValue() < 3 ? Color.RED :
                    happinessBar.getValue() > 7 ? Color.GREEN : Color.ORANGE);
            energyBar.setForeground(energyBar.getValue() < 3 ? Color.RED :
                    energyBar.getValue() > 7 ? Color.GREEN : Color.ORANGE);
        }
    }

    private void showMessage(String message) {
        messageArea.append("• " + message + "\n");
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }

    // Simple Pet class to handle basic pet functionality
    private class Pet {
        String name;
        PetType type;
        int hunger;
        int happiness;
        int energy;
        Color color;

        Pet(String name, PetType type) {
            this.name = name;
            this.type = type;
            this.hunger = 5;
            this.happiness = 5;
            this.energy = 5;
            this.color = getPetColor(type);
        }

        private Color getPetColor(PetType type) {
            return switch (type) {
                case DOG -> new Color(139, 69, 19);  // Brown
                case CAT -> Color.GRAY;
                case RABBIT -> new Color(255, 228, 196); // Bisque
                case BIRD -> new Color(30, 144, 255);   // Blue
                case FISH -> new Color(64, 224, 208);   // Turquoise
            };
        }

        void feed() {
            if (hunger >= 10) {
                throw new RuntimeException(name + " is too full!");
            }
            hunger = Math.min(10, hunger + 2);
            energy = Math.min(10, energy + 1);
        }

        void play() {
            if (energy <= 1) {
                throw new RuntimeException(name + " is too tired!");
            }
            happiness = Math.min(10, happiness + 2);
            hunger = Math.max(1, hunger - 1);
            energy = Math.max(1, energy - 2);
        }

        void sleep() {
            energy = 10; // Fully restore energy
            hunger = Math.max(1, hunger - 1);
        }

        void pet() {
            happiness = Math.min(10, happiness + 1);
        }
    }
}