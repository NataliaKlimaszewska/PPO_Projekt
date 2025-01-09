import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class FastFoodGuessingGame extends JFrame implements ActionListener {
    private JTextField userInput;
    private JLabel feedbackLabel, imageLabel;
    private JButton submitButton, endGameButton, promptButton;
    private int correctGuesses = 0;
    private int totalGuesses = 0;
    private FastFoodItem currentItem;

    public FastFoodGuessingGame() {
        setTitle("Fast Food Guessing Game");
        setSize(1024, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Initialize components
        userInput = new JTextField();
        userInput.setPreferredSize(new Dimension(200, 30));
        add(userInput);

        submitButton = new RoundedButton("Submit Guess");
        submitButton.setFocusable(false);
        submitButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        submitButton.setBackground(Color.GREEN);
        submitButton.addActionListener(e -> handleGuess());
        add(submitButton);

        endGameButton = new RoundedButton("End Game");
        endGameButton.setFocusable(false);
        endGameButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        endGameButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        endGameButton.setBackground(Color.RED);
        endGameButton.addActionListener(e -> endGame());
        add(endGameButton);

        promptButton = new RoundedButton("Prompt");
        promptButton.setFocusable(false);
        promptButton.setFont(new Font("SansSerif", Font.BOLD, 20));
        promptButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        promptButton.setBackground(Color.LIGHT_GRAY);
        promptButton.addActionListener(e -> showPrompt());
        add(promptButton);

        feedbackLabel = new JLabel("");
        add(feedbackLabel);

        imageLabel = new JLabel();
        add(imageLabel);

        setVisible(true);
    }

    private void handleGuess() {
        if (currentItem == null) {
            feedbackLabel.setForeground(Color.RED);
            feedbackLabel.setText("No item to guess. Please reload the game.");
            return;
        }

        try {
            int userGuess = Integer.parseInt(userInput.getText());
            int actualCalories = currentItem.getCalories();
            totalGuesses++;

            if (Math.abs(userGuess - actualCalories) <= 80) {
                feedbackLabel.setForeground(Color.GREEN);
                feedbackLabel.setText(
                        "You guessed correctly! Fat: " + currentItem.fat + "g, Carbs: " + currentItem.carbs + "g, Protein: " + currentItem.protein + "g, Calories: " + actualCalories);
                correctGuesses++;
                loadNextItem();
            } else {
                feedbackLabel.setForeground(Color.RED);
                feedbackLabel.setText("Not Correct. Fat: " + currentItem.fat + "g, Carbs: " + currentItem.carbs + "g, Calories: " + actualCalories);
            }
        } catch (NumberFormatException ex) {
            feedbackLabel.setForeground(Color.RED);
            feedbackLabel.setText("Please enter a valid number.");
        }
    }

    private void loadNextItem() {
        currentItem = DatabaseConnection.getRandomMenuItem();
        if (currentItem != null) {
            String imagePath = "src/images/" + currentItem.getImagePath();
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image image = imageIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);
        } else {
            JOptionPane.showMessageDialog(this, "No data found!");
        }
        userInput.setText("");
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

    private void initializeFrame() {
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    class RoundedButton extends JButton implements ActionListener {
        public RoundedButton(String label){
            super(label);
            setContentAreaFilled(false);
            setOpaque(false);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == submitButton){
                handleGuess();
            } else if(e.getSource() == endGameButton){
                endGame();
            } else if(e.getSource() == promptButton){
                showPrompt();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Successfully Connected to Database!");
            } else {
                System.out.println("Error while connecting to Database.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to Database.");
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            FastFoodGuessingGame game = new FastFoodGuessingGame();
            game.initializeFrame();
        });
    }
}
