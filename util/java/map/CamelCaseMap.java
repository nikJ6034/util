/**
 * 0. Project : 한전 전기차 충전 시스템 프로젝트
 * 1. FileName	: CamelCaseMap.java
 * 2. Package		: vgip.biz.service.custom.util
 * 3. Comment		:
 * 4. 작 성 자		: line
 * 5. 작 성 일		: Nov 6, 2018 3:24:40 PM
 * 6. 변 경 이 력	
 * 		이름			:			일자			:			변경내용
 * -----------------------------------------------------------------------
 * 		line	:		Nov 6, 2018		:	신규 개발.
 */
package com.kepco.ev.util;

import java.util.HashMap;

import com.google.common.base.CaseFormat;


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
public class CamelCaseMap extends HashMap<String, Object>{

	private static final long serialVersionUID = 1L;

	@Override
	public Object put(String key, Object value) {
		// TODO Auto-generated method stub
		String camelCaseKey = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL,key.toUpperCase());
		return super.put(camelCaseKey, value);
	}
	
}
