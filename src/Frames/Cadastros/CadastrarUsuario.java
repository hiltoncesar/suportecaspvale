/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames.Cadastros;

import controle.SuporteAreasJpaController;
import controle.SuporteUsuariosJpaController;
import entidades.SuporteAreas;
import entidades.SuporteUsuarios;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hilton
 */
public final class CadastrarUsuario extends javax.swing.JInternalFrame {

    private final SuporteUsuariosJpaController emfUsuario = new SuporteUsuariosJpaController(new util.Persistencia().emf());
    private String modoGravar, modoBuscar = "";
    private final SuporteAreasJpaController emfArea = new SuporteAreasJpaController(new util.Persistencia().emf());
    private int[] arrayAreas;
    private final int i_usuario;

    public CadastrarUsuario(int i_usuario) {
        this.i_usuario = i_usuario;
        initComponents();
        tabelaUsuarios();
        listarAreas();
        setFrameIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagens/icone_2_16.png")));
        jTFnome.setDocument(new util.FormatarCampo(3, "textopadrao"));
        jTFcargo.setDocument(new util.FormatarCampo(3, "textopadrao"));
        jTFusuario.setDocument(new util.FormatarCampo(20, "login"));
    }

    public DefaultTableModel tabelaUsuarios() {
        //<editor-fold defaultstate="collapsed" desc="Manipulação da Tabela">
        /* Método controlador da tabela*/

        //int iFun = id_funcionario[fu.getSelectedIndex()];
        DefaultTableModel modelo = new DefaultTableModel(null, new String[]{
            "CÓDIGO", "USUÁRIO", "NOME", "CARGO", "ÁREA"}) {
            @Override
            public boolean isCellEditable(int a, int b) {
                return false;
            }
        };
        String[] linha = new String[]{null, null, null, null, null};
        int linhas = modelo.getRowCount();

        List<SuporteUsuarios> listaUsuarios = null;
        try {
            listaUsuarios = emfUsuario.findSuporteUsuariosEntitiesOrdenado();
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex.getCause().getMessage(), "Erro de Conexão", javax.swing.JOptionPane.ERROR_MESSAGE);
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
                        modelo.setValueAt(listaUsuarios.get(i).getUsuario(), l, 1);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaUsuarios.get(i).getUsuarioNome(), l, 2);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaUsuarios.get(i).getCargo(), l, 3);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaUsuarios.get(i).getIArea().getAreaNome(), l, 4);
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

    public void listarAreas() {
        List<SuporteAreas> listaAreas = emfArea.findSuporteAreasEntitiesOrdenado();
        String[] lista = new String[listaAreas.size() + 1];
        lista[0] = "Selecione...";
        arrayAreas = new int[listaAreas.size() + 1];
        arrayAreas[0] = 0;
        for (int i = 0; i < lista.length - 1; i++) {
            lista[i + 1] = listaAreas.get(i).getAreaNome();
            arrayAreas[i + 1] = listaAreas.get(i).getIArea();
        }
//        new util.ComboDinamico(jCBbairro);
        jCBareas.setModel(new DefaultComboBoxModel(lista));
    }

    public int indexCombo(int id, int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (id == array[i]) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTFcodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTusuarios = new javax.swing.JTable();
        jBnovo = new javax.swing.JButton();
        jBatualizar = new javax.swing.JButton();
        jBexcluir = new javax.swing.JButton();
        jBgravar = new javax.swing.JButton();
        jTFusuario = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jCBsituacao = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jTFnome = new javax.swing.JTextField();
        jTFcargo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPFsenha1 = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        jCBareas = new javax.swing.JComboBox();
        jPFsenha2 = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cadastrar Usuário");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/icone_2.png"))); // NOI18N
        setMinimumSize(new java.awt.Dimension(569, 448));
        setName("cadastrarusuario"); // NOI18N

        jTFcodigo.setEditable(false);

        jLabel3.setText("Código:");

        jLabel2.setText("Usuário:");

        jLabel4.setText("Senha:");

        jTusuarios.setAutoCreateRowSorter(true);
        jTusuarios.setModel(new javax.swing.table.DefaultTableModel(
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
                "CÓDIGO", "USUÁRIO", "NOME", "CARGO", "ÁREA"
            }
        ));
        jTusuarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTusuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTusuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTusuarios);
        if (jTusuarios.getColumnModel().getColumnCount() > 0) {
            jTusuarios.getColumnModel().getColumn(0).setPreferredWidth(37);
            jTusuarios.getColumnModel().getColumn(2).setPreferredWidth(111);
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
        jBexcluir.setText("Desativar");
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

        jTFusuario.setEditable(false);

        jLabel5.setText("Situação:");

        jCBsituacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ATIVO", "DESATIVADO", "ONLINE", "OFFLINE" }));
        jCBsituacao.setEnabled(false);

        jLabel6.setText("Nome:");

        jTFnome.setEditable(false);

        jTFcargo.setEditable(false);

        jLabel7.setText("Cargo:");

        jPFsenha1.setEditable(false);

        jLabel8.setText("Área:");

        jCBareas.setMaximumRowCount(20);
        jCBareas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBareas.setEnabled(false);

        jPFsenha2.setEditable(false);

        jLabel9.setText("Confirmar:");

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
                .addGap(0, 147, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTFcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPFsenha1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPFsenha2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTFusuario)
                                    .addComponent(jTFnome)
                                    .addComponent(jTFcargo)
                                    .addComponent(jCBareas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCBsituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
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
                    .addComponent(jTFusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jPFsenha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPFsenha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTFnome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTFcargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jCBareas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
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

    private void jTusuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTusuariosMouseClicked
        selecionar();
    }//GEN-LAST:event_jTusuariosMouseClicked

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
        jTFusuario.setText("");
        jTFnome.setText("");
        jTFcargo.setText("");
        jPFsenha1.setText("");
        jPFsenha2.setText("");
        jCBareas.setSelectedIndex(0);
        jCBsituacao.setSelectedIndex(0);
    }

    public void campos(boolean estado) {
        limparCampos();
        jTFusuario.setEditable(estado);
        jTFnome.setEditable(estado);
        jTFcargo.setEditable(estado);
        jPFsenha1.setEditable(estado);
        jPFsenha2.setEditable(estado);
        //jCBsituacao.setEnabled(estado);
        jCBareas.setEnabled(estado);
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
        tabelaUsuarios();
    }

    public void selecionar() {
        modoGravar = "EDITAR";
        modoBuscar = "";
        jBatualizar.setEnabled(true);
        int i_usu = 0;
        if (this.i_usuario == 1) {
            i_usu = Integer.parseInt(String.valueOf(jTusuarios.getValueAt(jTusuarios.getSelectedRow(), 0)));
        } else {
            i_usu = this.i_usuario;
        }
        SuporteUsuarios usuario = emfUsuario.findSuporteUsuarios(i_usu);
        campos(true);
        jBnovo.setEnabled(true);
        jBgravar.setEnabled(true);
        jBexcluir.setEnabled(true);
        jBgravar.setText("Alterar");
        jTFcodigo.setText(String.valueOf(usuario.getIUsuario()));
        jTFusuario.setText(usuario.getUsuario());
        jTFnome.setText(usuario.getUsuarioNome());
        jTFcargo.setText(usuario.getCargo());
        switch (usuario.getSituacao()) {
            case "ATIVO":
                jCBsituacao.setSelectedIndex(0);
                break;
            case "DESATIVADO":
                jCBsituacao.setSelectedIndex(1);
                break;
            case "ONLINE":
                jCBsituacao.setSelectedIndex(2);
                break;
            case "OFFLINE":
                jCBsituacao.setSelectedIndex(3);
                break;
        }
        int i_area = usuario.getIArea().getIArea();
        jCBareas.setSelectedIndex(indexCombo(i_area, arrayAreas));
        jTFusuario.setEditable(false);
//        nivelSecretario(nivel);
    }

    public void gravar() {
        if (validarCamposVazios() && validarSenha()) {
            SuporteUsuarios usuario = null;
            jBexcluir.setEnabled(false);
            switch (modoGravar) {
                case "NOVO":
                    usuario = new SuporteUsuarios();
                    usuario.setUsuario(jTFusuario.getText());
                    gravarCampos(usuario);
                    usuario.setSituacao("ATIVO");
                    emfUsuario.create(usuario);
                    jBnovo.setEnabled(true);
                    campos(false);
                    tabelaUsuarios();
                    break;
                case "EDITAR":
                    usuario = emfUsuario.findSuporteUsuarios(Integer.parseInt(jTFcodigo.getText()));
                    gravarCampos(usuario);
                    try {
                        emfUsuario.edit(usuario);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        javax.swing.JOptionPane.showMessageDialog(null, "Não foi possível alterar o cadastro.",
                                "ERRO: 001", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                    campos(false);
                    tabelaUsuarios();
                    break;
            }
            jBgravar.setEnabled(false);
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Os campos precisam ser informados\n"
                    + "ou a combinação de senha não confere.", "Atenção! Registro não alterado",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    public void excluir() {
        try {
            int i_usu = 0;
            if (this.i_usuario == 1) {
                i_usu = Integer.parseInt(String.valueOf(jTusuarios.getValueAt(jTusuarios.getSelectedRow(), 0)));
            } else {
                i_usu = this.i_usuario;
            }
            SuporteUsuarios usuario = emfUsuario.findSuporteUsuarios(i_usu);
            jBexcluir.setEnabled(false);
            usuario.setSituacao("DESATIVADO");
            emfUsuario.edit(usuario);
            campos(false);
            tabelaUsuarios();
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

    public void gravarCampos(SuporteUsuarios usuario) {
        usuario.setUsuarioNome(jTFnome.getText());
        usuario.setCargo(jTFcargo.getText());
        usuario.setSenha(new util.Md5().md5(Arrays.toString(jPFsenha1.getPassword())));
        //usuario.setSituacao(String.valueOf(jCBsituacao.getSelectedItem()));
        usuario.setIArea(emfArea.findSuporteAreas(arrayAreas[jCBareas.getSelectedIndex()]));
        usuario.setAlteracao(new java.util.Date());
    }

    public boolean validarCamposVazios() {
        if (jTFusuario.getText() == null || jPFsenha1.getText().equals("") || jPFsenha2.getText().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public boolean validarSenha() {
        System.out.println(Arrays.toString(jPFsenha1.getPassword()));
        System.out.println(Arrays.toString(jPFsenha2.getPassword()));

        if (Arrays.equals(jPFsenha1.getPassword(), jPFsenha2.getPassword())) {
            System.out.println("true");
            return true;
        } else {
            System.out.println("false");
            return false;
        }
    }

    public void tamanhoColunas(javax.swing.JTable tabela) {
        tabela.getColumnModel().getColumn(0).setPreferredWidth(34);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBatualizar;
    private javax.swing.JButton jBexcluir;
    private javax.swing.JButton jBgravar;
    private javax.swing.JButton jBnovo;
    private javax.swing.JComboBox jCBareas;
    private javax.swing.JComboBox jCBsituacao;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPasswordField jPFsenha1;
    private javax.swing.JPasswordField jPFsenha2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFcargo;
    private javax.swing.JTextField jTFcodigo;
    private javax.swing.JTextField jTFnome;
    private javax.swing.JTextField jTFusuario;
    private javax.swing.JTable jTusuarios;
    // End of variables declaration//GEN-END:variables
}
