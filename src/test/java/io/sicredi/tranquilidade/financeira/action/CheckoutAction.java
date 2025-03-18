package io.sicredi.tranquilidade.financeira.action;


import com.codeborne.selenide.Selenide;
import io.sicredi.tranquilidade.financeira.screen.CheckoutPage;
import net.datafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static io.sicredi.tranquilidade.financeira.action.ProdutosAction.valorTotalItensTelaProdutos;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutAction extends CheckoutPage {
    Faker faker = new Faker();

    public CheckoutAction assertPaginaCheckout() {
        validateText(title,"Checkout: Your Information");
        Assertions.assertEquals("https://www.saucedemo.com/checkout-step-one.html",webdriver().driver().getWebDriver().getCurrentUrl());
        return Selenide.page(CheckoutAction.class);

    }
    public CheckoutAction informacoesPessoais() {
        inserir(firstname,faker.name().firstName()).inserir(lastname,faker.name().lastName()).inserir(postalcode, faker.address().zipCode());
        return Selenide.page(CheckoutAction.class);

    }
    public CheckoutAction assertNomeItensAdd(List<String> itens) {
        itens.forEach(produto -> assertTrue($(By.xpath("//*[contains(text(),'"+produto+"')]")).exists()));
        return Selenide.page(CheckoutAction.class);

    }
    public CheckoutAction assertValorTotal() {
        assertThat("Valor está divergente: ", valortotaltelacheckout.getText().replace("$",""), containsString(valorTotalItensTelaProdutos.toString()));
        return Selenide.page(CheckoutAction.class);

    }
    public CheckoutAction next() {
        click(btncontinue);
        return Selenide.page(CheckoutAction.class);

    }
    public void finalizar() {
        click(btnfinish);

    }
    public Boolean assertCheckoutCompleto(String mensagemSucesso) {
        validateText(checkoutcomplete,mensagemSucesso);
        return Boolean.TRUE;
    }
}

