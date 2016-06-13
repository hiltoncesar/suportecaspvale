/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames.Acoes;

import controle.SuporteAcessoRemotoJpaController;
import controle.SuporteHistoricoAcessoJpaController;
import entidades.SuporteAcessoRemoto;
import entidades.SuporteHistoricoAcesso;
import entidades.SuporteUsuarios;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hilton
 */
public final class AcessarHost extends javax.swing.JInternalFrame {

    private SuporteAcessoRemotoJpaController emfAcesso = new SuporteAcessoRemotoJpaController(new util.Persistencia().emf());
    private final SuporteHistoricoAcessoJpaController emfHistorico = new SuporteHistoricoAcessoJpaController(new util.Persistencia().emf());
    private final SuporteUsuarios usuario;
    private int i_acesso = 0;
    public Thread t;
    // private final String wts = "C:\\Windows\\system32\\mstsc.exe /v:";
    // private final String navegador = "C:\\Program Files (x86)\\Internet Explorer\\iexplore.exe";
    private File f;
    private final util.Config config = new util.Config();

    public AcessarHost(SuporteUsuarios usuario) {
        this.usuario = usuario;
        initComponents();
        tabelaAcessoRemoto();
        jTFnavegador.setText(config.getKey("navegador"));
        jTFcomando.setText(config.getKey("comando"));
        listarHistorico();
        setFrameIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagens/icone_2_24.png")));
        t = new Thread(new Tempo_historico());
        t.start();
    }

