package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entities.Discount;
import com.example.demo.excelexporter.DiscountExcelExporter;
import com.example.demo.repository.DiscountRepository;
import com.example.demo.service.DiscountService;

import java.util.List;

import javax.validation.Valid;

@Controller
public class DiscountController {

	@Autowired
	DiscountService discountService;

	@Autowired
	DiscountRepository discountRepository;

	@RequestMapping("admin/discount/list")
	public String listDiscount(Model model) {
		List<Discount> lstDiscount = discountService.getAllDiscount();
		model.addAttribute("listDiscount", lstDiscount);
		return "admin/discount/tables";
	}

	@RequestMapping("admin/discount/save")
	public String createDiscount(Discount discount, Model model) {
		return "admin/discount/create";
	}

	@RequestMapping(value = "/api/discount/save", method = RequestMethod.POST)
	public String insertDiscount(@Valid @ModelAttribute("discount") Discount newDiscount, Model model, BindingResult result) {
		try {
			Discount existingDiscountName = discountRepository.findByDiscountNameEquals(newDiscount.getDiscountName());
			if (existingDiscountName != null) {
				model.addAttribute("error1", "Mã giảm giá đã tồn tại!");
				return "admin/discount/create";
			}
			if (result.hasErrors()) {
				return "admin/discount/create";
			}
			newDiscount.setCondition(0);
			discountRepository.save(newDiscount);
			return "redirect:/admin/discount/save/success";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/discount/save/failed";
		}
	}

	@RequestMapping("admin/discount/save/success")
	public String insertSuccessAccount(Model model) {
		List<Discount> lstDiscount = discountService.getAllDiscount();
		model.addAttribute("listDiscount", lstDiscount);
		model.addAttribute("insertSuccess", "Thêm mã giảm giá thành công!");
		return "admin/discount/tables";
	}

	@RequestMapping("admin/discount/save/failed")
	public String insertFailedAccount(Model model) {
		List<Discount> lstDiscount = discountService.getAllDiscount();
		model.addAttribute("listDiscount", lstDiscount);
		model.addAttribute("insertFailed", "Thêm mã giảm giá thất bại!");
		return "admin/discount/tables";
	}

	@RequestMapping("/api/discount/update/{id}")
	public String editDiscount(@PathVariable("id") Long id, Model model) {
		model.addAttribute("discount", discountService.getDiscountById(id));
		return "admin/discount/update";
	}

	@RequestMapping(value = "/api/discount/{id}", method = RequestMethod.POST)
	public String updateDiscount(@Valid @ModelAttribute("discount") Discount newDiscount, @PathVariable("id") Long id,
			Model model) {
		try {
			Discount _discountExisting = discountService.getDiscountById(id);
			_discountExisting.setDecreasePercent(newDiscount.getDecreasePercent());
			_discountExisting.setCondition(newDiscount.getCondition());
			_discountExisting.setStartDay(newDiscount.getStartDay());
			_discountExisting.setEndDay(newDiscount.getEndDay());
			discountRepository.save(_discountExisting);
			return "redirect:/admin/discount/update/success";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/discount/update/failed";
		}
	}

	@RequestMapping("admin/discount/update/success")
	public String updateSuccess(Model model) {
		List<Discount> lstDiscount = discountService.getAllDiscount();
		model.addAttribute("listDiscount", lstDiscount);
		model.addAttribute("updateSuccess", "Thay đổi mã giảm giá thành công!");
		return "admin/discount/tables";
	}

	@RequestMapping("admin/discount/update/failed")
	public String updateFailed(Model model) {
		List<Discount> lstDiscount = discountService.getAllDiscount();
		model.addAttribute("listDiscount", lstDiscount);
		model.addAttribute("updateFailed", "Thay đổi mã giảm giá thất bại!");
		return "admin/discount/tables";
	}

	/***
	 * Export data to excel file
	 */
	@RequestMapping(value = "admin/discount/excelExport", method = RequestMethod.GET)
	public ModelAndView exportToExcel() {
		ModelAndView mav = new ModelAndView();
		mav.setView(new DiscountExcelExporter());
		// read data from DB
		List<Discount> list = discountService.getAllDiscount();
		// send to excelImpl class
		mav.addObject("list", list);
		return mav;
	}

}
