package com.kardex.infrastructure.input;

import com.kardex.application.dto.orderDto.*;
import com.kardex.application.handler.orderHandler.IOrderHandler;
import com.kardex.domain.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.ORDERS_BASE_PATH)
@RequiredArgsConstructor
public class OrderRestController {
    private final IOrderHandler orderHandler;

    @Operation(summary = "Get a order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = "Order found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = "Order not found", content = @Content)
    })
    @GetMapping(Constants.GET_ORDER_ID_PATH)
    public ResponseEntity<OrderResponse> getOrder(@Parameter(description = "ID of the order to be returned")
                                                  @PathVariable(name = "orderId") Long orderId,
                                                  @Parameter(description = "Token of the order to be returned")
                                                  @PathVariable(name = "tokenOrder") String tokenOrder) {
        return ResponseEntity.ok(orderHandler.getOrder(orderId, tokenOrder));
    }

    @Operation(summary = "Get a order history by its order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = "Order found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponse.class))),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = "Order not found", content = @Content)
    })
    @GetMapping(Constants.GET_ORDER_HISTORY_PATH)
    public ResponseEntity<List<OrderStatusHistoryResponse>> getOrderHistory(
            @PathVariable(name = "orderId") Long orderId) {
        return ResponseEntity.ok(orderHandler.getOrderHistory(orderId));
    }

    @Operation(summary = "Get paginated list of orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = "Paged orders returned",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = "No data found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<Page<OrderPaginated>> getOrders(
            HttpServletRequest request,
            @RequestParam(defaultValue = Constants.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = Constants.DEFAULT_SIZE) int size,
            @RequestParam(defaultValue = Constants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = Constants.DEFAULT_ASC) boolean asc) {
        Page<OrderPaginated> orders = orderHandler.getAllOrders(request, page, size, sortBy, asc);
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Update an existing order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = "Order updated successfully", content = @Content),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = "Order not found", content = @Content)
    })
    @PutMapping(Constants.ORDER_ID_PATH)
    public ResponseEntity<Void> updateOrder(@Parameter(description = "ID of the order to be updated")
                                            @PathVariable(name = "orderId") Long orderId,
                                            @RequestBody OrderUpdateRequest orderRequest) {
        orderHandler.updateOrder(orderId, orderRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = "Order deleted successfully", content = @Content),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = "Order not found", content = @Content)
    })
    @DeleteMapping(Constants.ORDER_ID_PATH) // Usamos la constante para el ID path
    public ResponseEntity<Void> deleteOrder(@Parameter(description = "ID of the order to be deleted")
                                            @PathVariable(name = "orderId") Long orderId,
                                            HttpServletRequest request) {
        orderHandler.deleteOrder(request, orderId);
        return ResponseEntity.noContent().build();
    }

}
