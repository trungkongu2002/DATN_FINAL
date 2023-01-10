package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BillDetailDTO {

	private Long id;
	private Long bill;
	private String product;
	private Integer quantity;
	private Double unitPrice;
	private Double total;
	private String size;
	private String color;
	private String thumnail;
}
