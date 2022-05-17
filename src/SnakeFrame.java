import javax.swing.JFrame;

public class SnakeFrame extends JFrame {
    
    SnakeFrame() {
        add(new SnakePannel());
        setTitle("Snake Game");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
