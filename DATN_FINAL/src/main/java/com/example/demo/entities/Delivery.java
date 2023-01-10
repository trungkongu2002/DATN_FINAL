package com.example.demo.entities;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "deliveries")
public class Delivery {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@OneToOne
	@JoinColumn(name = "bill_id")
	private Bill bill;

	@CreationTimestamp
	@Column(name = "create_time")
	private Date createDay;

	@Column(name = "create_by")
	private Integer createBy;

	@Column(name = "status")
	private Integer status;

	@Column(name = "address")
	private String address;

	@Column(name = "ward")
	private String ward;

	@Column(name = "district")
	private String district;

	@Column(name = "province")
	private String province;

	@Column(name = "services")
	private Integer services;
	

	@Column(name = "note")
	private String note;

	@Column(name = "end_day")
	private Date endDay;
}
