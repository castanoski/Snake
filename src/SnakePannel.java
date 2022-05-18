import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakePannel extends JPanel implements ActionListener {
    
    static final int HEIGHT = 600;      //must be divided by dimention
    static final int WIDTH = 600;       //must be divided by dimention
    static final int DIMENTION = 30;    //must be divided by 15
    static final int NUMBER_OF_PIXEL = (HEIGHT*WIDTH)/(DIMENTION*DIMENTION);
    static final int SPEED = 100;
    int snakeLength = 3;
    char direction = 'R';
    boolean gameOver = false;
    int[] snakePixelX = new int[NUMBER_OF_PIXEL];
    int[] snakePixelY = new int[NUMBER_OF_PIXEL];
    int appleX;
    int appleY;
    Timer timer;
    Random random;


    SnakePannel() {
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setBackground(Color.BLACK);
        random = new Random();
        timer = new Timer(SPEED, this);
        setFocusable(true);
        addKeyListener(new Keyboard());      
        snakeStart();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(27, 30, 50));

        //stampa delle linee guida
        for(int i = 0; i < WIDTH/DIMENTION; i++)
            g.drawLine(i*DIMENTION, 0, i*DIMENTION, HEIGHT);
        for(int i = 0; i < HEIGHT/DIMENTION; i++)
            g.drawLine( 0, i*DIMENTION, WIDTH, i*DIMENTION);

        //stampa della apple
            g.setColor(Color.YELLOW);
            g.fillRect(appleX, appleY, DIMENTION, DIMENTION);
            g.setColor(new Color(random.nextInt(50)+205,random.nextInt(50)+205, random.nextInt(60)+100));
            g.fillRect(appleX+(DIMENTION/5), appleY+(DIMENTION/5), 3*(DIMENTION/5), 3*(DIMENTION/5));

        //stampa del serpente
        for(int i = 0; i < snakeLength; i++) {
            g.setColor(Color.RED);
            g.fillRect(snakePixelX[i], snakePixelY[i], DIMENTION, DIMENTION);
            g.setColor(new Color(random.nextInt(80)+130,0,0));
            g.fillRect(snakePixelX[i]+(DIMENTION/15), snakePixelY[i]+(DIMENTION/15), 13*(DIMENTION/15), 13*(DIMENTION/15));
        }
    }
    
    public void snakeStart() {
        newApple();
        timer.start();
    }

    public void newApple() {
        appleX = random.nextInt(WIDTH/DIMENTION)*DIMENTION;
        appleY = random.nextInt(HEIGHT/DIMENTION)*DIMENTION;
    }

    public void moving() {
        for(int i = snakeLength; i > 0; i--) {
            snakePixelX[i] = snakePixelX[i-1];
            snakePixelY[i] = snakePixelY[i-1];
        }
        switch(direction){
            case 'R':
                snakePixelX[0] += DIMENTION;
                break;
            case 'L':
                snakePixelX[0] -= DIMENTION;
                break;
            case 'U':
                snakePixelY[0] -= DIMENTION;
                break;
            case 'D':
                snakePixelY[0] += DIMENTION;
                break;
        }   
    }

    public void checkEating() {
        if(snakePixelX[0] == appleX && snakePixelY[0] == appleY) {
            snakeLength++;
            newApple();
        }
    }

    public void checkDying() {

    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!gameOver) {
            moving();
            checkEating();
            checkDying();
        }
        repaint();
    }

    //aggiungi controlli sul pezzo precedente, non sulla direzione
    public class Keyboard extends KeyAdapter {

        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()) {
                case KeyEvent.VK_W :
                    if(direction != 'D')
                        direction = 'U';
                    break;
                case KeyEvent.VK_A:
                    if(direction != 'R')
                        direction = 'L';
                    break;
                case KeyEvent.VK_S:
                    if(direction != 'U')
                        direction = 'D';
                    break;
                case KeyEvent.VK_D:
                    if(direction != 'L') 
                        direction = 'R';
                    break;
                case KeyEvent.VK_UP :
                    if(direction != 'D')
                        direction = 'U';
                    break;
                case KeyEvent.VK_LEFT:
                    if(direction != 'R')
                        direction = 'L';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U')
                        direction = 'D';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') 
                        direction = 'R';
                    break;
            }
        }
    }

}

