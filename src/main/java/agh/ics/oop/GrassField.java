package agh.ics.oop;

import java.util.*;


public class GrassField extends AbstractWorldMap implements IWorldMap, IPositionChangeObserver{

    protected int grassNumber = 10;
    protected int maxWidth;
    protected int maxHeight;
    private  MapVisualizer mapVisualizer;

    private int width;
    private int height;
    public int plantEnergy;
    public int jungleWidth;
    public int jungleHeight;


    public GrassField(int grassNumber){
        this.grassNumber=grassNumber;
        this.maxWidth = 10;
        this.maxHeight =5;
        this.animalMap = new Animal[maxWidth][maxHeight];
        this.mapVisualizer = new MapVisualizer(this);

        placeGrass();
    }

    public GrassField(int mapType, int width, int height, int plantEnergy, double jungleRatio){
        this.mapType = mapType;
        this.width = width;
        this.height = height;
        this.plantEnergy = plantEnergy;
        this.jungleWidth = (int)(this.width*jungleRatio);
        this.jungleHeight = (int)(this.height*jungleRatio);
        this.jungleLowerLeft = new Vector2d((this.width - this.jungleWidth)/2, (this.height - this.jungleHeight)/2);
        this.jungleUpperRight = new Vector2d(this.jungleWidth-1+(this.width - this.jungleWidth)/2,
                this.jungleHeight-1+(this.height - this.jungleHeight)/2);

        this.epoch=0;

    }

    public void placeGrass()
    {

        int x = (int)(Math.random()*(this.width));
        int y = (int)(Math.random()*(this.height));

        while(isOccupied(new Vector2d(x, y)) || new Vector2d(x,y).follows(this.jungleLowerLeft) &&
                new Vector2d(x,y).precedes(this.jungleUpperRight))
        {

            x = (int)(Math.random()*(this.width));
            y = (int)(Math.random()*(this.height));

        }

        Grass grass = new Grass(new Vector2d(x, y));
        grassHashMap.put(grass.getPosition(), grass);

    }

    public void placeGrassInJungle(){
        Random random = new Random();
        int a = random.ints(this.jungleLowerLeft.x, this.jungleUpperRight.x+1).findFirst().getAsInt();
        int b = random.ints(this.jungleLowerLeft.y, this.jungleUpperRight.y+1).findFirst().getAsInt();
        while(isOccupied(new Vector2d(a, b)))
        {

            a = random.ints(this.jungleLowerLeft.x, this.jungleUpperRight.x+1).findFirst().getAsInt();
            b = random.ints(this.jungleLowerLeft.y, this.jungleUpperRight.y+1).findFirst().getAsInt();
        }

        Grass jungleGrass = new Grass(new Vector2d(a, b));
        grassHashMap.put(jungleGrass.getPosition(), jungleGrass);
    }


    @Override
    public boolean isOccupied(Vector2d position)
    {
        return grassHashMap.containsKey(position) || animalHashMap.containsKey(position) && !animalHashMap.get(position).isEmpty();
    }

    public Animal getAnimalWithBiggestEnergy(List<Animal> animalList){
        Animal animal = animalList.get(1);
        for(Animal a: animalList){
            if(a.energy>animal.energy){
                animal=a;
            }
        }
        return animal;
    }




    @Override
    public Object objectAt(Vector2d position) {
        if(isOccupied(position))
        {
            if (grassHashMap.containsKey(position)) return grassHashMap.get(position);
            else if(this.animalHashMap.get(position).size()>1){
                return getAnimalWithBiggestEnergy(this.animalHashMap.get(position));
            }
            else if(this.animalHashMap.get(position).size()==1) return this.animalHashMap.get(position).get(0);
        }

        return null;
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
           if (position.follows(new Vector2d(0, 0)) && position.precedes(new Vector2d(width-1, height-1))) {

               return true;
           }

        return false;
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            if(animalHashMap.containsKey(animal.getPosition())){
                animalHashMap.get(animal.getPosition()).add(animal);
            }
            else{
                List<Animal> newList = new ArrayList<>();
                newList.add(animal);
                animalHashMap.put(animal.getPosition(), newList);
            }

            this.mapBoundary.xSorted.add(animal.getPosition());
            this.mapBoundary.ySorted.add(animal.getPosition());

            return true;
        }
        throw new IllegalArgumentException("Cannot access position:" + animal.getPosition().toString());
    }

    public void eatPlant(){
        for(Animal a: animals){
            if(grassHashMap.containsKey(a.getPosition())){
                grassHashMap.remove(a.getPosition());
                a.boostEnergy(plantEnergy);
            }
        }
    }

    public void takeEnergy(){
     for(Animal a: animals){
         a.energy-=5;
     }
    }

    public void deleteDeadAnimal(){

        for(int i=0; i<animals.size(); i++){
            if(animals.get(i).energy<=0) {
                Animal a = animals.get(i);
                animals.remove(a);
                animalHashMap.get(a.getPosition()).remove(a);
            }
        }
    }


    @Override
    public String toString(){
        return mapVisualizer.draw(new Vector2d(0, 0), new Vector2d(this.maxWidth, this.maxHeight));
    }

    public Vector2d getLowerLeft(){
        return this.mapBoundary.lowerLeft();
    }

    public Vector2d getUpperRight(){
        return this.mapBoundary.upperRight();

    }


}
