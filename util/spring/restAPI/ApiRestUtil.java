package egovframework.smc.util.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import egovframework.common.jfile.org.json.JSONArray;
import egovframework.common.jfile.org.json.JSONException;
import egovframework.common.jfile.org.json.JSONObject;
import egovframework.common.util.EgovProperties;
import egovframework.common.util.JsonUtil;

public abstract class ApiRestUtil {
    
	public static final String FORMAT_HTTP_HEADER_AUTHORIZATION = "Basic %s";
	
	public abstract String getAuthKey();
	
	public abstract String getServiceType();
    
    private String getHeaderAuthorization() {
        String authKey = getAuthKey(); //"CustomPortal" + ":" + "546655b9f357404a";
        String format = String.format(FORMAT_HTTP_HEADER_AUTHORIZATION, Base64.encodeBase64String(authKey.getBytes()));
        return format;
     }
    
    
    private ResponseEntity<String> getRest(String url,  Map<String, Object> parameter) {
     	RequestType request = RequestType.jsonType;
		String temUrl = getResTemplateUrl(url);
		HttpHeaders httpHeaders = request.getHttpHeaders();
		httpHeaders.set(HttpHeaders.AUTHORIZATION, getHeaderAuthorization());
		httpHeaders.set("ServiceType", getServiceType());
		httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		RestTemplate restTemplate = request.getRestTemplate();
		HttpEntity<?> httpEntity = new HttpEntity<Object>(httpHeaders);
		String finalUrl = urlEncodingHttpGet(parameter, temUrl);
		return restTemplate.exchange(finalUrl, HttpMethod.GET, httpEntity, String.class);
    }
	
