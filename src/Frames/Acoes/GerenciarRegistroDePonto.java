/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames.Acoes;

import controle.SuporteIntercorrenciaJpaController;
import controle.SuportePeriodosJpaController;
import controle.SuportePontoeletronicoJpaController;
import controle.SuporteUsuariosJpaController;
import entidades.SuporteIntercorrencia;
import entidades.SuportePeriodos;
import entidades.SuportePontoeletronico;
import entidades.SuporteUsuarios;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hilton
 */
public class GerenciarRegistroDePonto extends javax.swing.JInternalFrame {

    private final SuportePeriodosJpaController emfPeriodo = new SuportePeriodosJpaController(new util.Persistencia().emf());
    private final SuporteUsuariosJpaController emfUsuario = new SuporteUsuariosJpaController(new util.Persistencia().emf());
    private final String usuario, usuario_nome;
    private final int i_usuario, i_area;
    private int[] arrayPeriodos;
    private int[] arrayUsuarios;

    public GerenciarRegistroDePonto(int i_usuario, String usuario, String usuario_nome, int i_area) {
        initComponents();
        setFrameIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagens/icone_2_24.png")));
        this.i_usuario = i_usuario;
        this.usuario = usuario;
        this.usuario_nome = usuario_nome;
        this.i_area = i_area;
        listarPeriodos();
        tabelaPonto();
        listarFuncionarios();
        if (this.i_area == 7) {
            jCBfuncionario.setEnabled(true);
        }
    }

    public void listarFuncionarios() {
        List<SuporteUsuarios> listaUser = emfUsuario.findSuporteUsuariosEntitiesOrdenado();
        String[] lista = new String[listaUser.size() + 1];
        lista[0] = "TODOS";
        arrayUsuarios = new int[listaUser.size() + 1];
        arrayUsuarios[0] = 0;
        for (int i = 0; i < lista.length - 1; i++) {
            lista[i + 1] = listaUser.get(i).getUsuarioNome();
            arrayUsuarios[i + 1] = listaUser.get(i).getIUsuario();
        }
        jCBfuncionario.setModel(new DefaultComboBoxModel(lista));
        jCBfuncionario.setSelectedItem(usuario_nome.trim());
    }

    public void listarPeriodos() {
        List<SuportePeriodos> listaPeriodos = emfPeriodo.findSuportePeriodosEntitiesOrdenado();
        String[] lista = new String[listaPeriodos.size() + 1];
        lista[0] = "TODOS";
        arrayPeriodos = new int[listaPeriodos.size() + 1];
        arrayPeriodos[0] = 0;
        for (int i = 0; i < lista.length - 1; i++) {
            lista[i + 1] = listaPeriodos.get(i).getMesReferencia() + " / " + listaPeriodos.get(i).getExercicio();
            arrayPeriodos[i + 1] = listaPeriodos.get(i).getIPeriodo();
        }
        jCBperiodos.setModel(new DefaultComboBoxModel(lista));
        jCBperiodos.setSelectedIndex(0);
    }

    public String formato(Date dt, String tipo) {
        SimpleDateFormat formato = new SimpleDateFormat(tipo);
        return formato.format(dt);
    }

