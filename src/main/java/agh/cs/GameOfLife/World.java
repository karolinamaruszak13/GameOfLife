package agh.cs.GameOfLife;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.FileReader;
import java.io.IOException;

import static agh.cs.GameOfLife.MapDirection.NORTH;

class Statistics extends JPanel{
    private GrassField statisticsMap;
    private int daysPassed;


    Statistics(GrassField statisticsMap){
        this.statisticsMap = statisticsMap;
        daysPassed = 0;

    }

    @Override
    public void paintComponent(java.awt.Graphics graphics){
        super.paintComponent(graphics);
        this.setSize(800, 800);
        this.setLocation(0, 0);
        Font myFont = new Font ("Courier New", 1, 14);
        graphics.setFont(myFont);

        graphics.drawString( "Number of animals: " + statisticsMap.number0fAnimals(), 10, 30);
        graphics.drawString("Number of grasses: " + statisticsMap.numberOfGrasses(), 10, 70);
        graphics.drawString("Dominant genes: ", 10, 110);
        graphics.drawString(statisticsMap.getTheMostDominantGen() , 10, 130);
        graphics.drawString("Average standard of living: " + statisticsMap.meanEnergy() , 10, 170);
        graphics.drawString("Passed days: " + daysPassed , 10, 210);
        daysPassed += 1;

    }


}

class Cell extends JPanel {
    private IMapElement mapElement;
    private String element;


    Cell(IMapElement mapElement) {
        this.mapElement = mapElement;
        if (mapElement != null) {
            this.element = mapElement.getElement();
        } else {
            this.element = null;
        }

    }

    @Override
    public void paintComponent(java.awt.Graphics graphics) {
        super.paintComponent(graphics);
        if (element == null) {
            graphics.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        } else if (element.equals("animal")) {
            graphics.setColor(new Color(114, 57, 6));
            graphics.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        } else if (element.equals("JungleGrass")) {
            graphics.setColor(new Color(20, 68, 25));
            graphics.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        } else if (element.equals("grass")) {
            graphics.setColor(new Color(2, 132, 32));
            graphics.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }

    }

    public void setElement(IMapElement element) {
        if (element != null) {
            this.element = element.getElement();
        } else {
            this.element = null;
        }
    }
}


class Pane extends WindowAdapter {
    Pane(JPanel p, JButton pauseButton, JPanel stats) {
        JFrame f = new JFrame();
        pauseButton.setBounds(10, 10, 10, 10);
        Container contentPane = f.getContentPane();
        contentPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        f.setPreferredSize(new Dimension(2000, 1000));

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        pauseButton.setLocation(0, 0);
//        p.setSize(new Dimension(1, 1));
        f.add(pauseButton);
        f.add(p);
        stats.setSize(1,1);


        f.add(stats);

        f.pack();
        f.show();

        f.setVisible(true);
    }
}

public class World {
    private static boolean isPaused = false;

    public static void main(String[] args) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject parameters = (JSONObject) jsonParser.parse(new FileReader("parameters.json"));

        JButton pauseButton = new JButton("Pause");
        GrassField worldMap = new GrassField(Math.toIntExact((long)parameters.get("width")),  Math.toIntExact((long)parameters.get("height")), (Double) parameters.get("jungleRatio"));
        int min = 0;
        int maxWidth = worldMap.getWidth() - 1;
        int maxHeight = worldMap.getHeight() - 1;
        for (int i = 0; i < (long)parameters.get("numberOfAnimals"); i++) {
            int x = (int)(Math.random() * (maxWidth - min + 1) + min);
            int y = (int)(Math.random() * (maxHeight - min + 1) + min);

            worldMap.placeAnimal(new Animal(worldMap, new Vector2d(x, y), NORTH, Math.toIntExact((long)parameters.get("startEnergy")),
                    Math.toIntExact((long)parameters.get("moveEnergy")), Math.toIntExact((long)parameters.get("plantEnergy"))));
        }


        final int width = Math.toIntExact((long)parameters.get("width"));
        final int height = Math.toIntExact((long)parameters.get("height"));
        final Cell[][] biosphere = new Cell[height][width];
        final JPanel gui = new JPanel(new GridLayout(height, width, 2, 2));

//        gui.setPreferredSize();
        for (int ii = 0; ii < height; ii++) {
            for (int jj = 0; jj < width; jj++) {
                Cell cell = new Cell(worldMap.fff(ii, jj));
                cell.setPreferredSize(new Dimension(5, 5));


                gui.add(cell);
                biosphere[ii][jj] = cell;
            }
        }
        gui.setPreferredSize(new Dimension(1000, 1000));


//        gui.setMaximumSize(new Dimension(1000, 1000));

        Statistics panel = new Statistics(worldMap);
        panel.setPreferredSize(new Dimension(1000, 1000));

        ActionListener actionListener = (ActionEvent actionEvent) -> {
            if (!isPaused) {
                worldMap.movement();
                worldMap.reproduction();
                worldMap.eatGrass();
                worldMap.placeGrass();
                worldMap.jungle();

                for (int ii = 0; ii < height; ii++) {
                    for (int jj = 0; jj < width; jj++) {
                        biosphere[ii][jj].setElement(worldMap.fff(ii, jj));
                    }
                }
                panel.repaint();
                gui.repaint();


            }
            if (actionEvent.getSource() == pauseButton) {
                isPaused = !isPaused;
            }

        };

        Timer timer = new Timer(1000, actionListener);
        timer.start();
        Pane p = new Pane(gui, pauseButton, panel);
        pauseButton.addActionListener(actionListener);
    }
}