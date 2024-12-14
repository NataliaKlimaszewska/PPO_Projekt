import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FastFoodGuessingGame extends JFrame {
    private JTextField userInput;
    private JLabel feedbackLabel;
    private JButton submitButton, endGameButton;
    private int correctGuesses = 0;
    private int totalGuesses = 0;

    public FastFoodGuessingGame() {
        setTitle("Fast Food Guessing Game");
        setSize(1024, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel imageLabel = new JLabel(new ImageIcon("path/to/mcdonald_dish.jpg"));
        add(imageLabel);

        userInput = new JTextField(10);
        add(userInput);

        submitButton = new JButton("Submit Guess");
        endGameButton = new JButton("End Game");
        feedbackLabel = new JLabel("");

        add(submitButton);
        add(endGameButton);
        add(feedbackLabel);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });

        endGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                endGame();
            }
        });
    }

    private void handleGuess() {
        try {
            int userGuess = Integer.parseInt(userInput.getText());
            int actualCalories = 250; // Example value, should be fetched from database
            totalGuesses++;

            if (Math.abs(userGuess - actualCalories) <= 80) {
                feedbackLabel.setForeground(Color.GREEN);
                feedbackLabel.setText("You guessed correctly! Fat: 10g, Carbs: 30g, Protein: 15g, Calories: " + actualCalories);
                correctGuesses++;
            } else if (Math.abs(userGuess - actualCalories) > 100) {
                feedbackLabel.setForeground(Color.RED);
                feedbackLabel.setText("Not Correct. Fat: 10g, Carbs: 30g, Calories: " + actualCalories);
            }
        } catch (NumberFormatException ex) {
            feedbackLabel.setForeground(Color.RED);
            feedbackLabel.setText("Please enter a valid number.");
        }
    }

    private void endGame() {
        JOptionPane.showMessageDialog(this, "You guessed correctly " + correctGuesses + " out of " + totalGuesses + " times.");
        // Reset game logic here
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FastFoodGuessingGame game = new FastFoodGuessingGame();
            game.setVisible(true);
        });
    }
}