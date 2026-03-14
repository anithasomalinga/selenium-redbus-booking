package org.anitha;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RedBusBooking {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.addArguments("--start-maximized");
//        WebDriver driver = new ChromeDriver(chromeOptions);
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.redbus.in");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
//        By sourceButtonLocator = By.xpath("//input[contains(@id,\"srcinput\")]");
        By sourceButtonLocator = By.id("srcinput");
        WebElement sourceButtonElement = wait.until(ExpectedConditions.elementToBeClickable(sourceButtonLocator));
//        WebElement sourceButton = driver.findElement(sourceButtonLocator);
//        sourceButton.click();
        //⚠️ If .click() Does Not Work
        //
        //Some modern UI frameworks block direct clicks. Use JavaScript click:
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", sourceButtonElement);

        driver.switchTo().activeElement();
        sourceButtonElement.sendKeys("Chennai");

        By searchCategoryLocator = By.xpath("//div[contains(@class,\"searchCategory\")]");
        List<WebElement> searchCategories = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchCategoryLocator, 1));

        WebElement locationSelection = searchCategories.get(0);
        By listHeaderLocator = By.xpath(".//div[contains(@class,\"listHeader\")]");
        List<WebElement> locationList = locationSelection.findElements(listHeaderLocator);

        for(WebElement webElement : locationList) {
            if("Chennai".equalsIgnoreCase(webElement.getText())) {
                webElement.click();
                break;
            }
        }

        By destButtonLocator = By.id("destinput");
        WebElement destButtonElement = wait.until(ExpectedConditions.elementToBeClickable(destButtonLocator));
        js.executeScript("arguments[0].click();", destButtonElement);
        driver.switchTo().activeElement();
        destButtonElement.sendKeys("Madurai");

        searchCategories = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchCategoryLocator, 1));

        locationSelection = searchCategories.get(0);
        locationList = locationSelection.findElements(listHeaderLocator);

        for(WebElement webElement : locationList) {
            if("Madurai".equalsIgnoreCase(webElement.getText())) {
                webElement.click();
                break;
            }
        }

        By dateOptionsLocator = By.xpath("//div[contains(@class,\"tonalWrap\")]");
        List<WebElement> dateOptions = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(dateOptionsLocator, 1));

        WebElement dateOptionTomorrow = dateOptions.get(1);
        dateOptionTomorrow.click();

//        By searchButtonLocator = By.xpath("//button[contains(@class,\"primaryButton\")]");
//        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(searchButtonLocator));
//        searchButton.click();

        By primoBusLocator = By.xpath("//div[contains(text(),\"Primo Bus\")]");
        wait.until(ExpectedConditions.elementToBeClickable(primoBusLocator)).click();

        By acLocator = By.xpath("//div[contains(text(),\"AC\")]");
        wait.until(ExpectedConditions.elementToBeClickable(acLocator)).click();

        By noOfBusesLocator = By.xpath("//span[contains(@class,\"subtitle\")]");
        WebElement noOfBuses;
        if(wait.until(ExpectedConditions.textToBePresentInElementLocated(noOfBusesLocator, "buses"))) {
            noOfBuses = driver.findElement(noOfBusesLocator);
            System.out.println(noOfBuses.getText());
        }

        By busesFrameLocator = By.xpath("//li[contains(@class,\"tupleWrapper\")]");
        By busNameLocator = By.xpath(".//div[contains(@class,\"travelsName\")]");
        By endOfListLocator = By.xpath("//span[contains(@class,\"endText\")]");

        while(true) {
            List<WebElement> rowList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(busesFrameLocator));
            List<WebElement> endOfList = driver.findElements(endOfListLocator);
            if(!endOfList.isEmpty()) {
                break;
            }
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth'})", rowList.get(rowList.size() - 3));
        }
        List<WebElement> busList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(busesFrameLocator));
        System.out.println("No of buses: " + busList.size());
        for(WebElement element : busList) {
            WebElement travelName = element.findElement(busNameLocator);
            System.out.println(travelName.getText());
        }


    }
}
