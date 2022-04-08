package agh.ics.oop;

import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;


public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{

    protected  int width;
    protected  int height;
    public int mapType;
    public int jungleWidth;
    public int jungleHeight;
    public Vector2d jungleLowerLeft;
    public Vector2d jungleUpperRight;
    public List<Animal> animals = new ArrayList<>();
    protected List<Animal> deadAnimals = new ArrayList<>();
    public Animal[][] animalMap;
    LinkedHashMap<Vector2d, List<Animal>> animalHashMap = new LinkedHashMap<>();
    protected MapBoundary mapBoundary = new MapBoundary();
    LinkedHashMap<Vector2d, Grass> grassHashMap = new LinkedHashMap<>();

    public int epoch;





    @Override
    public boolean isOccupied(Vector2d position)
    {

        return this.animalHashMap.containsKey(position) && !this.animalHashMap.get(position).isEmpty();

    }

    public Animal getAnimalWithBiggestEnergy(List<Animal> animals){
        Animal animal = animals.get(0);
        for(Animal a: animals){
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
            if(this.animalHashMap.get(position).size()>1){

                return getAnimalWithBiggestEnergy(this.animalHashMap.get(position));
            }
            else if(this.animalHashMap.get(position).size()==1) return this.animalHashMap.get(position).get(0);

        }

    return null;
    }


    public void eatPlant(){

    }

    public void takeEnergy(){}

    public void deleteDeadAnimal(){

    }

    public void placeGrass(){}

    public void placeGrassInJungle(){}

    @Override
    public String toString(){
        MapVisualizer mapVisualizer = new MapVisualizer(this);
        return mapVisualizer.draw(new Vector2d(0, 0), new Vector2d(this.width, this.height));
    }

    @Override
    public void positionChange(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        animalHashMap.get(oldPosition).remove(animal);
        if(animalHashMap.containsKey(newPosition)){
            animalHashMap.get(newPosition).add(animal);
        }
        else{
            List<Animal> newList = new ArrayList<>();
            newList.add(animal);
            animalHashMap.put(newPosition, newList);
        }

    }

    public abstract  Vector2d getLowerLeft();

    public abstract Vector2d getUpperRight();


}
