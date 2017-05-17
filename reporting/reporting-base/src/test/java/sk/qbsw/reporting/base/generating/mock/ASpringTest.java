package sk.qbsw.reporting.base.generating.mock;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-reporting.xml"})
public abstract class ASpringTest
{

	@Autowired
	protected ApplicationContext appContext;
	
	@Autowired
	protected  AutowireCapableBeanFactory beanFactory;
}
