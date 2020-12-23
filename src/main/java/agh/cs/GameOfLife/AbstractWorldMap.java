package agh.cs.GameOfLife;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    Map<Vector2d, ArrayList<Animal>> animals;

    public AbstractWorldMap() {
        this.animals = new LinkedHashMap<>();
    }

    @Override
    public Map<Vector2d, ArrayList<Animal>> getAnimals()  {
        return animals;
    }

    @Override
    public void reproduction(){
        int x = 0;
        HashMap<Vector2d, ArrayList<Animal>> animalCopy = new HashMap<Vector2d, ArrayList<Animal>>(animals);
        for (Map.Entry<Vector2d, ArrayList<Animal>> entry : animalCopy.entrySet()){
            ArrayList<Animal> a = entry.getValue();
            if (a.size() > 1){
                int maxEnergy1 = Integer.MIN_VALUE;
                int maxEnergy2 = Integer.MIN_VALUE;
                Animal strongestAnimal1 = null;
                Animal strongestAnimal2 = null;
                for (Animal animal: a) {
                    if(animal.getEnergy() > maxEnergy1){
                        maxEnergy1 = animal.getEnergy();
                        strongestAnimal1 = animal;
                    }
                }
                for (Animal animal: a) {
                    if(animal.getEnergy() > maxEnergy2){
                        if(animal != strongestAnimal1){
                            maxEnergy2 = animal.getEnergy();
                            strongestAnimal2 = animal;
                        }
                    }
                }

                if(strongestAnimal1.getEnergy() >= 0.5*strongestAnimal1.getStartEnergy() && strongestAnimal2.getEnergy() >= 0.5*strongestAnimal2.getStartEnergy()){
                    Animal childAnimal = new Animal(this, strongestAnimal1.getPosition(), MapDirection.NORTH, (int)(0.25*strongestAnimal1.getEnergy() + 0.25*strongestAnimal2.getEnergy()), strongestAnimal1.getMoveEnergy(), strongestAnimal1.getPlantEnergy(), strongestAnimal1.getGenes());
                    this.placeAnimal(childAnimal);
                    strongestAnimal1.setEnergy((int)(0.75*strongestAnimal1.getEnergy()));
                    strongestAnimal2.setEnergy((int)(0.75*strongestAnimal2.getEnergy()));

                }

            }
        }

    }



    @Override
    public  void placeAnimal(Animal animal) {
        Vector2d position = animal.getPosition();
        ArrayList<Animal> animalList = animals.get(position);
        if(animalList == null) {
            animalList = new ArrayList<Animal>();
            animalList.add(animal);
            animals.put(position, animalList);
        } else {
//            if(!animalList.contains(animal)) animalList.add(animal);
            animalList.add(animal);
        }
    }


    @Override
    public void movement (){
        HashMap<Vector2d, ArrayList<Animal>> animalCopy = new HashMap<Vector2d, ArrayList<Animal>>(animals);
        for (Map.Entry<Vector2d, ArrayList<Animal>> entry : animalCopy.entrySet()){
            ArrayList<Animal> a = entry.getValue();
            for (Animal animal: a) {
                Vector2d position = animal.getPosition();
                animals.remove(position);
                animal.move();
                if(animal.getEnergy() > 0) {
                    animals.get(animal.getPosition()).add(animal);
                }
            }
        }
    }



    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        ArrayList<Animal> animal = animals.get(oldPosition);
        this.animals.remove(oldPosition, animal);
        this.animals.put(newPosition, animal);

    }

    @Override
    public void remove(Animal animal){
        animals.get(animal.getPosition()).remove(animal);
        if (animals.get(animal.getPosition()).isEmpty()){
            animals.remove(animal.getPosition());
        }
//        getAnimals().remove(animal.getPosition());

    }
}
