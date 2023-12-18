package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;

import java.util.List;

public class ForcesIcon implements PixelDrawing {

    private Point pos;
    private final List<Integer> forces = List.of(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,3,2,0,0,0,0,3,0,0,0,0,0,2,3,2,0,0,0,3,3,3,0,0,0,0,2,3,2,0,0,3,3,3,3,3,0,0,0,2,3,2,0,0,0,2,3,2,0,0,0,3,3,3,3,3,0,0,2,3,2,0,0,0,0,3,3,3,0,0,0,2,3,2,0,0,0,0,0,3,0,0,0,0,2,3,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    private final int height = 9;
    private final int width = 13;

    private final Box border;
    public ForcesIcon(Point pos){
        this.pos=pos;
        border = new Box(height,width,1,1,pos);
    }
    @Override
    public void tick(PixelDisplay graphic) {
        if(0< pos.x&&pos.x+width<graphic.getPixelWidth()) {
            for (int h = 0; h < height; h++) {
                for (int w = 0; w < width; w++) {
                    var color = forces.get(h * width + w);
                    if (0 < h + pos.y && h + pos.y < graphic.getPixelHeight()) {
                        if (color != -1) {
                            graphic.setPixel(w + (int) pos.x, h + (int) pos.y, PixelColor.fromValue(color));
                        }
                    }
                }
            }
        }
        border.tick(graphic);
    }
}
