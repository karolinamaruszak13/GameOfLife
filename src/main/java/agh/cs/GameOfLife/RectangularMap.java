package agh.cs.GameOfLife;

class RectangularMap extends AbstractWorldMap {
    public int width;
    public int height;

    public RectangularMap(int width, int height){
        this.width = width;
        this.height = height;
    }
    public String toString(){
        MapVisualizer v = new MapVisualizer(this);
        return "haha";
//        return v.draw(new Vector2d(0,0), new Vector2d(width, height));
    }


    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
//        return isOccupiedBy(animals, position);
        return true;
    }

    @Override
    public Object objectAt(Vector2d position) {
//        return _objectAt(animals, position);
        return null;

    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
    }
}
