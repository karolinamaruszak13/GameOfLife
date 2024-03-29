package agh.cs.GameOfLife;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.sqrt;

public class GrassField extends AbstractWorldMap {
    public int numberOfGrass;
    public int width;
    public int height;
    public double jungleRatio;
    Map<Vector2d,Grass> grasses;
    public GrassField(int numberOfGrass) {
        this.numberOfGrass = numberOfGrass;
        this.grasses = new LinkedHashMap<>();
        placeGrass();

    }

    public GrassField(int width, int height, double jungleRatio) {
        this.width = width;
        this.height = height;
        this.numberOfGrass = (int)Math.sqrt(width * height * 0.05);
        this.grasses = new LinkedHashMap<>();
        this.jungleRatio = jungleRatio;
        placeGrass();

    }
    public GrassField(){
        this.grasses = new LinkedHashMap<>();
    }

    public Map<Vector2d, Grass> getGrasses(){
        return grasses;

    }


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
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public  int getNumberOfGrass(){
        return numberOfGrass;
    }

    public void placeGrass() {

        List<Integer> randomList1 = IntStream.range(0, getWidth())
                .boxed()
                .collect(Collectors.toList());
        List<Integer> randomList2 = IntStream.range(0, getHeight())
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(randomList1);
        Collections.shuffle(randomList2);
        for (int i = 0; i < numberOfGrass; i++) {
            Vector2d v = new Vector2d(randomList1.get(i),randomList2.get(i));
            grasses.put(v,new Grass(v));
        }
    }
    public void placeOneGrass(Grass grass){
        grasses.put(grass.getPosition(), grass);
    }

    public void jungle(){
        int size = (int) sqrt(5 * numberOfGrass);
        int squareWidth =(int) Math.floor((double)getWidth() * jungleRatio);
        int squareHeight = (int)(getHeight() * jungleRatio);

        List<Integer> randomList1 = IntStream.range(((getHeight() - squareHeight) / 2), ((getHeight() + squareHeight) / 2))
                .boxed()
                .collect(Collectors.toList());
        List<Integer> randomList2 = IntStream.range(((getHeight() - squareHeight) / 2), ((getHeight() + squareHeight) / 2))
                .boxed()
                .collect(Collectors.toList());
        Collections.shuffle(randomList1);
        Collections.shuffle(randomList2);
        for (int i = 0; i < Math.min(squareWidth, squareHeight); i++) {
            Vector2d v = new Vector2d(randomList1.get(i),randomList2.get(i));
            grasses.put(v,new JungleGrass(v));
        }
    }

    public String meanEnergy(){
        int sum = 0;
        double mean = 0;
        int amount = 0;
        for (Map.Entry<Vector2d, ArrayList<Animal>> entry : animals.entrySet()){
            ArrayList<Animal> animal = entry.getValue();
            for (Animal a: animal) {
                sum += a.getEnergy();
                amount += 1;
            }
        }

        mean = (double)sum / amount;
        return String.valueOf(mean);
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
    public String getTheMostDominantGen(){
        HashMap<List<Integer>, Integer> genesCounter = new HashMap<List<Integer>, Integer>();
        for (Map.Entry<Vector2d, ArrayList<Animal>> entry : animals.entrySet()) {
            ArrayList<Animal> animal = entry.getValue();
            for (Animal a : animal) {
                List<Integer> gene = a.getGenes();
                if(genesCounter.containsKey(gene)){
                    genesCounter.put(gene, genesCounter.get(gene) +1);
                }
                else{
                    genesCounter.put(gene, 1);
                }

            }
        }

        return String.valueOf(genesCounter.entrySet().stream().max((entry1, entry2) -> Integer.compare(entry1.getValue(), entry2.getValue())).get().getKey());

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
    public String number0fAnimals() {
        int x = 0;

        for (Map.Entry<Vector2d, ArrayList<Animal>> entry : animals.entrySet()) {
            ArrayList<Animal> animal = entry.getValue();
            for (Animal a : animal) {
                x += 1;

            }
        }
        return String.valueOf(x);
    }

    public String numberOfGrasses(){

        return String.valueOf(grasses.size());

    }
}
