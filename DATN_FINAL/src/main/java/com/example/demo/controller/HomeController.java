package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entities.User;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.ProductServiceImp;

@Controller
public class HomeController {

	@Autowired
	private ProductServiceImp productServiceImp;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository cateRepository;

	@RequestMapping({ "/", "/home/index" })
	public String home(Model model, Principal principal) {
		model.addAttribute("listCategory", cateRepository.findAll());
		model.addAttribute("maxBuy", productServiceImp.getProductByBuyQuantity());
		model.addAttribute("maxRating", productServiceImp.getProductByRating());
		model.addAttribute("maxLike", productServiceImp.getProductByLike());
		model.addAttribute("maxInter", productServiceImp.getProductByInteraction().subList(1,
				productServiceImp.getProductByInteraction().size() - 1));
		model.addAttribute("mostInter", productServiceImp.getProductByInteraction().get(0));
		model.addAttribute("discountPro", productServiceImp.getAllProductByDiscount());
		if (principal != null) {
			User user = userRepository.findByUsernameEquals(principal.getName());
			model.addAttribute("userLike", productServiceImp.getAllProductByUserLike(user));
		}

		return "customer/home";
	}

	@RequestMapping({ "/admin", "/admin/home/index" })
	public String admin() {
		return "redirect:/assets/admin/index.html";
	}

	@RequestMapping({ "/products" })
	public String products() {
		return "customer/product";
	}

	@RequestMapping({ "/history" })
	public String history() {
		return "customer/history";
	}

	@RequestMapping({ "/details" })
	public String detail() {
		return "customer/chitiet-sanpham";
	}

	@RequestMapping({ "/contact" })
	public String contact() {
		return "customer/lienhe";
	}

	@RequestMapping({ "/detail-news" })
	public String detailnews() {
		return "customer/detail-news";
	}

	@RequestMapping({ "/tintuc" })
	public String tintuc() {
		return "customer/tintuc";
	}

	@RequestMapping({ "/carts" })
	public String cart() {
		return "customer/cart";
	}

	@RequestMapping({ "/productdetail" })
	public String prodetail() {
		return "customer/productdetail";
	}
}
