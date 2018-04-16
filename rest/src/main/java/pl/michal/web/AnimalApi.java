package pl.michal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.michal.domain.Animal;
import pl.michal.service.AnimalRepository;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Path;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


@RestController
public class AnimalApi {

    @Autowired
    AnimalRepository animalRepository;

    @RequestMapping("/")
    public String index(){
        return "Works";
    }

    @RequestMapping(value = "/animal/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Animal getAnimal(@PathVariable("id") int id) throws SQLException{
        return animalRepository.getById(id);
    }

    @RequestMapping(value = "/animals", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Animal> getAnimals(@RequestParam("filter") String f) throws SQLException{
        List<Animal> animals = new LinkedList<Animal>();
        for(Animal u : animalRepository.getAll()){
            if (u.getName().contains(f)){
                animals.add(u);
            }
        }
        return animals;
    }

    @RequestMapping(value = "/animal", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long addAnimal(@RequestBody Animal u){
        return new Long(animalRepository.add(u));
    }

    @RequestMapping(value = "/deleteAnimal", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteAnimal(@PathVariable("id") int id) throws SQLException{
        return new Long(animalRepository.delete(animalRepository.getById(id)));
    }

}
