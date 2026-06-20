import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// ================= PLAYER =================

class Player {

    int x;
    int y;
    String position;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.position = "l";
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);

        if ("r".equals(position)) {

            g.fillRect(x, y, 30, 30);
            g.fillRect(x, y + 30, 30, 30);
            g.fillRect(x, y - 30, 30, 30);
            g.fillRect(x - 30, y + 30, 30, 30);
            g.fillRect(x - 30, y - 30, 30, 30);
            g.fillRect(x + 30, y, 30, 30);

        } else if ("d".equals(position)) {

            g.fillRect(x, y, 30, 30);
            g.fillRect(x, y + 30, 30, 30);
            g.fillRect(x + 30, y - 30, 30, 30);
            g.fillRect(x - 30, y, 30, 30);
            g.fillRect(x - 30, y - 30, 30, 30);
            g.fillRect(x + 30, y, 30, 30);

        } else if ("u".equals(position)) {

            g.fillRect(x, y, 30, 30);
            g.fillRect(x, y - 30, 30, 30);
            g.fillRect(x - 30, y + 30, 30, 30);
            g.fillRect(x - 30, y, 30, 30);
            g.fillRect(x + 30, y + 30, 30, 30);
            g.fillRect(x + 30, y, 30, 30);

        } else { // LEFT

            g.fillRect(x, y, 30, 30);
            g.fillRect(x, y + 30, 30, 30);
            g.fillRect(x, y - 30, 30, 30);
            g.fillRect(x - 30, y, 30, 30);
            g.fillRect(x + 30, y + 30, 30, 30);
            g.fillRect(x + 30, y - 30, 30, 30);
        }
    }

    public void moveLeft() {
        x -= 10;
        position = "l";
    }

    public void moveRight() {
        x += 10;
        position = "r";
    }

    public void moveUp() {
        y -= 10;
        position = "u";
    }

    public void moveDown() {
        y += 10;
        position = "d";
    }
}

// ================= BULLET =================
class Bullet {

    int x;
    int y;
    int dx;
    int dy;

    public Bullet(int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);

        if (dx == 0) {
            g.fillRect(x, y, 5, 10); // vertical
        } else {
            g.fillRect(x, y, 10, 5); // horizontal
        }
    }
}

// ================= ENEMY =================
class Enemy {
    int x;
    int y;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y += 2;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, 30, 30);
    }
}

// ================= GAME =================
public class game extends JPanel implements KeyListener {

    Player player = new Player(100, 100);

    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Enemy> enemies = new ArrayList<>();

    public game() {
        setFocusable(true);
        addKeyListener(this);

        // Create the first enemy
        enemies.add(new Enemy(200, 0));

        // Game loop
        Timer timer = new Timer(30, e -> {

            for (Bullet b : bullets) {
                b.move();
            }

            for (Enemy enemy : enemies) {
                enemy.move();
            }

            repaint();
        });

        timer.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        player.draw(g);

        for (Bullet b : bullets) {
            b.draw(g);
        }

        for (Enemy e : enemies) {
            e.draw(g);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

        // MOVEMENT
        if (e.getKeyCode() == KeyEvent.VK_LEFT)
            player.moveLeft();

        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            player.moveRight();

        if (e.getKeyCode() == KeyEvent.VK_UP)
            player.moveUp();

        if (e.getKeyCode() == KeyEvent.VK_DOWN)
            player.moveDown();

        // FIRE
        if (e.getKeyCode() == KeyEvent.VK_X) {

            if ("l".equals(player.position)) {
                bullets.add(new Bullet(player.x - 30, player.y + 15, -10, 0));
            }
            if ("r".equals(player.position)) {
                bullets.add(new Bullet(player.x + 60, player.y + 15, 10, 0));
            }
            if ("u".equals(player.position)) {
                bullets.add(new Bullet(player.x + 15, player.y - 30, 0, -10));
            }
            if ("d".equals(player.position)) {
                bullets.add(new Bullet(player.x + 15, player.y + 60, 0, 10));
            }
        }

        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("2D Shooter");

        game g = new game();

        frame.add(g);
        frame.setSize(900, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        g.requestFocusInWindow();
    }
}