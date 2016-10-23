package org.wsy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@EnableZuulProxy
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
public class ApiProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiProxyApplication.class, args);
	}
}

@RestController
@RequestMapping("/calgate")
class ReservationApiGatewayRestController {

	private static final String CALSERVICE = "calculator-service";
	private static List<ServiceInstance> services = null;

	@Autowired
	private DiscoveryClient discoveryClient;

	public String calfallback(double a, double b, String calFlag) {
		return "calculator services down";
	}

	@HystrixCommand(fallbackMethod = "calfallback")
	@RequestMapping("/cal")
	public String cal(@RequestParam("a") double a, @RequestParam("b") double b, @RequestParam("calFlag") String calFlag)
			throws Exception {
		System.out.println("TIME->"+new Date()+" rev -> [a:" + a + ", b:" + b + ", calFlag:" + calFlag + "]");
		services = discoveryClient.getInstances(CALSERVICE);
		System.out.println("TIME->"+new Date()+" get " + services.size() + " available services");
		String url = services.get(new Random().nextInt(services.size())).getUri() + "/cal";
		System.out.println("request service url:" + url);
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("a", String.valueOf(a)));
			params.add(new BasicNameValuePair("b", String.valueOf(b)));
			params.add(new BasicNameValuePair("calFlag", calFlag));
			return "TIME->"+new Date()+" service return : " + HTTP.post(params, url);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

}