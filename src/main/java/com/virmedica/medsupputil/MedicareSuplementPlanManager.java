/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.virmedica.medsupputil;

import com.transengen.data.jpa.Tier4SessionFactory;
import com.virmedica.medsupputil.MedicareSuplementPlan;

import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 *
 * @author Caitlin Lopez
 */
public class MedicareSuplementPlanManager {



	private MedicareSuplementPlanManager() {


	}



	private static final MedicareSuplementPlanManager INSTANCE = new MedicareSuplementPlanManager();

	public static MedicareSuplementPlanManager getInstance() {
		return INSTANCE;
	}

	private static final Logger log = Logger.getLogger(MedicareSuplementPlanManager.class);

	/**
	 * checks for all the plan letters
	 *
	 * @param letter the plan letter stored in bvr.medicareSupplementalLetter
	 * @param planLetters the plan letters as list (can accept <code>null</code>
	 * in this case will return false
	 * @return <code>true</code> if plan letters matches the stored plan letter
	 */
	public boolean MedicareSupplementaAlllLetterMatches(String letter, String[] planLetters) {
		Session session = null;
		boolean bRet = false;
		try {
			if (planLetters != null && planLetters.length > 0) {
				session = Tier4SessionFactory.getInstance().openSession();
				MedicareSuplementPlanAPI api = new MedicareSuplementPlanAPI(session);

				// see if is in the database
				bRet = api.findMedicareSuplementHasAllLetters(planLetters);
				if (bRet) {
					for (String v : planLetters) {
						if (v.equalsIgnoreCase(letter)) {
							bRet = true;
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return bRet;

	}



	/**
	 * giving the plan letter returns the following information
	 * <ul>
      <li>patientcoinsurance the patient coinsurance</li>
       <li> patientcopay</li>
       <li>partbdeductiblecovered</li>
       <li>ooplimit</li>
	 * </ul>
	 * @param planLetter the plan letter
	 * @param valueName one of
	 * * <ul>
        <li>patientcoinsurance the patient coinsurance</li>
       <li> patientcopay</li>
       <li>partbdeductiblecovered</li>
       <li>ooplimit</li>
	 * </ul>
	 * @return <code>null</code> if valueName is invalid or the value
	 */
	public String getMedicareInfo(String planLetter, MedicareSuplementPlanParameters valueName) {
		Session session = null;
		String  ret = "";
		try {
			if (planLetter != null && planLetter.isEmpty() == false) {

				session = Tier4SessionFactory.getInstance().openSession();
				MedicareSuplementPlanAPI api = new MedicareSuplementPlanAPI(session);
				List<MedicareSuplementPlan> list = api.findMedicareSuplementPlanEntitiesfindByPlanletter(planLetter);
				if ( list.isEmpty() == false)
				{
					MedicareSuplementPlan plan = list.get(0);
					switch(valueName)
					{

					case PATIENTCOINSURANCE:
						ret =plan.getPatientcoinsurance().toString();
						break;
					case PATIENTCOPAY:
						ret = plan.getPatientcopay().toString();
						break;
					case PARTBDEDUCTIBLECOVERED:
						ret = plan.getPartbdeductiblecovered();
						break;
					case OOPLIMIT:
						ret = plan.getOoplimit();
						break;
					case MEDIGAPCOINSURANCE:
						ret = plan.getMEDIGAPCOINSURANCE();
						break;
					default:
						ret="0";
						break;
					}



				}

			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return ret;

	}

	/**
	 * checks if the plan letter matches the stored value
	 *
	 * @param letter the plan letter, note that this can be <code>null</code>
	 * (will return false in this case)
	 * @param planLetter use to determine if this query matches the requested
	 * query
	 *
	 * @return <code>true</code> if the plan letter matches the stored value
	 */
	public boolean MedicareSupplementalLetterMatches(String letter, String planLetter) {
		Session session = null;
		boolean bRet = false;
		try {
			if (letter != null && letter.isEmpty() == false) {
				if (planLetter != null && planLetter.isEmpty() == false) {
					session = Tier4SessionFactory.getInstance().openSession();
					MedicareSuplementPlanAPI api = new MedicareSuplementPlanAPI(session);
					// see if is in the database
					bRet = api.findMedicareSuplementHasLetter(letter);
					if (bRet) {
						bRet = planLetter.equalsIgnoreCase(letter);
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return bRet;

	}

}
