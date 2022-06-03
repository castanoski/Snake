import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SnakePannel extends JPanel implements ActionListener {
    
    static final int HEIGHT = 510;      //must be divided by dimention
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
    int best;


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
        //stampa delle linee guida
        g.setColor(new Color(10, 10, 20));
        for(int i = 0; i < WIDTH/DIMENTION; i++)
            g.drawLine(i*DIMENTION, 0, i*DIMENTION, HEIGHT);
        for(int i = 0; i < HEIGHT/DIMENTION; i++)
            g.drawLine( 0, i*DIMENTION, WIDTH, i*DIMENTION);

        //stampa della apple
            g.setColor(Color.YELLOW);
            g.fillRect(appleX, appleY, DIMENTION, DIMENTION);
            g.setColor(new Color(random.nextInt(50)+205,random.nextInt(50)+180, random.nextInt(60)+90));
            g.fillRect(appleX+(DIMENTION/5), appleY+(DIMENTION/5), 3*(DIMENTION/5), 3*(DIMENTION/5));

        //stampa del serpente
        for(int i = 0; i < snakeLength; i++) {
            g.setColor(Color.RED);
            g.fillRect(snakePixelX[i], snakePixelY[i], DIMENTION, DIMENTION);
            g.setColor(new Color(random.nextInt(80)+130,0,0));
            g.fillRect(snakePixelX[i]+(DIMENTION/15), snakePixelY[i]+(DIMENTION/15), 13*(DIMENTION/15), 13*(DIMENTION/15));
        }

        //stampa punteggio e best
        g.setColor(Color.WHITE);
        g.setFont(new Font("serif", Font.ITALIC, 40));
        g.drawString("Score : " + (snakeLength - 3), 0, DIMENTION);
        g.drawString("Best : " + best, 0, 2*DIMENTION);
    }
    
    public void snakeStart() {
        newApple();
        newSnake();
        snakeLength = 3;
        gameOver = false;
        direction = 'R';
        timer.start();
    }

    public void newApple() {
        appleX = random.nextInt(WIDTH/DIMENTION)*DIMENTION;
        appleY = random.nextInt(HEIGHT/DIMENTION)*DIMENTION;
    }

    public void newSnake() {
        snakePixelX[0] = 3*DIMENTION;
        snakePixelY[0] = 4*DIMENTION; 
        snakePixelX[1] = 3*DIMENTION;
        snakePixelY[1] = 5*DIMENTION;
        snakePixelX[2] = 3*DIMENTION;
        snakePixelY[2] = 6*DIMENTION; 
    }

    public void moving() {
        for(int i = snakeLength; i > 0; i--) {
            snakePixelX[i] = snakePixelX[i-1];
            snakePixelY[i] = snakePixelY[i-1];
        }
        switch(direction){
            case 'R':
                snakePixelX[0] += DIMENTION;
                if(snakePixelX[0] == WIDTH)
                    snakePixelX[0] = 0;
                break;
            case 'L':
                snakePixelX[0] -= DIMENTION;
                if(snakePixelX[0] == -DIMENTION)
                    snakePixelX[0] = WIDTH-DIMENTION;
                break;
            case 'U':
                snakePixelY[0] -= DIMENTION;
                if(snakePixelY[0] == -DIMENTION)
                    snakePixelY[0] = HEIGHT-DIMENTION;
                break;
            case 'D':
                snakePixelY[0] += DIMENTION;
                if(snakePixelY[0] == HEIGHT)
                    snakePixelY[0] = 0;
                break;
        }   
    }

    public void checkEating() {
        if(snakePixelX[0] == appleX && snakePixelY[0] == appleY) {
            snakeLength++;
            newApple();
            if(best < snakeLength - 3) {
                best = snakeLength - 3;
            }
        }
    }

    public void checkDying() {
        for(int i = snakeLength-1; i > 0; i--) {
            if(snakePixelX[0] == snakePixelX[i] && snakePixelY[0] == snakePixelY[i])
                gameOver = true;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!gameOver) {
            moving();
            checkEating();
            checkDying();
        } else {
            timer.stop();
            snakeStart();
        }
        repaint();
    }

    //AGGIUNGI CONTROLLI SU SNAKEPIXELX[1]
    public class Keyboard extends KeyAdapter {

        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()) {
                case KeyEvent.VK_UP :
                    if(snakePixelY[0] != snakePixelY[1] + DIMENTION && !(snakePixelY[0] == 0 && direction == 'D'))
                        direction = 'U';
                    break;
                case KeyEvent.VK_LEFT:
                    if(snakePixelX[0] != snakePixelX[1] + DIMENTION && !(snakePixelX[0] == 0 && direction == 'R'))
                        direction = 'L';
                    break;
                case KeyEvent.VK_DOWN:
                    if(snakePixelY[0] != snakePixelY[1] - DIMENTION && !(snakePixelY[0] == HEIGHT-DIMENTION && direction == 'U'))
                        direction = 'D';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(snakePixelX[0] != snakePixelX[1] - DIMENTION && !(snakePixelX[0] == WIDTH-DIMENTION && direction == 'L')) 
                        direction = 'R';
                    break;
            }
        }
    }

}

