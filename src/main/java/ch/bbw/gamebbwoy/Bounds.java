package ch.bbw.gamebbwoy;

import java.util.List;

public class Bounds {
    private static final int BOTTOM = 0;
    private static final int RIGHT = 1;
    private static final int TOP = 2;
    private static final int LEFT = 3;

    private final Integer[] bounds = new Integer[4];

    public Bounds(int bottom,int right,int top,int left){
        bounds[BOTTOM] = bottom;
        bounds[RIGHT] = right;
        bounds[TOP] = top;
        bounds[LEFT] = left;
    }

    public int bottom(){
        return bounds[BOTTOM];
    }
    public int right(){
        return bounds[RIGHT];
    }
    public int top(){
        return bounds[TOP];
    }
    public int left(){
        return bounds[LEFT];
    }


}
