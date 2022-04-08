package agh.ics.oop;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MapTest {
    @Test
    public void placeTest(){

        AbstractWorldMap map = new RectangularMap(10, 5);
        Animal animal = new Animal(map, new Vector2d(2,3));
        map.place(animal);
        assertTrue(map.isOccupied(animal.getPosition()));
    }

    @Test
    public void engineTest(){
        String[] args ={"r", "f", "r", "f"};
        MoveDirection[] directions = new OptionsParser().parse(args);
        AbstractWorldMap map = new RectangularMap(10, 7);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();
        System.out.println(map);
        assertTrue(map.isOccupied(new Vector2d(2,2)));
        assertTrue(map.isOccupied(new Vector2d(3,6)));
    }

    @Test
    public void exceptionsTest() {
        try{
            String[] args ={"r", "f", "r", "f", "w"};
            MoveDirection[] directions = new OptionsParser().parse(args);
            AbstractWorldMap map = new RectangularMap(10, 7);
            Vector2d[] positions = { new Vector2d(2,2), new Vector2d(2,2) };
            IEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();
            System.out.println(map);
        }catch (IllegalArgumentException ex){
            System.out.println(ex.getMessage());

        }

    }


}
