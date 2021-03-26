package com.qa.runners;

import com.qa.utils.DriverManager;
import com.qa.utils.GlobalParams;
import com.qa.utils.ServerManager;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.*;

@CucumberOptions(plugin = {"pretty",
        "html:target/cucumber",
        "summary",
        "de.monochromata.cucumber.report.PrettyReports:target/cucumber-html-reports"},
        features = {"src/test/resources"},
        glue = {"com.qa.stepdef"},
        monochrome=true,
        tags = "@test")
public class MyTestNGRunnersTest {

    private static final ThreadLocal<TestNGCucumberRunner> testNGCucumberRunner=new ThreadLocal<>();

    public TestNGCucumberRunner getRunner(){
        return testNGCucumberRunner.get();
    }
    public void setTestNGCucumberRunner(TestNGCucumberRunner testNGCucumberRunner1){
        testNGCucumberRunner.set(testNGCucumberRunner1);
    }
    @Parameters({"platformName","deviceName","UDID",
            "systemPort","chromeDriverPort",
            "wdaLocalPort","webkitDebugProxyPort"})
    @BeforeClass(alwaysRun = true)
    public void setUpClass(String platformName,String deviceName,String UDID,
                           @Optional("androidOnly") String systemPort,
                           @Optional("androidOnly") String chromeDriverPort,
                           @Optional("iOSOnly") String wdaLocalPort,
                           @Optional("iOSOnly")String webkitDebugProxyPort) throws Exception {
        ThreadContext.put("ROUTINGKEY",platformName+"_"+deviceName);

        GlobalParams params=new GlobalParams();
        params.setPlatformName(platformName);
        params.setDeviceName(deviceName);
        params.setUDID(UDID);
        switch (platformName){
            case "Android":
                params.setSystemPort(systemPort);
                params.setChromeDriverPort(chromeDriverPort);
                break;
            case "iOS":
                params.setWdaLocalPort(wdaLocalPort);
                params.setWebkitDebugProxyPort(webkitDebugProxyPort);
                break;
        }
        new ServerManager().startServer();
        new DriverManager().initializeDriver();
        setTestNGCucumberRunner(new TestNGCucumberRunner(this.getClass()));
    }

    @SuppressWarnings("unused")
    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        // the 'featureWrapper' parameter solely exists to display the feature
        // file in a test report
        getRunner().runScenario(pickleWrapper.getPickle());
    }

    /**
     * Returns two dimensional array of {@link PickleWrapper}s with their
     * associated {@link FeatureWrapper}s.
     *
     * @return a two dimensional array of scenarios features.
     */
    @DataProvider
    public Object[][] scenarios() {
        return getRunner().provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        DriverManager driverManager=new DriverManager();
        if(driverManager.getDriver()!=null){
            driverManager.getDriver().quit();
            driverManager.setDriver(null);
        }
        ServerManager serverManager=new ServerManager();
        if(serverManager.getServer()!=null){
            serverManager.getServer().stop();
        }
        if(testNGCucumberRunner!=null) {
            getRunner().finish();
        }
    }
}
