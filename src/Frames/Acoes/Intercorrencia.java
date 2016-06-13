/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames.Acoes;

import controle.SuporteIntercorrenciaJpaController;
import controle.SuportePontoeletronicoJpaController;
import controle.exceptions.NonexistentEntityException;
import entidades.SuporteIntercorrencia;
import entidades.SuportePontoeletronico;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hilton
 */
public final class Intercorrencia extends javax.swing.JDialog {

    private final SuporteIntercorrenciaJpaController emfInter = new SuporteIntercorrenciaJpaController(new util.Persistencia().emf());
    private final SuportePontoeletronicoJpaController emfAcesso = new SuportePontoeletronicoJpaController(new util.Persistencia().emf());
    private Date dtRegistro;
    private Date dtGravada;
    private Date dtAtual;
    private final int i_ponto;
    private final String nome_usuario;
    public Thread t;

    public Intercorrencia(java.awt.Frame parent, boolean modal, Date dtRegistro, int i_ponto, String usuario) {
        super(parent, modal);
        initComponents();
        this.dtRegistro = dtRegistro;
        this.i_ponto = i_ponto;
        this.nome_usuario = usuario;
        tabelaIntercorrencia();
    }

    /* public Intercorrencia(java.awt.Frame parent, boolean modal, int i_ponto, String usuario, Date dtGravada, Date dtAtual) {
     super(parent, modal);
     initComponents();
     this.dtAtual = dtAtual;
     this.dtGravada = dtGravada;
     this.i_ponto = i_ponto;
     this.nome_usuario = usuario;
     }*/
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

    public DefaultTableModel tabelaIntercorrencia() {
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
        campos("inicio");
        jTinter.setModel(modelo);
        tamanhoColunas(jTinter);
        return modelo;

    }

    public void tamanhoColunas(javax.swing.JTable tabela) {
        tabela.getColumnModel().getColumn(0).setPreferredWidth(24);
    }//</editor-fold>

    public void gravarInicio() {
        if (jCBtipo.getSelectedIndex() > 0) {
            int ano, mes, dia;
            Date horaInicio = (Date) jSinicio.getValue();
            Calendar dtInicio = new GregorianCalendar();
            ano = Integer.parseInt(formato(dtRegistro, "yyyy"));
            mes = Integer.parseInt(formato(dtRegistro, "MM")) - 1;
            dia = Integer.parseInt(formato(dtRegistro, "dd"));
            dtInicio.setTime(horaInicio);
            dtInicio.set(ano, mes, dia);

            SuporteIntercorrencia interNovo = new SuporteIntercorrencia();
            SuportePontoeletronico pontoSelecionado = emfAcesso.findSuportePontoeletronico(this.i_ponto);
            interNovo.setDtInicio(dtInicio.getTime());
            interNovo.setIPonto(pontoSelecionado);
            interNovo.setMotivo(jTAmotivo.getText());
            interNovo.setSituacao(jCBtipo.getSelectedItem().toString());
            interNovo.setAlteracao(this.nome_usuario);
            emfInter.create(interNovo);
            tabelaIntercorrencia();
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um tipo.", "Registro não gravado", JOptionPane.ERROR_MESSAGE);
            jCBtipo.requestFocus();
        }
    }

