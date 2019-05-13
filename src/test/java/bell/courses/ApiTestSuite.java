package bell.courses;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ApiCatalogueTests.class,
        ApiOfficeTests.class,
        ApiOrganizationTests.class,
        ApiUserTests.class
})
public class ApiTestSuite {
}