package io.company.outsera.tests;

import io.company.outsera.action.CarrinhoAction;
import io.company.outsera.action.CheckoutAction;
import io.company.outsera.action.LoginAction;
import io.company.outsera.action.ProdutosAction;
import io.company.outsera.setup.SetupTest;
import io.company.outsera.utils.report.ConfigFail;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static io.company.outsera.utils.loader.ManagerPropertyLoader.getLogin;

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

