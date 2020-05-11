package teste;

import importacaoExcelPadrao.importacaoExcelPadrao;

public class teste {

    public static void main(String[] args) {
        int mes = 4;
        int ano = 2019;
        Integer nroBanco = 1;
        String banco = "Banco do Brasil";
        String filtroBanco = "001";
        
        String colunaData = "A";
        String colunaDoc = "B";
        String colunaPretexto = "C";
        String colunaHistorico = "D";
        String colunaValor = "E;-F";
        
        System.out.println(importacaoExcelPadrao.principal(mes, ano, nroBanco, banco, filtroBanco, "",colunaData, colunaDoc, colunaPretexto, colunaHistorico, colunaValor).replaceAll("<br>", "\n"));
    }
    
}
