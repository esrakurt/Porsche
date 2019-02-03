package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.BaseClass;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class purchasePorsche extends BaseClass {

    @Test
    public void porsche() throws InterruptedException {


//      1. Open browser
//      2. Go to url “https://www.porsche.com/usa/modelstart/”
        openWebsite();

//      3. Select model 718
        driver.findElement(By.xpath("//a[@class='b-teaser-link']/div[1]")).click();

//      4. Remember the price of 718 Cayman Model S
        String model718Price = driver.findElement(By.xpath("//div[@id='m982130']//div//div//div[2]")).getText();
        String priceOfModel718 =model718Price.substring(6,13).replace(",","").trim();
        int priceOfModel718S = Integer.valueOf(priceOfModel718);
        System.out.println("718 Cayman Model S price is: " + priceOfModel718S);

//      5. Click on 718 Cayman S
        driver.findElement(By.xpath("//div[@id='m982130']")).click();

//      6. Verify that Base price displayed on the page is same as the price from step 4
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String winHandleBefore = driver.getWindowHandle();

        // Switch to new window opened
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        int basePrice = getPrice(driver.findElement(By.xpath("//*[@id='s_price']/div[1]/div[1]/div[2]")).getText());
        System.out.println("Price on home page: "+priceOfModel718S+" | Base price: "+basePrice);
        Assert.assertEquals(priceOfModel718S, basePrice);

//      7. Verify that Price for Equipment is 0
        int priceForEquipment = getPrice(driver.findElement(By.xpath("//section[@id='s_price']/div[1]/div[2]/div[2]")).getText());
        System.out.println("Price for equipment is: " + priceForEquipment);
        Assert.assertEquals(0, priceForEquipment);

//      8. Verify that total price is the sum of base price + Delivery, Processing and Handling Fee
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        int priceForDeliveryAndSo = getPrice(driver.findElement(By.xpath("//section[@id='s_price']/div[1]/div[3]/div[2]")).getText());
        System.out.println("Price for Delivery, Processing and Handling Fee is: " + priceForDeliveryAndSo);

        int totalPrice = getPrice(driver.findElement(By.xpath("//section[@id='s_price']/div[1]/div[4]/div[2]")).getText());
        System.out.println("Total price: " + totalPrice);

        int totalOfBaseAndDeliveryAndSo = basePrice + priceForDeliveryAndSo;
        System.out.println("Total price of base and delivery and other fees: " + totalOfBaseAndDeliveryAndSo);

        Assert.assertEquals(totalOfBaseAndDeliveryAndSo, totalPrice);

//      9. Select color “Miami Blue”
        driver.findElement(By.xpath("//li[@id='s_exterieur_x_FJ5']")).click();

//      10.Verify that Price for Equipment is Equal to Miami Blue price
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        int priceForEquipmentAfterMiami = getPrice(driver.findElement(By.xpath("//section[@id='s_price']/div[1]/div[2]/div[2]")).getText());
        int miamiBluePrice = getPrice(driver.findElement(By.id("s_exterieur_x_FJ5")).getAttribute("data-price"));
        System.out.println("Price for Equipment: " +priceForEquipmentAfterMiami + " | " + "Miami Blue price: " + miamiBluePrice);

        Assert.assertEquals(miamiBluePrice, priceForEquipmentAfterMiami);

//      11.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        int totalPriceMiamiBlue = getPrice(driver.findElement(By.xpath("//section[@id='s_price']/div[1]/div[4]/div[2]")).getText());
        int expectedMiamiBlueTotal = basePrice + miamiBluePrice + priceForDeliveryAndSo;
        System.out.println("Actual Total price with Miami Blue: "+ totalPriceMiamiBlue + " | Expected price with Miami Blue: " + expectedMiamiBlueTotal);

        Assert.assertEquals(totalPriceMiamiBlue, expectedMiamiBlueTotal);

//      12.Select 20" Carrera Sport Wheels

        JavascriptExecutor je=(JavascriptExecutor)driver;
        ((JavascriptExecutor)driver).executeScript("scroll(0,50)");
        driver.findElement(By.xpath("//li[@id='s_exterieur_x_MXRD']//span[@style='max-width: 5.9375rem;']")).click();

//      13.Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        int sportWheelPrice = getPrice(driver.findElement(By.xpath("(//div[@class='tt_price tt_cell'])[2]")).getText());
        int priceForEquipmentMiamiAndWheels =  miamiBluePrice + sportWheelPrice;
        int actualPriceForEquipmentMiamiAndWheels = getPrice(driver.findElement(By.xpath("//section[@id='s_price']/div[1]/div[2]/div[2]")).getText());

        System.out.println("Actual Equipment price of miami and wheels: "+ actualPriceForEquipmentMiamiAndWheels + " | Expected Equipment price of Miami and Wheels: " + priceForEquipmentMiamiAndWheels);

        Assert.assertEquals(actualPriceForEquipmentMiamiAndWheels, priceForEquipmentMiamiAndWheels);

//      14.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        int expectedTotalWithWheels = priceOfModel718S + priceForDeliveryAndSo + sportWheelPrice +miamiBluePrice;
        int actualTotalWithWheels = getPrice(driver.findElement(By.xpath("//section[@id='s_price']/div[1]/div[4]/div[2]")).getText());
        System.out.println("Actual Total price of car with miami, wheels: "+ actualTotalWithWheels + " | Expected Total price of car Miami, wheels: " + expectedTotalWithWheels);

        Assert.assertEquals(actualTotalWithWheels, expectedTotalWithWheels);

//      15.Select seats ‘Power Sport Seats (14-way) with Memory Package’

        action.sendKeys(Keys.PAGE_DOWN).perform();
        Thread.sleep(1000);
        action.sendKeys(Keys.PAGE_DOWN).perform();
        Thread.sleep(1000);
//
        driver.findElement(By.id("s_interieur_x_PP06")).click();

//      16.Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels
//            + Power Sport Seats (14-way) with Memory Package
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        int powerSportsSeat = getPrice(driver.findElement(By.xpath("(//div[@id='seats_73']//div[@class='pBox'])[2]")).getText());
        int priceForEquipmentMiamiWheelsPowerSeat =  miamiBluePrice + sportWheelPrice +powerSportsSeat;
        int actualPriceForEquipmentMiamiWheelsPowerSeat = getPrice(driver.findElement(By.xpath("//section[@id='s_price']/div[1]/div[2]/div[2]")).getText());

        System.out.println("Actual Equipment price of miami, wheels and seat: "+ actualPriceForEquipmentMiamiWheelsPowerSeat + " | Expected Equipment price of Miami, Wheels and Seat: " + priceForEquipmentMiamiWheelsPowerSeat);

        Assert.assertEquals(actualPriceForEquipmentMiamiAndWheels, priceForEquipmentMiamiAndWheels);


//      17.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing
//    and Handling Fee

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        int expectedTotalWithMiamiWheelsSeat = priceOfModel718S + priceForDeliveryAndSo + sportWheelPrice +miamiBluePrice +powerSportsSeat;
        int actualTotalWithMiamiWheelsSeat = getPrice(driver.findElement(By.xpath("//section[@id='s_price']/div[1]/div[4]/div[2]")).getText());
        System.out.println("Actual Total price of car with Miami, wheels, seat: "+ actualTotalWithMiamiWheelsSeat + " | Expected Total price of car with Miami, wheels, seat: " + expectedTotalWithMiamiWheelsSeat);

        Assert.assertEquals(actualTotalWithMiamiWheelsSeat, expectedTotalWithMiamiWheelsSeat);

//      18.Click on Interior Carbon Fiber

        action.sendKeys(Keys.PAGE_DOWN).perform();
        Thread.sleep(1000);
        driver.findElement(By.id("IIC_subHdl")).click();

//      19.Select Interior Trim in Carbon Fiber i.c.w. Standard Interior

        driver.findElement(By.id("vs_table_IIC_x_PEKH_x_c01_PEKH")).click();

//      20.Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels
//            + Power Sport Seats (14-way) with Memory Package + Interior Trim in Carbon Fiber i.c.w.
//            Standard Interior
        int trim = getPrice(driver.findElement(By.xpath("//div[@id='vs_table_IIC_x_PEKH']//div//div[2]")).getText());
        int priceForEquipmentMiamiWheelsPowerSeatTrim =  miamiBluePrice + sportWheelPrice + powerSportsSeat + trim;
        int actualPriceForEquipmentMiamiWheelsPowerSeatTrim = getPrice(driver.findElement(By.xpath("//section[@id='s_price']/div[1]/div[2]/div[2]")).getText());

        System.out.println("Actual Equipment price of miami, wheels, seat, trim: "+ actualPriceForEquipmentMiamiWheelsPowerSeatTrim + " | Expected Equipment price of Miami, Wheels, Seat, trim: " + priceForEquipmentMiamiWheelsPowerSeatTrim);

        Assert.assertEquals(actualPriceForEquipmentMiamiWheelsPowerSeatTrim, priceForEquipmentMiamiWheelsPowerSeatTrim);

//      21.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing
//    and Handling Fee
        int expectedTotalWithMiamiWheelsSeatTrim = priceOfModel718S + priceForDeliveryAndSo + sportWheelPrice +miamiBluePrice +powerSportsSeat + trim;
        int actualTotalWithMiamiWheelsSeatTrim = getPrice(driver.findElement(By.xpath("//section[@id='s_price']/div[1]/div[4]/div[2]")).getText());
        System.out.println("Actual Total price of car with Miami, wheels, seat, trim: "+ actualTotalWithMiamiWheelsSeatTrim + " | Expected Total price of car with Miami, wheels, seat, trim: " + expectedTotalWithMiamiWheelsSeatTrim);

        Assert.assertEquals(actualTotalWithMiamiWheelsSeatTrim, expectedTotalWithMiamiWheelsSeatTrim);

//      22.Click on Performance

        driver.findElement(By.id("IMG_subHdl")).click();
        Thread.sleep(800);

//      23.Select 7-speed Porsche Doppelkupplung (PDK)
        driver.findElement(By.id("vs_table_IMG_x_M250_x_c11_M250")).click();
        int transmission = getPrice(driver.findElement(By.xpath("(//div[.='$3,210'])[2]")).getText());

//      24.Select Porsche Ceramic Composite Brakes (PCCB)
        action.sendKeys(Keys.PAGE_DOWN).perform();
        Thread.sleep(1000);
        driver.findElement(By.id("vs_table_IMG_x_M450_x_c81_M450")).click();
        int breaks = getPrice(driver.findElement(By.xpath("(//div[.='$7,410'])[2]")).getText());

//      25.Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels
//            + Power Sport Seats (14-way) with Memory Package + Interior Trim in Carbon Fiber i.c.w.
//    Standard Interior + 7-speed Porsche Doppelkupplung (PDK) + Porsche Ceramic Composite
//    Brakes (PCCB)

        int brake = getPrice(driver.findElement(By.xpath("//div[@id='vs_table_IMG_x_M450']//div//div[2]")).getText());
        int priceForEquipmentMiamiWheelsPowerSeatTrimBrake =  miamiBluePrice + sportWheelPrice + powerSportsSeat + trim;
        int actualPriceForEquipmentMiamiWheelsPowerSeatTrimBrake = getPrice(driver.findElement(By.xpath("//section[@id='s_price']/div[1]/div[2]/div[2]")).getText());

        System.out.println("Actual Equipment price of miami, wheels, seat, trim, brake: "+ actualPriceForEquipmentMiamiWheelsPowerSeatTrimBrake + " | Expected Equipment price of Miami, Wheels, Seat, trim, brake: " + priceForEquipmentMiamiWheelsPowerSeatTrimBrake);

        Assert.assertEquals(actualPriceForEquipmentMiamiWheelsPowerSeatTrim, priceForEquipmentMiamiWheelsPowerSeatTrim);
//      26.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing
//    and Handling Fee

        int expectedTotalWithMiamiWheelsSeatTrimBrake = priceOfModel718S + priceForDeliveryAndSo + sportWheelPrice +miamiBluePrice +powerSportsSeat + trim + brake;
        int actualTotalWithMiamiWheelsSeatTrimBrake = getPrice(driver.findElement(By.xpath("//section[@id='s_price']/div[1]/div[4]/div[2]")).getText());
        System.out.println("Actual Total price of car with Miami, wheels, seat, trim, brake: "+ actualTotalWithMiamiWheelsSeatTrimBrake + " | Expected Total price of car with Miami, wheels, seat, trim, brake: " + expectedTotalWithMiamiWheelsSeatTrimBrake);

        Assert.assertEquals(actualTotalWithMiamiWheelsSeatTrim, expectedTotalWithMiamiWheelsSeatTrim);




    }

    public int getPrice(String word){
        word= word.substring(1).replace(",","");//$7,536  7536
        int result = Integer.valueOf(word);
        return result;
    }

}

