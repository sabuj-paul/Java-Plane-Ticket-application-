import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FinalProject extends JFrame implements ActionListener, MouseListener
{
    ImageIcon img1, img2;
    JLabel chipsLabel, drinksLabel, imgLabel1, imgLabel2, chipsPriceLabel, drinksPriceLabel, couponLabel;
    JTextField ageField;
    JPasswordField couponField;
    JTextField economyTF, businessTF;
    JTextArea purchaseHistoryArea;
    JButton clearBtn, cartBtn, purchaseBtn;
    JScrollPane scrollPane;
    JPanel panel;
    Color myColor;
    Font myFont;

    public FinalProject()
    {
        super("Flight E-ticket Management");
        this.setSize(1000,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myColor = new Color(160, 160, 160);
        myFont = new Font("Arial", Font.BOLD,12);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(myColor);

        couponLabel = new JLabel("Coupon Code ");
        couponLabel.setBounds(347, 230, 100, 30);
        couponLabel.setBackground(Color.WHITE);
        couponLabel.setOpaque(true);
        couponLabel.setForeground(Color.RED);
        couponLabel.setFont(myFont);
        panel.add(couponLabel);

        couponField = new JPasswordField();
        couponField.setBounds(347, 262, 100, 30);
        couponField.setEchoChar('*');
        couponField.setBackground(Color.CYAN);
        panel.add(couponField);

        img1 = new ImageIcon("Images/OIP.jpg");
        imgLabel1 = new JLabel(img1);
        imgLabel1.setBounds(10, -20, 200, 300);
        panel.add(imgLabel1);

        chipsLabel = new JLabel("Economy class Ticket Quantity:");
        chipsLabel.setBounds(240, 100, 187, 30);
        panel.add(chipsLabel);

        economyTF= new JTextField();
        economyTF.setBounds(442, 100, 100, 30);
        panel.add(economyTF);

        chipsPriceLabel = new JLabel("Price: 10000 Taka");
        chipsPriceLabel.setBounds(65, 235, 160, 30);
        panel.add(chipsPriceLabel);

        img2 = new ImageIcon("Images/vip.jpg");
        imgLabel2 = new JLabel(img2);
        imgLabel2.setBounds(10, 270, 200, 270);
        panel.add(imgLabel2);

        drinksLabel = new JLabel("Business Class Ticket Quantity:");
        drinksLabel.setBounds(240, 170, 185, 30);
        panel.add(drinksLabel);

        businessTF = new JTextField();
        businessTF.setBounds(442, 170, 100, 30);
        panel.add(businessTF);

        drinksPriceLabel = new JLabel("Price: 25000 Taka");
        drinksPriceLabel.setBounds(62, 520, 160, 30);
        panel.add(drinksPriceLabel);

        clearBtn = new JButton("Clear");
        clearBtn.setBounds(321, 300, 150, 30);
        clearBtn.addActionListener(this);
        panel.add(clearBtn);

        cartBtn = new JButton("Add To Cart");
        cartBtn.setBounds(321, 330, 150, 30);
        cartBtn.addActionListener(this);
        panel.add(cartBtn);

        purchaseBtn = new JButton("Confirm Purchase");
        purchaseBtn.setBounds(321, 360, 150, 30);
        purchaseBtn.addActionListener(this);
        panel.add(purchaseBtn);

        purchaseHistoryArea = new JTextArea();
        purchaseHistoryArea.setEditable(false);
        scrollPane = new JScrollPane(purchaseHistoryArea);
        scrollPane.setBounds(600, 20, 400, 520);
        panel.add(scrollPane);

        this.add(panel);
        this.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent ae)
    {
        String command = ae.getActionCommand();

        if(cartBtn.getText().equals(command))
        {
            addToCart();
        }
        else if(purchaseBtn.getText().equals(command))
        {
            purchase();
        }
        else if(clearBtn.getText().equals(command))
        {
            clearFields();
        }
    }

    public void mouseClicked(MouseEvent me){}
    public void mousePressed(MouseEvent me){}
    public void mouseReleased(MouseEvent me){}
    public void mouseEntered(MouseEvent me){}
    public void mouseExited(MouseEvent me){}

    private void addToCart() {
        String e_TicketQuantity = economyTF.getText().trim();
        String b_TicketQuantity = businessTF.getText().trim();
        String coupon = new String(couponField.getPassword()).trim();

        if (b_TicketQuantity.isEmpty() && e_TicketQuantity.isEmpty() ) {
            JOptionPane.showMessageDialog(this, "Please enter quantity");
            return;
        }

        if (!b_TicketQuantity.matches("\\d*") || !e_TicketQuantity.matches("\\d*")) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric quantities");
            return;
        }

        if (b_TicketQuantity.isEmpty()) b_TicketQuantity = "0";
        if (e_TicketQuantity.isEmpty()) e_TicketQuantity = "0";

        Info newInfo = new Info(b_TicketQuantity, coupon, e_TicketQuantity);

        try {
            FileWriter writer = new FileWriter("PurchaseData.txt", true);
            writer.write("Economy Class Ticket" + ", " + newInfo.getE_TicketQuantity() +
                    ", " + "Business Class Ticket" + ", " + newInfo.getB_TicketQuantity() +
                    ", " + "Total price" + ", " + newInfo.getTotalPrice() + "\n");
            writer.close();
            JOptionPane.showMessageDialog(this, "Successfully added to cart");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving purchase data");
        }
    }

    private void purchase()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("PurchaseData.txt"));
            String line;
            StringBuilder content = new StringBuilder();
            int customerCount = 1;

            while( (line = reader.readLine()) != null )
            {
                String[] parts = line.split(",");

                if(parts.length >= 6)
                {
                    
                    String e_TicketQuantity = parts[1].trim();
                    String b_TicketQuantity = parts[3].trim();
                    String total = parts[5].trim();

                    content.append("------------------------------ \n");
                    content.append("Customer ").append(customerCount++).append("\n");
                    content.append("Economy Class Ticket: ").append(e_TicketQuantity).append("\n");
                    content.append("Business Class Ticket: ").append(b_TicketQuantity).append("\n");
                    content.append("Total Price: ").append(total).append("\n");
                    content.append("------------------------------ \n");
                }
            }

            reader.close();


            PrintWriter pw = new PrintWriter("PurchaseData.txt");
            pw.close();

            purchaseHistoryArea.setText(content.toString());

            if (content.length() == 0) {
                JOptionPane.showMessageDialog(this, "No purchases found.");
            } else {
                JOptionPane.showMessageDialog(this, "Purchase confirmed and history displayed.");
            }

        }
        catch(IOException ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading purchase data");
        }
    }

    private void clearFields()
    {
        economyTF.setText("");
        businessTF.setText("");
        couponField.setText("");
    }
}
