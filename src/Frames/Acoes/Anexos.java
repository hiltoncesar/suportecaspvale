/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames.Acoes;

import controle.SuporteAnexosJpaController;
import controle.SuporteCategoriasJpaController;
import controle.SuporteEntidadesJpaController;
import entidades.SuporteAnexos;
import entidades.SuporteCategorias;
import entidades.SuporteEntidades;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hilton
 */
public final class Anexos extends javax.swing.JInternalFrame {

    private final SuporteAnexosJpaController emfAnexos = new SuporteAnexosJpaController(new util.Persistencia().emf());
    private final SuporteEntidadesJpaController emfEntidade = new SuporteEntidadesJpaController(new util.Persistencia().emf());
    private final SuporteCategoriasJpaController emfCategorias = new SuporteCategoriasJpaController(new util.Persistencia().emf());
    private String modoGravar, modoBuscar = "";
    private int[] arrayEntidades, arrayCategorias;
    private File f;

    public Anexos() {
        initComponents();
        listarEntidades();
        listarCategorias();
        tabelaAnexos();
        setFrameIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagens/icone_2_24.png")));

    }

    public DefaultTableModel tabelaAnexos() {        
        //<editor-fold defaultstate="collapsed" desc="Manipulação da Tabela">
        /* Método controlador da tabela*/

        //int iFun = id_funcionario[fu.getSelectedIndex()];
        DefaultTableModel modelo = new DefaultTableModel(null, new String[]{
            "CÓDIGO", "ENTIDADE", "CATEGORIA", "ARQUIVO", "DESCRIÇÃO", "DATA"}) {
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            @Override
            public boolean isCellEditable(int a, int b) {
                return false;
            }
        };
        String[] linha = new String[]{null, null, null, null, null, null};
        int linhas = modelo.getRowCount();

        List<SuporteAnexos> listaAnexos = null;
        try {
            listaAnexos = emfAnexos.findSuporteAnexosEntitiesOrdenado();
//            listaAnexos = emfAnexos.findSuporteAnexosOrdenado();
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Erro ao recuperar informações da tabela\n"
                    + ex.getMessage(), "Erro de Conexão", javax.swing.JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return null;
        }
        while (linhas > 0) {//limpa conteudo da tabela para atulização
            for (int i = linhas; i > 0; i--) {
                modelo.removeRow(i - 1);
                linhas = modelo.getRowCount();
            }//fim do for
        }//fim do while

        int l = 0;
        if (!listaAnexos.isEmpty()) {
            for (int i = 0; i < listaAnexos.size(); i++) {
                try {
                    modelo.addRow(linha);
                    try {
                        modelo.setValueAt(listaAnexos.get(i).getIAnexo(), l, 0);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaAnexos.get(i).getIEntidade().getEntidadeNome(), l, 1);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaAnexos.get(i).getICategoria().getCategoriaNome(), l, 2);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaAnexos.get(i).getDiretorio(), l, 3);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaAnexos.get(i).getAnexoNome(), l, 4);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(dataFormatada(listaAnexos.get(i).getAlteracao()), l, 5);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                } catch (NullPointerException erro) {
                    System.out.println("Atenção! Campos vazios.");
                }
                l++;
            }
        }
        jTanexos.setModel(modelo);
        tamanhoColunas(jTanexos);
        return modelo;
        //</editor-fold>
    }

    public void listarEntidades() {
        //<editor-fold defaultstate="collapsed" desc="Lista de Entidades">
        List<SuporteEntidades> listaEntidades = emfEntidade.findSuporteEntidadesEntitiesOrdenado();
        String[] lista = new String[listaEntidades.size() + 1];
        lista[0] = "Selecione...";
        arrayEntidades = new int[listaEntidades.size() + 1];
        arrayEntidades[0] = 0;
        for (int i = 0; i < lista.length - 1; i++) {
            lista[i + 1] = listaEntidades.get(i).getEntidadeNome();
            arrayEntidades[i + 1] = listaEntidades.get(i).getIEntidade();
        }
//        new util.ComboDinamico(jCBentidades);
        jCBentidades.setModel(new DefaultComboBoxModel(lista));
        //</editor-fold>
    }

    public void listarCategorias() {
        //<editor-fold defaultstate="collapsed" desc="Lista de Entidades">
        List<SuporteCategorias> listarCategorias = emfCategorias.findSuporteCategoriasEntitiesOrdenado();
        String[] lista = new String[listarCategorias.size() + 1];
        lista[0] = "Selecione...";
        arrayCategorias = new int[listarCategorias.size() + 1];
        arrayCategorias[0] = 0;
        for (int i = 0; i < lista.length - 1; i++) {
            lista[i + 1] = listarCategorias.get(i).getCategoriaNome();
            arrayCategorias[i + 1] = listarCategorias.get(i).getICategoria();
        }
//        new util.ComboDinamico(jCBentidades);
        jCBcategorias.setModel(new DefaultComboBoxModel(lista));
        //</editor-fold>
    }

    public int indexCombo(int id, int[] array) {
        //<editor-fold defaultstate="collapsed" desc="Index ComboBox">
        for (int i = 0; i < array.length; i++) {
            if (id == array[i]) {
                return i;
            }
        }
        return -1;
        //</editor-fold>
    }

    public String dataFormatada(Date dt) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formato.format(dt);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTanexos = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jBnovo = new javax.swing.JButton();
        jTFcodigo = new javax.swing.JTextField();
        jBatualizar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jBexcluir = new javax.swing.JButton();
        jCBsituacao = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jBgravar = new javax.swing.JButton();
        jCBentidades = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jTFdiretorio = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jCBcategorias = new javax.swing.JComboBox();
        jBanexar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTAdescricao = new javax.swing.JTextArea();
        jBselecionar = new javax.swing.JButton();
        jProg = new javax.swing.JProgressBar();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Anexar Documento");
        setMinimumSize(new java.awt.Dimension(579, 472));
        setName("anexo"); // NOI18N

        jTanexos.setAutoCreateRowSorter(true);
        jTanexos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "CÓDIGO", "ENTIDADE", "CATEGORIA", "ARQUIVO", "DESCRICAO", "DATA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTanexos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTanexos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTanexosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTanexos);
        if (jTanexos.getColumnModel().getColumnCount() > 0) {
            jTanexos.getColumnModel().getColumn(0).setPreferredWidth(40);
        }

        jLabel3.setText("Código:");

        jBnovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/novo_16.png"))); // NOI18N
        jBnovo.setText("Novo");
        jBnovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBnovoActionPerformed(evt);
            }
        });

        jTFcodigo.setEditable(false);

        jBatualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/atualizar_16.png"))); // NOI18N
        jBatualizar.setText("Atualizar");
        jBatualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBatualizarActionPerformed(evt);
            }
        });

        jLabel5.setText("Situação:");

        jBexcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/deletar_16.png"))); // NOI18N
        jBexcluir.setText("Excluir");
        jBexcluir.setEnabled(false);
        jBexcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBexcluirActionPerformed(evt);
            }
        });

        jCBsituacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ATIVO", "DESATIVADO" }));
        jCBsituacao.setEnabled(false);

        jLabel8.setText("Entidade:");

        jBgravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ok_16.png"))); // NOI18N
        jBgravar.setText("Gravar");
        jBgravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBgravarActionPerformed(evt);
            }
        });

        jCBentidades.setMaximumRowCount(20);
        jCBentidades.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBentidades.setEnabled(false);

        jLabel4.setText("Arquivo:");

        jTFdiretorio.setEditable(false);

        jLabel6.setText("Descrição:");

        jLabel14.setText("Categoria:");

        jCBcategorias.setMaximumRowCount(20);
        jCBcategorias.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBcategorias.setEnabled(false);

        jBanexar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/folder_16.png"))); // NOI18N
        jBanexar.setEnabled(false);
        jBanexar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBanexarActionPerformed(evt);
            }
        });

        jTAdescricao.setColumns(20);
        jTAdescricao.setLineWrap(true);
        jTAdescricao.setRows(1);
        jTAdescricao.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jTAdescricao);

        jBselecionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/open_16.png"))); // NOI18N
        jBselecionar.setEnabled(false);
        jBselecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBselecionarActionPerformed(evt);
            }
        });

        jProg.setForeground(new java.awt.Color(255, 102, 0));
        jProg.setRequestFocusEnabled(false);
        jProg.setString("");
        jProg.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jBnovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBatualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBexcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBgravar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel3)
                            .addComponent(jLabel14)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTFcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCBsituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jCBentidades, 0, 414, Short.MAX_VALUE)
                                        .addComponent(jCBcategorias, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jTFdiretorio)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jBanexar)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jBselecionar))))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addComponent(jProg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBatualizar, jBexcluir, jBgravar, jBnovo});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBanexar, jBselecionar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTFcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBsituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBentidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBcategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jBanexar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTFdiretorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(jBselecionar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jBnovo)
                            .addComponent(jBatualizar)
                            .addComponent(jBgravar)
                            .addComponent(jBexcluir))
                        .addGap(12, 12, 12))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBanexar, jBselecionar});

        setBounds(0, 0, 579, 472);
    }// </editor-fold>//GEN-END:initComponents

    private void jTanexosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTanexosMouseClicked
        selecionar();
    }//GEN-LAST:event_jTanexosMouseClicked

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
        new Thread(new Tempo_gravarArquivo()).start();
    }//GEN-LAST:event_jBgravarActionPerformed

    private void jBanexarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBanexarActionPerformed
        SelecionarArquivo c = new SelecionarArquivo(new Frames.Login(), rootPaneCheckingEnabled, "anexar", "");
        c.setVisible(true);
        this.f = c.file;
        if (f != null) {
            jTFdiretorio.setText(f.getName());
            jTAdescricao.setText(f.getName());
        }
