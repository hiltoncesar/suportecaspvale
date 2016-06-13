/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hilton
 */
@Entity
@Table(name = "suporte_pontoeletronico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuportePontoeletronico.findAll", query = "SELECT s FROM SuportePontoeletronico s"),
    @NamedQuery(name = "SuportePontoeletronico.findByIPonto", query = "SELECT s FROM SuportePontoeletronico s WHERE s.iPonto = :iPonto"),
    @NamedQuery(name = "SuportePontoeletronico.findByDtEntrada", query = "SELECT s FROM SuportePontoeletronico s WHERE s.dtEntrada = :dtEntrada"),
    @NamedQuery(name = "SuportePontoeletronico.findByDtAlmoco", query = "SELECT s FROM SuportePontoeletronico s WHERE s.dtAlmoco = :dtAlmoco"),
    @NamedQuery(name = "SuportePontoeletronico.findByDtRetorno", query = "SELECT s FROM SuportePontoeletronico s WHERE s.dtRetorno = :dtRetorno"),
    @NamedQuery(name = "SuportePontoeletronico.findByDtSaida", query = "SELECT s FROM SuportePontoeletronico s WHERE s.dtSaida = :dtSaida"),
    @NamedQuery(name = "SuportePontoeletronico.findByDtRegistro", query = "SELECT s FROM SuportePontoeletronico s WHERE s.dtRegistro = :dtRegistro"),
    @NamedQuery(name = "SuportePontoeletronico.findBySituacao", query = "SELECT s FROM SuportePontoeletronico s WHERE s.situacao = :situacao"),
    @NamedQuery(name = "SuportePontoeletronico.findByValor", query = "SELECT s FROM SuportePontoeletronico s WHERE s.valor = :valor"),
    @NamedQuery(name = "SuportePontoeletronico.findByAlteracao", query = "SELECT s FROM SuportePontoeletronico s WHERE s.alteracao = :alteracao")})
public class SuportePontoeletronico implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "i_ponto")
    private Integer iPonto;
    @Column(name = "dt_entrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtEntrada;
    @Column(name = "dt_almoco")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtAlmoco;
    @Column(name = "dt_retorno")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtRetorno;
    @Column(name = "dt_saida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtSaida;
    @Column(name = "dt_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtRegistro;
    @Basic(optional = false)
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "valor")
    private BigInteger valor;
    @Basic(optional = false)
    @Column(name = "alteracao")
    private String alteracao;
    @JoinColumn(name = "i_periodo", referencedColumnName = "i_periodo")
    @ManyToOne
    private SuportePeriodos iPeriodo;
    @JoinColumn(name = "i_usuario", referencedColumnName = "i_usuario")
    @ManyToOne(optional = false)
    private SuporteUsuarios iUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iPonto")
    private List<SuporteIntercorrencia> suporteIntercorrenciaList;

    public SuportePontoeletronico() {
    }

    public SuportePontoeletronico(Integer iPonto) {
        this.iPonto = iPonto;
    }

    public SuportePontoeletronico(Integer iPonto, String situacao, String alteracao) {
        this.iPonto = iPonto;
        this.situacao = situacao;
        this.alteracao = alteracao;
    }

    public Integer getIPonto() {
        return iPonto;
    }

    public void setIPonto(Integer iPonto) {
        this.iPonto = iPonto;
    }

    public Date getDtEntrada() {
        return dtEntrada;
    }

    public void setDtEntrada(Date dtEntrada) {
        this.dtEntrada = dtEntrada;
    }

    public Date getDtAlmoco() {
        return dtAlmoco;
    }

    public void setDtAlmoco(Date dtAlmoco) {
        this.dtAlmoco = dtAlmoco;
    }

    public Date getDtRetorno() {
        return dtRetorno;
    }

    public void setDtRetorno(Date dtRetorno) {
        this.dtRetorno = dtRetorno;
    }

    public Date getDtSaida() {
        return dtSaida;
    }

    public void setDtSaida(Date dtSaida) {
        this.dtSaida = dtSaida;
    }

    public Date getDtRegistro() {
        return dtRegistro;
    }

    public void setDtRegistro(Date dtRegistro) {
        this.dtRegistro = dtRegistro;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public BigInteger getValor() {
        return valor;
    }

    public void setValor(BigInteger valor) {
        this.valor = valor;
    }

    public String getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(String alteracao) {
        this.alteracao = alteracao;
    }

    public SuportePeriodos getIPeriodo() {
        return iPeriodo;
    }

    public void setIPeriodo(SuportePeriodos iPeriodo) {
        this.iPeriodo = iPeriodo;
    }

    public SuporteUsuarios getIUsuario() {
        return iUsuario;
    }

    public void setIUsuario(SuporteUsuarios iUsuario) {
        this.iUsuario = iUsuario;
    }

    @XmlTransient
    public List<SuporteIntercorrencia> getSuporteIntercorrenciaList() {
        return suporteIntercorrenciaList;
    }

    public void setSuporteIntercorrenciaList(List<SuporteIntercorrencia> suporteIntercorrenciaList) {
        this.suporteIntercorrenciaList = suporteIntercorrenciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iPonto != null ? iPonto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuportePontoeletronico)) {
            return false;
        }
        SuportePontoeletronico other = (SuportePontoeletronico) object;
        if ((this.iPonto == null && other.iPonto != null) || (this.iPonto != null && !this.iPonto.equals(other.iPonto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.SuportePontoeletronico[ iPonto=" + iPonto + " ]";
    }
    
}
