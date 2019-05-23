package PadgeObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class gasMilladgeObjects {
	
	public gasMilladgeObjects(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(id="uscodreading")
	public WebElement CurrentOdometerReading;
	
	
	
	@FindBy(id="uspodreading")
	public WebElement PreviousOdometerReading;
	
	
	@FindBy(id="usgasputin")
	public WebElement GasAddedtotheTank;
	
	
	@FindBy(id="usgasprice")
	public WebElement usgasprice;
	
	@FindBy(xpath="//table[@id='standard']//td[1]//input[1]")
	public WebElement calculate;
	
	@FindBy(xpath="//b[contains(.,'miles per gallon')]")
	public WebElement result;
}
