package com.infoschool.infoschool.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SigninRequest {
	@NotBlank
	private String email;

	@NotBlank
	private String password;

}