    public DefaultTableModel tabelaAcessoRemoto() {
        //<editor-fold defaultstate="collapsed" desc="Manipulação da Tabela">
        /* Método controlador da tabela*/

        //int iFun = id_funcionario[fu.getSelectedIndex()];
        DefaultTableModel modelo = new DefaultTableModel(null, new String[]{
            "CÓDIGO", "ENTIDADE | HOST", "TIPO", "ESTADO"}) {
//            Class[] types = new Class[]{
//                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
//            };
            @Override
            public boolean isCellEditable(int a, int b) {
                return false;
            }
        };
        String[] linha = new String[]{null, null, null, null};
        int linhas = modelo.getRowCount();

        List<SuporteAcessoRemoto> listaAcesso =emfAcesso.findSuporteAcessoRemotoEntitiesOrdenado();
        //List<SuporteAcessoRemoto> listaAcesso = null;
     //   try {
            //listaAcesso = emfAcesso.findSuporteAcessoRemotoEntitiesJDBC();
//    //    } catch (Exception ex) {
//            javax.swing.JOptionPane.showMessageDialog(null,
//                    "Erro ao recuperar informações da tabela\n"
//                    + ex.getCause().getMessage(), "Erro de Conexão", javax.swing.JOptionPane.ERROR_MESSAGE);
//            return null;
//        }
        while (linhas > 0) {//limpa conteudo da tabela para atulização
            for (int i = linhas; i > 0; i--) {
                modelo.removeRow(i - 1);
                linhas = modelo.getRowCount();
            }//fim do for
        }//fim do while

        int l = 0;
        if (!listaAcesso.isEmpty()) {
            for (int i = 0; i < listaAcesso.size(); i++) {
                try {
                    modelo.addRow(linha);
                    try {
                        modelo.setValueAt(listaAcesso.get(i).getIAcesso(), l, 0);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaAcesso.get(i).getIEntidade().getEntidadeNome() +" | "+listaAcesso.get(i).getNomeAcesso(), l, 1);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaAcesso.get(i).getComando(), l, 2);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        if (listaAcesso.get(i).getOnline()) {
                            modelo.setValueAt("CONECTADO", l, 3);
                        } else {
                            modelo.setValueAt("DESCONECTADO", l, 3);
                        }
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                } catch (NullPointerException erro) {
                    System.out.println("Atenção! Campos vazios.");
                }
                l++;
            }
        }
        jTacessoRemoto.setModel(modelo);
        tamanhoColunas(jTacessoRemoto);
        return modelo;
        //</editor-fold>
    }

    //tipo == 0 data tipo == 1 hora
    public String dataFormatada(Date dt) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formato.format(dt);
    }

    public void listarHistorico() {
        List<SuporteHistoricoAcesso> listaHistorico = emfHistorico.findSuporteHistoricoAcessoUltimos(10, 0);
        jTAsaida.setText("");
        for (int i = 0; i < listaHistorico.size(); i++) {
            jTAsaida.setText(jTAsaida.getText() + listaHistorico.get(i).getComentario() + "\n");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTacessoRemoto = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jBdesconectar = new javax.swing.JButton();
        jBconectar = new javax.swing.JButton();
        jLstatus = new javax.swing.JLabel();
        jBatualizar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTAsaida = new javax.swing.JTextArea();
        jTFnavegador = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jTFcomando = new javax.swing.JTextField();
        jBalterar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Acesso Remoto");
        setMinimumSize(new java.awt.Dimension(592, 546));
        setName("acessarhost"); // NOI18N
        setPreferredSize(new java.awt.Dimension(592, 546));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jTacessoRemoto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "CODIGO", "ENTIDADE", "HOST", "CONECTADO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTacessoRemoto.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTacessoRemoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTacessoRemotoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTacessoRemoto);

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jBdesconectar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/desconectar_32.png"))); // NOI18N
        jBdesconectar.setToolTipText("Desconectar");
        jBdesconectar.setEnabled(false);
        jBdesconectar.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jBdesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBdesconectarActionPerformed(evt);
            }
        });

        jBconectar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/conectar_32.png"))); // NOI18N
        jBconectar.setToolTipText("Conectar ao host selecionado");
        jBconectar.setEnabled(false);
        jBconectar.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jBconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBconectarActionPerformed(evt);
            }
        });

        jLstatus.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        jLstatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLstatus.setText("NÃO CONECTADO");

        jBatualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/atualizar_16.png"))); // NOI18N
        jBatualizar.setToolTipText("Atualizar Tabela");
        jBatualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBatualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jBatualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLstatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBconectar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBdesconectar))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jBatualizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBdesconectar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBconectar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLstatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTAsaida.setEditable(false);
        jTAsaida.setColumns(1);
        jTAsaida.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTAsaida.setLineWrap(true);
        jTAsaida.setRows(1);
        jTAsaida.setTabSize(1);
        jTAsaida.setWrapStyleWord(true);
        jTAsaida.setFocusable(false);
        jTAsaida.setRequestFocusEnabled(false);
        jScrollPane2.setViewportView(jTAsaida);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/open_16.png"))); // NOI18N
        jButton1.setText("Selecionar Navegador");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jBalterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ok_16.png"))); // NOI18N
        jBalterar.setText("Aplicar Alterações");
        jBalterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBalterarActionPerformed(evt);
            }
        });

        jLabel1.setText("Navegador:");

        jLabel2.setText("Comando:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTFnavegador)
                    .addComponent(jTFcomando))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBalterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jScrollPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTFnavegador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)))
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFcomando, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBalterar)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTacessoRemotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTacessoRemotoMouseClicked
        if (jTacessoRemoto.isEnabled()) {
            i_acesso = (int) jTacessoRemoto.getValueAt(jTacessoRemoto.getSelectedRow(), 0);
            String conectado = (String) jTacessoRemoto.getValueAt(jTacessoRemoto.getSelectedRow(), 3);
            switch (conectado) {
                case "DESCONECTADO":
                    jBconectar.setEnabled(true);
                    jBdesconectar.setEnabled(false);
                    break;
                case "CONECTADO":
                    jBdesconectar.setEnabled(true);
                    jBconectar.setEnabled(true);
                    break;
            }
        }
    }//GEN-LAST:event_jTacessoRemotoMouseClicked

    private void jBconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBconectarActionPerformed
        if (jTacessoRemoto.hasFocus() == false) {
            try {
                conectar();
                tabelaAcessoRemoto();
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(null,
                        "Erro ao registrar comando.\n"
                        + ex.getCause().getMessage(), "Registro não gravado", javax.swing.JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(AcessarHost.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            jBconectar.setEnabled(false);
        }
    }//GEN-LAST:event_jBconectarActionPerformed

    private void jBdesconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBdesconectarActionPerformed
        try {
            desconectar();
            tabelaAcessoRemoto();
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Erro ao registrar comando.\n"
                    + ex.getCause().getMessage(), "Registro não gravado", javax.swing.JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(AcessarHost.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jBdesconectarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SelecionarArquivo c = new SelecionarArquivo(new Frames.Login(), rootPaneCheckingEnabled, "navegador", "");
        c.setVisible(true);
        this.f = c.file;
        if (f != null) {
            jTFnavegador.setText(f.getAbsolutePath());
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jBalterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBalterarActionPerformed
        alterar();
    }//GEN-LAST:event_jBalterarActionPerformed

    private void jBatualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBatualizarActionPerformed
        tabelaAcessoRemoto();
        listarHistorico();
        t.suspend();
        t.resume();
    }//GEN-LAST:event_jBatualizarActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        t.stop();
    }//GEN-LAST:event_formInternalFrameClosed
    
    

    private class Tempo_historico implements Runnable {

        @Override
        public void run() {
            for (int i = 1; 1 <= 1; i++) {
                listarHistorico();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {

                }
            }
        }
    }

    public void conectar() throws Exception {
        this.i_acesso = (int) jTacessoRemoto.getValueAt(jTacessoRemoto.getSelectedRow(), 0);
        SuporteAcessoRemoto acessoRemoto = emfAcesso.findSuporteAcessoRemoto(this.i_acesso);
        String start;
        if (acessoRemoto.getComando().equals("LOGMEIN")) {
            start = jTFnavegador.getText() + " " + acessoRemoto.getLink();
        } else if (acessoRemoto.getComando().equals("ÁREA DE TRABALHO REMOTA")) {
            start = jTFcomando.getText() + " " + acessoRemoto.getIpPorta() + " /f";
        } else {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "O tipo de conexão foi localizado.\n"
                    + "Acesse o cadastro de Acesso Remoto e informe um tipo para este host.",
                    "Não conectado", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            System.out.println("COMANDO: " + start);
            Runtime.getRuntime().exec(start);
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Não foi possível executar o comando.\n"
                    + ex.getCause().getMessage(), "Não conectado", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        SuporteHistoricoAcesso historicoAcesso = new SuporteHistoricoAcesso();
        Date data = new Date();
        String host = acessoRemoto.getNomeAcesso();
        String entidadeNome = acessoRemoto.getIEntidade().getEntidadeNome();
        String usuarioNome = usuario.getUsuario();

        historicoAcesso.setIUsuario(usuario);
        historicoAcesso.setDtAcesso(data);
        historicoAcesso.setIAcesso(acessoRemoto);
        historicoAcesso.setComentario(entidadeNome + " - " + host + " - " + usuarioNome + " - " + dataFormatada(data) + " - " + "CONECTADO");
        acessoRemoto.setOnline(true);

        emfAcesso.edit(acessoRemoto);
        emfHistorico.create(historicoAcesso);

        listarHistorico();
        jLstatus.setText("CONECTADO AO HOST: " + entidadeNome + " - " + host);
        jBconectar.setEnabled(false);
        jTacessoRemoto.setEnabled(false);
        jBdesconectar.setEnabled(true);

    }

    public void desconectar() throws Exception {
        SuporteAcessoRemoto acessoRemoto = emfAcesso.findSuporteAcessoRemoto(this.i_acesso);
        SuporteHistoricoAcesso historicoAcesso = new SuporteHistoricoAcesso();
        Date data = new Date();
        String host = acessoRemoto.getNomeAcesso();
        String entidadeNome = acessoRemoto.getIEntidade().getEntidadeNome();
        String usuarioNome = usuario.getUsuario();

        historicoAcesso.setIUsuario(usuario);
        historicoAcesso.setDtAcesso(data);
        historicoAcesso.setIAcesso(acessoRemoto);
        historicoAcesso.setComentario(entidadeNome + " - " + host + " - " + usuarioNome + " - " + dataFormatada(data) + " - " + "DESCONECTADO");
        acessoRemoto.setOnline(false);

        emfAcesso.edit(acessoRemoto);
        emfHistorico.create(historicoAcesso);

        listarHistorico();
        jLstatus.setText("NÃO CONECTADO");
        jBconectar.setEnabled(true);
        jBdesconectar.setEnabled(false);
        jTacessoRemoto.setEnabled(true);
        this.i_acesso = 0;
    }

    public void atualizar() {
        tabelaAcessoRemoto();
    }

    public void alterar() {
        config.setKey("navegador", jTFnavegador.getText());
        config.setKey("comando", jTFcomando.getText());
        jTFnavegador.setText(config.getKey("navegador"));
        jTFcomando.setText(config.getKey("comando"));
        javax.swing.JOptionPane.showMessageDialog(null,
                "Os campos Navegador e Comando foram alterados.", "Alteração de campos", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    public void tamanhoColunas(javax.swing.JTable tabela) {
        tabela.getColumnModel().getColumn(0).setPreferredWidth(24);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBalterar;
    private javax.swing.JButton jBatualizar;
    private javax.swing.JButton jBconectar;
    private javax.swing.JButton jBdesconectar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLstatus;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTAsaida;
    private javax.swing.JTextField jTFcomando;
    private javax.swing.JTextField jTFnavegador;
    private javax.swing.JTable jTacessoRemoto;
    // End of variables declaration//GEN-END:variables
}
