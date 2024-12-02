package MeSaaServices.demo.controller;


import MeSaaServices.demo.model.dtos.FoodDTO;
import MeSaaServices.demo.model.entities.Food;
import MeSaaServices.demo.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/foods")
public class FoodController {

    @Autowired
    private FoodService foodService;

    // Get all foods (with partial data: id, name, price, description, imgUrl, menuId)
    @GetMapping
    public ResponseEntity<List<Food>> getAllFoods() {
        List<Food> foods = foodService.getAllFoods();
        return ResponseEntity.ok(foods);
    }

    // Get a single food by its ID
    @GetMapping("/{foodId}")
    public ResponseEntity<Food> getFoodById(@PathVariable String foodId) {
        Optional<Food> foodOptional = foodService.getFoodById(foodId);
        return foodOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create a new food item (expects a FoodDTO)
    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody FoodDTO foodDTO) {
        // Convert FoodDTO to Food entity
        Food food = Food.builder()
                .name(foodDTO.getName())
                .description(foodDTO.getDescription())
                .imgURL(foodDTO.getImgURL())
                .menu(foodDTO.getMenu())
                .price(foodDTO.getPrice())
                .build();

        Food createdFood = foodService.createFood(food);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFood);
    }

    // Edit an existing food item (expects a FoodDTO and the foodId in the URL)
    @PutMapping("/{foodId}")
    public ResponseEntity<Food> updateFood(@PathVariable String foodId, @RequestBody FoodDTO foodDTO) {
        // Convert FoodDTO to Food entity
        Food food = Food.builder()
                .name(foodDTO.getName())
                .description(foodDTO.getDescription())
                .imgURL(foodDTO.getImgURL())
                .menu(foodDTO.getMenu())
                .price(foodDTO.getPrice())
                .build();

        Food updatedFood = foodService.updateFood(foodId, food);
        return updatedFood != null
                ? ResponseEntity.ok(updatedFood)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Delete a food item by its ID
    @DeleteMapping("/{foodId}")
    public ResponseEntity<Void> deleteFood(@PathVariable String foodId) {
        boolean isDeleted = foodService.deleteFood(foodId);
        return isDeleted
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
