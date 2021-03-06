/**
 * 0. Project : 한전 전기차 충전 시스템 프로젝트
 * 1. FileName	: UnknownFeildValidator.java
 * 2. Package		: vgip.biz.service.custom.validator
 * 3. Comment		:
 * 4. 작 성 자		: line
 * 5. 작 성 일		: Nov 6, 2018 2:56:13 PM
 * 6. 변 경 이 력	
 * 		이름			:			일자			:			변경내용
 * -----------------------------------------------------------------------
 * 		line	:		Nov 6, 2018		:	신규 개발.
 */
package vgip.biz.service.custom.validator;

import org.springframework.validation.Errors;

import vgip.biz.service.custom.validator.baseValidator.BaseValidator;

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
public class UnknownFieldValidator extends BaseValidator{

	String field;
	
	public UnknownFieldValidator(Class<?> clazz, String field) {
		super(clazz);
		this.field = field;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getField() {
		// TODO Auto-generated method stub
		return field;
	}

	@Override
	public void rejectValue(Errors errors) {
		// TODO Auto-generated method stub
		errors.rejectValue(getField(), "required", getField()+"는 필수값입니다.");
	}

}
