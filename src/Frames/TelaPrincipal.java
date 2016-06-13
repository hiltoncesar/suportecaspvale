/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames;

import Frames.Acoes.AcessarHost;
import Frames.Acoes.Anexos;
import Frames.Acoes.Chat;
import Frames.Acoes.ControleDePonto;
import Frames.Cadastros.*;
import controle.SuporteUsuariosJpaController;
import java.awt.Toolkit;
import java.awt.Window;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import util.Minimizar;

/**
 *
 * @author Hilton
 */
public class TelaPrincipal extends javax.swing.JFrame {

    private SuporteUsuariosJpaController emfUsuario;
    private static util.Minimizar min;// = new util.Minimizar(this);
    private int i_usu;
    private final util.Config config = new util.Config();
    private final String usuario;
    private final String usuario_nome;

    public TelaPrincipal(int i_usu, String usuario) {
        this.emfUsuario = new SuporteUsuariosJpaController(new util.Persistencia().emf());
        this.i_usu = i_usu;
        this.usuario = usuario;
        this.usuario_nome = emfUsuario.findSuporteUsuarios(i_usu).getUsuarioNome();
        tema();
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagens/icone_2.png")));        
        min = new Minimizar(this,this.i_usu);
        min.trayH();
        this.setTitle("Suporte - CASPVALE" + "      ||      Usuário: " + usuario);
        jLrodape.setText("CASPVALE - Sistemas Públicos" + "      ||      Usuário: " + usuario);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLrodape = new javax.swing.JLabel();
        jBanexar = new javax.swing.JButton();
        jBacessoRemoto = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        desk = new javax.swing.JDesktopPane();
        jButton1 = new javax.swing.JButton();
        jBponto = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMconfigPonto = new javax.swing.JMenu();
        jMIgerais = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMItema = new javax.swing.JMenuItem();
        jMIsair = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMIcadastrarAcessoRemoto = new javax.swing.JMenuItem();
        jMIareas = new javax.swing.JMenuItem();
        jMIentidades = new javax.swing.JMenuItem();
        jMIcategorias = new javax.swing.JMenuItem();
        jMIperiodos = new javax.swing.JMenuItem();
        jMIusuarios = new javax.swing.JMenuItem();

        setTitle("Suporte - CASPVALE");
        setMinimumSize(new java.awt.Dimension(800, 600));
        setType(java.awt.Window.Type.POPUP);

        jLrodape.setText("CASPVALE - Sistemas Públicos");
        jLrodape.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jBanexar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/docs_64.png"))); // NOI18N
        jBanexar.setToolTipText("ANEXAR ARQUIVO");
        jBanexar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBanexarActionPerformed(evt);
            }
        });

        jBacessoRemoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/access_64.png"))); // NOI18N
        jBacessoRemoto.setToolTipText("ACESSO REMOTO");
        jBacessoRemoto.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jBacessoRemoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBacessoRemotoActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        desk.setBackground(new java.awt.Color(240, 240, 240));
        desk.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        desk.setOpaque(false);

        javax.swing.GroupLayout deskLayout = new javax.swing.GroupLayout(desk);
        desk.setLayout(deskLayout);
        deskLayout.setHorizontalGroup(
            deskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        deskLayout.setVerticalGroup(
            deskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/chat2_64.png"))); // NOI18N
        jButton1.setToolTipText("CHAT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jBponto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ponto_64.png"))); // NOI18N
        jBponto.setToolTipText("REGISTRO DE PONTO");
        jBponto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBpontoActionPerformed(evt);
            }
        });

        jMconfigPonto.setText("Configurações");

        jMIgerais.setText("Gerais");
        jMIgerais.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIgeraisActionPerformed(evt);
            }
        });
        jMconfigPonto.add(jMIgerais);

        jMenuItem1.setText("Configurar Ponto Eletrônico");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMconfigPonto.add(jMenuItem1);

        jMItema.setText("Tema");
        jMItema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMItemaActionPerformed(evt);
            }
        });
        jMconfigPonto.add(jMItema);

        jMIsair.setText("Sair");
        jMIsair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIsairActionPerformed(evt);
            }
        });
        jMconfigPonto.add(jMIsair);

        jMenuBar1.add(jMconfigPonto);

        jMenu2.setText("Cadastros");

        jMIcadastrarAcessoRemoto.setText("Acesso Remoto");
        jMIcadastrarAcessoRemoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIcadastrarAcessoRemotoActionPerformed(evt);
            }
        });
        jMenu2.add(jMIcadastrarAcessoRemoto);

        jMIareas.setText("Áreas");
        jMIareas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIareasActionPerformed(evt);
            }
        });
        jMenu2.add(jMIareas);

        jMIentidades.setText("Entidade");
        jMIentidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIentidadesActionPerformed(evt);
            }
        });
        jMenu2.add(jMIentidades);

        jMIcategorias.setText("Categorias");
        jMIcategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIcategoriasActionPerformed(evt);
            }
        });
        jMenu2.add(jMIcategorias);

        jMIperiodos.setText("Períodos");
        jMIperiodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIperiodosActionPerformed(evt);
            }
        });
        jMenu2.add(jMIperiodos);

        jMIusuarios.setText("Usuários");
        jMIusuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMIusuariosActionPerformed(evt);
            }
        });
        jMenu2.add(jMIusuarios);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBacessoRemoto)
                    .addComponent(jBanexar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jBponto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLrodape, javax.swing.GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
                    .addComponent(desk)))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBacessoRemoto, jBanexar, jBponto, jButton1});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desk)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLrodape, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBacessoRemoto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBanexar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBponto)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBacessoRemoto, jBanexar, jBponto, jButton1});

        setSize(new java.awt.Dimension(816, 638));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMIareasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIareasActionPerformed
        exibeForm(new CadastrarArea(), "cadastrararea");
    }//GEN-LAST:event_jMIareasActionPerformed

    private void jMIcategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIcategoriasActionPerformed
        exibeForm(new CadastrarCategoria(), "cadastrarcategoria");
    }//GEN-LAST:event_jMIcategoriasActionPerformed

    private void jMIentidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIentidadesActionPerformed
        exibeForm(new CadastrarEntidade(), "cadastrarentidade");
    }//GEN-LAST:event_jMIentidadesActionPerformed

    private void jMIusuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIusuariosActionPerformed
        exibeForm(new CadastrarUsuario(i_usu), "cadastrarusuario");
    }//GEN-LAST:event_jMIusuariosActionPerformed

    private void jBacessoRemotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBacessoRemotoActionPerformed
        exibeForm(new AcessarHost(emfUsuario.findSuporteUsuarios(i_usu)), "acessarhost");
    }//GEN-LAST:event_jBacessoRemotoActionPerformed

    private void jBanexarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBanexarActionPerformed
        exibeForm(new Anexos(), "anexo");
        //new Thread(new Tempo_salvarArquivo(new Anexos(),"anexo")).start();
    }//GEN-LAST:event_jBanexarActionPerformed