	 /**
     * REST API 호출 하여 restTemplate를 통해 List 에 담기
     * @param url
     * @param parameter
     * @return
     */
    public List<Map<String, Object>> getRestList( String url,  Map<String, Object> parameter) {
    	List<Map<String, Object>> result = null;
    	try {
    		result = new ArrayList<Map<String,Object>>();
    		ResponseEntity<String> rest = getRest(url,parameter);
    			// 리스트에  결과 리스트를 담음
    		JSONArray jsonData;
    		jsonData = new JSONArray(rest.getBody());
			result = JsonUtil.getListMapFromJsonArray(jsonData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
     	
    }
    
    /**
     * REST API 호출 하여 restTemplate를 통해 List 에 담기
     * @param url
     * @return
     */
    public List<Map<String, Object>> getRestList(String url) {
    	return getRestList(url, null);
    }
    
    /**
     * REST API 호출 하여 restTemplate를 통해  Map< String, Object > 에 담기
     * @param url
     * @param parameter
     * @return
     */
    public Map<String, Object>  getRestMap( String url,  Map<String, Object> parameter) {
    	
    	// Map으로 결과를 담음
    	Map<String, Object> resultMap = null;
     	
    	try {
    		resultMap = new HashMap<String, Object>();
    		ResponseEntity<String> rest = getRest(url,parameter);
			JSONObject jsonData = new JSONObject(rest.getBody());
			resultMap = JsonUtil.getMapFromJsonObject(jsonData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         	
    	 return resultMap;
    }
    
    /**
     * REST API 호출 하여 restTemplate를 통해  Map< String, Object > 에 담기
     * @param url
     * @return
     */
    public Map<String, Object> getRestMap(String url) {
    	return getRestMap(url, null);
    }
    
    private void setDefaultHeader(HttpHeaders httpHeaders) {
		httpHeaders.set(HttpHeaders.AUTHORIZATION, getHeaderAuthorization());
		httpHeaders.set("ServiceType", getServiceType());
    }
    
    /**
     * HTTP METHOD 방식이 POST
     * HEADER TYPE 
     * 				- JSON
     * 				- FORM 
     * 에 따라 정보 RestTemplate을 이용하는 메서드 
     * - 관련 내역 
     * 		sendPostJson(String url)
     * 		sendPostJson(String url, Map<String, Object> param)
     * 		sendPostForm(String url)
     * 		sendPostForm(String url, Map<String, Object> param)
     * 		sendPost(String url, RequestType request, Map<String, Object> param )
     * @param url 
     * @param  Map<String,Object> param
     * @return Map<String,Object> result;
     */
	public Map<String,Object> sendPostJson(String url, Map<String, Object> param) {
		RequestType requestType = RequestType.jsonType;
		HttpEntity<?> httpEntity = getDefultTypeEntity(requestType, param);
		return sendRequest(url,requestType.getRestTemplate() ,httpEntity, HttpMethod.POST );
	}
	
	public Map<String,Object> sendPostJson(String url) {
		return sendPostJson(url, null);
	}

	public Map<String,Object> sendPostForm(String url, Map<String, Object> param) {
		RequestType requestType = RequestType.formType;
		HttpEntity<?> httpEntity = getFormTypeEntity(requestType, param);
		return sendRequest(url, requestType.getRestTemplate() ,httpEntity, HttpMethod.POST );
	}
	
	public Map<String,Object> sendPostForm(String url) {
		return sendPostForm(url, null);
	}
	
	public Map<String,Object> sendPostMulipartForm(String url, Map<String, Object> param) {
		RequestType requestType = RequestType.multipartFormType;
		HttpEntity<?> httpEntity = getFormTypeEntity(requestType, param);
		return sendRequest(url, requestType.getRestTemplate() ,httpEntity, HttpMethod.POST );
	}
	
	 /**
     * HTTP METHOD 방식이 PUT
     * HEADER TYPE 
     * 				- JSON
     * 				- FORM 
     * 에 따라 정보 RestTemplate을 이용하는  메서드 
     * - 관련 내역 
     * 		sendPutJson(String url)
     * 		sendPutJson(String url, Map<String, Object> param)
     * 		sendPutForm(String url)
     * 		sendPutForm(String url, Map<String, Object> param)
     * 		sendPut(String url, RequestType request, Map<String, Object> param )
     * @param url 
     * @param  Map<String,Object> param
     * @return Map<String,Object> result;
     */
	public Map<String,Object> sendPutJson(String url, Map<String, Object> param) {
		RequestType requestType = RequestType.jsonType;
		HttpEntity<?> httpEntity = getDefultTypeEntity(requestType, param);
		return sendRequest(url,requestType.getRestTemplate() ,httpEntity, HttpMethod.PUT );
	}
	
	public Map<String,Object> sendPutJson(String url) {
		return sendPutJson(url, null);
	}

	public Map<String,Object> sendPutForm(String url, Map<String, Object> param) {
		RequestType requestType = RequestType.formType;
		HttpEntity<?> httpEntity = getFormTypeEntity(requestType, param);
		return sendRequest(url, requestType.getRestTemplate() ,httpEntity, HttpMethod.PUT );
	}
	
	public Map<String,Object> sendPutForm(String url) {
		return sendPutForm(url, null);
	}
	
	public Map<String,Object> sendPutMulipartForm(String url, Map<String, Object> param) {
		RequestType requestType = RequestType.multipartFormType;
		HttpEntity<?> httpEntity = getFormTypeEntity(requestType, param);
		return sendRequest(url, requestType.getRestTemplate() ,httpEntity, HttpMethod.PUT );
	}
	
	/**
     * HTTP METHOD 방식이 DELETE
     * HEADER TYPE 
     * 				- JSON
     * 				- FORM 
     * 에 따라 정보 RestTemplate을 이용하는  메서드 
     * - 관련 내역 
     * 		sendDeleteJson(String url)
     * 		sendDeleteJson(String url, Map<String, Object> param)
     * 		sendDeleteForm(String url)
     * 		sendDeleteForm(String url, Map<String, Object> param)
     * 		sendDelete(String url, RequestType request, Map<String, Object> param )
     * @param url 
     * @param  Map<String,Object> param
     * @return Map<String,Object> result;
     */
	public Map<String,Object> sendDeleteJson(String url, Map<String, Object> param) {
		RequestType requestType = RequestType.jsonType;
		HttpEntity<?> httpEntity = getDefultTypeEntity(requestType, param);
		return sendRequest(url,requestType.getRestTemplate() ,httpEntity, HttpMethod.DELETE );
	}

	public Map<String,Object> sendDeleteForm(String url, Map<String, Object> param) {
		RequestType requestType = RequestType.formType;
		HttpEntity<?> httpEntity = getFormTypeEntity(requestType, param);
		return sendRequest(url, requestType.getRestTemplate() ,httpEntity, HttpMethod.DELETE );
	}
	
	private Map<String,Object> sendRequest(String url,RestTemplate restTemplate, HttpEntity<?> httpEntity, HttpMethod httpMethod) {
		Map<String,Object> result = new HashMap<String,Object>();
		String temUrl = getResTemplateUrl(url);
		ResponseEntity<String> exchange = null;
//		exchange = restTemplate.postForEntity(temUrl, httpEntity, String.class);
		exchange = restTemplate.exchange(temUrl, httpMethod, httpEntity, String.class);
		result = exchangeResult(result , exchange);
		return result;
	}
	
	/**
	 * Map형태로Post 방식에 사용할 
	 * Data 담기
	 * @param parameter
	 * @return MultiValueMap<String, String>
	 */
	private MultiValueMap<String, Object> getMultiValueMap(Map<String, Object> parameter){
		MultiValueMap<String, Object> paramMultiMap = new LinkedMultiValueMap<String, Object>();
		for (String key : parameter.keySet()) {
			paramMultiMap.add(key, parameter.get(key));
		}
        return paramMultiMap;
	}
	
	
	/**
	 * url 정보
	 * 로그아웃을 하기위해 
	 * nid.naver.com 을 예외 상황으로 두었다.
	 * @param url
	 * @return application.yml 에 등록된 정보 를 갖고온다.
	 */
	private String getResTemplateUrl(String url){
		String tempUrl = EgovProperties.getProperty("platform.path")+url;
		return tempUrl;
	}

	/**
	 * 기본 엔티티 타입 form 외에 사용하세요.
	 * @param requestType
	 * @param param
	 * @return
	 */
	private HttpEntity<?> getDefultTypeEntity(RequestType requestType, Map<String,Object>param){
		HttpHeaders httpHeaders = requestType.getHttpHeaders();
		setDefaultHeader(httpHeaders);
		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(param, requestType.getHttpHeaders());
		return httpEntity;
	}
	
	/**
	 * form 엔티티 타입
	 * @param requestType
	 * @param param
	 * @return
	 */
	private HttpEntity<?> getFormTypeEntity(RequestType requestType, Map<String,Object>param){
		HttpHeaders httpHeaders = requestType.getHttpHeaders();
		setDefaultHeader(httpHeaders);
		MultiValueMap<String, Object> multiParam= getMultiValueMap(param);
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(multiParam, httpHeaders);
		return httpEntity;
	}
	
	
	/**
	 * 나중에 사용에 따라 코드 수정 하셔도 될것 같습니다! 
	 * @param result
	 * @param exchange
	 * @return Map<String, Object>
	 */
	private Map<String, Object> exchangeResult(Map<String,Object> result, ResponseEntity<String> exchange) {
		
		result.put("status", exchange.getStatusCode().toString());
		if(StringUtils.isNotBlank(exchange.getBody())){
			result.put("data", getMapFromStringJsonArrayBySimpleJSONObject(exchange.getBody()));
		}
		
		return result;
	}
	
	/**
     * script에서 JSON.stringify 배열화시킨 String 으로
     * org.json.simple.JSONObject를 이용하여 
     * Map 형태로 변환 해주는 작업이다.
     * @param jsonArray
     * @return Map<String, Object>
     */
    @SuppressWarnings("unchecked")
	public static Map<String, Object> getMapFromStringJsonArrayBySimpleJSONObject(String jsonArray){
    	
    	JSONParser parser = new JSONParser();
		Object obj = null;
		try {
			obj = parser.parse( jsonArray );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject) obj;
		
		Map<String, Object> map = null;
        try {
            map = new ObjectMapper().readValue(jsonObj.toJSONString(), Map.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        return map;	
    }
	
	private String urlEncodingHttpGet(Map<String,Object> param , String url){
		String result = "";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
				if(param != null){
					for(String key : param.keySet()){
						if(param.get(key) instanceof String[]){
							String arrayStr = "";
							for(String str : (String[])param.get(key)){
								arrayStr += str+",";
							}
							if(arrayStr.substring(arrayStr.length()-1).equals(",")){
								arrayStr = arrayStr.substring(0, arrayStr.length()-1);
							}
							builder.queryParam(key, arrayStr);
						}
						else{
							builder.queryParam(key, param.get(key));
						}
					}
				}
		try {
			result = URLDecoder.decode(builder.toUriString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
		return result;
	}
	
}
