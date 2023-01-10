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
public class CategoryDTO {

	private Integer id;
	private String name;
	private String note;
	private Date createDay;
	private String createdBy;
	private Date modifyDay;
	private String modifiedBy;
	private String status;

}
