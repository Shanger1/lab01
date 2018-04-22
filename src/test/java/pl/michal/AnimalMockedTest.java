package pl.michal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.michal.domain.Animal;
import pl.michal.repository.AnimalRepository;
import pl.michal.repository.AnimalRepositoryFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AnimalMockedTest {

    AnimalRepository animalRepository;

    @Mock
    private Connection connectionMock;

    @Mock
    private PreparedStatement addStatement;
    @Mock
    private PreparedStatement getAllStatement;
    @Mock
    private PreparedStatement getByIdStatement;
    @Mock
    private PreparedStatement updateStatement;
    @Mock
    private PreparedStatement deleteStatement;

    @Mock
    ResultSet resultSet;

    @Before
    public void initRepository() throws SQLException {
        when(connectionMock.prepareStatement("INSERT INTO Animal (name, age, numberOfLegs) VALUES (?, ?, ?)")).thenReturn(addStatement);
        when(connectionMock.prepareStatement("SELECT id, name, age, numberOfLegs FROM Animal")).thenReturn(getAllStatement);
        when(connectionMock.prepareStatement("UPDATE Animal SET name = ?, age = ?, numberOfLegs = ? WHERE id = ?")).thenReturn(updateStatement);
        when(connectionMock.prepareStatement("SELECT id, name, age, numberOfLegs FROM Animal WHERE id = ?")).thenReturn(getByIdStatement);
        when(connectionMock.prepareStatement("DELETE FROM Animal WHERE id = ?")).thenReturn(deleteStatement);
        animalRepository = new AnimalRepositoryFactory();
        animalRepository.setConnection(connectionMock);
        verify(connectionMock).prepareStatement("INSERT INTO Animal (name, age, numberOfLegs) VALUES (?, ?, ?)");
        verify(connectionMock).prepareStatement("SELECT id, name, age, numberOfLegs FROM Animal");
        verify(connectionMock).prepareStatement("UPDATE Animal SET name = ?, age = ?, numberOfLegs = ? WHERE id = ?");
        verify(connectionMock).prepareStatement("SELECT id, name, age, numberOfLegs FROM Animal WHERE id = ?");
        verify(connectionMock).prepareStatement("DELETE FROM Animal WHERE id = ?");
    }

    @Test
    public void addAnimalTest() throws Exception {
        when(addStatement.executeUpdate()).thenReturn(1);
        Animal dog = new Animal();
        dog.setId(1);
        dog.setAge(12);
        dog.setName("Adar");
        dog.setNumberOfLegs(4);

        assertEquals(1, animalRepository.add(dog));

        verify(addStatement, times(1)).setString(1, "Adar");
        verify(addStatement, times(1)).setInt(2, 12);
        verify(addStatement, times(1)).setInt(3, 4);
        verify(addStatement).executeUpdate();
    }

    @Test
    public void deleteAnimalTest() throws Exception {
        when(deleteStatement.executeUpdate()).thenReturn(1);
        Animal dog = new Animal();
        dog.setId(1);
        dog.setAge(12);
        dog.setName("Adar");
        dog.setNumberOfLegs(4);
        assertEquals(1, animalRepository.delete(dog));
        verify(deleteStatement, times(1)).setInt(1, dog.getId());
        verify(deleteStatement).executeUpdate();
    }


    abstract class AbstractResultSet implements ResultSet {
        int i = 0;

        @Override
        public boolean next() throws SQLException {
            if (i == 1)
                return false;

            i++;
            return true;
        }

        @Override
        public int getInt(String id) throws SQLException {
            return 1;
        }

        @Override
        public String getString(String columnLabel) throws SQLException {
            return "Adar";
        }

    }

    @Test
    public void getAllAnimalsTest() throws Exception {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getInt("id")).thenCallRealMethod();
        when(mockedResultSet.getString("name")).thenCallRealMethod();
        when(mockedResultSet.getInt("age")).thenCallRealMethod();
        when(mockedResultSet.getInt("numberOfLegs")).thenCallRealMethod();
        when(getAllStatement.executeQuery()).thenReturn(mockedResultSet);

        assertEquals(1, animalRepository.getAll().size());

        verify(getAllStatement, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getInt("id");
        verify(mockedResultSet, times(1)).getString("name");
        verify(mockedResultSet, times(1)).getInt("age");
        verify(mockedResultSet, times(1)).getInt("numberOfLegs");
        verify(mockedResultSet, times(2)).next();
    }

    @Test
    public void getByIdTest() throws SQLException {
        AbstractResultSet mockedResultSet = mock(AbstractResultSet.class);
        when(mockedResultSet.next()).thenCallRealMethod();
        when(mockedResultSet.getInt("id")).thenCallRealMethod();
        when(mockedResultSet.getString("name")).thenCallRealMethod();
        when(mockedResultSet.getInt("age")).thenCallRealMethod();
        when(mockedResultSet.getInt("numberOfLegs")).thenCallRealMethod();
        when(getByIdStatement.executeQuery()).thenReturn(mockedResultSet);

        assertNotNull(animalRepository.getById(1));

        verify(getByIdStatement, times(1)).executeQuery();
        verify(mockedResultSet, times(1)).getInt("id");
        verify(mockedResultSet, times(1)).getString("name");
        verify(mockedResultSet, times(1)).getInt("age");
        verify(mockedResultSet, times(1)).getInt("numberOfLegs");
        verify(mockedResultSet, times(2)).next();
    }

    @Test
    public void updateAnimalTest() throws SQLException {
        when(updateStatement.executeUpdate()).thenReturn(1);
        Animal dog = new Animal();
        dog.setId(1);
        dog.setAge(12);
        dog.setName("Adar");
        dog.setNumberOfLegs(4);

        Animal animalUpdated = new Animal();
        animalUpdated.setId(dog.getId());
        animalUpdated.setName("Alice");
        animalUpdated.setAge(dog.getAge());
        animalUpdated.setNumberOfLegs(dog.getNumberOfLegs());

        assertEquals(1, animalRepository.update(dog.getId(), animalUpdated));
        verify(updateStatement).executeUpdate();


    }


}
