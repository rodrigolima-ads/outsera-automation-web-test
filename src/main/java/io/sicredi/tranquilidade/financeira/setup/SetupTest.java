package io.sicredi.tranquilidade.financeira.setup;


import com.codeborne.selenide.Configuration;
import com.google.common.base.Strings;
import io.sicredi.tranquilidade.financeira.utils.report.ConfigFail;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.sicredi.tranquilidade.financeira.utils.constantes.Parameters.URL;
import static io.sicredi.tranquilidade.financeira.utils.report.Report.configParametsReport;


@ExtendWith(ConfigFail.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SetupTest {

    @BeforeEach
    public void setup(TestInfo testInfo) {
        configSelenide();
        open(URL);
        getWebDriver().manage().window().maximize();
        configParametsReport(testInfo);


    }
    private static void configSelenide() {
        if (!Strings.isNullOrEmpty(System.getenv("CI"))) {
            Configuration.remote = "http://chrome:4444/wd/hub";
            Configuration.headless = true;
            Configuration.browser = "chrome";
        }

    }
}