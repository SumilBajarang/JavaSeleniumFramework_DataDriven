package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import tests.BaseClass;

public class HomePage extends BaseClass {

	WebDriver driver;

	//2. Create constructor
	public HomePage(WebDriver rdriver)
	{
		driver = rdriver;
		PageFactory.initElements(rdriver, this);//driver that will be used to lookup the web element

	}

	@FindBy(xpath="//select[@id='searchDropdownBox']")
	WebElement dropDown;
	
	@FindBy(xpath="//div[@id='nav-search-dropdown-card']")
	WebElement dropDownBtn;
	
	@FindBy(id="twotabsearchtextbox")
	WebElement searchBox;
	
	@FindBy(xpath="//span[@class='a-price']/span")
	WebElement price;
	
	public String getSelectedValueFromCategoryDropdown() {
		Select select=new Select(dropDown);
		String defaultOption=select.getFirstSelectedOption().getText();
		return defaultOption;
	}
	public void selectDataFromCategoryDropdown(String value) {
		Select select=new Select(dropDown);
		clickUsingAction(dropDownBtn,driver);
		String dropDownXpath="//select/option[text()='"+value+"']";
		String valueOfAttr=driver.findElement(By.xpath(dropDownXpath)).getAttribute("value");
		select.selectByValue(valueOfAttr);
	}	
	public void searchValueInDropdown(String text) {
		searchBox.sendKeys(text);
		searchBox.sendKeys(Keys.ENTER);
		hardWait(5);
	}
	public void clickOnCheckBox(String chkBox,String label) {
		String allCheckBoxTextLocator="//span[text()='"+label+"']/ancestor::div[contains(@data-a-expander-name,'collapsible-left-nav-expander')]//div[contains(@class,'checkbox-fancy')]/following-sibling::span";
		List<WebElement> elements=driver.findElements(By.xpath(allCheckBoxTextLocator));
		List<String> allCheckBox=new ArrayList<String>();
		for(WebElement element:elements) {
			allCheckBox.add(element.getText());
		}
		if(allCheckBox.contains(chkBox)) {
			String chkBoxLocator="//span[text()='"+chkBox+"']/ancestor::a//input[@type='checkbox']";	    
		    WebElement chkBoxEle=driver.findElement(By.xpath(chkBoxLocator));
			clickUsingAction(chkBoxEle,driver);
			logger.info(chkBox+ " is Checked");
		    hardWait(5);
		}
		else {
			logger.info(chkBox+ " is not displayed");
		}
		
	}
	public void expandSection(String text) {
		String labelLocator="//span[text()='"+text+"']/ancestor::div[contains(@data-a-expander-name,'collapsible-left-nav-expander')]//a[@role='button']";
		try {
			WebElement ele=driver.findElement(By.xpath(labelLocator));
			if(ele.getAttribute("aria-expanded").equals("false")) {
				clickUsingAction(ele,driver);
			}
		}
		catch(Exception ex) {
			
		}				
		hardWait(2);
	}
	public void clickOnPriceRange(String text) {
		String allPriceDisplayed="//span[text()='Price']/ancestor::div[contains(@data-a-expander-name,'collapsible-left-nav-expander')]//a/span";
		List<WebElement> elements=driver.findElements(By.xpath(allPriceDisplayed));
		List<String> allPriceText=new ArrayList<String>();
		for(WebElement element:elements) {
			allPriceText.add(element.getText());
		}
		if(allPriceText.contains(text)) {
			String linkLocator="//span[contains(text(),'"+text+"')]/parent::a";
			WebElement ele=driver.findElement(By.xpath(linkLocator));
			clickUsingAction(ele,driver);
			hardWait(5);		
			}
		else {
			System.out.println(text+ " is not displayed");
		}
	    }
	public String returnPriceOfPhone(String mobileName,String price) {
		String phonePrice="//span[contains(text(),'"+mobileName+"')]/ancestor::div[contains(@class,'a-spacing-top-small')]//span[@class='a-price']//span[@class='a-price-whole']";
	    String priceOfPhone=driver.findElement(By.xpath(phonePrice)).getText();
	    System.out.println(priceOfPhone);
	    hardWait(2);
	    return priceOfPhone;
	}
}
