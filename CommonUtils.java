package stepDefinition;


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import runner.WebConnector;

public class CommonUtils {
	
	WebConnector selenium = WebConnector.getInstance();
	
	@Given("^Open \"([^\"]*)\" and go to \"([^\"]*)\"$")
	public void openBrowserAndGoToURL(String browserName, String url) throws Throwable {
		selenium.openBrowser(browserName);
		selenium.navigate(url); 
	}
	
	@Then("^User click on \"([^\"]*)\"$")
	public void user_click_on(String objectToBeClicked) throws Throwable {
	   selenium.click(objectToBeClicked);
	   
	}
	
	@Then("^User select \"([^\"]*)\" from \"([^\"]*)\" dropdown box$")
	public void select_from_dropdown(String airportName, String objectToBeSelected) throws Throwable {
	    selenium.click(objectToBeSelected);
		selenium.selectLocation(airportName, objectToBeSelected+"_alloptions");
	    Thread.sleep(1000);
	}
	
	
}
