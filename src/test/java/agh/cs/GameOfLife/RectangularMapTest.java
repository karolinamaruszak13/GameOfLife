package agh.cs.GameOfLife;
import org.junit.Test;

import static org.junit.Assert.*;

public class RectangularMapTest {
    @Test
    public void runTest(){
        String[] dir = {"f","b","r","l","f","f","r","r","f","f","f","f","f","f","f","f"};

        MoveDirection[] directions = new OptionsParser().parse(dir).toArray(new MoveDirection[0]);
        IWorldMap map = new RectangularMap(10, 5);
        Animal a1 = new Animal(map);
        Animal a2 = new Animal(map,new Vector2d(1,1));
        map.place(a1);
        map.place(a2);
        map.run(directions);
        System.out.println(map.toString());
        assertEquals(a1, map.objectAt(new Vector2d(1,3)));
        assertEquals(a2, map.objectAt(new Vector2d(0,4)));
        assertEquals( a1.getOrientation(), MapDirection.SOUTH);
        assertEquals( a2.getOrientation(), MapDirection.NORTH);



    }

}