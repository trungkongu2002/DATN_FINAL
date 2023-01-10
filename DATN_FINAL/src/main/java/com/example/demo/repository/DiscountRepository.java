package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
	public Discount findByDiscountNameEquals(String discountName);
}
