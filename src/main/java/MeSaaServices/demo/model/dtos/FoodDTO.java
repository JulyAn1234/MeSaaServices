package MeSaaServices.demo.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodDTO {
    private String name;
    private String description;
    private String imgURL;
    private String menu;
    private double price;
}
