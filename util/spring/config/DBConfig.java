/**
 * 0. Project : 한전 전기차 충전 시스템 프로젝트
 * 1. FileName	: DBConfig.java
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

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;

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
@MapperScan(value="com.line.kepco", sqlSessionFactoryRef="sqlSessionFactory")
@PropertySource("classpath:config.properties")
@EnableTransactionManagement
public class DBConfig {
	
	@Autowired
	private Environment env;
	
	/**
	 * 데이터베이스 설정
	 * @return DataSource
	 * @exception Exception
	 */
	@Bean(name = "dataSource")
	public DataSource dataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(env.getProperty("db.driver"));
		basicDataSource.setUrl(env.getProperty("db.url"));
		basicDataSource.setUsername(env.getProperty("db.uername"));
		basicDataSource.setPassword(env.getProperty("db.password"));
//		basicDataSource.setTestOnBorrow(true);
	   return basicDataSource;
	}
	
	/**
	 * sql로그 설정
	 * @param DataSource
	 * @return Log4jdbcProxyDataSource
	 * @exception Exception
	 */
	@Bean(name = "dataSourceLog")
	public Log4jdbcProxyDataSource dataSourceLog(DataSource dataSource) {
		Log4JdbcCustomFormatter formatter = new Log4JdbcCustomFormatter();
		formatter.setLoggingType(LoggingType.MULTI_LINE);
		formatter.setSqlPrefix("SQL : \n");
		
		return new Log4jdbcProxyDataSource(dataSource);
	}
	
	/**
	 * 트렌잭션 관리 설정
	 * @param DataSource
	 * @return PlatformTransactionManager
	 * @exception Exception
	 */
	@Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        transactionManager.setGlobalRollbackOnParticipationFailure(false);
        return transactionManager;
    }
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(Log4jdbcProxyDataSource dataSourceLog) throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		
		// 쿼리 로그를 안나오게 하시려면 dataSourceLog -> dataSource로 변경하세요.
		sqlSessionFactory.setDataSource(dataSourceLog);
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/**/*.xml"));
		
		return (SqlSessionFactory) sqlSessionFactory.getObject();
	}
	
	@Bean
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}
