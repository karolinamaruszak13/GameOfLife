package agh.cs.GameOfLife;

import java.util.Objects;

import static java.lang.Math.*;

public class Vector2d {
    public int x;
    public int y;

    public Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "(" + x + ", " + y + ")";
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public boolean precedes(Vector2d other){
        return (this.x <= other.x) && (this.y <= other.y);
    }

    public boolean follows(Vector2d other){
        return (this.x >= other.x) && (this.y >= other.y);
    }

    public  Vector2d upperRight(Vector2d other){
        return new Vector2d(max(this.x, other.x), max(this.y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(min(this.x, other.x), min(this.y, other.y));
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(abs(this.x - other.x), abs(this.y - other.y));
    }

    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

    public Vector2d opposite(){
        return new Vector2d(-this.x, -this.y);
    }
}
