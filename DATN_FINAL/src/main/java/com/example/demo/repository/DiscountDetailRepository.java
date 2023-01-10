package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.DiscountDetail;
import com.example.demo.entities.Product;

@Repository
public interface DiscountDetailRepository extends JpaRepository<DiscountDetail,Long> {
	List<DiscountDetail> findByDiscountEndDayAfter(Date date);
	
	@Query("select i.product from DiscountDetail i where i.discount.endDay < ?1")
	Page<Product> findByDiscountEndDayAfter(Date date, Pageable pageable);
}