/*
package test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utilities.Driver;
import utilities.TestBase;
import utilities.TestBase2;
import java.util.Set;
public class CaymanNew extends TestBase {
    @Test
    public void buyPorshce() throws InterruptedException {
//                1. Open browser
////        2. Go to url “https://www.porsche.com/usa/modelstart/”
          driver.get("https://www.porsche.com/usa/modelstart/");
//
//
////        3. Select model 718
        driver.findElement(By.xpath("//a//div//span")).click();
////        4. Remember the price of 718 Cayman Model S
        String expectPrice = driver.findElement(By.xpath("(//div[contains(text(),'69')])[2]")).getText();//From $ 69,300.00*
//
//
      expectPrice=expectPrice.substring(7,13).replace(",","");
        int ExpecPrice = Integer.valueOf(expectPrice);
//
////        5. Click on 718 Cayman S
        driver.findElement(By.xpath("(//div[contains(text(),'69')])[2]")).click();
//
////        6. Verify that Base price displayed on the page is same as the price from step 4
        String parent = driver.getWindowHandle();
//
       Thread.sleep(5000);
//
       Set<String> all = driver.getWindowHandles();
        for(String my : all){
            if(! my.equals(parent)){
                driver.switchTo().window(my);
                break;
            }
        }
       int basePrice = getPrice(driver.findElement(By.xpath("(//div[.='$69,300'])[2]")).getText());//69,300
//
        Assert.assertEquals(basePrice,ExpecPrice);
//
//
////        7. Verify that Price for Equipment is 0
        String equipment = driver.findElement(By.xpath("(//*[@class='ccaPrice'])[6]")).getText();//0
//
        Assert.assertTrue(equipment.contains("0"),"0");
////        8. Verify that total price is the sum of base price + Delivery, Processing and Handling Fee
        int fee = getPrice(driver.findElement(By.xpath("(//*[@class='ccaPrice'])[7]")).getText());//1,250
        int total = getPrice(driver.findElement(By.xpath("(//*[@class='ccaPrice'])[8]")).getText());//70.500
        Assert.assertEquals(total,basePrice+fee);

////        9. Select color “Miami Blue”
        driver.findElement(By.id("s_exterieur_x_FJ5")).click();
//
//
////        10.Verify that Price for Equipment is Equal to Miami Blue price
       int bluePrice = getPrice(driver.findElement(By.id("s_exterieur_x_FJ5")).getAttribute("data-price"));
        int equipmentBlue = getPrice(driver.findElement(By.xpath("(//*[@class='ccaPrice'])[6]")).getText());//2,580
        Assert.assertEquals(bluePrice,equipmentBlue);

////        11.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
        int totalBlue = getPrice(driver.findElement(By.xpath("(//*[@class='ccaPrice'])[8]")).getText()); //73.130
        Assert.assertEquals(totalBlue,fee+equipmentBlue+basePrice);
       Thread.sleep(1000);
//
////        12.Select 20" Carrera Sport Wheels
        driver.findElement(By.id("scrollIndicator")).click();
        Thread.sleep(2000);
//
        WebElement element = driver.findElement(By.xpath("(//span[@class='img-element'])[11]")); //20 wheels
       Thread.sleep(2000);
//
//
        actions.moveToElement(element).click(element);
        actions.perform();
        driver.findElement(By.id("s_exterieur_x_MXRD")).click();
//
//
////        13.Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels
        int wheels = getPrice(driver.findElement(By.id("s_exterieur_x_MXRD")).getAttribute("data-price"));
        int eqWheels= getPrice(driver.findElement(By.xpath("(//*[@class='ccaPrice'])[6]")).getText());
        Assert.assertEquals(eqWheels,wheels+bluePrice);
//
//
//
////        14.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
       int totalWheels = getPrice(driver.findElement(By.xpath("(//*[@class='ccaPrice'])[8]")).getText());
        Assert.assertEquals(totalWheels,basePrice+fee+eqWheels);
//
////        15.Select seats ‘Power Sport Seats (14-way) with Memory Package’
        actions.sendKeys(Keys.PAGE_DOWN).perform();
        Thread.sleep(1000);
        actions.sendKeys(Keys.PAGE_DOWN).perform();
        Thread.sleep(1000);
//
        driver.findElement(By.id("s_interieur_x_PP06")).click();
////        16.Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package
        int eqSeats = getPrice(driver.findElement(By.xpath("(//*[@class='ccaPrice'])[6]")).getText());
        int seats = getPrice(driver.findElement(By.xpath("(//*[@class='pBox'])[8]")).getText());
        Assert.assertEquals(eqSeats,seats+bluePrice+wheels);
////        17.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
        Thread.sleep(2000);
        int totalSeats = getPrice(driver.findElement(By.xpath("(//*[@class='ccaPrice'])[8]")).getText());
        Assert.assertEquals(totalSeats,basePrice+fee+eqSeats);
////        18.Click on Interior Carbon Fiber
        actions.sendKeys(Keys.PAGE_DOWN).perform();
       Thread.sleep(1000);
        actions.sendKeys(Keys.PAGE_DOWN).perform();
        Thread.sleep(1000);
//
        driver.findElement(By.id("IIC_subHdl")).click();
        Thread.sleep(800);
////        19.Select Interior Trim in Carbon Fiber i.c.w. Standard Interior
        driver.findElement(By.id("vs_table_IIC_x_PEKH_x_c01_PEKH")).click();
//
////        20.Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package + Interior Trim in Carbon Fiber i.c.w. Standard Interior
//
        Thread.sleep(1000);
        int eqCarbon = getPrice(driver.findElement(By.xpath("(//*[@class='ccaPrice'])[6]")).getText());
        int Carbon = getPrice(driver.findElement(By.xpath("(//*[@class='pBox'])[117]")).getText());
        Assert.assertEquals(eqCarbon,Carbon+bluePrice+seats+wheels);
////        21.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
        Thread.sleep(1000);
        int totalCarbon = getPrice(driver.findElement(By.xpath("(//*[@class='ccaPrice'])[8]")).getText());
        Assert.assertEquals(totalCarbon,basePrice+fee+eqCarbon);
////        22.Click on Performance
//
        actions.sendKeys(Keys.PAGE_UP).perform();
        Thread.sleep(1000);
//
//
        driver.findElement(By.id("IMG_subHdl")).click();
        Thread.sleep(800);
////        23.Select 7-speed Porsche Doppelkupplung (PDK)
        driver.findElement(By.id("vs_table_IMG_x_M250_x_c11_M250")).click();
        int transmission = getPrice(driver.findElement(By.xpath("(//div[.='$3,210'])[2]")).getText());
//
//
////                24.Select Porsche Ceramic Composite Brakes (PCCB)
        actions.sendKeys(Keys.PAGE_DOWN).perform();
       Thread.sleep(1000);
        driver.findElement(By.id("vs_table_IMG_x_M450_x_c81_M450")).click();
        int breaks = getPrice(driver.findElement(By.xpath("(//div[.='$7,410'])[2]")).getText());
//
////        25.Verify that Price for Equipment is the sum of Miami Blue price + 20" Carrera Sport Wheels + Power Sport Seats (14-way) with Memory Package + Interior Trim in Carbon Fiber i.c.w. Standard Interior + 7-speed Porsche Doppelkupplung (PDK) + Porsche Ceramic Composite Brakes (PCCB)
        int eqFinal = getPrice(driver.findElement(By.xpath("(//*[@class='ccaPrice'])[6]")).getText());
        Assert.assertEquals(eqFinal,transmission+breaks+bluePrice+wheels+seats+Carbon);
////        26.Verify that total price is the sum of base price + Price for Equipment + Delivery, Processing and Handling Fee
        int totalFinal = getPrice(driver.findElement(By.xpath("(//*[@class='ccaPrice'])[8]")).getText());
        Assert.assertEquals(totalFinal,basePrice+fee+eqFinal);
//
   }
//
    public int getPrice(String word){
        word= word.substring(1).replace(",","");//$7,536  7536
        int result = Integer.valueOf(word);
        return result;
    }
//
}

 */


