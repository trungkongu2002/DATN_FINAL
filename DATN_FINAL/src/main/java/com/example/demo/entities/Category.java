package com.example.demo.entities;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "category_name")
	@NotEmpty
	private String name;

	@Column(name = "note")
	private String note;

	@Column(name = "create_day")
	private Date createDay;

	@Column(name = "created_by")
	private Integer createdBy;

	@Column(name = "modify_day")
	private Date modifyDay;

	@Column(name = "modified_by")
	private Integer modifiedBy;

	@Column(name = "status")
	private int status;
}
