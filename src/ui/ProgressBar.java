package ui;
import javax.swing.*;
import java.awt.*;

public class ProgressBar extends JPanel {
    private int value;
    private String label;
    private Color fillColor;

    public ProgressBar(String label, Color fillColor) {
        this.label = label;
        this.fillColor = fillColor;
        setPreferredSize(new Dimension(150, 25));
    }

    public void setValue(int value) {
        this.value = Math.max(0, Math.min(10, value));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(240, 240, 240));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        int fillWidth = (int) ((value / 10.0) * getWidth());
        g2d.setColor(fillColor);
        g2d.fillRoundRect(0, 0, fillWidth, getHeight(), 10, 10);
        g2d.setColor(Color.GRAY);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
        g2d.setColor(Color.BLACK);
        FontMetrics fm = g2d.getFontMetrics();
        String text = label + ": " + value + "/10";
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        g2d.drawString(text, (getWidth() - textWidth) / 2, (getHeight() + textHeight / 2) / 2);
    }
}
