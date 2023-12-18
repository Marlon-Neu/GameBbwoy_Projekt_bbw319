package ch.bbw.gamebbwoy;

import ch.bbw.gamebbwoy.api.PixelDisplay;
import ch.bbw.gamebbwoy.api.PixelDrawing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class World {
    private final PixelDisplay graphic;
    private final Bounds drawingBounds;
    private final Bounds gameBounds;
    private final List<PhysicsObject> physicsObjects = new ArrayList<>();
    private final List<CollisionObject> collisionObjects = new ArrayList<>();
    private final List<GameObject> gameObjects = new ArrayList<>();
    private final PlayerSquare player;

    private int numberOfBG=0;

    public World(PixelDisplay graphic, Bounds drawingBounds, Bounds gameBounds, PlayerSquare player) {
        this.graphic = graphic;
        this.drawingBounds = drawingBounds;
        this.gameBounds = gameBounds;
        this.player = player;
        gameObjects.add(player);
        addPhysicObject(player);
        addCollisionObject(player);
    }

    private void addPhysicObject(PhysicsObject object) {physicsObjects.add(object);}
    private void addCollisionObject(CollisionObject object) {collisionObjects.add(object);}
    public void add(GameObject object){
        gameObjects.add(object);
        player.addGameObject(object);
        if(object instanceof PhysicsObject){
            addPhysicObject((PhysicsObject) object);
        }
        if(object instanceof CollisionObject){
            addCollisionObject((CollisionObject) object);
        }
        if(object instanceof BackgroundStars){
            numberOfBG++;
        };
    }

    public int getObjectNum(){
        return gameObjects.size()-numberOfBG;
    }

    public int getNumberOfBG() {
        return numberOfBG;
    }

    public void update(){
        for(int i=0;i<physicsObjects.size()-1;i++){
            for (int j=1;j<physicsObjects.size();j++){
                physicsObjects.get(i).interact(physicsObjects.get(j));
            }
        }
        List<ForcesUp> toRemove = new ArrayList<>();
        for (int j=1;j<collisionObjects.size();j++){
           if(collisionObjects.get(j) instanceof ForcesUp){
               if(collisionObjects.get(0).collides(collisionObjects.get(j))){
                   toRemove.add((ForcesUp) collisionObjects.get(j));
               }
           }
        }
        for(ForcesUp object : toRemove){
            collisionObjects.remove(object);
            physicsObjects.remove(object);
            gameObjects.remove(object);
        }
        Iterator<GameObject> iterator= gameObjects.iterator();
        while(iterator.hasNext()){
            GameObject object= iterator.next();
            object.update();
            if(object instanceof PixelDrawing){
                if(object.inBounds(drawingBounds)){
                    ((PixelDrawing)object).tick(graphic);
                }
            }
            if(!object.inBounds(gameBounds)) {
                if(object instanceof PhysicsObject)physicsObjects.remove(object);
                if(object instanceof CollisionObject) collisionObjects.remove(object);
                if(object instanceof BackgroundStars) numberOfBG--;
                player.removeGameObject(object);
                iterator.remove();
            }
        }
    }



}
