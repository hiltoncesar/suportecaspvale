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
@Table(name = "suporte_usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuporteUsuarios.findAll", query = "SELECT s FROM SuporteUsuarios s"),
    @NamedQuery(name = "SuporteUsuarios.findByIUsuario", query = "SELECT s FROM SuporteUsuarios s WHERE s.iUsuario = :iUsuario"),
    @NamedQuery(name = "SuporteUsuarios.findByAlteracao", query = "SELECT s FROM SuporteUsuarios s WHERE s.alteracao = :alteracao"),
    @NamedQuery(name = "SuporteUsuarios.findByCargo", query = "SELECT s FROM SuporteUsuarios s WHERE s.cargo = :cargo"),
    @NamedQuery(name = "SuporteUsuarios.findBySenha", query = "SELECT s FROM SuporteUsuarios s WHERE s.senha = :senha"),
    @NamedQuery(name = "SuporteUsuarios.findBySituacao", query = "SELECT s FROM SuporteUsuarios s WHERE s.situacao = :situacao"),
    @NamedQuery(name = "SuporteUsuarios.findByUsuario", query = "SELECT s FROM SuporteUsuarios s WHERE s.usuario = :usuario"),
    @NamedQuery(name = "SuporteUsuarios.findByUsuarioNome", query = "SELECT s FROM SuporteUsuarios s WHERE s.usuarioNome = :usuarioNome")})
public class SuporteUsuarios implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "i_usuario")
    private Integer iUsuario;
    @Column(name = "alteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date alteracao;
    @Column(name = "cargo")
    private String cargo;
    @Column(name = "senha")
    private String senha;
    @Column(name = "situacao")
    private String situacao;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "usuario_nome")
    private String usuarioNome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iUsuario")
    private List<SuportePontoeletronico> suportePontoeletronicoList;
    @OneToMany(mappedBy = "iUsuarioDe")
    private List<SuporteHistoricoChat> suporteHistoricoChatList;
    @OneToMany(mappedBy = "iUsuarioPara")
    private List<SuporteHistoricoChat> suporteHistoricoChatList1;
    @OneToMany(mappedBy = "iUsuario")
    private List<SuporteHistoricoAcesso> suporteHistoricoAcessoList;
    @JoinColumn(name = "i_area", referencedColumnName = "i_area")
    @ManyToOne
    private SuporteAreas iArea;

    public SuporteUsuarios() {
    }

    public SuporteUsuarios(Integer iUsuario) {
        this.iUsuario = iUsuario;
    }

    public Integer getIUsuario() {
        return iUsuario;
    }

    public void setIUsuario(Integer iUsuario) {
        this.iUsuario = iUsuario;
    }

    public Date getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(Date alteracao) {
        this.alteracao = alteracao;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    @XmlTransient
    public List<SuportePontoeletronico> getSuportePontoeletronicoList() {
        return suportePontoeletronicoList;
    }

    public void setSuportePontoeletronicoList(List<SuportePontoeletronico> suportePontoeletronicoList) {
        this.suportePontoeletronicoList = suportePontoeletronicoList;
    }

    @XmlTransient
    public List<SuporteHistoricoChat> getSuporteHistoricoChatList() {
        return suporteHistoricoChatList;
    }

    public void setSuporteHistoricoChatList(List<SuporteHistoricoChat> suporteHistoricoChatList) {
        this.suporteHistoricoChatList = suporteHistoricoChatList;
    }

    @XmlTransient
    public List<SuporteHistoricoChat> getSuporteHistoricoChatList1() {
        return suporteHistoricoChatList1;
    }

    public void setSuporteHistoricoChatList1(List<SuporteHistoricoChat> suporteHistoricoChatList1) {
        this.suporteHistoricoChatList1 = suporteHistoricoChatList1;
    }

    @XmlTransient
    public List<SuporteHistoricoAcesso> getSuporteHistoricoAcessoList() {
        return suporteHistoricoAcessoList;
    }

    public void setSuporteHistoricoAcessoList(List<SuporteHistoricoAcesso> suporteHistoricoAcessoList) {
        this.suporteHistoricoAcessoList = suporteHistoricoAcessoList;
    }

    public SuporteAreas getIArea() {
        return iArea;
    }

    public void setIArea(SuporteAreas iArea) {
        this.iArea = iArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iUsuario != null ? iUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuporteUsuarios)) {
            return false;
        }
        SuporteUsuarios other = (SuporteUsuarios) object;
        if ((this.iUsuario == null && other.iUsuario != null) || (this.iUsuario != null && !this.iUsuario.equals(other.iUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.SuporteUsuarios[ iUsuario=" + iUsuario + " ]";
    }
    
}
