package timesheet;
import java.io.IOException;

import jxl.read.biff.BiffException;

public class RaisingRequest {
	public static void main(String args[]) throws BiffException, IOException {
		ReUsableMethods object = new ReUsableMethods();
		object.openBrowser();

		//	object.openUrl("http://139.59.14.24/");
		object.openUrl("http://139.59.77.88/");

		String cred[][] = object.excelRead();

		for(int i=1;cred[0][i]!=null;i++)
		{
			object.log.info("Employee:"+i);
			String Username =cred[0][i];
			//Username=Username.trim();
			object.loginUser(Username,cred[1][i]);

			object.ClickCancelTour();
			object.RaiseRequest(5000);
			object.logout();

		}
	}
}
