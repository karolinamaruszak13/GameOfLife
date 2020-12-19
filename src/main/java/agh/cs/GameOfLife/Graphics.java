package agh.cs.GameOfLife;
import java.awt.*;

public class Graphics extends Frame {
    Graphics(){
//        GrassField map1 = new GrassField(10);
//        Animal a = new Animal(map1, new Vector2d(2,2),NORTH, 3, 0, 4 );
//        Animal b = new Animal(map1, new Vector2d(2,2),SOUTH, 3, 0, 4 );
//        map1.placeAnimal(a);
//        map1.placeAnimal(b);
//        map1.reproduction();
//        map1.movement();
//        System.out.println(map1.animals);
        Button b=new Button("click me");
        b.setBounds(30,100,80,30);// setting button position


        add(b);
        setSize(300,300);
        setLayout(null);
        setVisible(true);
    }
}

//   try {
//            GrassField map1 = new GrassField(10);
//            Animal a = new Animal(map1, new Vector2d(2,2),NORTH, 3, 0, 4 );
//            Animal b = new Animal(map1, new Vector2d(2,2),SOUTH, 3, 0, 4 );
//            map1.placeAnimal(a);
//            map1.placeAnimal(b);
//            System.out.println(map1.toString()); // mapa przed wykonaniem ruchow zwierzaczkow
//            map1.reproduction();
//            map1.movement();
//            System.out.println(map1.animals);
//
//            System.out.println(map1.toString()); // mapa po wykonaniu ruchow zwierzaczkow
//
//        }
//        catch(IllegalArgumentException exception) {
//            System.out.println(exception);
//        }