    public void gravarFim() {
        switch (jCBtipo.getSelectedIndex()) {
            case 0:
                JOptionPane.showMessageDialog(null, "Selecione um tipo.", "Registro não gravado", JOptionPane.ERROR_MESSAGE);
                jCBtipo.requestFocus();
                break;
        }
        if (jCBtipo.getSelectedIndex() > 0) {
            SuporteIntercorrencia inter = emfInter.findSuporteIntercorrencia(Integer.parseInt(jTinter.getValueAt(jTinter.getSelectedRow(), 0).toString()));
            int ano, mes, dia;
            Date horaFim = (Date) jSfim.getValue();
            Calendar dtFim = new GregorianCalendar();
            ano = Integer.parseInt(formato(dtRegistro, "yyyy"));
            mes = Integer.parseInt(formato(dtRegistro, "MM")) - 1;
            dia = Integer.parseInt(formato(dtRegistro, "dd"));
            dtFim.setTime(horaFim);
            dtFim.set(ano, mes, dia);

            inter.setMotivo(jTAmotivo.getText());
            inter.setSituacao(jCBtipo.getSelectedItem().toString());
            inter.setAlteracao(this.nome_usuario);

            if (inter.getDtFim() == null) {
                inter.setDtFim(dtFim.getTime());
            } else {
                System.out.println("SAIDA intercorrencia já gravada");
            }

            try {
                emfInter.edit(inter);
            } catch (Exception ex) {
                Logger.getLogger(Intercorrencia.class.getName()).log(Level.SEVERE, null, ex);
            }
            tabelaIntercorrencia();
        }
    }

    private void selecionarRegistroTabela() {
        int i_inter = 0;
        try {
            i_inter = Integer.parseInt(jTinter.getValueAt(jTinter.getSelectedRow(), 0).toString());
            SuporteIntercorrencia inter = emfInter.findSuporteIntercorrencia(i_inter);
            Date data = inter.getDtInicio();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(data);
            jTAmotivo.setText(inter.getMotivo());
            jTFsituacao.setText(inter.getSituacao());
            for (int i = 0; i < jCBtipo.getItemCount(); i++) {
                if (jCBtipo.getItemAt(i).toString().trim().equals(inter.getSituacao().trim())) {
                    jCBtipo.setSelectedItem(inter.getSituacao());
                    i = jCBtipo.getItemCount() + 1;
                } else {
                    jCBtipo.setSelectedIndex(0);
                }
            }
            if (inter.getSituacao().contains("ATRASO")) {
                jBexcluir.setEnabled(false);
            } else {
                jBexcluir.setEnabled(true);
            }

            campos("editar");
            setHorasGravadas(i_inter);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("INDEX INVALID !!!!");
        }
    }

    public void setHorasGravadas(int i_inter) {
        SuporteIntercorrencia inter = emfInter.findSuporteIntercorrencia(i_inter);
        if (inter.getDtInicio() != null) {
            jSinicio.setValue(inter.getDtInicio());
            jSinicio.setEnabled(false);
            jBgravarEntrada.setEnabled(false);
        } else {
            jSinicio.setValue(horaZerada(dtRegistro));
            jSinicio.setEnabled(true);
        }
        if (inter.getDtFim() != null) {
            jSfim.setValue(inter.getDtFim());
            jSfim.setEnabled(false);
            jBgravarSaida.setEnabled(false);
            jTAmotivo.setEditable(false);
        } else {
            jSfim.setValue(horaZerada(dtRegistro));
            jSfim.setEnabled(true);
            jTAmotivo.setEditable(true);
        }
    }

    public void campos(String modo) {
        switch (modo) {
            case "novo":
                jBgravarEntrada.setEnabled(true);
                jSinicio.setEnabled(true);
                jSfim.setEnabled(false);
                jBgravarSaida.setEnabled(false);
                jTAmotivo.setEditable(true);
                jTAmotivo.setText("");
                jTFsituacao.setText("");
                break;
            case "editar":
                jSinicio.setEnabled(false);
                jBgravarEntrada.setEnabled(false);
                jSfim.setEnabled(true);
                jBgravarSaida.setEnabled(true);
                jTAmotivo.setEditable(true);
                break;
            case "inicio":
                jBgravarEntrada.setEnabled(false);
                jSinicio.setValue(horaZerada(dtRegistro));
                jSfim.setValue(horaZerada(dtRegistro));
                jSfim.setEnabled(false);
                jSinicio.setEnabled(false);
                jBgravarSaida.setEnabled(false);
                jTAmotivo.setEditable(false);
                jTAmotivo.setText("");
                jTFsituacao.setText("");
                break;
        }
    }

