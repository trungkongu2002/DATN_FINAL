package com.example.demo.dto;

import com.example.demo.entities.Cart;
import com.example.demo.entities.ProductDetail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartItem {
    private Long id;
    private int quantity;
    private ProductDetail product;
    
    public CartItem() {
		// TODO Auto-generated constructor stub
	}

    public CartItem(Cart cart) {
        this.setId(cart.getId());
        this.setQuantity(cart.getQuantity());
        this.setProduct(cart.getProduct());
    }
}
