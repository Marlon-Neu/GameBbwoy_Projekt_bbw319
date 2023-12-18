package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;

public class BackgroundStars extends Point implements GameObject, PixelDrawing {
    private double parallax;
    private PixelColor color;

    public BackgroundStars(double x, double y) {
        super(x, y);
    }

    public BackgroundStars(double x, double y,double parallax, PixelColor color) {
        super(x, y);
        this.parallax = parallax;
        this.color = color;
    }

    @Override
    public Point getPosition() {
        return this;
    }

    @Override
    public void update() {

    }

    @Override
    public void update(PlayerSquare player) {
        x -= player.getCenterOfGravity().getXSpeed() * player.getTimePerDeltaTime()*parallax*.1;
        y -= player.getCenterOfGravity().getYSpeed() * player.getTimePerDeltaTime()*parallax*.1;
    }

    @Override
    public void tick(PixelDisplay graphic) {
        if((0<=x&&x<graphic.getPixelWidth())&&(0<=y&&y<graphic.getPixelHeight())) {
            graphic.setPixel((int)x,(int) y, color);
        }
    }
}
