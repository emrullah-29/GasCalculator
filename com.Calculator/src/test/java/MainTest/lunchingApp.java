package MainTest;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import PadgeObjects.gasMilladgeObjects;
import io.github.bonigarcia.wdm.WebDriverManager;

public class lunchingApp {
	
	WebDriver driver;
	Xls_Reader xl = new Xls_Reader("/Users/abrahamben/Desktop/Calculator/com.Calculator/src/test/resources/testdata.xlsx");
	
	
	@BeforeTest
	
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.navigate().to("https://www.calculator.net/gas-mileage-calculator.html");
	}
	
	@Test
	public void CalculateTest() {
		
		gasMilladgeObjects calculate = new gasMilladgeObjects(driver);
		
//		double currentOr=3356; 		// String.value of convert string to number 
//		double previousOr=2245;
//		double gas = 30;
		
		int rowCount = xl.getColumnCount("Data");
		
		for(int i=2;i<=rowCount;i++) {
			
			String run = xl.getCellData("Data", "Execute", i);
			if(!run.equalsIgnoreCase("Y")) {
				xl.setCellData("Data", "Status", i, "Skip Requested");
				continue;
			}
			
			
			String currentOr = xl.getCellData("Data", "CurrentOR", i);
			String previousOr = xl.getCellData("Data", "PreviousOR", i);
			String gas = xl.getCellData("Data", "Gas", i);
		
			
	
		calculate.CurrentOdometerReading.clear();
		calculate.CurrentOdometerReading.sendKeys(currentOr);
		calculate.PreviousOdometerReading.clear();
		calculate.PreviousOdometerReading.sendKeys(previousOr);
		calculate.GasAddedtotheTank.clear();
		calculate.GasAddedtotheTank.sendKeys(gas);
		calculate.calculate.click();
		
		String[] actualResult = calculate.result.getText().split(" ");
		xl.setCellData("Data", "Actual", i, actualResult[0]);
		System.out.println(actualResult[0]);
		
		double co = Double.parseDouble(currentOr);
		double po = Double.parseDouble(previousOr);
		double gs = Double.parseDouble(gas);
		
		double expectedResult = (co-po)/gs;
		DecimalFormat df =new DecimalFormat("0.00");
		String expectedResult2 = String.valueOf(df.format(expectedResult));
		xl.setCellData("Data", "Expected", i, expectedResult2);
		
		if(actualResult[0].equals(expectedResult2)) {
			xl.setCellData("Data", "Status", i, "PASS");
			
		}else {
			xl.setCellData("Data", "Status", i, "FAIL");
		    }
		
		xl.setCellData("Data", "Time", i, LocalDateTime.now().toString()); 
		
		}
		
		
		
		
		
	}
	
	
	
	
	

	@AfterTest
	public void closeUp() {
		driver.close();
		driver.quit();
	}
}
