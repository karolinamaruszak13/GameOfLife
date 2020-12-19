package agh.cs.GameOfLife;



public interface IMapElement  {
    /**
     * Indicate the position of the object
     *
     * @return position of the object
     */
    public Vector2d getPosition();

    public String getElement();
}
