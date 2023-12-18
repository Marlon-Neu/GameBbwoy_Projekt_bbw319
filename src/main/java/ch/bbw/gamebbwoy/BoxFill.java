package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;

import java.util.List;

public class BoxFill extends Box implements PixelDrawing {
    private PixelColor color=PixelColor.BLACK;
    public BoxFill(int bottom, int right, int top, int left, Point position) {
        super(bottom, right, top, left, position);
    }

    public BoxFill(int bottom, int right, int top, int left, Point position,PixelColor color) {
        super(bottom, right, top, left, position);
        this.color=color;
    }

    @Override
    public void setColor(PixelColor color) {
        this.color = color;
    }

    @Override
    public void tick(PixelDisplay graphic) {
        Bounds bounds = getBounds();
        for(int h = bounds.top();h< bounds.bottom();h++){
            for(int w = bounds.left();w< bounds.right();w++){
                if((0<=w&&w<graphic.getPixelWidth())&&(0<=h&&h<graphic.getPixelHeight())) {
                    graphic.setPixel(w, h, color);
                }
            }
        }


    }
}
