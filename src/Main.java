import pets.*;
import logic.*;
import exceptions.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.*;
import javax.swing.border.Border;
import java.util.Random;

public class Main extends JFrame {
    private Pet currentPet;
    private GameEngine gameEngine;
    private JPanel petImagePanel;
    private JLabel petImageLabel;
    private JLabel petNameLabel;
    private JProgressBar hungerBar, happinessBar, energyBar;
    private JTextArea messageArea;
    private String currentPetName;
    private Color[] gradientColors = {
            new Color(255, 102, 102),  // Red
            new Color(255, 178, 102),  // Orange
            new Color(255, 255, 102),  // Yellow
            new Color(102, 255, 102),  // Green
            new Color(102, 178, 255),  // Blue
            new Color(178, 102, 255)   // Purple
    };

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new Main().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Main() {
        setupUI();
        initializeGame();
    }

    private void initializeGame() {
        this.gameEngine = new GameEngine();
        createPet(Pet.PetType.FISH, "Talking Fish");
    }

    private void setupUI() {
        setTitle("Virtual Pet Simulator");
        setSize(800, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Use a gradient background for the entire frame
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(230, 240, 255),
                        0, getHeight(), new Color(210, 230, 255));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        });
        getContentPane().setLayout(new BorderLayout());

        // Title panel with gradient background
        JPanel titlePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(100, 149, 237),
                        getWidth(), 0, new Color(70, 130, 220));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        titlePanel.setPreferredSize(new Dimension(getWidth(), 60));
        titlePanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Virtual Pet Simulator", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Add animated icons to the title bar
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        iconPanel.setOpaque(false);
        String[] emojis = {"🐶", "🐱", "🐰", "🐦", "🐠"};
        for (String emoji : emojis) {
            JLabel emojiLabel = new JLabel(emoji);
            emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 24));
            iconPanel.add(emojiLabel);
        }
        titlePanel.add(iconPanel, BorderLayout.EAST);

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Create pet image panel with rounded border
        petImagePanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(255, 255, 255),
                        0, getHeight(), new Color(240, 240, 255));
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                // Add subtle decorative elements
                g2d.setColor(new Color(200, 220, 255, 50));
                g2d.fillOval(-50, -50, 150, 150);
                g2d.fillOval(getWidth() - 100, getHeight() - 100, 150, 150);
            }
        };
        petImagePanel.setOpaque(false);
        petImagePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        petImageLabel = new JLabel();
        petImageLabel.setHorizontalAlignment(JLabel.CENTER);

        petNameLabel = new JLabel("", JLabel.CENTER);
        petNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        petNameLabel.setForeground(new Color(60, 60, 90));

        petImagePanel.add(petImageLabel, BorderLayout.CENTER);
        petImagePanel.add(petNameLabel, BorderLayout.SOUTH);

        // Create status panel (right side)
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        statusPanel.setBorder(createRoundedTitledBorder("Pet Status", new Color(100, 149, 237)));
        statusPanel.setOpaque(false);

        // Create status bars with colorful design
        JPanel hungerPanel = createStatPanel("Hunger", new Color(255, 180, 0)); // Orange
        hungerBar = (JProgressBar) hungerPanel.getComponent(1);
        hungerBar.setValue(40);
        hungerBar.setForeground(new Color(255, 180, 0));
        hungerPanel.add(new JLabel("40%"), BorderLayout.EAST);

        JPanel happinessPanel = createStatPanel("Happiness", new Color(50, 205, 50)); // Green
        happinessBar = (JProgressBar) happinessPanel.getComponent(1);
        happinessBar.setValue(100);
        happinessBar.setForeground(new Color(50, 205, 50));
        happinessPanel.add(new JLabel("100%"), BorderLayout.EAST);

        JPanel energyPanel = createStatPanel("Energy", new Color(30, 144, 255)); // Blue
        energyBar = (JProgressBar) energyPanel.getComponent(1);
        energyBar.setValue(100);
        energyBar.setForeground(new Color(30, 144, 255));
        energyPanel.add(new JLabel("100%"), BorderLayout.EAST);

        statusPanel.add(Box.createVerticalStrut(10));
        statusPanel.add(hungerPanel);
        statusPanel.add(Box.createVerticalStrut(10));
        statusPanel.add(happinessPanel);
        statusPanel.add(Box.createVerticalStrut(10));
        statusPanel.add(energyPanel);
        statusPanel.add(Box.createVerticalStrut(10));

        // Create pet selector panel with colorful buttons
        JPanel petSelectorPanel = new JPanel();
        petSelectorPanel.setLayout(new BoxLayout(petSelectorPanel, BoxLayout.Y_AXIS));
        petSelectorPanel.setBorder(createRoundedTitledBorder("Choose Pet", new Color(178, 102, 255)));
        petSelectorPanel.setOpaque(false);

        String[] petTypes = {"DOG", "CAT", "RABBIT", "BIRD", "FISH"};
        Color[] petColors = {
                new Color(240, 183, 107), // Dog - Golden
                new Color(240, 240, 240), // Cat - White-ish
                new Color(240, 210, 230), // Rabbit - Pink
                new Color(65, 171, 242),  // Bird - Blue
                new Color(0, 183, 235)    // Fish - Turquoise
        };

        for (int i = 0; i < petTypes.length; i++) {
            JButton petButton = createColorfulButton(petTypes[i], petColors[i],
                    Color.WHITE, petTypes[i].toLowerCase() + ".png");
            petButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            petButton.setMaximumSize(new Dimension(150, 40));
            final String petType = petTypes[i];
            petButton.addActionListener(e -> handlePetSelection(petType));
            petSelectorPanel.add(petButton);
            petSelectorPanel.add(Box.createVerticalStrut(8));
        }

        // Add status and selector to right panel
        JPanel rightPanel = new JPanel(new BorderLayout(0, 15));
        rightPanel.setOpaque(false);
        rightPanel.add(statusPanel, BorderLayout.NORTH);
        rightPanel.add(petSelectorPanel, BorderLayout.CENTER);

        // Create colorful action buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton feedButton = createColorfulButton("Feed", new Color(255, 180, 0), Color.WHITE, "food.png");
        JButton playButton = createColorfulButton("Play", new Color(50, 205, 50), Color.WHITE, "toy.png");
        JButton sleepButton = createColorfulButton("Sleep", new Color(30, 144, 255), Color.WHITE, "sleep.png");
        JButton petButton = createColorfulButton("Pet", new Color(255, 105, 180), Color.WHITE, "pet.png");
        JButton cleanButton = createColorfulButton("Clean", new Color(102, 178, 255), Color.WHITE, "clean.png");
        JButton trainButton = createColorfulButton("Train", new Color(178, 102, 255), Color.WHITE, "train.png");

        buttonPanel.add(feedButton);
        buttonPanel.add(playButton);
        buttonPanel.add(sleepButton);
        buttonPanel.add(petButton);
        buttonPanel.add(cleanButton);
        buttonPanel.add(trainButton);

        // Create message area with custom background
        messageArea = new JTextArea(5, 40);
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 14));
        messageArea.setBackground(new Color(245, 245, 255));
        messageArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane messageScroll = new JScrollPane(messageArea);
        messageScroll.setBorder(createRoundedTitledBorder("Messages", new Color(100, 149, 237)));
        messageScroll.setOpaque(false);
        messageScroll.getViewport().setOpaque(false);

        // Add action listeners
        feedButton.addActionListener(e -> handleFeed());
        playButton.addActionListener(e -> handlePlay());
        sleepButton.addActionListener(e -> handleSleep());
        petButton.addActionListener(e -> handleSpecial());
        cleanButton.addActionListener(e -> handleClean());
        trainButton.addActionListener(e -> handleTrain());

        // Add components to content panel
        contentPanel.add(petImagePanel, BorderLayout.CENTER);
        contentPanel.add(rightPanel, BorderLayout.EAST);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add all components to frame
        getContentPane().add(titlePanel, BorderLayout.NORTH);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(messageScroll, BorderLayout.SOUTH);

        // Set frame to be centered on screen
        setLocationRelativeTo(null);
    }

    private JPanel createStatPanel(String label, Color color) {
        JPanel panel = new JPanel(new BorderLayout(5, 0));
        panel.setOpaque(false);

        JLabel nameLabel = new JLabel(label + ":");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        nameLabel.setForeground(color.darker());

        // Create a custom progress bar
        JProgressBar bar = new JProgressBar(0, 100) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw background
                g2d.setColor(new Color(240, 240, 240));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                // Draw filled portion
                int fillWidth = (int) ((getValue() / 100.0) * getWidth());
                GradientPaint gradient = new GradientPaint(
                        0, 0, color.brighter(),
                        fillWidth, 0, color);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, fillWidth, getHeight(), 10, 10);

                // Draw border
                g2d.setColor(color.darker());
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            }
        };
        bar.setOpaque(false);
        bar.setBorderPainted(false);
        bar.setPreferredSize(new Dimension(150, 25));

        panel.add(nameLabel, BorderLayout.WEST);
        panel.add(bar, BorderLayout.CENTER);

        return panel;
    }

    private JButton createColorfulButton(String text, Color bgColor, Color fgColor, String iconName) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Create gradient background
                GradientPaint gradient;
                if (getModel().isPressed()) {
                    gradient = new GradientPaint(
                            0, 0, bgColor.darker(),
                            0, getHeight(), bgColor);
                } else if (getModel().isRollover()) {
                    gradient = new GradientPaint(
                            0, 0, bgColor.brighter(),
                            0, getHeight(), bgColor);
                } else {
                    gradient = new GradientPaint(
                            0, 0, bgColor,
                            0, getHeight(), bgColor.darker());
                }

                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Draw subtle glow effect
                if (getModel().isRollover()) {
                    g2d.setColor(new Color(255, 255, 255, 50));
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight()/2, 15, 15);
                }

                // Draw text with shadow
                FontMetrics metrics = g2d.getFontMetrics(getFont());
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

                // Draw text shadow
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.drawString(getText(), x+1, y+1);

                // Draw text
                g2d.setColor(fgColor);
                g2d.drawString(getText(), x, y);
            }

            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += 20; // Make buttons wider
                return size;
            }
        };

        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(fgColor);

        return button;
    }

    private Border createRoundedTitledBorder(String title, Color color) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }

    private void handleFeed() {
        try {
            gameEngine.feedPet();
            updateUI();
            showMessage("• You fed " + currentPetName + ". Yum! 🍕");
        } catch (InvalidActionException e) {
            showMessage("• " + e.getMessage());
        }
    }

    private void handlePlay() {
        try {
            gameEngine.playWithPet();
            updateUI();
            showMessage("• You played with " + currentPetName + ". Fun time! 🎾");
        } catch (LowEnergyException | InvalidActionException e) {
            showMessage("• " + e.getMessage());
        }
    }

    private void handleSleep() {
        gameEngine.putPetToSleep();
        updateUI();
        showMessage("• " + currentPetName + " is sleeping. Sweet dreams! 💤");
    }

    private void handleSpecial() {
        try {
            gameEngine.performSpecialAction();
            updateUI();

            String specialAction = "";
            switch (currentPet.getType()) {
                case DOG -> specialAction = "You pet " + currentPetName + ". Tail wagging! 🐕";
                case CAT -> specialAction = "You pet " + currentPetName + ". Purr purr! 🐱";
                case RABBIT -> specialAction = "You pet " + currentPetName + ". Nose twitching! 🐇";
                case BIRD -> specialAction = "You pet " + currentPetName + ". Happy chirping! 🐦";
                case FISH -> specialAction = "You tap the tank and " + currentPetName + " swims excitedly! 🐠";
            }

            showMessage("• " + specialAction);
        } catch (LowEnergyException e) {
            showMessage("• " + e.getMessage());
        }
    }

    private void handleClean() {
        showMessage("• You cleaned " + currentPetName + "'s area. So fresh and clean! ✨");
        // Add any additional logic for cleaning
    }

    private void handleTrain() {
        try {
            if (currentPet.getEnergy() < 4) {
                throw new LowEnergyException(currentPetName + " is too tired to train! 💤");
            }

            String[] tricks = {"sit", "roll over", "high five", "spin", "jump"};
            Random rand = new Random();
            String trick = tricks[rand.nextInt(tricks.length)];

            showMessage("• You trained " + currentPetName + " to " + trick + "! Good job! 🏆");

            // Reduce energy and increase happiness
            currentPet.energy = Math.max(0, currentPet.energy - 3);
            currentPet.happiness = Math.min(10, currentPet.happiness + 2);
            updateUI();

        } catch (LowEnergyException e) {
            showMessage("• " + e.getMessage());
        }
    }

    private void handlePetSelection(String petType) {
        // Convert to title case for display
        String name = petType.charAt(0) + petType.substring(1).toLowerCase();
        if (petType.equals("FISH")) {
            name = "Talking Fish";
        }
        createPet(Pet.PetType.valueOf(petType), name);
    }

    private void createPet(Pet.PetType type, String name) {
        // Create the pet based on type
        switch (type) {
            case DOG -> currentPet = new Dog(name);
            case CAT -> currentPet = new Cat(name);
            case RABBIT -> currentPet = new Rabbit(name);
            case BIRD -> currentPet = new Bird(name);
            case FISH -> currentPet = new Fish(name);
        }

        // Set the current pet name for display
        currentPetName = name;

        // Update image and name label
        ImageIcon petIcon = getPetImage(type);
        petImageLabel.setIcon(petIcon);
        petNameLabel.setText(name);

        // Update game engine
        gameEngine.setCurrentPet(currentPet);
        updateUI();
        showMessage("• Welcome to your new " + type.toString().toLowerCase() + ", " + name + "! ❤️");
    }

    private ImageIcon getPetImage(Pet.PetType type) {
        // In a real application, you would load image files from resources
        // Here we'll create colorful cartoon-style pet images
        int width = 200;
        int height = 200;

        ImageIcon icon = null;

        switch (type) {
            case DOG:
                icon = createDogIcon(width, height);
                break;
            case CAT:
                icon = createCatIcon(width, height);
                break;
            case RABBIT:
                icon = createRabbitIcon(width, height);
                break;
            case BIRD:
                icon = createBirdIcon(width, height);
                break;
            case FISH:
                icon = createFishIcon(width, height);
                break;
        }

        return icon;
    }

    private ImageIcon createDogIcon(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        setupGraphics(g2d);

        // Colors
        Color bodyColor = new Color(240, 183, 107); // Light golden
        Color earColor = new Color(194, 144, 78);  // Darker golden
        Color eyeWhite = Color.WHITE;
        Color eyeColor = new Color(102, 67, 41); // Brown

        // Body - rounded rectangle
        g2d.setPaint(bodyColor);
        g2d.fill(new RoundRectangle2D.Double(50, 80, 100, 80, 40, 40));

        // Head - circle
        g2d.fill(new Ellipse2D.Double(30, 20, 140, 110));

        // Ears
        g2d.setPaint(earColor);
        g2d.fill(new Ellipse2D.Double(20, 10, 60, 60));
        g2d.fill(new Ellipse2D.Double(120, 10, 60, 60));

        // Eyes
        g2d.setPaint(eyeWhite);
        g2d.fill(new Ellipse2D.Double(55, 55, 30, 35));
        g2d.fill(new Ellipse2D.Double(115, 55, 30, 35));

        g2d.setPaint(eyeColor);
        g2d.fill(new Ellipse2D.Double(65, 65, 15, 20));
        g2d.fill(new Ellipse2D.Double(120, 65, 15, 20));

        // Highlights in eyes (white dots)
        g2d.setPaint(Color.WHITE);
        g2d.fill(new Ellipse2D.Double(68, 68, 7, 7));
        g2d.fill(new Ellipse2D.Double(123, 68, 7, 7));

        // Nose
        g2d.setPaint(new Color(50, 50, 50));
        g2d.fill(new Ellipse2D.Double(85, 85, 30, 20));

        // Mouth - smile
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.draw(new Arc2D.Double(70, 90, 60, 30, 190, 160, Arc2D.OPEN));

        // Tongue
        g2d.setPaint(new Color(255, 150, 150));
        g2d.fill(new Ellipse2D.Double(90, 105, 20, 15));

        // Add sparkle effect
        drawSparkles(g2d, width, height);

        g2d.dispose();
        return new ImageIcon(image);
    }

    private ImageIcon createCatIcon(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        setupGraphics(g2d);

        // Colors
        Color bodyColor = new Color(240, 240, 240); // White-ish
        Color earInnerColor = new Color(255, 192, 203); // Pink
        Color eyeColor = new Color(46, 204, 113); // Bright green

        // Body
        g2d.setPaint(bodyColor);
        g2d.fill(new Ellipse2D.Double(40, 90, 120, 80)); // Body
        g2d.fill(new Ellipse2D.Double(40, 20, 120, 100)); // Head

        // Ears
        int[] xPointsLeft = {60, 40, 80};
        int[] yPointsLeft = {40, 10, 10};
        g2d.fillPolygon(xPointsLeft, yPointsLeft, 3);

        int[] xPointsRight = {140, 160, 120};
        int[] yPointsRight = {40, 10, 10};
        g2d.fillPolygon(xPointsRight, yPointsRight, 3);

        // Inner ears
        g2d.setPaint(earInnerColor);
        int[] xInnerLeft = {60, 47, 73};
        int[] yInnerLeft = {40, 15, 15};
        g2d.fillPolygon(xInnerLeft, yInnerLeft, 3);

        int[] xInnerRight = {140, 153, 127};
        int[] yInnerRight = {40, 15, 15};
        g2d.fillPolygon(xInnerRight, yInnerRight, 3);

        // Eyes
        g2d.setPaint(Color.WHITE);
        g2d.fill(new Ellipse2D.Double(60, 50, 30, 35));
        g2d.fill(new Ellipse2D.Double(110, 50, 30, 35));

        // Pupils with cat-like slits
        g2d.setPaint(eyeColor);
        g2d.fill(new Ellipse2D.Double(67, 55, 16, 25));
        g2d.fill(new Ellipse2D.Double(117, 55, 16, 25));

        g2d.setPaint(Color.BLACK);
        g2d.fill(new Rectangle2D.Double(72, 57, 6, 21));
        g2d.fill(new Rectangle2D.Double(122, 57, 6, 21));

        // Nose
        g2d.setPaint(new Color(255, 150, 150));
        int[] xNose = {95, 105, 100};
        int[] yNose = {80, 80, 90};
        g2d.fillPolygon(xNose, yNose, 3);

        // Whiskers
        g2d.setPaint(Color.BLACK);
        g2d.setStroke(new BasicStroke(1.5f));
        // Left whiskers
        g2d.draw(new Line2D.Double(80, 90, 40, 85));
        g2d.draw(new Line2D.Double(80, 95, 40, 95));
        g2d.draw(new Line2D.Double(80, 100, 40, 105));
        // Right whiskers
        g2d.draw(new Line2D.Double(120, 90, 160, 85));
        g2d.draw(new Line2D.Double(120, 95, 160, 95));
        g2d.draw(new Line2D.Double(120, 100, 160, 105));

        // Mouth
        g2d.draw(new Line2D.Double(95, 90, 90, 100));
        g2d.draw(new Line2D.Double(105, 90, 110, 100));

        // Add sparkle effect
        drawSparkles(g2d, width, height);

        g2d.dispose();
        return new ImageIcon(image);
    }

    private ImageIcon createRabbitIcon(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        setupGraphics(g2d);

        // Colors
        Color bodyColor = new Color(240, 210, 230); // Light pink
        Color earInnerColor = new Color(255, 192, 203); // Pink
        Color eyeColor = new Color(255, 50, 50); // Bright red

        // Body
        g2d.setPaint(bodyColor);
        g2d.fill(new Ellipse2D.Double(50, 90, 100, 70)); // Body
        g2d.fill(new Ellipse2D.Double(60, 40, 80, 80)); // Head

        // Feet
        g2d.fill(new Ellipse2D.Double(60, 140, 30, 20));
        // Feet
        g2d.fill(new Ellipse2D.Double(60, 140, 30, 20)); // Left foot
        g2d.fill(new Ellipse2D.Double(110, 140, 30, 20)); // Right foot

        // Ears - tall and standing
        g2d.fill(new RoundRectangle2D.Double(70, 0, 20, 60, 10, 10)); // Left ear
        g2d.fill(new RoundRectangle2D.Double(110, 0, 20, 60, 10, 10)); // Right ear

        // Inner ears
        g2d.setPaint(earInnerColor);
        g2d.fill(new RoundRectangle2D.Double(75, 5, 10, 50, 5, 5)); // Left inner ear
        g2d.fill(new RoundRectangle2D.Double(115, 5, 10, 50, 5, 5)); // Right inner ear

        // Eyes
        g2d.setPaint(Color.WHITE);
        g2d.fill(new Ellipse2D.Double(70, 60, 25, 30)); // Left eye
        g2d.fill(new Ellipse2D.Double(105, 60, 25, 30)); // Right eye

        // Pupils
        g2d.setPaint(eyeColor);
        g2d.fill(new Ellipse2D.Double(77, 65, 12, 20)); // Left pupil
        g2d.fill(new Ellipse2D.Double(111, 65, 12, 20)); // Right pupil

        // Highlights in eyes
        g2d.setPaint(Color.WHITE);
        g2d.fill(new Ellipse2D.Double(80, 68, 6, 6)); // Left highlight
        g2d.fill(new Ellipse2D.Double(114, 68, 6, 6)); // Right highlight

        // Nose
        g2d.setPaint(new Color(255, 150, 150));
        g2d.fill(new Ellipse2D.Double(88, 85, 24, 15));

        // Mouth
        g2d.setPaint(Color.BLACK);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.draw(new Line2D.Double(100, 100, 100, 110));
        g2d.draw(new Arc2D.Double(90, 105, 20, 10, 180, 180, Arc2D.OPEN));

        // Whiskers
        g2d.draw(new Line2D.Double(88, 95, 60, 90));
        g2d.draw(new Line2D.Double(88, 100, 60, 100));
        g2d.draw(new Line2D.Double(112, 95, 140, 90));
        g2d.draw(new Line2D.Double(112, 100, 140, 100));

        // Add sparkle effect
        drawSparkles(g2d, width, height);

        g2d.dispose();
        return new ImageIcon(image);
    }

    private ImageIcon createBirdIcon(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        setupGraphics(g2d);

        // Colors
        Color bodyColor = new Color(65, 171, 242); // Blue
        Color beakColor = new Color(255, 204, 0); // Yellow
        Color wingColor = new Color(40, 140, 210); // Darker blue
        Color eyeColor = Color.BLACK;

        // Body
        g2d.setPaint(bodyColor);
        g2d.fill(new Ellipse2D.Double(50, 60, 100, 80)); // Body

        // Head
        g2d.fill(new Ellipse2D.Double(120, 40, 60, 60));

        // Wing
        g2d.setPaint(wingColor);
        g2d.fill(new Ellipse2D.Double(60, 70, 70, 50));

        // Tail
        int[] xTail = {50, 20, 30};
        int[] yTail = {80, 70, 100};
        g2d.setPaint(wingColor);
        g2d.fillPolygon(xTail, yTail, 3);

        // Eyes
        g2d.setPaint(Color.WHITE);
        g2d.fill(new Ellipse2D.Double(140, 50, 20, 25));

        g2d.setPaint(eyeColor);
        g2d.fill(new Ellipse2D.Double(145, 55, 10, 15));

        // Eye highlight
        g2d.setPaint(Color.WHITE);
        g2d.fill(new Ellipse2D.Double(147, 57, 5, 5));

        // Beak
        g2d.setPaint(beakColor);
        int[] xBeak = {180, 200, 180};
        int[] yBeak = {60, 70, 80};
        g2d.fillPolygon(xBeak, yBeak, 3);

        // Feet
        g2d.setPaint(beakColor);
        g2d.setStroke(new BasicStroke(3.0f));
        g2d.draw(new Line2D.Double(95, 140, 95, 160));
        g2d.draw(new Line2D.Double(85, 160, 105, 160));
        g2d.draw(new Line2D.Double(115, 140, 115, 160));
        g2d.draw(new Line2D.Double(105, 160, 125, 160));

        // Add sparkle effect
        drawSparkles(g2d, width, height);

        g2d.dispose();
        return new ImageIcon(image);
    }

    private ImageIcon createFishIcon(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        setupGraphics(g2d);

        // Colors
        Color bodyColor = new Color(0, 183, 235); // Turquoise
        Color finColor = new Color(0, 150, 200); // Darker blue
        Color eyeColor = Color.BLACK;

        // Draw water background with bubbles
        g2d.setPaint(new Color(200, 240, 255, 100));
        g2d.fill(new RoundRectangle2D.Double(10, 10, width - 20, height - 20, 20, 20));

        // Bubbles
        g2d.setPaint(new Color(255, 255, 255, 150));
        g2d.fill(new Ellipse2D.Double(30, 30, 15, 15));
        g2d.fill(new Ellipse2D.Double(60, 20, 10, 10));
        g2d.fill(new Ellipse2D.Double(150, 40, 20, 20));
        g2d.fill(new Ellipse2D.Double(170, 70, 15, 15));

        // Fish body
        g2d.setPaint(bodyColor);
        Ellipse2D fishBody = new Ellipse2D.Double(60, 70, 100, 60);
        g2d.fill(fishBody);

        // Tail
        int[] xTail = {60, 30, 60};
        int[] yTail = {80, 100, 120};
        g2d.setPaint(finColor);
        g2d.fillPolygon(xTail, yTail, 3);

        // Top fin
        int[] xTopFin = {100, 120, 140};
        int[] yTopFin = {70, 40, 70};
        g2d.fillPolygon(xTopFin, yTopFin, 3);

        // Bottom fin
        int[] xBottomFin = {100, 120, 140};
        int[] yBottomFin = {130, 160, 130};
        g2d.fillPolygon(xBottomFin, yBottomFin, 3);

        // Eye
        g2d.setPaint(Color.WHITE);
        g2d.fill(new Ellipse2D.Double(140, 85, 20, 20));

        g2d.setPaint(eyeColor);
        g2d.fill(new Ellipse2D.Double(145, 90, 10, 10));

        // Eye highlight
        g2d.setPaint(Color.WHITE);
        g2d.fill(new Ellipse2D.Double(147, 92, 4, 4));

        // Mouth
        g2d.setPaint(Color.BLACK);
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.draw(new Arc2D.Double(150, 100, 15, 10, 180, 180, Arc2D.OPEN));

        // Gill
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.draw(new Arc2D.Double(130, 80, 10, 40, 90, 180, Arc2D.OPEN));

        // Add sparkle effect
        drawSparkles(g2d, width, height);

        g2d.dispose();
        return new ImageIcon(image);
    }

    private void setupGraphics(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    }

    private void drawSparkles(Graphics2D g2d, int width, int height) {
        // Add some sparkle effects
        Random rand = new Random();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));

        for (int i = 0; i < 10; i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            int size = rand.nextInt(5) + 2;

            g2d.setPaint(new Color(255, 255, 255, 150));
            g2d.fill(new Ellipse2D.Double(x, y, size, size));
        }
    }

    private void updateUI() {
        if (currentPet != null) {
            hungerBar.setValue(currentPet.getHunger() * 10);
            happinessBar.setValue(currentPet.getHappiness() * 10);
            energyBar.setValue(currentPet.getEnergy() * 10);

            // Update the numeric values next to bars
            JPanel hungerPanel = (JPanel) hungerBar.getParent();
            ((JLabel)hungerPanel.getComponent(2)).setText(hungerBar.getValue() + "%");

            JPanel happinessPanel = (JPanel) happinessBar.getParent();
            ((JLabel)happinessPanel.getComponent(2)).setText(happinessBar.getValue() + "%");

            JPanel energyPanel = (JPanel) energyBar.getParent();
            ((JLabel)energyPanel.getComponent(2)).setText(energyBar.getValue() + "%");
        }
    }

    private void showMessage(String message) {
        messageArea.append(message + "\n");
        // Scroll to the bottom
        messageArea.setCaretPosition(messageArea.getDocument().getLength());
    }
}