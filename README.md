Virtual Pet Stimulator 🎮 🐶🐱🐰🐦🐠
```markdown
A colorful, interactive virtual pet simulator built with Java Swing where you can adopt and care for different pets! 🐶🐱🐰🐦🐠

## ✨ Features

- 🏠 **Adopt multiple pets**: Choose from 5 different pets (Dog, Cat, Rabbit, Bird, Fish)
- 🍕 **Feed your pet**: Keep their hunger levels low
- 🎾 **Play with your pet**: Increase their happiness
- 💤 **Put them to sleep**: Restore their energy
- ✨ **Special actions**: Unique interactions for each pet type
- 📊 **Status tracking**: Monitor hunger, happiness, and energy levels
- 🎨 **Beautiful UI**: Colorful gradients, rounded corners, and smooth animations
- 🔊 **Sound effects**: (Coming soon!)

## 🛠️ Technologies Used

- **Java 17**
- **Java Swing** for GUI
- **Maven** for dependency management
- **FlatLaf** for modern look and feel (optional)

## 📦 Project Structure

src/
├── exceptions/       # Custom exceptions
│   ├── InvalidActionException.java
│   ├── LowEnergyException.java
│   └── OutOfBoundsException.java
├── logic/            # Game logic and event handling
│   ├── GameEngine.java
│   └── EventManager.java
├── pets/             # Pet classes
│   ├── Pet.java (abstract base)
│   ├── Dog.java
│   ├── Cat.java
│   ├── Rabbit.java
│   ├── Bird.java
│   └── Fish.java
├── ui/               # Custom UI components
│   ├── RoundButton.java
│   ├── ProgressBar.java
│   └── PetDisplayPanel.java
└── Main.java         # Main application class
```

## 🚀 How to Run

1. **Prerequisites**:
   - JDK 17 or later installed
   - Maven installed (optional)

2. **Running the application**:
   ```bash
   # Clone the repository
   git clone https://github.com/yourusername/VirtualPetSimulator.git
   cd virtual-pet-simulator
   
   # Compile and run
   javac -d out src/*.java src/**/*.java
   java -cp out Main
   
   # Or with Maven
   mvn clean compile exec:java
   ```

## 🎮 How to Play

1. **Choose a pet** from the selection panel
2. **Name your pet** when prompted
3. **Interact** using the buttons:
   - 🍕 Feed - Reduces hunger
   - 🎾 Play - Increases happiness (costs energy)
   - 💤 Sleep - Restores full energy
   - ✨ Special - Unique action for each pet
   - 🧹 Clean - Keep your pet's environment clean
   - 🏆 Train - Teach your pet new tricks

4. **Monitor stats** in the status panel
5. **Messages** will appear in the log area


## 👥 Team

- **Ubaid Raza Dar** - Core OOP Logic & Project Overview
- **Maheen Khadim** - GUI Development (Swing & FlatLaf)
- **Fariya Zainab** - Event Handling & Pet Interactions
- **Burhan** - Exception Handling & Application Stability
- **Subhan Abrar** - Additional Features & Enhancements

## 🤝 Contributing

Contributions are welcome! Here's how:

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📜 License

Distributed under the [MIT License](LICENSE).

## 📧 Contact

Project Link: [https://github.com/maheen-736/VirtualPetStimulator](https://github.com/maheen-736/VirtualPetStimulator.git)

---

Made with ❤️ and Java ☕ by Team Virtual Pets
```


