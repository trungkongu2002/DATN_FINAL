package com.example.demo.entities;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "is_payed")
    private boolean isPayed;
    
    private Integer quantity;
    
    @Column(name = "create_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "product_detail_id", referencedColumnName = "id")
    private ProductDetail product;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", referencedColumnName = "id")
    private User user;
    
    public Cart(ProductDetail product, Integer quantity, User user, boolean isPayed) {
        this.quantity = quantity;
        this.product = product;
        this.user = user;
        this.isPayed = isPayed;
        this.createdDate = new Date();
    }
}
