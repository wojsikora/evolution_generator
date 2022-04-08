package agh.ics.oop;


import java.util.Objects;

public class Vector2d {

    public  int x;
    public  int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y=y;
    }

    @Override
    public String toString()
    {
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean precedes(Vector2d other)
    {
        return this.x<=other.x && this.y<=other.y;
    }

    public boolean follows(Vector2d other)
    {
        return this.x>=other.x && this.y>=other.y;
    }

    public Vector2d upperRight(Vector2d other)
    {
        return new Vector2d(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other)
    {
        return new Vector2d(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    private Vector2d changeIfWrapped(Vector2d vector, Settings settings){

            if (vector.x >= settings.width) vector.x = 0;
            if (vector.x < 0) vector.x = settings.width - 1;
            if (vector.y >= settings.height) vector.y = 0;
            if (vector.y < 0) vector.y = settings.height - 1;
        return vector;
    }

    public Vector2d add(Vector2d other, Settings settings, int mapType)
    {
        Vector2d  newVector = new Vector2d(this.x+other.x, this.y+other.y);
        if(mapType==1) changeIfWrapped(newVector, settings);
        return newVector;
    }

    public Vector2d subtract(Vector2d other, Settings settings, int mapType)
    {
        Vector2d  newVector = new Vector2d(this.x-other.x, this.y-other.y);
        if(mapType==1) changeIfWrapped(newVector, settings);
        return newVector;
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    public Vector2d opposite()
    {
        return new Vector2d(this.x*(-1), this.y*(-1));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x*17, this.y*23);
    }
}
