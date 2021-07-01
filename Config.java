package com.gokul;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ConfigurationProperties
@EnableJpaRepositories(basePackages="com.gokul.dao", entityManagerFactoryRef="emf")
@EnableAutoConfiguration
@ComponentScan(basePackages="com.gokul.entity")
public class Config {
	@Value("${spring.url}")
	String url;
	@Value("${spring.driver}")
	String driver;
	@Value("${spring.username}")
	String username;
	@Value("${spring.password}")
	String password;
	@Value("${dialect}")
	String dialect;
	@Value("${show}")
	String show;
	@Value("${hb}")
	String hb;
	
	@Bean
	public DataSource dataSource()
	{
		DriverManagerDataSource dr=new DriverManagerDataSource();
		dr.setUrl(url);
		dr.setDriverClassName(driver);
		dr.setPassword(password);
		dr.setUsername(username);
		return dr;
	}
	
	@Bean(name="emf")
	public LocalSessionFactoryBean sessionFactory()
	{
		LocalSessionFactoryBean ls=new LocalSessionFactoryBean();
		ls.setDataSource(dataSource());
		ls.setHibernateProperties(hibernateProperties());
		ls.setPackagesToScan(new String[] {"com.gokul.entity"});
		return ls;
		
	}

	private Properties hibernateProperties() {
		Properties p= new Properties();
		p.put("hibernate.dialect", dialect);
		p.put("hibernate.show_sql ", show);
		p.put("hibernate.hbm2ddl.auto",hb);
 		return p;
	}
}
