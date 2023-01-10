package com.example.demo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartDto {
	private List<CartItem> cartItems;
    private double totalCost;

    public CartDto(List<CartItem> cartItemDtoList, double totalCost) {
        this.cartItems = cartItemDtoList;
        this.totalCost = totalCost;
    }
}
