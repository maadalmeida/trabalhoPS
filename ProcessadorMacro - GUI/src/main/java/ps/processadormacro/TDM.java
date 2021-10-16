package ps.processadormacro;
import java.util.ArrayList;
import static ps.processadormacro.PM.linhas;

public class TDM {
    private ArrayList<String> prototipos, definicoes, parametros, prototiposMacrosInternas, parametrosMacrosInternas;
    //prototipos -> armazena os prototipos das macros definidas
    //definicoes -> armazena as linhas das definições das macros
    //parametros -> array de uso temporário para auxiliar nas substituições de parâmetros, tanto formais quanto reais
    //prototiposMacrosInternas, parametrosMacrosInternas -> arrays auxiliares para garantir processamento correto das definições aninhadas
    private ArrayList<Integer> contador, numeroExpansoes;
    //contador -> armazena o número referente a posição inicial de cada definição de macro no array definicoes
    //numeroExpansoes -> armazena o número referente a quantidade de expansões que uma macro já teve
   
    public TDM() {
        this.contador = new ArrayList<>();
        this.definicoes = new ArrayList<>();
        this.parametros = new ArrayList<>();
        this.prototipos = new ArrayList<>();
        this.numeroExpansoes = new ArrayList<>();
        this.prototiposMacrosInternas = new ArrayList<>();
        this.parametrosMacrosInternas = new ArrayList<>();
    }
    
    protected void setPrototipoMacro(String prototipo) {
    //adiciona o protótipo da macro no array de prototipos
        prototipos.add(prototipo.substring(0, prototipo.indexOf("MACRO")).concat(prototipo.substring(prototipo.indexOf("MACRO") + 6)));
        contador.add(definicoes.size());
        numeroExpansoes.add(0);
        setParametros(prototipo.substring(prototipo.indexOf("MACRO") + 6));
    }
    
    private void setParametros(String particaoLinha) {
    //método auxiliar de setPrototipoMacro e getParametrosChamada
    //adiciona todos os parametros identificados no array parametros
        String parametrosLinha[];
        int aux = particaoLinha.indexOf(";"), cont;
        if(aux != -1) {
            particaoLinha = particaoLinha.substring(0, aux);
        }
        parametrosLinha = particaoLinha.split(",");
        
        for(cont = 0; cont < parametrosLinha.length; cont++) {
            if(parametrosLinha[cont].length() > 1 && parametrosLinha[cont].contains(" ")) {
                parametrosLinha[cont] = parametrosLinha[cont].trim();
            }
            parametros.add(parametrosLinha[cont]);
        }
    }
    
