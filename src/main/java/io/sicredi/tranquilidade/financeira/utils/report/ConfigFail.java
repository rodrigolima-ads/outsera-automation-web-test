package io.sicredi.tranquilidade.financeira.utils.report;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sicredi.tranquilidade.financeira.utils.constantes.Parameters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestPlan;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static io.sicredi.tranquilidade.financeira.utils.report.Report.generateReport;
import static org.apache.commons.lang3.StringUtils.substringBetween;

public class ConfigFail implements TestWatcher, TestExecutionListener {
    static ReportDTO reportDTO;
    static List<Error> exceptionsList;

    static {
        String exceptiosTypesFile = "src/test/resources/json/exceptions_element.json";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            exceptionsList = objectMapper.readValue(new File(exceptiosTypesFile), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());

        }
    }

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        reportDTO = ReportDTO.builder().build();
    }

    @Override
    public void testFailed(ExtensionContext extensionContext, Throwable throwable) {
        if (Parameters.REPORT) {
            reportDTO =
                    ReportDTO.builder()
                            .status("FAILED")
                            .type(throwable.getClass().getSimpleName())
                            .element(getCauseFail(throwable))
                            .newScenario(Boolean.FALSE)
                            .details(getDetalheErro(throwable.getClass().getSimpleName())).steps(reportDTO.getSteps()).build();

            generateReport(reportDTO.getStatus(), "Ops! ocorreu um erro durante a execução de teste");
        }
        closeWebDriver();
    }


    public String getDetalheErro(String tipoErro) {
        return exceptionsList.stream()
                .filter(i -> Objects.equals(i.getException(), tipoErro))
                .findFirst()
                .map(Error::getMessage)
                .orElse("Tipo de erro não cadastrado na lista de exceptions");
    }

    public String getCauseFail(Throwable throwable) {
        String cause = substringBetween(String.valueOf(throwable.getCause()), "{", "}");
        if (cause == null) cause = substringBetween(String.valueOf(throwable.getMessage()), "{", "}");
        if (cause == null) cause = throwable.getMessage();
        return cause;
    }


    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Error {
        private String message;
        private String exception;
    }

    @Data
    @Builder
    @AllArgsConstructor
    static class ReportDTO {
        private String type;
        private String details;
        @Builder.Default
        private String status = "PASSED";
        private String element;
        @Builder.Default
        private Integer steps = 1;
        @Builder.Default
        private Boolean newScenario = Boolean.TRUE;
    }

}