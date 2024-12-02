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

    private String id;
    private String nombre;
    private String capacidad;
    private boolean disponible;
    private String mesero;
    private String personaTitular;

}
