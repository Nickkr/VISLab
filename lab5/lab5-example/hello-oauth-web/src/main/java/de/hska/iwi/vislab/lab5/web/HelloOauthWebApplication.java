package de.hska.iwi.vislab.lab5.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableOAuth2Client
@Controller
public class HelloOauthWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloOauthWebApplication.class, args);
	}

	@Value("${oauth.resource:http://localhost:8080}")
	private String baseUrl;

	@Value("${oauth.authorize:http://localhost:8080/oauth/authorize}")
	private String authorizeUrl;

	@Value("${oauth.token:http://localhost:8080/oauth/token}")
	private String tokenUrl;

	@Autowired
	private OAuth2RestOperations restTemplate;

	@RequestMapping("/")
	public String home() {
		return "greet";
	}

	@RequestMapping("/greet")
	public String next(Model model) {
		String hello = restTemplate.getForObject(baseUrl + "/hello", String.class);
		model.addAttribute("greet", hello);
		return "greet";
	}

	@RequestMapping("/fibonacci")
	public String getFibonacciView(Model model) {
		model.addAttribute("fibNumber", '-');
		return "fibonacci";
	}

	@PostMapping("/fibonacci")
	public String nextFibonacciNumber(Model model) {
		FibonacciNumber fibNumber = restTemplate.postForObject(baseUrl + "/fibonaccinumber", null ,FibonacciNumber.class);
		model.addAttribute("fibNumber", fibNumber.value);
		return "fibonacci::fibNumber";
		
	}

	@DeleteMapping("/fibonacci")
	public String resetFibonacciNumber(Model model) {
		restTemplate.delete(baseUrl + "/fibonaccinumber");
		model.addAttribute("fibNumber", '-');
		return "fibonacci::fibNumber";
	}

	@Bean
	public OAuth2RestOperations restTemplate(OAuth2ClientContext oauth2ClientContext) {
		return new OAuth2RestTemplate(resource(), oauth2ClientContext);
	}

	@Bean
	protected OAuth2ProtectedResourceDetails resource() {
		ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
		resource.setAccessTokenUri(tokenUrl);
		resource.setClientId("my-client-with-secret");
		resource.setClientSecret("secret");
		return resource;
	}
}
