/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hilton
 */
@Entity
@Table(name = "suporte_intercorrencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuporteIntercorrencia.findAll", query = "SELECT s FROM SuporteIntercorrencia s"),
    @NamedQuery(name = "SuporteIntercorrencia.findByIIntercorrencia", query = "SELECT s FROM SuporteIntercorrencia s WHERE s.iIntercorrencia = :iIntercorrencia"),
    @NamedQuery(name = "SuporteIntercorrencia.findByDtInicio", query = "SELECT s FROM SuporteIntercorrencia s WHERE s.dtInicio = :dtInicio"),
    @NamedQuery(name = "SuporteIntercorrencia.findByDtFim", query = "SELECT s FROM SuporteIntercorrencia s WHERE s.dtFim = :dtFim"),
    @NamedQuery(name = "SuporteIntercorrencia.findByAlteracao", query = "SELECT s FROM SuporteIntercorrencia s WHERE s.alteracao = :alteracao"),
    @NamedQuery(name = "SuporteIntercorrencia.findBySituacao", query = "SELECT s FROM SuporteIntercorrencia s WHERE s.situacao = :situacao"),
    @NamedQuery(name = "SuporteIntercorrencia.findByMotivo", query = "SELECT s FROM SuporteIntercorrencia s WHERE s.motivo = :motivo")})
public class SuporteIntercorrencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "i_intercorrencia")
    private Integer iIntercorrencia;
    @Column(name = "dt_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtInicio;
    @Column(name = "dt_fim")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtFim;
    @Column(name = "alteracao")
    private String alteracao;
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "motivo")
    private String motivo;
    @JoinColumn(name = "i_ponto", referencedColumnName = "i_ponto")
    @ManyToOne(optional = false)
    private SuportePontoeletronico iPonto;

    public SuporteIntercorrencia() {
    }

    public SuporteIntercorrencia(Integer iIntercorrencia) {
        this.iIntercorrencia = iIntercorrencia;
    }

    public Integer getIIntercorrencia() {
        return iIntercorrencia;
    }

    public void setIIntercorrencia(Integer iIntercorrencia) {
        this.iIntercorrencia = iIntercorrencia;
    }

    public Date getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Date dtInicio) {
        this.dtInicio = dtInicio;
    }

    public Date getDtFim() {
        return dtFim;
    }

    public void setDtFim(Date dtFim) {
        this.dtFim = dtFim;
    }

    public String getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(String alteracao) {
        this.alteracao = alteracao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public SuportePontoeletronico getIPonto() {
        return iPonto;
    }

    public void setIPonto(SuportePontoeletronico iPonto) {
        this.iPonto = iPonto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iIntercorrencia != null ? iIntercorrencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuporteIntercorrencia)) {
            return false;
        }
        SuporteIntercorrencia other = (SuporteIntercorrencia) object;
        if ((this.iIntercorrencia == null && other.iIntercorrencia != null) || (this.iIntercorrencia != null && !this.iIntercorrencia.equals(other.iIntercorrencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.SuporteIntercorrencia[ iIntercorrencia=" + iIntercorrencia + " ]";
    }
    
}
