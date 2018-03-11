package pl.michal.repository;


import pl.michal.domain.Animal;

import java.util.List;

public interface AnimalRepository {
    public void initDatabase();
    public Animal getById(long id);
    public List<Animal> getAll();
    public void add(Animal animal);
    public void delete(Animal animal);
    public void update(long oldId, Animal newAnimal);
}