    protected void setDefinicaoMacro(String linha, boolean macroInterna) {
    //adiciona linha no array de definições de macro
        String temp = linha;
        if(temp.contains(";")) {
            temp = temp.substring(0, temp.indexOf(";"));
        }

        int cont;
        if(macroInterna) {
            if(temp.contains("ENDM")) {                                         //finaliza macro interna
                definicoes.add(temp);

                temp = prototiposMacrosInternas.get(prototiposMacrosInternas.size() - 1);
                String parametrosMacroInterna[];
                parametrosMacroInterna = temp.substring(temp.indexOf(" ")).split(",");
                for(cont = 0; cont < parametrosMacroInterna.length; cont++) {
                    parametrosMacrosInternas.remove(parametrosMacrosInternas.size() - 1);
                }
                prototiposMacrosInternas.remove(prototiposMacrosInternas.size() - 1);
            } else if(temp.contains("MACRO")) {                                 //abre macro interna
                definicoes.add(temp);
                
                prototiposMacrosInternas.add(temp.substring(0, temp.indexOf("MACRO")).concat(temp.substring(temp.indexOf("MACRO") + 6)));
                temp = temp.substring(temp.indexOf("MACRO") + 6);
                String parametrosMacroInterna[] = temp.split(",");
                parametrosMacroInterna = limpaEspacosParametros(parametrosMacroInterna);
                for(cont = 0; cont < parametrosMacroInterna.length; cont++) {
                    parametrosMacrosInternas.add(parametrosMacroInterna[cont]);
                }
            } else {                                                            //adiciona linha macro interna
                String parametrosLinha[];
                int contParametrosFormais, contParametrosMacrosInternas;
                boolean substituir;

                if(!temp.trim().contains(" ")) {                                //string única
                    for(contParametrosFormais = 0; contParametrosFormais < parametros.size(); contParametrosFormais++) {
                        substituir = true;
                        for(contParametrosMacrosInternas = 0; contParametrosMacrosInternas < parametrosMacrosInternas.size(); contParametrosMacrosInternas++) {
                            if(parametrosMacrosInternas.get(contParametrosMacrosInternas).contentEquals(temp.trim())) {
                                substituir = false;
                            }
                        }
                        if(substituir && parametros.get(contParametrosFormais).contentEquals(temp.trim())) {
                            int j = indexOcorrenciaParametro(temp, temp.trim());
                            temp = temp.substring(0, temp.indexOf(parametros.get(contParametrosFormais), j)).concat("#" + contParametrosFormais + 
                                    temp.substring(temp.indexOf(parametros.get(contParametrosFormais), j) + temp.trim().length()));
                        }
                    }
                } else {
                    parametrosLinha = temp.replace(",", " ").split(" ");
                    parametrosLinha = limpaEspacosParametros(parametrosLinha);
                    
                    for(contParametrosFormais = 0; contParametrosFormais < parametros.size(); contParametrosFormais++) {
                        substituir = true;
                        for(cont = 0; cont < parametrosLinha.length; cont++) {
                            for(contParametrosMacrosInternas = 0; contParametrosMacrosInternas < parametrosMacrosInternas.size(); contParametrosMacrosInternas++) {
                                if(parametrosMacrosInternas.get(contParametrosMacrosInternas).contentEquals(parametrosLinha[cont])) {
                                    substituir = false;
                                }
                            }
                            if(substituir && parametros.get(contParametrosFormais).contentEquals(parametrosLinha[cont])) {
                                int j = indexOcorrenciaParametro(temp, parametrosLinha[cont]);
                                temp = temp.substring(0, temp.indexOf(parametros.get(contParametrosFormais), j)).concat("#" + contParametrosFormais + 
                                        temp.substring(temp.indexOf(parametros.get(contParametrosFormais), j) + parametrosLinha[cont].length()));
                            }
                        }
                    }
                }
                definicoes.add(temp);
            }
        } else {
            if(!temp.contains("ENDM")) {
                String parametrosLinha[];
                int contParametrosFormais;

                if(!temp.trim().contains(" ")) {                                //string única
                    for(contParametrosFormais = 0; contParametrosFormais < parametros.size(); contParametrosFormais++) {
                        if(parametros.get(contParametrosFormais).contentEquals(temp.trim())) {
                            int j = indexOcorrenciaParametro(temp, temp.trim());
                            temp = temp.substring(0, temp.indexOf(parametros.get(contParametrosFormais), j)).concat("#" + contParametrosFormais + 
                                    temp.substring(temp.indexOf(parametros.get(contParametrosFormais), j) + temp.trim().length()));
                        }
                    }
                } else {
                    parametrosLinha = temp.replace(",", " ").split(" ");
                    parametrosLinha = limpaEspacosParametros(parametrosLinha);

                    for(contParametrosFormais = 0; contParametrosFormais < parametros.size(); contParametrosFormais++) {
                        for(cont = 0; cont < parametrosLinha.length; cont++) {
                            if(parametros.get(contParametrosFormais).contentEquals(parametrosLinha[cont])) {
                                int j = indexOcorrenciaParametro(temp, parametrosLinha[cont]);
                                temp = temp.substring(0, temp.indexOf(parametros.get(contParametrosFormais), j)).concat("#" + contParametrosFormais + 
                                        temp.substring(temp.indexOf(parametros.get(contParametrosFormais), j) + parametrosLinha[cont].length()));
                            }
                        }
                    }
                }
            }
            definicoes.add(temp);
        }
    }

    private int indexOcorrenciaParametro(String linha, String parametro) {
    //método auxiliar de setDefinicaoMacro
    //retorna a posição da linha a partir da qual o parâmetro pode ocorrer
        int i, j = 0;
        String s = linha.replace(",", " ");
        for(i = 0; i < s.split(" ").length; i++) {
            if(s.split(" ")[i].contentEquals(parametro)) {
                break;
            }
            j += s.split(" ")[i].length() + 1;
        }
        return j;
    }
    
    protected void setNumeroExpansoes(int indexPrototipo, int numero) {
    //atualiza posição do array numeroExpansoes
        numeroExpansoes.set(indexPrototipo, numero);
    }
    
    protected int getNumeroExpansoes(int indexPrototipo) {
    //retorna número de expansões da macro correspondente
        return numeroExpansoes.get(indexPrototipo);
    }
    
    protected int getNumeroMacrosDefinidas() {
    //retorna número de macros já definidas
        return prototipos.size();
    }
    
    protected String getNomeMacro(int index) {
    //retorna nome da macro correspondente, sem delimitador e sem parâmetros
        return prototipos.get(index).substring(0, prototipos.get(index).indexOf(" "));
    }
    
    protected ArrayList<String> getParametrosChamada(int indexPrototipo, int indexChamada) {
    //identifica os parametros reais de uma linha de chamada
        String nomeMacro = getNomeMacro(indexPrototipo), chamada = linhas.get(indexChamada);
        setParametros(chamada.substring(chamada.indexOf(nomeMacro) + nomeMacro.length() + 1));
        return parametros;
    }
    
    protected int getContador(int indexPrototipo) {
    //retorna número referente a posição inicial da definição da macro no array definições
        return contador.get(indexPrototipo);
    }
    
    protected String getLinhaDefinicao(int indexLinha) {
    //retorna a linha do array de definições
        return definicoes.get(indexLinha);
    }
    
    protected void limpaParametros() {
    //limpa o array parametros
        parametros.clear();
    }
    
    private String[] limpaEspacosParametros(String[] parametros) {
    //método auxiliar de setDefinicaoMacro
    //limpa espaços extras
        int cont;
        for(cont = 0; cont < parametros.length; cont++) {
            if(parametros[cont].length() > 1 && parametros[cont].contains(" ")) {
                parametros[cont] = parametros[cont].trim();
            }
        }
        return parametros;
    }
}
