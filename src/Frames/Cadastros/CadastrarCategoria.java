/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames.Cadastros;

import controle.SuporteCategoriasJpaController;
import entidades.SuporteCategorias;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hilton
 */
public final class CadastrarCategoria extends javax.swing.JInternalFrame {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("SuporteCaspValePU");
    private final SuporteCategoriasJpaController emfCategorias = new SuporteCategoriasJpaController(new util.Persistencia().emf());
    //private final SuporteCategoriasJpaController emfCategorias;
    
    private String modoGravar, modoBuscar = "";
    public CadastrarCategoria() {
        //this.emfCategorias = new SuporteCategoriasJpaController(emf);
        initComponents();
        tabelaCategorias();
        setFrameIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagens/icone_2_24.png")));
        jTFcategoria.setDocument(new util.FormatarCampo(3, "textopadrao"));
    }

     public DefaultTableModel tabelaCategorias() {
        //<editor-fold defaultstate="collapsed" desc="Manipulação da Tabela">
        /* Método controlador da tabela*/

        //int iFun = id_funcionario[fu.getSelectedIndex()];
        DefaultTableModel modelo = new DefaultTableModel(null, new String[]{
            "CÓDIGO", "CATEGORIA", "DESCRIÇÃO"}) {
            @Override
            public boolean isCellEditable(int a, int b) {
                return false;
            }
        };
        String[] linha = new String[]{null, null, null};
        int linhas = modelo.getRowCount();
        
        List<SuporteCategorias> listaCategoria = null;
        try {
            listaCategoria = emfCategorias.findSuporteCategoriasEntitiesOrdenado();
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
        if (!listaCategoria.isEmpty()) {
            for (int i = 0; i < listaCategoria.size(); i++) {
                try {
                    modelo.addRow(linha);
                    try {
                        modelo.setValueAt(listaCategoria.get(i).getICategoria(), l, 0);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaCategoria.get(i).getCategoriaNome(), l, 1);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaCategoria.get(i).getDescricao(), l, 2);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                } catch (NullPointerException erro) {
                    System.out.println("Atenção! Campos vazios.");
                }
                l++;
            }
        }
        jTcategorias.setModel(modelo);
       tamanhoColunas(jTcategorias);
        return modelo;
        //</editor-fold>
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTFcodigo = new javax.swing.JTextField();
        jTFcategoria = new javax.swing.JTextField();
        jTFdescricao = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTcategorias = new javax.swing.JTable();
        jBnovo = new javax.swing.JButton();
        jBatualizar = new javax.swing.JButton();
        jBexcluir = new javax.swing.JButton();
        jBgravar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jCBsituacao = new javax.swing.JComboBox();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cadastrar Categoria");
        setMinimumSize(new java.awt.Dimension(412, 349));
        setName("cadastrarcategoria"); // NOI18N

        jTFcodigo.setEditable(false);

        jTFcategoria.setEditable(false);

        jTFdescricao.setEditable(false);

        jLabel4.setText("Descrição:");

        jLabel2.setText("Categoria:");

        jLabel3.setText("Código:");

        jTcategorias.setAutoCreateRowSorter(true);
        jTcategorias.setModel(new javax.swing.table.DefaultTableModel(
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
                "CÓDIGO", "CATEGORIA", "DESCRIÇÃO"
            }
        ));
        jTcategorias.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTcategorias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTcategoriasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTcategorias);

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

        jLabel5.setText("Situação:");

        jCBsituacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ATIVO", "DESATIVADO" }));
        jCBsituacao.setEnabled(false);

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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCBsituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTFdescricao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                                    .addComponent(jTFcategoria, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBatualizar, jBexcluir, jBgravar, jBnovo});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jCBsituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTFcategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jTcategoriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTcategoriasMouseClicked
        selecionar();
    }//GEN-LAST:event_jTcategoriasMouseClicked

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

    public void limparCampos() {
        jTFcodigo.setText("");
        jTFcategoria.setText("");
        jTFdescricao.setText("");
        jCBsituacao.setSelectedIndex(0);
    }

    public void campos(boolean estado) {
        limparCampos();
        jTFcategoria.setEditable(estado);
        jTFdescricao.setEditable(estado);
        jCBsituacao.setEnabled(estado);
    }

    public void novo() {
        limparCampos();
        campos(true);
        jBnovo.setEnabled(false);
        jBgravar.setEnabled(true);
        jBexcluir.setEnabled(false);
        jCBsituacao.setEnabled(false);
        jBgravar.setText("Gravar");
        modoGravar = "NOVO";
    }

    public void atualizar() {
        limparCampos();
        campos(false);
        jBnovo.setEnabled(true);
        jBgravar.setEnabled(false);
        jBexcluir.setEnabled(false);
        tabelaCategorias();
    }

    public void selecionar() {
        modoBuscar = "";
        jBatualizar.setEnabled(true);
        int i_categoria = Integer.parseInt(String.valueOf(jTcategorias.getValueAt(jTcategorias.getSelectedRow(), 0)));
        SuporteCategorias categoria = emfCategorias.findSuporteCategorias(i_categoria);
        campos(true);
        jBnovo.setEnabled(true);
        jBgravar.setEnabled(true);
        jBexcluir.setEnabled(true);
        jBgravar.setText("Alterar");
        jTFcodigo.setText(String.valueOf(categoria.getICategoria()));
        jTFdescricao.setText(categoria.getDescricao());
        jTFcategoria.setText(categoria.getCategoriaNome());
        if(!categoria.getSituacao().equals("ATIVO")) jCBsituacao.setSelectedIndex(1);
        modoGravar = "EDITAR";
//        nivelSecretario(nivel);
    }

    public void gravar() {
        if (validarCamposVazios()) {
            SuporteCategorias categoria = null;
            jBexcluir.setEnabled(false);
            switch (modoGravar) {
                case "NOVO":
                    categoria = new SuporteCategorias();
                    categoria.setDescricao(jTFdescricao.getText());
                    categoria.setCategoriaNome(jTFcategoria.getText());
                    categoria.setSituacao(String.valueOf(jCBsituacao.getSelectedItem()));
                    categoria.setAlteracao(new java.util.Date());
                    emfCategorias.create(categoria);
                    jBnovo.setEnabled(true);
                    campos(false);
                    tabelaCategorias();
                    break;
                case "EDITAR":
                    categoria = emfCategorias.findSuporteCategorias(Integer.parseInt(jTFcodigo.getText()));
                    categoria.setDescricao(jTFdescricao.getText());
                    categoria.setCategoriaNome(jTFcategoria.getText());
                    categoria.setSituacao(String.valueOf(jCBsituacao.getSelectedItem()));
                    categoria.setAlteracao(new java.util.Date());
                    try {
                        emfCategorias.edit(categoria);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        javax.swing.JOptionPane.showMessageDialog(null, "Não foi possível alterar o cadastro.",
                                "ERRO: 001", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                    campos(false);
                    tabelaCategorias();
                    break;
            }
            jBgravar.setEnabled(false);
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Os campos precisam ser informados.", "Atenção! Campos vazios.",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    public void excluir() {
        try {
            jBexcluir.setEnabled(false);
            int id = Integer.parseInt(jTFcodigo.getText());
            emfCategorias.destroy(id);
            campos(false);
            tabelaCategorias();
        } catch (controle.exceptions.NonexistentEntityException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Não foi possível excluir o registro.", "ERRO E002", javax.swing.JOptionPane.ERROR_MESSAGE);
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
        return jTFcategoria.getText() != null;

//        if (jTFarea.getText() == null) {
//            return false;
//        } else {
//            return true;
//        } 
    }
    
    public void tamanhoColunas(javax.swing.JTable tabela) {
        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBatualizar;
    private javax.swing.JButton jBexcluir;
    private javax.swing.JButton jBgravar;
    private javax.swing.JButton jBnovo;
    private javax.swing.JComboBox jCBsituacao;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFcategoria;
    private javax.swing.JTextField jTFcodigo;
    private javax.swing.JTextField jTFdescricao;
    private javax.swing.JTable jTcategorias;
    // End of variables declaration//GEN-END:variables
}
