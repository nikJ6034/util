/**
 * 0. Project : 한전 전기차 충전 시스템 프로젝트
 * 1. FileName	: BaseValidator.java
 * 2. Package		: vgip.biz.service.custom.validator
 * 3. Comment		:
 * 4. 작 성 자		: line
 * 5. 작 성 일		: Nov 6, 2018 11:20:28 AM
 * 6. 변 경 이 력	
 * 		이름			:			일자			:			변경내용
 * -----------------------------------------------------------------------
 * 		line	:		Nov 6, 2018		:	신규 개발.
 */
package vgip.biz.service.custom.validator.baseValidator;

import java.lang.reflect.Method;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author line
 *
 * @sess <pre>
 * == 개정이력(Modification information) ==
 * 수정일 		수정자 		수정내용
 * -------------------------------------------------------
 * Nov 6, 2018 	line	최초 생성
 *
 * </pre>
 */
public abstract class BaseValidator implements Validator{

	Class<?> clazz;
	
	@SuppressWarnings("unused")
	private BaseValidator() {};
	
	public BaseValidator(Class<?> clazz){
		this.clazz = clazz;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Method method;
		try {
			method = clazz.getMethod(getMethodName());
			String memberId = (String)method.invoke(target);
			if(isEmptyOrWhitespace(memberId)){
				rejectValue(errors);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			errors.reject("exception", getField()+"Error");
		}
	}
	
	public abstract String getField();
	
	public abstract void rejectValue(Errors errors);
	
	private String getMethodName() {
		StringBuilder stringBuilder = new StringBuilder(getField());
		stringBuilder.setCharAt(0, Character.toUpperCase(getField().charAt(0)));
		return "get"+stringBuilder.toString();
	}
	
	public boolean isEmptyOrWhitespace(String value){
		if(value==null || value.trim().length() == 0){
			return true;
		}else{
			return false;
		}
	}

}
