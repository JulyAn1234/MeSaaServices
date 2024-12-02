package MeSaaServices.demo.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "menus")
public class Menu {
    @Id
    private String id;
    private String name;
    private String description;
}