package timesheet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Reusable2 {
	
	public WebDriver driver;
	
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
