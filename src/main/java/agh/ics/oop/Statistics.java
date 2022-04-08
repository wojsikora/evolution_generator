package agh.ics.oop;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Statistics {

    public FileWriter fileWriter;
    public int avgAnimalNumCount;
    public int avgGrassNumCount;
    public int avgAverageEnergyCount;
    public int avgAverageLifespanCount;
    public int avgAverageChildrenNumCount;

    public int avgAnimalNum;
    public int avgGrassNum;
    public int avgAverageEnergy;
    public int avgAverageLifespan;
    public int avgAverageChildrenNum;

    public AbstractWorldMap map;

    public Statistics(AbstractWorldMap map, int num) throws IOException {

        this.map = map;
        this.fileWriter = new FileWriter("statisticsFile"+num+".csv");
        fileWriter.append("epoch, animal_number, grass_number, average_energy, average_lifespan, avg_child_num" + "\n");
        this.avgAnimalNumCount=0;
        this.avgGrassNumCount=0;
        this.avgAverageEnergyCount=0;
        this.avgAverageLifespanCount=0;
        this.avgAverageChildrenNumCount=0;

        this.avgAnimalNum=0;
        this.avgGrassNum=0;
        this.avgAverageEnergy=0;
        this.avgAverageLifespan=0;
        this.avgAverageChildrenNum=0;
    }

    public int getCurrentAnimalNumber(){
        return this.map.animals.size();
    }

    public int getCurrentGrassNumber(){
        return this.map.grassHashMap.size();
    }

    public String getDominantGenotype(){
        List<int[]> genotypes = new ArrayList<>();

        for(Animal a: this.map.animals){
            genotypes.add(a.genes.genotype);
        }

        int sameGenotypeCounter;
        int maxSameGenotypeNumber =1;
        int[] dominantGenotype = genotypes.get(0);

        for(int i=0; i<genotypes.size()-1; i++){
            sameGenotypeCounter=0;
            for(int j=i+1; j<genotypes.size(); j++){
                if(Arrays.equals(genotypes.get(i), genotypes.get(j))){
                    sameGenotypeCounter++;
                }
            }
            if(maxSameGenotypeNumber<sameGenotypeCounter){
                maxSameGenotypeNumber = sameGenotypeCounter;
                dominantGenotype = genotypes.get(i);
            }
        }

        if(maxSameGenotypeNumber>1){

            String dominantGenotypeString= " ";
            for(int i=0; i<dominantGenotype.length; i++){
                dominantGenotypeString+=dominantGenotype[i];
                dominantGenotypeString+=" ";
            }
            return dominantGenotypeString;
        }
        else{
            return "There is not any dominant genotype";
        }

    }

    public int getAverageAliveAnimalEnergy(){
        int count =0;
        if(this.map.animals.size()==0) return 0;
        for(Animal a: this.map.animals){
            count+=a.energy;
        }
        return count / this.map.animals.size();
    }

    public int getAverageDeadAnimalLifespan(){
        int count =0;
        if(this.map.deadAnimals.size()==0) return 0;
        for(Animal a: this.map.deadAnimals){
            count+=a.age;
        }
        return count / this.map.deadAnimals.size();
    }

    public int getAverageAliveAnimalChildrenNumber(){
        int count = 0;
        if(this.map.animals.size()==0) return 0;
        for(Animal a: this.map.animals){
            count+=a.childrenNumber;
        }

        return count / this.map.animals.size();
    }

    public void saveStatisticsToCSVFile() throws IOException {
        this.avgAnimalNumCount+=getCurrentAnimalNumber();
        this.avgGrassNumCount+=getCurrentGrassNumber();
        this.avgAverageEnergyCount+=getAverageAliveAnimalEnergy();
        this.avgAverageLifespanCount+=getAverageDeadAnimalLifespan();
        this.avgAverageChildrenNumCount+= getAverageAliveAnimalChildrenNumber();
        this.fileWriter.append(this.map.epoch + ","+getCurrentAnimalNumber() + "," + getCurrentGrassNumber() + ","
        + getAverageAliveAnimalEnergy() + ","+ getAverageDeadAnimalLifespan() + "," + getAverageAliveAnimalChildrenNumber() +"\n");
    }

    public void addAverageStatisticsLine() throws IOException {

        this.avgAnimalNum =this.avgAnimalNumCount / this.map.epoch;
        this.avgGrassNum= this.avgGrassNumCount/ this.map.epoch;
        this.avgAverageEnergy= this.avgAverageEnergyCount/ this.map.epoch;
        this.avgAverageLifespan= this.avgAverageLifespanCount/ this.map.epoch;
        this.avgAverageChildrenNum= this.avgAverageChildrenNumCount/ this.map.epoch;;

        this.fileWriter.append("Average values" + ": "+ this.avgAnimalNum + "," + this.avgGrassNum + "," + this.avgAverageEnergy + ","
        + this.avgAverageLifespan + "," + this.avgAverageChildrenNum+ "\n");

        fileWriter.flush();
        fileWriter.close();
    }


}
