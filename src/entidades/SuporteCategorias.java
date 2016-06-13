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
@Table(name = "suporte_categorias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuporteCategorias.findAll", query = "SELECT s FROM SuporteCategorias s"),
    @NamedQuery(name = "SuporteCategorias.findByICategoria", query = "SELECT s FROM SuporteCategorias s WHERE s.iCategoria = :iCategoria"),
    @NamedQuery(name = "SuporteCategorias.findByAlteracao", query = "SELECT s FROM SuporteCategorias s WHERE s.alteracao = :alteracao"),
    @NamedQuery(name = "SuporteCategorias.findByCategoriaNome", query = "SELECT s FROM SuporteCategorias s WHERE s.categoriaNome = :categoriaNome"),
    @NamedQuery(name = "SuporteCategorias.findByDescricao", query = "SELECT s FROM SuporteCategorias s WHERE s.descricao = :descricao"),
    @NamedQuery(name = "SuporteCategorias.findBySituacao", query = "SELECT s FROM SuporteCategorias s WHERE s.situacao = :situacao")})
public class SuporteCategorias implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "i_categoria")
    private Integer iCategoria;
    @Column(name = "alteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date alteracao;
    @Column(name = "categoria_nome")
    private String categoriaNome;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "situacao")
    private String situacao;
    @OneToMany(mappedBy = "iCategoria")
    private List<SuporteAnexos> suporteAnexosList;

    public SuporteCategorias() {
    }

    public SuporteCategorias(Integer iCategoria) {
        this.iCategoria = iCategoria;
    }

    public Integer getICategoria() {
        return iCategoria;
    }

    public void setICategoria(Integer iCategoria) {
        this.iCategoria = iCategoria;
    }

    public Date getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(Date alteracao) {
        this.alteracao = alteracao;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
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
        hash += (iCategoria != null ? iCategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuporteCategorias)) {
            return false;
        }
        SuporteCategorias other = (SuporteCategorias) object;
        if ((this.iCategoria == null && other.iCategoria != null) || (this.iCategoria != null && !this.iCategoria.equals(other.iCategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.SuporteCategorias[ iCategoria=" + iCategoria + " ]";
    }
    
}
