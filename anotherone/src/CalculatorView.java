import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CalculatorView {
    private JFrame frame;
    private JTextField textField;
    private JButton[] buttons;

    public CalculatorView() {
        frame = new JFrame("Project Gurukul's Scientific Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(300, 40));
        frame.add(textField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(9, 4));
        buttonPanel.setBackground(Color.ORANGE);

        String[] buttonLabels = {
                "1", "2", "3", "/", "4", "5", "6", "*", "7", "8", "9", "-",
                "0", ".", "=", "+", "Clear", "(", ")", "^", "sqrt", "cbrt",
                "log", "sin", "cos", "tan", "asin", "acos", "atan", "!", "%", "|x|"
        };

        buttons = new JButton[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new JButton(buttonLabels[i]);
            buttonPanel.add(buttons[i]);
        }

        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.pack();
    }

    public void show() {
        frame.setVisible(true);
    }

    public String getExpression() {
        return textField.getText();
    }

    public void setExpression(String expression) {
        textField.setText(expression);
    }

    public void addCalculationListener(ActionListener listener) {
        for (JButton button : buttons) {
            button.addActionListener(listener);
        }
    }

    public void displayError(String errorMessage) {
        textField.setText(errorMessage);
    }
}