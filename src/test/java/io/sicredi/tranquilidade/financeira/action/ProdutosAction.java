package io.sicredi.tranquilidade.financeira.action;


import com.codeborne.selenide.Selenide;
import io.sicredi.tranquilidade.financeira.screen.ProdutosPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;

public class ProdutosAction extends ProdutosPage {
    static List<String> itens;
    static Double valorTotalItensTelaProdutos = 0d;

    public ProdutosAction assertPaginaProdutos() {
        validateText(title,"Products");
        Assertions.assertEquals("https://www.saucedemo.com/inventory.html",webdriver().driver().getWebDriver().getCurrentUrl());
        return Selenide.page(ProdutosAction.class);

    }

    public ProdutosAction adicionarProdutosCarrinho(List<String> produtos) {
        itens = produtos;
        for (String item : produtos) {
            click(findxpathproduto, item);
        }
        return Selenide.page(ProdutosAction.class);
    }

    public ProdutosAction assertTotalItensIconeCarrinho(){

        Assertions.assertEquals(itens.size(), Integer.valueOf(totalitensiconecarrinho.getOwnText()));

        return Selenide.page(ProdutosAction.class);
    }
    public void irCarrinho(){
        valorTotalTelaProdutos();
        click(ircarrinho);

    }
    private void valorTotalTelaProdutos(){
        for (String item : itens) {
            var valorItem = Double.parseDouble($(By.xpath(String.format(findxpathvalores, item))).getText().replace("$",""));
            valorTotalItensTelaProdutos = valorTotalItensTelaProdutos + valorItem;
        }

    }
}

