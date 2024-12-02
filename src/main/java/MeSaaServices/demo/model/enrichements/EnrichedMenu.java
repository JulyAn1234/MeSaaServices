package MeSaaServices.demo.model.enrichements;

import MeSaaServices.demo.model.entities.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrichedMenu {
    private String id;
    private String name;
    private String description;
    private List<Food> foods;
}
