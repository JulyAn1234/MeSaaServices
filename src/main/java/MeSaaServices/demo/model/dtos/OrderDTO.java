package MeSaaServices.demo.model.dtos;

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
public class OrderDTO {
    private String mesa;
    private String description;
    private String creationDate;
    private boolean ready;
    private boolean complete;
    private int modality;
    private List<OrderItem> foods;
}