    public void novoCadastro(String modo) {
        jBexcluir.setEnabled(false);
        switch (modo) {
            case "intercorrencia":
                campos("novo");
                jCBtipo.setSelectedIndex(0);
                jSinicio.setValue(horaZerada(dtRegistro));
                jSfim.setValue(horaZerada(dtRegistro));
                break;
            /* case "horario":

             campos("horario");
             int ano,
             mes,
             dia;
             Date horaInicio = (Date) jSinicio.getValue();
             Date horaFim = (Date) jSfim.getValue();
             Calendar dtGravadaC = new GregorianCalendar();
             Calendar dtAtualC = new GregorianCalendar();
             ano = Integer.parseInt(formato(dtGravada, "yyyy"));
             mes = Integer.parseInt(formato(dtGravada, "MM")) - 1;
             dia = Integer.parseInt(formato(dtGravada, "dd"));
             dtGravadaC.setTime(horaInicio);
             dtGravadaC.set(ano, mes, dia);
             dtAtualC.setTime(horaFim);
             dtAtualC.set(ano, mes, dia);

             SuporteIntercorrencia interNovo = new SuporteIntercorrencia();
             SuportePontoeletronico pontoSelecionado = emfAcesso.findSuportePontoeletronico(this.i_ponto);
             interNovo.setDtInicio(dtGravadaC.getTime());
             interNovo.setDtFim(dtAtualC.getTime());
             interNovo.setIPonto(pontoSelecionado);
             interNovo.setMotivo(jTAmotivo.getText());
             interNovo.setSituacao(jCBtipo.getSelectedItem().toString());
             interNovo.setAlteracao(this.nome_usuario);
             emfInter.create(interNovo);
             break;*/
        }
    }

    public Date horaZerada(Date dt) {
        int ano = Integer.parseInt(formato(dt, "yyyy"));
        int mes = Integer.parseInt(formato(dt, "MM")) - 1;
        int dia = Integer.parseInt(formato(dt, "dd"));
        return new GregorianCalendar(ano, mes, dia, 0, 0).getTime();
    }

