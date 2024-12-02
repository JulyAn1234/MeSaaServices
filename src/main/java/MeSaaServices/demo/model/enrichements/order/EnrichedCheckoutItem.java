package MeSaaServices.demo.model.enrichements.order;

import MeSaaServices.demo.model.entities.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class EnrichedCheckoutItem {
    Food food;
    int quantity;
    double totalPriceForItem;
}
