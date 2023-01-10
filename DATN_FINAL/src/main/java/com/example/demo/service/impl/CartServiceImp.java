package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.AddToCart;
import com.example.demo.dto.CartDto;
import com.example.demo.dto.CartItem;
import com.example.demo.entities.Cart;
import com.example.demo.entities.ProductDetail;
import com.example.demo.entities.User;
import com.example.demo.exception.CartItemNotExistException;
import com.example.demo.repository.CartRepository;
import com.example.demo.service.CartService;


@Service
public class CartServiceImp implements CartService{
	@Autowired
    private CartRepository cartRepository;

    @Override
    public Cart addToCart(AddToCart addToCart, ProductDetail product, User user) {
    	Cart foundCart = cartRepository.findByProductAndUser(product,user);
    	if(foundCart==null) {
    		Cart cart = new Cart(product, addToCart.getQuantity(), user, false);
            cartRepository.save(cart);
            return cart;
    	} else {
    		foundCart.setQuantity(foundCart.getQuantity()+addToCart.getQuantity());
    		return foundCart;
    	}
    }

    @Override
    public CartDto listCartItem(User user) {
        List<Cart> cartList = cartRepository.findAllByUser(user);
        List<CartItem> cartItemList = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart : cartList) {
        	CartItem cartItem = new CartItem(cart);
            cartItemList.add(cartItem);
        }
        for (CartItem cartItem : cartItemList) {
            totalCost += (cartItem.getProduct().getProduct().getPrice() * cartItem.getQuantity());
        }
        return new CartDto(cartItemList, totalCost);

    }

    private CartItem getCartItemFromCart(Cart cart) {
        return new CartItem(cart);
    }

    @Override
    public Cart updateCart(AddToCart addToCart, Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        cart.get().setQuantity(addToCart.getQuantity());
        cart.get().setCreatedDate(new Date());
        cartRepository.save(cart.get());
        return cart.get();
    }

    @Override
    public void removeCartItem(Long id, Long userId) throws CartItemNotExistException {
        if (!cartRepository.existsById(id))
            throw new CartItemNotExistException("Cart id is invalid : " + id);
        cartRepository.deleteById(id);
    }

    @Override
    public void deleteUserCartItems(User user) {
        cartRepository.deleteByUser(user);
    }

	
}
