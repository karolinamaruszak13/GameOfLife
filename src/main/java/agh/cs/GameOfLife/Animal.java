package agh.cs.GameOfLife;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Animal implements IMapElement {
    public IWorldMap map;
    public ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position;
    private int energy;
    private int startEnergy;
    private int moveEnergy;
    private int plantEnergy;
    private int[] genes;

   public Animal(){

   }

    public Animal(IWorldMap map) {
        this.map = map;
        this.position = new Vector2d(0,0);
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.position = initialPosition;
    }

    public Animal(IWorldMap map, Vector2d position, MapDirection orientation, int startEnergy, int moveEnergy, int plantEnergy){
        this.map = map;
        this.position = position;
        this.orientation = orientation;
        this.energy = startEnergy;
        this.startEnergy = startEnergy;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        genes = new int[32];
        int min = 0;
        int max = 7;
        for (int i = 0; i < genes.length; i++) {
            genes[i] = (int)(Math.random() * (max - min + 1) + min);
        }
        System.out.println(Arrays.toString(genes));
    }

    public boolean equals(Object other){
        return this.toString().equals(other.toString());
    }

//    public Animal move(MoveDirection direction){
//        if (direction == RIGHT && map.canMoveTo(orientation.next().toUnitVector())){
//            orientation = orientation.next();
//        }
//        else if(direction == LEFT && map.canMoveTo(orientation.previous().toUnitVector())){
//            orientation = orientation.previous();
//        }
//
//        else if (direction == FORWARD && map.canMoveTo(position.add(orientation.toUnitVector()))){
//            position = position.add(orientation.toUnitVector());
//        }
//        else if(direction == BACKWARD && map.canMoveTo(position.subtract(orientation.toUnitVector()))){
//            position = position.subtract(orientation.toUnitVector());
//        }
//        if (position.x < 0){
//            position = position.add(new Vector2d(map.getWidth()+1,0));
//        }
//        else if (position.x >= map.getWidth()+1){
//            position = position.subtract(new Vector2d(map.getWidth()+1,0));
//        }
//        if (position.y < 0){
//            position = position.add(new Vector2d(0,map.getHeight()+1));
//        }
//        else if (position.y >= map.getHeight()+1){
//            position = position.subtract(new Vector2d(0,map.getHeight()+1));
//        }
//        energy -= moveEnergy;
//        return new Animal(map, position, orientation, energy , moveEnergy, plantEnergy);
//    }

    public void move(){
//        System.out.println("Aktualna orientacja i pozycja: " + orientation + " " + position);
        int number_of_rotates = genes[new Random().nextInt(genes.length)];
        for( int i=0; i < number_of_rotates; i++){
            orientation = orientation.next();
        }
        position = position.add(orientation.toUnitVector());
        energy -= moveEnergy;
//        System.out.println("Aktualna orientacja i pozycja: " + orientation + " " + position + " " + number_of_rotates);

    }

    public int getEnergy(){
        return energy;
    }
    public int getStartEnergy(){
        return startEnergy;
    }
    public int getMoveEnergy(){
        return moveEnergy;
    }

    public int getPlantEnergy(){
        return plantEnergy;
    }
    public void setEnergy(int energy){
        this.energy = energy;
    }

    public  Vector2d getPosition(){
        return position;
    }

    @Override
    public String getElement() {
        return "animal";
    }

    public MapDirection getOrientation(){
        return orientation;
    }
    public String toString(){
        switch(orientation) {
            case NORTH: return "N";
            case NORTH_EAST: return  "NE";
            case EAST: return "E";
            case SOUTH_EAST: return "SE";
            case SOUTH: return "S";
            case SOUTH_WEST: return "SW";
            case WEST: return "W";
            case NORTH_WEST: return "NW";

        }
        return null;
    }

    void addObserver(IPositionChangeObserver observer){
        observers.add(observer);
    }

    void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }
    void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver o: observers) {
            o.positionChanged(oldPosition, newPosition);
            System.out.println("Position changed");
        }
    }
}



