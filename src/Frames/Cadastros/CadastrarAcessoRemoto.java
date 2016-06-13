/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames.Cadastros;


import controle.SuporteAcessoRemotoJpaController;
import controle.SuporteEntidadesJpaController;
import entidades.SuporteAcessoRemoto;
import entidades.SuporteEntidades;
import java.io.File;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hilton
 */
public final class CadastrarAcessoRemoto extends javax.swing.JInternalFrame {

    private final SuporteEntidadesJpaController emfEntidade = new SuporteEntidadesJpaController(new util.Persistencia().emf());
    private final SuporteAcessoRemotoJpaController emfAcesso = new SuporteAcessoRemotoJpaController(new util.Persistencia().emf());
    private String modoGravar, modoBuscar = "";
    // private final String wts = "C:\\Windows\\system32\\mstsc.exe /v:";
    //private final String navegador = "C:\\Program Files (x86)\\Internet Explorer\\iexplore.exe";
    private int[] arrayEntidades;
    private File f;

    public CadastrarAcessoRemoto() {
        initComponents();
        tabelaAcessoRemoto();
        listarEntidades();
        setFrameIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagens/icone_2_24.png")));
        formatos();
    }
    
    public void formatos() {
//        DecimalFormat formatoValor = new DecimalFormat("#####0.00");
//        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");        
        jTFidtv.setDocument(new util.FormatarCampo(9, "numeros"));
        jTFnome.setDocument(new util.FormatarCampo(9, "textopadrao"));
        
    }

