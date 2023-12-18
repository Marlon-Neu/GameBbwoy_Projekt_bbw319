package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.PixelColor;
import ch.bbw.gamebbwoy.api.PixelDisplay;

public class CollidingSquare extends Square implements CollisionObject {

    private CollisionBox weakCollision;
    private CollisionBox strongCollision;

    private boolean drawCollision =false;
    public CollidingSquare(Point position, double angleInRadians, int size) {
        super(position, angleInRadians, size);
        weakCollision = CollisionBox.defaultBox();
        strongCollision = CollisionBox.defaultBox();;
        updateCollision();
    }

    public void setDrawCollision(boolean drawCollision) {
        this.drawCollision = drawCollision;
    }

    @Override
    public void setAngleInRadians(double angleInRadians) {
        super.setAngleInRadians(angleInRadians);
        updateCollision();
    }

    @Override
    public void setSize(int size) {
        super.setSize(size);
        updateCollision();
    }

    @Override
    public void setPosition(Point position) {
        super.setPosition(position);
        updateCollision();
    }

    public CollisionBox getCollision() {
        return strongCollision;
    }

    private void updateCollision(){
        double quarterAngle = Math.abs(getAngleInRadians())%(Math.PI/2);
        double adjust = Math.sin(quarterAngle) + Math.cos(quarterAngle);
        int newWeakCBSize =(int) (getSize()*adjust);
        int newStrongCBSize =(int) (getSize()/adjust);
        weakCollision.setBox(newWeakCBSize,newWeakCBSize,newWeakCBSize,newWeakCBSize, getPosition());
        weakCollision.setColor(PixelColor.LOW);
        strongCollision.setBox(newStrongCBSize,newStrongCBSize,newStrongCBSize,newStrongCBSize,getPosition());
        strongCollision.setColor(PixelColor.HIGH);
    }

    @Override
    public void tick(PixelDisplay graphic) {
        if(drawCollision) {
            weakCollision.tick(graphic);
            strongCollision.tick(graphic);
        }
        super.tick(graphic);
    }

    @Override
    public boolean collides(CollisionObject c) {
        if(weakCollision.collides(c)){
            return true;
        }
        return strongCollision.collides(c);
    }
}
