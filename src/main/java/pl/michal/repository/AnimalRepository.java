package pl.michal.repository;


import pl.michal.domain.Animal;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AnimalRepository {
    public void setConnection(Connection connection) throws SQLException;
    public Connection getConnection();
    Animal getById(int id);
    public List<Animal> getAll();
    public void add(Animal animal);
    public void delete(Animal animal) throws SQLException;
    public void update(int oldId, Animal newAnimal) throws SQLException;
}