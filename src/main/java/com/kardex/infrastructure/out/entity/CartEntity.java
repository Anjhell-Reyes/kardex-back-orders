package com.kardex.infrastructure.out.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@Data
@NoArgsConstructor
public class CartEntity {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    // Relación con CartItemEntity
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "cart_id")  // Esto es necesario para que la relación se maneje correctamente en la base de datos
    private List<CartItemEntity> items = new ArrayList<>();
}
