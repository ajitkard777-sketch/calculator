import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleCalculator extends JFrame implements ActionListener {
    private final JTextField display;
    private double firstNum = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public SimpleCalculator() {
        setTitle("Simple Calculator - Ajit Karad (IT2303)");
        setSize(340, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(241, 245, 249));

        display = new JTextField("0");
        display.setFont(new Font("Consolas", Font.BOLD, 28));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.WHITE);
        display.setForeground(new Color(15, 23, 42));
        display.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 8, 8));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPanel.setBackground(new Color(241, 245, 249));

        String[] buttons = {
            "C", "<-", "/", "*",
            "7", "8",  "9", "-",
            "4", "5",  "6", "+",
            "1", "2",  "3", "=",
            "0", ".",  "%", "00"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
            btn.setFocusPainted(false);
            if (text.equals("=")) {
                btn.setBackground(new Color(37, 99, 235));
                btn.setForeground(Color.WHITE);
            } else if ("+-*/%".contains(text)) {
                btn.setBackground(new Color(226, 232, 240));
                btn.setForeground(new Color(30, 58, 138));
            } else if (text.equals("C") || text.equals("<-")) {
                btn.setBackground(new Color(254, 226, 226));
                btn.setForeground(new Color(185, 28, 28));
            } else {
                btn.setBackground(Color.WHITE);
                btn.setForeground(new Color(15, 23, 42));
            }
            btn.addActionListener(this);
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.CENTER);
        System.out.println("[INFO] Simple Calculator GUI launched (Ajit Karad | IT2303).");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if ("0123456789.".contains(cmd) || cmd.equals("00")) {
            if (startNewNumber || display.getText().startsWith("Error")) {
                display.setText(cmd.equals(".") ? "0." : (cmd.equals("00") ? "0" : cmd));
                startNewNumber = false;
            } else if (!(cmd.equals(".") && display.getText().contains("."))) {
                display.setText(display.getText() + cmd);
            }
        } else if (cmd.equals("C")) {
            display.setText("0");
            firstNum = 0;
            operator = "";
            startNewNumber = true;
            System.out.println("[INFO] Display cleared.");
        } else if (cmd.equals("<-")) {
            String cur = display.getText();
            if (!startNewNumber && cur.length() > 1) {
                display.setText(cur.substring(0, cur.length() - 1));
            } else {
                display.setText("0");
                startNewNumber = true;
            }
        } else if ("+-*/%".contains(cmd)) {
            firstNum = Double.parseDouble(display.getText());
            operator = cmd;
            startNewNumber = true;
        } else if (cmd.equals("=")) {
            if (operator.isEmpty()) return;
            double secondNum = Double.parseDouble(display.getText());
            double result = 0;
            if (operator.equals("/") && secondNum == 0) {
                display.setText("Error: Div by 0");
                System.out.println("[WARN] Division by zero prevented (" + firstNum + " / 0).");
                operator = "";
                startNewNumber = true;
                return;
            }
            switch (operator) {
                case "+": result = firstNum + secondNum; break;
                case "-": result = firstNum - secondNum; break;
                case "*": result = firstNum * secondNum; break;
                case "/": result = firstNum / secondNum; break;
                case "%": result = firstNum % secondNum; break;
            }
            String outStr = (result == (long) result) ? String.valueOf((long) result) : String.valueOf(result);
            display.setText(outStr);
            System.out.println("[CALC] " + firstNum + " " + operator + " " + secondNum + " = " + outStr);
            operator = "";
            startNewNumber = true;
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("       GUI SIMPLE CALCULATOR (JAVA SWING)");
        System.out.println("==================================================");
        System.out.println("Name: Ajit Karad | Roll No.: IT2303 | Div: SY-C");
        SwingUtilities.invokeLater(() -> new SimpleCalculator().setVisible(true));
    }
}