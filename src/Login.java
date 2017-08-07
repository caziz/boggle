import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Hashtable;

public class Login extends JFrame implements ActionListener, KeyListener {
    private static final long serialVersionUID = 5061049907596629241L;
    public static String username = "Player";
    public static int length = 1;
    public static int time = 10000;
    Color blue = new Color(0, 160, 250);
    ImageIcon logo = new ImageIcon(this.getClass().getResource("logo.png"));
    JTextField txtUsername = new JTextField(username, 16);
    JRadioButton button4 = new JRadioButton("4");
    JRadioButton button5 = new JRadioButton("5");
    JRadioButton button6 = new JRadioButton("6");
    JSlider slider = new JSlider(0, 300, 180);

    public static void main(String[] arguments){
        new Login();
    }

    public Login() {
        super("");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResource("logo.png"));
        } catch (IOException e) {e.printStackTrace();}
        this.setIconImage(image);

        JPanel panel = new JPanel();
        panel.setBackground(blue);
        JPanel north = new JPanel();
        north.setBackground(blue);
        JLabel lblUsername = new JLabel("Enter Username:");
        JLabel lblLength = new JLabel("Enter Board Side Length:");
        JLabel lblTime = new JLabel("Select Time:");
        JButton btnStart = new JButton("Start");

        north.add(new JLabel(logo));
        panel.add(lblUsername);
        panel.add(txtUsername);
        txtUsername.addKeyListener(this);;
        btnStart.addActionListener(this);
        getContentPane().add(BorderLayout.CENTER,panel);
        getContentPane().add(BorderLayout.NORTH,north);

        panel.add(lblLength);

        //make radio buttons
        ButtonGroup group = new ButtonGroup();
        button4.setSelected(true);
        group.add(button4);
        group.add(button5);
        group.add(button6);
        panel.add(button4);
        panel.add(button5);
        panel.add(button6);

        slider.setMajorTickSpacing(60);
        slider.setMinorTickSpacing(15);
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);
        panel.add(lblTime);
        Hashtable<Integer, JLabel> hash = new Hashtable<Integer, JLabel>();
        hash.put(new Integer(15), new JLabel("(min)"));
        hash.put(new Integer(60), new JLabel("1"));
        hash.put(new Integer(120), new JLabel("2"));
        hash.put(new Integer(180), new JLabel("3"));
        hash.put(new Integer(240), new JLabel("4"));
        hash.put(new Integer(300), new JLabel("5"));
        slider.setLabelTable(hash);
        slider.setPaintLabels(true);
        panel.add(slider);

        panel.add(btnStart);

        setSize(350, 270);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void nimbusLookAndFeel() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && !(txtUsername.getText().equals(""))){
            runGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (((JButton) e.getSource()).getText().equals("Start") && !(txtUsername.getText().equals(""))) {
            runGame();
        }
    }

    public void runGame() {
        if (button4.isSelected()) {
            length = 4;
        } else if (button5.isSelected()) {
            length = 5;
        } else {
            length = 6;
        }
        username = txtUsername.getText();
        time = slider.getValue();
        dispose();
        new BoggleGame();
    }
}
