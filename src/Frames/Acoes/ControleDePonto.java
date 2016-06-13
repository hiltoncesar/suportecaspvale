/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Frames.Acoes;

import controle.SuporteConfigPontoJpaController;
import controle.SuportePeriodosJpaController;
import controle.SuportePontoeletronicoJpaController;
import controle.SuporteUsuariosJpaController;
import controle.exceptions.IllegalOrphanException;
import controle.exceptions.NonexistentEntityException;
import entidades.SuportePeriodos;
import entidades.SuportePontoeletronico;
import entidades.SuporteUsuarios;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hilton
 */
public final class ControleDePonto extends javax.swing.JInternalFrame {

    /**
     * Creates new form ControleDePonto
     */
    private final SuportePontoeletronicoJpaController emfAcesso = new SuportePontoeletronicoJpaController(new util.Persistencia().emf());
    private final SuporteUsuariosJpaController emfUsuario = new SuporteUsuariosJpaController(new util.Persistencia().emf());
    private final SuportePeriodosJpaController emfPeriodo = new SuportePeriodosJpaController(new util.Persistencia().emf());
    private final SuporteConfigPontoJpaController emfConfigPonto = new SuporteConfigPontoJpaController(new util.Persistencia().emf());
    public Thread t;
    private final int i_usuario;
    private final String usuario;
    private String usuario_nome;
    private int antecipaEntrada = 10, antecipaAlmoco = 10, antecipaRetorno = 10, antecipaSaida = 10, prorrogaEntrada = -5, prorrogaAlmoco = -5, prorrogaRetorno = -5, prorrogaSaida = -5;
    private int[] arrayUsuarios;
    private int[] arrayPeriodos;

    public ControleDePonto(int i_usuario, String usuario, String usuario_nome) {
        initComponents();
        setFrameIcon(new javax.swing.ImageIcon(this.getClass().getResource("/imagens/icone_2_24.png")));
        this.i_usuario = i_usuario;
        this.usuario = usuario;
        this.usuario_nome = usuario_nome;
        listarPeriodos();
        tabelaPonto();
        t = new Thread(new Relogio());
        t.start();
        iniciaSpinner();
        selecionaDataRegistro();
        selecionarRegistroTabela();
        listarFuncionarios();        
    }
    
    public void getConfigPonto() {
        antecipaEntrada = emfConfigPonto.findSuporteConfigPonto(1).getAntecipaEntrada();
        antecipaAlmoco = emfConfigPonto.findSuporteConfigPonto(1).getAntecipaAlmoco();
        antecipaRetorno = emfConfigPonto.findSuporteConfigPonto(1).getAntecipaRetorno();
        antecipaSaida = emfConfigPonto.findSuporteConfigPonto(1).getAntecipaSaida();
        prorrogaEntrada = emfConfigPonto.findSuporteConfigPonto(1).getProrrogaEntrada();
        prorrogaAlmoco = emfConfigPonto.findSuporteConfigPonto(1).getProrrogaAlmoco();
        prorrogaRetorno = emfConfigPonto.findSuporteConfigPonto(1).getProrrogaRetorno();
        prorrogaSaida = emfConfigPonto.findSuporteConfigPonto(1).getProrrogaSaida();
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

        String tipo = "usuario";
        if (jCBfuncionario.getSelectedIndex() == 0) {
            tipo = "geral";
        }
        
        if(jCBperiodos.getSelectedIndex() != 0){
            tipo = tipo + ".referencia";
        }

        SuportePontoeletronicoJpaController emf = new SuportePontoeletronicoJpaController(new util.Persistencia().emf());
        List<SuportePontoeletronico> listaPonto = emf.findSuportePontoeletronicoEntitiesFiltros(tipo, 0, 0, null, null, null, i_usuario, arrayPeriodos[jCBperiodos.getSelectedIndex()]);

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

    }

    public void tamanhoColunas(javax.swing.JTable tabela) {
        tabela.getColumnModel().getColumn(0).setPreferredWidth(24);
    }//</editor-fold>

