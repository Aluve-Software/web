package tech.aluvesoftware;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Pattern;

public class WebActions {
    private final WebDriver driver;
    private final int waitTime;

    public WebActions(WebDriver driver, int waitTime) {
        this.driver = driver;
        this.waitTime = waitTime;
    }

    /**
     * This function checks a checkbox.
     *
     * @param checkBoxText The text label of the checkbox that needs to be checked.
     * @param locator The By locator used to find an element
     * */
    public void checkCheckboxByLabel(String checkBoxText, By locator) {
        WebElement boxToCheck = driver.findElement(locator);
        if (!boxToCheck.isSelected()) {
            boxToCheck.click();
        }
    }

    /**
     * This function clicks on a radio button based on its label if it is not already selected.
     *
     * @param label The label of the radio button that needs to be clicked.
     * @param locator The By locator used to find an element
     */
    public void clickRadioBtnByLabel(String label, By locator) {
        WebElement radioBtn = driver.findElement(locator);
        if (!radioBtn.isSelected()) {
            radioBtn.click();
        }
    }


    /**
     * This function checks if a given text is present in an element located by a given locator using
     * explicit wait.
     *
     * @param byLocator It is a locator strategy used to identify a web element on the page. It can be any
     * of the supported locator strategies in Selenium such as ID, name, class name, CSS selector, or
     * XPath.
     * @param text The text that we want to check if it is present in the element located by the given By
     * locator.
     * @return The method is returning a boolean value indicating whether the specified text is present in
     * the web element located by the given locator. If the text is present, it returns true, otherwise it
     * returns false.
     */
    public boolean isTextPresentInElement(By byLocator, String text) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            return wait.until(ExpectedConditions.textToBePresentInElementLocated(byLocator, text));
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * @param byLocator The By locator used to find an element
     */
    public String getElementText(By byLocator){

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
            wait.until(ExpectedConditions.not(ExpectedConditions.textMatches(byLocator, Pattern.compile("^$"))));
            return driver.findElement(byLocator).getText();
        } catch (Exception e) {
            return driver.findElement(byLocator).getText();
        }
    }

    /**
     * The function clicks on an element located by a given locator, with a maximum of three attempts
     * in case of exceptions.
     *
     * @param byLocator The parameter "byLocator" is a By object that represents a locator strategy (such
     * as By.id, By.xpath, By.cssSelector, etc.) and a locator value (such as "username",
     * "//input[@id='username']", ".login-form input[name='username']", etc.) used to identify
     */
    public void clickElement(By byLocator) {
        boolean exceptionOccurred = false;
        int numberOfTries = 0;
        do {
            try {
                numberOfTries++;
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTime));
                wait.until(ExpectedConditions.elementToBeClickable(byLocator));
                wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator)).click();
            } catch (Exception e) {
                exceptionOccurred = true;
            }
        } while (exceptionOccurred && numberOfTries < 3);
    }


    /**
     * The function updates a field on a page with new data using a given locator.
     *
     * @param locator The locator is a By object that specifies how to locate the web element on the
     * page. It can be based on various attributes such as ID, class name, name, tag name, link text,
     * or partial link text.
     * @param newData newData is a String variable that represents the new data that will be entered
     * into the field.
     */
    public void updateField(By locator, String newData){
        (new Actions(driver)).moveToElement(driver.findElement(locator)).perform();
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(newData);
    }

    /**
     * The function selects an option for a select element in a page.
     *
     * @param locator The locator is a By object that specifies how to locate the web element on the
     * page. It can be based on various attributes such as ID, class name, name, tag name, link text,
     * or partial link text.
     * @param visibleData visibleData is a String variable that represents the visible text that will be selected
     */
    public void selectTextOption(By locator, String visibleData){
        WebElement selectElement = driver.findElement(locator);
        Select select = new Select(selectElement);
        // Select an option by visible text
        select.selectByVisibleText(visibleData);
    }
}
