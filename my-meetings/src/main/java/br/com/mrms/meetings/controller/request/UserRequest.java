package br.com.mrms.meetings.controller.request;

import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

	private Integer id;
	
	@NotBlank(message = "{user.name.not-blank}")
	@Size(min = 2, max = 150, message = "{user.name.size}")
	private String name;
	
	@NotBlank(message = "{user.password.not-blank}")
	@Column(nullable = false)
	private String password;
	
	private Set<RoleRequest> roles;
	
}
