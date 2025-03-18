package io.sicredi.tranquilidade.financeira.action;


import com.codeborne.selenide.Selenide;
import io.sicredi.tranquilidade.financeira.screen.CarrinhoPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarrinhoAction extends CarrinhoPage {

    public CarrinhoAction assertPaginaCarrinho() {
        validateText(title,"Your Cart");
        Assertions.assertEquals("https://www.saucedemo.com/cart.html",webdriver().driver().getWebDriver().getCurrentUrl());
        return Selenide.page(CarrinhoAction.class);

    }
    public CarrinhoAction assertTotalItensAdd(Integer totalItens) {
        Assertions.assertEquals(totalItens, itenscarrinho.size());
        return Selenide.page(CarrinhoAction.class);

    }
    public CarrinhoAction assertNomeItensAdd(List<String> itens) {
        itens.forEach(produto -> assertTrue($(By.xpath("//*[contains(text(),'"+produto+"')]")).exists()));
        return Selenide.page(CarrinhoAction.class);

    }
    public void irCheckout() {
        click(irchechout);

    }
}

