/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames.Cadastros;

import controle.SuporteAreasJpaController;
import entidades.SuporteAreas;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hilton
 */
public final class CadastrarArea extends javax.swing.JInternalFrame {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("SuporteCaspValePU");
    private final SuporteAreasJpaController emfAreas = new SuporteAreasJpaController(new util.Persistencia().emf());
    //private final SuporteAreasJpaController emfAreas;
    
    private String modoGravar, modoBuscar = "";

    public CadastrarArea() {
        //this.emfAreas = new SuporteAreasJpaController(emf);
        initComponents();
        tabelaAreas();
        setFrameIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagens/icone_2_24.png")));
        jTFarea.setDocument(new util.FormatarCampo(3, "textopadrao"));
    }

    public DefaultTableModel tabelaAreas() {
        //<editor-fold defaultstate="collapsed" desc="Manipulação da Tabela">
        /* Método controlador da tabela*/

        //int iFun = id_funcionario[fu.getSelectedIndex()];
        DefaultTableModel modelo = new DefaultTableModel(null, new String[]{
            "CÓDIGO", "ÁREA", "DESCRIÇÃO"}) {
            @Override
            public boolean isCellEditable(int a, int b) {
                return false;
            }
        };
        String[] linha = new String[]{null, null, null};
        int linhas = modelo.getRowCount();

        List<SuporteAreas> listaArea =null;
        try {
            listaArea = emfAreas.findSuporteAreasEntitiesOrdenado();
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
        if (!listaArea.isEmpty()) {
            for (int i = 0; i < listaArea.size(); i++) {
                try {
                    modelo.addRow(linha);
                    try {
                        modelo.setValueAt(listaArea.get(i).getIArea(), l, 0);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaArea.get(i).getAreaNome(), l, 1);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaArea.get(i).getDescricaoArea(), l, 2);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                } catch (NullPointerException erro) {
                    System.out.println("Atenção! Campos vazios.");
                }
                l++;
            }
        }
        jTareas.setModel(modelo);
        tamanhoColunas(jTareas);
        return modelo;
        //</editor-fold>
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTareas = new javax.swing.JTable();
        jBnovo = new javax.swing.JButton();
        jBatualizar = new javax.swing.JButton();
        jBexcluir = new javax.swing.JButton();
        jBgravar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jTFcodigo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTFarea = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTFdescricao = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cadastrar Área");
        setMinimumSize(new java.awt.Dimension(412, 349));
        setName("cadastrararea"); // NOI18N

        jTareas.setAutoCreateRowSorter(true);
        jTareas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "CÓDIGO", "ÁREA", "DESCRIÇÃO"
            }
        ));
        jTareas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTareas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTareasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTareas);
        if (jTareas.getColumnModel().getColumnCount() > 0) {
            jTareas.getColumnModel().getColumn(0).setPreferredWidth(5);
            jTareas.getColumnModel().getColumn(1).setPreferredWidth(58);
            jTareas.getColumnModel().getColumn(2).setPreferredWidth(86);
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

        jLabel2.setText("Área:");

        jTFarea.setEditable(false);

        jLabel4.setText("Descrição:");

        jTFdescricao.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jBnovo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBatualizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBexcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBgravar)
                .addGap(0, 6, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTFcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTFdescricao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                                    .addComponent(jTFarea, javax.swing.GroupLayout.Alignment.LEADING))
                                .addContainerGap(16, Short.MAX_VALUE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
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
                    .addComponent(jTFarea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTFdescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBnovo)
                    .addComponent(jBatualizar)
                    .addComponent(jBgravar)
                    .addComponent(jBexcluir))
                .addGap(4, 4, 4))
        );

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

    private void jTareasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTareasMouseClicked
        selecionar();
    }//GEN-LAST:event_jTareasMouseClicked
    public void limparCampos() {
        jTFcodigo.setText("");
        jTFarea.setText("");
        jTFdescricao.setText("");
    }

    public void campos(boolean estado) {
        limparCampos();
        jTFarea.setEditable(estado);
        jTFdescricao.setEditable(estado);
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
        int i_area = Integer.parseInt(String.valueOf(jTareas.getValueAt(jTareas.getSelectedRow(), 0)));
        SuporteAreas area = emfAreas.findSuporteAreas(i_area);
        campos(true);
        jBnovo.setEnabled(true);
        jBgravar.setEnabled(true);
        jBexcluir.setEnabled(true);
        jBgravar.setText("Alterar");
        jTFcodigo.setText(String.valueOf(area.getIArea()));
        jTFdescricao.setText(area.getDescricaoArea());
        jTFarea.setText(area.getAreaNome());
        modoGravar = "EDITAR";
//        nivelSecretario(nivel);
    }

    public void gravar() {
        if (validarCamposVazios()) {
            SuporteAreas area = null;
            jBexcluir.setEnabled(false);
            switch (modoGravar) {
                case "NOVO":
                    area = new SuporteAreas();
                    area.setDescricaoArea(jTFdescricao.getText());
                    area.setAreaNome(jTFarea.getText());
                    emfAreas.create(area);
                    jBnovo.setEnabled(true);
                    campos(false);
                    tabelaAreas();
                    break;
                case "EDITAR":
                    area = emfAreas.findSuporteAreas(Integer.parseInt(jTFcodigo.getText()));
                    area.setDescricaoArea(jTFdescricao.getText());
                    area.setAreaNome(jTFarea.getText());
                    try {
                        emfAreas.edit(area);
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
            emfAreas.destroy(id);
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
        return jTFarea.getText() != null;

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
    private javax.swing.JButton jBatualizar;
    private javax.swing.JButton jBexcluir;
    private javax.swing.JButton jBgravar;
    private javax.swing.JButton jBnovo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFarea;
    private javax.swing.JTextField jTFcodigo;
    private javax.swing.JTextField jTFdescricao;
    private javax.swing.JTable jTareas;
    // End of variables declaration//GEN-END:variables
}
