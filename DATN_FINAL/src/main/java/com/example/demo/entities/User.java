package com.example.demo.entities;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

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
@Table(name = "users")
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "username")
	private String username;

	@Column(name = "email")
	@Email
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "avartar")
	private String avatar;

	@Column(name = "gender")
	private Integer gender;

	@Column(name = "phone_number")
	@Size(min = 10, max = 11)
	private String phoneNumber;

	@Column(name = "status")
	private Integer status;

	@Column(name = "provine_city")
	private String provineCity;

	@Column(name = "district")
	private String district;

	@Column(name = "ward")
	private String ward;

	@Column(name = "address")
	private String address;

	@Column(name = "total_spending")
	private BigDecimal totalSpending;

	@Column(name = "tb_coin")
	private BigDecimal tbCoin;

	@Column(name = "create_day")
	private Date createDay;

	@Column(name = "is_member")
	private Integer isMember;

	@Column(name = "member_type")
	private String memberType;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	List<UserRole> userRoles;

}
