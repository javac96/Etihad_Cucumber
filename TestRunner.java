package runner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.relevantcodes.extentreports.NetworkMode;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
				  features = "features",
				  glue = {"stepDefinition"},
				  dryRun = false,
				  monochrome = true,
				  plugin = {"html:target/Cucumber_Reports",
						    "pretty",
						    "json:target/cucumber.json",
						    "junit:target/cucumber.xml",
						    "runner.ExtentCucumberFormatter"
				  },
				  tags={"@flight_search"}
				 )
public class TestRunner {

	@BeforeClass
    public static void setup() {
		long timeInMillis = System.currentTimeMillis();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(timeInMillis);
		SimpleDateFormat dateFromat1 = new SimpleDateFormat("MM_dd_yyyy_hh_mm_ss_a");
		
		//String path = System.getProperty("user.dir")+"/Extent Reports/report.html";
		File f = new File(System.getProperty("user.dir")+"/output/Run_"+dateFromat1.format(cal1.getTime()));
		f.mkdir();
		
		WebConnector selenium = WebConnector.getInstance();
		selenium.currentReportFolder = f.getName();
		String path = f+"/report.html";
	    File CP_file = new File(path);
        // Initiates the extent report and generates the output in the output/Run_<unique timestamp>/report.html file by default.
		//ExtentCucumberFormatter.initiateExtentCucumberFormatter();
		ExtentCucumberFormatter.initiateExtentCucumberFormatter(CP_file, NetworkMode.OFFLINE);
		// Loads the extent config xml to customize on the report.
        ExtentCucumberFormatter.loadConfig(new File("src/resources/extent-config.xml"));

        // User can add the system information as follows
        //ExtentCucumberFormatter.addSystemInfo("Browser Name", "Firefox");
        ExtentCucumberFormatter.addSystemInfo("Browser Name", "Chrome");
        //ExtentCucumberFormatter.addSystemInfo("Browser version", "v44.0");
        ExtentCucumberFormatter.addSystemInfo("Browser version", "v54.0");
        ExtentCucumberFormatter.addSystemInfo("Selenium version", "v2.53.0");

        
        /*// Also you can add system information using a hash map
        Map systemInfo = new HashMap();
        systemInfo.put("Cucumber version", "v1.2.3");
        systemInfo.put("Extent Cucumber Reporter version", "v1.1.0");
        ExtentCucumberFormatter.addSystemInfo(systemInfo);*/
    }

	/*
	@AfterClass
	public static void Close_the_browser(){
	
		WebConnector selenium = WebConnector.getInstance();
		selenium.closeBrowser();
		
	}
	*/
	
	
}
