import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class FastFoodGuessingGame extends JFrame {
    private JTextField userInput;
    private JLabel gameIcon;
    private JLabel feedbackLabel;
    private JButton submitButton, endGameButton, promptButton;
    private int correctGuesses = 0;
    private int totalGuesses = 0;
    private FastFoodItem currentItem;
    private JLabel imageLabel;

    public FastFoodGuessingGame() {
//Icons 

        ImageIcon icon = new ImageIcon("gameIcon.png");
        ImageIcon correctAnswerIcon = new ImageIcon("correctAnswerIcon.jpg");
        ImageIcon wrongAnswerIcon = new ImageIcon("wrongAnswerIcon.png");
//JLabels 
        JLabel gameIcon = new JLabel();
        lable.setText("Fast Food Guessing Game");
        lable.setIcon(icon);
        lable.setIcon(correctAnswerIcon);
        lable.setIcon(wrongAnswerIcon);
        this.add(lable);
        

//JFrames
JFrame frame = new JFrame();
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setSize(1024, 800);
frame.setLayout(new FlowLayout());
frame.add(gameIcon);
frame.setVisible(true); 


        setTitle("Fast Food Guessing Game");
        setSize(1024, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        currentItem = DatabaseConnection.getRandomMenuItem();
        imageLabel = new JLabel(new ImageIcon(currentItem.getImagePath()));
        add(imageLabel);


        userInput = new JTextField(10);
        add(userInput);

        // Przycisk "Submit Guess" - zamiana na RoundedButton
        submitButton = new RoundedButton("Submit Guess");
        submitButton.setBackground(Color.GREEN); // Kolor tła dla przycisku

        // Przycisk "End Game" - zamiana na RoundedButton
        endGameButton = new RoundedButton("End Game");
        endGameButton.setBackground(Color.RED); // Inny kolor tła

        // Przycisk "Prompt" - zamiana na RoundedButton
        promptButton = new RoundedButton("Prompt");
        promptButton.setBackground(Color.LIGHT_GRAY); // Inny kolor tła

        // Etykieta feedback
        feedbackLabel = new JLabel("");
        add(submitButton);
        add(endGameButton);
        add(promptButton);
        add(feedbackLabel);

        // Dodanie nasłuchiwaczy akcji do przycisków
        submitButton.addActionListener(e -> handleGuess());
        endGameButton.addActionListener(e -> endGame());
        promptButton.addActionListener(e -> showPrompt());
    }

    private void handleGuess() {
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
        imageLabel.setIcon(new ImageIcon(currentItem.getImagePath()));
        userInput.setText("");
        if (currentItem != null) {
            String imagePath = "src/images/" + currentItem.getImagePath();
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image image = imageIcon.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);
        } else {
            JOptionPane.showMessageDialog(this, "No data found! ");
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



    private void initializeFrame() {
        setVisible(true);
    }

//Buttons
    class RoundedButton extends JButton implements ActionListener {
submitButton.addActionListener(this);
endGameButton.addActionListener(this);
promptButton.addActionListener(this);

submitButton.setFocusable(false);
endGameButton.setFocusable(false);
promptButton.setFocusable(false);

submitButton.setFont(new Font("SansSerif", Font.BOLD, 20));
endGameButton.setFont(new Font("SansSerif", Font.BOLD, 20));
promptButton.setFont(new Font("SansSerif", Font.BOLD, 20));

submitButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
endGameButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
promptButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));




    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == submitButton){
            handleGuess();
        }else if(e.getSource() == endGameButton){
            endGame();
        }else if(e.getSource() == promptButton){
            showPrompt();
        }

        if(e.getSource() == submitButton && answer == true){
            feedbackLabel.setText("That's right! Keep going!");
        lable.setVisible(true);
            handleGuess();
    }else if (e.getSource() == submitButton && answer == false){
        feedbackLabel.setText("Try again! You can do it!");
        lable.setVisible(true);
        handleGuess();
    }
    }

        public RoundedButton(String label) {
            super(label);
            setContentAreaFilled(false); // Wyłączenie domyślnego wypełnienia tła
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Rysowanie zaokrąglonego tła
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

            // Rysowanie tekstu
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Połączenie z bazą danych powiodło się!");
            } else {
                System.out.println("Błąd połączenia z bazą danych.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            FastFoodGuessingGame game = new FastFoodGuessingGame();
            game.initializeFrame();
        });
    }



}
