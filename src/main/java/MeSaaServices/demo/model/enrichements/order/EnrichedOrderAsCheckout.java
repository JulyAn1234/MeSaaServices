package MeSaaServices.demo.model.enrichements.order;

import MeSaaServices.demo.model.entities.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnrichedOrderAsCheckout {
    private String id;
    private Table table;
    private String description;
    private String creationDate;
    private boolean ready;
    private boolean complete;
    private List<EnrichedCheckoutItem> foods;
    private int modality;
    private double total;
    private double subtotal;
    private double fee;
    private double IVA;
}
