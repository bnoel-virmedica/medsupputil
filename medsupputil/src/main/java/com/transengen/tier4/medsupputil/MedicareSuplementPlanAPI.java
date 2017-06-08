/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.transengen.tier4.medsupputil;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import com.transengen.tier4.medsupputil.MedicareSuplementPlan;
import java.util.Arrays;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Caitlin Lopez
 */
class MedicareSuplementPlanAPI implements Serializable
{

    private static final long serialVersionUID = 1L;
   private static final Logger log = Logger.getLogger(MedicareSuplementPlanAPI.class);   
    private final Session session;

    /**
     * constructor sets the hibernate session
     *
     * @param session
     *
     */
    public MedicareSuplementPlanAPI(final Session session)
    {
        this.session = session;
    }

    /**
     * gets all the <code>MedicareSuplementPlan</code> as list
     *
     * @return all the <code>MedicareSuplementPlan</code> as list
     */
    public List<MedicareSuplementPlan> findMedicareSuplementPlanEntities()
    {
        return session.getNamedQuery("MedicareSuplementPlan.findAll").list();

    }

    /**
     * checks for all the plan letters
     *
     * @param letters the plan letters as list (can accept <code>null</code> in
     * this case will return false
     * @return <code>true</code> if plan letters matches the stored plan letter
     */
    public boolean findMedicareSuplementHasAllLetters(String[] letters)
    {

        boolean bRet = false;
        if (letters != null && letters.length > 0)
        {
            
            // create the criteria
              Criteria c = session.createCriteria(MedicareSuplementPlan.class);
              // do the in(letters)
              c.add(Restrictions.in("planletter", letters));
              List list=  c.list();
              log.debug("findMedicareSuplementHasAllLetters found  = " + list.size() + " for criteria " + Arrays.toString(letters));
             if ( list.isEmpty() == false)
             {
                  bRet = true;
             }
            
        }
        return bRet;
    }

    /**
     *
     * @param letter
     * @return
     */
    public boolean findMedicareSuplementHasLetter(String letter)
    {

        boolean bRet = false;
        if (letter != null && letter.isEmpty() == false)
        {
           
            Criteria c = session.createCriteria(MedicareSuplementPlan.class);
             c.add(Restrictions.eq("planletter", letter.toUpperCase()));
       
             List list = c.list();
             log.debug("findMedicareSuplementHasLetter list = " + list.size());
             if ( list.isEmpty() == false)
             {
                 bRet = true;
             }
        }
        return bRet;

    }
 /**
     * gets the <code>MedicareSuplementPlan</code> as list that matches the
     * letter
     *
     * @param letter the plan letter to match
     * @return the <code>MedicareSuplementPlan</code> as list that matches the
     * letter
     */
    public List<MedicareSuplementPlan> findMedicareSuplementPlanEntitiesfindByPlanletter(String letter) {

	Criteria c = session.createCriteria(MedicareSuplementPlan.class);
	c.add(Restrictions.eq("planletter", letter.toUpperCase()));

	List list = c.list();

	return list;

    }

    /**
     * gets the <code>MedicareSuplementPlan</code> as list that matches the id
     *
     * @param id the <code>MedicareSuplementPlan</code> id identifier
     * @return the <code>MedicareSuplementPlan</code> as list that matches the
     * id
     */
    public List<MedicareSuplementPlan> findMedicareSuplementPlanEntitiesById(int id)
    {
        return session.getNamedQuery("MedicareSuplementPlan.findAll").setInteger("ID", id).list();

    }

   

    /**
     * gets the <code>MedicareSuplementPlan</code> as list that matches the
     * patient co-insurance
     *
     * @param PATIENTCOINSURANCE the patient co-insurance identifier
     * @return the <code>MedicareSuplementPlan</code> as list that matches the
     * patient co-insurance
     */
    public List<MedicareSuplementPlan> findMedicareSuplementPlanEntitiesfindByPatientcoinsurance(int PATIENTCOINSURANCE)
    {
        return session.getNamedQuery("MedicareSuplementPlan.findByPatientcoinsurance").setInteger("PATIENTCOINSURANCE", PATIENTCOINSURANCE).list();

    }

    /**
     * gets the <code>MedicareSuplementPlan</code> as list that matches the
     * patient co-pay
     *
     * @param PATIENTCOPAY the patient co-pay identifier
     * @return the <code>MedicareSuplementPlan</code> as list that matches the
     * patient co-pay
     */
    public List<MedicareSuplementPlan> findMedicareSuplementPlanEntities(int PATIENTCOPAY)
    {
        return session.getNamedQuery("MedicareSuplementPlan.findByPatientcopay").setInteger("PATIENTCOPAY", PATIENTCOPAY).list();

    }

    /**
     * gets the <code>MedicareSuplementPlan</code> as list that matches the
     * patient part b covered deductible
     *
     * @param PARTBDEDUCTIBLECOVERED the patient part be covered deductible
     * @return he <code>MedicareSuplementPlan</code> as list that matches the
     * patient part b covered deductible
     */
    public List<MedicareSuplementPlan> findMedicareSuplementPlanEntitiesfindByPartbdeductiblecovered(String PARTBDEDUCTIBLECOVERED)
    {
        return session.getNamedQuery("MedicareSuplementPlan.findByPartbdeductiblecovered").setString("PARTBDEDUCTIBLECOVERED", PARTBDEDUCTIBLECOVERED).list();

    }

    /**
     * gets the <code>MedicareSuplementPlan</code> as list that matches the
     * patient op limit
     *
     * @param OOPLIMIT the op-limit identifier
     * @return the <code>MedicareSuplementPlan</code> as list that matches the
     * patient op limit
     */
    public List<MedicareSuplementPlan> findMedicareSuplementfindByOoplimit(String OOPLIMIT)
    {
        return session.getNamedQuery("MedicareSuplementPlan.findByOoplimit").setString("OOPLIMIT", OOPLIMIT).list();

    }
}
