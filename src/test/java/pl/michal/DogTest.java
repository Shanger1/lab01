package pl.michal;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DogTest {
    @Test
    public void testDog() {
        Pies dog = new Pies();
        assertNotNull(dog);
    }

}
