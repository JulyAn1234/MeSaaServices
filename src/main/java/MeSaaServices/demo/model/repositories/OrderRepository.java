package MeSaaServices.demo.model.repositories;

import MeSaaServices.demo.model.entities.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
