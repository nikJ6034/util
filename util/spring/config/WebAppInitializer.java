/**
 * 0. Project : 한전 전기차 충전 시스템 프로젝트
 * 1. FileName	: WebAppInitializer.java
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
import javax.servlet.FilterRegistration;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.wicket.protocol.http.WicketFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;

import com.line.kepco.WicketKepcoApplication;

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
public class WebAppInitializer implements WebApplicationInitializer{

	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        servletContext.addListener(new ContextLoaderListener(rootContext));
        rootContext.register(SpringConfig.class);
        
        FilterRegistration encodingFilter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");
        
        FilterRegistration springSecurityFilterChain = servletContext.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
        springSecurityFilterChain.addMappingForUrlPatterns(null, false, "/*");
        
        FilterRegistration wicketFilter = servletContext.addFilter("WicketKepcoApplication", WicketFilter.class);
        wicketFilter.setInitParameter("applicationClassName", WicketKepcoApplication.class.getName());
        wicketFilter.setInitParameter(WicketFilter.FILTER_MAPPING_PARAM, "/*");
        wicketFilter.addMappingForUrlPatterns(null, false, "/*");
        
        
        
	}

}
