package MeSaaServices.demo.model.repositories;

import MeSaaServices.demo.model.entities.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends MongoRepository<Menu, String> {

}