
import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;

public class SimpleCalculator extends JFrame {
    private JTextField displayField;
    private String currentInput = "";
    private double result = 0;
    private String lastOperator = "";

    public SimpleCalculator() {
        super("Simple Calculator");
        setLayout(new BorderLayout());

        displayField = new JTextField();
        displayField.setFont(new Font("Arial", Font.PLAIN, 20));
        displayField.setEditable(false);
        add(displayField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 5, 5));
        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.equals("=")) {
                calculateResult();
            } else if (command.matches("[0-9.]")) {
                currentInput += command;
                updateDisplay();
            } else {
                handleOperator(command);
            }
        }

        private void calculateResult() {
            double secondOperand = Double.parseDouble(currentInput);
            switch (lastOperator) {
                case "+":
                    result += secondOperand;
                    break;
                case "-":
                    result -= secondOperand;
                    break;
                case "*":
                    result *= secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result /= secondOperand;
                    } else {
                        currentInput = "";
                        result = 0;
                        updateDisplay();
                        JOptionPane.showMessageDialog(
                                null, "Error: Division by zero!", "Error", JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    break;
            }
            currentInput = "";
            lastOperator = "";
            updateDisplay();
        }

        private void handleOperator(String operator) {
            if (!currentInput.isEmpty()) {
                if (result == 0) {
                    result = Double.parseDouble(currentInput);
                } else {
                    calculateResult();
                }
                currentInput = "";
            }
            lastOperator = operator;
        }

        private void updateDisplay() {
            displayField.setText(currentInput.isEmpty() ? String.valueOf(result) : currentInput);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SimpleCalculator::new);
    }
}
