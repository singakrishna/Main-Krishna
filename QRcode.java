package selenium_pac;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.time.Duration;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import Config.Driver;

public class QRcode extends Driver {
	@Test
	public void load() throws Exception {
		d.get("https://demo.cyclos.org/ui/home");
		d.findElement(By.id("login-link")).click();
		d.findElement(By.xpath("//input[@type='text']")).sendKeys("demo");
		d.findElement(By.xpath("//input[@type='password']")).sendKeys("1234");
		d.findElement(By.xpath("//button/span")).click();
		d.findElement(By.linkText("Receive QR-code payment")).click();
		FileInputStream fis=new FileInputStream("D:\\Selenium-krishna root\\NetBanking\\TestData\\QRData.xlsx");
		XSSFWorkbook wb=new XSSFWorkbook(fis);
		XSSFSheet s=wb.getSheetAt(0);
		for(int i=1;i<=s.getLastRowNum();i++) {
	
			
			d.findElement(By.xpath("//input")).clear();
			d.findElement(By.xpath("//input")).sendKeys(s.getRow(i).getCell(0).getStringCellValue());
			String amount=d.findElement(By.xpath("//input")).getAttribute("value");
			d.findElement(By.cssSelector(".btn > span")).click();
			
			
			if(amount.equals("")) {
				
				assertEquals(d.findElement(By.cssSelector(".invalid-feedback")).getText(),"This field is required");
			}
			else if(iselementPresent(d,By.xpath("//page-content/div/div/div"))) {
				Thread.sleep(2000);
			
				d.findElement(By.xpath("//action-button[@class='d-inline-block button']//button[@type='button']")).click();
			}
			
			else {
				
				assertEquals(d.findElement(By.cssSelector(".notification-message")).getText(),"Amount must be a positive number");
				Thread.sleep(5000);
				d.findElement(By.cssSelector(".visually-hidden:nth-child(1)")).click();
				Thread.sleep(3000);
			}
			Thread.sleep(5000);
			
		}
		Thread.sleep(5000);
	}
	

	public boolean iselementPresent(WebDriver Driver, By Locator) {
		d.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
		try
		{
			Driver.findElement(Locator);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public void pagee() {
		
		for(int i=1;i<=3;i++) {
			log.login12(excel.getCellData(4, 0), excel.getCellData(4, 1));
			
			log.QRPay.click();
			log.amount.clear();
			log.amount.sendKeys(excel1.getCellData(i, 0));
			String money=log.amount.getAttribute("value");
			log.next.click();
			if(money.equals("")) {
				
				assertEquals(log.error.getText(),"This field is required");
				excel1.SetCellValue(2, 1,"invalid","D:\\\\Selenium-krishna root\\\\NetBanking\\\\TestData\\\\QRData.xlsx");
			}
			else if(iselementPresent(d,By.xpath("//action-button[2]/button/span"))){
				log.previous.click();
				excel1.SetCellValue(1, 1,"valid","D:\\\\Selenium-krishna root\\\\NetBanking\\\\TestData\\\\QRData.xlsx");
			}
			else
			{
				assertEquals(log.notif.getText(),"Amount must be a positive number");
				log.cancel.click();
				excel1.SetCellValue(3, 1,"invalid","D:\\\\Selenium-krishna root\\\\NetBanking\\\\TestData\\\\QRData.xlsx");
			}
			Thread.sleep(5000);
		}
	}

}


