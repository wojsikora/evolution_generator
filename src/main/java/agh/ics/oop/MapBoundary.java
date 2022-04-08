package agh.ics.oop;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver{
    Comparator xComparator = new XComparator();
    Comparator yComparator = new YComparator();

    SortedSet<Vector2d> xSorted = new TreeSet<Vector2d>(xComparator);
    SortedSet<Vector2d> ySorted = new TreeSet<Vector2d>(yComparator);

    @Override
    public void positionChange(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        if(oldPosition.x != newPosition.x){
            xSorted.remove(oldPosition);
            xSorted.add(newPosition);
        }
        if(oldPosition.y != newPosition.y){
            ySorted.remove(oldPosition);
            ySorted.add(newPosition);
        }
    }

    public Vector2d lowerLeft() {
        return new Vector2d(this.xSorted.first().x, this.ySorted.first().y);

    }

    public Vector2d upperRight() {
        return new Vector2d(this.xSorted.last().x, this.ySorted.last().y);
    }
}