//   private class Tempo_salvarArquivo implements Runnable {
//       private final JInternalFrame frameThread;
//       private final String nomeFrame;
//       public Tempo_salvarArquivo(JInternalFrame frameThread, String nomeFrame){     
//           this.frameThread = frameThread;
//           this.nomeFrame = nomeFrame;
//       }       
//        @Override
//        public void run() {
//            exibeForm(frameThread,nomeFrame);
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//
//            }
//            //return;
//        }
//    }
//    

    private void jMIgeraisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIgeraisActionPerformed
        ConfiguracoesGerais cg = new ConfiguracoesGerais(new Frames.Login(), rootPaneCheckingEnabled);
        //cg.setVisible(true);
    }//GEN-LAST:event_jMIgeraisActionPerformed

    private void jMIcadastrarAcessoRemotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIcadastrarAcessoRemotoActionPerformed
        exibeForm(new CadastrarAcessoRemoto(), "cadastraracessoremoto");
    }//GEN-LAST:event_jMIcadastrarAcessoRemotoActionPerformed

    private void jMIsairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIsairActionPerformed
        String[] str = {"Sair", "Minimizar"};
        int result = JOptionPane.showOptionDialog(null,
                "Deseja sair da aplicação?", "Sair", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, str, str[1]);
        if (result == 0) {
            this.dispose();
            new util.Status().setStatus(i_usu, "OFFLINE");
            System.exit(0);
        } else {
            this.setVisible(false);
        }
    }//GEN-LAST:event_jMIsairActionPerformed

    private void jMItemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMItemaActionPerformed
        try {
            util.TelaTemas tm = new util.TelaTemas(new Frames.Login(), rootPaneCheckingEnabled);
            tm.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(TelaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMItemaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Chat c = new Chat(i_usu);
        c.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jBpontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBpontoActionPerformed
        exibeForm(new ControleDePonto(i_usu,usuario,usuario_nome), "controledeponto");
    }//GEN-LAST:event_jBpontoActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        exibeForm(new Configuracoes_Ponto(emfUsuario.findSuporteUsuarios(i_usu).getIArea().getIArea()), "configponto");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMIperiodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMIperiodosActionPerformed
        exibeForm(new CadastrarPeriodos(), "cadastraperiodo");
    }//GEN-LAST:event_jMIperiodosActionPerformed

    public void exibeForm(javax.swing.JInternalFrame frame, String tipo) {
        //<editor-fold defaultstate="collapsed" desc="Controle dos Frames">
        /* Método controlador dos JInternalFrames*/
        javax.swing.JInternalFrame[] iframe = desk.getAllFrames();
        util.Centralizar c;
        if (iframe.length <= 0) {
            desk.add(frame);
            c = new util.Centralizar(frame);
            frame.setVisible(true);
        } else {
            switch (tipo) {
                case "cadastrararea":
                    for (int i = 0; i < iframe.length; i++) {
                        if (iframe[i].getName().equals("cadastrararea")) {
                            i = iframe.length + 1;
                            return;
                        }
                    }//fim do for  
                    desk.add(frame);
                    c = new util.Centralizar(frame);
                    frame.setVisible(true);
                    break;
                case "cadastrarcategoria":
                    for (int i = 0; i < iframe.length; i++) {
                        if (iframe[i].getName().equals("cadastrarcategoria")) {
                            i = iframe.length + 1;
                            return;
                        }
                    }//fim do for  
                    desk.add(frame);
                    c = new util.Centralizar(frame);
                    frame.setVisible(true);
                    break;
                case "cadastrarusuario":
                    for (int i = 0; i < iframe.length; i++) {
                        if (iframe[i].getName().equals("cadastrarusuario")) {
                            i = iframe.length + 1;
                            return;
                        }
                    }//fim do for  
                    desk.add(frame);
                    c = new util.Centralizar(frame);
                    frame.setVisible(true);
                    break;
                case "cadastrarentidade":
                    for (int i = 0; i < iframe.length; i++) {
                        if (iframe[i].getName().equals("cadastrarentidade")) {
                            i = iframe.length + 1;
                            return;
                        }
                    }//fim do for  
                    desk.add(frame);
                    c = new util.Centralizar(frame);
                    frame.setVisible(true);
                    break;
                case "cadastraracessoremoto":
                    for (int i = 0; i < iframe.length; i++) {
                        if (iframe[i].getName().equals("cadastraracessoremoto")) {
                            i = iframe.length + 1;
                            return;
                        }
                    }//fim do for  
                    desk.add(frame);
                    c = new util.Centralizar(frame);
                    frame.setVisible(true);
                    break;
                case "acessarhost":
                    for (int i = 0; i < iframe.length; i++) {
                        if (iframe[i].getName().equals("acessarhost")) {
                            i = iframe.length + 1;
                            return;
                        }
                    }//fim do for  
                    desk.add(frame);
                    c = new util.Centralizar(frame);
                    frame.setVisible(true);
                    break;
                case "anexo":
                    for (int i = 0; i < iframe.length; i++) {
                        if (iframe[i].getName().equals("anexo")) {
                            i = iframe.length + 1;
                            return;
                        }
                    }//fim do for  
                    desk.add(frame);
                    c = new util.Centralizar(frame);
                    frame.setVisible(true);
                    break;
                case "controledeponto":
                    for (int i = 0; i < iframe.length; i++) {
                        if (iframe[i].getName().equals("controledeponto")) {
                            i = iframe.length + 1;
                            return;
                        }
                    }//fim do for  
                    desk.add(frame);
                    c = new util.Centralizar(frame);
                    frame.setVisible(true);
                    break;
                case "configponto":
                    for (int i = 0; i < iframe.length; i++) {
                        if (iframe[i].getName().equals("configponto")) {
                            i = iframe.length + 1;
                            return;
                        }
                    }//fim do for  
                    desk.add(frame);
                    c = new util.Centralizar(frame);
                    frame.setVisible(true);
                    break;
                case "cadastraperiodo":
                    for (int i = 0; i < iframe.length; i++) {
                        if (iframe[i].getName().equals("cadastraperiodo")) {
                            i = iframe.length + 1;
                            return;
                        }
                    }//fim do for  
                    desk.add(frame);
                    c = new util.Centralizar(frame);
                    frame.setVisible(true);
                    break; 
            }//fim do switch
        }//fim do else 
        //</editor-fold>
    }//fim do metodo

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
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal(1,"Hilton").setVisible(true);               
               // min.trayH();
            }
        });
    }
    public void tema() {
        try {
            boolean useDarkTexture = false;
            com.jtattoo.plaf.mcwin.McWinLookAndFeel.setTheme("Default");
            UIManager.setLookAndFeel(config.getKey("tema"));
            Window windows[] = Window.getWindows();
            for (int i = 0; i < windows.length; i++) {
                if (windows[i].isDisplayable()) {
                    SwingUtilities.updateComponentTreeUI(windows[i]);
                }
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException");
        } catch (InstantiationException ex) {
            System.out.println("InstantiationException");
        } catch (IllegalAccessException ex) {
            System.out.println("IllegalAccessException");
        } catch (UnsupportedLookAndFeelException ex) {
            System.out.println("UnsupportedLookAndFeelException");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desk;
    private javax.swing.JButton jBacessoRemoto;
    private javax.swing.JButton jBanexar;
    private javax.swing.JButton jBponto;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLrodape;
    private javax.swing.JMenuItem jMIareas;
    private javax.swing.JMenuItem jMIcadastrarAcessoRemoto;
    private javax.swing.JMenuItem jMIcategorias;
    private javax.swing.JMenuItem jMIentidades;
    private javax.swing.JMenuItem jMIgerais;
    private javax.swing.JMenuItem jMIperiodos;
    private javax.swing.JMenuItem jMIsair;
    private javax.swing.JMenuItem jMItema;
    private javax.swing.JMenuItem jMIusuarios;
    private javax.swing.JMenu jMconfigPonto;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
