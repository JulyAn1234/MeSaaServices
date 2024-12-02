package MeSaaServices.demo.service;

import MeSaaServices.demo.model.entities.Food;
import MeSaaServices.demo.model.repositories.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    public Optional<Food> getFoodById(String foodId) {
        return foodRepository.findById(foodId);
    }

    public Food createFood(Food food) {
        return foodRepository.save(food);
    }

    public Food updateFood(String foodId, Food food) {
        if (foodRepository.existsById(foodId)) {
            food.setId(foodId);
            return foodRepository.save(food);
        }
        return null;
    }

    public boolean deleteFood(String foodId) {
        if (foodRepository.existsById(foodId)) {
            foodRepository.deleteById(foodId);
            return true;
        }
        return false;
    }
}