    public DefaultTableModel tabelaAcessoRemoto() {
        //<editor-fold defaultstate="collapsed" desc="Manipulação da Tabela">
        /* Método controlador da tabela*/

        //int iFun = id_funcionario[fu.getSelectedIndex()];
        DefaultTableModel modelo = new DefaultTableModel(null, new String[]{
            "CÓDIGO", "ENTIDADE  |  HOST", "TIPO"}) {
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            @Override
            public boolean isCellEditable(int a, int b) {
                return false;
            }
        };
        String[] linha = new String[]{null, null, null, null};
        int linhas = modelo.getRowCount();

        List<SuporteAcessoRemoto> listaAcesso = null;
        try {
            listaAcesso = emfAcesso.findSuporteAcessoRemotoEntitiesOrdenado();
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
                        modelo.setValueAt(listaAcesso.get(i).getIEntidade().getEntidadeNome()+" | "+listaAcesso.get(i).getNomeAcesso(), l, 1);
                    } catch (NullPointerException erro) {
                        System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaAcesso.get(i).getComando(), l, 2);
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jTFcodigo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jCBsituacao = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        jCBentidades = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jTFidtv = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTFipporta = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTFnome = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTFsenhatv = new javax.swing.JTextField();
        jTFusuario = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTFsenha = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTacessoRemoto = new javax.swing.JTable();
        jBnovo = new javax.swing.JButton();
        jBatualizar = new javax.swing.JButton();
        jBexcluir = new javax.swing.JButton();
        jBgravar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jTFlink = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jCBtipo = new javax.swing.JComboBox();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cadastrar Acesso Remoto");
        setMinimumSize(new java.awt.Dimension(495, 541));
        setName("cadastraracessoremoto"); // NOI18N

        jLabel3.setText("Código:");

        jTFcodigo.setEditable(false);

        jLabel5.setText("Situação:");

        jCBsituacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ATIVO", "DESATIVADO" }));
        jCBsituacao.setEnabled(false);

        jLabel8.setText("Entidade:");

        jCBentidades.setMaximumRowCount(20);
        jCBentidades.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBentidades.setEnabled(false);

        jLabel4.setText("TeamViewer ID:");

        jTFidtv.setEditable(false);

        jLabel6.setText("IP:Porta:");

        jTFipporta.setEditable(false);

        jLabel9.setText("Nome do Host:");

        jTFnome.setEditable(false);

        jLabel11.setText("Team Viewer Senha:");

        jTFsenhatv.setEditable(false);

        jTFusuario.setEditable(false);

        jLabel12.setText("Domínio\\Usuário:");

        jTFsenha.setEditable(false);

        jLabel13.setText("Senha:");

        jTacessoRemoto.setAutoCreateRowSorter(true);
        jTacessoRemoto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CÓDIGO", "USUÁRIO", "NOME", "CARGO", "ÁREA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
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

        jLabel7.setText("Atalho:");

        jTFlink.setEditable(false);

        jLabel14.setText("Tipo:");

        jCBtipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione...", "ÁREA DE TRABALHO REMOTA", "LOGMEIN", "TEAM VIEWER" }));
        jCBtipo.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTFcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(125, 125, 125)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCBsituacao, 0, 106, Short.MAX_VALUE))
                                    .addComponent(jCBentidades, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jTFipporta, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTFidtv, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTFsenhatv, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jTFnome)
                                    .addComponent(jTFlink, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jCBtipo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTFusuario, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTFsenha))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jBnovo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBatualizar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBexcluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBgravar)))
                        .addGap(0, 13, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBatualizar, jBexcluir, jBgravar, jBnovo});

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
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTFnome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFidtv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jTFsenhatv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFipporta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFlink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jTFsenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBtipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBnovo)
                    .addComponent(jBatualizar)
                    .addComponent(jBgravar)
                    .addComponent(jBexcluir)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTacessoRemotoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTacessoRemotoMouseClicked
        selecionar();
    }//GEN-LAST:event_jTacessoRemotoMouseClicked

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
        jTFidtv.setText("");
        jTFipporta.setText("");
        jTFsenha.setText("");
        jTFsenhatv.setText("");
        jTFlink.setText("");
        jCBsituacao.setSelectedIndex(0);
        jCBentidades.setSelectedIndex(0);
        jCBtipo.setSelectedIndex(0);
    }

    public void campos(boolean estado) {
        limparCampos();
        jTFusuario.setEditable(estado);
        jTFnome.setEditable(estado);
        //jTFcomando.setEditable(estado);
        jTFlink.setEditable(estado);
        jTFidtv.setEditable(estado);
        jTFipporta.setEditable(estado);
        jTFsenha.setEditable(estado);
        jTFsenhatv.setEditable(estado);
        jCBsituacao.setEnabled(estado);
        jCBentidades.setEnabled(estado);
        jCBtipo.setEnabled(estado);
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
        tabelaAcessoRemoto();
    }

    public void selecionar() {
        modoGravar = "EDITAR";
        modoBuscar = "";
        jBatualizar.setEnabled(true);
        int i_acesso = Integer.parseInt(String.valueOf(jTacessoRemoto.getValueAt(jTacessoRemoto.getSelectedRow(), 0)));
        SuporteAcessoRemoto acessoRemoto = emfAcesso.findSuporteAcessoRemoto(i_acesso);
        campos(true);
        jBnovo.setEnabled(true);
        jBgravar.setEnabled(true);
        jBexcluir.setEnabled(true);
        jBgravar.setText("Alterar");
        jTFcodigo.setText(String.valueOf(acessoRemoto.getIAcesso()));
        jTFusuario.setText(acessoRemoto.getUsuario());
        jTFnome.setText(acessoRemoto.getNomeAcesso());
        if (acessoRemoto.getIdTeamviewer() != null) {
            jTFidtv.setText(String.valueOf(acessoRemoto.getIdTeamviewer()));
        }
        jTFipporta.setText(acessoRemoto.getIpPorta());
        jTFsenha.setText(acessoRemoto.getSenha());
        jTFsenhatv.setText(acessoRemoto.getSenhaTeamviewer());
        jTFlink.setText(acessoRemoto.getLink());
        if (!acessoRemoto.getSituacao().equals("ATIVO")) {
            jCBsituacao.setSelectedIndex(1);
        }
        int i_entidade = acessoRemoto.getIEntidade().getIEntidade();
        jCBentidades.setSelectedIndex(indexCombo(i_entidade, arrayEntidades));
        if (acessoRemoto.getComando().equals("ÁREA DE TRABALHO REMOTA")) {
            jCBtipo.setSelectedIndex(1);
        } else if (acessoRemoto.getComando().equals("LOGMEIN")) {
            jCBtipo.setSelectedIndex(2);
        }
        else if (acessoRemoto.getComando().equals("TEAM VIEWER")) {
            jCBtipo.setSelectedIndex(3);
        } else jCBtipo.setSelectedIndex(0);
//        nivelSecretario(nivel);
    }

    public SuporteAcessoRemoto gravarCampos(SuporteAcessoRemoto acessoRemoto) {        
        acessoRemoto.setComando(String.valueOf(jCBtipo.getSelectedItem()));
        acessoRemoto.setUsuario(jTFusuario.getText());
        acessoRemoto.setNomeAcesso(jTFnome.getText());
        if (!jTFidtv.getText().equals("")) {
            acessoRemoto.setIdTeamviewer(Integer.parseInt(jTFidtv.getText()));
        }
        acessoRemoto.setIpPorta(jTFipporta.getText());
        acessoRemoto.setSenha(jTFsenha.getText());
        acessoRemoto.setSenhaTeamviewer(jTFsenhatv.getText());
        acessoRemoto.setLink(jTFlink.getText());
        acessoRemoto.setSituacao(String.valueOf(jCBsituacao.getSelectedItem()));
        acessoRemoto.setIEntidade(emfEntidade.findSuporteEntidades(arrayEntidades[jCBentidades.getSelectedIndex()]));
        acessoRemoto.setAlteracao(new java.util.Date());
        return acessoRemoto;
    }

    public void gravar() {
        if (validarCamposVazios()) {
            SuporteAcessoRemoto acessoRemoto = null;
            jBexcluir.setEnabled(false);
            switch (modoGravar) {
                case "NOVO":
                    acessoRemoto = new SuporteAcessoRemoto();
                    acessoRemoto = gravarCampos(acessoRemoto);
                    acessoRemoto.setOnline(false);
                    emfAcesso.create(acessoRemoto);
                    jBnovo.setEnabled(true);
                    campos(false);
                    tabelaAcessoRemoto();
                    break;
                case "EDITAR":
                    acessoRemoto = emfAcesso.findSuporteAcessoRemoto(Integer.parseInt(jTFcodigo.getText()));
                    acessoRemoto = gravarCampos(acessoRemoto);
                    try {
                        emfAcesso.edit(acessoRemoto);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        javax.swing.JOptionPane.showMessageDialog(null, "Não foi possível alterar o cadastro.",
                                "ERRO: 001", javax.swing.JOptionPane.ERROR_MESSAGE);
                    }
                    campos(false);
                    tabelaAcessoRemoto();
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
            emfAcesso.destroy(id);
            campos(false);
            tabelaAcessoRemoto();
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
        if (jTFnome.getText() == null
                || jCBentidades.getSelectedIndex() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public void tamanhoColunas(javax.swing.JTable tabela) {
        tabela.getColumnModel().getColumn(0).setPreferredWidth(5);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBatualizar;
    private javax.swing.JButton jBexcluir;
    private javax.swing.JButton jBgravar;
    private javax.swing.JButton jBnovo;
    private javax.swing.JComboBox jCBentidades;
    private javax.swing.JComboBox jCBsituacao;
    private javax.swing.JComboBox jCBtipo;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTFcodigo;
    private javax.swing.JTextField jTFidtv;
    private javax.swing.JTextField jTFipporta;
    private javax.swing.JTextField jTFlink;
    private javax.swing.JTextField jTFnome;
    private javax.swing.JTextField jTFsenha;
    private javax.swing.JTextField jTFsenhatv;
    private javax.swing.JTextField jTFusuario;
    private javax.swing.JTable jTacessoRemoto;
    // End of variables declaration//GEN-END:variables
}
