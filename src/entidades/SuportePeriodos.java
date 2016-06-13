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
@Table(name = "suporte_periodos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuportePeriodos.findAll", query = "SELECT s FROM SuportePeriodos s"),
    @NamedQuery(name = "SuportePeriodos.findByIPeriodo", query = "SELECT s FROM SuportePeriodos s WHERE s.iPeriodo = :iPeriodo"),
    @NamedQuery(name = "SuportePeriodos.findByDtInicio", query = "SELECT s FROM SuportePeriodos s WHERE s.dtInicio = :dtInicio"),
    @NamedQuery(name = "SuportePeriodos.findByDtFim", query = "SELECT s FROM SuportePeriodos s WHERE s.dtFim = :dtFim"),
    @NamedQuery(name = "SuportePeriodos.findByMesReferencia", query = "SELECT s FROM SuportePeriodos s WHERE s.mesReferencia = :mesReferencia"),
    @NamedQuery(name = "SuportePeriodos.findByExercicio", query = "SELECT s FROM SuportePeriodos s WHERE s.exercicio = :exercicio")})
public class SuportePeriodos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "i_periodo")
    private Integer iPeriodo;
    @Basic(optional = false)
    @Column(name = "dt_inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtInicio;
    @Basic(optional = false)
    @Column(name = "dt_fim")
    @Temporal(TemporalType.TIME)
    private Date dtFim;
    @Column(name = "mes_referencia")
    private String mesReferencia;
    @Column(name = "exercicio")
    private Integer exercicio;
    @OneToMany(mappedBy = "iPeriodo")
    private List<SuportePontoeletronico> suportePontoeletronicoList;

    public SuportePeriodos() {
    }

    public SuportePeriodos(Integer iPeriodo) {
        this.iPeriodo = iPeriodo;
    }

    public SuportePeriodos(Integer iPeriodo, Date dtInicio, Date dtFim) {
        this.iPeriodo = iPeriodo;
        this.dtInicio = dtInicio;
        this.dtFim = dtFim;
    }

    public Integer getIPeriodo() {
        return iPeriodo;
    }

    public void setIPeriodo(Integer iPeriodo) {
        this.iPeriodo = iPeriodo;
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

    public String getMesReferencia() {
        return mesReferencia;
    }

    public void setMesReferencia(String mesReferencia) {
        this.mesReferencia = mesReferencia;
    }

    public Integer getExercicio() {
        return exercicio;
    }

    public void setExercicio(Integer exercicio) {
        this.exercicio = exercicio;
    }

    @XmlTransient
    public List<SuportePontoeletronico> getSuportePontoeletronicoList() {
        return suportePontoeletronicoList;
    }

    public void setSuportePontoeletronicoList(List<SuportePontoeletronico> suportePontoeletronicoList) {
        this.suportePontoeletronicoList = suportePontoeletronicoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iPeriodo != null ? iPeriodo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuportePeriodos)) {
            return false;
        }
        SuportePeriodos other = (SuportePeriodos) object;
        if ((this.iPeriodo == null && other.iPeriodo != null) || (this.iPeriodo != null && !this.iPeriodo.equals(other.iPeriodo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.SuportePeriodos[ iPeriodo=" + iPeriodo + " ]";
    }
    
}
