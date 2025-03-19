package io.company.outsera.screen;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class CarrinhoPage extends BasePage {

    public static ElementsCollection itenscarrinho = $$(By.className("cart_item"));
    public static SelenideElement title = $(By.className("title"));
    public static SelenideElement irchechout = $(By.id("checkout"));


}
