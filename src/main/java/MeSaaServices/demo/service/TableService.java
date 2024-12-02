package MeSaaServices.demo.service;

import MeSaaServices.demo.model.dtos.TableDTO;
import MeSaaServices.demo.model.entities.Table;
import MeSaaServices.demo.model.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {

    private final TableRepository tableRepository;

    @Autowired
    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    public Table getTableById(String id) {
        return tableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found with id: " + id));
    }

    public Table createTable(TableDTO tableDTO) {
        Table table = convertToEntity(tableDTO);
        return tableRepository.save(table);
    }

    public Table updateTable(String id, TableDTO tableDTO) {
        Table existingTable = tableRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Table not found with id: " + id));

        existingTable.setNombre(tableDTO.getNombre());
        existingTable.setCapacidad(tableDTO.getCapacidad());
        existingTable.setDisponible(tableDTO.isDisponible());
        existingTable.setMesero(tableDTO.getMesero());
        existingTable.setPersonaTitular(tableDTO.getPersonaTitular());

        return tableRepository.save(existingTable);
    }

    public void deleteTable(String id) {
        if (!tableRepository.existsById(id)) {
            throw new RuntimeException("Table not found with id: " + id);
        }
        tableRepository.deleteById(id);
    }

    private Table convertToEntity(TableDTO tableDTO) {
        return Table.builder()
                .nombre(tableDTO.getNombre())
                .capacidad(tableDTO.getCapacidad())
                .disponible(tableDTO.isDisponible())
                .mesero(tableDTO.getMesero())
                .personaTitular(tableDTO.getPersonaTitular())
                .build();
    }
}
