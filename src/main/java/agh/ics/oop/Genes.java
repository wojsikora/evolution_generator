package agh.ics.oop;

import java.util.Arrays;

public class Genes {

    protected int[] genotype;

    public Genes(){
        this.genotype = new int[32];
    }

    public void generateGenes(){
        for(int i=0; i<32; i++){
            int x =  (int)(Math.random()*8);
            genotype[i] = x;
        }
        Arrays.sort(genotype);
    }

    private void dividingProcess(int leftOrRight, Animal firstParent, Animal secondParent ){
        int ratio = (firstParent.energy/(firstParent.energy+secondParent.energy));
        if(leftOrRight%2==0){
            for(int i=0; i<(int)(ratio*32); i++){
                genotype[i] = firstParent.genes.genotype[i];
            }
            for(int i=(int)(ratio*32); i<32; i++){
                genotype[i] = secondParent.genes.genotype[i];
            }
        }
        else{
            for(int i=0; i<32 -(int)(ratio*32); i++){
                genotype[i] = secondParent.genes.genotype[i];
            }
            for(int i=32 -(int)(ratio*32); i<32; i++){
                genotype[i] = firstParent.genes.genotype[i];
            }
        }
    }

    protected void divideInheritedGenes(Animal firstParent, Animal secondParent){
        int x = (int) (Math.random()*2);
        if(firstParent.energy >= secondParent.energy){
            dividingProcess(x, firstParent, secondParent);
        }
        else{
            dividingProcess(x, secondParent, firstParent);
        }
    }

    protected int chooseDirectionRandomly(){
        int i = (int) (Math.random()*32);
        return this.genotype[i];
    }

}
