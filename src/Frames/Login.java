/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames;

import controle.SuporteUsuariosJpaController;
import entidades.SuporteUsuarios;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import org.postgresql.util.PSQLException;

/**
 *
 * @author servbeta11
 */
public final class Login extends javax.swing.JFrame {

    private SuporteUsuariosJpaController emfUsuario;
    private String usuarioNome;
    private String senha;
    private String situacao;
    private entidades.SuporteUsuarios usuarioLogin;
    public int nivel, i_usuario;
    private final util.Config config = new util.Config();

    public Login() {
        //setLookAndFeel();
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagens/icone_2.png")));
        jTFusuarios.setDocument(new util.FormatarCampo(3, "textominimizado"));
        carregaConfig();
    }

    public int validaUsuario(String usuario) {
        List<entidades.SuporteUsuarios> lista = emfUsuario.findSuporteUsuariosEntities();
        for (int i = 0; i < lista.size(); i++) {
            if (usuario.equals(lista.get(i).getUsuario())) {
                this.usuarioNome = usuario;
                this.i_usuario = emfUsuario.findSuporteUsuarios(emfUsuario.findSuporteUsuariosEntities().get(i).getIUsuario()).getIUsuario();
                this.usuarioLogin = emfUsuario.findSuporteUsuarios(emfUsuario.findSuporteUsuariosEntities().get(i).getIUsuario());
                //this.nivel = controleL().findLogin(controleL().findLoginEntities().get(i).getILogin()).getNivel();
                this.senha = emfUsuario.findSuporteUsuarios(emfUsuario.findSuporteUsuariosEntities().get(i).getIUsuario()).getSenha();
                this.situacao = emfUsuario.findSuporteUsuarios(emfUsuario.findSuporteUsuariosEntities().get(i).getIUsuario()).getSituacao();
                jLerro.setText("Usuário localizado!");
                return 1;
            }
        }
        jTFusuarios.setText("");
        jLerro.setText("Usuário não cadastrado.");
        return 0;
    }

    public int validaSenha(String senha) {
        entidades.SuporteUsuarios usu = this.usuarioLogin;
        System.out.println("Senha cadastrada: " + usu.getSenha());
        System.out.println("Senha informada: " + senha);
        if (usu.getSenha().equals(senha) && !this.situacao.equals("DESATIVADO")) {
            jLerro.setText(jLerro.getText() + "  |  Senha validada");
            return 1;
        }
        jLerro.setText(jLerro.getText() + "  |  Senha inválida");
        return 0;
    }

