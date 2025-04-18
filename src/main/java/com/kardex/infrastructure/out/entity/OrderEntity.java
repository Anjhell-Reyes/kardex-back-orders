package com.kardex.infrastructure.out.entity;

import com.kardex.domain.model.CartItem;
import com.kardex.domain.model.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class OrderEntity {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    // Relación con CartItemEntity
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private List<CartItemEntity> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    private StatusEntity status;

    @Column(name = "number_order", unique = true, nullable = false)
    private String numberOrder;

    @Column(name = "token_order", unique = true, nullable = false)
    private String tokenOrder;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "created_at")
    @CreatedDate
    private LocalDateTime createdAt;
}
