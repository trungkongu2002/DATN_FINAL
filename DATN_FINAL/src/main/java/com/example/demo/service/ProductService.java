package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.Product;
import com.example.demo.entities.User;
public interface ProductService {
	Product saveProduct(Product product);

	List<Product> getAllProduct();

	Product updateProduct(Product product);

	String removeProductById(Long id);

	Product getProductById(Long id);

	Product getProductByName(String name);

	List<Product> getAllProductByCategoryId(Long id);

	List<Product> getProductByCategoryName(String name);

	List<Product> getProductByMaterial(String material);

	List<Product> getProductByInteraction();

	List<Product> getProductByLike();

	List<Product> getProductByRating();

	List<Product> getProductByComment();

	List<Product> getProductByFilter(String CateName, String material, String color, String size, String order);

	List<Product> getProductByBuyQuantity();

	List<Product> getProductByPrice(Double min, Double max);

	void reductionQuantity(User user);

	List<Product> getAllProductByCreateDayDesc();

	List<Product> getAllProductByCreateDayAsc();

	List<Product> getAllProductByUserLike(User user);

	List<Product> getAllProductByDiscount();

	void addLikeProduct(User user, Product product);
}
