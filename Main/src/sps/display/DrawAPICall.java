package sps.display;

import com.badlogic.gdx.graphics.Color;

public class DrawAPICall {
    public final float X;
    public final float Y;
    public final float X2;
    public final float Y2;
    public final Color Color;
    public final Float Radius;

    public DrawAPICall(Color color, float x,float y, float x2, float y2){
        this(color,x,y,x2,y2,null);
    }

    public DrawAPICall(Color color,float x,float y, float radius){
        this(color,x,y,0,0,radius);
    }

    private DrawAPICall(Color color, float x, float y, float x2, float y2, Float radius){
        Color = color;
        X = x;
        Y = y;
        X2 = x2;
        Y2 = y2;
        Radius = radius;
    }
}
