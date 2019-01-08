package egovframework.smc.util.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public enum RequestType {
	jsonType("json"), formType("form"), multipartFormType("multipartForm"), defaultType("default");
	
	HttpHeaders httpHeaders;
	RestTemplate restTemplate;
	
	String httpType;
	
    
    public static final String FORMAT_HTTP_HEADER_AUTHORIZATION = "Basic %s";
	
	RequestType(String type) {
		httpType = type;
		if(type.equals("json")) {
			httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
		}else if(type.equals("form")) {
			httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			restTemplate = new RestTemplate();
		}else if(type.equals("multipartForm")) {
			httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
			restTemplate = new RestTemplate();
		} if(type.equals("default")) {
			httpHeaders = new HttpHeaders();
			httpHeaders.add("Accept", "*/*");
			restTemplate = new RestTemplate();
		}
	}

	public HttpHeaders getHttpHeaders() {
		return httpHeaders;
	}

	public void setHttpHeaders(HttpHeaders httpHeaders) {
		this.httpHeaders = httpHeaders;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public String getHttpType(){
		return httpType;
	}
	
}
