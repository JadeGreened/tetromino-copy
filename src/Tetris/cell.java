package Tetris;

import java.awt.image.BufferedImage;

public class cell {
    int row;
    int col;
    BufferedImage image;
    public cell(int row,int col,BufferedImage image){
        this.row = row;
        this.col = col;
        this.image = image;

    }
    public void moveLeft(){
        col--;
    }public void moveRight(){
        col++;
    }public void moveDown(){
        row++;
    }public void moveUp(){
        row--;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
