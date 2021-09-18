/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.processadormacro;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import static ps.processadormacro.PM.linhas;
import static ps.processadormacro.PM.tabela;


public class JFramePM extends javax.swing.JFrame {

    public JFramePM() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Processador de Macro - Grupo 100");

        jButton1.setText("Abrir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Arquivo de saída:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextField1.setText("Local do Arquivo");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Salvo no arquivo:");

        jTextField2.setText("Local do Arquivo de Saída");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jMenu1.setText("Arquivo");

        jMenuItem3.setText("Sair");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Sobre");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        jMenuItem4.setText("Informações");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
                    .addComponent(jTextField1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(1, 1, 1)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File(""));
        chooser.setFileFilter(new FileNameExtensionFilter("asm","ASM"));
        int value = chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();

        try{
            FileReader rArquivo = new FileReader(filename);
            BufferedReader rBuffer = new BufferedReader(rArquivo);
            FileWriter wArquivo = new FileWriter("saida.asm");
            BufferedWriter wBuffer = new BufferedWriter(wArquivo);
            jTextField1.setText(filename);
            jTextField2.setText("saida.asm");
            
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
            FileReader saida = new FileReader("saida.asm");
            BufferedReader saidaBuffer = new BufferedReader(saida);
            jTextArea1.read(saidaBuffer,null);
            saidaBuffer.close();
            jTextArea1.requestFocus();
        }catch(IOException e){
            JOptionPane.showMessageDialog(null,e);
        }
        this.remove(jButton1);
        this.repaint();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        JOptionPane.showMessageDialog(null, "Universidade Federal de Pelotas - 2021/1 \n" + "Trabalho de Programação de Sistemas - Grupo 100 \n" + "\n" + "18200581 THALIA DJUNE COSTA LONGARAY\n" +
            "14101919 LUCAS BRAATZ ARAUJO\n" +
            "19100900 ALEJANDRO TOMAS REYES ALBERONI\n" +
            "14200894 RAFAEL DA SILVA MARTINS\n" +
            "17200154 JOAZ FERNANDO BASTOS DA SILVA FILHO\n" +
            "11108244 MATEUS AL ALAM DE ALMEIDA\n" +
            "18101409 CLEBER FARIAS BERNDSEN JUNIOR");
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFramePM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFramePM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFramePM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFramePM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFramePM().setVisible(true);
            }
        });
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
