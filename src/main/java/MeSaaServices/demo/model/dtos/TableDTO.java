package MeSaaServices.demo.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableDTO {
    private String nombre;
    private Integer capacidad;
    private boolean disponible;
    private String mesero;
    private String personaTitular;
}
