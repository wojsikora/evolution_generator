package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationMap {

    public AbstractWorldMap map;
    public Settings settings;
    public int magicNumber;
    public int simulationType;

    public SimulationMap(AbstractWorldMap map, Settings settings, int simulationType){

        this.map = map;
        this.settings = settings;
        this.magicNumber =0;
        this.simulationType= simulationType;
    }


    public void generateStartingAnimals(){
        for(int i=0; i<this.settings.startAnimalNumber; i++){
            int x = (int)(Math.random()*(this.settings.width));
            int y = (int)(Math.random()*(this.settings.height));
            Animal animal = new Animal(this.map, new Vector2d(x,y),this.settings, this.settings.startEnergy);
            animal.genes.generateGenes();
            this.map.place(animal);
            this.map.animals.add(animal);
        }
    }

    public void simulationProcess(){
        this.map.epoch++;
        removeDeadAnimals();
        moveAnimals();
        consumePlants();
        animalReproduction();
        generateNewPlants();
        if(this.simulationType==1){
            if(this.magicNumber<3){
                if(this.map.animals.size()==5){
                    generateNewCopiesAnimal();
                }
            }
        }
    }



    public void removeDeadAnimals(){
        for(int i=0; i<this.map.animals.size(); i++){
            if(this.map.animals.get(i).energy<=0) {
                Animal a = this.map.animals.get(i);
                this.map.animals.remove(a);
                this.map.animalHashMap.get(a.getPosition()).remove(a);
                this.map.deadAnimals.add(a);
            }
        }
    }

    public void moveAnimals(){
        if(!this.map.animals.isEmpty()) {
            for (Animal a : this.map.animals) {
                a.energy -= this.settings.moveEnergy;
                a.age+=1;
                a.moveSimulation();
            }
        }
    }

    public List<Animal> getAllAnimalsWithBiggestEnergy(Vector2d position){
        List<Animal> animalsWithBiggestEnergy = new ArrayList<>();
        int biggestEnergy = 0;
        for(Animal a: this.map.animals){
            if(a.energy>biggestEnergy){
                biggestEnergy = a.energy;
            }
        }

        for(Animal a: this.map.animals){
            if(a.energy == biggestEnergy){
                animalsWithBiggestEnergy.add(a);
            }
        }
        return animalsWithBiggestEnergy;
    }

    public List<Animal> getTwoAnimalsWithBiggestEnergy(Vector2d position){
        List<Animal> twoAnimalsWithBiggestEnergy = getAllAnimalsWithBiggestEnergy(position);

        if(twoAnimalsWithBiggestEnergy.size()<2){
            int biggestEnergy = twoAnimalsWithBiggestEnergy.get(0).energy;
            int secondBiggestEnergy = 0;
            for(Animal a: this.map.animals){
                if(a.energy>secondBiggestEnergy && a.energy<biggestEnergy){
                    secondBiggestEnergy = a.energy;
                }
            }

            for(Animal a: this.map.animals){
                if(a.energy == secondBiggestEnergy){
                    twoAnimalsWithBiggestEnergy.add(a);
                }
            }
        }

        return twoAnimalsWithBiggestEnergy;
    }


    public void consumePlants(){
        for(Animal a: this.map.animals){
            if(this.map.grassHashMap.containsKey(a.getPosition())){
                if(this.map.animalHashMap.get(a.getPosition()).size()>1){
                    List<Animal> animalsWithBiggestEnergy = getAllAnimalsWithBiggestEnergy(a.getPosition());
                    int energyPerAnimal = this.settings.plantEnergy/ animalsWithBiggestEnergy.size();
                    for(Animal animal: animalsWithBiggestEnergy){
                        animal.boostEnergy(energyPerAnimal);
                    }
                }
                else{
                    a.boostEnergy(this.settings.plantEnergy);
                }
                this.map.grassHashMap.remove(a.getPosition());

            }
        }
    }

    public void breed(Animal firstParent, Animal secondParent){
        if(firstParent.energy>=75 && secondParent.energy>=75){
            Animal child = new Animal(this.map, firstParent.getPosition(), this.settings, (int)(0.25*firstParent.energy) + (int)(0.25*secondParent.energy));
            firstParent.loseEnergyForChild();
            secondParent.loseEnergyForChild();
            child.genes.divideInheritedGenes(firstParent, secondParent);
            this.map.place(child);
            this.map.animals.add(child);

            firstParent.childrenNumber+=1;
            secondParent.childrenNumber+=1;

        }
    }

    public void animalReproduction(){
        for(int i=0; i<this.settings.width; i++){
            for(int j=0; j<this.settings.height; j++){

                if(this.map.animalHashMap.containsKey(new Vector2d(i,j))){
                    if(this.map.animalHashMap.get(new Vector2d(i,j)).size()>1){
                        List<Animal> animalsAtPosition = getTwoAnimalsWithBiggestEnergy(new Vector2d(i,j));

                        this.breed(animalsAtPosition.get(0), animalsAtPosition.get(1));


                    }
                }
            }
        }
    }

    public boolean checkIfInJungle(Vector2d position){
        return position.follows(this.map.jungleLowerLeft) && position.precedes(this.map.jungleUpperRight);
    }

    public boolean checkIfAnyPositionOutsideJungleEmpty(){

        for(int i=0; i<this.settings.width; i++){
            for(int j=0; j<this.settings.height; j++){

                if(!checkIfInJungle(new Vector2d(i,j))){
                    if(!this.map.isOccupied(new Vector2d(i,j))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkIfAnyPositionEmptyInJungle(){

        for(int i= this.map.jungleLowerLeft.x; i<= this.map.jungleUpperRight.x; i++) {
            for (int j = this.map.jungleLowerLeft.y; j <= this.map.jungleUpperRight.y; j++) {
                if (!this.map.isOccupied(new Vector2d(i, j))){

                    return true;
                }
            }
        }
        return false;
    }

    public void generateNewPlants(){
        if(checkIfAnyPositionOutsideJungleEmpty()){
            this.map.placeGrass();
        }

        if(checkIfAnyPositionEmptyInJungle()){
            this.map.placeGrassInJungle();
        }
    }

    public Vector2d notOccupiedPosition(){

        if(checkIfAnyPositionEmptyInJungle() || checkIfAnyPositionOutsideJungleEmpty()){
            int x = (int)(Math.random()*(this.settings.width));
            int y = (int)(Math.random()*(this.settings.height));

            while(this.map.animalHashMap.size()!=0){
                 x = (int)(Math.random()*(this.settings.width));
                 y = (int)(Math.random()*(this.settings.height));
            }

            return new Vector2d(x,y);
        }
        else return null;
    }

    public void generateNewCopiesAnimal(){
        for(Animal a: this.map.animals){
            Vector2d vector2d = notOccupiedPosition();
            if(vector2d==null) return;
            else {
                Animal animal = new Animal(this.map, vector2d, this.settings, this.settings.startEnergy);
                System.arraycopy(a.genes.genotype, 0, animal.genes.genotype, 0, 32);
                this.map.place(animal);
                this.map.animals.add(animal);
            }
        }
    }

}