    public DefaultTableModel tabelaPonto() {
        //<editor-fold defaultstate="collapsed" desc="Manipulação da Tabela">
        /* Método controlador da tabela*/

        //int iFun = id_funcionario[fu.getSelectedIndex()];
        DefaultTableModel modelo = new DefaultTableModel(null, new String[]{
            "ID", "DATA", "ENTRADA", "ALMOÇO", "RETORNO", "SAÍDA", "ALTERAÇAO", "SITUAÇÃO"}) {
//            Class[] types = new Class[]{
//                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
//            };
            @Override
            public boolean isCellEditable(int a, int b) {
                return false;
            }
        };
        String[] linha = new String[]{null, null, null, null, null, null, null, null};
        int linhas = modelo.getRowCount();

        String nivel = "";
        String consulta = "";
        int user = 0;
        int index_func = jCBfuncionario.getSelectedIndex();

        if (index_func == 0) {
            nivel = "geral";
        } else {
            nivel = "usuario";
            user = arrayUsuarios[index_func];
        }

        if (jCBperiodos.getSelectedIndex() != 0) {
            consulta = nivel + ".referencia";
        } else {
            consulta = nivel + ".geral";
        }

        SuportePontoeletronicoJpaController emf = new SuportePontoeletronicoJpaController(new util.Persistencia().emf());
        List<SuportePontoeletronico> listaPonto = emf.findSuportePontoeletronicoEntitiesFiltros(consulta, 0, 0, null, null, null, user, arrayPeriodos[jCBperiodos.getSelectedIndex()]);

        while (linhas > 0) {//limpa conteudo da tabela para atulização
            for (int i = linhas; i > 0; i--) {
                modelo.removeRow(i - 1);
                linhas = modelo.getRowCount();
            }//fim do for
        }//fim do while

        int l = 0;
        if (!listaPonto.isEmpty()) {
            for (int i = 0; i < listaPonto.size(); i++) {
                try {
                    modelo.addRow(linha);
                    try {
                        modelo.setValueAt(listaPonto.get(i).getIPonto(), l, 0);
                    } catch (NullPointerException erro) {
                        //System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(formato(listaPonto.get(i).getDtRegistro(), "dd/MM/yyyy"), l, 1);
                    } catch (NullPointerException erro) {
                        //System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(formato(listaPonto.get(i).getDtEntrada(), "HH:mm"), l, 2);
                    } catch (NullPointerException erro) {
                        // System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(formato(listaPonto.get(i).getDtAlmoco(), "HH:mm"), l, 3);
                    } catch (NullPointerException erro) {
                        // System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(formato(listaPonto.get(i).getDtRetorno(), "HH:mm"), l, 4);
                    } catch (NullPointerException erro) {
                        // System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(formato(listaPonto.get(i).getDtSaida(), "HH:mm"), l, 5);
                    } catch (NullPointerException erro) {
                        // System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaPonto.get(i).getAlteracao(), l, 6);
                    } catch (NullPointerException erro) {
                        //System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaPonto.get(i).getSituacao(), l, 7);
                    } catch (NullPointerException erro) {
                        //System.out.println("Atenção! Campos vazios.");
                    }
                } catch (NullPointerException erro) {
                    // System.out.println("Atenção! Campos vazios.");
                }
                l++;
            }
        }
        jTponto.setModel(modelo);
        tamanhoColunas(jTponto);
        return modelo;

    }//</editor-fold>

    public DefaultTableModel tabelaIntercorrencia(int i_ponto) {
        //<editor-fold defaultstate="collapsed" desc="Manipulação da Tabela">
        /* Método controlador da tabela*/

        //int iFun = id_funcionario[fu.getSelectedIndex()];
        DefaultTableModel modelo = new DefaultTableModel(null, new String[]{
            "ID", "INÍCIO", "FIM", "ALTERAÇÃO", "SITUAÇÃO"}) {
            @Override
            public boolean isCellEditable(int a, int b) {
                return false;
            }
        };
        String[] linha = new String[]{null, null, null, null, null};
        int linhas = modelo.getRowCount();

        SuporteIntercorrenciaJpaController emf = new SuporteIntercorrenciaJpaController(new util.Persistencia().emf());
        List<SuporteIntercorrencia> listaIntercorrencias = emf.findSuporteIntercorrenciaDoPonto(i_ponto);

        while (linhas > 0) {//limpa conteudo da tabela para atulização
            for (int i = linhas; i > 0; i--) {
                modelo.removeRow(i - 1);
                linhas = modelo.getRowCount();
            }//fim do for
        }//fim do while

        int l = 0;
        if (!listaIntercorrencias.isEmpty()) {
            for (int i = 0; i < listaIntercorrencias.size(); i++) {
                try {
                    modelo.addRow(linha);
                    try {
                        modelo.setValueAt(listaIntercorrencias.get(i).getIIntercorrencia(), l, 0);
                    } catch (NullPointerException erro) {
                        //System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(formato(listaIntercorrencias.get(i).getDtInicio(), "HH:mm"), l, 1);
                    } catch (NullPointerException erro) {
                        //System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(formato(listaIntercorrencias.get(i).getDtFim(), "HH:mm"), l, 2);
                    } catch (NullPointerException erro) {
                        // System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaIntercorrencias.get(i).getAlteracao(), l, 3);
                    } catch (NullPointerException erro) {
                        //System.out.println("Atenção! Campos vazios.");
                    }
                    try {
                        modelo.setValueAt(listaIntercorrencias.get(i).getSituacao(), l, 4);
                    } catch (NullPointerException erro) {
                        //System.out.println("Atenção! Campos vazios.");
                    }
                } catch (NullPointerException erro) {
                    // System.out.println("Atenção! Campos vazios.");
                }
                l++;
            }
        }
        jTinter.setModel(modelo);
        tamanhoColunas(jTinter);
        return modelo;

    }//</editor-fold>

