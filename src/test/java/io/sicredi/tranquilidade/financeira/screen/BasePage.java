package io.sicredi.tranquilidade.financeira.screen;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.SetValueOptions;
import com.codeborne.selenide.ex.ElementNotFound;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.sicredi.tranquilidade.financeira.utils.report.Report.generateReport;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class BasePage{
    private static final Logger log = Logger.getLogger("QALogger");

    public static void waitElementVisible(SelenideElement element, int time) {
        try {
            Wait<WebDriver> wait = new FluentWait<>(getWebDriver())
                    .withTimeout(Duration.ofSeconds(time))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(ElementNotFound.class)
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class)
                    .ignoring(Exception.class)
                    .withMessage("Não foi possivel encontrado o elemento no tempo definido!");
            wait.until(ExpectedConditions.visibilityOfAllElements(element));
        } catch (Exception exception) {
            logActionTest(element, String.format("Não foi possivel encontrar o ELEMENTO no tempo definido!: %d segundos", time));

        }

    }

    public BasePage click(SelenideElement el) {
        generateReport("CLICK EM ELEMENTO", String.format("Elemento %s a ser clicado", el));
        el.shouldBe(enabled).click();
        logActionTest(el, "Click");
        return this;

    }

    public BasePage click(String xpath, String text) {
        generateReport("CLICK", String.format("Item %s clicado", text));
        $(By.xpath(String.format(xpath, text))).click();
        logActionTest(text, "Click em parte do texto realizado!");
        return this;
    }

    public BasePage inserir(SelenideElement el, String text) {
        el.shouldBe(exist).setValue(SetValueOptions.withText(text));
        logActionTest(el, String.format("Texto: %s inserido com sucesso!", text));
        generateReport("INSERINDO TEXTO NO CAMPO", String.format("Texto %s inserido com sucesso", text));
        return this;
    }

    public BasePage validateText(WebElement textoAtual, String textoEsperado) {
        assertThat(textoAtual.getText(), containsString(textoEsperado));
        generateReport("VALIDAR SE CONTEM PARTE DO TEXTO", "Texto atual corresponde ao esperado!");
        logActionTest(textoAtual, "contem parte do texto");
        return this;
    }

    public boolean isTextVisible(String text,int seg) {
        return $(By.xpath("//*[contains(text(),'" + text + "')]")).isDisplayed();
    }


    public void validateMsgMouseHover(SelenideElement el, String msg) {
        el.hover();
        assertThat("Mensagem não apresentada", isTextVisible(msg,1));
        generateReport("Mouse Houve", String.format("Mensagem  %s apresentada", msg));

    }

    public void validateColorsRGB(SelenideElement el, String rgbColor, String cssValue) {
        Assertions.assertThat(el.getCssValue(cssValue)).contains(rgbColor);
    }


    public static void logActionTest(Object element, String action) {
        log.log(Level.INFO, () -> String.format("Elemento: %s | Action: %s", element, action));

    }

}


