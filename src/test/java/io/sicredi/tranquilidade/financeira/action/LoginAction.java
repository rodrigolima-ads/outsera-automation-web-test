package io.sicredi.tranquilidade.financeira.action;


import com.codeborne.selenide.Selenide;
import io.sicredi.tranquilidade.financeira.screen.LoginPage;
import io.sicredi.tranquilidade.financeira.utils.loader.ManagerPropertyLoader.Credencias;


public class LoginAction extends LoginPage {
    private static final String BACKGROUND_RED = "226, 35, 26";
    private static final String BACKGROUND_COLOR = "background-color";

    public LoginAction loginSauceDemo(Credencias user) {
        inserir(inputusuario, user.getLogin())
                .inserir(inputsenha, user.getSenha())
                .click(btnEntrar);
      return   Selenide.page(LoginAction.class);

    }

    public void validarlogin(String mensagem) {
        if (mensagem.endsWith("locked out.")) {
            validateText(msguserlocked,mensagem);
            validateColorsRGB(colormsgerror, BACKGROUND_RED, BACKGROUND_COLOR);
        }else {
            validateText(titlehome,mensagem);
        }

    }

}