    public void abreSistema() {
        emfUsuario = new SuporteUsuariosJpaController(new util.Persistencia().emf());
        int zerado = -1;
        try{
        zerado = emfUsuario.getSuporteUsuariosCount();
        } catch(Exception ex){
            JOptionPane.showMessageDialog(null,ex.getCause().getMessage());
            return;
        }
            if (zerado != 0) {
                String senhaLocal = new util.Md5().md5(Arrays.toString(jPFsenha.getPassword()));
                try {
                    if (validaUsuario(jTFusuarios.getText()) == 1 && validaSenha(senhaLocal) == 1) {
                        TelaPrincipal tela = new TelaPrincipal(this.i_usuario, this.usuarioNome);
                        this.dispose();
                        tela.setVisible(true);
                        new util.Status().setStatus(i_usuario, "ONLINE");
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                    String erro = "Conexão negada.\nVerifique se o nome da máquina e a porta estão corretos e se o postmaster está aceitando conexões TCP/IP. \nError Code: 0";
                    JOptionPane.showMessageDialog(null, erro, "Erro de conexão", JOptionPane.ERROR_MESSAGE);
                }
                lembrar(jCBlembrar.isSelected());
            } else {
                String erro = "Não existe usuário cadastrado no sistema.\n"
                        + "Sera cadastrado o usuário ADMINISTRADOR para o acesso.\n\n"
                        + "Nome do usuário: admin\n"
                        + "Senha: admin";
                JOptionPane.showMessageDialog(null, erro, "ABRIR SISTEMA", JOptionPane.INFORMATION_MESSAGE);
                SuporteUsuarios usuario = new SuporteUsuarios();
                usuario.setUsuarioNome("ADMINISTRADOR");
                usuario.setUsuario("admin");
                jPFsenha.setText("admin");
                usuario.setSenha(new util.Md5().md5(Arrays.toString(jPFsenha.getPassword())));
                usuario.setSituacao("ATIVO");
                usuario.setCargo("ADMINISTRADOR DO SISTEMA");
                usuario.setAlteracao(new java.util.Date());
                emfUsuario.create(usuario);
                SuporteUsuarios usuNovo = emfUsuario.findSuporteUsuariosEntities().get(0);
                TelaPrincipal tela = new TelaPrincipal(usuNovo.getIUsuario(), usuNovo.getUsuario());
                this.dispose();
                tela.setVisible(true);
                new util.Status().setStatus(usuNovo.getIUsuario(), "ONLINE");
            }        
    }

    public void lembrar(boolean opcao) {
        if (opcao == true) {
            config.setKey("login", jTFusuarios.getText());
            config.setKey("senha", String.valueOf(jPFsenha.getPassword()));
        } else {
            config.setKey("login", "");
            config.setKey("senha", "");
        }
    }

    public void carregaConfig() {
        if (!config.getKey("login").equals("")) {
            jTFusuarios.setText(config.getKey("login"));
            jPFsenha.setText(config.getKey("senha"));
            jCBlembrar.setSelected(true);
        }
        config.setKey("status", "ONLINE");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jTFusuarios = new javax.swing.JTextField();
        jPFsenha = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jBok = new javax.swing.JButton();
        jBcancelar = new javax.swing.JButton();
        jCBlembrar = new javax.swing.JCheckBox();
        jLerro = new javax.swing.JLabel();
        jLconfig = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Suporte CaspVale");
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(243, 249));
        setMinimumSize(new java.awt.Dimension(243, 249));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPFsenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPFsenhaActionPerformed(evt);
            }
        });

        jLabel1.setText("Usuário:");

        jLabel2.setText("Senha:");

        jBok.setText("OK");
        jBok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBokActionPerformed(evt);
            }
        });

        jBcancelar.setText("Cancelar");
        jBcancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBcancelarActionPerformed(evt);
            }
        });

        jCBlembrar.setBackground(new java.awt.Color(255, 255, 255));
        jCBlembrar.setFont(new java.awt.Font("sansserif", 0, 10)); // NOI18N
        jCBlembrar.setText("Lembrar usuário e senha");

        jLerro.setBackground(new java.awt.Color(255, 255, 255));
        jLerro.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLerro.setForeground(new java.awt.Color(204, 0, 0));

        jLconfig.setBackground(new java.awt.Color(255, 102, 102));
        jLconfig.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLconfig.setForeground(new java.awt.Color(102, 255, 51));
        jLconfig.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/conf_16.png"))); // NOI18N
        jLconfig.setToolTipText("Painel de Configurações");
        jLconfig.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLconfigMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPFsenha)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jBok, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBcancelar))
                            .addComponent(jTFusuarios))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCBlembrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLconfig)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLerro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBcancelar, jBok});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFusuarios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPFsenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBcancelar)
                    .addComponent(jBok))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jCBlembrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLconfig, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLerro, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/caspvale.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 23, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(259, 271));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jPFsenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPFsenhaActionPerformed
        abreSistema();
    }//GEN-LAST:event_jPFsenhaActionPerformed

    private void jBcancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBcancelarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jBcancelarActionPerformed

    private void jBokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBokActionPerformed
        abreSistema();
    }//GEN-LAST:event_jBokActionPerformed

    private void jLconfigMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLconfigMouseClicked
//        String result = "";
//        result = JOptionPane.showInputDialog(null, "Infome o código de acesso","Painel de Configurações", JOptionPane.QUESTION_MESSAGE);
//        if (result.equals(new util.Config().getKey("codigoAcesso"))) {
//            ConfiguracoesGerais cg = new ConfiguracoesGerais(new Frames.Login(), rootPaneCheckingEnabled);
//            cg.setVisible(true);
//        } else {
//            JOptionPane.showMessageDialog(null, "Código inválido!","Painel de Configurações", JOptionPane.ERROR_MESSAGE);
//        }

        ConfiguracoesGerais cg = new ConfiguracoesGerais(new Frames.Login(), rootPaneCheckingEnabled);
        //cg.setVisible(true);
    }//GEN-LAST:event_jLconfigMouseClicked

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

                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBcancelar;
    private javax.swing.JButton jBok;
    private javax.swing.JCheckBox jCBlembrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLconfig;
    private javax.swing.JLabel jLerro;
    private javax.swing.JPasswordField jPFsenha;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTFusuarios;
    // End of variables declaration//GEN-END:variables
}
