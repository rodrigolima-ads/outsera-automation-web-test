package io.company.outsera.screen;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;


public class ProdutosPage extends BasePage {
    public static SelenideElement title = $(By.className("title"));
    public static String findxpathproduto = "//*[contains(text(),'%s')]/ancestor::div[2]//child::button";
    public static String findxpathvalores = "(//*[contains(text(),'%s')]/ancestor::div[2]//child::div)[5]";
    public static SelenideElement totalitensiconecarrinho = Selenide.$(By.className("shopping_cart_badge"));
    public static SelenideElement ircarrinho = Selenide.$(By.className("shopping_cart_link"));



}
