package MeSaaServices.demo.controller;

import MeSaaServices.demo.model.dtos.TableDTO;
import MeSaaServices.demo.model.entities.Table;
import MeSaaServices.demo.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/tables")
public class TableController {

    private final TableService tableService;

    @Autowired
    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    // Get all tables
    @GetMapping
    public ResponseEntity<List<Table>> getAllTables() {
        List<Table> tables = tableService.getAllTables();
        return ResponseEntity.ok(tables);
    }

    // Get table by ID
    @GetMapping("/{id}")
    public ResponseEntity<Table> getTableById(@PathVariable String id) {
        Table table = tableService.getTableById(id);
        return ResponseEntity.ok(table);
    }

    // Create a new table
    @PostMapping
    public ResponseEntity<Table> createTable(@RequestBody TableDTO tableDTO) {
        Table createdTable = tableService.createTable(tableDTO);
        return new ResponseEntity<>(createdTable, HttpStatus.CREATED);
    }

    // Update a table
    @PutMapping("/{id}")
    public ResponseEntity<Table> updateTable(
            @PathVariable String id,
            @RequestBody TableDTO tableDTO) {
        Table updatedTable = tableService.updateTable(id, tableDTO);
        return ResponseEntity.ok(updatedTable);
    }

    // Delete a table
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTable(@PathVariable String id) {
        tableService.deleteTable(id);
        return ResponseEntity.noContent().build();
    }
}