    public void selecionaDataRegistro() {
        String dtRegistroCalendario = formato(jDCregistro.getCurrent().getTime(), "dd/MM/yyyy");
        String dtRegistroTabela;
        int linhas = jTponto.getRowCount();
        boolean encontrou = false;
        int i_ponto = -1;

        for (int i = 0; i < linhas; i++) {
            dtRegistroTabela = jTponto.getValueAt(i, 1).toString();
            if (dtRegistroCalendario.equalsIgnoreCase(dtRegistroTabela)) {
                jTponto.setRowSelectionInterval(i, i);
                i_ponto = Integer.parseInt(jTponto.getValueAt(i, 0).toString());
                encontrou = true;
                campos("editar");
                setHorasGravadas(i_ponto);
            }
        }
        if (!encontrou) {
            jTponto.clearSelection();
            iniciaSpinner();
            campos("novo");
        }
    }

    private void selecionarRegistroTabela() {
        int i_ponto = 0;
        try {
            i_ponto = Integer.parseInt(jTponto.getValueAt(jTponto.getSelectedRow(), 0).toString());
            SuportePontoeletronico ponto = emfAcesso.findSuportePontoeletronico(i_ponto);
            Date data = ponto.getDtRegistro();
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(data);
            jDCregistro.setCurrent(calendar);
            jDCregistro.setSelectedDate(calendar);
            campos("editar");
            setHorasGravadas(i_ponto);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("INDEX INVALID !!!!");
        }
    }

    private class Relogio implements Runnable {

        @Override
        public void run() {
            for (int i = 1; 1 <= 1; i++) {
                jLrelogio.setText(formato(new Date(), "HH:mm:ss"));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {

                }
            }
        }
    }

    public String formato(Date dt, String tipo) {
        SimpleDateFormat formato = new SimpleDateFormat(tipo);
        return formato.format(dt);
    }

    public void iniciaSpinner() {
        int ano = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "yyyy"));
        int mes = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "MM")) - 1;
        int dia = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "dd"));
        int hora = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "HH"));
        int minuto = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "mm"));
        Calendar c = new GregorianCalendar(ano, mes, dia, 0, 0);
        jSalmoco.setValue((Date) c.getTime());
        jSretorno.setValue((Date) c.getTime());
        jSsaida.setValue((Date) c.getTime());

        c.set(ano, mes, dia, hora, minuto);
        jSentrada.setValue((Date) c.getTime());
    }

    public String validaHoraDoRegistro(Calendar dtInformada, int i_ponto) {
        Date diferenca = emfAcesso.diferenca(formato(dtInformada.getTime(), "yyyy-MM-dd HH:mm:ss"), i_ponto);
        int ano = Integer.parseInt(formato(dtInformada.getTime(), "yyyy"));
        int mes = Integer.parseInt(formato(dtInformada.getTime(), "MM")) - 1;
        int dia = Integer.parseInt(formato(dtInformada.getTime(), "dd"));
        int hora = Integer.parseInt(formato(dtInformada.getTime(), "HH"));
        int minuto = Integer.parseInt(formato(dtInformada.getTime(), "mm"));
        Calendar c = new GregorianCalendar(ano, mes, dia, hora, minuto);
        c.setTime(diferenca);
        c.set(ano, mes, dia);
        //System.out.println("retorno: " + c.getTime());
        return "";
    }

    public String validaDataServidor(String tipo) {
        String retorno = "";
        Timestamp dtServidor = emfAcesso.dataServidor();
        int ano, mes, dia;
        Date horaInformada = null;
        switch (tipo) {
            case "entrada":
                horaInformada = (Date) jSentrada.getValue();
                break;
            case "almoco":
                horaInformada = (Date) jSalmoco.getValue();
                break;
            case "retorno":
                horaInformada = (Date) jSretorno.getValue();
                break;
            case "saida":
                horaInformada = (Date) jSsaida.getValue();
                break;
        }
        Calendar dtInformada = new GregorianCalendar();
        ano = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "yyyy"));
        mes = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "MM")) - 1;
        dia = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "dd"));
        dtInformada.setTime(horaInformada);
        dtInformada.set(ano, mes, dia);

        long diferenca = dtServidor.getTime() - dtInformada.getTimeInMillis();
        long segundos = diferenca / 1000l; //Transformando milisegundos em segundos          
        long min = segundos / 60; //Transformando segundos em minutos        
        long horas = (segundos / 60) / 60; // segundos ->111q horas         
        long dias = horas / 24; // horas -> dias  

