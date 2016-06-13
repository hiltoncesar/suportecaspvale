/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hilton
 */
@Entity
@Table(name = "suporte_config_ponto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuporteConfigPonto.findAll", query = "SELECT s FROM SuporteConfigPonto s"),
    @NamedQuery(name = "SuporteConfigPonto.findByIConfigPonto", query = "SELECT s FROM SuporteConfigPonto s WHERE s.iConfigPonto = :iConfigPonto"),
    @NamedQuery(name = "SuporteConfigPonto.findByAntecipaEntrada", query = "SELECT s FROM SuporteConfigPonto s WHERE s.antecipaEntrada = :antecipaEntrada"),
    @NamedQuery(name = "SuporteConfigPonto.findByAntecipaAlmoco", query = "SELECT s FROM SuporteConfigPonto s WHERE s.antecipaAlmoco = :antecipaAlmoco"),
    @NamedQuery(name = "SuporteConfigPonto.findByAntecipaRetorno", query = "SELECT s FROM SuporteConfigPonto s WHERE s.antecipaRetorno = :antecipaRetorno"),
    @NamedQuery(name = "SuporteConfigPonto.findByAntecipaSaida", query = "SELECT s FROM SuporteConfigPonto s WHERE s.antecipaSaida = :antecipaSaida"),
    @NamedQuery(name = "SuporteConfigPonto.findByProrrogaEntrada", query = "SELECT s FROM SuporteConfigPonto s WHERE s.prorrogaEntrada = :prorrogaEntrada"),
    @NamedQuery(name = "SuporteConfigPonto.findByProrrogaAlmoco", query = "SELECT s FROM SuporteConfigPonto s WHERE s.prorrogaAlmoco = :prorrogaAlmoco"),
    @NamedQuery(name = "SuporteConfigPonto.findByProrrogaRetorno", query = "SELECT s FROM SuporteConfigPonto s WHERE s.prorrogaRetorno = :prorrogaRetorno"),
    @NamedQuery(name = "SuporteConfigPonto.findByProrrogaSaida", query = "SELECT s FROM SuporteConfigPonto s WHERE s.prorrogaSaida = :prorrogaSaida")})
public class SuporteConfigPonto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "i_config_ponto")
    private Integer iConfigPonto;
    @Basic(optional = false)
    @Column(name = "antecipa_entrada")
    private int antecipaEntrada;
    @Basic(optional = false)
    @Column(name = "antecipa_almoco")
    private int antecipaAlmoco;
    @Basic(optional = false)
    @Column(name = "antecipa_retorno")
    private int antecipaRetorno;
    @Basic(optional = false)
    @Column(name = "antecipa_saida")
    private int antecipaSaida;
    @Basic(optional = false)
    @Column(name = "prorroga_entrada")
    private int prorrogaEntrada;
    @Basic(optional = false)
    @Column(name = "prorroga_almoco")
    private int prorrogaAlmoco;
    @Basic(optional = false)
    @Column(name = "prorroga_retorno")
    private int prorrogaRetorno;
    @Basic(optional = false)
    @Column(name = "prorroga_saida")
    private int prorrogaSaida;

    public SuporteConfigPonto() {
    }

    public SuporteConfigPonto(Integer iConfigPonto) {
        this.iConfigPonto = iConfigPonto;
    }

    public SuporteConfigPonto(Integer iConfigPonto, int antecipaEntrada, int antecipaAlmoco, int antecipaRetorno, int antecipaSaida, int prorrogaEntrada, int prorrogaAlmoco, int prorrogaRetorno, int prorrogaSaida) {
        this.iConfigPonto = iConfigPonto;
        this.antecipaEntrada = antecipaEntrada;
        this.antecipaAlmoco = antecipaAlmoco;
        this.antecipaRetorno = antecipaRetorno;
        this.antecipaSaida = antecipaSaida;
        this.prorrogaEntrada = prorrogaEntrada;
        this.prorrogaAlmoco = prorrogaAlmoco;
        this.prorrogaRetorno = prorrogaRetorno;
        this.prorrogaSaida = prorrogaSaida;
    }

    public Integer getIConfigPonto() {
        return iConfigPonto;
    }

    public void setIConfigPonto(Integer iConfigPonto) {
        this.iConfigPonto = iConfigPonto;
    }

    public int getAntecipaEntrada() {
        return antecipaEntrada;
    }

    public void setAntecipaEntrada(int antecipaEntrada) {
        this.antecipaEntrada = antecipaEntrada;
    }

    public int getAntecipaAlmoco() {
        return antecipaAlmoco;
    }

    public void setAntecipaAlmoco(int antecipaAlmoco) {
        this.antecipaAlmoco = antecipaAlmoco;
    }

    public int getAntecipaRetorno() {
        return antecipaRetorno;
    }

    public void setAntecipaRetorno(int antecipaRetorno) {
        this.antecipaRetorno = antecipaRetorno;
    }

    public int getAntecipaSaida() {
        return antecipaSaida;
    }

    public void setAntecipaSaida(int antecipaSaida) {
        this.antecipaSaida = antecipaSaida;
    }

    public int getProrrogaEntrada() {
        return prorrogaEntrada;
    }

    public void setProrrogaEntrada(int prorrogaEntrada) {
        this.prorrogaEntrada = prorrogaEntrada;
    }

    public int getProrrogaAlmoco() {
        return prorrogaAlmoco;
    }

    public void setProrrogaAlmoco(int prorrogaAlmoco) {
        this.prorrogaAlmoco = prorrogaAlmoco;
    }

    public int getProrrogaRetorno() {
        return prorrogaRetorno;
    }

    public void setProrrogaRetorno(int prorrogaRetorno) {
        this.prorrogaRetorno = prorrogaRetorno;
    }

    public int getProrrogaSaida() {
        return prorrogaSaida;
    }

    public void setProrrogaSaida(int prorrogaSaida) {
        this.prorrogaSaida = prorrogaSaida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iConfigPonto != null ? iConfigPonto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuporteConfigPonto)) {
            return false;
        }
        SuporteConfigPonto other = (SuporteConfigPonto) object;
        if ((this.iConfigPonto == null && other.iConfigPonto != null) || (this.iConfigPonto != null && !this.iConfigPonto.equals(other.iConfigPonto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.SuporteConfigPonto[ iConfigPonto=" + iConfigPonto + " ]";
    }
    
}
