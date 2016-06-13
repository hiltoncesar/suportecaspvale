/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames.Cadastros;


import controle.SuportePeriodosJpaController;
import entidades.SuportePeriodos;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hilton
 */
public final class CadastrarPeriodos extends javax.swing.JInternalFrame {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("SuporteCaspValePU");
    private final SuportePeriodosJpaController emfPeriodos = new SuportePeriodosJpaController(new util.Persistencia().emf());
    //private final SuporteAreasJpaController emfAreas;
    
    private String modoGravar, modoBuscar = "";

    public CadastrarPeriodos() {
        //this.emfAreas = new SuporteAreasJpaController(emf);
        initComponents();
        tabelaAreas();
        setFrameIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagens/icone_2_24.png")));     
        jSano.setText(formato(new Date(),"yyyy"));
    }

    public String formato(Date dt, String tipo) {
        /*  "dd/MM/yyyy"
         "HH:mm:ss"
         "dd/MM/yyyy"
         "HH:mm"
         "yyyy-MM-dd"
         "HH:mm:ss"  */
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formato = new SimpleDateFormat(tipo);
        return formato.format(dt);
    }
    
    public DefaultTableModel tabelaAreas() {
        //<editor-fold defaultstate="collapsed" desc="Manipulação da Tabela">
        /* Método controlador da tabela*/

        //int iFun = id_funcionario[fu.getSelectedIndex()];
        DefaultTableModel modelo = new DefaultTableModel(null, new String[]{
            "CÓDIGO", "INÍCIO", "FIM","MÊS","EXERCÍCIO"}) {
            @Override
            public boolean isCellEditable(int a, int b) {
                return false;
            }
        };
        String[] linha = new String[]{null, null, null,null,null};
        int linhas = modelo.getRowCount();

        List<SuportePeriodos> listaPeriodos =null;
        try {
            listaPeriodos = emfPeriodos.findSuportePeriodosEntities();
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
        if (!listaPeriodos.isEmpty()) {
            for (int i = 0; i < listaPeriodos.size(); i++) {
                try {
                    modelo.addRow(linha);
                    try {
                        modelo.setValueAt(listaPeriodos.get(i).getIPeriodo(), l, 0);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(formato(listaPeriodos.get(i).getDtInicio(),"dd/MM/yyyy"), l, 1);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(formato(listaPeriodos.get(i).getDtFim(),"dd/MM/yyyy"), l, 2);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaPeriodos.get(i).getMesReferencia(), l, 3);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaPeriodos.get(i).getExercicio(), l, 4);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                } catch (NullPointerException erro) {
                    System.out.println("Atenção! Campos vazios.");
                }
                l++;
            }
        }
        jTperiodos.setModel(modelo);
        tamanhoColunas(jTperiodos);
        return modelo;
        //</editor-fold>
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        dateChooserPanel1 = new datechooser.beans.DateChooserPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTperiodos = new javax.swing.JTable();
        jBnovo = new javax.swing.JButton();
        jBatualizar = new javax.swing.JButton();
        jBexcluir = new javax.swing.JButton();
        jBgravar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTFcodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jDCfim = new datechooser.beans.DateChooserCombo();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jDCinicio = new datechooser.beans.DateChooserCombo();
        jCBmes = new javax.swing.JComboBox<>();
        jSano = new javax.swing.JTextField();

        jToolBar1.setRollover(true);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cadastrar Período");
        setToolTipText("");
        setMinimumSize(new java.awt.Dimension(412, 349));
        setName("cadastraperiodo"); // NOI18N

        jTperiodos.setAutoCreateRowSorter(true);
        jTperiodos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "CÓDIGO", "INIICO", "FIM", "MES", "EXERCICIO"
            }
        ));
        jTperiodos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTperiodos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTperiodosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTperiodos);
        if (jTperiodos.getColumnModel().getColumnCount() > 0) {
            jTperiodos.getColumnModel().getColumn(0).setPreferredWidth(5);
            jTperiodos.getColumnModel().getColumn(1).setPreferredWidth(58);
            jTperiodos.getColumnModel().getColumn(2).setPreferredWidth(86);
        }

        jBnovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/novo_16.png"))); // NOI18N
        jBnovo.setText("Novo");
        jBnovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBnovoActionPerformed(evt);
            }
        });

        jBatualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/atualizar_16.png"))); // NOI18N
        jBatualizar.setText("Atualizar");
        jBatualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBatualizarActionPerformed(evt);
            }
        });

        jBexcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/deletar_16.png"))); // NOI18N
        jBexcluir.setText("Excluir");
        jBexcluir.setEnabled(false);
        jBexcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBexcluirActionPerformed(evt);
            }
        });

        jBgravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ok_16.png"))); // NOI18N
        jBgravar.setText("Gravar");
        jBgravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBgravarActionPerformed(evt);
            }
        });

        jLabel3.setText("Código:");

        jTFcodigo.setEditable(false);

        jLabel2.setText("Exercício:");

        jLabel4.setText("Mês:");

        jDCfim.setFormat(2);
        jDCfim.setWeekStyle(datechooser.view.WeekDaysStyle.FULL);
        jDCfim.setEnabled(false);
        jDCfim.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
        jDCfim.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                jDCfimOnCommit(evt);
            }
        });

        jLabel5.setText("Fim:");

        jLabel6.setText("Início:");

        jDCinicio.setWeekStyle(datechooser.view.WeekDaysStyle.FULL);
        jDCinicio.setEnabled(false);
        jDCinicio.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
        jDCinicio.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                jDCinicioOnCommit(evt);
            }
        });

        jCBmes.setMaximumRowCount(12);
        jCBmes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "JANEIRO", "FEVEREIRO", "MARÇO", "ABRIL", "MAIO", "JUNHO", "JULHO", "AGOSTO", "SETEMBRO", "OUTUBRO", "NOVEMBRO", "DEZEMBRO" }));
        jCBmes.setEnabled(false);

        jSano.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jSano, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCBmes, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jDCinicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDCfim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBnovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBatualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBexcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBgravar)
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBatualizar, jBexcluir, jBgravar, jBnovo});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jCBmes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jDCinicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jDCfim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBnovo)
                    .addComponent(jBatualizar)
                    .addComponent(jBgravar)
                    .addComponent(jBexcluir)))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jDCfim, jLabel5});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jDCinicio, jLabel6});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBnovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBnovoActionPerformed
        novo();
    }//GEN-LAST:event_jBnovoActionPerformed

    private void jBatualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBatualizarActionPerformed
        atualizar();
    }//GEN-LAST:event_jBatualizarActionPerformed

    private void jBexcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBexcluirActionPerformed
        excluir();
    }//GEN-LAST:event_jBexcluirActionPerformed

    private void jBgravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBgravarActionPerformed
        gravar();
    }//GEN-LAST:event_jBgravarActionPerformed

    private void jTperiodosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTperiodosMouseClicked
        selecionar();
    }//GEN-LAST:event_jTperiodosMouseClicked

    private void jDCfimOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_jDCfimOnCommit
        
    }//GEN-LAST:event_jDCfimOnCommit

    private void jDCinicioOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_jDCinicioOnCommit
        // TODO add your handling code here:
    }//GEN-LAST:event_jDCinicioOnCommit
    public void limparCampos() {
        jTFcodigo.setText("");
        jDCinicio.setCurrent(null);
        jDCfim.setCurrent(null);
        jCBmes.setSelectedIndex(0);
        jSano.setText(formato(new Date(),"yyyy"));
    }

    public void campos(boolean estado) {
        limparCampos();
        jDCinicio.setEnabled(estado);
        jDCfim.setEnabled(estado);
        jCBmes.setEnabled(estado);
        jSano.setEnabled(estado);
    }

    public void novo() {
        limparCampos();
        campos(true);
        jBnovo.setEnabled(false);
        jBgravar.setEnabled(true);
        jBexcluir.setEnabled(false);
        jBgravar.setText("Gravar");
        modoGravar = "NOVO";
    }

    public void atualizar() {
        limparCampos();
        campos(false);
        jBnovo.setEnabled(true);
        jBgravar.setEnabled(false);
        jBexcluir.setEnabled(false);
        tabelaAreas();
    }

    public void selecionar() {
        modoBuscar = "";
        jBatualizar.setEnabled(true);
        int i_periodo = Integer.parseInt(String.valueOf(jTperiodos.getValueAt(jTperiodos.getSelectedRow(), 0)));
        SuportePeriodos periodo = emfPeriodos.findSuportePeriodos(i_periodo);
        campos(true);
        jBnovo.setEnabled(true);
        jBgravar.setEnabled(true);
        jBexcluir.setEnabled(true);
        jBgravar.setText("Alterar");
        jTFcodigo.setText(String.valueOf(periodo.getIPeriodo()));
        Calendar calendar = new GregorianCalendar();
        Date dtInicio = periodo.getDtInicio();
        Date dtFim = periodo.getDtFim();
        calendar.setTime(dtInicio);
        jDCinicio.setCurrent(calendar);
        calendar.setTime(dtFim);
        jDCfim.setCurrent(calendar);
        
        
        modoGravar = "EDITAR";
//        nivelSecretario(nivel);
    }

    public void gravar() {
        if (validarCamposVazios()) {
            SuportePeriodos periodos = null;
            jBexcluir.setEnabled(false);
            switch (modoGravar) {
                case "NOVO":
                    periodos = new SuportePeriodos();
                    periodos.setDtInicio(jDCinicio.getCurrent().getTime());
                    periodos.setDtFim(jDCfim.getCurrent().getTime());
                    periodos.setExercicio(Integer.parseInt(jSano.getText()));
                    periodos.setMesReferencia(jCBmes.getSelectedItem().toString());
                    emfPeriodos.create(periodos);
                    jBnovo.setEnabled(true);
                    campos(false);
                    tabelaAreas();
                    break;
                case "EDITAR":
                    periodos = emfPeriodos.findSuportePeriodos(Integer.parseInt(jTFcodigo.getText()));
                    periodos.setDtInicio(jDCinicio.getCurrent().getTime());
                    periodos.setDtFim(jDCfim.getCurrent().getTime());
                    periodos.setExercicio(Integer.parseInt(jSano.getText()));
                    periodos.setMesReferencia(jCBmes.getSelectedItem().toString());
                    try {
                        emfPeriodos.edit(periodos);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        javax.swing.JOptionPane.showMessageDialog(null, "Não foi possível alterar o cadastro.",
                                "ERRO: 001", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                    campos(false);
                    tabelaAreas();
                    break;
            }
            jBgravar.setEnabled(false);
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "O campo ÁREA precisa ser informado", "Atenção! Campos vazios.",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    public void excluir() {
        try {
            jBexcluir.setEnabled(false);
            int id = Integer.parseInt(jTFcodigo.getText());
            emfPeriodos.destroy(id);
            campos(false);
            tabelaAreas();
        } catch (java.lang.NumberFormatException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Não foi possível excluir o registro.", "ERRO E003", javax.swing.JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Não foi possível excluir o registro.", "ERRO E004", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean validarCamposVazios() {
        return !jSano.equals(null);

//        if (jTFarea.getText() == null) {
//            return false;
//        } else {
//            return true;
//        } 
    }
    
    public void tamanhoColunas(javax.swing.JTable tabela) {
        tabela.getColumnModel().getColumn(0).setPreferredWidth(34);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private datechooser.beans.DateChooserPanel dateChooserPanel1;
    private javax.swing.JButton jBatualizar;
    private javax.swing.JButton jBexcluir;
    private javax.swing.JButton jBgravar;
    private javax.swing.JButton jBnovo;
    private javax.swing.JComboBox<String> jCBmes;
    private datechooser.beans.DateChooserCombo jDCfim;
    private datechooser.beans.DateChooserCombo jDCinicio;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField jSano;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFcodigo;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable jTperiodos;
    // End of variables declaration//GEN-END:variables
}
