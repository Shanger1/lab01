package pl.michal;

import org.junit.Before;
import org.junit.Test;
import pl.michal.domain.Animal;
import pl.michal.repository.AnimalRepository;
import pl.michal.repository.AnimalRepositoryFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AnimalTest {
    AnimalRepository animalRepository;

    @Test
    public void testDog() {
        Animal animal = new Animal();
        assertNotNull(animal);
    }

    @Test
    public void testGetById() {
        Long findId = (long) 1;
        assertNotNull(animalRepository.getById(findId));
    }

    @Test
    public void testGetAll() {
        assertNotNull(animalRepository.getAll());
    }

    @Test
    public void testAdd() {
        Animal dog = new Animal();
        dog.setId((long) 1);
        dog.setAge(12);
        dog.setName("Adar");
        dog.setNumberOfLegs(4);

        animalRepository.add(dog);
        assertNotNull(animalRepository.getById(dog.getId()));
        assertThat(dog.getId(), is(1));
        assertThat(dog.getName(), containsString("dar"));
        assertEquals(4, dog.getNumberOfLegs());

    }

    @Test
    public void testDelete() {
        Animal dog = animalRepository.getById((long) 1);
        animalRepository.delete(dog);

        if (animalRepository.getAll().size() > 0) {
            assertNotNull(animalRepository.getAll());
        }

        assertNull(animalRepository.getById(dog.getId()));

    }

    @Test
    public void testUpdate() {
        Animal dog = new Animal();
        dog.setId((long) 1);
        dog.setAge(12);
        dog.setName("Adar");
        dog.setNumberOfLegs(4);
        Long animalToUpdate = (long) 1;

        animalRepository.update(animalToUpdate, dog);
        assertEquals(animalRepository.getById(animalToUpdate).getName(), dog.getName());

        for (Animal animal : animalRepository.getAll()) {
            if (dog.getId() == animalToUpdate) {
                assertNotEquals(animal.getName(), dog.getName());
            }
        }
    }

    @Before
    public void initRepository() {
        animalRepository = AnimalRepositoryFactory.getInstance();

        Animal cat = new Animal();
        Animal parrot = new Animal();

        cat.setId((long) 1);
        cat.setAge(8);
        cat.setName("Miau");
        cat.setNumberOfLegs(4);

        parrot.setId((long) 2);
        parrot.setAge(3);
        parrot.setName("Papuga");
        parrot.setNumberOfLegs(2);

        animalRepository.add(cat);
        animalRepository.add(parrot);
    }

}
