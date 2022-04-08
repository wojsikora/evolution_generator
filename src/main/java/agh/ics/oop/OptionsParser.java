package agh.ics.oop;


public class OptionsParser {

    public  MoveDirection[] parse(String[] arr)
    {

        MoveDirection[] directionArray  = new MoveDirection[arr.length];

        for(int i=0; i<arr.length; i++)
        {
            switch(arr[i]){

                case "f", "forward" -> directionArray[i] = (MoveDirection.FORWARD);
                case "b", "backward" -> directionArray[i] = (MoveDirection.BACKWARD);
                case "r", "right" -> directionArray[i] = (MoveDirection.RIGHT);
                case "l", "left" -> directionArray[i] = (MoveDirection.LEFT);
                default -> throw new IllegalArgumentException(arr[i] + " is not legal move specification");
            }
        }
        return directionArray;
    }

}
