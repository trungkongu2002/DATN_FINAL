package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Product;
import com.example.demo.entities.ProductDetail;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
	@Query("SELECT pd FROM ProductDetail pd WHERE pd.product = :product")
	List<ProductDetail> findByProduct(@Param("product") Product product);

	public List<ProductDetail> findByProductIdEquals(Long id);

	@Query("SELECT pd FROM ProductDetail pd WHERE pd.product = :product and size = :size and color = :color")
	ProductDetail findByProductDetailNameSizeColorEquals(@Param("product") Product product, String size, String color);

	ProductDetail findBySizeAndColorAndProduct(String size, String color, Product product);

	ProductDetail findByColorAndSizeAndProductId(String color, String size, Long id);

	List<ProductDetail> findByColorAndProductId(String color, Long id);

}
