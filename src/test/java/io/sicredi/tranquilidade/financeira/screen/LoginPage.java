package io.sicredi.tranquilidade.financeira.screen;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;


public class LoginPage extends BasePage {

    public SelenideElement inputusuario = $(By.id("user-name"));
    public SelenideElement inputsenha = $(By.id("password"));
    public SelenideElement btnEntrar = $(By.id("login-button"));
    public SelenideElement msguserlocked = $(By.xpath("//*[@data-test='error']"));
    public SelenideElement titlehome = $(By.className("app_logo"));
    public SelenideElement colormsgerror = $(By.xpath("//*[@class='error-message-container error']"));


}
