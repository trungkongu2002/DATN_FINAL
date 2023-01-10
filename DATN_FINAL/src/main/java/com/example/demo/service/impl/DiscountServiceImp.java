package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Discount;
import com.example.demo.repository.DiscountRepository;
import com.example.demo.service.DiscountService;

import java.util.List;

@Service
public class DiscountServiceImp implements DiscountService {

    @Autowired
    private DiscountRepository discountRepository;



    @Override
    public List<Discount> getAllDiscount() {
        return  discountRepository.findAll();
    }

    @Override
    public Discount getDiscountById(Long id) {
        return discountRepository.findById(id).get();
    }

    @Override
    public Discount updateDiscountById(Discount newDiscount) {

			return discountRepository.save(newDiscount);
    }

    @Override
    public Discount saveDiscount(Discount newDiscount) {
        return discountRepository.save(newDiscount);
    }
}
