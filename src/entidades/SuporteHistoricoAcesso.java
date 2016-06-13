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
@Table(name = "suporte_historico_acesso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuporteHistoricoAcesso.findAll", query = "SELECT s FROM SuporteHistoricoAcesso s"),
    @NamedQuery(name = "SuporteHistoricoAcesso.findByIHistorico", query = "SELECT s FROM SuporteHistoricoAcesso s WHERE s.iHistorico = :iHistorico"),
    @NamedQuery(name = "SuporteHistoricoAcesso.findByComentario", query = "SELECT s FROM SuporteHistoricoAcesso s WHERE s.comentario = :comentario"),
    @NamedQuery(name = "SuporteHistoricoAcesso.findByDtAcesso", query = "SELECT s FROM SuporteHistoricoAcesso s WHERE s.dtAcesso = :dtAcesso")})
public class SuporteHistoricoAcesso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "i_historico")
    private Integer iHistorico;
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "dt_acesso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtAcesso;
    @JoinColumn(name = "i_acesso", referencedColumnName = "i_acesso")
    @ManyToOne
    private SuporteAcessoRemoto iAcesso;
    @JoinColumn(name = "i_usuario", referencedColumnName = "i_usuario")
    @ManyToOne
    private SuporteUsuarios iUsuario;

    public SuporteHistoricoAcesso() {
    }

    public SuporteHistoricoAcesso(Integer iHistorico) {
        this.iHistorico = iHistorico;
    }

    public Integer getIHistorico() {
        return iHistorico;
    }

    public void setIHistorico(Integer iHistorico) {
        this.iHistorico = iHistorico;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getDtAcesso() {
        return dtAcesso;
    }

    public void setDtAcesso(Date dtAcesso) {
        this.dtAcesso = dtAcesso;
    }

    public SuporteAcessoRemoto getIAcesso() {
        return iAcesso;
    }

    public void setIAcesso(SuporteAcessoRemoto iAcesso) {
        this.iAcesso = iAcesso;
    }

    public SuporteUsuarios getIUsuario() {
        return iUsuario;
    }

    public void setIUsuario(SuporteUsuarios iUsuario) {
        this.iUsuario = iUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iHistorico != null ? iHistorico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuporteHistoricoAcesso)) {
            return false;
        }
        SuporteHistoricoAcesso other = (SuporteHistoricoAcesso) object;
        if ((this.iHistorico == null && other.iHistorico != null) || (this.iHistorico != null && !this.iHistorico.equals(other.iHistorico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.SuporteHistoricoAcesso[ iHistorico=" + iHistorico + " ]";
    }
    
}
