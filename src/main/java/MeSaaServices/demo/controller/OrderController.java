package MeSaaServices.demo.controller;

import MeSaaServices.demo.model.dtos.OrderDTO;
import MeSaaServices.demo.model.enrichements.order.EnrichedOrder;
import MeSaaServices.demo.model.enrichements.order.EnrichedOrderAsCheckout;
import MeSaaServices.demo.model.entities.order.Order;
import MeSaaServices.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // GET /orders -> List of enriched orders (id, table, description, creationDate, ready, complete, enrichedFoods)
    @GetMapping
    public ResponseEntity<List<EnrichedOrder>> getAllOrders() {
        List<EnrichedOrder> enrichedOrders = orderService.getAllEnrichedOrders();
        return ResponseEntity.ok(enrichedOrders);
    }

    // GET /orders/{orderId} -> Get a single enriched order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<EnrichedOrder> getOrderById(@PathVariable String orderId) {
        try {
            EnrichedOrder enrichedOrder = orderService.getEnrichedOrderById(orderId);
            return ResponseEntity.ok(enrichedOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // GET /ordersAsCheckout -> List of orders as checkout (id, table, description, creationDate, ready, complete, enrichedFoods, subtotal, IVA, total)
    @GetMapping("/ordersAsCheckout")
    public ResponseEntity<List<EnrichedOrderAsCheckout>> getAllOrdersAsCheckout() {
        List<EnrichedOrderAsCheckout> enrichedOrdersAsCheckout = orderService.getAllEnrichedOrdersAsCheckout();
        return ResponseEntity.ok(enrichedOrdersAsCheckout);
    }

    // GET /ordersAsCheckout/{orderId} -> Get a single order as checkout by ID
    @GetMapping("/ordersAsCheckout/{orderId}")
    public ResponseEntity<EnrichedOrderAsCheckout> getOrderAsCheckoutById(@PathVariable String orderId) {
        try {
            EnrichedOrderAsCheckout enrichedOrderAsCheckout = orderService.getEnrichedOrderAsCheckoutById(orderId);
            return ResponseEntity.ok(enrichedOrderAsCheckout);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // POST /orders -> Create a new order (expects an OrderDTO as payload)
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            Order createdOrder = orderService.createOrder(orderDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // PUT /orders/markOrderAsReady/{orderId} -> Mark order as ready (chef finished preparing)
    @PutMapping("/markOrderAsReady/{orderId}")
    public ResponseEntity<Void> markOrderAsReady(@PathVariable String orderId) {
        try {
            orderService.markOrderAsReady(orderId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // PUT /orders/markOrderAsCompleted/{orderId} -> Mark order as completed (checkout done)
    @PutMapping("/markOrderAsCompleted/{orderId}")
    public ResponseEntity<Void> markOrderAsCompleted(@PathVariable String orderId) {
        try {
            orderService.markOrderAsCompleted(orderId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

