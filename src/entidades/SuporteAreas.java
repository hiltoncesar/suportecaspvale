/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hilton
 */
@Entity
@Table(name = "suporte_areas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuporteAreas.findAll", query = "SELECT s FROM SuporteAreas s"),
    @NamedQuery(name = "SuporteAreas.findByIArea", query = "SELECT s FROM SuporteAreas s WHERE s.iArea = :iArea"),
    @NamedQuery(name = "SuporteAreas.findByAreaNome", query = "SELECT s FROM SuporteAreas s WHERE s.areaNome = :areaNome"),
    @NamedQuery(name = "SuporteAreas.findByDescricaoArea", query = "SELECT s FROM SuporteAreas s WHERE s.descricaoArea = :descricaoArea")})
public class SuporteAreas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "i_area")
    private Integer iArea;
    @Column(name = "area_nome")
    private String areaNome;
    @Column(name = "descricao_area")
    private String descricaoArea;
    @OneToMany(mappedBy = "iArea")
    private List<SuporteUsuarios> suporteUsuariosList;

    public SuporteAreas() {
    }

    public SuporteAreas(Integer iArea) {
        this.iArea = iArea;
    }

    public Integer getIArea() {
        return iArea;
    }

    public void setIArea(Integer iArea) {
        this.iArea = iArea;
    }

    public String getAreaNome() {
        return areaNome;
    }

    public void setAreaNome(String areaNome) {
        this.areaNome = areaNome;
    }

    public String getDescricaoArea() {
        return descricaoArea;
    }

    public void setDescricaoArea(String descricaoArea) {
        this.descricaoArea = descricaoArea;
    }

    @XmlTransient
    public List<SuporteUsuarios> getSuporteUsuariosList() {
        return suporteUsuariosList;
    }

    public void setSuporteUsuariosList(List<SuporteUsuarios> suporteUsuariosList) {
        this.suporteUsuariosList = suporteUsuariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iArea != null ? iArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuporteAreas)) {
            return false;
        }
        SuporteAreas other = (SuporteAreas) object;
        if ((this.iArea == null && other.iArea != null) || (this.iArea != null && !this.iArea.equals(other.iArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.SuporteAreas[ iArea=" + iArea + " ]";
    }
    
}
