package com.example.demo.entities;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "products")
public class Product implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "product_name")
	private String name;

	@Column(name = "unit_price")
	private Double price;

	@Column(name = "note")
	private String note;

	@Column(name = "material")
	private String material;
	
	private String image;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<DiscountDetail> discountDetails;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<ProductDetail> productDetails;

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
