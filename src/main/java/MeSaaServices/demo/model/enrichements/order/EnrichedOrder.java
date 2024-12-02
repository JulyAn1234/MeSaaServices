package MeSaaServices.demo.model.enrichements.order;

import MeSaaServices.demo.model.entities.Table;
import MeSaaServices.demo.model.entities.order.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrichedOrder {
    private String id;
    private Table table;
    private String description;
    private String creationDate;
    private int modality;
    private boolean ready;
    private boolean complete;
    private List <EnrichedOrderItem> foods;
}
