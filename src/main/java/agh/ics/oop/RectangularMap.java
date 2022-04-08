package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class RectangularMap extends AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    private final int width;
    private final int height;

    private MapVisualizer mapVisualizer;

    public RectangularMap(int width, int height){
        this.width = width;
        this.height = height;
        this.animalMap = new Animal[this.width][this.height];
        mapVisualizer = new MapVisualizer(this);

    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.precedes(new Vector2d(width - 1, height - 1)) && position.follows(new Vector2d(0, 0)) && !isOccupied(position);
    }



    @Override
    public boolean place(Animal animal) {
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position)
    {
        return animalMap[position.x][position.y] != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        if(animalMap[position.x][position.y] !=null) return animalMap[position.x][position.y];
        return null;
    }

    @Override
    public String toString(){
        return mapVisualizer.draw(new Vector2d(0, 0), new Vector2d(this.width, this.height));
    }

    @Override
    public Vector2d getLowerLeft() {
        return this.mapBoundary.lowerLeft();
    }

    @Override
    public Vector2d getUpperRight() {
        return this.mapBoundary.upperRight();
    }


}
