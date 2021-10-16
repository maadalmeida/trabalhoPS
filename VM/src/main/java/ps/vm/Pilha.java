package ps.vm;

import java.util.ArrayList;
import static ps.vm.VM.mem;

public class Pilha {
    private ArrayList<String> linhas;
    
    public Pilha() {
        this.linhas = new ArrayList<>();
    }
    
    protected boolean push(String n) {
        int temp = linhas.size();
        if(getEndTopoPilha() == 0) {
            return false;                                                       //pilha cheia
        }
        linhas.add(n);
        if(linhas.get(temp).equals(n)) {
            mem.pushPilha(n);
            return true;
        }
        return false;
    }
    
    protected String pop() {
        if(linhas.size() <= 0) {                                                //se a pilha está vazia
            return null;
        }
        int temp = linhas.size() - 1;                                           //p/ usar como index da última posição ocupada
        String linhaTemp = linhas.remove(temp);                                 //copia linha removida da última posição
        if(temp == linhas.size()) {                                             //se tamanho atual é igual ao tamanho inicial - 1
            mem.popPilha();
            return linhaTemp;                                                   //retorna linha removida
        }
        return null;
    }
    
    protected int getEndTopoPilha() {
        return mem.getEndTopoPilha();
    }
}