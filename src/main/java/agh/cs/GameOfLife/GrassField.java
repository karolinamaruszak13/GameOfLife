package agh.cs.GameOfLife;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap {
    public int numberOfGrass;
    Map<Vector2d,Grass> grasses;
    public GrassField(int numberOfGrass) {
        this.numberOfGrass = numberOfGrass;
        this.grasses = new LinkedHashMap<>();
        placeGrass();

    }
    public GrassField(){
        this.grasses = new LinkedHashMap<>();
    }

    public Map<Vector2d, Grass> getGrasses(){
        return grasses;

    }



    public String toString(){
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        MapVisualizer v = new MapVisualizer(this);

        for (Map.Entry<Vector2d,ArrayList<Animal>> entry: animals.entrySet()) {
            ArrayList<Animal> animalList = entry.getValue();
            for (Animal animal: animalList) {
                if (animal.getPosition().x < minX) minX = animal.getPosition().x;
                if (animal.getPosition().y < minY) minY= animal.getPosition().y;

                if (animal.getPosition().x > maxX) maxX = animal.getPosition().x;
                if (animal.getPosition().y > maxY) maxY= animal.getPosition().y;

            }

        }

        for (Map.Entry<Vector2d,Grass> entry: grasses.entrySet()) {
            Grass grass = entry.getValue();
            if (grass.getPosition().x < minX) minX = grass.getPosition().x;
            if (grass.getPosition().y < minY) minY= grass.getPosition().y;

            if (grass.getPosition().x > maxX) maxX = grass.getPosition().x;
            if (grass.getPosition().y > maxY) maxY = grass.getPosition().y;
        }

        return v.draw(new Vector2d(minX,minY), new Vector2d(maxX, maxY));
    }

//
//    @Override
//    public boolean isOccupied(Vector2d position) {
//        boolean flag = isOccupiedBy(animals, position);
//        if (!flag){
//            return isOccupiedBy(grasses, position);
//        }
//        else{
//            return true;
//        }
//    }
//
    @Override
    public Object objectAt(Vector2d position) {
        Object object = null;
        if(grasses.containsKey(position)) {
            object = grasses.get(position);
        }

        if(animals.containsKey(position)){
            object = animals.get(position).get(0);
        }
        return object;

    }


    @Override
    public boolean isOccupied(Vector2d position){
        return !grasses.containsKey(position) || !animals.containsKey(position);
    }

    @Override
    public int getWidth() {
        return 1000;
    }

    @Override
    public int getHeight() {
        return 1000;
    }

    public void placeGrass() {
        int size = (int) sqrt(10 * numberOfGrass);
        List<Integer> randomList = IntStream.range(0, (size + 1) * size)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(randomList);
        for (int i = 0; i < (int)numberOfGrass/10; i++) {
            Vector2d v = new Vector2d(randomList.get(i)/size,randomList.get(i)%size);
            grasses.put(v,new Grass(v));
        }
    }
    public void placeOneGrass(Grass grass){
        grasses.put(grass.getPosition(), grass);
    }

    public void jungle(){
        int size = (int) sqrt(5 * numberOfGrass);
        List<Integer> randomList1 = IntStream.range(7, size)
                .boxed()
                .collect(Collectors.toList());
        List<Integer> randomList2 = IntStream.range(7, size)
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(randomList1);
        Collections.shuffle(randomList2);
        for (int i = 0; i < 5; i++) {
            Vector2d v = new Vector2d(randomList1.get(i),randomList2.get(i));
            System.out.println("jungle" + v);
            grasses.put(v,new JungleGrass(v));
        }
    }



    public void eatGrass(){
        HashMap<Vector2d, ArrayList<Animal>> animalCopy = new HashMap<Vector2d, ArrayList<Animal>>(animals);
        for (Map.Entry<Vector2d, ArrayList<Animal>> entry : animalCopy.entrySet()){
            ArrayList<Animal> arrayCopy = new ArrayList<Animal>(entry.getValue());
            for (Animal animal: arrayCopy) {
                if (grasses.containsKey(animal.getPosition())) {
                    ArrayList<Animal> animalsAtTheSamePosition = animals.get(animal.getPosition());
                    int maxEnergy = Integer.MIN_VALUE;
                    Animal strongestAnimal = animal;
                    for (Animal a : animalsAtTheSamePosition) {
                        if (a.getEnergy() > maxEnergy) {
                            maxEnergy = a.getEnergy();
                            strongestAnimal = a;
                        }
                    }
                    grasses.remove(animal.getPosition());
                    strongestAnimal.setEnergy(strongestAnimal.getEnergy() + strongestAnimal.getPlantEnergy());
                }
            }    }
    }
    @Override
    public void movement (){
        HashMap<Vector2d, ArrayList<Animal>> animalCopy = new HashMap<Vector2d, ArrayList<Animal>>(animals);
        for (Map.Entry<Vector2d, ArrayList<Animal>> entry : animalCopy.entrySet()){
             ArrayList<Animal> arrayCopy = new ArrayList<Animal>(entry.getValue());
            for (Animal animal: arrayCopy) {
                Vector2d position = animal.getPosition();
                getAnimals().get(animal.getPosition()).remove(animal);
                if (getAnimals().get(animal.getPosition()).isEmpty()){
                    getAnimals().remove(position);
                }
                animal.move();
                if(animal.getEnergy() > 0) {
                    if(getAnimals().containsKey(animal.getPosition())){
                        getAnimals().get(animal.getPosition()).add(animal);
                    }
                    else{
                        getAnimals().put(animal.getPosition(), new ArrayList<Animal>(){{add(animal);}});
                     }
                }
            }
        }
//        System.out.println(animals);
    }

    public IMapElement fff(int i, int j){
        if (animals.containsKey(new Vector2d(i,j))){
            return animals.get(new Vector2d(i,j)).get(0);
        }
        else if(grasses.containsKey(new Vector2d(i, j))) {
            return grasses.get(new Vector2d(i, j));

        }
        return null;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }
    public int number0fAnimals() {
        int x = 0;

        for (Map.Entry<Vector2d, ArrayList<Animal>> entry : animals.entrySet()) {
            ArrayList<Animal> animal = entry.getValue();
            for (Animal a : animal) {
                x += 1;

            }
        }
        return x;


    }
}
