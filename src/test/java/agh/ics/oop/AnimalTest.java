package agh.ics.oop;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalTest {

    @Test
    public void orientationTest(){
        Animal animal = new Animal();
        String[] args = {"r", "f", "r"};
        OptionsParser parser = new OptionsParser();

        MoveDirection[] directions = parser.parse(args);
        for(MoveDirection d: directions){
            animal.move(d);
        }

        assertEquals(animal.getOrientation(), MapDirection.SOUTH);
    }

    @Test
    public void positionTest(){
        Animal animal = new Animal();
        String[] args = {"r", "f", "r"};
        OptionsParser parser = new OptionsParser();

        MoveDirection[] directions = parser.parse(args);
        for(MoveDirection d: directions){
            animal.move(d);
        }

        assertEquals(animal.getPosition(), new Vector2d(3,2));
    }

    @Test
    public void mapBoundaryTest1(){
        Animal animal = new Animal();
        String[] args = {"f", "f", "f", "f"};
        OptionsParser parser = new OptionsParser();

        MoveDirection[] directions = parser.parse(args);
        for(MoveDirection d: directions){
            animal.move(d);
        }

        assertEquals(animal.getPosition(), new Vector2d(2,4));
    }

    @Test
    public void mapBoundaryTest2(){
        Animal animal = new Animal();
        String[] args = {"r", "f", "f", "f"};
        OptionsParser parser = new OptionsParser();

        MoveDirection[] directions = parser.parse(args);
        for(MoveDirection d: directions){
            animal.move(d);
        }

        assertEquals(animal.getPosition(), new Vector2d(4,2));
    }

    @Test
    public void parsingCorrectnessTest(){
        String[] args = {"r", "f", "l", "b"};
        OptionsParser parser = new OptionsParser();
        MoveDirection[] directions = parser.parse(args);

        assertEquals(directions[0], MoveDirection.RIGHT);
        assertEquals(directions[1], MoveDirection.FORWARD);
        assertEquals(directions[2], MoveDirection.LEFT);
        assertEquals(directions[3], MoveDirection.BACKWARD);
    }
}
