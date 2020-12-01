package importacaoExcelPadrao;

import Entity.Executavel;
import Robo.AppRobo;
import TemplateContabil.Control.ControleTemplates;
import TemplateContabil.Model.Entity.Importation;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class importacaoExcelPadrao {

    private static String nomeApp = "";

    public static void main(String[] args) {
        try {
            AppRobo robo = new AppRobo(nomeApp);

            //String parametros = "[mes:04][ano:2019][codigoEmpresa:722][nomePastaEmpresa:Vinicius Dalcin][nomeApp:Vinicius Dalcin Importação][banco:Banco do Brasil#1#001|.ofx#copia]";
            //robo.definirParametros(parametros);
            robo.definirParametros();

            String pastaEmpresa = robo.getParametro("pastaEmpresa").getString();
            String pastaAnual = robo.getParametro("pastaAnual").getString();
            String pastaMensal = robo.getParametro("pastaMensal").getString();
            String banco = robo.getParametro("banco").getString();
            String idTemplate = robo.getParametro("idTemplate").getString();
            String filtroArquivo = robo.getParametro("filtroArquivo").getString();

            Map<String, String> colunas = new HashMap<>();
            colunas.put("data", robo.getParametro("colunaData").getString());
            colunas.put("documento", robo.getParametro("colunaDocumento").getString());
            colunas.put("pretexto", robo.getParametro("colunaPreTexto").getString());
            colunas.put("data", robo.getParametro("colunaHistorico").getString());
            colunas.put("data", robo.getParametro("colunaEntrada").getString());
            colunas.put("data", robo.getParametro("colunaSaida").getString());
            colunas.put("data", robo.getParametro("colunaValor").getString());

            int mes = robo.getParametro("mes").getMes();
            int ano = robo.getParametro("ano").getInteger();
            nomeApp = "Importação " + pastaEmpresa + " - " + banco;

            robo.setNome(nomeApp);
            robo.executar(
                    principal(mes, ano, pastaEmpresa, pastaAnual, pastaMensal, banco, idTemplate, filtroArquivo, colunas)
            );
        } catch (Exception e) {
            System.out.println("Ocorreu um erro na aplicação: " + e);
            System.exit(0);
        }
    }

    public static String principal(int mes, int ano, String pastaEmpresa, String pastaAnual, String pastaMensal, String banco, String idTemplate, String filtroArquivo, Map<String, String> colunas) {
        try {
            Importation importation = new Importation(Importation.TIPO_EXCEL);
            importation.setIdTemplateConfig(idTemplate);
            importation.getExcelCols().putAll(colunas);
            importation.setNome(banco);

            ControleTemplates controle = new ControleTemplates(mes, ano);
            controle.setPastaEscMensal(pastaEmpresa);
            controle.setPasta(pastaAnual, pastaMensal);

            Map<String, Executavel> execs = new LinkedHashMap<>();
            execs.put("Procurando arquivo", controle.new defineArquivoNaImportacao(filtroArquivo,importation));
            execs.put("Criando template", controle.new defineArquivoNaImportacao(filtroArquivo,importation));

            return AppRobo.rodarExecutaveis(nomeApp, execs);
        } catch (Exception e) {
            return "Ocorreu um erro no Java: " + e;
        }
    }

}