    public void excluirIntercorrencia() {
        jBexcluir.setEnabled(false);
        int i_inter = 0;
        i_inter = Integer.parseInt(jTinter.getValueAt(jTinter.getSelectedRow(), 0).toString());
        String[] str = {"Excluir", "Cancelar"};
        int result = JOptionPane.showOptionDialog(null,
                "Deseja excluir o cadastro selecionado?", "ATENÇÃO!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, str, str[1]);
        if (result == 0) {
            try {
                emfInter.destroy(i_inter);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(Intercorrencia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        tabelaIntercorrencia();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTinter = new javax.swing.JTable();
        jCBtipo = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTAmotivo = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jBgravarSaida = new javax.swing.JButton();
        jSfim = new javax.swing.JSpinner();
        jSinicio = new javax.swing.JSpinner();
        jBgravarEntrada = new javax.swing.JButton();
        jBnovo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTFsituacao = new javax.swing.JTextField();
        jBexcluir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ADICIONAR INTERCORRÊNCIA");
        setIconImage(null);
        setMinimumSize(new java.awt.Dimension(503, 424));

        jTinter.setAutoCreateRowSorter(true);
        jTinter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTinter.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTinter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTinterMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTinter);

        jCBtipo.setMaximumRowCount(20);
        jCBtipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione...", "HORA EXTRA 60% (até 18:59)\t", "HORA EXTRA 100% (19:00 até 21:59)", "HORA EXTRA 120% (após às 22:00)\t", "HORA EXTRA FERIADO 60% (até 18:59)\t", "HORA EXTRA FERIADO 100% (19:00 até 21:59)", "HORA EXTRA FERIADO 120% (após às 22:00)", "NÃO CONSIDERAR" }));

        jTAmotivo.setColumns(20);
        jTAmotivo.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jTAmotivo.setLineWrap(true);
        jTAmotivo.setRows(1);
        jTAmotivo.setWrapStyleWord(true);
        jTAmotivo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Motivo"));
        jScrollPane2.setViewportView(jTAmotivo);

        jLabel1.setText("Tipo:");

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jBgravarSaida.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBgravarSaida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ok_16.png"))); // NOI18N
        jBgravarSaida.setText("FIM");
        jBgravarSaida.setEnabled(false);
        jBgravarSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBgravarSaidaActionPerformed(evt);
            }
        });

        jSfim.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jSfim.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        jSfim.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSfim.setEditor(new javax.swing.JSpinner.DateEditor(jSfim, "HH:mm"));
        jSfim.setEnabled(false);
        jSfim.setMaximumSize(new java.awt.Dimension(144, 64));
        jSfim.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSfimStateChanged(evt);
            }
        });
        jSfim.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jSfimInputMethodTextChanged(evt);
            }
        });

        jSinicio.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jSinicio.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        jSinicio.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSinicio.setEditor(new javax.swing.JSpinner.DateEditor(jSinicio, "HH:mm"));
        jSinicio.setEnabled(false);
        jSinicio.setMaximumSize(new java.awt.Dimension(144, 64));
        jSinicio.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSinicioStateChanged(evt);
            }
        });
        jSinicio.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jSinicioInputMethodTextChanged(evt);
            }
        });

        jBgravarEntrada.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBgravarEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ok_16.png"))); // NOI18N
        jBgravarEntrada.setText("INÍCIO");
        jBgravarEntrada.setEnabled(false);
        jBgravarEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBgravarEntradaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBgravarEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSinicio, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSfim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBgravarSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        jPanel5Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBgravarEntrada, jBgravarSaida, jSfim, jSinicio});

        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSinicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSfim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBgravarEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBgravarSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jBnovo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jBnovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/add_32.png"))); // NOI18N
        jBnovo.setText("NOVO CADASTRO");
        jBnovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBnovoActionPerformed(evt);
            }
        });

        jLabel2.setText("Situação:");

        jTFsituacao.setEditable(false);

        jBexcluir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBexcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/deletar_16.png"))); // NOI18N
        jBexcluir.setText("EXCLUIR");
        jBexcluir.setEnabled(false);
        jBexcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBexcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCBtipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jBnovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBexcluir)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFsituacao)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBtipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBnovo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBexcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTFsituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(519, 462));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jBgravarEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBgravarEntradaActionPerformed
        gravarInicio();
    }//GEN-LAST:event_jBgravarEntradaActionPerformed

    private void jSinicioStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSinicioStateChanged

    }//GEN-LAST:event_jSinicioStateChanged

    private void jSinicioInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jSinicioInputMethodTextChanged

    }//GEN-LAST:event_jSinicioInputMethodTextChanged

    private void jSfimStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSfimStateChanged

    }//GEN-LAST:event_jSfimStateChanged

    private void jSfimInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jSfimInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jSfimInputMethodTextChanged

    private void jBgravarSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBgravarSaidaActionPerformed
        gravarFim();
    }//GEN-LAST:event_jBgravarSaidaActionPerformed

    private void jTinterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTinterMouseClicked
        selecionarRegistroTabela();
    }//GEN-LAST:event_jTinterMouseClicked

    private void jBnovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBnovoActionPerformed
        novoCadastro("intercorrencia");
    }//GEN-LAST:event_jBnovoActionPerformed

    private void jBexcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBexcluirActionPerformed
        excluirIntercorrencia();
    }//GEN-LAST:event_jBexcluirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBexcluir;
    private javax.swing.JButton jBgravarEntrada;
    private javax.swing.JButton jBgravarSaida;
    private javax.swing.JButton jBnovo;
    private javax.swing.JComboBox jCBtipo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSfim;
    private javax.swing.JSpinner jSinicio;
    private javax.swing.JTextArea jTAmotivo;
    private javax.swing.JTextField jTFsituacao;
    private javax.swing.JTable jTinter;
    // End of variables declaration//GEN-END:variables
}
