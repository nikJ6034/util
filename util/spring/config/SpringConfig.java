/**
 * 0. Project : 한전 전기차 충전 시스템 프로젝트
 * 1. FileName	: SpringConfig.java
 * 2. Package		: com.line.kepco.config
 * 3. Comment		:
 * 4. 작 성 자		: line
 * 5. 작 성 일		: 2018. 10. 15. 오후 1:36:08
 * 6. 변 경 이 력	
 * 		이름			:			일자			:			변경내용
 * -----------------------------------------------------------------------
 * 		line	:		2018. 10. 15.		:	신규 개발.
 */

package com.line.kepco.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
@Configuration
@EnableWebMvc
@ComponentScan("com.line.kepco")
public class SpringConfig implements WebMvcConfigurer{
	
}
