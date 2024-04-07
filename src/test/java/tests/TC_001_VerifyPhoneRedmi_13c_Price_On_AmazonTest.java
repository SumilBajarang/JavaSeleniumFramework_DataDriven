package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import utilities.ReadExcelFile;
@Listeners(utilities.ExtentListenerClass.class)
public class TC_001_VerifyPhoneRedmi_13c_Price_On_AmazonTest extends BaseClass{

	@Test(dataProvider="homePageData")
	public void verifyRedmi13CPriceTest(String searchItem) throws IOException {
		HomePage homePage=new HomePage(driver);
		String selectedValue=homePage.getSelectedValueFromCategoryDropdown();
		verifyTwoStringsAreEqual(selectedValue,"All Categories");
		logger.info("Verified that default value is : All Categories");
		homePage.selectDataFromCategoryDropdown("Electronics");
		Assert.assertTrue(false);
		selectedValue=homePage.getSelectedValueFromCategoryDropdown();
		logger.info("Electronics selected from dropdown");
		verifyTwoStringsAreEqual(selectedValue,"Electronics");
		logger.info("Verified that selected value is : Electronics");
		homePage.searchValueInDropdown(searchItem);
		logger.info("Searching phone :"+searchItem);
		homePage.expandSection("Delivery Day");	  
		logger.info("Expanded Delevery Day section");
		homePage.clickOnCheckBox("Get It by Tomorrow","Delivery Day");
		scrollBy(driver);
		hardWait(3);
		logger.info("Scrolled down");	  
		homePage.expandSection("Price");
		logger.info("Expanded Price section");
		homePage.clickOnPriceRange("₹10,000 - ₹20,000");
		logger.info("Clicked on price within \"₹10,000 - ₹20,000\" range");
		scrollBy(driver);
		hardWait(3);
		logger.info("Scrolled down");		  
		homePage.expandSection("Memory Storage Capacity");
		logger.info("Expanded Memory Storage Capacity section");	  
		homePage.clickOnCheckBox("256 GB","Memory Storage Capacity");
		String actualPrice=homePage.returnPriceOfPhone("Startrail Silver, 8GB RAM, 256GB Storage", "14,499");
		verifyTwoStringsAreEqual(actualPrice,"13,499");
		logger.info("Verified that price of Startrail Silver, 8GB RAM, 256GB Storage is 13,499");
	}
	@DataProvider(name="homePageData")
	public String[][] homePagData() {
		String filePath=System.getProperty("user.dir")+"//TestData//testdata.xlsx";
		System.out.println(filePath);
		int rowCount=ReadExcelFile.getRowCount(filePath, "HomePage");
		System.out.println(rowCount);
		int ColCount=ReadExcelFile.getColCount(filePath, "HomePage");
		System.out.println(ColCount);
		System.out.println("hii");
		String[][] data=new String[rowCount-1][ColCount];//new String[1][1];
		for(int i=1;i<rowCount;i++) {
			System.out.println("first");
			for(int j=0;j<ColCount;j++) {
				System.out.println("2nd");
				data[i-1][j]=ReadExcelFile.getCellValue(filePath, "HomePage", i, j);
			}
		}
		System.out.println("ok");
		return data;
	}
}