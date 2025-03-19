package io.company.outsera.tests;

import io.company.outsera.action.LoginAction;
import io.company.outsera.setup.SetupTest;
import io.company.outsera.utils.report.ConfigFail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static io.company.outsera.utils.loader.ManagerPropertyLoader.getLogin;

@Tags(value = {@Tag("Regressivo"), @Tag("login")})
@ExtendWith(ConfigFail.class)
class LoginTest extends SetupTest {


    @ParameterizedTest
    @CsvSource({"bloqueado, 'Epic sadface: Sorry, this user has been locked out.'", "permissao, Swag Labs"})
    @DisplayName("[LOGIN] - Deve conseguir logar com usuario com acesso e apresentar erro para usuario bloqueado")
    void deveValidaLoginParaUsuarioComESemPermissao(String usuario, String status) {

        new LoginAction()
                .loginSauceDemo(getLogin(usuario))
                .validarlogin(status);



    }


}

