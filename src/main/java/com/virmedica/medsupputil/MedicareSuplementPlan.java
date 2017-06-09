package com.virmedica.medsupputil;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Caitlin Lopez
 */
@Entity
@Table(name = "MEDICARE_SUPLEMENT_PLAN")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "MedicareSuplementPlan.findAll", query = "SELECT m FROM MedicareSuplementPlan m")
            , @NamedQuery(name = "MedicareSuplementPlan.findById", query = "SELECT m FROM MedicareSuplementPlan m WHERE m.id = :id")
            , @NamedQuery(name = "MedicareSuplementPlan.findByPlanletter", query = "SELECT m FROM MedicareSuplementPlan m WHERE m.planletter = :planletter")
            , @NamedQuery(name = "MedicareSuplementPlan.findByPatientcoinsurance", query = "SELECT m FROM MedicareSuplementPlan m WHERE m.patientcoinsurance = :patientcoinsurance")
            , @NamedQuery(name = "MedicareSuplementPlan.findByPatientcopay", query = "SELECT m FROM MedicareSuplementPlan m WHERE m.patientcopay = :patientcopay")
            , @NamedQuery(name = "MedicareSuplementPlan.findByPartbdeductiblecovered", query = "SELECT m FROM MedicareSuplementPlan m WHERE m.partbdeductiblecovered = :partbdeductiblecovered")
            , @NamedQuery(name = "MedicareSuplementPlan.findByOoplimit", query = "SELECT m FROM MedicareSuplementPlan m WHERE m.ooplimit = :ooplimit")
            , @NamedQuery(name = "MedicareSuplementPlan.hasLetter", query = "SELECT count(*) FROM MedicareSuplementPlan m WHERE m.planletter = :planletter")
            , @NamedQuery(name = "MedicareSuplementPlan.hasAllLetters", query = "SELECT count(*) FROM MedicareSuplementPlan m WHERE m.planletter in (:planletter)")

        })
public class MedicareSuplementPlan implements Serializable
{

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private BigDecimal id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "PLANLETTER")
    private String planletter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PATIENTCOINSURANCE")
    private BigInteger patientcoinsurance;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PATIENTCOPAY")
    private BigDecimal patientcopay;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "PARTBDEDUCTIBLECOVERED")
    private String partbdeductiblecovered;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 430)
    @Column(name = "OOPLIMIT")
    private String ooplimit;
    
    @Size(min = 1, max = 30)
    @Column(name = "MEDIGAPCOINSURANCE")
    private String MEDIGAPCOINSURANCE;
    
    public MedicareSuplementPlan()
    {
    }

    public MedicareSuplementPlan(BigDecimal id)
    {
        this.id = id;
    }

    public MedicareSuplementPlan(BigDecimal id, String planletter, BigInteger patientcoinsurance, BigDecimal patientcopay, String partbdeductiblecovered, String ooplimit)
    {
        this.id = id;
        this.planletter = planletter;
        this.patientcoinsurance = patientcoinsurance;
        this.patientcopay = patientcopay;
        this.partbdeductiblecovered = partbdeductiblecovered;
        this.ooplimit = ooplimit;
    }

    public BigDecimal getId()
    {
        return id;
    }

    public void setId(BigDecimal id)
    {
        this.id = id;
    }

    public String getPlanletter()
    {
        return planletter;
    }

    public void setPlanletter(String planletter)
    {
        this.planletter = planletter;
    }

    public BigInteger getPatientcoinsurance()
    {
        return patientcoinsurance;
    }

    public void setPatientcoinsurance(BigInteger patientcoinsurance)
    {
        this.patientcoinsurance = patientcoinsurance;
    }

    public BigDecimal getPatientcopay()
    {
        return patientcopay;
    }

    public void setPatientcopay(BigDecimal patientcopay)
    {
        this.patientcopay = patientcopay;
    }

    public String getPartbdeductiblecovered()
    {
        return partbdeductiblecovered;
    }

    public void setPartbdeductiblecovered(String partbdeductiblecovered)
    {
        this.partbdeductiblecovered = partbdeductiblecovered;
    }

    public String getOoplimit()
    {
        return ooplimit;
    }

    public void setOoplimit(String ooplimit)
    {
        this.ooplimit = ooplimit;
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicareSuplementPlan))
        {
            return false;
        }
        MedicareSuplementPlan other = (MedicareSuplementPlan) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    public String getMEDIGAPCOINSURANCE() {
	return MEDIGAPCOINSURANCE;
    }

    public void setMEDIGAPCOINSURANCE(String MEDIGAPCOINSURANCE) {
	this.MEDIGAPCOINSURANCE = MEDIGAPCOINSURANCE;
    }

    @Override
    public String toString()
    {
        return "com.virmedica.database.MedicareSuplementPlan[ id=" + id + " ]";
    }

}
