/**
 * 0. Project : 한전 전기차 충전 시스템 프로젝트
 * 1. FileName	: Role.java
 * 2. Package		: com.line.kepco.session
 * 3. Comment		:
 * 4. 작 성 자		: line
 * 5. 작 성 일		: 2018. 10. 15. 오후 3:27:08
 * 6. 변 경 이 력	
 * 		이름			:			일자			:			변경내용
 * -----------------------------------------------------------------------
 * 		line	:		2018. 10. 15.		:	신규 개발.
 */
package com.line.kepco.security;

/**
 * @author line
 *
 * @sess <pre>
 * == 개정이력(Modification information) ==
 * 수정일 		수정자 		수정내용
 * -------------------------------------------------------
 * 2014.01.24	line	최초 생성
 *
 * </pre>
 */
public enum Role {
	SUPERVISOR("supervisor"),

	TELLER("teller"),

	USER("user");

	private final String springSecurityRoleName;

	private Role(String springSecurityRoleName) {
		this.springSecurityRoleName = springSecurityRoleName;
	}

	public String getSpringSecurityRoleName() {
		return springSecurityRoleName;
	}
}
