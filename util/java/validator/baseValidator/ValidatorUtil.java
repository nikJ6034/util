/**
 * 0. Project : 한전 전기차 충전 시스템 프로젝트
 * 1. FileName	: ValidatorUtil.java
 * 2. Package		: vgip.biz.service.custom.validator
 * 3. Comment		:
 * 4. 작 성 자		: line
 * 5. 작 성 일		: Nov 6, 2018 1:09:59 PM
 * 6. 변 경 이 력	
 * 		이름			:			일자			:			변경내용
 * -----------------------------------------------------------------------
 * 		line	:		Nov 6, 2018		:	신규 개발.
 */
package vgip.biz.service.custom.validator.baseValidator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
public class ValidatorUtil {
	
	private Object target;
	private Errors errors;
//	private Class<? extends RequiredBaseValidator>[] validators;
	private Class<?> vo;
	private List<Class<? extends BaseValidator>> validators;
	
	public ValidatorUtil(Class<?> vo ,Object target, Errors errors){
		this.vo = vo;
		this.target = target;
		this.errors = errors;
		this.validators = new ArrayList<Class<? extends BaseValidator>>();
	}
	
	public void add(Class<? extends BaseValidator> validator) {
		validators.add(validator);
	}
	
	public void validate() {
		Iterator<Class<? extends BaseValidator>> iterator = validators.iterator();
		
		while(iterator.hasNext()) {
			Class<? extends BaseValidator> validator = iterator.next();
			try {
				Constructor<? extends BaseValidator> constructor = validator.getConstructor(Class.class);
				Validator newValidator = constructor.newInstance(vo);
				newValidator.validate(target, errors);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
