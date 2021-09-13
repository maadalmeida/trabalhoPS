package ps.processadormacro;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PM {
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
        String temp = linhas.get(indexLinha);
        int cont, numeroMacrosDefinidas = tabela.getNumeroMacrosDefinidas();
        for(cont = 0; cont < numeroMacrosDefinidas; cont++) {
            if(temp.contains(tabela.getNomeMacro(cont))) {
                return cont;
            }
        }
        return -1;
    }
    
    private static int modoDefinicao(BufferedReader buffer, int indexLinha) throws IOException {
        //incompleto
        String temp = linhas.get(indexLinha);
        int auxDelimitador = temp.indexOf("MACRO");
        tabela.setPrototipoMacro(temp, auxDelimitador);
        
        do {
            linhas.add(buffer.readLine());
            indexLinha++;
            tabela.setDefinicaoMacro(indexLinha);
        } while(!linhas.get(indexLinha).contains("ENDM"));
        
        tabela.limpaParametros();
        
        return indexLinha;
    }
    
    private static void modoExpansao(BufferedWriter buffer, int indexPrototipoMacro, int indexLinha) throws IOException {
        ArrayList<String> parametrosReais = tabela.getParametrosChamada(indexLinha);
        String temp;
        int contLinha, contParametro;
        for(contLinha = tabela.getContador(indexPrototipoMacro); !tabela.getLinhaDefinicao(contLinha).contains("ENDM"); contLinha++) {
            temp = tabela.getLinhaDefinicao(contLinha);
            for(contParametro = 0; contParametro < parametrosReais.size(); contParametro++) {
                if(temp.contains("#" + contParametro)) {
                    temp = temp.substring(0, temp.indexOf("#" + contParametro)).concat(parametrosReais.get(contParametro) + temp.substring(temp.indexOf("#" + contParametro) + 2));
                }
            }
            buffer.write(temp, 0, temp.length());
            buffer.newLine();
        }
        tabela.limpaParametros();
    }
    
    private static void modoCopia(BufferedWriter buffer, int indexLinha) throws IOException {
        buffer.write(linhas.get(indexLinha), 0, linhas.get(indexLinha).length());
        buffer.newLine();
    }
}