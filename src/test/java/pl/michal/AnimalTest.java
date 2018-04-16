package pl.michal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.michal.domain.Animal;
import pl.michal.repository.AnimalRepository;
import pl.michal.repository.AnimalRepositoryFactory;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


@Ignore
@RunWith(JUnit4.class)
public class AnimalTest {
    AnimalRepository animalRepository;

    @Test
    public void testGetById() {
        int findId = animalRepository.getAll().get(0).getId();
        assertNotNull(animalRepository.getById(findId));
    }

    @Test
    public void testGetAll() {
        assertNotNull(animalRepository.getAll());
    }

    @Test
    public void testAdd() {
        Animal dog = new Animal();
        dog.setId(1);
        dog.setAge(12);
        dog.setName("Adar");
        dog.setNumberOfLegs(4);

        animalRepository.add(dog);
        assertNotNull(animalRepository.getById(dog.getId()));
        assertThat(dog.getId(), is(1));
        assertThat(dog.getName(), containsString("dar"));
        assertEquals(12, dog.getAge());
        assertEquals(4, dog.getNumberOfLegs());

    }


    @Test
    public void testDelete() throws SQLException {
        Animal dog = animalRepository.getById(0);
        animalRepository.delete(dog);

        if (animalRepository.getAll().size() > 0) {
            assertNotNull(animalRepository.getAll());
        }

        assertNull(animalRepository.getById(dog.getId()).getName());

    }
    @Test
    public void testUpdate() throws SQLException {
        Animal animalTest = animalRepository.getById(3);
        Animal cat = new Animal();
        cat.setAge(15);
        cat.setName("Alice");
        cat.setNumberOfLegs(3);
        int animalToUpdate = animalRepository.getAll().get(0).getId();

        assertEquals(1, animalRepository.update(animalToUpdate, cat));

        assertEquals(animalRepository.getById(animalToUpdate).getName(), cat.getName());

    }

    @Before
    public void initRepository() throws SQLException {
        animalRepository = AnimalRepositoryFactory.getInstance();

        Animal cat = new Animal();
        Animal parrot = new Animal();

        cat.setId(1);
        cat.setAge(8);
        cat.setName("Miau");
        cat.setNumberOfLegs(4);

        parrot.setId(2);
        parrot.setAge(3);
        parrot.setName("Papuga");
        parrot.setNumberOfLegs(2);

        animalRepository.add(cat);
        animalRepository.add(parrot);
    }


}
