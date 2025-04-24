package com.kardex.domain.usecase;

import com.kardex.domain.api.IEmailServicePort;
import com.kardex.domain.model.EmailRequest;
import com.kardex.domain.api.ICartServicePort;
import com.kardex.domain.api.IOrderServicePort;
import com.kardex.domain.exception.*;
import com.kardex.domain.model.*;
import com.kardex.domain.spi.*;
import com.kardex.domain.utils.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderUseCase implements IOrderServicePort {

    private final IOrderPersistencePort orderPersistencePort;
    private final IStatusPersistencePort statusPersistencePort;
    private final IProductPersistencePort productPersistencePort;
    private final INotificationPersistencePort notificationPersistencePort;
    private final IOrderStatusHistoryPersistencePort orderStatusHistoryPersistencePort;
    private final ICartServicePort cartServicePort;
    private final ICartPersistencePort cartPersistencePort;
    private final IEmailServicePort emailServicePort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IStatusPersistencePort statusPersistencePort, IProductPersistencePort productPersistencePort, INotificationPersistencePort notificationPersistencePort, IOrderStatusHistoryPersistencePort orderStatusHistoryPersistencePort, ICartServicePort cartServicePort, ICartPersistencePort cartPersistencePort, IEmailServicePort emailServicePort) {
        this.orderPersistencePort = orderPersistencePort;
        this.statusPersistencePort = statusPersistencePort;
        this.productPersistencePort = productPersistencePort;
        this.notificationPersistencePort = notificationPersistencePort;
        this.orderStatusHistoryPersistencePort = orderStatusHistoryPersistencePort;
        this.cartServicePort = cartServicePort;
        this.cartPersistencePort = cartPersistencePort;
        this.emailServicePort = emailServicePort;
    }

    @Override
    public void saveOrder(Long cartId, File archivo) {
        // Obtener el carrito con productos completos
        Cart cart = cartServicePort.getCart(cartId);

        if (cart == null || cart.getItems().isEmpty()) {
            throw new CartEmptyException(); // Puedes definir esta excepción si no existe
        }

        // Generar número y token únicos para la orden
        String numberOrder = UUID.randomUUID().toString().substring(0, 8);
        String tokenOrder = UUID.randomUUID().toString();

        // Calcular el total de la orden
        double totalAmount = cart.getItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();

        // Crear la orden
        Order order = new Order();
        order.setItems(new ArrayList<>(cart.getItems())); // Clonamos los items
        order.setUserId(cart.getUserId());
        order.setCustomerEmail(cart.getCustomerEmail());
        order.setStatus(statusPersistencePort.getStatusById(Constants.DEFAULT_STATUS_ID));
        order.setNumberOrder(numberOrder);
        order.setTokenOrder(tokenOrder);
        order.setTotalAmount(totalAmount);

        // Guardar la orden (suponiendo que tienes un puerto para ello)
        Order savedOrder = orderPersistencePort.saveOrder(order);

        // Limpiar carrito y guardar
        cart.setItems(new ArrayList<>());
        cartPersistencePort.saveCart(cart);

        // Agrupar productos por proveedor
        Map<String, List<CartItem>> groupedByProvider = order.getItems().stream()
                .collect(Collectors.groupingBy(item -> item.getProduct().getProvider().getEmail()));

        // Enviar correos por proveedor
        groupedByProvider.forEach((providerEmail, items) -> {
            // Crear un emailRequest para cada proveedor con los productos correspondientes
            EmailRequest emailRequest = new EmailRequest();
            emailRequest.setOrderId(savedOrder.getId());
            emailRequest.setOrderNumber(savedOrder.getNumberOrder());
            emailRequest.setOrderToken(savedOrder.getTokenOrder());
            emailRequest.setProviderEmail(providerEmail);
            emailRequest.setItems(items);

            // Enviar el correo al proveedor y al usuario
            emailServicePort.sendEmailToProvider(emailRequest, archivo);
        });

        // Obtener los productos para el cliente
        List<String> productNames = order.getItems().stream()
                .map(item -> item.getProduct().getName())
                .toList();

        emailServicePort.sendEmailtoUser(order.getCustomerEmail(), productNames, archivo);
    }

    @Override
    public Order getOrder(Long orderId, String tokenOrder) {
        Order order = orderPersistencePort.getOrder(orderId);

        if (order.getProducts() == null || order.getProductIds() == null) {
            throw new ProductNotFoundException();
        }

        if (!order.getTokenOrder().equals(tokenOrder)) {
            throw new UserForbiddenException();
        }

        List<Long> productIds = order.getProductIds();

        List<Product> products = new ArrayList<>();
        for (Long productId : productIds) {
            Product product = productPersistencePort.getProduct(productId);
            if (product == null) {
                throw new ProductNotFoundException();
            }
            products.add(product);
        }

        for (CartItem item : order.getItems()) {
            // Buscamos el producto correspondiente para cada CartItem
            Product product = products.stream()
                    .filter(p -> p.getId().equals(item.getProduct().getId()))
                    .findFirst()
                    .orElseThrow(ProductNotFoundException::new);
            item.setProduct(product);  // Asignamos el producto al CartItem
        }

        return order;
    }

    @Override
    public CustomPage<Order> getAllOrders(String userId, int page, int size, String sortBy, boolean asc) {
        if (page < 0) {
            throw new InvalidPageIndexException();
        }

        int offset = page * size;
        String sortByField = getSortByField(sortBy);

        CustomPage<Order> ordersPage = orderPersistencePort.getAllOrders(userId, offset, size, sortByField, asc);

        for (Order order : ordersPage.getContent()) {
            List<CartItem> items = order.getItems();

            // Obtener todos los IDs de producto
            List<Long> productIds = items.stream()
                    .map(item -> item.getProduct().getId())
                    .distinct()
                    .toList();

            // Obtener los productos reales
            List<Product> products = productIds.stream()
                    .map(productPersistencePort::getProduct)
                    .toList();

            // Asignar productos completos a cada item
            for (CartItem item : items) {
                Product product = products.stream()
                        .filter(p -> p.getId().equals(item.getProduct().getId()))
                        .findFirst()
                        .orElse(null);

                if (product != null) {
                    item.setProduct(product);
                }
            }
        }

        return ordersPage;
    }


    @Override
    public void updateOrder(Long orderId, Order order) {
        Order oldOrder = orderPersistencePort.getOrder(orderId);

        if (oldOrder.getItems() == null || oldOrder.getItems().isEmpty()) {
            throw new ProductNotFoundException();
        }

        // Obtener los productos reales desde la base de datos
        List<Long> productIds = oldOrder.getItems().stream()
                .map(item -> item.getProduct().getId())
                .toList();

        List<Product> products = productIds.stream()
                .map(productPersistencePort::getProduct)
                .toList();

        for (CartItem item : oldOrder.getItems()) {
            Product fullProduct = products.stream()
                    .filter(p -> p.getId().equals(item.getProduct().getId()))
                    .findFirst()
                    .orElseThrow(ProductNotFoundException::new);
            item.setProduct(fullProduct);
        }

        // Copiamos campos que no deben cambiar
        order.setId(oldOrder.getId());
        order.setItems(oldOrder.getItems());
        order.setUserId(oldOrder.getUserId());
        order.setCustomerEmail(oldOrder.getCustomerEmail());
        order.setNumberOrder(oldOrder.getNumberOrder());
        order.setTokenOrder(oldOrder.getTokenOrder());
        order.setCreatedAt(oldOrder.getCreatedAt());
        order.setTotalAmount(oldOrder.getTotalAmount());

        // Solo permitimos cambio de estado
        order.setStatus(copyIfNull(
                statusPersistencePort.getStatusById(order.getStatus().getStatusId()),
                oldOrder.getStatus()
        ));

        // Guardamos el historial del estado
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(order);
        history.setStatus(order.getStatus());
        orderStatusHistoryPersistencePort.saveOrderStatusHistory(history);

        // Actualizamos la orden
        orderPersistencePort.updateOrder(order);

        // Enviamos notificación si el estado cambió
        if (!oldOrder.getStatus().equals(order.getStatus())) {
            notificationPersistencePort.sendNotification(
                    order.getUserId(),
                    "#" + order.getNumberOrder(),
                    order.getStatus().getStatus()
            );
        }
    }

    @Override
    public List<OrderStatusHistory> getOrderHistory(Long orderId) {
        List<OrderStatusHistory> history = orderStatusHistoryPersistencePort.getAllStatusHistoryByOrderId(orderId);
        if (history.isEmpty()) {
            throw new OrderHistoryNotFoundException();
        }
        return history;
    }

    @Override
    public void deleteOrder(String userId, Long orderId) {
        Order order = orderPersistencePort.getOrder(orderId);

        validateUserPermission(userId, order);

        orderPersistencePort.deleteOrder(orderId);
    }

    private void validateUserPermission(String userId, Order order) {
        if (!order.getUserId().equals(userId)) {
            throw new UserForbiddenException();
        }
    }

    private <T> T copyIfNull(T requestValue, T oldValue) {
        return (requestValue == null) ? oldValue : requestValue;
    }

    public String getSortByField(String sortBy) {
        return switch (sortBy) {
            case "product" -> Constants.SORT_BY_PRODUCT_NAME;
            case "provider" -> Constants.SORT_BY_PROVIDER_NAME;

            default -> Constants.DEFAULT_SORT_BY;
        };
    }
}
