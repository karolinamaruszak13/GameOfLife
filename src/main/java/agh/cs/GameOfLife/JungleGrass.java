package agh.cs.GameOfLife;

public class JungleGrass extends Grass{

    public JungleGrass(Vector2d position) {
        super(position);
    }
    @Override
    public String getElement() {
        return "JungleGrass";
    }
}
