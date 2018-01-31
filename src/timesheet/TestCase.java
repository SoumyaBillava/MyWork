package timesheet;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import jxl.read.biff.BiffException;

public class TestCase extends ReUsableMethods{

	@BeforeTest
	public void OpeningBroser(){
		openBrowser();
	}

	@Test(priority =0)
	public void OpenSentrifugoUrl(){
		openUrl("http://139.59.14.24/");
	}

	//	@Test(priority =1)
	public void Login1User()
	{
		loginUser("EMPP0012","bepuzesug");

	}
	//	@Test(priority =2)
	public void SelectTimeSheet() throws InterruptedException
	{
		enterTimeSheet();

	}
	//	@Test(priority =3)
	public void Readcredentail() throws InterruptedException, BiffException, IOException
	{
		String cred[][] = excelRead();
		//int size = cred[0].length;
		//System.out.println(size);
		//cred.length();
		for(int i=1;cred[0][i]!=null;i++)
		{
			System.out.println("Employee:"+i);
			System.out.println(cred[0][i]+" and "+cred[1][i]);
		}

	}
}
