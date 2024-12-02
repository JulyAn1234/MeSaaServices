package MeSaaServices.demo.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tables")
public class Table {
    @Id
    private String id;
    private String nombre;
    private String capacidad;
    private boolean disponible;
    private String mesero;
    private String personaTitular;
}

