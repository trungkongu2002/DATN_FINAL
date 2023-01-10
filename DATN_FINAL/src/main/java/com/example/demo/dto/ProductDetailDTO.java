package com.example.demo.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDetailDTO {

	private Long id;
	private String size;
	private String color;
	private Integer quantity;
	private Double price;
	private String thumnail;
	private String product;
	private String category;
	private Date createDay;
	private String createdBy;
	private Date modifyDay;
	private String modifiedBy;
	private String note;
	private String status;
}
