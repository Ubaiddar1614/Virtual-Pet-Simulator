package logic;
import javax.swing.*;
        import java.awt.event.*;

public class EventManager {
    public static void addActionListener(JButton button, ActionListener listener) {
        button.addActionListener(listener);
    }

    public static void setupPetSelection(JComboBox<String> comboBox, PetSelectionCallback callback) {
        comboBox.addActionListener(e -> {
            if (comboBox.getSelectedItem() != null) {
                callback.onPetSelected(comboBox.getSelectedItem().toString());
            }
        });
    }

    public interface PetSelectionCallback {
        void onPetSelected(String petType);
    }
}