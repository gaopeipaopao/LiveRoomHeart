package com.example.gaope.liveroomheart;

/**
 * Created by gaope on 2018/5/24.
 */

public class Heart {

    public float x;
    public float y;
    public float process;

    //loc为当前Herat在BitmapList中的位置
    private int loc;

    public void setX(float x1){
        x = x1;
    }

    public float getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getY() {
        return y;
    }

    public void setProcess(float process) {
        this.process = process;
    }

    public float getProcess() {
        return process;
    }

    public void setLoc(int loc) {
        this.loc = loc;
    }

    public int getLoc() {
        return loc;
    }
}
