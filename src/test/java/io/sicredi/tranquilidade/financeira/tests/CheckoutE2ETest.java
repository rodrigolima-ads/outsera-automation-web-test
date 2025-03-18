package io.sicredi.tranquilidade.financeira.tests;

import io.sicredi.tranquilidade.financeira.action.CarrinhoAction;
import io.sicredi.tranquilidade.financeira.action.CheckoutAction;
import io.sicredi.tranquilidade.financeira.action.LoginAction;
import io.sicredi.tranquilidade.financeira.action.ProdutosAction;
import io.sicredi.tranquilidade.financeira.setup.SetupTest;
import io.sicredi.tranquilidade.financeira.utils.report.ConfigFail;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static io.sicredi.tranquilidade.financeira.utils.loader.ManagerPropertyLoader.getLogin;

@ExtendWith(ConfigFail.class)
@Tags(value = {@Tag("Regressivo"), @Tag("checkout")})
class CheckoutE2ETest extends SetupTest {

    List<String> listaProdutos =  List.of("Bike Light", "Onesie", "Backpack", "Jacket", "T-Shirt");

    @Test
    @DisplayName("[CHECKOUT] - Realizar fluxo de ponta a ponta até checkout")
    void deveConseguirRealizarTodaJornadaDeCompra() {

        new LoginAction()
                .loginSauceDemo(getLogin("permissao"));

        new ProdutosAction()
                .assertPaginaProdutos()
                .adicionarProdutosCarrinho(listaProdutos)
                .assertTotalItensIconeCarrinho()
                .irCarrinho();

        new CarrinhoAction()
                .assertPaginaCarrinho()
                .assertTotalItensAdd(listaProdutos.size())
                .assertNomeItensAdd(listaProdutos)
                .irCheckout();

        new CheckoutAction()
                .assertPaginaCheckout()
                .informacoesPessoais()
                .next()
                .assertNomeItensAdd(listaProdutos)
                .assertValorTotal()
                .finalizar();

        Assertions.assertTrue(new CheckoutAction().assertCheckoutCompleto("Thank you for your order!"));

    }



}

