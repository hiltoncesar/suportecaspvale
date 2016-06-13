/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "suporte_entidades")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuporteEntidades.findAll", query = "SELECT s FROM SuporteEntidades s"),
    @NamedQuery(name = "SuporteEntidades.findByIEntidade", query = "SELECT s FROM SuporteEntidades s WHERE s.iEntidade = :iEntidade"),
    @NamedQuery(name = "SuporteEntidades.findByAlteracao", query = "SELECT s FROM SuporteEntidades s WHERE s.alteracao = :alteracao"),
    @NamedQuery(name = "SuporteEntidades.findByCnpj", query = "SELECT s FROM SuporteEntidades s WHERE s.cnpj = :cnpj"),
    @NamedQuery(name = "SuporteEntidades.findByContato", query = "SELECT s FROM SuporteEntidades s WHERE s.contato = :contato"),
    @NamedQuery(name = "SuporteEntidades.findByEndereco", query = "SELECT s FROM SuporteEntidades s WHERE s.endereco = :endereco"),
    @NamedQuery(name = "SuporteEntidades.findByEntidadeNome", query = "SELECT s FROM SuporteEntidades s WHERE s.entidadeNome = :entidadeNome"),
    @NamedQuery(name = "SuporteEntidades.findBySituacao", query = "SELECT s FROM SuporteEntidades s WHERE s.situacao = :situacao")})
public class SuporteEntidades implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "i_entidade")
    private Integer iEntidade;
    @Column(name = "alteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date alteracao;
    @Column(name = "cnpj")
    private String cnpj;
    @Column(name = "contato")
    private String contato;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "entidade_nome")
    private String entidadeNome;
    @Column(name = "situacao")
    private String situacao;
    @OneToMany(mappedBy = "iEntidade")
    private List<SuporteAcessoRemoto> suporteAcessoRemotoList;
    @OneToMany(mappedBy = "iEntidade")
    private List<SuporteAnexos> suporteAnexosList;

    public SuporteEntidades() {
    }

    public SuporteEntidades(Integer iEntidade) {
        this.iEntidade = iEntidade;
    }

    public Integer getIEntidade() {
        return iEntidade;
    }

    public void setIEntidade(Integer iEntidade) {
        this.iEntidade = iEntidade;
    }

    public Date getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(Date alteracao) {
        this.alteracao = alteracao;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEntidadeNome() {
        return entidadeNome;
    }

    public void setEntidadeNome(String entidadeNome) {
        this.entidadeNome = entidadeNome;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    @XmlTransient
    public List<SuporteAcessoRemoto> getSuporteAcessoRemotoList() {
        return suporteAcessoRemotoList;
    }

    public void setSuporteAcessoRemotoList(List<SuporteAcessoRemoto> suporteAcessoRemotoList) {
        this.suporteAcessoRemotoList = suporteAcessoRemotoList;
    }

    @XmlTransient
    public List<SuporteAnexos> getSuporteAnexosList() {
        return suporteAnexosList;
    }

    public void setSuporteAnexosList(List<SuporteAnexos> suporteAnexosList) {
        this.suporteAnexosList = suporteAnexosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iEntidade != null ? iEntidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuporteEntidades)) {
            return false;
        }
        SuporteEntidades other = (SuporteEntidades) object;
        if ((this.iEntidade == null && other.iEntidade != null) || (this.iEntidade != null && !this.iEntidade.equals(other.iEntidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.SuporteEntidades[ iEntidade=" + iEntidade + " ]";
    }
    
}
