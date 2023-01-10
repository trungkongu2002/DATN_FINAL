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
public class ProductDTO {

	private Long id;
	private String name;
	private Double price;
	private String note;
	private String material;
	private String category;
	private Date createDay;
	private String createdBy;
	private Date modifyDay;
	private String modifiedBy;
	private String status;
}
