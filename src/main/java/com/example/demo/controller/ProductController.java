package com.example.demo.controller;

import java.security.Principal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entities.Category;
import com.example.demo.entities.Product;
import com.example.demo.entities.ProductDetail;
import com.example.demo.entities.User;
import com.example.demo.repository.BillDetailRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.DiscountDetailRepository;
import com.example.demo.repository.InteractionRepository;
import com.example.demo.repository.ProductDetailRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.ProductServiceImp;

@Controller
@RequestMapping("product")
public class ProductController {
	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private ProductServiceImp productService;

	@Autowired
	private CategoryRepository categoryRepo;

	@Autowired
	private InteractionRepository interactionRepository;

	@Autowired
	private DiscountDetailRepository discountDetailRepository;

	@Autowired
	private ProductDetailRepository productDetailRepo;

	@Autowired
	private BillDetailRepository billDetailRepository;

	@Autowired
	private UserRepository userRepository;

	// product Create
	@RequestMapping("create")
	public String create(Model model) {
		List<Category> list = categoryRepo.findAll();
		model.addAttribute("listCategory", list);
		return "product/create";
	}

	// create --> productDetail index
	@PostMapping("/store")
	public String store(@ModelAttribute("product") Product product, Model model) {
		Product p = product;
		this.productRepo.save(p);

		Product prod = productRepo.findByName(p.getName());
		model.addAttribute("product", product);

		List<ProductDetail> listPD = productDetailRepo.findByProduct(prod);
		model.addAttribute("listProductDetail", listPD);

		return "productDetail/index";
	}

	// update product --> productDetail index
	@PostMapping("/update/{id}")
	public String update(@ModelAttribute("product") Product product, Model model, @PathVariable("id") Long id) {
		Product p = product;
		p.setId(id);
		this.productRepo.save(p);

		Product prod = productRepo.findByName(p.getName());
		model.addAttribute("product", product);

		List<ProductDetail> listPD = productDetailRepo.findByProduct(prod);
		model.addAttribute("listProductDetail", listPD);

		return "productDetail/index";
	}

	// edit product
	@RequestMapping("edit/{id}")
	public String editProduct(Model model, @PathVariable("id") Long id) {
		Product product = productRepo.findById(id).get();
		model.addAttribute("product", product);

		List<Category> list = categoryRepo.findAll();
		model.addAttribute("listCategory", list);

		return "product/edit";
	}

	// delete product
	@GetMapping("delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		Product p = productRepo.findById(id).get();
		List<ProductDetail> listProD = productDetailRepo.findByProduct(p);
		if (listProD.size() != 0) {
			for (ProductDetail proD : listProD) {
				this.productDetailRepo.deleteById(proD.getId());
			}
		}
		this.productRepo.deleteById(id);
		return "redirect:/product/index";
	}

