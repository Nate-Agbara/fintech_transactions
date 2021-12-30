package com.fintech.transaction;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * @author: Nathan
 */
@Suite
@SelectClasses({DataStoreSystemsHealthTest.class, TransactionApplicationTests.class})
public class ContinousIntegrationTestSuite {

    //Intentionally empty - Test suite annotations set up is enough
}
