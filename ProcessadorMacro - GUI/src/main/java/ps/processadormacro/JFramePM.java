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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
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
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        JOptionPane.showMessageDialog(null, "Universidade Federal de Pelotas - 2021/2 \n" + "Trabalho de Programação de Sistemas - Grupo 100 \n" + "\n" + "18200581 THALIA DJUNE COSTA LONGARAY\n" +
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
