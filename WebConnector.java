package runner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebConnector {

	WebDriver driver = null;
	Properties OR = null;
	static WebConnector w;
	public String currentReportFolder;
	public List<WebElement> scroll_ele= null;

	//constructor for Singleton 
	private WebConnector(){
		if(OR==null){
			try {
				OR = new Properties();
				FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\resources\\OR.properties");
				OR.load(fis);
			} catch (Exception e) {

				System.out.println("Error on initializing properties file.");
			}

		}
	}

	public void openBrowser(String browserName){
		if(browserName.equalsIgnoreCase("Mozilla")){
			driver = new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("Chrome")){
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium Driver\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("Edge")){
			System.setProperty("webdriver,edge,driver", "C:\\Selenium Driver\\MicrosoftWebdriver.exe");
			driver = new EdgeDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	public void navigate(String url){
		driver.get(OR.getProperty(url));
	}

	//Singleton
	public static WebConnector getInstance() {
		if(w==null){
			w=new WebConnector();
		}
		return w;
	}

	public void click( String objectName){
		driver.findElement(By.xpath(OR.getProperty(objectName))).click();
	}

	public boolean isElementDisplayed(String elementToBeDispaleyd){
		return driver.findElement(By.xpath(OR.getProperty(elementToBeDispaleyd))).isDisplayed();
	}

	public void selectLocation(String airport, String locationPath){

		WebDriverWait wait = new WebDriverWait(driver, 30);
		List<WebElement> airports = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy( By.xpath(OR.getProperty(locationPath))));
		for(WebElement el : airports){
			if(el.getText().equalsIgnoreCase(airport)){
				el.click();
				break;
			}
		}

	}//method selectLocation end


	public void selectDate(String travelDate, String objectDateCalendar) throws Exception{
		String months[] = {"January", "February", "March", "April", "May", "June",
				"July", "August", "September", "October", "November", "December"};
		DateFormat df = new SimpleDateFormat("MM/dd/yy");
		Date dt = df.parse(travelDate);

		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		String month_year = months[month]+" "+year;

		WebElement month_yr = driver.findElement(By.xpath(OR.getProperty(objectDateCalendar)));
		if(!month_yr.getText().trim().equalsIgnoreCase(month_year)){
			while(!month_yr.getText().trim().equalsIgnoreCase(month_year)){
				click(objectDateCalendar+"_next");//continue clicking on the next button
				month_yr = driver.findElement(By.xpath(OR.getProperty(objectDateCalendar)));//update
			}
		}
		List<WebElement> dates = driver.findElements(By.xpath(OR.getProperty(objectDateCalendar+"_allDates")));
		for(WebElement date : dates){
			if(Integer.parseInt(date.getText()) == day){
				date.click();
				break;
			}
		}
	}//method close

	public void selectTravelClass(String classType, String buttonPathObject){

		List<WebElement> button = driver.findElements(By.xpath(OR.getProperty(buttonPathObject)));
		System.out.println(button.size());

		for(WebElement radio: button){
			if(radio.getAttribute("value").equalsIgnoreCase(classType)){

				if(!radio.isSelected())
					radio.click();
				break;
			}
		}
	}//method selectTravelClass end

	//Close browser
	public void closeBrowser(){

		driver.close();
	}
}