	// index product
	@GetMapping("index")
	public String index(Model model, @ModelAttribute("product") Product product,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "20") Integer size) {
		model.addAttribute("create", true);
		List<Category> list = categoryRepo.findAll();
		model.addAttribute("listCategory", list);

		Pageable pageable = PageRequest.of(page, size);
		Page<Product> data = this.productRepo.findAll(pageable);
		model.addAttribute("data", data);
		return "customer/product";
	}

	@GetMapping("indexbycate/{id}")
	public String indexByCate(Model model, @ModelAttribute("product") Product product,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "20") Integer size, @PathVariable Long id) {
		model.addAttribute("create", true);
		List<Category> list = categoryRepo.findAll();
		model.addAttribute("listCategory", list);

		Pageable pageable = PageRequest.of(page, size);
		Page<Product> data = this.productRepo.findByCategoryId(id, pageable);
		model.addAttribute("data", data);
		return "customer/product";
	}

	@GetMapping("userLike")
	public String getUserLike(Model model, @ModelAttribute("product") Product product,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "20") Integer size, Principal principal) {
		if (principal == null) {
			return "redirect:/security/login";
		}
		User user = userRepository.findByUsernameEquals(principal.getName());
		model.addAttribute("create", true);
		List<Category> list = categoryRepo.findAll();
		model.addAttribute("listCategory", list);

		Pageable pageable = PageRequest.of(page, size);
		Page<Product> data = interactionRepository.findByUserLike(user, pageable);
		model.addAttribute("data", data);
		return "customer/product";
	}

	@GetMapping("discountPro")
	public String getDisCountProduct(Model model, @ModelAttribute("product") Product product,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "20") Integer size) {
		model.addAttribute("create", true);
		List<Category> list = categoryRepo.findAll();
		model.addAttribute("listCategory", list);

		Pageable pageable = PageRequest.of(page, size);
		Page<Product> data = discountDetailRepository.findByDiscountEndDayAfter(new Date(), pageable);
		model.addAttribute("data", data);
		return "customer/product";
	}

	@GetMapping("maxInter")
	public String getMaxInter(Model model, @ModelAttribute("product") Product product,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "20") Integer size) {
		model.addAttribute("create", true);
		List<Category> list = categoryRepo.findAll();
		model.addAttribute("listCategory", list);

		Pageable pageable = PageRequest.of(page, size);
		Page<Product> data = new Page<Product>() {

			@Override
			public Iterator<Product> iterator() {
				// TODO Auto-generated method stub
				return productService.getAllProductByInteraction().iterator();
			}

			@Override
			public Pageable previousPageable() {
				// TODO Auto-generated method stub
				return pageable;
			}

			@Override
			public Pageable nextPageable() {
				// TODO Auto-generated method stub
				return pageable;
			}

			@Override
			public boolean isLast() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isFirst() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasPrevious() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasContent() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Sort getSort() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getSize() {
				// TODO Auto-generated method stub
				return 10;
			}

			@Override
			public int getNumberOfElements() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getNumber() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<Product> getContent() {
				// TODO Auto-generated method stub
				return productService.getAllProductByInteraction();
			}

			@Override
			public <U> Page<U> map(Function<? super Product, ? extends U> converter) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getTotalPages() {
				// TODO Auto-generated method stub
				return 100;
			}

			@Override
			public long getTotalElements() {
				// TODO Auto-generated method stub
				return 1000;
			}
		};
		model.addAttribute("data", data);
		return "customer/product";
	}

	@GetMapping("maxBuy")
	public String getMaxBuy(Model model, @ModelAttribute("product") Product product,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "20") Integer size) {
		model.addAttribute("create", true);
		List<Category> list = categoryRepo.findAll();
		model.addAttribute("listCategory", list);

		Pageable pageable = PageRequest.of(page, size);
		Page<Product> data = new Page<Product>() {

			@Override
			public Iterator<Product> iterator() {
				// TODO Auto-generated method stub
				return productService.getAllProductByBuyQuantity().iterator();
			}

			@Override
			public Pageable previousPageable() {
				// TODO Auto-generated method stub
				return pageable;
			}

			@Override
			public Pageable nextPageable() {
				// TODO Auto-generated method stub
				return pageable;
			}

			@Override
			public boolean isLast() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isFirst() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasPrevious() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasContent() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Sort getSort() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getSize() {
				// TODO Auto-generated method stub
				return 10;
			}

			@Override
			public int getNumberOfElements() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getNumber() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<Product> getContent() {
				// TODO Auto-generated method stub
				return productService.getAllProductByBuyQuantity();
			}

			@Override
			public <U> Page<U> map(Function<? super Product, ? extends U> converter) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getTotalPages() {
				// TODO Auto-generated method stub
				return 100;
			}

			@Override
			public long getTotalElements() {
				// TODO Auto-generated method stub
				return 1000;
			}
		};
		model.addAttribute("data", data);
		return "customer/product";
	}

	@GetMapping("index2")
	public String index2(Model model, @ModelAttribute("product") Product product,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "20") Integer size) {
		model.addAttribute("create", true);
		List<Category> list = categoryRepo.findAll();
		model.addAttribute("listCategory", list);

		Pageable pageable = PageRequest.of(page, size);
		Page<ProductDetail> data = this.productDetailRepo.findAll(pageable);

		model.addAttribute("data", data);
		return "customer/product";
	}

	// Product Detail

	// create productDetail
	@PostMapping("/productDetail/store/{id}")
	public String storeDetail(@ModelAttribute("productDetail") ProductDetail productDetail, Model model,
			@PathVariable("id") Long id) {
		ProductDetail proDt = productDetail;

		Product product = productRepo.findById(id).get();
		proDt.setProduct(product);

		this.productDetailRepo.save(proDt);

		model.addAttribute("product", product);

		List<ProductDetail> listPD = productDetailRepo.findByProduct(product);
		model.addAttribute("listProductDetail", listPD);
		model.addAttribute("buyquantity", billDetailRepository.findQuantityByProduct(product));
		model.addAttribute("listProduct", productRepo.findByCategory(product.getCategory()));
		return "customer/productdetail";
	}

	// productDetail index
	@GetMapping("/productDetail/index/{id}")
	public String indexDetail(@ModelAttribute("productDetail") ProductDetail productDetail, Model model,
			@PathVariable("id") Long id) {
		Product product = productRepo.findById(id).get();
//		System.out.println(product);
		model.addAttribute("product", product);

		List<ProductDetail> listPD = productDetailRepo.findByProduct(product);
		model.addAttribute("listProductDetail", listPD);
		if (billDetailRepository.findQuantityByProduct(product) != null) {
			model.addAttribute("buyquantity", billDetailRepository.findQuantityByProduct(product));
		}
		model.addAttribute("buyquantity", 0);
		model.addAttribute("listProduct", productRepo.findByCategory(product.getCategory()));
//		System.out.println(listPD);
		return "customer/productdetail";
	}

	// delete productDetail
	@GetMapping("/productDetail/delete/{id}")
	public String deleteDetail(@ModelAttribute("productDetail") ProductDetail productDetail, Model model,
			@PathVariable("id") Long id) {
		ProductDetail proDt = productDetailRepo.getById(id);
		Product product = proDt.getProduct();
		model.addAttribute("product", product);
		productDetailRepo.deleteById(id);

		List<ProductDetail> listPD = productDetailRepo.findByProduct(product);
		model.addAttribute("listProductDetail", listPD);

		return "productDetail/index";
	}

	@PutMapping("/addLike/{id}")
	public boolean addLike(@PathVariable("id") Long id, Principal principal) {
		if (principal == null) {
			return false;
		}
		User user = userRepository.findByUsernameEquals(principal.getName());
		Product product = productRepo.findById(id).get();
		productService.addLikeProduct(user, product);
		return true;
	}

	@GetMapping("/getList")
	@ResponseBody
	public List<ProductDetail> find(@RequestParam("id") Long id) {
		return productRepo.findById(id).get().getProductDetails();
	}

	@GetMapping("/getSize")
	@ResponseBody
	public List<ProductDetail> find(@RequestParam("color") String color, @RequestParam("proId") Long id) {
		return productDetailRepo.findByColorAndProductId(color, id);
	}

	@PostMapping("/filter")
	public String findByComboBox(@RequestParam("cateName") @Nullable String cateName,
			@RequestParam("material") @Nullable String material, @RequestParam("color") @Nullable String color,
			@RequestParam("size") @Nullable String size, @RequestParam("order") @Nullable String order, Model model,
			@ModelAttribute("product") Product product, @RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "20") Integer size2) {
		List<Product> lst = productService.getProductByFilter(cateName, material, color, size, order);
		model.addAttribute("create", true);
		List<Category> list = categoryRepo.findAll();
		model.addAttribute("listCategory", list);

		Pageable pageable = PageRequest.of(page, size2);
		Page<Product> data = new Page<Product>() {

			@Override
			public Iterator<Product> iterator() {
				// TODO Auto-generated method stub
				return lst.iterator();
			}

			@Override
			public Pageable previousPageable() {
				// TODO Auto-generated method stub
				return pageable;
			}

			@Override
			public Pageable nextPageable() {
				// TODO Auto-generated method stub
				return pageable;
			}

			@Override
			public boolean isLast() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isFirst() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasPrevious() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean hasContent() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public Sort getSort() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getSize() {
				// TODO Auto-generated method stub
				return 10;
			}

			@Override
			public int getNumberOfElements() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getNumber() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<Product> getContent() {
				// TODO Auto-generated method stub
				return lst;
			}

			@Override
			public <U> Page<U> map(Function<? super Product, ? extends U> converter) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getTotalPages() {
				// TODO Auto-generated method stub
				return 100;
			}

			@Override
			public long getTotalElements() {
				// TODO Auto-generated method stub
				return 1000;
			}
		};
		model.addAttribute("data", data);
		return "customer/product";
	}
}
