package sk.qbsw.core.persistence.test;

import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import sk.qbsw.core.persistence.test.dao.CDaoTestCase;
import sk.qbsw.core.persistence.test.dao.CHibernateCastingTestCase;
import sk.qbsw.core.testing.ISlowTestClass;

/**
 * TestSuite for all fast test cases
 * 
 * @version 1.12.0
 * @since 1.12.0
 * @author Dalibor Rak
 */
@RunWith(Suite.class)
@ExcludeCategory(ISlowTestClass.class)
@SuiteClasses({ CDaoTestCase.class, CHibernateCastingTestCase.class })
public class CAllFastTests {

}
