package agh.cs.GameOfLife;

public class Grass implements IMapElement{
    public Vector2d position;

    public Grass(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition(){
        return position;
    }

    @Override
    public String getElement() {
        return "grass";
    }


    public String toString(){
        return "*";
//        return position.toString();
    }
    public boolean equals(Object other){
        return this.toString().equals(other.toString());
    }
}
