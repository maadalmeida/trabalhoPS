package ps.processadormacro;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PM {
    //suporte a .SER
        //-pode não funcionar quando label é passado como parâmetro
    //suporte a redefinição de macro
    //conflitos de sequências de caracteres resolvidos
    //substituição de todas as ocorrências de \t por espaço em branco
    //suporte a definições de macro aninhadas
        //-testar modoExpansao
    static ArrayList<String> linhas = new ArrayList<>();
    static TDM tabela = new TDM();
    
    public static void main(String[] args) throws IOException {
        FileReader rArquivo = new FileReader("entrada.asm");
        BufferedReader rBuffer = new BufferedReader(rArquivo);
        FileWriter wArquivo = new FileWriter("saida.asm");
        BufferedWriter wBuffer = new BufferedWriter(wArquivo);
        
        int cont, auxChamada;
        for(cont = 0; rBuffer.ready(); cont++){
            linhas.add(rBuffer.readLine());
            linhas.set(cont, linhas.get(cont).replace("\t", " "));
            if(verificacaoDefinicao(cont)) {
                cont = modoDefinicao(rBuffer, cont);
            } else if((auxChamada = verificacaoChamada(cont)) >= 0) {
                modoExpansao(wBuffer, auxChamada, cont);
            } else {
                modoCopia(wBuffer, cont);
            }
        }
        
        rBuffer.close();
        rArquivo.close();
        wBuffer.close();
        wArquivo.close();
    }
    
    private static boolean verificacaoDefinicao(int indexLinha) {
        return linhas.get(indexLinha).contains("MACRO");
    }
    
    private static int verificacaoChamada(int indexLinha) {
        String temp[] = linhas.get(indexLinha).split(" ");
        int cont, contMacro, numeroMacrosDefinidas = tabela.getNumeroMacrosDefinidas();
        
        for(contMacro = numeroMacrosDefinidas - 1; contMacro >= 0; contMacro--) {                   //caso haja redefinição, identifica primeiro a definição mais recente
            for(cont = 0; (cont < 2) && (cont < temp.length); cont++) {                             //caso contenha label na linha de chamada
                if(temp[cont].contentEquals(tabela.getNomeMacro(contMacro))){                       //equalsIgnoreCase também funciona
                    return contMacro;                                                               //retorna index da macro chamada
                }
            }
        }
        return -1;
    }
    
    private static int modoDefinicao(BufferedReader rBuffer, int indexLinha) throws IOException {
        String temp = linhas.get(indexLinha);
        int contAninhamento = 0;
        boolean macroInterna = false;
        tabela.setPrototipoMacro(temp);
        
        do {
            if(linhas.get(indexLinha).contains("ENDM") && macroInterna) {
                contAninhamento--;
                if(contAninhamento == 0) {
                    macroInterna = false;
                }
            }
            linhas.add(rBuffer.readLine());
            indexLinha++;
            linhas.set(indexLinha, linhas.get(indexLinha).replace("\t", " "));
            if(linhas.get(indexLinha).contains("MACRO")) {
                contAninhamento++;
                macroInterna = true;
            }
            tabela.setDefinicaoMacro(linhas.get(indexLinha), macroInterna);
        } while(!linhas.get(indexLinha).contains("ENDM") || contAninhamento != 0);
        
        tabela.limpaParametros();
        
        return indexLinha;
    }
    
    private static void modoExpansao(BufferedWriter wBuffer, int indexPrototipo, int indexLinha) throws IOException {
        ArrayList<String> parametrosReais = new ArrayList<>();
        parametrosReais.addAll(tabela.getParametrosChamada(indexPrototipo, indexLinha));
        tabela.limpaParametros();
        String temp;
        int contLinhas, contParametrosReais, contAninhamento = 0;
        boolean macroInterna = false;
        tabela.setNumeroExpansoes(indexPrototipo, tabela.getNumeroExpansoes(indexPrototipo) + 1);
        
        for(contLinhas = tabela.getContador(indexPrototipo); !tabela.getLinhaDefinicao(contLinhas).contains("ENDM") || macroInterna; contLinhas++) {
            temp = tabela.getLinhaDefinicao(contLinhas);
            
            if(temp.contains("MACRO")) {
                contAninhamento++;
                macroInterna = true;
                tabela.setPrototipoMacro(temp);
            } else if(temp.contains("ENDM") && contAninhamento != 0) {
                contAninhamento--;
                if(contAninhamento == 0) {
                    macroInterna = false;
                    tabela.limpaParametros();
                }
                tabela.setDefinicaoMacro(temp, macroInterna);
            } else {
                for(contParametrosReais = 0; contParametrosReais < parametrosReais.size(); contParametrosReais++) {
                    if(temp.contains("#" + contParametrosReais)) {
                        temp = temp.substring(0, temp.indexOf("#" + contParametrosReais)).concat(parametrosReais.get(contParametrosReais) + temp.substring(temp.indexOf("#" + contParametrosReais) + 2));
                        contParametrosReais--;
                    }
                }
                if(macroInterna) {
                    tabela.setDefinicaoMacro(temp, macroInterna);
                } else {
                    if(contLinhas == tabela.getContador(indexPrototipo) && linhas.get(indexLinha).trim().indexOf(tabela.getNomeMacro(indexPrototipo)) > 0) {
                        temp = linhas.get(indexLinha).substring(0, linhas.get(indexLinha).indexOf(tabela.getNomeMacro(indexPrototipo))).concat(temp);
                    } else if(temp.contains(".SER")) {
                       temp = temp.substring(0, temp.indexOf(".SER")).concat("00" + tabela.getNumeroExpansoes(indexPrototipo) + temp.substring(temp.indexOf(".SER") + 4));
                    }
                    wBuffer.write(temp, 0, temp.length());
                    wBuffer.newLine();
                }
            }
        }
        
        tabela.limpaParametros();
    }
    
    private static void modoCopia(BufferedWriter wBuffer, int cont) throws IOException {
        wBuffer.write(linhas.get(cont), 0, linhas.get(cont).length());
        wBuffer.newLine();
    }
}
