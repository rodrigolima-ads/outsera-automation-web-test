package io.sicredi.tranquilidade.financeira.screen;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;


public class CheckoutPage extends BasePage {

     public static SelenideElement firstname = $(By.name("firstName"));
    public static SelenideElement lastname = $(By.name("lastName"));
    public static SelenideElement postalcode = $(By.name("postalCode"));
    public static SelenideElement title = $(By.className("title"));
    public static SelenideElement valortotaltelacheckout = $(By.xpath("//*[@data-test='subtotal-label']"));
    public static SelenideElement btncontinue= $(By.id("continue"));
    public static SelenideElement btnfinish= $(By.id("finish"));
    public static SelenideElement checkoutcomplete = $(By.className("complete-header"));

}
