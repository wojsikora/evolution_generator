package agh.ics.oop;

import agh.ics.oop.gui.IAnimalChangeObserver;

import java.util.ArrayList;
import java.util.List;


public class SimulationEngine implements IEngine, Runnable{
    public MoveDirection[] moveDirections;
    public AbstractWorldMap map;
    public Vector2d[] positions;
    public int moveDelay;
    protected List<IAnimalChangeObserver> observers = new ArrayList<>();
    public SimulationMap simulationMap;
    public Settings settings;
    public int simulationType;
    public boolean flag = false;

    public SimulationEngine(MoveDirection[] moveDirections, AbstractWorldMap map, Vector2d[] positions){
        this.moveDirections = moveDirections;
        this.map= map;
        this.positions = positions;

    }

    public SimulationEngine(AbstractWorldMap map, Vector2d[] positions){

        this.map= map;
        this.positions = positions;

    }

    public SimulationEngine(AbstractWorldMap map, Settings settings, int simulationType){
        this.map= map;
        this.settings = settings;
        this.simulationType = simulationType;
        this.simulationMap = new SimulationMap(this.map, this.settings, this.simulationType);
        simulationMap.generateStartingAnimals();
    }

    public void setMoveDelay(int moveDelay){
        this.moveDelay=moveDelay;
    }

    public void addToObservers(IAnimalChangeObserver observer){
        observers.add(observer);
    }

    public void setMoveDirections(MoveDirection[] moveDirections){
        this.moveDirections = moveDirections;
    }

    @Override
    public void run() {

        while(true) {
           System.out.println(flag);
            if (flag) {

                this.simulationMap.simulationProcess();
                for (IAnimalChangeObserver o : observers) {
                    o.updateAnimalsOnMap();
                    try {
                        Thread.sleep(moveDelay);
                    } catch (InterruptedException exception) {
                        System.out.println(exception.getMessage());
                    }
                }
            }
        }
    }

}

