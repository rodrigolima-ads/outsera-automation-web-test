package io.sicredi.tranquilidade.financeira.utils.report;

import com.google.common.io.Resources;

import io.sicredi.tranquilidade.financeira.utils.constantes.Parameters;
import lombok.SneakyThrows;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;


public class Report extends ConfigFail {


    static String nomeTeste;
    static String nomeCenario;
    static String imagem;
    static Integer passo = 1;
    private static final String RESPONSAVEL = "Equipe de Automação";
    private static final String BACKGROUND = "background-color: #000000;";
    private static final String TAG_EVIDENCIA = "<tr class='evidencia'>";
    private static final String TAG_TD = "</td>";
    private static final String TAG_TR = "</tr>";
    private static final String TAG_B = "</b></td>";
    private static final String TAG_TABLE = "</table>";
    private static final String PATH = System.getProperty("user.dir");
    public static final String TITULO = "Relátorio de Automação de Testes - Web";
    private static final LocalDate date = LocalDate.now();
    public static final Logger log = Logger.getLogger("QALogger");

    public static String timeStampEvd() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date());
    }

    public static String capture(WebDriver driver) {
        TakesScreenshot newScreen = (TakesScreenshot) driver;
        String scnShot = newScreen.getScreenshotAs(OutputType.BASE64);
        return "data:image/jpg;base64, " + scnShot;

    }

    static File rootsPath = new File(String.format("%s/evidencias/relatorio/%s", PATH, date));

    static String pathReport() {
        if (!rootsPath.exists()) {
            boolean wasSuccessful = rootsPath.mkdirs();
            if (!wasSuccessful) {
                log.info("was not successful.");
            }

        }
        return String.format("/%s/%s.html", rootsPath, nomeTeste);


    }

    @SneakyThrows
    public static void generateReport(String titulo, String acao) {
        if (Parameters.REPORT) {
            try {
                imagem = capture(getWebDriver());
                if (Boolean.TRUE.equals(reportDTO.getNewScenario())) {
                    try (PrintWriter writer = new PrintWriter(pathReport(), String.valueOf(StandardCharsets.UTF_8))) {
                        writer.println("<html>");
                        writer.println("<head> <link rel='shortcut icon' href='imagens/iconAndroid.png' />  <link rel='icon' href='imagens/iconAndroid.png' type='image/x-icon' />  <title>" + TITULO + "</title>");
                        writer.println("<meta http-equiv=\"Content-Type\"content=\"text/html;charset=UTF-8\">");
                        writer.println("<style>");
                        writer.println(".evidencia{");
                        writer.println("border-collapse:collapse;");
                        writer.println("border: 1px solid black;");
                        writer.println("}");
                        writer.println(".Passed{");
                        writer.println("color: #39ff14;");
                        writer.println(BACKGROUND);
                        writer.println("}");
                        writer.println(".Warning{");
                        writer.println("color: #FFFF00;");
                        writer.println(BACKGROUND);
                        writer.println("}");
                        writer.println(".Failed{");
                        writer.println("color:#FF0000;");
                        writer.println(BACKGROUND);
                        writer.println("}");
                        writer.println(".Error{");
                        writer.println("color:#FFA500;");
                        writer.println("}");
                        writer.println("</style>");
                        writer.println("</head>");
                        writer.println("<body>");
                        writer.println("<center><h1><font color='#003366'>Relatário de Execução do Teste:</font><center><br>");
                        writer.println("<table>");
                        writer.println("<tr>");
                        writer.println("<td><img src='data:image/png;base64," + readFileFromResources("imagens/ImagemReportLeft.txt") + "'/></td>");
                        writer.println("<td>");
                        writer.println("<center><h2>" + TITULO + "<font color='#FA8072'></font></h2><center><br>");
                        writer.println(TAG_TD);
                        writer.println("<td><img src='data:image/jpeg;base64," + readFileFromResources("imagens/ImagemReportRight.txt") + "'/></td>");
                        writer.println(TAG_TR);
                        writer.println(TAG_TABLE);
                        writer.println("</p>");
                        writer.println("<center><h5>Cenário: <font color='#00AA00'>" + nomeCenario + "</font><center><br>"); //Nova Linha
                        // INICIO BLOCO CUSTOMIZACAO PARA AMBIENTE
                        writer.println("<table border=5 colspan=5 >");
                        writer.println(TAG_EVIDENCIA);
                        writer.println("<td class='evidencia' colspan=4 ><b><center>AMBIENTE</center></b></td></tr><tr>");
                        writer.println(TAG_EVIDENCIA);
                        writer.println("<td class='evidencia' colspan=3  > BROWSER: </td><td class='evidencia' style='width:470px' >Chorme</td></tr><tr>");
                        writer.println(TAG_EVIDENCIA);
                        writer.println("<td class='evidencia' colspan=3 > SISTEMA OPERACIONAL: </td><td class='evidencia' >" + System.getProperty("os.name") + "</td></tr><tr>");
                        writer.println(TAG_EVIDENCIA);
                     //   writer.println("<td class='evidencia' colspan=3 > AMBIENTE: </td><td class='evidencia' >" + System.getProperty("env").toUpperCase() + "</td></tr><tr>");
                        writer.println(TAG_EVIDENCIA);
                        writer.println(TAG_EVIDENCIA);
                        // FIM BLOCO CUSTOMIZACAO PARA AMBIENTE
                        writer.println("<table class='evidencia' >");
                        writer.println(TAG_EVIDENCIA);
                        writer.println("<td class='evidencia' colspan=4 ><b><center>" + titulo + "</center></b></td>");
                        writer.println(TAG_TR);
                        writer.println("<tr>");
                        writer.println("<td class='evidencia' > PASSO: </td><td class='evidencia' >" + reportDTO.getSteps() + TAG_TD);
                        writer.println("<td class='evidencia' > DATA/HORA EXECUÇÃO: </td><td class='evidencia' >" + timeStampEvd() + TAG_TD);
                        writer.println(TAG_TR);
                        writer.println("<tr>");
                        writer.println("<td class='evidencia' > AÇÃO: </td><td class='evidencia' colspan=3 >" + acao + TAG_TD);
                        writer.println(TAG_TR);
                        writer.println("<tr>");
                        writer.println("<td class='evidencia' > STATUS: </td><td class='evidencia' ><b class='" + reportDTO.getStatus() + "' >" + reportDTO.getStatus() + TAG_B);
                        writer.println("<td class='evidencia' > Responsável: </td><td class='evidencia' >" + RESPONSAVEL + TAG_TD);
                        writer.println(TAG_TR);
                        writer.println("<tr>");

                        if (reportDTO.getStatus().equals("FAILED")) {
                            writer.println("<tr>");
                            writer.println("<td class='evidencia' > ERRO: </td><td class='evidencia' ><b class='" + reportDTO.getType() + "' >" + reportDTO.getType() + TAG_B);
                            writer.println("<td class='evidencia' > Detalhe do Erro: </td><td class='evidencia' >" + reportDTO.getDetails() + TAG_TD);
                            writer.println(TAG_TR);
                            writer.println("<tr>");
                            writer.println("<td class='evidencia' > ELEMENTO: </td><td class='evidencia' colspan=3 >" + reportDTO.getElement() + TAG_TD);
                            writer.println(TAG_TR);

                        }
                        writer.println("<tr>");
                        writer.println("<td colspan=4 class='evidencia' ><center>EVIDENCIA</center></td>");
                        writer.println(TAG_TR);
                        writer.println("<tr>");
                        writer.println("<td colspan=4 class='evidencia' ><img src='" + imagem + "' style= 'max-width: 100%;'width:1460px;height:1080px;' /></td>");
                        writer.println(TAG_TR);
                        writer.println(TAG_TABLE);
                        writer.println("</p>");
                        writer.flush();

                    }
                    reportDTO.setNewScenario(Boolean.FALSE);
                } else {
                    try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(pathReport(), true)))) {
                        writer.println("</p>");
                        writer.println("<meta http-equiv=\"Content-Type\"content=\"text/html;charset=UTF-8\">");
                        writer.println("<table class='evidencia' >");
                        writer.println(TAG_EVIDENCIA);
                        writer.println("<td class='evidencia' colspan=4 > <center><b class='" + titulo + "' >" + titulo + "</center></b></td>");
                        writer.println(TAG_TR);
                        writer.println("<tr>");
                        writer.println("<td class='evidencia' > PASSO: </td><td class='evidencia' >" + reportDTO.getSteps() + TAG_TD);
                        writer.println("<td class='evidencia' > DATA/HORA EXECUÇÃO: </td><td class='evidencia' >" + timeStampEvd() + TAG_TD);
                        writer.println(TAG_TR);
                        writer.println("<tr>");
                        writer.println("<td class='evidencia' > AÇÃO: </td><td class='evidencia' colspan=3 >" + acao + TAG_TD);
                        writer.println(TAG_TR);
                        writer.println("<tr>");
                        writer.println("<td class='evidencia' > STATUS: </td><td class='evidencia' ><b class='" + reportDTO.getStatus() + "' >" + reportDTO.getStatus() + TAG_B);
                        writer.println("<td class='evidencia' > Responsável: </td><td class='evidencia' >" + RESPONSAVEL + TAG_TD);
                        writer.println(TAG_TR);
                        if (reportDTO.getStatus().equals("FAILED")) {
                            writer.println("<tr>");
                            writer.println("<td class='evidencia' > ERRO: </td><td class='evidencia' ><b class='" + reportDTO.getType() + "' >" + reportDTO.getType() + TAG_B);
                            writer.println("<td class='evidencia' > Detalhe do Erro: </td><td class='evidencia' >" + reportDTO.getDetails() + TAG_TD);
                            writer.println(TAG_TR);
                            writer.println("<tr>");
                            writer.println("<td class='evidencia' > ELEMENTO: </td><td class='evidencia' colspan=3 >" + reportDTO.getElement() + TAG_TD);
                            writer.println(TAG_TR);

                        }
                        writer.println("<tr>");
                        writer.println("<td colspan=4 class='evidencia' ><center>EVIDENCIA</center></td>");
                        writer.println(TAG_TR);
                        writer.println("<tr>");
                        writer.println("<td colspan=4 class='evidencia' ><img src='" + imagem + "' style= 'max-width: 100%;'width:1460px;height:1080px;' /></td>");
                        writer.println(TAG_TR);
                        writer.println(TAG_TABLE);
                        writer.println("</p>");
                        writer.flush();

                    }
                }
                reportDTO.setStatus("PASSED");
                reportDTO.setSteps(passo++);
                log.log(Level.INFO, () -> "[Passo evidenciado: " + acao + " ]");
            } catch (Exception e) {
                log.info(e.getMessage());

            }

        }

    }

    public static void configParametsReport(TestInfo info) {
        nomeCenario = info.getDisplayName();
        Optional<Method> optional = info.getTestMethod();
        optional.ifPresent(method -> nomeTeste = method.getName()+info.getDisplayName().substring(0,3));
        reportDTO.setNewScenario(Boolean.TRUE);

    }

    @SneakyThrows
    public static String readFileFromResources(String fileName) {
        URL url = Resources.getResource(fileName);
        return Resources.toString(url, StandardCharsets.UTF_8);
    }
}