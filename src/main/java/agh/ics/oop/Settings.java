package agh.ics.oop;

public class Settings {
      //mapType: 0 - with borders, 1 - wrapped
    public final int firstEvolutionType; //0 - classic, 1 - magic
    public final int secondEvolutionType;
    public final int width;
    public final int height;
    public final int startEnergy;
    public final int moveEnergy;
    public final int plantEnergy;
    public final double jungleRatio;
    public final int startAnimalNumber;

    public Settings(int firstEvolutionType, int secondEvolutionType, int width, int height, int startEnergy, int moveEnergy, int plantEnergy, double jungleRatio, int startAnimalNumber){
        this.firstEvolutionType = firstEvolutionType;
        this.secondEvolutionType = secondEvolutionType;
        this.width = width;
        this.height = height;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.jungleRatio = jungleRatio;
        this.startAnimalNumber = startAnimalNumber;

    }
}
