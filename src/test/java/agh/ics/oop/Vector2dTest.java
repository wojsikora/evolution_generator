package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Vector2dTest {

    @Test
    public void equalsTest(){

        assertTrue(new Vector2d(1,1).equals(new Vector2d(1,1)));
    }

    @Test
    public void toStringTest(){
        assertEquals(new Vector2d(5,5).toString(), "(5,5)");
    }

    @Test
    public void precedesTest(){

        assertTrue(new Vector2d(1,1).precedes(new Vector2d(2,2)));
    }

    @Test
    public void followsTest(){
        assertTrue(new Vector2d(2,2).follows(new Vector2d(1,1)));
    }

    @Test
    public void upperRightTest(){
        assertEquals(new Vector2d(1,1).upperRight(new Vector2d(2,2)), new Vector2d(2,2));
    }

    @Test
    public void lowerLeftTest(){
        assertEquals(new Vector2d(1,1).lowerLeft(new Vector2d(2,2)), new Vector2d(1,1));
    }

//    @Test
//    public void addTest(){
//        assertEquals(new Vector2d(1,1).add(new Vector2d(2,2)), new Vector2d(3,3));
//    }

//    @Test
//    public void subtractTest(){
//        assertEquals(new Vector2d(4,4).subtract(new Vector2d(2,2)), new Vector2d(2,2));
//    }

    @Test
    public void oppositeTest(){
        assertEquals(new Vector2d(1,1).opposite(), new Vector2d(-1,-1));
    }


}
