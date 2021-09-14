package ps.processadormacro;
import java.io.IOException;
import java.util.ArrayList;

public class PM {
    static ArrayList<String> linhas = new ArrayList<>();
    static TDM tabela = new TDM();
    
    public static void main(String[] args) throws IOException {
        JFramePM janela = new JFramePM();
        janela.setVisible(true);
    }
}