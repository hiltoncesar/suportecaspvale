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
import javax.persistence.Lob;
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
@Table(name = "suporte_anexos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuporteAnexos.findAll", query = "SELECT s FROM SuporteAnexos s"),
    @NamedQuery(name = "SuporteAnexos.findByIAnexo", query = "SELECT s FROM SuporteAnexos s WHERE s.iAnexo = :iAnexo"),
    @NamedQuery(name = "SuporteAnexos.findByAlteracao", query = "SELECT s FROM SuporteAnexos s WHERE s.alteracao = :alteracao"),
    @NamedQuery(name = "SuporteAnexos.findByAnexoNome", query = "SELECT s FROM SuporteAnexos s WHERE s.anexoNome = :anexoNome"),
    @NamedQuery(name = "SuporteAnexos.findByDiretorio", query = "SELECT s FROM SuporteAnexos s WHERE s.diretorio = :diretorio"),
    @NamedQuery(name = "SuporteAnexos.findBySituacao", query = "SELECT s FROM SuporteAnexos s WHERE s.situacao = :situacao")})
public class SuporteAnexos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "i_anexo")
    private Integer iAnexo;
    @Column(name = "alteracao")
    @Temporal(TemporalType.TIMESTAMP)
    private Date alteracao;
    @Column(name = "anexo_nome")
    private String anexoNome;
    @Lob
    @Column(name = "arquivo")
    private byte[] arquivo;
    @Column(name = "diretorio")
    private String diretorio;
    @Column(name = "situacao")
    private String situacao;
    @JoinColumn(name = "i_categoria", referencedColumnName = "i_categoria")
    @ManyToOne
    private SuporteCategorias iCategoria;
    @JoinColumn(name = "i_entidade", referencedColumnName = "i_entidade")
    @ManyToOne
    private SuporteEntidades iEntidade;

    public SuporteAnexos() {
    }
    
    public SuporteAnexos(int iAnexo, String entNome ,String catNome,String diretori,String anexoNome, Date alter) {
        this.iAnexo = iAnexo;
        this.iEntidade.setEntidadeNome(entNome); 
        this.iCategoria.setCategoriaNome(catNome);
        this.diretorio = diretori;
        this.anexoNome = anexoNome;
        this.alteracao = alter;
    }

    public SuporteAnexos(Integer iAnexo) {
        this.iAnexo = iAnexo;
    }

    public Integer getIAnexo() {
        return iAnexo;
    }

    public void setIAnexo(Integer iAnexo) {
        this.iAnexo = iAnexo;
    }

    public Date getAlteracao() {
        return alteracao;
    }

    public void setAlteracao(Date alteracao) {
        this.alteracao = alteracao;
    }

    public String getAnexoNome() {
        return anexoNome;
    }

    public void setAnexoNome(String anexoNome) {
        this.anexoNome = anexoNome;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public String getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public SuporteCategorias getICategoria() {
        return iCategoria;
    }

    public void setICategoria(SuporteCategorias iCategoria) {
        this.iCategoria = iCategoria;
    }

    public SuporteEntidades getIEntidade() {
        return iEntidade;
    }

    public void setIEntidade(SuporteEntidades iEntidade) {
        this.iEntidade = iEntidade;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iAnexo != null ? iAnexo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SuporteAnexos)) {
            return false;
        }
        SuporteAnexos other = (SuporteAnexos) object;
        if ((this.iAnexo == null && other.iAnexo != null) || (this.iAnexo != null && !this.iAnexo.equals(other.iAnexo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.SuporteAnexos[ iAnexo=" + iAnexo + " ]";
    }
    
}
