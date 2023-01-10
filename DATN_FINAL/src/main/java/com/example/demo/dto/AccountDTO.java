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
public class AccountDTO {
	private Integer id;
	private String username;
	private String password;
	private Integer role;
	private Date createDay;
	private Integer status;
}