//        System.out.println(f.getAbsolutePath());
//        System.out.println(f.getName());
    }//GEN-LAST:event_jBanexarActionPerformed

    private void jBselecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBselecionarActionPerformed
        new Thread(new Tempo_salvarArquivo()).start();
    }//GEN-LAST:event_jBselecionarActionPerformed
    private class Tempo_salvarArquivo implements Runnable {

        @Override
        public void run() {
            salvarArquivo();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {

            }
            return;
        }
    }

    private class Tempo_gravarArquivo implements Runnable {

        @Override
        public void run() {
            gravarArquivo();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {

            }       
            return;
        }
    }
    
    private class Tempo_tabela implements Runnable {        
        @Override
        public void run() {
            System.out.println("asdfasdfafsadf");
            tabelaAnexos();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {

            }
        }
    }

    public void limparCampos() {
        jTFcodigo.setText("");
        jTFdiretorio.setText("");
        jTAdescricao.setText("");
        jCBsituacao.setSelectedIndex(0);
        jCBentidades.setSelectedIndex(0);
        jCBcategorias.setSelectedIndex(0);
    }

    public void campos(boolean estado) {
        limparCampos();
        //jTFdiretorio.setEditable(estado);
        jTAdescricao.setEditable(estado);
        jCBsituacao.setEnabled(estado);
        jCBentidades.setEnabled(estado);
        jCBcategorias.setEnabled(estado);
        jBselecionar.setEnabled(estado);
    }

    public void novo() {
        limparCampos();
        campos(true);
        jBnovo.setEnabled(false);
        jBgravar.setEnabled(true);
        jBanexar.setEnabled(true);
        jBexcluir.setEnabled(false);
        jCBsituacao.setEnabled(false);
        jBselecionar.setEnabled(false);
        jBgravar.setText("Gravar");
        modoGravar = "NOVO";
    }

    public void atualizar() {
        jProg.setString("");
        limparCampos();
        campos(false);
        jBnovo.setEnabled(true);
        jBgravar.setEnabled(false);
        jBexcluir.setEnabled(false);
        jBanexar.setEnabled(false);
        jBselecionar.setEnabled(false);
        tabelaAnexos();
    }

    public void selecionar() {
        jProg.setString("");
        modoGravar = "EDITAR";
        modoBuscar = "";
        jBatualizar.setEnabled(true);
        int i_anexo = Integer.parseInt(String.valueOf(jTanexos.getValueAt(jTanexos.getSelectedRow(), 0)));
        SuporteAnexos anexos = emfAnexos.findSuporteAnexos(i_anexo);
        campos(true);
        jBnovo.setEnabled(true);
        jBgravar.setEnabled(true);
        jBexcluir.setEnabled(true);
        jBanexar.setEnabled(false);
        jBgravar.setText("Alterar");
        jTFcodigo.setText(String.valueOf(anexos.getIAnexo()));
        jTFdiretorio.setText(anexos.getDiretorio());
        jTAdescricao.setText(anexos.getAnexoNome());
        if (!anexos.getSituacao().equals("ATIVO")) {
            jCBsituacao.setSelectedIndex(1);
        }
        int i_entidade = anexos.getIEntidade().getIEntidade();
        jCBentidades.setSelectedIndex(indexCombo(i_entidade, arrayEntidades));
        int i_categoria = anexos.getICategoria().getICategoria();
        jCBcategorias.setSelectedIndex(indexCombo(i_categoria, arrayCategorias));
//        nivelSecretario(nivel);
    }

    public SuporteAnexos gravarCampos(SuporteAnexos anexo) throws FileNotFoundException, IOException {
        anexo.setAnexoNome(jTAdescricao.getText());
        anexo.setSituacao(String.valueOf(jCBsituacao.getSelectedItem()));
        anexo.setICategoria(emfCategorias.findSuporteCategorias(arrayCategorias[jCBcategorias.getSelectedIndex()]));
        anexo.setIEntidade(emfEntidade.findSuporteEntidades(arrayEntidades[jCBentidades.getSelectedIndex()]));
        anexo.setAlteracao(new java.util.Date());
        return anexo;
    }

    public void gravar() throws FileNotFoundException, IOException {
        if (validarCamposVazios()) {
            SuporteAnexos anexo = null;
            jBexcluir.setEnabled(false);
            switch (modoGravar) {
                case "NOVO":
                    anexo = new SuporteAnexos();
                    //converte o objeto file em array de bytes
                    InputStream is = new FileInputStream(f);
                    byte[] bytes = new byte[(int) f.length()];
                    int offset = 0;
                    int numRead = 0;
                    while (offset < bytes.length
                            && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                        offset += numRead;
                    }
                    anexo.setArquivo(bytes);
                    anexo.setDiretorio(jTFdiretorio.getText());
                    anexo = gravarCampos(anexo);
                    emfAnexos.create(anexo);
                    jBnovo.setEnabled(true);
                    jBanexar.setEnabled(true);
                    campos(false);
                    tabelaAnexos();
                    break;
                case "EDITAR":
                    anexo = emfAnexos.findSuporteAnexos(Integer.parseInt(jTFcodigo.getText()));
                    anexo = gravarCampos(anexo);
                    try {
                        emfAnexos.edit(anexo);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        javax.swing.JOptionPane.showMessageDialog(null, "Não foi possível alterar o cadastro.",
                                "ERRO: 001", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                    campos(false);
                    tabelaAnexos();
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
            emfAnexos.destroy(id);
            campos(false);
            tabelaAnexos();
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
        if (jTFdiretorio.getText() == null
                || jCBentidades.getSelectedIndex() == 0
                || jCBcategorias.getSelectedIndex() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void tamanhoColunas(javax.swing.JTable tabela) {
        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
    }

    public void salvarArquivo() {
        String nome = (String) jTanexos.getValueAt(jTanexos.getSelectedRow(), 3);
        SelecionarArquivo c = new SelecionarArquivo(new Frames.Login(), rootPaneCheckingEnabled, "selecionar", nome);
        c.setVisible(true);
        this.f = c.file;
        if (f != null) {
            jProg.setIndeterminate(true);
            jProg.setString("TRABALHANDO");
            ResultSet rs = emfAnexos.recuperarArquivo(Integer.parseInt(jTFcodigo.getText()));
            try {
                if (rs.next()) {
                    byte[] bytes = rs.getBytes("arquivo");
                    //converte o array de bytes em file
                    f = new File(f.getAbsolutePath());
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bytes);
                    fos.close();
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Anexos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Anexos.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Anexos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        jProg.setIndeterminate(false);
        jProg.setString("CONCLUÍDO");
    }

    public void gravarArquivo() {
        jProg.setIndeterminate(true);
        jProg.setString("TRABALHANDO");
        try {
            gravar();
        } catch (IOException ex) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Erro ao anexar arquivo.\n"
                    + ex.getCause().getMessage(), "Erro", javax.swing.JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Anexos.class.getName()).log(Level.SEVERE, null, ex);
        }
        jProg.setIndeterminate(false);
        jProg.setString("CONCLUÍDO");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBanexar;
    private javax.swing.JButton jBatualizar;
    private javax.swing.JButton jBexcluir;
    private javax.swing.JButton jBgravar;
    private javax.swing.JButton jBnovo;
    private javax.swing.JButton jBselecionar;
    private javax.swing.JComboBox jCBcategorias;
    private javax.swing.JComboBox jCBentidades;
    private javax.swing.JComboBox jCBsituacao;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JProgressBar jProg;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTAdescricao;
    private javax.swing.JTextField jTFcodigo;
    private javax.swing.JTextField jTFdiretorio;
    private javax.swing.JTable jTanexos;
    // End of variables declaration//GEN-END:variables
}
