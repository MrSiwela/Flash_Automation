package org.example.WebshopTestSteps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;

import javax.xml.bind.Element;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyStepdefs {

    WebDriver driver;

    @BeforeAll
    public void initSteps(){
        String path = System.getProperty("user.dir");
        System.out.println(path);
        System.setProperty("webdriver.chrome.driver",path+"/drivers/chromedriver.exe");
    }
    @Given("A user navigates to the correct URL {string}")
    public void aUserNavigatesToTheCorrectURL(String url) {
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
        driver.get(url);
        driver.manage().window().maximize();
    }

    @When("The user Navigates to the registration Page and Registers Using The following details : {string},{string},{string},{string} and {string}.")
    public void theUserNavigatesToTheRegistrationPageAndRegistersUsingTheFollowingDetailsAnd(String gender, String firstname, String lastname, String email, String password) throws InterruptedException {
        WebElement registerPageButton = driver.findElement(By.xpath("//a[contains(text(),'Register')]"));
        registerPageButton.click();
        WebElement radioButton = driver.findElement(By.xpath("//label[contains(text(),'"+gender+"')]//preceding::input[1]"));
        if(!radioButton.isDisplayed()){
            radioButton.wait();
        }
        radioButton.click();
        WebElement firstNameInput = driver.findElement(By.xpath("//input[@name='FirstName']"));
        firstNameInput.sendKeys(firstname);
        WebElement lastNameInput = driver.findElement(By.xpath("//input[@name='LastName']"));
        lastNameInput.sendKeys(lastname);
        WebElement emailInput = driver.findElement(By.xpath("//input[@name='Email']"));
        emailInput.sendKeys(email);
        WebElement passwordInput = driver.findElement(By.xpath("//input[@name='Password']"));
        passwordInput.sendKeys(password);
        WebElement confirmPasswordInput = driver.findElement(By.xpath("//input[@name='ConfirmPassword']"));
        confirmPasswordInput.sendKeys(password);
        WebElement registerButton = driver.findElement(By.xpath("//input[@name='register-button']"));
        registerButton.click();

        WebElement continueButton = driver.findElement(By.xpath("//input[@value='Continue']"));
        if(!continueButton.isDisplayed()){
            continueButton.wait();
            continueButton.click();
        }else{
            continueButton.click();
        }
    }

    @Then("The user Logs In with the given Details.")
    public void theUserLogsInWithTheGivenDetails() {

    }

    @And("Adds all desktop Items to the cart.")
    public void addsAllDesktopItemsToTheCart() throws InterruptedException {
        WebElement computersButton = driver.findElement(By.xpath("//a[@href='/computers']"));
        if(!computersButton.isDisplayed()){
            computersButton.wait();
            computersButton.click();
        }else{
            computersButton.click();
        }

        WebElement desktopsButton = driver.findElement(By.xpath("//div[@class='center-2']//a[@href='/desktops']"));
        if(!desktopsButton.isDisplayed()){
            desktopsButton.wait();
            desktopsButton.click();
        }else{
            desktopsButton.click();
        }
        WebElement display = driver.findElement(By.xpath("//select[@name='products-pagesize']"));
        Select displaySelect = new Select(display);
        List<WebElement> options = displaySelect.getOptions();
        if(options.size()>0){
            displaySelect.selectByIndex(options.size()-1);
        }
        List<WebElement> items = driver.findElements(By.xpath("//input[@value='Add to cart']"));
        for (WebElement itemBox:items) {
            try{
                itemBox.click();
                WebElement itemAddToCart = driver.findElement(By.xpath("//div[@class='product-essential']//input[@value='Add to cart']"));
                if(!itemAddToCart.isDisplayed()){
                    itemAddToCart.wait();
                    itemAddToCart.click();
                }else{
                    itemAddToCart.click();
                }
                Thread.sleep(2000);
                WebElement notificationBar = driver.findElement(By.xpath("//div[@id='bar-notification']"));
                if(notificationBar.getText().contains("Please select HDD")){
                    WebElement hddSelect = driver.findElement(By.xpath("//label[contains(text(),'320 GB')]//preceding::input[1]"));
                    if(hddSelect.isDisplayed()){
                        new Actions(driver).moveToElement(hddSelect).click().build().perform();
                    }
                    itemAddToCart.click();
                }
                if(notificationBar.getText().contains("Please select Processor")){
                    WebElement processor = driver.findElement(By.xpath("//label[contains(text(),'Slow')]//preceding::input[1]"));
                    if(processor.isDisplayed()){
                        new Actions(driver).moveToElement(processor).click().build().perform();
                    }
                    itemAddToCart.click();
                }
                driver.navigate().back();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    @And("Removes an item in the cart.")
    public void removesAnItemInTheCart() throws InterruptedException {
        WebElement cart = driver.findElement(By.xpath("//a[@href='/cart']"));
        if(!cart.isDisplayed()){
            cart.wait();
            cart.click();
        }else{
            cart.click();
        }
        WebElement selectItem = driver.findElement(By.xpath("//input[@name='removefromcart']"));
        selectItem.click();
        WebElement updateCart = driver.findElement(By.xpath("//input[@name='updatecart']"));
        updateCart.click();
    }

    @Then("Navigates to my account and validate order is created successful.")
    public void navigatesToMyAccountAndValidateOrderIsCreatedSuccessful() {
        WebElement orderDetails = driver.findElement(By.xpath("//a[contains(text(),'Click here for order details.')]"));
        orderDetails.click();

        WebElement customerAccount = driver.findElement(By.xpath("//a[@href='/customer/info']"));
        customerAccount.click();

        WebElement customerOrders = driver.findElement(By.xpath("//a[@href='/customer/orders']"));
        customerOrders.click();

        WebElement pendingOrderDetails = driver.findElement(By.xpath("//input[@value='Details']"));
        pendingOrderDetails.click();
    }

    @And("Validates the cart with details {string},{string},{string}.")
    public void validatesTheCartWithDetails(String country, String stateProvince, String zipcode) {

        WebElement countries = driver.findElement(By.xpath("//select[@id='CountryId']"));
        Select countrySelect = new Select(countries);
        countrySelect.selectByVisibleText(country);
        WebElement stateProvinceSelect = driver.findElement(By.xpath("//select[@id='StateProvinceId']"));
        Select stateProvinceOption = new Select(stateProvinceSelect);
        stateProvinceOption.selectByVisibleText(stateProvince);
        WebElement zipCode = driver.findElement(By.xpath("//input[@id='ZipPostalCode']"));
        zipCode.sendKeys(zipcode);
        WebElement agreement = driver.findElement(By.xpath("//input[@id='termsofservice']"));
        agreement.click();

        WebElement checkout = driver.findElement(By.xpath("//button[@id='checkout']"));
        checkout.click();
    }

    @And("Checkout items from cart and validate checkout details {string},{string},{string},{string},{string}, {string} and {string}.")
    public void checkoutItemsFromCartAndValidateCheckoutDetailsAnd(String country, String stateProvince, String zipcode, String number, String city, String address1, String address2) {
        WebElement countries = driver.findElement(By.xpath("//select[@id='BillingNewAddress_CountryId']"));
        Select countrySelect = new Select(countries);
        countrySelect.selectByVisibleText(country);
        WebElement stateProvinceSelect = driver.findElement(By.xpath("//select[@id='BillingNewAddress_StateProvinceId']"));
        Select stateProvinceOption = new Select(stateProvinceSelect);
        stateProvinceOption.selectByVisibleText(stateProvince);
        WebElement billingNewAddressCity = driver.findElement(By.xpath("//input[@id='BillingNewAddress_City']"));
        billingNewAddressCity.sendKeys(city);
        WebElement billingNewAddressAddress1 = driver.findElement(By.xpath("//input[@id='BillingNewAddress_Address1']"));
        billingNewAddressAddress1.sendKeys(address1);
        WebElement billingNewAddressAddress2 = driver.findElement(By.xpath("//input[@id='BillingNewAddress_Address2']"));
        billingNewAddressAddress2.sendKeys(address2);
        WebElement zipCode = driver.findElement(By.xpath("//input[@id='BillingNewAddress_ZipPostalCode']"));
        zipCode.sendKeys(zipcode);
        WebElement phone = driver.findElement(By.xpath("//input[@id='BillingNewAddress_PhoneNumber']"));
        phone.sendKeys(number);
        WebElement billingContinueBtn = driver.findElement(By.xpath("//input[@onclick='Billing.save()']"));
        billingContinueBtn.click();
        WebElement shippingContinueBtn = driver.findElement(By.xpath("//input[@onclick='Shipping.save()']"));
        shippingContinueBtn.click();
        WebElement shippingMethodContinueBtn = driver.findElement(By.xpath("//input[@onclick='ShippingMethod.save()']"));
        shippingMethodContinueBtn.click();
        WebElement paymentMethodContinueBtn = driver.findElement(By.xpath("//input[@onclick='PaymentMethod.save()']"));
        paymentMethodContinueBtn.click();
        WebElement paymentInfoContinueBtn = driver.findElement(By.xpath("//input[@onclick='PaymentInfo.save()']"));
        paymentInfoContinueBtn.click();
        WebElement confirmBtn = driver.findElement(By.xpath("//input[@value='Confirm']"));
        confirmBtn.click();
    }
}
