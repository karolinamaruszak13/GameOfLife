package agh.cs.GameOfLife;
import java.util.ArrayList;
import java.util.stream.Stream;

public class OptionsParser {


    public ArrayList<MoveDirection> parse(String[] items) {
        String[] array = Stream.of(MoveDirection.values()).map(MoveDirection::name).toArray(String[]::new);
        ArrayList<MoveDirection> directions = new ArrayList<>();
        for (String argument: items) {
            switch(argument){
                case "f":
                case "forward":
                    directions.add(MoveDirection.FORWARD);
                    break;
                case "b":
                case "backward":
                    directions.add(MoveDirection.BACKWARD);
                    break;
                case "r":
                case "right":
                    directions.add(MoveDirection.RIGHT);
                    break;
                case "l":
                case "left":
                    directions.add(MoveDirection.LEFT);
                    break;
                default:
                    throw new IllegalArgumentException(argument + " is not legal move specification");
            }
        }

        return directions;


    }
}
