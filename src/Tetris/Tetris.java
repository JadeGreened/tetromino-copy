package Tetris;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;

public class Tetris extends JPanel {
    private int state;
    public static final int PLAYING = 0;
    public static final int PAUSE = 1;
    public static final int GAME_OVER = 2;
    private static final String[] STATE = {"[P]Pause","[C]Continue","[S]Restart"};
    public static BufferedImage background;
    public static BufferedImage gameOver;
    public static BufferedImage I;
    public static BufferedImage L;
    public static BufferedImage J;
    public static BufferedImage O;
    public static BufferedImage S;
    public static BufferedImage T;
    public static BufferedImage Z;

    public Tetromino currentTetromino;
    public Tetromino nextTetromino;
    private int lines;
    private int scores;

    public static int cellSize = 26;
    public static int COL = 10;
    public static int ROW = 20;
    private cell[][] wall = new cell[ROW][COL];

    static {
        try {
            background = ImageIO.read(Tetris.class.getResource("tetris.png"));
            gameOver = ImageIO.read(Tetris.class.getResource("game-over.png"));
            I = ImageIO.read(Tetris.class.getResource("I.png"));
            L = ImageIO.read(Tetris.class.getResource("L.png"));
            J = ImageIO.read(Tetris.class.getResource("J.png"));
            O = ImageIO.read(Tetris.class.getResource("O.png"));
            S = ImageIO.read(Tetris.class.getResource("S.png"));
            T = ImageIO.read(Tetris.class.getResource("T.png"));
            Z = ImageIO.read(Tetris.class.getResource("Z.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paint(Graphics g) {
        Font font = new Font(Font.SANS_SERIF,Font.BOLD,30);
        g.setFont(font);
        g.setColor(new Color(0x667799));
        g.drawImage(background, 0, 0, null);
        g.translate(15, 15);
        paintWall(g);
        paintCurrentTetromino(g);
        paintNextTetromino(g);
        paintScore(g);
        g.translate(-15,-15);
        paintState(g);
    }

    private void paintScore(Graphics g) {
        int x = 293;
        int y = 162;
        g.drawString("Score"+scores,x,y);
        y+=56;
        g.drawString("Lines"+lines,x,y);
    }

    private void paintState(Graphics g) {
        g.drawString(STATE[state],309,287);
        if (state == GAME_OVER){
            g.drawImage(gameOver,0,0,null);
        }
    }

    private void paintNextTetromino(Graphics g) {
        for (cell cell : nextTetromino.cells) {
            int x = (cell.getCol() + 10) * cellSize;
            int y = (cell.getRow() + 1) * cellSize;
            g.drawImage(cell.getImage(), x + 10, y + 1, null);
        }
    }

    private void paintWall(Graphics g) {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                cell cell = wall[row][col];
                int x = cellSize * col;
                int y = cellSize * row;
                if (cell == null) {
                } else {
                    g.drawImage(cell.getImage(), x, y, null);
                }
            }
        }
    }

    private void paintCurrentTetromino(Graphics g) {
        for (cell cell : currentTetromino.cells) {
            int x = cell.getCol() * cellSize;
            int y = cell.getRow() * cellSize;
            g.drawImage(cell.getImage(), x, y, null);
        }
    }

    private void landIntoWall() {
        cell[] cells = currentTetromino.cells;
        for (cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            wall[row][col] = cell;
        }
    }

    private boolean canDrop() {
        cell[] cells = currentTetromino.cells;
        for (cell cell : cells) {
            if (cell.getRow() == ROW - 1) {
                return false;
            }
        }
        for (cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            if (wall[row + 1][col] != null) {
                return false;
            }
        }
        return true;
    }

    private void softDropAction() {
        boolean drop = canDrop();
        if (drop) {
            currentTetromino.down();
        } else {
            landIntoWall();
            detroyLine();
            if (!isGameOver()) {
                currentTetromino = nextTetromino;
                nextTetromino = Tetromino.getRandomOne();
            }else{
                state = GAME_OVER;
            }

        }
    }

    private void moveRightAction() {
        currentTetromino.right();
        if (outOfBounds() || concide()) {
            currentTetromino.left();
        }
    }

    private void moveLeftAction() {
        currentTetromino.left();
        if (outOfBounds() || concide()) {
            currentTetromino.right();
        }
    }

    private void moveDown() {
        currentTetromino.down();
        if (outOfBounds() || concide()) {
            currentTetromino.up();
        }

    }

    private void rotateRight() {
        currentTetromino.rotateRight();
        if (outOfBounds() || concide()) {
            currentTetromino.rotateLeft();
        }
    }


    private boolean concide() {
        cell[] cells = currentTetromino.cells;
        for (cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            if (wall[row][col] != null) {
                return true;
            }
        }
        return false;
    }

    private boolean outOfBounds() {
        cell[] cells = currentTetromino.cells;
        for (cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            if ((row < 0 || row >= ROW) || (col < 0 || col >= COL)) {
                return true;
            }
        }
        return false;
    }
private int[] scoreTable = {0,1,5,20,100};
    private void detroyLine() {
        int lines = 0;
        for (int row = 0;row<ROW;row++){
            if (fullCells(row)){
                deleteRow(row);
                lines++;
            }
        }
        this.scores += scoreTable[lines];
        //this.scores = this.score + scoreTable[lines]
        this.lines += lines;
    }

    private void deleteRow(int row) {
        for (int i = row;i>=1;i--){
            System.arraycopy(wall[i-1],0,wall[i],0,COL);
        }
        Arrays.fill(wall[0],null);

    }

    private boolean fullCells(int row) {
        cell[] line = wall[row];
        for (cell cell : line) {
            if (cell==null){
                return false;
            }
        }
        return true;
    }

    private boolean isGameOver() {
        cell[] cells = nextTetromino.cells;
        for (cell cell : cells) {
            int row = cell.getRow();
            int col = cell.getCol();
            if (wall[row][col] != null) {
                return true;
            }
        }
        return false;
    }

    public void action() {
        state = PLAYING;
        currentTetromino = Tetromino.getRandomOne();
        nextTetromino = Tetromino.getRandomOne();
        repaint();
        KeyListener kl = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if(key==KeyEvent.VK_Q){
                    System.exit(0);
                }
                switch(state){
                    case GAME_OVER :
                        if (key==KeyEvent.VK_S){
                            restartAction();
                        }
                        return;
                    case PAUSE:
                        if (key==KeyEvent.VK_C){
                            state = PLAYING;
                        }
                        return;
                }
                switch (key) {
                    //s 83 a 65 d 68 w 87
                    case 87:
                        rotateRight();
                        break;
                    case 83:
                        moveDown();
                        break;
                    case 65:
                        moveLeftAction();
                        break;
                    case 68:
                        moveRightAction();
                        break;
                    case KeyEvent.VK_P:
                        state = PAUSE;
                }
                repaint();
            }

            private void restartAction() {
                scores = 0;
                lines = 0;
                wall = new cell[ROW][COL];
                currentTetromino = Tetromino.getRandomOne();
                nextTetromino = Tetromino.getRandomOne();
                state = PLAYING;
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
        this.addKeyListener(kl);
        this.requestFocus();
        while (true) {
            if (state == PLAYING){
                softDropAction();
            }
            repaint();
            try {
                Thread.sleep(10000 / 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Tetris panel = new Tetris();
        frame.add(panel);
        frame.setSize(550, 600);
        frame.setVisible(true);
        panel.action();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
