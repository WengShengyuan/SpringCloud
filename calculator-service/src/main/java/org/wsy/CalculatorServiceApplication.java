package org.wsy;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.protocol.json.SdkStructuredJsonFactoryImpl;

@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class CalculatorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalculatorServiceApplication.class, args);
	}
}

@Controller
class CalculatorController {
	
	@Value("${plus}")
	private String PLUS;
	@Value("${minus}")
	private String MINUS;
	@Value("${times}")
	private String TIMES;
	@Value("${div}")
	private String DIV;
	
	@ResponseBody
	@RequestMapping(name="cal")
	public String cal(@RequestParam("a") Double a, @RequestParam("b") Double b, @RequestParam("calFlag") String calFlag) throws Exception {
		
		System.out.println("TIME -> "+new Date()+" rev -> a:"+a+", b:"+b+", calFlag:"+calFlag+"]");
		
		if(calFlag.equalsIgnoreCase(PLUS))
			return String.valueOf(a+b);
		else if(calFlag.equalsIgnoreCase(MINUS))
			return String.valueOf(a-b);
		else if (calFlag.equalsIgnoreCase(TIMES))
			return String.valueOf(a*b);
		else if (calFlag.equalsIgnoreCase(DIV))
			return String.valueOf(a/b);
		else 
			throw new Exception("wrong cal flag");
	}
	
}
