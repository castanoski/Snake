import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakePannel extends JPanel implements ActionListener {
    
    static final int HEIGHT = 600;
    static final int WIDTH = 600;
    static final int DIMENTION = 20;
    static final int NUMBER_OF_PIXEL = (HEIGHT*WIDTH)/(DIMENTION*DIMENTION);
    static final int SPEED = 100;
    int snakeLength = 3;
    char direction = 'R';
    boolean gameOver = false;
    int[] snakePixelX = new int[NUMBER_OF_PIXEL];
    int[] snakePixelY = new int[NUMBER_OF_PIXEL];
    Timer timer;
    Random random;


    SnakePannel() {
        setPreferredSize(new Dimension(HEIGHT,WIDTH));
        setBackground(Color.BLACK);
        //addKeyListener();      //????
        start();
    }
    
    public void start() {
        random = new Random();
        timer = new Timer(SPEED, this);
        
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        

        
    }

}
