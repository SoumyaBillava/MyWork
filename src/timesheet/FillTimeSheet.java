package timesheet;

import java.io.IOException;

import jxl.read.biff.BiffException;

public class FillTimeSheet {
	public static void main(String args[]) throws BiffException, IOException, InterruptedException
	{
		ReUsableMethods object = new ReUsableMethods();
		object.openBrowser();
		//	object.openUrl("http://139.59.14.24/");
		object.openUrl("http://139.59.77.88/");

		String cred[][] = object.excelRead();

		for(int i=1;cred[0][i]!=null;i++)
		{
			object.log.info("Employee:"+i);
			String Username =cred[0][i];
			Username=Username.trim();
			object.loginUser(Username,cred[1][i]);

			object.ClickCancelTour();

			int correctcredential = object.enterTimeSheet();
			if(correctcredential==1)
				object.logout();
			else
				object.log.info("You have entered wrong credential");
		}
		object.closeBrowser();	
		System.out.println("closed browser");

	}
}
