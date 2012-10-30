package com.programmingspring.koans;

import java.util.Map;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import spock.lang.Specification;

public class SpockApplicationContextKoanTest extends Specification {

	def "testKoan1CreateEmptyApplicationContext"() {
		when:
		def applicationContext = new GenericApplicationContext()

		then:
		applicationContext != null
		applicationContext.getClass() == GenericApplicationContext.class
	}

	def "testKoan2CreateARootBeanDefinition"() {
		setup:
		def beanClass = SampleRepository.class

		when:
		def builder = BeanDefinitionBuilder.rootBeanDefinition(beanClass);

		then:
		builder != null
		builder.getBeanDefinition().getBeanClass() == SampleRepository.class
	}

	def "testKoan3AddASpringBeanProgrammaticallyToAnApplicationContextRetrievingByType"() {
		setup:
		def applicationContext = new GenericApplicationContext();
		def builder = BeanDefinitionBuilder.rootBeanDefinition(SampleRepository.class);

		when:
		applicationContext.registerBeanDefinition("repository", builder.getBeanDefinition());

		then:
		applicationContext.getBean(Repository.class) != null
	}
	
	def "testKoan4CreateApplicationContextWithValidBeansDocumentAndNoBeans"() {
		when:
		def  applicationContext = new ClassPathXmlApplicationContext("no-beans.xml");
		
		then:
		applicationContext != null
	}

	def "testKoan5CreateApplicationContextWithOneBeanRetrievingByType"() {
		setup:
		def applicationContext = new ClassPathXmlApplicationContext("single-bean.xml");

		when:
		def beanRetrievedByType = applicationContext.getBean(Repository.class);
		
		then:
		beanRetrievedByType != null
	}
	
	def "testKoan6CreateApplicationContextWithTwoQualifyingBeansRetrievedByType"() {
		
		setup:
		def applicationContext =
				new ClassPathXmlApplicationContext("two-bean.xml");

		when:
		Map<String, Repository> beansRetrievedByType = applicationContext.getBeansOfType(Repository.class)
		
		then:
		beansRetrievedByType != null
		beansRetrievedByType.size() == 2
		beansRetrievedByType.get("sampleBean1") != null
		beansRetrievedByType.get("sampleBean2") != null
	}

	def "testKoan7CreateApplicationContextWithOneBeanRetrievingByName"() {
		setup:
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("single-bean.xml");

		when:
		def beanRetrievedByName = (Repository)applicationContext.getBean("repository")
		
		then:
		beanRetrievedByName != null
	}
	
	public void testKoan75CreateApplicationContextWithOneBeanRetrievingByName() {
		setup:
		def applicationContext = new ClassPathXmlApplicationContext("single-bean-complete.xml");

		when:
		def beanRetrievedByName = applicationContext.getBean("sampleBean", Repository.class);
		
		then:
		beanRetrievedByName != null
	}

}

