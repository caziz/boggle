import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

/**
* @author Client GUI Team (Period 5)
* Christopher Aziz
* @version May 2015
*
* Receive 2D array from Server
* Display board to User
* Store words submitted by User
* Submit authenticated words to server
*/

public class BoggleGame extends JFrame implements ActionListener, KeyListener {
    private static final long serialVersionUID = -3125864417227564060L;
    static String username;
    static int length = Login.length;
    JTextArea searchTextArea = new JTextArea(0, 12);
    static DefaultListModel<String> wordListModel= new DefaultListModel<String>();
    JButton[][] buttons = new JButton[length][length];
    static JList<String> wordList = new JList<String>(wordListModel);
    JScrollPane wordScrollPane = new JScrollPane(wordList);
    JPanel northPanel = new JPanel();
    JPanel timePanel = new JPanel();
    JPanel usernamePanel = new JPanel();
    JTextArea scoreTextArea = new JTextArea("0");
    JPanel southPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel centerPanel2 = new JPanel();
    JPanel eastPanel = new JPanel();
    static int score = 0;
	static final WordValidator valid = new WordValidator();

    static JTextArea timeTextArea = new JTextArea();
    ImageIcon logo = new ImageIcon(getClass().getResource("logo.png"));

    Color white = new Color(255, 255, 255);
    Color blue = new Color(0, 150, 240);
    Color orange = new Color(245, 130, 32);