    public void tamanhoColunas(javax.swing.JTable tabela) {
        tabela.getColumnModel().getColumn(0).setPreferredWidth(24);
    }

    public void camposTipoConsulta(boolean estado){
        jYCano.setEnabled(estado);
        jBencerrar.setEnabled(estado);
        jCBperiodos.setEnabled(estado);
        jLexercicio.setEnabled(estado);
        jLcompetencia.setEnabled(estado);
        jDCinicio.setEnabled(!estado);
        jDCfim.setEnabled(!estado);
        jBconsultar.setEnabled(!estado);
        jLdtInicial.setEnabled(!estado);
        jLdtFinal.setEnabled(!estado);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTponto = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTinter = new javax.swing.JTable();
        jPconsulta = new javax.swing.JPanel();
        jRBcompetencia = new javax.swing.JRadioButton();
        jRBperiodo = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLcompetencia = new javax.swing.JLabel();
        jYCano = new com.toedter.calendar.JYearChooser();
        jBencerrar = new javax.swing.JButton();
        jLexercicio = new javax.swing.JLabel();
        jCBperiodos = new javax.swing.JComboBox();
        jPanel6 = new javax.swing.JPanel();
        jLdtInicial = new javax.swing.JLabel();
        jDCinicio = new datechooser.beans.DateChooserCombo();
        jLdtFinal = new javax.swing.JLabel();
        jDCfim = new datechooser.beans.DateChooserCombo();
        jBconsultar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jCBfuncionario = new javax.swing.JComboBox();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Gerenciar Registro de Ponto");
        setName("gerenciarregistrodeponto"); // NOI18N

        jTponto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTponto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTpontoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTponto);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        jLabel2.setText("Dias trabalhados:");

        jLabel9.setText("Dias regulares:");

        jLabel11.setText("Dias com atraso:");

        jTextField1.setEditable(false);

        jTextField2.setEditable(false);

        jTextField3.setEditable(false);

        jLabel19.setText("Dias com hora extra:");

        jTextField4.setEditable(false);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel3.setText("Dias com intercorrência:");

        jTextField5.setEditable(false);
        jTextField5.setText("15");

        jLabel10.setText("Registros fora do horário:");

        jTextField6.setEditable(false);

        jLabel12.setText("Horas das intercorrências:");

        jLabel13.setText("Horas justificadas:");

        jTextField7.setEditable(false);
        jTextField7.setText("22:59");

        jTextField8.setEditable(false);
        jTextField8.setText("08:00");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel14.setText("Horas do período:");

        jTextField9.setEditable(false);
        jTextField9.setText("22:59");

        jLabel15.setText("Horas trabalhadas:");

        jLabel16.setText("Horas à descontar:");

        jLabel17.setText("TOTAL DO PERÍODO:");

        jTextField10.setEditable(false);
        jTextField10.setText("22:59");

        jTextField11.setEditable(false);
        jTextField11.setText("22:59");