//    @Test
//    public void login(){
//        driver.get("https://www.porsche.com/usa/modelstart/");
//
//        //3.Select model 718
//        driver.findElement(By.linkText("718")).click();
//
//        //4. Remember the price of 718 Cayman Model S
//        WebElement priceOfModelS =driver.findElement(By.xpath("//*[@id=\"m982130\"]/div[1]/div[2]/div[2]"));
//        String infoWithPrice =priceOfModelS.getText();
//        String priceOfCar =infoWithPrice.substring(6,13).trim();
//
//
//        //5.Click on 718 Cayman S
//        driver.findElement(By.xpath("//*[@id=\"m982130\"]/div[1]/div[2]/div[1]")).click();
//
//        //6.Verify that Base price displayed on the page is same the price from step 4
//        String winHandleBefore = driver.getWindowHandle();
//
//        // Switch to new window opened
//        for(String winHandle : driver.getWindowHandles()){
//            driver.switchTo().window(winHandle);
//        }
//
//        WebElement basePrice =driver.findElement(By.xpath("//*[@id='s_price']/div[1]/div[1]/div[2]"));
//        String basePriceTxt =basePrice.getText();
//        String basePriceOfCar =basePriceTxt.substring(1).trim();
//        System.out.println("Price on home page: "+priceOfCar+" | Base price: "+basePriceOfCar);
//        Assert.assertEquals(priceOfCar, basePriceOfCar);
//
//        //7.Verify that Price for Equipment 0
//        String expectedEquipment ="0";
//        String equipmentTxt=driver.findElement(By.xpath("//*[@id=\"s_price\"]/div[1]/div[2]/div[2]")).getText();
//        String actualEquipment =equipmentTxt.substring(1).trim();
//        System.out.println("Expected equipment price: "+expectedEquipment+" | Actual Equipment price: "+actualEquipment);
//        Assert.assertEquals(expectedEquipment, actualEquipment);


//    public static void grabHold(WebDriver driver, String parentHandle){
//        /* /NOTE: Be sure to set -> String parentHandle=driver.getWindowHandle(); prior to the action preceding method deployment */
//        Set<String> windows= driver.getWindowHandles();
//        for(String window: windows){
//            if(window!=parentHandle)
//                driver.switchTo().window(window);
//        }
//    }
