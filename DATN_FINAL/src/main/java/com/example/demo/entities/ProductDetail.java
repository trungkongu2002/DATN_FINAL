package com.example.demo.entities;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_details")
public class ProductDetail implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "size")
	private String size;

	@Column(name = "color")
	private String color;

	@Column(name = "quantity")
	private Integer quantity;

	@Column(name = "thumnail")
	private String thumnail;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(name = "create_day")
	private Date createDay;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "modify_day")
	private Date modifyDay;

	@Column(name = "modified_by")
	private Integer modifiedBy;

	@Column(name = "status")
	private Integer status;
}
