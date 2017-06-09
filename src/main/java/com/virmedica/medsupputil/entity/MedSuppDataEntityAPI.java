package com.virmedica.medsupputil.entity;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class MedSuppDataEntityAPI {

	private final Session session;

	public MedSuppDataEntityAPI(Session session) {
		this.session = session;
	}

	public MedSuppDataEntity queryByPlanLetter(String planLetter) {
		MedSuppDataEntity entity = new MedSuppDataEntity();
		if (planLetter != null) {
			Criteria criteria = session.createCriteria(MedSuppDataEntity.class);
			criteria.add(Restrictions.eq("planLetter", planLetter.toUpperCase()));
			entity = (MedSuppDataEntity) criteria.uniqueResult();
		}
		return entity;
	}
}
