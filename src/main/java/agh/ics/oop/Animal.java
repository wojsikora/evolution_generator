package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import static agh.ics.oop.Settings.*;

public class Animal implements IMapElement {
    private Vector2d position;
    private MapDirection orientation;
    private AbstractWorldMap map;
    protected List<IPositionChangeObserver> observers = new ArrayList<>();
    protected int energy;
    protected Genes genes;
    protected int age;
    protected int childrenNumber;

    public Settings settings;

    public Animal() {
        this.position = new Vector2d(2, 2);
        this.orientation = MapDirection.NORTH;
    }

    public Animal(AbstractWorldMap map){
        this.map = map;
        this.addObserver(this.map);
        //this.energy = startEnergy;
    }

    public Animal(AbstractWorldMap map, Vector2d initialPosition, Settings settings, int energy){
        this.map = map;
        this.position = initialPosition;
        this.orientation = getRandomOrientation();
        this.addObserver(this.map);
        this.settings = settings;
        this.energy = energy;
        this.genes = new Genes();
        this.age = 0;
        this.childrenNumber = 0;
    }

    public MapDirection getRandomOrientation(){
        int x = (int)(Math.random()*(8));
        MapDirection direction = null;
        switch (x){
            case 0 -> direction = MapDirection.NORTH;
            case 1 -> direction = MapDirection.NORTHEAST;
            case 2 -> direction = MapDirection.EAST;
            case 3 -> direction = MapDirection.SOUTHEAST;
            case 4 -> direction = MapDirection.SOUTH;
            case 5 -> direction = MapDirection.SOUTHWEST;
            case 6 -> direction  =MapDirection.WEST;
            case 7 -> direction = MapDirection.NORTHWEST;
        }
        return direction;
    }

    public void boostEnergy(int energy){
        if(this.energy+energy<=100) this.energy+=energy;
        else this.energy = 100;
    }

    public void loseEnergyForChild(){
        this.energy-=(int)(0.25*this.energy);
    }

    @Override
    public String toString() {
        return orientation.toString();
    }

    public boolean isAt(Vector2d position){
        return this.position == position;
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case RIGHT -> this.orientation = orientation.next();
            case LEFT -> this.orientation = orientation.previous();
            case FORWARD -> {
                Vector2d newPosition = position.add(this.orientation.toUnitVector(), this.settings, this.map.mapType);
                if(this.map.canMoveTo(newPosition))
                {

                    this.positionChanged(this.position, newPosition);

                    this.position = newPosition;
                }
            }
            case BACKWARD ->
                    {
                        Vector2d newPosition = position.subtract(this.orientation.toUnitVector(), this.settings, this.map.mapType);

                        if(map.canMoveTo(newPosition))
                        {

                            this.positionChanged(this.position, newPosition);
                            this.position = position.subtract(this.orientation.toUnitVector(), this.settings, this.map.mapType);
                        }
                    }

        }
    }

    public void moveSimulation(){

        int rotation = this.genes.chooseDirectionRandomly();
        if(rotation==0){
            Vector2d newPosition = position.add(this.orientation.toUnitVector(), this.settings, this.map.mapType);
            if(this.map.canMoveTo(newPosition))
            {

                this.positionChanged(this.position, newPosition);

                this.position = newPosition;
            }
        }
        else if(rotation==4){
            {
                Vector2d newPosition = position.subtract(this.orientation.toUnitVector(), this.settings, this.map.mapType);

                if(map.canMoveTo(newPosition))
                {

                    this.positionChanged(this.position, newPosition);
                    this.position = position.subtract(this.orientation.toUnitVector(), this.settings, this.map.mapType);
                }
            }
        }
        else{
            for(int i=0; i<rotation; i++){
                this.orientation = orientation.next();
            }
        }

    }

    public Vector2d getPosition(){
        return this.position;
    }

    public MapDirection getOrientation(){
        return this.orientation;
    }

    public void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver o:observers){

            o.positionChange(oldPosition, newPosition, this);

        }
    }

    public String basedOnEnergyColor(){
        if(this.energy<=20) return "red";
        if(this.energy<=40) return "yellow";
        if(this.energy<=60) return "brown";
        if(this.energy<=80) return "grey";
        else return "black";
    }

    @Override
    public String getUrl() {
        String url = "";
        String color = basedOnEnergyColor();
        switch(this.toString()){
            case "N" -> url = "src/main/resources/up"+color+".png";
            case "E" -> url = "src/main/resources/right"+color+".png";
            case "S" -> url = "src/main/resources/down"+color+".png";
            case "W" -> url = "src/main/resources/left"+color+".png";
            case "NE" -> url = "src/main/resources/upright"+color+".png";
            case "NW" -> url = "src/main/resources/upleft"+color+".png";
            case "SE" -> url = "src/main/resources/downright"+color+".png";
            case "SW" -> url = "src/main/resources/downleft"+color+".png";
        }
        return url;
    }

}
