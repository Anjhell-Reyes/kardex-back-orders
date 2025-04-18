package com.kardex.infrastructure.input;

import com.kardex.application.dto.cartDto.*;
import com.kardex.application.handler.cartHandler.ICartHandler;
import com.kardex.domain.utils.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.CART_BASE_PATH)
@RequiredArgsConstructor
public class CartRestController {
    private final ICartHandler cartHandler;

    @Operation(summary = "Get a cart by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = "Cart found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartResponse.class))),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = "Cart not found", content = @Content)
    })
    @GetMapping()
    public ResponseEntity<CartResponse> getCart(@Parameter(description = "ID of the cart to be returned")
                                                HttpServletRequest request) {
        return ResponseEntity.ok(cartHandler.getCart(request));
    }

    @Operation(summary = "add product to cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = "added product",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartResponse.class))),
            @ApiResponse(responseCode = Constants.CONFLICT, description = "unadded product", content = @Content)
    })
    @PostMapping(Constants.ADD_ITEM_CART_PATH)
    public ResponseEntity<Void> addItem(
            HttpServletRequest request,
            @RequestBody CartItemRequest item) {
        cartHandler.addItem(request, item);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Delete a cart by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = Constants.OK, description = "Cart deleted successfully", content = @Content),
            @ApiResponse(responseCode = Constants.NOT_FOUND, description = "Cart not found", content = @Content)
    })
    @DeleteMapping(Constants.REMOVE_ITEM_CART_PATH)
    public ResponseEntity<Void> removeItem(@Parameter(description = "ID of the cart to be deleted")
                                           HttpServletRequest request,
                                           @PathVariable Long productId) {
        cartHandler.removeItem(request, productId);
        return ResponseEntity.noContent().build();
    }

}
