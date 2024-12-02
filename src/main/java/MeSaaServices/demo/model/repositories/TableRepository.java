package MeSaaServices.demo.model.repositories;

import MeSaaServices.demo.model.entities.Menu;
import MeSaaServices.demo.model.entities.Table;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends MongoRepository<Table, String> {
}
