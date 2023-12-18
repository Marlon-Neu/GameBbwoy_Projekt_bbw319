package ch.bbw.gamebbwoy;

import java.util.List;

public class CollisionBox extends Box implements CollisionObject {
    public CollisionBox(int bottom, int right, int top, int left, Point position) {
        super(bottom, right, top, left, position);
    }

    public boolean collides(CollisionObject collidePair){
        if(collidePair instanceof CollisionBox){
           return collides((CollisionBox) collidePair);
        }
        if(collidePair instanceof CollidingSquare){
            return collides((CollidingSquare) collidePair);
        }
        return collidePair.collides(this);
    }

    private boolean collides(CollisionBox collisionBox){
        Bounds bordersSelf = getBounds();
        Bounds bordersPair = collisionBox.getBounds();
        if (bordersDisjoint(bordersSelf.left(), bordersSelf.right(), bordersPair.left(), bordersPair.right())){
            return false;
        }
        return !bordersDisjoint(bordersSelf.top(), bordersSelf.bottom(), bordersPair.top(), bordersPair.bottom());
    }

    private boolean collides(CollidingSquare collidingSquare){
        return collides(collidingSquare.getCollision());
    }


    private boolean bordersDisjoint(int selfLesser, int selfGreater, int pairLesser, int pairGreater) {
        if(notBetween(selfLesser, pairLesser, pairGreater) && notBetween(selfGreater, pairLesser, pairGreater)){
            return notBetween(pairLesser, selfLesser, selfGreater);
        }
        return false;
    }

    private boolean notBetween(int num, int lesser, int greater){
        return lesser > num || num > greater;
    }

    public static CollisionBox defaultBox(){
        return new CollisionBox(0,0,0,0,new Point(0,0));
    }
}
