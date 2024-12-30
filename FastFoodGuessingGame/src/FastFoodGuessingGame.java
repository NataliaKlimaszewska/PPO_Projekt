import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FastFoodGuessingGame extends JFrame {
    private JTextField userInput;
    private JLabel feedbackLabel;
    private JButton submitButton, endGameButton, promptButton;
    private int correctGuesses = 0;
    private int totalGuesses = 0;

    public FastFoodGuessingGame() {
        setTitle("Fast Food Guessing Game");
        setSize(1024, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Adding an image to the game
        JLabel imageLabel = new JLabel(new ImageIcon("path/to/mcdonald_dish.jpg"));
        add(imageLabel);

        // Input field for user's guess
        userInput = new JTextField(10);
        add(userInput);

        // Buttons for actions
        submitButton = new JButton("Submit Guess");
        endGameButton = new JButton("End Game");
        promptButton = new JButton("Prompt");
        feedbackLabel = new JLabel("");

        add(submitButton);
        add(endGameButton);
        add(promptButton);
        add(feedbackLabel);

        // Adding action listeners to buttons
        submitButton.addActionListener(e -> handleGuess());
        endGameButton.addActionListener(e -> endGame());
        promptButton.addActionListener(e -> showPrompt());
    }

    private void handleGuess() {
        try {
            int userGuess = Integer.parseInt(userInput.getText());
            int actualCalories = 250; // Example value, should be fetched from database
            totalGuesses++;

            if (Math.abs(userGuess - actualCalories) <= 80) {
                feedbackLabel.setForeground(Color.GREEN);
                feedbackLabel.setText(
                        "You guessed correctly! Fat: 10g, Carbs: 30g, Protein: 15g, Calories: " + actualCalories);
                correctGuesses++;
            } else {
                feedbackLabel.setForeground(Color.RED);
                feedbackLabel.setText("Not Correct. Fat: 10g, Carbs: 30g, Calories: " + actualCalories);
            }
        } catch (NumberFormatException ex) {
            feedbackLabel.setForeground(Color.RED);
            feedbackLabel.setText("Please enter a valid number.");
        }
    }

    private void endGame() {
        JOptionPane.showMessageDialog(this,
                "You guessed correctly " + correctGuesses + " out of " + totalGuesses + " times.");
        resetGame();
    }

    private void resetGame() {
        correctGuesses = 0;
        totalGuesses = 0;
        feedbackLabel.setText("");
        userInput.setText("");
    }

    private void showPrompt() {
        feedbackLabel.setForeground(Color.BLUE);
        feedbackLabel.setText("Try guessing within 80 calories range!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FastFoodGuessingGame game = new FastFoodGuessingGame();
            game.initializeFrame();
        });
    }

    private void initializeFrame() {
        setVisible(true);
    }

    // Optional: Panel for displaying image (if needed for more advanced
    // customization)
    public class ImagePanel extends JPanel {
        private Image image;

        public ImagePanel() {
            super();
            File imageFile = new File("path/to/mcdonald_dish.jpg");
            try {
                image = ImageIO.read(imageFile);
            } catch (IOException e) {
                System.err.println("Error. Can't open image file.");
                e.printStackTrace();
            }
            Dimension size = new Dimension(image.getWidth(this), image.getHeight(this));
            setPreferredSize(size);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }
}
