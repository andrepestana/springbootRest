package br.com.superpestana.springbootRest.model;

public class LoginResponse {

	private String token;

	public LoginResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}
	
}
