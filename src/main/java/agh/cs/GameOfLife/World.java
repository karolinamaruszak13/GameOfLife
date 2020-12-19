package agh.cs.GameOfLife;

import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.IOException;

import static agh.cs.GameOfLife.MapDirection.NORTH;

class JEBACPIS extends JPanel{
    private IMapElement mapElement;
    private String element;

    JEBACPIS(IMapElement mapElement){
        this.mapElement = mapElement;
        if (mapElement != null) {
            this.element = mapElement.getElement();
        }
        else {
            this.element = null;
        }
    }

    @Override
    public void paintComponent(java.awt.Graphics graphics){
        super.paintComponent(graphics);
        if (element == null){
            graphics.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        else if (element.equals("animal")){
            graphics.setColor(Color.blue);
            graphics.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
        else if(element.equals("grass")){
            graphics.setColor(Color.yellow);
            graphics.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }

    }
    public void setElement(Animal animal){
        if (animal != null){
            this.element = animal.getElement();
        }
        else {
            this.element = null;
        }
    }



}

class Pane extends WindowAdapter{
    Pane(JPanel p){
        JFrame f = new JFrame();
        Container contentPane = f.getContentPane();
        contentPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 0,0));
        f.setPreferredSize(new Dimension(1200, 1000));
        f.add(p);
        f.pack();
        f.show();

    }
}

public class World {
    public static void main(String[] args) throws IOException, ParseException {
//        JSONParser jsonParser = new JSONParser();
//        JSONObject a = (JSONObject) jsonParser.parse(new FileReader("parameters.json"));
//        System.out.println(a.get("name"));
//        View v = new View();
        GrassField map1 = new GrassField(10);
        Animal a = new Animal(map1, new Vector2d(10,10),NORTH, 3, 0, 4 );
        Animal b = new Animal(map1, new Vector2d(10,10),NORTH, 3, 0, 4 );
        map1.placeAnimal(a);
        map1.placeAnimal(b);



        final int s = 20;
        final JEBACPIS[][] biosphere = new JEBACPIS[s][s];
        final JPanel gui = new JPanel(new GridLayout(s, s, 2, 2));
        for (int ii = 0; ii < s; ii++) {
            for (int jj = 0; jj < s; jj++) {
                JEBACPIS cell = new JEBACPIS(map1.fff(ii, jj));
                cell.setPreferredSize(new Dimension(5, 5));
                gui.add(cell);
                biosphere[ii][jj] = cell;
            }
        }
        gui.setPreferredSize(new Dimension(1000, 1000));
        ActionListener actionListener = (ActionEvent actionEvent) -> {
            map1.reproduction();
            map1.movement();
            for (int ii = 0; ii < s; ii++) {
                for (int jj = 0; jj < s; jj++) {
                    biosphere[ii][jj].setElement(map1.fff(ii,jj));
                }
            }
            gui.repaint();
            System.out.println("oo");

        };
        Timer timer = new Timer(1000, actionListener);
        timer.start();
//        Pane p = new Pane(gui);
        JOptionPane.showMessageDialog(null, gui);





    }


//        try {
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





}