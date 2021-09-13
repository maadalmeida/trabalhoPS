package ps.processadormacro;
import java.util.ArrayList;
import static ps.processadormacro.PM.linhas;

public class TDM {
    private ArrayList<String> prototipos, definicoes, parametros;
    private ArrayList<Integer> contador;
   
    public TDM() {
        this.contador = new ArrayList<>();
        this.definicoes = new ArrayList<>();
        this.parametros = new ArrayList<>();
        this.prototipos = new ArrayList<>();
    }
    
    protected void setPrototipoMacro(String prototipo, int aux) {
        prototipos.add(prototipo.substring(0, aux).concat(prototipo.substring(aux + 6)));
        contador.add(definicoes.size());
        setParametros(prototipo.substring(aux + 6));
    }

    protected void setParametros(String prototipo) {
        int aux = prototipo.indexOf(";"), auxCorte, cont;
        if(aux != -1) {
            prototipo = prototipo.substring(0, aux);
        } else {
            aux = prototipo.length();
        }
        
        for(cont = 0; cont < aux; cont++) {
            if(prototipo.contains(",")) {
                parametros.add(prototipo.substring(0, prototipo.indexOf(",")));
                auxCorte = prototipo.indexOf(",");
                cont += auxCorte - 1;
                prototipo = prototipo.substring(auxCorte);
                while(prototipo.indexOf(",") == 0 || prototipo.indexOf(" ") == 0) {
                    cont++;
                    prototipo = prototipo.substring(1);
                }
            } else if(prototipo.contains(" ")) {
                parametros.add(prototipo.substring(0, prototipo.indexOf(" ")));
                auxCorte = prototipo.indexOf(" ");
                cont += auxCorte - 1;
                prototipo = prototipo.substring(auxCorte);
                while(prototipo.indexOf(",") == 0 || prototipo.indexOf(" ") == 0) {
                    cont++;
                    prototipo = prototipo.substring(1);
                }
            } else if(prototipo.length() != 0) {
                parametros.add(prototipo);
                cont = aux;
            }
        }
    }
    
    protected void setDefinicaoMacro(int indexLinha) {
        String temp = linhas.get(indexLinha);
        int cont, aux = 0;
        for(cont = 0; cont < parametros.size(); cont++) {
            if(temp.contains(parametros.get(cont))) {
                aux = temp.indexOf(parametros.get(cont), aux);
                if((aux > 0 && (temp.length() - aux > parametros.get(cont).length()) &&
                        (temp.substring(aux - 1, aux + parametros.get(cont).length() + 1).contains(" " + parametros.get(cont) + " ") ||
                        temp.substring(aux - 1, aux + parametros.get(cont).length() + 1).contains("," + parametros.get(cont) + " ") ||
                        temp.substring(aux - 1, aux + parametros.get(cont).length() + 1).contains(" " + parametros.get(cont) + ",") ||
                        temp.substring(aux - 1, aux + parametros.get(cont).length() + 1).contains("," + parametros.get(cont) + ","))) ||
                        (aux == 0 && (temp.length() - aux > parametros.get(cont).length()) &&
                        (temp.substring(aux, parametros.get(cont).length() + 1).contains(parametros.get(cont) + " ") ||
                        temp.substring(aux, parametros.get(cont).length() + 1).contains(parametros.get(cont) + ",")))) {
                    temp = temp.substring(0, aux).concat("#" + cont + temp.substring(aux + parametros.get(cont).length()));
                    cont--;
                } else if((aux > 0 && (temp.length() - aux == parametros.get(cont).length()) &&
                        (temp.substring(aux - 1).contains(" " + parametros.get(cont)) ||
                        temp.substring(aux - 1).contains("," + parametros.get(cont)))) ||
                        (aux == 0 && (temp.length() - aux == parametros.get(cont).length()) &&
                        temp.substring(aux).contains(parametros.get(cont)))) {
                    temp = temp.substring(0, aux).concat("#" + cont);
                } else if(aux >= 0) {
                    aux += parametros.get(cont).length();
                    cont--;
                }
                
                
            }
        }
        definicoes.add(temp);
    }
    
    protected void limpaParametros() {
        parametros.clear();
    }
    
    protected int getNumeroMacrosDefinidas() {
        return prototipos.size();
    }
    
    protected String getNomeMacro(int index) {
        int aux = prototipos.get(index).indexOf(" ") - 1;
        return prototipos.get(index).substring(0, aux);
    }
    
    protected ArrayList<String> getParametrosChamada(int indexChamada) {
        setParametros(linhas.get(indexChamada).substring(linhas.get(indexChamada).indexOf(" ") + 1));
        return parametros;
    }
    
    protected int getContador(int indexPrototipo) {
        return contador.get(indexPrototipo);
    }
    
    protected String getLinhaDefinicao(int indexLinha) {
        return definicoes.get(indexLinha);
    }
}