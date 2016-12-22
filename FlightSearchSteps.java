package stepDefinition;

import cucumber.api.java.en.Then;
import runner.WebConnector;

public class FlightSearchSteps {
	
	WebConnector selenium = WebConnector.getInstance();

	@Then("^User choose the \"([^\"]*)\" from \"([^\"]*)\" calendar$")
	public void choose_date_from_calendar(String departureDate, String objectToBeSelected) throws Throwable {
		selenium.click(objectToBeSelected);
		selenium.selectDate(departureDate, objectToBeSelected+"_allMonths");
		Thread.sleep(2000);
	   
	}
	
	@Then("^User choose travel class \"([^\"]*)\" from \"([^\"]*)\"$")
	public void choose_travel_class(String travelClass, String travelClassTypePath) throws Throwable {
	    selenium.selectTravelClass(travelClass, travelClassTypePath);
	    
	}
}
