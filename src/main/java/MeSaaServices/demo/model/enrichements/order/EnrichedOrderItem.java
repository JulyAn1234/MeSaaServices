package MeSaaServices.demo.model.enrichements.order;

import MeSaaServices.demo.model.entities.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrichedOrderItem {
    Food food;
    int quantity;
}