        jTextField12.setEditable(false);
        jTextField12.setText("22:59");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel11)
                    .addComponent(jLabel9)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jTextField1, jTextField10, jTextField11, jTextField12, jTextField2, jTextField3, jTextField4, jTextField5, jTextField6, jTextField7, jTextField8, jTextField9});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator1)
                            .addComponent(jSeparator2)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel2)
                                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel19)
                                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel9))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel11)
                                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel14)
                                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel16)
                                            .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel17)
                                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );

        jTinter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTinter);

        jPconsulta.setBorder(javax.swing.BorderFactory.createTitledBorder("CONSULTA"));

        buttonGroup1.add(jRBcompetencia);
        jRBcompetencia.setText("COMPETÊNCIA");
        jRBcompetencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRBcompetencia.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jRBcompetencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBcompetenciaActionPerformed(evt);
            }
        });

        buttonGroup1.add(jRBperiodo);
        jRBperiodo.setText("PERÍODO");
        jRBperiodo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jRBperiodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRBperiodoActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLcompetencia.setText("Competência:");
        jLcompetencia.setEnabled(false);

        jYCano.setEnabled(false);

        jBencerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ok_16.png"))); // NOI18N
        jBencerrar.setText("Encerrar");
        jBencerrar.setEnabled(false);
        jBencerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBencerrarActionPerformed(evt);
            }
        });

        jLexercicio.setText("Exercício:");
        jLexercicio.setEnabled(false);

        jCBperiodos.setMaximumRowCount(12);
        jCBperiodos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBperiodos.setEnabled(false);
        jCBperiodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBperiodosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLcompetencia)
                    .addComponent(jLexercicio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jYCano, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBencerrar, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                    .addComponent(jCBperiodos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jYCano, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBencerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLexercicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLcompetencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCBperiodos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLdtInicial.setText("Data inicial:");
        jLdtInicial.setEnabled(false);

        jDCinicio.setWeekStyle(datechooser.view.WeekDaysStyle.SHORT);
        jDCinicio.setEnabled(false);

        jLdtFinal.setText("Data final:");
        jLdtFinal.setEnabled(false);

        jDCfim.setWeekStyle(datechooser.view.WeekDaysStyle.SHORT);
        jDCfim.setEnabled(false);

        jBconsultar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jBconsultar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/buscar_16.png"))); // NOI18N
        jBconsultar.setToolTipText("Consultar seleção");
        jBconsultar.setEnabled(false);
        jBconsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBconsultarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLdtInicial)
                    .addComponent(jLdtFinal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDCinicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDCfim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBconsultar)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDCinicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLdtInicial, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLdtFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDCfim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jBconsultar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setText("Funcionário:");

        jCBfuncionario.setMaximumRowCount(20);
        jCBfuncionario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBfuncionario.setEnabled(false);
        jCBfuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBfuncionarioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPconsultaLayout = new javax.swing.GroupLayout(jPconsulta);
        jPconsulta.setLayout(jPconsultaLayout);
        jPconsultaLayout.setHorizontalGroup(
            jPconsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPconsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPconsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPconsultaLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCBfuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPconsultaLayout.createSequentialGroup()
                        .addGroup(jPconsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jRBcompetencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPconsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRBperiodo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        );
        jPconsultaLayout.setVerticalGroup(
            jPconsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPconsultaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPconsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jCBfuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPconsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRBcompetencia)
                    .addComponent(jRBperiodo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPconsultaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPconsulta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPconsulta, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCBperiodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBperiodosActionPerformed
        tabelaPonto();
    }//GEN-LAST:event_jCBperiodosActionPerformed

    private void jBconsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBconsultarActionPerformed

    }//GEN-LAST:event_jBconsultarActionPerformed

    private void jCBfuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBfuncionarioActionPerformed
        tabelaPonto();
    }//GEN-LAST:event_jCBfuncionarioActionPerformed

    private void jTpontoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTpontoMouseClicked
        int i_pontoSelecionado = Integer.parseInt(String.valueOf((jTponto.getValueAt(jTponto.getSelectedRow(), 0))));
        try {
            tabelaIntercorrencia(i_pontoSelecionado);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jTpontoMouseClicked

    private void jRBcompetenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBcompetenciaActionPerformed
        camposTipoConsulta(true);
    }//GEN-LAST:event_jRBcompetenciaActionPerformed

    private void jRBperiodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRBperiodoActionPerformed
        camposTipoConsulta(false);
    }//GEN-LAST:event_jRBperiodoActionPerformed

    private void jBencerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBencerrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBencerrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBconsultar;
    private javax.swing.JButton jBencerrar;
    private javax.swing.JComboBox jCBfuncionario;
    private javax.swing.JComboBox jCBperiodos;
    private datechooser.beans.DateChooserCombo jDCfim;
    private datechooser.beans.DateChooserCombo jDCinicio;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLcompetencia;
    private javax.swing.JLabel jLdtFinal;
    private javax.swing.JLabel jLdtInicial;
    private javax.swing.JLabel jLexercicio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPconsulta;
    private javax.swing.JRadioButton jRBcompetencia;
    private javax.swing.JRadioButton jRBperiodo;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTable jTinter;
    private javax.swing.JTable jTponto;
    private com.toedter.calendar.JYearChooser jYCano;
    // End of variables declaration//GEN-END:variables
}
