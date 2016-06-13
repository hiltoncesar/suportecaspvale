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
@Table(name = "suporte_resumo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuporteResumo.findAll", query = "SELECT s FROM SuporteResumo s"),
    @NamedQuery(name = "SuporteResumo.findByIPonto", query = "SELECT s FROM SuporteResumo s WHERE s.iPonto = :iPonto"),
    @NamedQuery(name = "SuporteResumo.findByIUsuario", query = "SELECT s FROM SuporteResumo s WHERE s.iUsuario = :iUsuario"),
    @NamedQuery(name = "SuporteResumo.findByDtRegistro", query = "SELECT s FROM SuporteResumo s WHERE s.dtRegistro = :dtRegistro"),
    @NamedQuery(name = "SuporteResumo.findByTotal", query = "SELECT s FROM SuporteResumo s WHERE s.total = :total")})
public class SuporteResumo implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "i_ponto")
    @Id
    private Integer iPonto;
    @Column(name = "i_usuario")
    private Integer iUsuario;
    @Column(name = "dt_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtRegistro;
    @Column(name = "total")
    @Temporal(TemporalType.TIMESTAMP)
    private Date total;

    public SuporteResumo() {
    }

    public Integer getIPonto() {
        return iPonto;
    }

    public void setIPonto(Integer iPonto) {
        this.iPonto = iPonto;
    }

    public Integer getIUsuario() {
        return iUsuario;
    }

    public void setIUsuario(Integer iUsuario) {
        this.iUsuario = iUsuario;
    }

    public Date getDtRegistro() {
        return dtRegistro;
    }

    public void setDtRegistro(Date dtRegistro) {
        this.dtRegistro = dtRegistro;
    }

    public Date getTotal() {
        return total;
    }

    public void setTotal(Date total) {
        this.total = total;
    }
    
}
