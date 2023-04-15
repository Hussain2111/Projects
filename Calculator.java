import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Calculator extends JFrame implements ActionListener{

    JTextField textfield;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[6];
    JButton Add,Sub,Mul,Div,Equal,AC;
    JPanel panel;
    
    int num1=0,num2=0,result=0;
    char operator;
    
    public Calculator(){
        makeFrame();
        makeButtons();
        addButtons();
        
    }
        
    private void makeFrame(){
        this.setSize(350, 420);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        
        Font font = new Font("Andalus", Font.BOLD,30);
        
        panel = new JPanel();
        panel.setBounds(0, 90, 340, 300);
        panel.setLayout(new GridLayout(4,4));
        panel.setPreferredSize(new Dimension(100, 100));
        panel.setBackground(Color.white);
        panel.setForeground(Color.black);
        panel.setFont(font);
        
        
        textfield = new JTextField("0");
        textfield.setFont(font);
        textfield.setBounds(0, 0, 340, 90);
        textfield.setEditable(false);
        textfield.setHorizontalAlignment(JTextField.RIGHT);
        
        this.add(panel);
        this.add(textfield);
        this.setVisible(true);
    }
        
    private void makeButtons(){
        Add = new JButton("+");
        Add.setForeground(Color.blue);
        Sub = new JButton("-");
        Sub.setForeground(Color.blue);
        Mul = new JButton("x");
        Mul.setForeground(Color.blue);
        Div = new JButton("÷");
        Div.setForeground(Color.blue);
        Equal= new JButton("=");
        Equal.setForeground(Color.red);
        AC = new JButton("AC");
        AC.setForeground(Color.yellow);
        
        functionButtons[0] = Add;
        functionButtons[1] = Sub;
        functionButtons[2] = Mul;
        functionButtons[3] = Div;
        functionButtons[4] = Equal;
        functionButtons[5] = AC;
        
        for(int i =0;i<6;i++) {
            functionButtons[i].addActionListener(this);
        }
        
        for(int i =0;i<10;i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
        }
    }
        
    private void addButtons(){
        panel.add(numberButtons[7]);
        panel.add(numberButtons[8]);
        panel.add(numberButtons[9]);
        panel.add(Div);
        
        panel.add(numberButtons[4]);
        panel.add(numberButtons[5]);
        panel.add(numberButtons[6]);
        panel.add(Mul);
        
        panel.add(numberButtons[1]);
        panel.add(numberButtons[2]);
        panel.add(numberButtons[3]);
        panel.add(Sub);
        
        panel.add(AC);
        panel.add(numberButtons[0]);
        panel.add(Equal);
        panel.add(Add);
        this.panel.setVisible(true);
    }
    
    private boolean equalsPressed = false;
    private boolean zeroPressed =false;
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == numberButtons[0]){
            zeroPressed = true;
        }
        
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                if (equalsPressed) {
                    textfield.setText(String.valueOf(i));
                    equalsPressed = false;
                }
                else if (textfield.getText().equals("0")) {
                    textfield.setText(String.valueOf(i));
                } 
                else {
                    textfield.setText(textfield.getText().concat(String.valueOf(i)));
                }
            }
        }   
        
        String input = textfield.getText();
        
        for (int i =0;i <6;i++){
            if (e.getSource() == functionButtons[i]){
                String operator = functionButtons[i].getText();
                if (equalsPressed){
                    textfield.setText(operator);
                    equalsPressed = false;
                }
                else if (input.equals("0")){
                    if(!zeroPressed){
                        textfield.setText("");
                    }
                    else{
                        textfield.setText(textfield.getText());
                        zeroPressed = false;
                    }
                }
            }
        }

        input = textfield.getText();
        
        if (e.getSource() == Div) {
            textfield.setText(textfield.getText() + "÷");
            operator = '÷';
        }
        
        if (e.getSource() == Mul) {
            textfield.setText(textfield.getText() + "x");
            operator = 'x';
        }
        
        if (e.getSource() == Add) {
            textfield.setText(textfield.getText() + "+");
            operator = '+';
        }
    
        if (e.getSource() == Sub) {
            textfield.setText(textfield.getText() + "-");
            operator = '-';
        }
        
        if (e.getSource() == Equal) {
            String exp = textfield.getText();
            String[] parts = exp.split("(?<=[-+x÷])|(?=[-+x÷])");
            ArrayList<String> list = new ArrayList<>(Arrays.asList(parts));
            for(int i=0;i<list.size();i++){
                if (list.get(0).equals("x")||list.get(0).equals("÷")||list.get(0).equals("+")){
                list.remove(0);
                }    
                else if (list.get(0).equals("-")) {
                    if(Character.isDigit(list.get(1).charAt(0))){
                        String num = "-";
                        num = num.concat(list.get(1));
                        list.remove(1);
                        list.set(0, num); 
                    }
                    else{
                        list.remove(0);
                    }
                }
            }
            
            for (int i = 0; i < list.size(); i++){
                if (!Character.isDigit(list.get(list.size()-1).charAt(0))){
                    list.remove(list.size()-1);
                }                
            }
            
            for (int i = 1; i < list.size(); i++) {
                if (Character.isDigit(list.get(i).charAt(0)) && isOperator(list.get(i - 1).charAt(0))) {
                    int j = i - 2;
                    while (j >= 0 && isOperator(list.get(j).charAt(0)) && !isNegativeNumber(list.get(j))) {
                        list.remove(j);
                        j--;
                    }
                }
            }
            
            if(list.size()==2){
                if(list.get(0).equals("+")){
                    list.remove(0);
                }
                else if(list.get(0).equals("-")){
                    String num="-";
                    num=num.concat(list.get(1));
                    list.set(0,num);
                    list.remove(1);
                }
            }
            
            for (int i=0;i<list.size();i++){
                if(list.get(0)==String.valueOf(0)){
                    list.remove(0);
                }
            }
            
            double a=0.0;
    
            for (String op : new String[]{"÷", "x", "+", "-"}) {
                for (int i = 1; i < list.size(); i += 2) {
                    if (list.get(i).equals(op)) {
                        if(list.get(i-1)!=null){
                            a=Double.parseDouble(list.get(i-1));
                        }
                        double b = Double.parseDouble(list.get(i+1));
                        double result = 0.0;
                        switch (op) {
                            case "÷":
                                result = a / b;
                                break;
                            case "x":
                                result = a * b;
                                break;
                            case "+":
                                result = a + b;
                                break;
                            case "-":
                                result = a - b;
                                break;
                        }
                        list.set(i, String.valueOf(result));
                        list.remove(i+1);
                        list.remove(i-1);
                        i -= 2;
                    }
                }
            }
            double result = Double.parseDouble(list.get(0));
            if (result == (int) result){
                textfield.setText(String.valueOf((int) result));
            }
            else{
                textfield.setText(String.valueOf(result));
            }
            
            equalsPressed = true;            
        }
        
        if (e.getSource() == AC) {
            num1 = 0;
            num2 = 0;
            result = 0;
            operator = '\0';
            textfield.setText("0");
        }
    }
    
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == 'x' || c == '÷';
    }
    private boolean isNegativeNumber(String s) {
        return s.length() > 1 && s.charAt(0) == '-' && Character.isDigit(s.charAt(1));
    }
}