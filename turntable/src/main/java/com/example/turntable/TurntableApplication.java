package com.example.turntable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class TurntableApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurntableApplication.class, args);
	}

	@GetMapping("/todayplaylist")
	public String todayplaylist() {
		return "todayPlaylist";
	}

	@GetMapping("/main")
	public String main() {
		return "main";
	}

	@GetMapping("/login")
	public String loginForm() {
		return "login";
	}

	@GetMapping("/signup")
	public String signupForm() {
		return "signup";
	}
}