    public BoggleGame(){
        // Set Title
        super();

        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getResource("logo.png"));
        } catch (IOException e) {e.printStackTrace();}
        this.setIconImage(image);

        // Set up Frame
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set Layout
        getContentPane().setLayout(new BorderLayout());
        nimbusLookAndFeel();

        // North Panel
        northPanel.setLayout(new GridLayout(1, 3));
        northPanel.setPreferredSize(new Dimension(550, 100));
        northPanel.setBackground(blue);

        // Username Panel
        usernamePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        usernamePanel.setBackground(blue);
        JLabel usernameLabel = new JLabel("Username:");
        usernamePanel.add(usernameLabel);
        JTextArea usernameTextArea = new JTextArea();
        usernameTextArea.setText(Login.username);
        usernamePanel.add(usernameTextArea);
        northPanel.add(usernamePanel);
        usernameTextArea.setEditable(false);
        add(northPanel, BorderLayout.NORTH);
        northPanel.add(usernamePanel);

        // Logo
        northPanel.add(new JLabel(logo));

        // Time Panel
        timePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        timePanel.setBackground(blue);
        timePanel.setPreferredSize(new Dimension(170, 20));
        JLabel timeLabel = new JLabel("Time:");
        timePanel.add(timeLabel);
        timeTextArea.setText("0");
        timePanel.add(timeTextArea);
        timeTextArea.setEditable(false);
        add(northPanel, BorderLayout.NORTH);
		
        // Score Panel
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        scorePanel.setBackground(blue);
        scorePanel.setPreferredSize(new Dimension(170, 30));
        JLabel scoreLabel = new JLabel("Score:");
        scorePanel.add(scoreLabel);
        scorePanel.add(scoreTextArea);

        // NE Panel
        JPanel NEPanel = new JPanel();
        NEPanel.setBackground(blue);
        NEPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        NEPanel.add(timePanel);
        NEPanel.add(scorePanel);
        northPanel.add(NEPanel);

        // South Panel
        southPanel.setPreferredSize(new Dimension(550, 50));
        southPanel.setBackground(blue);
        searchTextArea.setLineWrap(true);
        southPanel.add(searchTextArea);
        searchTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        searchTextArea.setFont(new Font("Geniva", Font.PLAIN, 30));
        searchTextArea.addKeyListener((KeyListener) this);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        southPanel.add(submitButton);
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(this);
        southPanel.add(quitButton);
        getContentPane().add(southPanel, BorderLayout.SOUTH);

        // Center Panel
        centerPanel.setPreferredSize(new Dimension(400, 400));
        centerPanel.setBackground(blue);
        centerPanel.setLayout(new GridLayout(length, length));
        new BoggleBoard(length);
        for(int i = 0; i < length; i++) {
            for(int j = 0; j < length; j++) {
                buttons[i][j] = new JButton(BoggleBoard.getLetters()[i][j]);
                buttons[i][j].setBackground(white);
                buttons[i][j].setFont(new Font("Geniva", Font.BOLD, 200 / length));
                buttons[i][j].addActionListener(this);
                centerPanel.add(buttons[i][j]);
            }
        }
        add(centerPanel, BorderLayout.CENTER);

        // Center Panel 2
        centerPanel2.setPreferredSize(new Dimension(400, 400));
        centerPanel.setBackground(blue);
        centerPanel2.add(new JButton("Start Game"));

        // East Panel
        eastPanel.add(wordScrollPane);
        wordScrollPane.setPreferredSize(new Dimension(150, 400));
        eastPanel.setBackground(blue);
        getContentPane().add(eastPanel, BorderLayout.EAST);

        // Start Timer Thread
        (new Thread(new Timer(this))).start();

        // Finalize
        pack();
        searchTextArea.requestFocusInWindow();
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
        } catch (Exception e) {
        }
    }

    public void clearButtons() {
        for(int i = 0; i < buttons.length; i++) {
            for(int j = 0; j < buttons[0].length; j++) {
                buttons[i][j].setBackground(white);
            }
        }
    }

    public void submitWord() {
        String theWord = searchTextArea.getText();
        searchTextArea.setText("");

        if (theWord.equals("")) {
            return;
        } else if (!findWord(theWord)) {
            wordListModel.addElement("Not on board!");
        } else if (theWord.length() < 3) {
            wordListModel.addElement("At least 3 letters!");
        } else if (!(WordValidator.isWordValid(theWord))) {
            wordListModel.addElement("Not a word!");
        } else if ((isUsedBefore(theWord))) {
            wordListModel.addElement("Already used!");
        } else {
            wordListModel.addElement(theWord.toUpperCase());
            score += getPoints(theWord);
            scoreTextArea.setText("" + score);
        }
        searchTextArea.getDocument().putProperty("filterNewlines", true);
        wordList.ensureIndexIsVisible(wordListModel.size() - 1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String buttonText = ((JButton) e.getSource()).getText();

        // Check if source is the Submit Button
        if (buttonText.equals("Submit")){
            submitWord();
            clearButtons();
            // Check if source is the Quit Button
        } else if (buttonText.equals("Quit")){
            dispose();
            new Login();
            // Source is a Letter Button
        } else if (buttonText.equals("New Game")){
            newGame();
        } else {
            searchTextArea.append(buttonText);
            ((JButton) e.getSource()).setBackground(orange);
        }
        searchTextArea.requestFocusInWindow();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        searchTextArea.setText(searchTextArea.getText().toUpperCase());
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            submitWord();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        searchTextArea.setText(searchTextArea.getText().toUpperCase());
        if (!findWord(searchTextArea.getText())) {
            clearButtons();
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            searchTextArea.setText("");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public void pauseGame() {
        remove(centerPanel);
        add(centerPanel2, BorderLayout.CENTER);
    }

    public void newGame() {
        remove(centerPanel2);
        add(centerPanel, BorderLayout.CENTER);
        for(int i = 0; i < length; i++) {
            for(int j = 0; j < length; j++) {
                buttons[i][j].setText(Character.toString(((char)(int)(Math.random() * 25 + 65))));
            }
        }
    }

    public boolean findWord(String word) {
        clearButtons();
        for (int row = 0; row < length; row++) {
            for (int col = 0; col < length; col++) {
                if (findWord(word, row, col, buttons)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean findWord(String word, int row, int col, JButton[][] buttons) {
        if (word.equals("")) {
            return true;
        }

        if (row < 0 || row >= length ||
            col < 0 || col >= length ||
        !(this.buttons[row][col].getText().equals(word.substring(0, 1))) ||
        buttons[row][col].getBackground().equals(orange)
        ) {
            return false;
        }

        String rest = word.substring(1, word.length());
        this.buttons[row][col].setBackground(orange);

        boolean letter =
        findWord(rest, row - 1, col - 1, buttons) ||
        findWord(rest, row - 1,   col, buttons) ||
        findWord(rest, row - 1, col + 1, buttons) ||
        findWord(rest, row, col - 1, buttons) ||
        findWord(rest, row, col + 1, buttons) ||
        findWord(rest, row + 1, col - 1, buttons) ||
        findWord(rest, row + 1, col, buttons) ||
        findWord(rest, row + 1, col + 1, buttons);
        if (!letter) {
            this.buttons[row][col].setBackground(white);
        }
        return letter;
    }

    public static void setTime(String time) {
        timeTextArea.setText(time);
    }

    public static boolean isUsedBefore(String word) {
        for(int i = 0; i < wordListModel.getSize(); i++) {
            if (word.equals(wordListModel.getElementAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static int getScore() {
        return score;
    }

    public static int getPoints(String word) {
        int wordLength = word.length();
        
        if (wordLength == 3) {
            return 1;
        } else if (wordLength == 4 || wordLength == 5) {
            return 2;
        } else if (wordLength == 6) {
            return 3;
        } else if (wordLength == 7) {
            return 5;
        } else if (wordLength >= 8) {
            return 11;
        } else {
            return 0;
        }
    }
}
