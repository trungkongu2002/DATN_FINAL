package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Discount;
import com.example.demo.entities.DiscountDetail;
import com.example.demo.entities.Product;

@Repository
public interface DiscountDetailRepository extends JpaRepository<DiscountDetail,Long> {
	List<DiscountDetail> findByDiscountEndDayAfter(Date date);
	
	@Query("select i.product from DiscountDetail i where i.discount.endDay > ?1")
	Page<Product> findByDiscountEndDayAfter(Date date, Pageable pageable);
	
	@Query("select distinct i.product from DiscountDetail i where i.discount.endDay > ?1")
	List<Product> findByDiscountEndDayAfter2(Date date);
	
<<<<<<< HEAD
	List<DiscountDetail> findByProductAndDiscountEndDayAfter(Product product,Date date);
	
	DiscountDetail findByProductAndDiscount(Product product,Discount discount);
	
	@Query("select max(i.discount.decreasePercent) from DiscountDetail i where i.discount.endDay >= ?1 and i.product = ?2 and i.discount.memberType like 'truc tiep' ")
=======
	@Query("select max(i.discount.decreasePercent) from DiscountDetail i where i.discount.endDay >= ?1 and i.product = ?2")
>>>>>>> 35b31b0f3530a1aca3db6c66a20490c83f77ed7b
	Integer findProductMaxDiscountEndDayAfter(Date date, Product product);
}
