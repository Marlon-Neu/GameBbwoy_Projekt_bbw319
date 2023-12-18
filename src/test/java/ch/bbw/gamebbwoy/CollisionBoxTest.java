package ch.bbw.gamebbwoy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollisionBoxTest {
    CollisionBox cb0 = new CollisionBox(5,5,5,5,new Point(20,15));
    CollisionBox cb1 = new CollisionBox(6,6,6,6,new Point(26,26));
    CollisionBox cb2 = new CollisionBox(2,2,2,2,new Point(28,28));

    @Test
    public void collidesTest(){
        assertTrue(cb0.collides(cb0));
        assertTrue(cb0.collides(cb1));
        assertFalse(cb0.collides(cb2));
        assertTrue(cb1.collides(cb2));
        assertTrue(cb2.collides(cb1));
        assertFalse(cb2.collides(cb0));
    }

}