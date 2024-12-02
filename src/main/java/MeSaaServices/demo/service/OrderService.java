package MeSaaServices.demo.service;


import MeSaaServices.demo.model.dtos.OrderDTO;
import MeSaaServices.demo.model.enrichements.order.EnrichedCheckoutItem;
import MeSaaServices.demo.model.enrichements.order.EnrichedOrder;
import MeSaaServices.demo.model.enrichements.order.EnrichedOrderAsCheckout;
import MeSaaServices.demo.model.enrichements.order.EnrichedOrderItem;
import MeSaaServices.demo.model.entities.Food;
import MeSaaServices.demo.model.entities.Table;
import MeSaaServices.demo.model.entities.order.Order;
import MeSaaServices.demo.model.repositories.FoodRepository;
import MeSaaServices.demo.model.repositories.OrderRepository;
import MeSaaServices.demo.model.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TableRepository tableRepository;

    @Autowired
    private FoodRepository foodRepository; // To fetch food items for order enrichment

    // 1. Create order
    public Order createOrder(OrderDTO orderDTO) {
        // Fetch the table object from the tableId
        Optional<Table> tableOpt = tableRepository.findById(orderDTO.getMesa());
        if (!tableOpt.isPresent()) {
            throw new IllegalArgumentException("Table not found");
        }
        var table = tableOpt.get();

        if (!table.isDisponible()) {
            throw new IllegalStateException("Table is already occupied");
        }

        // Set table as unavailable
        table.setDisponible(false);
        tableRepository.save(table);

        // Create a new order
        Order order = new Order();
        order.setTable(orderDTO.getMesa());
        order.setDescription(orderDTO.getDescription());
        order.setCreationDate(orderDTO.getCreationDate());
        order.setReady(false);
        order.setComplete(false);

        // Assuming that `OrderItem` is a list of food IDs and quantities
        order.setFoods(orderDTO.getFoods()); // Use a list of items (food and quantity)

        return orderRepository.save(order);
    }

    // 2. Mark the order as ready (chef finished preparing)
    public Order markOrderAsReady(String orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (!orderOpt.isPresent()) {
            throw new IllegalArgumentException("Order not found");
        }
        Order order = orderOpt.get();
        order.setReady(true);
        return orderRepository.save(order);
    }

    // 3. Mark the order as completed (checkout done)
    public Order markOrderAsCompleted(String orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (!orderOpt.isPresent()) {
            throw new IllegalArgumentException("Order not found");
        }
        Order order = orderOpt.get();

        if (order.isComplete()) {
            throw new IllegalStateException("Order is already completed");
        }

        // Mark order as completed
        order.setComplete(true);

        // Set table as available
        Optional<Table> tableOpt = tableRepository.findById(order.getTable());
        if (!tableOpt.isPresent()) {
            throw new IllegalArgumentException("Table not found");
        }
        Table table = tableOpt.get();
        table.setDisponible(true);
        tableRepository.save(table);

        return orderRepository.save(order);
    }
    // 4. Get enriched orders (includes Table and Foods)
    public List<EnrichedOrder> getAllEnrichedOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToEnrichedOrder)
                .collect(Collectors.toList());
    }

    public EnrichedOrder getEnrichedOrderById(String orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (!orderOpt.isPresent()) {
            throw new IllegalArgumentException("Order not found");
        }
        return convertToEnrichedOrder(orderOpt.get());
    }

    private EnrichedOrder convertToEnrichedOrder(Order order) {
        Optional<Table> tableOpt = tableRepository.findById(order.getTable());
        Table table = tableOpt.orElseThrow(() -> new IllegalArgumentException("Table not found"));

        List<EnrichedOrderItem> enrichedItems = order.getFoods().stream()
                .map(orderItem -> {
                    Optional<Food> foodOpt = foodRepository.findById(orderItem.getFood());
                    Food food = foodOpt.orElseThrow(() -> new IllegalArgumentException("Food not found"));
                    return new EnrichedOrderItem(food, orderItem.getQuantity());
                })
                .collect(Collectors.toList());

        EnrichedOrder enrichedOrder = new EnrichedOrder();
        enrichedOrder.setId(order.getId());
        enrichedOrder.setTable(table);
        enrichedOrder.setDescription(order.getDescription());
        enrichedOrder.setCreationDate(order.getCreationDate());
        enrichedOrder.setReady(order.isReady());
        enrichedOrder.setComplete(order.isComplete());
        enrichedOrder.setFoods(enrichedItems);
        return enrichedOrder;
    }

    // 5. Get enriched orders as checkout (with totals, subtotal, and IVA)
    public List<EnrichedOrderAsCheckout> getAllEnrichedOrdersAsCheckout() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(this::convertToEnrichedOrderAsCheckout)
                .collect(Collectors.toList());
    }

    public EnrichedOrderAsCheckout getEnrichedOrderAsCheckoutById(String orderId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        if (!orderOpt.isPresent()) {
            throw new IllegalArgumentException("Order not found");
        }
        return convertToEnrichedOrderAsCheckout(orderOpt.get());
    }

    private EnrichedOrderAsCheckout convertToEnrichedOrderAsCheckout(Order order) {
        Optional<Table> tableOpt = tableRepository.findById(order.getTable());
        Table table = tableOpt.orElseThrow(() -> new IllegalArgumentException("Table not found"));

        List<EnrichedCheckoutItem> checkoutItems = order.getFoods().stream()
                .map(orderItem -> {
                    Optional<Food> foodOpt = foodRepository.findById(orderItem.getFood());
                    Food food = foodOpt.orElseThrow(() -> new IllegalArgumentException("Food not found"));
                    double itemTotalPrice = food.getPrice() * orderItem.getQuantity();
                    return EnrichedCheckoutItem.builder()
                            .food(food)
                            .quantity(orderItem.getQuantity())
                            .totalPriceForItem(itemTotalPrice)
                            .build();
                })
                .collect(Collectors.toList());

        EnrichedOrderAsCheckout enrichedOrderAsCheckout = new EnrichedOrderAsCheckout();

        double subtotal = checkoutItems.stream().mapToDouble(EnrichedCheckoutItem::getTotalPriceForItem).sum();
        double iva = subtotal * 0.8; // Assuming 16% IVA
        double fee = 0;

        if(order.getModality() == 2){
            fee = 50;
        }

        double total = subtotal + iva + fee;

        enrichedOrderAsCheckout.setId(order.getId());
        enrichedOrderAsCheckout.setTable(table);
        enrichedOrderAsCheckout.setDescription(order.getDescription());
        enrichedOrderAsCheckout.setCreationDate(order.getCreationDate());
        enrichedOrderAsCheckout.setReady(order.isReady());
        enrichedOrderAsCheckout.setComplete(order.isComplete());
        enrichedOrderAsCheckout.setFoods(checkoutItems);
        enrichedOrderAsCheckout.setSubtotal(subtotal);
        enrichedOrderAsCheckout.setIVA(iva);
        enrichedOrderAsCheckout.setFee(fee);
        enrichedOrderAsCheckout.setTotal(total);
        return enrichedOrderAsCheckout;
    }
}

