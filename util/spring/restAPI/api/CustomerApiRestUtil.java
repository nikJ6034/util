package egovframework.smc.util.rest.api;

import org.springframework.stereotype.Component;

import egovframework.smc.util.rest.ApiRestUtil;

@Component
public class CustomerApiRestUtil extends ApiRestUtil{
	
	@Override
	public String getAuthKey() {
		// TODO Auto-generated method stub
		return "CustomPortal" + ":" + "546655b9f357404a";
	}

	@Override
	public String getServiceType() {
		// TODO Auto-generated method stub
		return "CustomPortal";
	}
    
    
}
