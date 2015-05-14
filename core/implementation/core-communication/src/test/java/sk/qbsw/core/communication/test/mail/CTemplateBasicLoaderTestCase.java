package sk.qbsw.core.communication.test.mail;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import sk.qbsw.core.communication.mail.service.ITemplateLoader;

/**
 * The Class CTestTemplateBasicLoader.
 * 
 * @author Dalibor Rak
 * @author Tomas Lauro
 * 
 * @version 1.9.0
 * @since 1.6.0
 */
@RunWith (SpringJUnit4ClassRunner.class)
@ContextConfiguration (locations = {"classpath:/spring/test-context.xml"})
public class CTemplateBasicLoaderTestCase
{	
	/** The loader. */
	@Autowired
	private ITemplateLoader loader;

	/**
	 * Inits the.
	 */
	@Before
	public void init ()
	{
		loader.setBasePath("/email");
	}

	/**
	 * Test path construction.
	 */
	@Test
	public void testPathConstruction ()
	{
		String toCheck = loader.getFullFileName("test", "en");
		assertEquals("Generated path not compatible", "/email" + File.separator + "test_en.vm", toCheck);
	}

	/**
	 * Test get template.
	 */
	@Test
	public void testGetTemplate ()
	{
		InputStream toCheck = loader.getTemplate("test", "en");
		assertTrue("Template not loaded", toCheck != null);
	}

	/**
	 * Test get template direct.
	 */
	@Test
	public void testGetTemplateDirect ()
	{
		InputStream toCheck = loader.getTemplate(this.getClass(), "test1", "en");
		assertTrue("Template not loaded", toCheck == null);
	}

}
