package egovframework.smc.util.rest.api;

import org.springframework.stereotype.Component;

import egovframework.smc.util.rest.ApiRestUtil;

@Component
public class ProfileApiRestUtil extends ApiRestUtil{
	
	@Override
	public String getAuthKey() {
		// TODO Auto-generated method stub
		return "Profile" + ":" + "38fcdb7cb0ee4112";
	}

	@Override
	public String getServiceType() {
		// TODO Auto-generated method stub
		return "Profile";
	}
    
    
}
