package com.example.demo.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bills")
public class Bill implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id")
	private User customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "staff_id")
	private User staff;

	@CreationTimestamp
	@Column(name = "create_day")
	private Date createDay;

	@Column(name = "total")
	private double total;

	@Column(name = "payment_type")
	private String paymentType;

	@Column(name = "status")
	private Integer status;

	@Column(name = "note")
	private String note;
	
	@Column(name = "delete_day")
    private Date deleteDay;

	@OneToMany(mappedBy = "bill")
	@JsonIgnore
	List<BillDetail> billDetails;
}