//        System.out.println("dif: " + diferenca);
//        System.out.println("Segundos: " + segundos);
//        System.out.println("Mintos: " + min);
//        System.out.println("Horas: " + horas);
//        System.out.println("Diferenca em Dias: " + dias); //Vai imprimir 25.  
//        System.out.println("DATA: " + new Date(diferenca));

        switch (tipo) {
            case "entrada":
                retorno = restricao(antecipaEntrada, prorrogaEntrada, min);
                switch (retorno) {
                    case "invalido":
                        JOptionPane.showMessageDialog(null, "Hora informada é posterior ao limite permitido.",
                                "Registro não gravado", JOptionPane.ERROR_MESSAGE);
                        return retorno;
                    case "atraso":

                        return retorno;
                    case "normal":
                        return retorno;
                }
                break;
            case "almoco":
                retorno = restricao(antecipaAlmoco, prorrogaAlmoco, min);
                switch (retorno) {
                    case "invalido":
                        JOptionPane.showMessageDialog(null, "Hora informada é posterior ao limite permitido.",
                                "Registro não gravado", JOptionPane.ERROR_MESSAGE);
                        return retorno;
                    case "atraso":
                        int i_ponto = Integer.parseInt(jTponto.getValueAt(jTponto.getSelectedRow(), 0).toString());
                        ForaDoHorario fh = new ForaDoHorario(new Frames.Login(), rootPaneCheckingEnabled, i_ponto, this.usuario, dtInformada.getTime(), dtServidor);
                        fh.setVisible(true);
                        fh.setVisible(false);
                        return fh.gravar;
                    case "normal":
                        return retorno;
                }
                break;
            case "retorno":
                retorno = restricao(antecipaAlmoco, prorrogaAlmoco, min);
                switch (retorno) {
                    case "invalido":
                        JOptionPane.showMessageDialog(null, "Hora informada é posterior ao limite permitido.",
                                "Registro não gravado", JOptionPane.ERROR_MESSAGE);
                        return retorno;
                    case "atraso":
                        int i_ponto = Integer.parseInt(jTponto.getValueAt(jTponto.getSelectedRow(), 0).toString());
                        ForaDoHorario fh = new ForaDoHorario(new Frames.Login(), rootPaneCheckingEnabled, i_ponto, this.usuario, dtInformada.getTime(), dtServidor);
                        fh.setVisible(true);
                        fh.setVisible(false);
                        return fh.gravar;
                    case "normal":
                        return retorno;
                }
                break;
            case "saida":
                retorno = restricao(antecipaSaida, prorrogaSaida, min);
                switch (retorno) {
                    case "invalido":
                        JOptionPane.showMessageDialog(null, "Hora informada é posterior ao limite permitido.",
                                "Registro não gravado", JOptionPane.ERROR_MESSAGE);
                        return retorno;
                    case "atraso":
                        int i_ponto = Integer.parseInt(jTponto.getValueAt(jTponto.getSelectedRow(), 0).toString());
                        ForaDoHorario fh = new ForaDoHorario(new Frames.Login(), rootPaneCheckingEnabled, i_ponto, this.usuario, dtInformada.getTime(), dtServidor);
                        fh.setVisible(true);
                        fh.setVisible(false);
                        return fh.gravar;
                    case "normal":
                        return retorno;
                }
                break;
        }

        switch (retorno) {
            case "invalido":
                return "invalido";
            case "atraso":
                return "atraso";
            case "normal":
                return "normal";
        }

        System.out.println("***************** RETORNO: " + retorno);
        return "Data Servidor: " + dtServidor;
    }

    public String restricao(int minAntecipa, int minProrroga, long min) {
        if (min <= minProrroga) {//-5
            return "invalido";
        } else if (min >= minAntecipa) {//10
            return "atraso";
        } else {
            return "normal";
        }
    }

    public void gravarEntrada() {
        int ano,
                mes,
                dia;
        Date horaEntrada = (Date) jSentrada.getValue();
        Calendar dtEntrada = new GregorianCalendar();
        Calendar dtRegistro = new GregorianCalendar();
        ano = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "yyyy"));
        mes = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "MM")) - 1;
        dia = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "dd"));
        dtEntrada.setTime(horaEntrada);
        dtEntrada.set(ano, mes, dia);
        dtRegistro.setTime(horaEntrada);
        dtRegistro.set(ano, mes, dia, 0, 0, 0);

        // validaDataServidor("entrada");
        SuportePontoeletronico p = new SuportePontoeletronico();
        p.setIUsuario(emfUsuario.findSuporteUsuarios(this.i_usuario));
        p.setDtRegistro(dtRegistro.getTime());
        p.setDtEntrada(dtEntrada.getTime());
        p.setAlteracao(this.usuario);
        String simNao = validaDataServidor("entrada");
        p.setSituacao(simNao);

        if (simNao.equals("invalido") || simNao.equals("nao")) {
            JOptionPane.showMessageDialog(null, "Registro não gravado", "", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            emfAcesso.create(p);
        } catch (javax.persistence.RollbackException ex) {
            JOptionPane.showMessageDialog(null,
                    "Já existe um registro para o dia selecionado.\n"
                    + "Selecione o dia na tabela e utilize o tipo"
                    + "'INTERCORRÊNCIA' para adicionar movimentações.",
                    "AVISO!", JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(ControleDePonto.class.getName()).log(Level.SEVERE, null, ex);
        }
        //int i_ponto = Integer.parseInt(jTponto.getValueAt(jTponto.getSelectedRow(), 0).toString());
        if (simNao.equals("atraso")) {           

            int ano1, mes1, dia1;
            Date horaInformada = null;
            horaInformada = (Date) jSentrada.getValue();
            Calendar dtInformada = new GregorianCalendar();
            ano1 = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "yyyy"));
            mes1 = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "MM")) - 1;
            dia1 = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "dd"));
            dtInformada.setTime(horaInformada);
            dtInformada.set(ano1, mes1, dia1);
            Timestamp dtServidor = emfAcesso.dataServidor();
            int p_novo = emfAcesso.findSuportePontoeletronicoOrdenado().get(0).getIPonto();
            ForaDoHorario fh = new ForaDoHorario(new Frames.Login(), rootPaneCheckingEnabled, p_novo, this.usuario, dtInformada.getTime(), dtServidor);
            fh.setVisible(true);
            if (fh.gravar.equals("nao")) {
                try {
                    emfAcesso.destroy(p_novo);
                    return;
                } catch (IllegalOrphanException ex) {
                    Logger.getLogger(ControleDePonto.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(ControleDePonto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        tabelaPonto();
        selecionaDataRegistro();
    }

    public void gravarAlmoco() {
        SuportePontoeletronico ponto2 = emfAcesso.findSuportePontoeletronico(Integer.parseInt(jTponto.getValueAt(jTponto.getSelectedRow(), 0).toString()));
        if (ponto2.getDtAlmoco() == null) {
            int ano, mes, dia;
            Date horaEntrada = (Date) jSalmoco.getValue();
            Calendar dtEntrada = new GregorianCalendar();
            ano = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "yyyy"));
            mes = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "MM")) - 1;
            dia = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "dd"));
            dtEntrada.setTime(horaEntrada);
            dtEntrada.set(ano, mes, dia);

            String simNao = validaDataServidor("almoco");

            if (simNao.equals("invalido") || simNao.equals("nao")) {
                JOptionPane.showMessageDialog(null, "Registro não gravado", "", JOptionPane.WARNING_MESSAGE);
                return;
            }

            SuportePontoeletronico ponto = emfAcesso.findSuportePontoeletronico(Integer.parseInt(jTponto.getValueAt(jTponto.getSelectedRow(), 0).toString()));
            ponto.setDtAlmoco(dtEntrada.getTime());
            ponto.setAlteracao(this.usuario);
            ponto.setSituacao(ponto.getSituacao().trim() + "-" + simNao);

            try {
                emfAcesso.edit(ponto);
            } catch (Exception ex) {
                Logger.getLogger(ControleDePonto.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getCause().getMessage());
            }
            tabelaPonto();
            selecionaDataRegistro();
        } else {
            System.out.println("else");
        }
    }

    public void gravarRetorno() {

        SuportePontoeletronico ponto2 = emfAcesso.findSuportePontoeletronico(Integer.parseInt(jTponto.getValueAt(jTponto.getSelectedRow(), 0).toString()));
        if (ponto2.getDtRetorno() == null) {
            int ano, mes, dia;
            Date horaEntrada = (Date) jSretorno.getValue();
            Calendar dtEntrada = new GregorianCalendar();
            ano = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "yyyy"));
            mes = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "MM")) - 1;
            dia = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "dd"));
            dtEntrada.setTime(horaEntrada);
            dtEntrada.set(ano, mes, dia);

            String simNao = validaDataServidor("retorno");

            if (simNao.equals("invalido") || simNao.equals("nao")) {
                JOptionPane.showMessageDialog(null, "Registro não gravado", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SuportePontoeletronico ponto = emfAcesso.findSuportePontoeletronico(Integer.parseInt(jTponto.getValueAt(jTponto.getSelectedRow(), 0).toString()));
            ponto.setDtRetorno(dtEntrada.getTime());
            ponto.setAlteracao(this.usuario);
            ponto.setSituacao(ponto.getSituacao().trim() + "-" + simNao);
            try {
                emfAcesso.edit(ponto);
            } catch (Exception ex) {
                Logger.getLogger(ControleDePonto.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getCause().getMessage());
            }
            tabelaPonto();
            selecionaDataRegistro();
        } else {
            System.out.println("else");
        }
    }

    public void gravarSaida() {

        SuportePontoeletronico ponto2 = emfAcesso.findSuportePontoeletronico(Integer.parseInt(jTponto.getValueAt(jTponto.getSelectedRow(), 0).toString()));
        if (ponto2.getDtSaida() == null) {
            int ano, mes, dia;
            Date horaEntrada = (Date) jSsaida.getValue();
            Calendar dtEntrada = new GregorianCalendar();
            ano = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "yyyy"));
            mes = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "MM")) - 1;
            dia = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "dd"));
            dtEntrada.setTime(horaEntrada);
            dtEntrada.set(ano, mes, dia);

            String simNao = validaDataServidor("saida");

            if (simNao.equals("invalido") || simNao.equals("nao")) {
                JOptionPane.showMessageDialog(null, "Registro não gravado", "", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SuportePontoeletronico ponto = emfAcesso.findSuportePontoeletronico(Integer.parseInt(jTponto.getValueAt(jTponto.getSelectedRow(), 0).toString()));
            ponto.setDtSaida(dtEntrada.getTime());
            ponto.setAlteracao(this.usuario);
            ponto.setSituacao(ponto.getSituacao().trim() + "-" + simNao);
            try {
                emfAcesso.edit(ponto);
            } catch (Exception ex) {
                Logger.getLogger(ControleDePonto.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getCause().getMessage());
            }

        } else {
            System.out.println("asdf");
        }
        tabelaPonto();
        selecionaDataRegistro();
    }

    public void campos(String modo) {
        switch (modo) {
            case "novo":
                jSentrada.setEnabled(true);
                jBgravarEntrada.setEnabled(true);
                // jLtipo.setEnabled(false);
                //jCBtipo.setEnabled(false);
                jSalmoco.setEnabled(false);
                jSretorno.setEnabled(false);
                jSsaida.setEnabled(false);
                jBgravarAlmoco.setEnabled(false);
                jBgravarRetorno.setEnabled(false);
                jBgravarSaida.setEnabled(false);
                jBinter.setEnabled(false);
                break;
            case "editar":
                jSentrada.setEnabled(false);
                jBgravarEntrada.setEnabled(false);
                //jLtipo.setEnabled(true);
                // jCBtipo.setEnabled(true);
                jSalmoco.setEnabled(true);
                jSretorno.setEnabled(true);
                jSsaida.setEnabled(true);
                jBgravarAlmoco.setEnabled(true);
                jBgravarRetorno.setEnabled(true);
                jBgravarSaida.setEnabled(true);
                jBinter.setEnabled(true);
                break;
        }
    }

    public void setHorasGravadas(int i_ponto) {
        SuportePontoeletronico ponto = emfAcesso.findSuportePontoeletronico(i_ponto);
        int ano = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "yyyy"));
        int mes = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "MM")) - 1;
        int dia = Integer.parseInt(formato(jDCregistro.getSelectedDate().getTime(), "dd"));
        Calendar c = new GregorianCalendar(ano, mes, dia, 0, 0);

        if (ponto.getDtEntrada() != null) {
            jSentrada.setValue(ponto.getDtEntrada());
            jSentrada.setEnabled(false);
            jBgravarEntrada.setEnabled(false);
        } else {
            jSentrada.setValue(c.getTime());
            jSentrada.setEnabled(true);
        }
        if (ponto.getDtAlmoco() != null) {
            jSalmoco.setValue(ponto.getDtAlmoco());
            jSalmoco.setEnabled(false);
            jBgravarAlmoco.setEnabled(false);
        } else {
            jSalmoco.setValue(c.getTime());
            jSalmoco.setEnabled(true);
        }
        if (ponto.getDtRetorno() != null) {
            jSretorno.setValue(ponto.getDtRetorno());
            jSretorno.setEnabled(false);
            jBgravarRetorno.setEnabled(false);
        } else {
            jSretorno.setValue(c.getTime());
            jSretorno.setEnabled(true);
        }
        if (ponto.getDtSaida() != null) {
            jSsaida.setValue(ponto.getDtSaida());
            jSsaida.setEnabled(false);
            jBgravarSaida.setEnabled(false);
        } else {
            jSsaida.setValue(c.getTime());
            jSsaida.setEnabled(true);
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
        List<SuportePeriodos> listaPeriodos = emfPeriodo.findSuportePeriodosEntities();
        String[] lista = new String[listaPeriodos.size() + 1];
        lista[0] = "TODOS";
        arrayPeriodos = new int[listaPeriodos.size() + 1];
        arrayPeriodos[0] = 0;
        for (int i = 0; i < lista.length - 1; i++) {
            lista[i + 1] = listaPeriodos.get(i).getMesReferencia();
            arrayPeriodos[i + 1] = listaPeriodos.get(i).getIPeriodo();
        }
        jCBperiodos.setModel(new DefaultComboBoxModel(lista));
        jCBperiodos.setSelectedIndex(0);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTponto = new javax.swing.JTable();
        jPentrada = new javax.swing.JPanel();
        jSentrada = new javax.swing.JSpinner();
        jBgravarEntrada = new javax.swing.JButton();
        jBgravarAlmoco = new javax.swing.JButton();
        jSretorno = new javax.swing.JSpinner();
        jBgravarRetorno = new javax.swing.JButton();
        jLrelogio = new javax.swing.JLabel();
        jCBfuncionario = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jSsaida = new javax.swing.JSpinner();
        jBgravarSaida = new javax.swing.JButton();
        jSalmoco = new javax.swing.JSpinner();
        jDCregistro = new datechooser.beans.DateChooserCombo();
        jBinter = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jCBperiodos = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("CONTROLE DE PONTO");
        setMinimumSize(new java.awt.Dimension(558, 480));
        setName("controledeponto"); // NOI18N
        setPreferredSize(new java.awt.Dimension(558, 480));

        jTponto.setAutoCreateRowSorter(true);
        jTponto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTponto.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTponto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTpontoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTponto);

        jPentrada.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPentrada.setMaximumSize(new java.awt.Dimension(542, 212));

        jSentrada.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jSentrada.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        jSentrada.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSentrada.setEditor(new javax.swing.JSpinner.DateEditor(jSentrada, "HH:mm"));
        jSentrada.setEnabled(false);
        jSentrada.setMaximumSize(new java.awt.Dimension(116, 50));
        jSentrada.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSentradaStateChanged(evt);
            }
        });
        jSentrada.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jSentradaInputMethodTextChanged(evt);
            }
        });

        jBgravarEntrada.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBgravarEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ok_16.png"))); // NOI18N
        jBgravarEntrada.setText("ENTRADA");
        jBgravarEntrada.setEnabled(false);
        jBgravarEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBgravarEntradaActionPerformed(evt);
            }
        });

        jBgravarAlmoco.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBgravarAlmoco.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ok_16.png"))); // NOI18N
        jBgravarAlmoco.setText("ALMOÇO");
        jBgravarAlmoco.setEnabled(false);
        jBgravarAlmoco.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBgravarAlmocoActionPerformed(evt);
            }
        });

        jSretorno.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jSretorno.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        jSretorno.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSretorno.setEditor(new javax.swing.JSpinner.DateEditor(jSretorno, "HH:mm"));
        jSretorno.setEnabled(false);
        jSretorno.setMaximumSize(new java.awt.Dimension(144, 64));
        jSretorno.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSretornoStateChanged(evt);
            }
        });
        jSretorno.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jSretornoInputMethodTextChanged(evt);
            }
        });

        jBgravarRetorno.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBgravarRetorno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ok_16.png"))); // NOI18N
        jBgravarRetorno.setText("RETORNO");
        jBgravarRetorno.setEnabled(false);
        jBgravarRetorno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBgravarRetornoActionPerformed(evt);
            }
        });

        jLrelogio.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLrelogio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLrelogio.setText("23:59:59");

        jCBfuncionario.setMaximumRowCount(20);
        jCBfuncionario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBfuncionario.setEnabled(false);
        jCBfuncionario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBfuncionarioActionPerformed(evt);
            }
        });

        jLabel2.setText("Funcionário:");

        jSsaida.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jSsaida.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        jSsaida.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSsaida.setEditor(new javax.swing.JSpinner.DateEditor(jSsaida, "HH:mm"));
        jSsaida.setEnabled(false);
        jSsaida.setMaximumSize(new java.awt.Dimension(144, 64));
        jSsaida.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSsaidaStateChanged(evt);
            }
        });
        jSsaida.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jSsaidaInputMethodTextChanged(evt);
            }
        });

        jBgravarSaida.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jBgravarSaida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/ok_16.png"))); // NOI18N
        jBgravarSaida.setText("SAÍDA");
        jBgravarSaida.setEnabled(false);
        jBgravarSaida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBgravarSaidaActionPerformed(evt);
            }
        });

        jSalmoco.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jSalmoco.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.HOUR_OF_DAY));
        jSalmoco.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSalmoco.setEditor(new javax.swing.JSpinner.DateEditor(jSalmoco, "HH:mm"));
        jSalmoco.setEnabled(false);
        jSalmoco.setMaximumSize(new java.awt.Dimension(144, 64));
        jSalmoco.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSalmocoStateChanged(evt);
            }
        });
        jSalmoco.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                jSalmocoInputMethodTextChanged(evt);
            }
        });

        jDCregistro.setFormat(0);
        jDCregistro.setFieldFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 18));
        jDCregistro.setBehavior(datechooser.model.multiple.MultyModelBehavior.SELECT_SINGLE);
        jDCregistro.addCommitListener(new datechooser.events.CommitListener() {
            public void onCommit(datechooser.events.CommitEvent evt) {
                jDCregistroOnCommit(evt);
            }
        });

        jBinter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagens/novo_16.png"))); // NOI18N
        jBinter.setText("Intercorrência");
        jBinter.setEnabled(false);
        jBinter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBinterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPentradaLayout = new javax.swing.GroupLayout(jPentrada);
        jPentrada.setLayout(jPentradaLayout);
        jPentradaLayout.setHorizontalGroup(
            jPentradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPentradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPentradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPentradaLayout.createSequentialGroup()
                        .addComponent(jDCregistro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jLrelogio))
                    .addGroup(jPentradaLayout.createSequentialGroup()
                        .addGroup(jPentradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSentrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBgravarEntrada))
                        .addGap(18, 18, 18)
                        .addGroup(jPentradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSalmoco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBgravarAlmoco, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPentradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jSretorno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBgravarRetorno, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPentradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSsaida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jBgravarSaida, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPentradaLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCBfuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBinter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPentradaLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jBgravarAlmoco, jBgravarEntrada, jBgravarRetorno, jBgravarSaida, jSalmoco, jSentrada, jSretorno, jSsaida});

        jPentradaLayout.setVerticalGroup(
            jPentradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPentradaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPentradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLrelogio, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                    .addComponent(jDCregistro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPentradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBinter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPentradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jCBfuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPentradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSentrada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSalmoco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSretorno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSsaida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPentradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPentradaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jBgravarRetorno)
                        .addComponent(jBgravarSaida))
                    .addComponent(jBgravarAlmoco, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBgravarEntrada, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPentradaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBgravarAlmoco, jBgravarEntrada, jBgravarRetorno, jBgravarSaida});

        jPentradaLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jBinter, jCBfuncionario});

        jLabel3.setText("Período:");

        jCBperiodos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jCBperiodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBperiodosActionPerformed(evt);
            }
        });

        jLabel4.setText("TOTAL:");

        jLabel5.setText("99:99h");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPentrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(154, 154, 154)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCBperiodos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPentrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jCBperiodos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addContainerGap())
        );

        setBounds(0, 0, 558, 480);
    }// </editor-fold>//GEN-END:initComponents

    private void jTpontoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTpontoMouseClicked
        selecionarRegistroTabela();
    }//GEN-LAST:event_jTpontoMouseClicked

    private void jBinterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBinterActionPerformed
        //validaDataServidor("*");
        int i_ponto = Integer.parseInt(jTponto.getValueAt(jTponto.getSelectedRow(), 0).toString());
        Intercorrencia i = new Intercorrencia(new Frames.Login(), rootPaneCheckingEnabled, jDCregistro.getCurrent().getTime(), i_ponto, this.usuario);
        i.setVisible(true);
    }//GEN-LAST:event_jBinterActionPerformed

    private void jDCregistroOnCommit(datechooser.events.CommitEvent evt) {//GEN-FIRST:event_jDCregistroOnCommit
        selecionaDataRegistro();
    }//GEN-LAST:event_jDCregistroOnCommit

    private void jSalmocoInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jSalmocoInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jSalmocoInputMethodTextChanged

    private void jSalmocoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSalmocoStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jSalmocoStateChanged

    private void jBgravarSaidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBgravarSaidaActionPerformed
        gravarSaida();
    }//GEN-LAST:event_jBgravarSaidaActionPerformed

    private void jSsaidaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jSsaidaInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jSsaidaInputMethodTextChanged

    private void jSsaidaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSsaidaStateChanged

    }//GEN-LAST:event_jSsaidaStateChanged

    private void jBgravarRetornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBgravarRetornoActionPerformed
        gravarRetorno();
    }//GEN-LAST:event_jBgravarRetornoActionPerformed

    private void jSretornoInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jSretornoInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jSretornoInputMethodTextChanged

    private void jSretornoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSretornoStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jSretornoStateChanged

    private void jBgravarAlmocoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBgravarAlmocoActionPerformed
        gravarAlmoco();
    }//GEN-LAST:event_jBgravarAlmocoActionPerformed

    private void jBgravarEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBgravarEntradaActionPerformed
        gravarEntrada();
    }//GEN-LAST:event_jBgravarEntradaActionPerformed

    private void jSentradaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jSentradaInputMethodTextChanged

    }//GEN-LAST:event_jSentradaInputMethodTextChanged

    private void jSentradaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSentradaStateChanged

    }//GEN-LAST:event_jSentradaStateChanged

    private void jCBfuncionarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBfuncionarioActionPerformed
        tabelaPonto();
    }//GEN-LAST:event_jCBfuncionarioActionPerformed

    private void jCBperiodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBperiodosActionPerformed
        tabelaPonto();
    }//GEN-LAST:event_jCBperiodosActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBgravarAlmoco;
    private javax.swing.JButton jBgravarEntrada;
    private javax.swing.JButton jBgravarRetorno;
    private javax.swing.JButton jBgravarSaida;
    private javax.swing.JButton jBinter;
    private javax.swing.JComboBox jCBfuncionario;
    private javax.swing.JComboBox jCBperiodos;
    private datechooser.beans.DateChooserCombo jDCregistro;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLrelogio;
    private javax.swing.JPanel jPentrada;
    private javax.swing.JSpinner jSalmoco;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSentrada;
    private javax.swing.JSpinner jSretorno;
    private javax.swing.JSpinner jSsaida;
    private javax.swing.JTable jTponto;
    // End of variables declaration//GEN-END:variables
}
