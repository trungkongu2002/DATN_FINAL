package com.example.demo.entities;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "interaction")
@Data
public class Interaction {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "like_status")
	private Integer likeStatus;

	@Column(name = "rating")
	private int rating;

	@Column(name = "create_time")
	private Date createTime;

	@Column(name = "modify_time")
	private Date modifyTime;

	@Column(name = "comment")
	private String comment;

	@Column(name = "status")
	private Boolean status;
}
