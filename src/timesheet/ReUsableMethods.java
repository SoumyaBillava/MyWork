package timesheet;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ReUsableMethods {

	public WebDriver driver;

	//to log in a file
	public Logger log = Logger.getLogger("devpinoyLogger");

	public void openBrowser() {
		System.setProperty("webdriver.firefox.marionette","C:\\Users\\soumya.billava\\Downloads\\geckodriver-v0.18.0-win64\\geckodriver.exe");
		driver=new FirefoxDriver();
		log.info("User opened the Firefox browser");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void openUrl(String url)
	{
		driver.get(url);
		log.info("User entered the URL:"+url);
	}

	public void loginUser(String Username, String Password)
	{
		driver.findElement(By.cssSelector("#username")).sendKeys(Username);
		driver.findElement(By.cssSelector("#password")).sendKeys(Password);
		driver.findElement(By.cssSelector("#loginsubmit")).click();
		log.info("logged into sentrifugo using the username:"+Username+" and password:"+Password);
	}

	public int enterTimeSheet() throws InterruptedException
	{

		//check for wrong credential
		try {
			driver.findElement(By.cssSelector("#usernameerror"));
			return 0;
		}catch(Exception ex) {
		}

		driver.findElement(By.xpath("html/body/div[1]/div/div[1]/div[2]/div[2]/div/ul/li[10]/a/b")).click();
		driver.findElement(By.cssSelector("#weekli>a")).click();

		//open the calendar
		driver.findElement(By.cssSelector("#start_date")).click();

		for(int k=1;k<=4;k++)
		{
			for(int l=1;l<=3;l++)
			{
				WebElement Mn = driver.findElement(By.xpath("html/body/div[3]/table/tbody/tr["+k+"]/td["+l+"]"));
				System.out.println("All the months:"+Mn.getText());
				if(!(Mn.getAttribute("class").equals("ui-state-default mtz-monthpicker mtz-monthpicker-month ui-state-disabled")))
				{
					System.out.println("Entering time to the month:"+Mn.getText());
					Mn.click();

					//move mouseover weeks
					Actions action = new Actions(driver);
					WebElement mouseoverchannel = driver.findElement(By.cssSelector("#show_hide>li"));
					action.moveToElement(mouseoverchannel).build().perform();
					List<WebElement> forms = driver.findElements(By.cssSelector(".scroll_change.week_select_list>li"));
					int week_Count=1;

					if(forms.size()>1) {
						week_Count = forms.size(); 

						for(int i=1;i<=week_Count;i++)
						{
							Thread.sleep(1000);
							boolean change = false;
							//select each week
							driver.findElement(By.xpath("html/body/div[1]/div[1]/div[1]/div[2]/div[2]/div/div[2]/div[1]/div/div[4]/div[5]/div/div/div/div[2]/ul[2]/li["+i+"]")).click();


							//in every week
							List <WebElement> Timesheet_row = driver.findElements(By.xpath("html/body/div[1]/div[1]/div[1]/div[2]/div[2]/div/div[2]/div[1]/div/div[4]/div[5]/div/div/table/tbody[3]/tr[1]/td"));
							int timeelement_size = Timesheet_row.size();
							//System.out.println("size is:"+timeelement_size);

							//in every day
							for(int j=2;j<timeelement_size;j++)
							{
								//System.out.println("inside day. and j is:"+j);
								WebElement eachelement = driver.findElement(By.xpath("html/body/div[1]/div[1]/div[1]/div[2]/div[2]/div/div[2]/div[1]/div/div[4]/div[5]/div/div/table/tbody[3]/tr[1]/td["+j+"]/input"));
								//System.out.println(eachelement.getAttribute("disabled"));

								//select the elements which are active
								if(eachelement.getAttribute("disabled")==null)
								{
									log.info("Entering time to "+driver.findElement(By.xpath("html/body/div[1]/div[1]/div[1]/div[2]/div[2]/div/div[2]/div[1]/div/div[4]/div[5]/div/div/table/tbody[1]/tr/th["+j+"]")).getText());

									//if no time is entered
									if(eachelement.getText().isEmpty())
									{
										eachelement.sendKeys("09:00");
										change = true;
									}
								}
							}
							Thread.sleep(500);
							//save time sheet
							if(change==true)
							{
								JavascriptExecutor jse = (JavascriptExecutor)driver;
								jse.executeScript("window.scrollBy(0,250)", "");
								driver.findElement(By.cssSelector(".save_but.sav_sub")).click();
								Thread.sleep(200);
								driver.findElement(By.cssSelector("#popup_ok")).click();

								Thread.sleep(1000);
							}

							//go to mouseover element again if weeks are still left
							if(i<week_Count)
							{
								Actions action1 = new Actions(driver);
								WebElement mouseoverchannel1 = driver.findElement(By.cssSelector("#show_hide>li"));
								action1.moveToElement(mouseoverchannel1).build().perform();
							}

						}
					}
					else {

						boolean change = false;
						//in every week
						List <WebElement> Timesheet_row = driver.findElements(By.xpath("html/body/div[1]/div[1]/div[1]/div[2]/div[2]/div/div[2]/div[1]/div/div[4]/div[5]/div/div/table/tbody[3]/tr[1]/td"));
						int timeelement_size = Timesheet_row.size();
						//System.out.println("size is:"+timeelement_size);

						//in every day
						for(int j=2;j<timeelement_size;j++)
						{
							//System.out.println("inside day. and j is:"+j);
							WebElement eachelement = driver.findElement(By.xpath("html/body/div[1]/div[1]/div[1]/div[2]/div[2]/div/div[2]/div[1]/div/div[4]/div[5]/div/div/table/tbody[3]/tr[1]/td["+j+"]/input"));
							//System.out.println(eachelement.getAttribute("disabled"));

							//select the elements which are active
							if(eachelement.getAttribute("disabled")==null)
							{
								log.info("Entering time to "+driver.findElement(By.xpath("html/body/div[1]/div[1]/div[1]/div[2]/div[2]/div/div[2]/div[1]/div/div[4]/div[5]/div/div/table/tbody[1]/tr/th["+j+"]")).getText());

								//if no time is entered
								if(eachelement.getText().isEmpty())
								{
									eachelement.sendKeys("09:00");
									change = true;
								}
							}
						}
						Thread.sleep(500);
						//save time sheet
						if(change==true)
						{
							JavascriptExecutor jse = (JavascriptExecutor)driver;
							jse.executeScript("window.scrollBy(0,250)", "");
							driver.findElement(By.cssSelector(".save_but.sav_sub")).click();
							Thread.sleep(200);
							driver.findElement(By.cssSelector("#popup_ok")).click();

							Thread.sleep(1000);
						}
					}

					//end of the loop, open the calendar to select next month
					driver.findElement(By.cssSelector("#start_date")).click();
				}
			}
		}

		return 1;	
	}

	public String[][] excelRead() throws BiffException, IOException
	{
		String[][] credential = new String[1000][1000];
		final String path = "C:\\Users\\soumya.billava\\Desktop\\Automation\\credential\\UserIDs_88.xls";
		//	final String path = "C:\\Users\\soumya.billava\\Desktop\\Automation\\sentrifugo_total.xls";
		FileInputStream fs = new FileInputStream(path);
		Workbook wb = Workbook.getWorkbook(fs);
		Sheet sheet = wb.getSheet(0);

		log.info("Total number of employees:"+ (sheet.getRows()-1));

		for(int i=1;i<sheet.getRows();i++)
		{
			Cell cell1 = sheet.getCell(0, i);
			Cell cell2 = sheet.getCell(1, i); 
			credential[0][i]=cell1.getContents();
			credential[1][i]=cell2.getContents();
		}
		wb.close();
		return credential;
	}

	public void logout()
	{
		driver.findElement(By.cssSelector("#logoutbutton")).click();
		driver.findElement(By.cssSelector("#logoutid>a:nth-child(5)")).click();
		log.info("Logged out of the application for the current user");
	}
	public void closeBrowser()
	{
		driver.quit();
		log.info("closed all the browsers");
	}
	public void ClickCancelTour()
	{
		try {
			driver.findElement(By.cssSelector("#canceltour")).click();
			log.info("cancelled tour");
		}catch(Exception ex) {

		}

	}

	public void RaiseRequest(int n)
	{
		//check for wrong credential
		/*	try {
			driver.findElement(By.cssSelector("#usernameerror"));
			return 0;
		}catch(Exception ex) {
		}
		 */

		//Click Service Request
		driver.findElement(By.xpath("html/body/div[1]/div/div[1]/div[2]/div[2]/div/ul/li[3]/a/b")).click();

		for(int i=0;i<n;i++)
		{

			//Click on Raise request
			driver.findElement(By.cssSelector(".add-btn-div")).click();

			//open the dropdownbox and fill the details
			driver.findElement(By.xpath("html/body/div[1]/div/div/div[2]/div[3]/div[2]/div[2]/div[2]/div/div/div/div[1]/div[2]/form/div/div[1]/div/div/a/div/b")).click();
			driver.findElement(By.xpath("html/body/div[3]/ul/li[1]")).click();

			driver.findElement(By.xpath("html/body/div[1]/div/div/div[2]/div[3]/div[2]/div[2]/div[2]/div/div/div/div[1]/div[2]/form/div/div[3]/div/div/a/div/b")).click();
			driver.findElement(By.xpath("html/body/div[4]/ul/li[4]")).click();

			driver.findElement(By.xpath("html/body/div[1]/div/div/div[2]/div[3]/div[2]/div[2]/div[2]/div/div/div/div[1]/div[2]/form/div/div[4]/div/div/a/div/b")).click();
			driver.findElement(By.xpath("html/body/div[5]/ul/li[4]")).click();

			driver.findElement(By.xpath("html/body/div[1]/div/div/div[2]/div[3]/div[2]/div[2]/div[2]/div/div/div/div[1]/div[2]/form/div/div[5]/div/div/a/div/b")).click();
			driver.findElement(By.xpath("html/body/div[6]/ul/li[2]")).click();

			driver.findElement(By.xpath("html/body/div[1]/div/div/div[2]/div[3]/div[2]/div[2]/div[2]/div/div/div/div[1]/div[2]/form/div/div[6]/div/textarea")).sendKeys("Upgrading to Firefox new Version");

			driver.findElement(By.cssSelector("#submitbutton")).click();

			log.info(" Raising Request is Done "+i+" Time.");

		}
	}
	
	public void raiseExpense(int n)
	{
		driver.findElement(By.xpath("html/body/div/div/div/div[2]/div[2]/div/ul/li[8]")).click();
		driver.findElement(By.cssSelector(".sprite.addrecord")).click();
		
		//fill details
		driver.findElement(By.cssSelector("#expense_name")).sendKeys("Stationary");
		
		driver.findElement(By.xpath("html/body/div[1]/div/div/div[2]/div[3]/div[2]/div[2]/div[2]/div/div/div/div[1]/div[2]/form/div[1]/div[2]/div/div/a/div/b")).click();
		driver.findElement(By.xpath("html/body/div[5]/ul/li[5]/div")).click();
		
		driver.findElement(By.xpath("html/body/div[1]/div/div/div[2]/div[3]/div[2]/div[2]/div[2]/div/div/div/div[1]/div[2]/form/div[1]/div[3]/div/div/a/div/b")).click();
		driver.findElement(By.xpath("html/body/div[6]/ul/li[2]/div")).click();
		
		driver.findElement(By.xpath("html/body/div[1]/div/div/div[2]/div[3]/div[2]/div[2]/div[2]/div/div/div/div[1]/div[2]/form/div[1]/div[4]/div/img")).click();
		driver.findElement(By.xpath("html/body/div[3]/table/tbody/tr[1]/td[4]")).click();
		
		driver.findElement(By.cssSelector("#expense_amount")).sendKeys("10000");
		
	}


}
