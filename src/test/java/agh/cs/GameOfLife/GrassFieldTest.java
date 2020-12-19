package agh.cs.GameOfLife;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static agh.cs.GameOfLife.MoveDirection.*;
import static agh.cs.GameOfLife.MapDirection.*;
import static org.junit.Assert.*;

public class GrassFieldTest {
//    @Test
//    public void runTest(){
//        String[] dir = {"f","b","r","l","f","f","r","r","f","f","f","f","f","f","f","f"};
//
//        MoveDirection[] directions = new OptionsParser().parse(dir).toArray(new MoveDirection[0]);
//        GrassField map1 = new GrassField(10);
//        Animal a1 = new Animal(map1);
//        Animal a2 = new Animal(map1,new Vector2d(1,1));
//        map1.place(a1);
//        map1.place(a2);
//        map1.run(directions);
////        System.out.println(map1.toString());
//        assertEquals(a1, map1.objectAt(new Vector2d(1,998)));
//        assertEquals(a2, map1.objectAt(new Vector2d(0,4)));
//        assertEquals( a1.getOrientation(), MapDirection.SOUTH);
//        assertEquals( a2.getOrientation(), MapDirection.NORTH);
//    }

    @Test
    //AAA - Arrange Act Assert
    public void killTest(){
        // Arrange
        String[] dir = {"f","f"};
        GrassField map = new GrassField(10);
        MoveDirection[] directions = new OptionsParser().parse(dir).toArray(new MoveDirection[0]);
        Animal a1 = new Animal(map, new Vector2d(3, 1), NORTH, 2, 2, 4);
        Map<Vector2d, Animal> oneAnimal = new LinkedHashMap<>();
        Map<Vector2d, Animal> empty = new LinkedHashMap<>();
        oneAnimal.put(new Vector2d(3, 1),a1);

       // Act
        map.place(a1);
        //Assert
        assertTrue(oneAnimal.equals(map.getAnimals()));

        //Act
        map.run(directions);
        //Assert
        assertTrue(empty.equals(map.getAnimals()));
    }

    @Test
    public void energyTest(){
        String[] dir = {"f","f"};
        GrassField map = new GrassField(10);
        MoveDirection[] directions = new OptionsParser().parse(dir).toArray(new MoveDirection[0]);
        Animal a1 = new Animal(map, new Vector2d(3, 1), NORTH, 4, 2, 4);
        Map<Vector2d, Animal> oneAnimal = new LinkedHashMap<>();
        Map<Vector2d, Animal> empty = new LinkedHashMap<>();
        oneAnimal.put(new Vector2d(3, 1),a1);
        map.place(a1);
        assertTrue(oneAnimal.equals(map.getAnimals()));
        map.run(directions);
        assertTrue(empty.equals(map.getAnimals()));
    }

    @Test
    public void eatenGrassTest(){
        String[] dir = {"f","f"};
        GrassField map = new GrassField();
        MoveDirection[] directions = new OptionsParser().parse(dir).toArray(new MoveDirection[0]);
        Animal a1 = new Animal(map, new Vector2d(3, 1), NORTH, 4, 0, 4);
        Grass g1 = new Grass(new Vector2d(3, 2));
        Map<Vector2d, Animal> oneAnimal = new LinkedHashMap<>();
        Map<Vector2d, Grass> oneGrass = new LinkedHashMap<>();
        Map<Vector2d, Grass> empty = new LinkedHashMap<>();
        oneAnimal.put(new Vector2d(3, 1),a1);
        oneGrass.put(new Vector2d(3, 2), g1);
        map.place(a1);
        map.placeOneGrass(g1);
        assertTrue(oneGrass.equals(map.getGrasses()));
        map.run(directions);
        assertTrue(empty.equals(map.getGrasses()));
        assertEquals(8, a1.getEnergy());
    }

    //stworzyc caly swiat + dodac trwake na chama gdzies i sprawdzic zeby zwierzaczek zjadl trawke
    //assert czy trawka zniknela przy przemieszczeniu sie zwierzaczka na pole o jeden dalej za trawka
    //assert czy zwierzaczek ma wiecej enegii(plant energy)




}