package Tetris;

import java.io.Serializable;
import java.util.Random;

public class Tetromino {
    cell[] cells = new cell[4];
    public static Tetromino getRandomOne(){
        Random r = new Random();
        int type = r.nextInt(7);
        switch (type){
            case 0:return new I();
            case 1:return new J();
            case 2:return new L();
            case 3:return new O();
            case 4:return new S();
            case 5:return new T();
            case 6:return new Z();
        }
        return null;
    }

    public void left() {
        for (cell c : cells) {
            c.moveLeft();
        }
    }
    public void right() {
        for (cell c : cells) {
            c.moveRight();
        }
    }public void down() {
        for (cell c : cells) {
            c.moveDown();
        }
    }public void up() {
        for (cell c : cells) {
            c.moveUp();
        }
    }

    private int index=1000;
    protected class State implements Serializable {
        int row0,col0,row1,col1,row2,col2,row3,col3;
        public State(int row0, int col0, int row1, int col1,
                     int row2, int col2, int row3, int col3) {
            this.row0 = row0;
            this.col0 = col0;
            this.row1 = row1;
            this.col1 = col1;
            this.row2 = row2;
            this.col2 = col2;
            this.row3 = row3;
            this.col3 = col3;
        }

    }
    protected State[] states;
    public void rotateRight(){
        index++;
        State state=states[index%4];
        cells[1].setRow(cells[1].getRow()+state.row1);
        cells[1].setCol(cells[1].getCol()+state.col1);
        cells[2].setRow(cells[2].getRow()+state.row2);
        cells[2].setCol(cells[2].getCol()+state.col2);
        cells[3].setRow(cells[3].getRow()+state.row3);
        cells[3].setCol(cells[3].getCol()+state.col3);
    }

    public void rotateLeft(){
        State state=states[index%4];
        cells[1].setRow(cells[1].getRow()-state.row1);
        cells[1].setCol(cells[1].getCol()-state.col1);
        cells[2].setRow(cells[2].getRow()-state.row2);
        cells[2].setCol(cells[2].getCol()-state.col2);
        cells[3].setRow(cells[3].getRow()-state.row3);
        cells[3].setCol(cells[3].getCol()-state.col3);
        index--;
    }

}
class I extends Tetromino{
    public I(){
        cells[0] = new cell(0,4,Tetris.I);
        cells[1] = new cell(0,3,Tetris.I);
        cells[2] = new cell(0,5,Tetris.I);
        cells[3] = new cell(0,6,Tetris.I);
        states=new State[]{
                new State(0,0, 1,-1,-1, 1,-2, 2),
                new State(0,0,-1, 1, 1,-1, 2,-2),
                new State(0,0, 1,-1,-1, 1,-2, 2),
                new State(0,0,-1, 1, 1,-1, 2,-2)
        };
    }
}
class T extends Tetromino {
    public T() {
        cells[0] = new cell(0, 4, Tetris.T);
        cells[1] = new cell(0, 3, Tetris.T);
        cells[2] = new cell(0, 5, Tetris.T);
        cells[3] = new cell(1, 4, Tetris.T);

        states = new State[]{
                new State(0, 0, -1, -1, 1, 1, 1, -1),
                new State(0, 0, -1, 1, 1, -1, -1, -1),
                new State(0, 0, 1, 1, -1, -1, -1, 1),
                new State(0, 0, 1, -1, -1, 1, 1, 1)
        };
    }
}
class L extends Tetromino{
    public L(){
        cells[0] = new cell(0,4,Tetris.L);
        cells[1] = new cell(0,3,Tetris.L);
        cells[2] = new cell(0,5,Tetris.L);
        cells[3] = new cell(1,3,Tetris.L);
        states=new State[]{
                new State(0,0,-1,-1, 1, 1, 0,-2),
                new State(0,0,-1, 1, 1,-1,-2, 0),
                new State(0,0, 1, 1,-1,-1, 0, 2),
                new State(0,0, 1,-1,-1, 1, 2, 0)
        };
    }
}

class J extends Tetromino{
    public J(){
        cells[0] = new cell(0,4,Tetris.J);
        cells[1] = new cell(0,3,Tetris.J);
        cells[2] = new cell(0,5,Tetris.J);
        cells[3] = new cell(1,5,Tetris.J);
        states=new State[]{
                new State(0,0,-1,-1, 1, 1, 2, 0),
                new State(0,0,-1, 1, 1,-1, 0,-2),
                new State(0,0, 1, 1,-1,-1,-2, 0),
                new State(0,0, 1,-1,-1, 1, 0, 2)
        };
    }
}
class S extends Tetromino{
    public S(){
        cells[0] = new cell(0,4,Tetris.S);
        cells[1] = new cell(0,5,Tetris.S);
        cells[2] = new cell(1,3,Tetris.S);
        cells[3] = new cell(1,4,Tetris.S);
        states=new State[]{
                new State(0,0, 1, 1, 0,-2, 1,-1),
                new State(0,0,-1,-1, 0, 2,-1, 1),
                new State(0,0, 1, 1, 0,-2, 1,-1),
                new State(0,0,-1,-1, 0, 2,-1, 1)
        };
    }
}

class Z extends Tetromino{
    public Z(){
        cells[0] = new cell(1,4,Tetris.Z);
        cells[1] = new cell(0,3,Tetris.Z);
        cells[2] = new cell(0,4,Tetris.Z);
        cells[3] = new cell(1,5,Tetris.Z);
        states=new State[]{
                new State(0,0, 0,-2,-1,-1,-1, 1),
                new State(0,0, 0, 2, 1, 1, 1,-1),
                new State(0,0, 0,-2,-1,-1,-1, 1),
                new State(0,0, 0, 2, 1, 1, 1,-1)
        };
    }
}

class O extends Tetromino{
    public O(){
        cells[0] = new cell(0,4,Tetris.O);
        cells[1] = new cell(0,5,Tetris.O);
        cells[2] = new cell(1,4,Tetris.O);
        cells[3] = new cell(1,5,Tetris.O);
        states=new State[]{
                new State(0,0,0,0,0,0,0,0),
                new State(0,0,0,0,0,0,0,0),
                new State(0,0,0,0,0,0,0,0),
                new State(0,0,0,0,0,0,0,0)
        };
    }
}