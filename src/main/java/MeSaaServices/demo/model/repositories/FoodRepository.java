package MeSaaServices.demo.model.repositories;

import MeSaaServices.demo.model.entities.Food;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodRepository extends MongoRepository<Food, String> {
    List<Food> findByMenu(String menu);
}