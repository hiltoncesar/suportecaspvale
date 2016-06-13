/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames.Acoes;

import controle.SuporteHistoricoChatJpaController;
import controle.SuporteUsuariosJpaController;
import entidades.SuporteHistoricoChat;
import entidades.SuporteUsuarios;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author servbeta11
 */
public final class Chat extends javax.swing.JFrame {

    private SuporteUsuariosJpaController emfUsuario = new SuporteUsuariosJpaController(new util.Persistencia().emf());
    private  SuporteHistoricoChatJpaController emfHistChat= new SuporteHistoricoChatJpaController(new util.Persistencia().emf());
    private static int i_usuario = 1;
    public Thread t;

    public Chat(int i_usuario) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagens/chat2_64.png")));
        this.i_usuario = i_usuario;
        tabelaUsuarios();
        //ler();
        dateChooser.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        jTusuarios.setRowSelectionInterval(0, 0);
        t = new Thread(new Tempo_historico());
        t.start();
    }

    public DefaultTableModel tabelaUsuarios() {
        //<editor-fold defaultstate="collapsed" desc="Manipulação da Tabela">
        /* Método controlador da tabela*/

        //int iFun = id_funcionario[fu.getSelectedIndex()];
        DefaultTableModel modelo = new DefaultTableModel(null, new String[]{
            "CÓDIGO", "NOME", "STATUS", "MENSAGENS"}) {
            @Override
            public boolean isCellEditable(int a, int b) {
                return false;
            }
        };
        String[] linha = new String[]{null, null, null, null};
        int linhas = modelo.getRowCount();

        List<SuporteUsuarios> listaUsuarios = null;
        try {
            listaUsuarios = emfUsuario.findSuporteUsuariosEntitiesOrdenado();
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Erro ao recuperar informações da tabela\n"
                    + ex.getCause().getMessage(), "Erro de Conexão", javax.swing.JOptionPane.ERROR_MESSAGE);
            return null;
        }
        while (linhas > 0) {//limpa conteudo da tabela para atulização
            for (int i = linhas; i > 0; i--) {
                modelo.removeRow(i - 1);
                linhas = modelo.getRowCount();
            }//fim do for
        }//fim do while

        int l = 0;
        if (!listaUsuarios.isEmpty()) {
            for (int i = 0; i < listaUsuarios.size(); i++) {
                try {
                    modelo.addRow(linha);
                    try {
                        modelo.setValueAt(listaUsuarios.get(i).getIUsuario(), l, 0);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaUsuarios.get(i).getUsuarioNome(), l, 1);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaUsuarios.get(i).getSituacao(), l, 2);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt("SEM MENSAGENS", l, 3);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                } catch (NullPointerException erro) {
                    System.out.println("Atenção! Campos vazios.");
                }
                l++;
            }
        }
        jTusuarios.setModel(modelo);
        tamanhoColunas(jTusuarios);
        return modelo;
        //</editor-fold>
    }

    public void tamanhoColunas(javax.swing.JTable tabela) {
        tabela.getColumnModel().getColumn(0).setPreferredWidth(1);
    }

    public String dataFormatada(Date dt) {
        SimpleDateFormat formato = new SimpleDateFormat("dd MMM 'de' yyyy HH:mm:ss");
        return formato.format(dt);
    }

    private class Tempo_historico implements Runnable {

        @Override
        public void run() {
            for (int i = 1; 1 <= 1; i++) {
                lerMensagemRecebidaTodas();
                lerMensagemRecebida();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {

                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTusuarios = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTAhistorico = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTAtexto = new javax.swing.JTextArea();
        jBenviar = new javax.swing.JButton();
        dateChooser = new datechooser.beans.DateChooserCombo();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CHAT");
        setMinimumSize(new java.awt.Dimension(478, 368));
        setType(java.awt.Window.Type.POPUP);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jTusuarios.setAutoCreateRowSorter(true);
        jTusuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "USUÁRIO", "STATUS"
            }
        ));
        jTusuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTusuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTusuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTusuarios);

        jTAhistorico.setEditable(false);
        jTAhistorico.setBackground(new java.awt.Color(204, 204, 204));
        jTAhistorico.setColumns(20);
        jTAhistorico.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTAhistorico.setLineWrap(true);
        jTAhistorico.setRows(1);
        jTAhistorico.setWrapStyleWord(true);
        jTAhistorico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTAhistoricoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTAhistorico);

        jTAtexto.setColumns(20);
        jTAtexto.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jTAtexto.setLineWrap(true);
        jTAtexto.setRows(1);
        jTAtexto.setWrapStyleWord(true);
        jTAtexto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTAtextoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTAtextoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTAtextoKeyTyped(evt);
            }
        });
        jScrollPane3.setViewportView(jTAtexto);

        jBenviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ok_16.png"))); // NOI18N
        jBenviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBenviarActionPerformed(evt);
            }
        });

        dateChooser.setCurrentView(new datechooser.view.appearance.AppearancesList("Grey",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    true,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 255),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(128, 128, 128),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(255, 0, 0),
                    false,
                    false,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                (datechooser.view.BackRenderer)null,
                false,
                true)));
    dateChooser.setAutoScroll(false);
    dateChooser.setNothingAllowed(false);
    dateChooser.setFormat(2);
    dateChooser.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
    dateChooser.addCommitListener(new datechooser.events.CommitListener() {
        public void onCommit(datechooser.events.CommitEvent evt) {
            dateChooserOnCommit(evt);
        }
    });

    jLabel1.setText("Exibir histórico a partir de:");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane2)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jScrollPane3)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jBenviar))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(dateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(dateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGap(11, 11, 11)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jBenviar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap())
    );

    setSize(new java.awt.Dimension(478, 368));
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTAhistoricoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTAhistoricoMouseClicked
        if (evt.getButton() == 3) {
            String[] str = {"SIM", "NÃO"};
            int result = JOptionPane.showOptionDialog(null,
                    "Limpar histórico das mensagem enviadas?", "HISTÓRICO", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, str, str[1]);
            if (result == 0) {
                int iUsuario = (int) jTusuarios.getValueAt(jTusuarios.getSelectedRow(), 0);
                emfHistChat.limparHistorico(i_usuario, iUsuario);
                lerMensagemRecebida();
                new util.ConfigChat(verificaArquivo()).limpa();
            }
        }
    }//GEN-LAST:event_jTAhistoricoMouseClicked

    private void jBenviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBenviarActionPerformed
        //gravar();
        enviar();
    }//GEN-LAST:event_jBenviarActionPerformed

    private void jTAtextoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAtextoKeyReleased
        switch (evt.getKeyCode()) {
            case 10:
                if (!evt.isShiftDown()) {
                    enviar();
                } else {
                    jTAtexto.setText(jTAtexto.getText() + "\n");
                }
                break;
        }
    }//GEN-LAST:event_jTAtextoKeyReleased

    private void jTAtextoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAtextoKeyPressed

    }//GEN-LAST:event_jTAtextoKeyPressed

    private void jTAtextoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTAtextoKeyTyped

    }//GEN-LAST:event_jTAtextoKeyTyped

    private void jTusuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTusuariosMouseClicked
        lerMensagemRecebida();
    }//GEN-LAST:event_jTusuariosMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        t.stop();
    }//GEN-LAST:event_formWindowClosed

    private void dateChooserOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_dateChooserOnCommit
        lerMensagemRecebida();
    }//GEN-LAST:event_dateChooserOnCommit

    public String verificaArquivo() {
        int iUsuario = (int) jTusuarios.getValueAt(jTusuarios.getSelectedRow(), 0);
        SuporteUsuarios usuPara = emfUsuario.findSuporteUsuarios(iUsuario);
        SuporteUsuarios usuDe = emfUsuario.findSuporteUsuarios(Chat.i_usuario);
        String situacao = usuDe.getSituacao();
        String caminho = "lib\\historico\\";
        String usuLoginDe = usuDe.getUsuario();
        String usuLoginPara = usuPara.getUsuario();
        String arquivo = caminho + usuDe.getIUsuario() + "-" + usuPara.getIUsuario() + ".cache";
        try {
            switch (situacao) {
                case "ATIVO":
                    FileWriter arquivoWriter = new FileWriter(new File(arquivo), true);
                    break;
                case "DESTIVADO":
                    JOptionPane.showMessageDialog(null, "Usuário desativado", "Abrir Chat", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case "ONLINE":
                    arquivoWriter = new FileWriter(new File(arquivo), true);
                    break;
                case "OFFLINE":
                    arquivoWriter = new FileWriter(new File(arquivo), true);
                    break;
            }

        } catch (IOException ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arquivo;
    }

    public void ler() {
        jTAhistorico.setText(new util.ConfigChat(verificaArquivo()).abre());
    }

    public void enviar() {
        int iUsuario = (int) jTusuarios.getValueAt(jTusuarios.getSelectedRow(), 0);
        SuporteUsuarios usuPara = emfUsuario.findSuporteUsuarios(iUsuario);
        SuporteUsuarios usuDe = emfUsuario.findSuporteUsuarios(this.i_usuario);
        String texto = jTAtexto.getText();
        SuporteHistoricoChat histChat = new SuporteHistoricoChat();
        histChat.setIUsuarioDe(usuDe);
        histChat.setIUsuarioPara(usuPara);
        histChat.setTexto(texto.trim());
        histChat.setAlteracao(new Date());
        histChat.setVisualizado(false);
        emfHistChat.create(histChat);
        String mensagem = usuDe.getUsuario() + " - " + dataFormatada(histChat.getAlteracao()) + ": " + histChat.getTexto() + "\n";
        new util.ConfigChat(verificaArquivo()).grava(mensagem.trim());
        jTAtexto.setText("");
    }

    public void lerMensagemRecebida() {
        int iUsuario = (int) jTusuarios.getValueAt(jTusuarios.getSelectedRow(), 0);
        SuporteUsuarios usuPara = emfUsuario.findSuporteUsuarios(iUsuario);
        SuporteUsuarios usuDe = emfUsuario.findSuporteUsuarios(Chat.i_usuario);
        String msg = usuPara.getUsuarioNome();
        setTitle("CHAT  | " + usuDe.getUsuario() + " |  " + "em conversa com: " + msg);
        List<SuporteHistoricoChat> lista = emfHistChat.findSuporteHistoricoChatEntitiesLidos(false, usuDe.getIUsuario(), usuPara.getIUsuario());

        try {
            if (lista.size() > 0) {
                SuporteHistoricoChat histChat;
                String saida = "";
                for (int i = 0; i < lista.size(); i++) {
                    histChat = emfHistChat.findSuporteHistoricoChat(lista.get(i).getIHistoricoChat());
                    histChat.setVisualizado(true);
                    emfHistChat.edit(histChat);
                    saida += lista.get(i).getIUsuarioDe().getUsuario() + " - " + dataFormatada(lista.get(i).getAlteracao()) + ": " + lista.get(i).getTexto() + "\n";
                }
                if (!usuDe.equals(usuPara)) {
                    new util.ConfigChat(verificaArquivo()).grava(saida.trim());
                }
                jTusuarios.setValueAt("SEM MENSAGENS", jTusuarios.getSelectedRow(), 3);
            }
        } catch (Exception ex) {
            Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
        ler();
        //jTAhistorico.setText(saida);
    }

    public void lerMensagemRecebidaTodas() {
        SuporteUsuarios usuDe = emfUsuario.findSuporteUsuarios(Chat.i_usuario);
        List<SuporteHistoricoChat> lista = emfHistChat.findSuporteHistoricoChatEntitiesLidosTodos(usuDe.getIUsuario());
        if (lista.size() > 0) {
            int i_usu_linha;
            for (SuporteHistoricoChat lista1 : lista) {
                for (int l = 0; l < jTusuarios.getRowCount(); l++) {
                    System.out.println("L = "+l);
                    System.out.println("VALOR = "+jTusuarios.getValueAt(l, 0));
                    if (lista1.getIUsuarioDe().getIUsuario() == jTusuarios.getValueAt(l, 0)) {
                        jTusuarios.setValueAt("NOVA MENSAGEM", l, 3);
                    }
                }
            }
            SuporteHistoricoChat histChat;
            System.out.println("nao lida");
        }
    }

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
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Chat(i_usuario).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserCombo dateChooser;
    private javax.swing.JButton jBenviar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTAhistorico;
    private javax.swing.JTextArea jTAtexto;
    private javax.swing.JTable jTusuarios;
    // End of variables declaration//GEN-END:variables
}
