package com.transengen.tier4.medsupputil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import com.transengen.data.jpa.ClientProductCustomizationEntityAPI;
import com.transengen.data.jpa.Tier4SessionFactory;
import com.transengen.data.jpa.entity.ClientProductCustomizationEntity;
import com.transengen.tier4.medsupputil.entity.MedSuppDataEntity;
import com.transengen.tier4.medsupputil.entity.MedSuppDataEntityAPI;
import com.transengen.tier4.medsupputil.entity.MedicareSupplementalCobDetailsEntity;


public class MedSuppUtil {

	public static final Logger log = Logger.getLogger(MedSuppUtil.class.getName());
	public static final Set<String> PLAN_K_L = new HashSet<String>(Arrays.asList("K", "L"));
	private static final Set<String> UNSUPPORTED_MEDICARE_STATES = new HashSet<String>(Arrays.asList("MA", "MN", "WI"));
	private static final Pattern INTEGER_MATCHER_PATTERN = Pattern.compile("(-?\\d+)(\\D.*)?");
	private static MedSuppUtil instance;
	
	public static MedSuppUtil getInstance() {
        if(instance == null){
            instance = new MedSuppUtil();
        }
        return instance;
    }

	private MedSuppUtil() {
		// No outside construction
	}


	public MedicareSupplementalCobDetailsEntity getMedicareSupplmentalCobDetails(/*policy1, policy2, policy3, state*/) {
		return null;
	}
	
	enum LashPlanTypeEnum {

		UNDISCLOSED("Undisclosed"), PART_A_B("Part A & B"), PART_A("Part A"), EPO("EPO"), HMO("HMO"), MEDICARE_MANAGED_CARE("Medicare Managed Care"), INDEMNITY("Indemnity"), MEDICAID("Medicaid"), SPECIAL_PLAN_TYPE_LOGIC("Special Plan Type Logic"), PPO("PPO"), POS("POS"), QMB("QMB"), STANDARD("Standard"), VA_BENEFITS("VA Benefits"), PRIME("Prime"), TRICARE_FOR_LIFE("Tricare For Life"), ;

		private String name;

		LashPlanTypeEnum(String name) {
			this.name = name;

		}

		public String getName() {
			return name;
		}
	}
	
	enum PayerTypeCrossWalkEnum {

		// TODO : Need to find one element missing

		MEDICARE_SECONDARY_WORKING_AGED("12", "Medicare Secondary Working Aged", LashPayerTypeEnum.MEDICARE, LashPlanTypeEnum.PART_A_B), MEDICARE_SECONDARY_END_STAGE_RENAL_DISEASE("13", "Medicare Secondary End-Stage Renal Disease", LashPayerTypeEnum.MEDICARE, LashPlanTypeEnum.PART_A_B), MEDICARE_SECONDARY_NO_FAULT_INSURANCE("14", "Medicare Secondary, No Fault Insurance", LashPayerTypeEnum.MEDICARE, LashPlanTypeEnum.PART_A_B), MEDICARE_SECONDARY_WORKER_S_COMPENSATION("15", "Medicare Secondary Worker'S Compensation", LashPayerTypeEnum.MEDICARE, LashPlanTypeEnum.PART_A_B), MEDICARE_SECONDARY_PUBLIC_HEALTH_SERVICE("16", "Medicare Secondary Public Health Service", LashPayerTypeEnum.MEDICARE, LashPlanTypeEnum.PART_A_B), MEDICARE_SECONDARY_BLACK_LUNG("41", "Medicare Secondary Black Lung", LashPayerTypeEnum.MEDICARE, LashPlanTypeEnum.PART_A_B),

		MEDICARE_SECONDARY_VETERAN_S_ADMINISTRATION("42", "Medicare Secondary Veteran'S Administration", LashPayerTypeEnum.MEDICARE, LashPlanTypeEnum.PART_A_B), MEDICARE_SECONDARY_DISABLED_BENEFICIARY_LESS_65("43", "Medicare Secondary Disabled Beneficiary <65", LashPayerTypeEnum.MEDICARE, LashPlanTypeEnum.PART_A_B), MEDICARE_SECONDARY_OTHER_LIABILITY_INSURANCE_PRIMARY("47", "Medicare Secondary Other Liability Insurance Primary", LashPayerTypeEnum.MEDICARE, LashPlanTypeEnum.PART_A_B), MEDICARE_CONDITIONALLY_PRIMARY("CP", "Medicare Conditionally Primary", LashPayerTypeEnum.MEDICARE, LashPlanTypeEnum.PART_A_B), HMO("HM", "HMO", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.HMO), HMO_MEDICARE_RISK("HN", "HMO Medicare Risk", LashPayerTypeEnum.MEDICARE, LashPlanTypeEnum.MEDICARE_MANAGED_CARE), SPECIAL_LOW_INCOME_MEDICARE_BENEFICIARY("HS", "Special Low Income Medicare Beneficiary", LashPayerTypeEnum.MEDICARE, LashPlanTypeEnum.PART_A_B), MEDICARE_PART_A("MA", "Medicare Part A", LashPayerTypeEnum.MEDICARE, LashPlanTypeEnum.PART_A), MEDICARE_PART_B("MB", "Medicare Part B", LashPayerTypeEnum.MEDICARE, LashPlanTypeEnum.PART_A_B), MEDICARE_PRIMARY("MP", "Medicare Primary", LashPayerTypeEnum.MEDICARE, LashPlanTypeEnum.PART_A_B), QUALIFIED_MEDICARE_BENEFICIARY("MP", "Qualified Medicare Beneficiary", LashPayerTypeEnum.MEDICARE, LashPlanTypeEnum.QMB),

		AUTO_INSURANCE_POLICY("AP", "Auto Insurance Policy", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.UNDISCLOSED), COMMERCIAL("C1", "Commercial", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.UNDISCLOSED), COBRA("CO", "COBRA", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.UNDISCLOSED), FAMILY_OR_FRIENDS("FF", "Family Or Friends", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.UNDISCLOSED), GROUP_POLICY("GP", "Group Policy", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.UNDISCLOSED), INDIVIDUAL_POLICY("IP", "Individual Policy", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.UNDISCLOSED), LONG_TERM_CARE("LC", "Long Term Care", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.UNDISCLOSED), LONG_TERM_POLICY("LD", "Long Term Policy", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.UNDISCLOSED), LIFE_INSURANCE("LI", "Life Insurance", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.UNDISCLOSED), PROPERTY_INSURANCE_PERSONAL("PE", "Property Insurance - Personal", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.UNDISCLOSED), PERSONAL("PL", "Personal", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.UNDISCLOSED), PROPERTY_INSURANCE_REAL("RP", "Property Insurance - Real", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.UNDISCLOSED),

		DISABILITY("D", "Disability", LashPayerTypeEnum.UNDISCLOSED, LashPlanTypeEnum.UNDISCLOSED), DISABILITY_BENEFITS("DB", "Disability Benefits", LashPayerTypeEnum.UNDISCLOSED, LashPlanTypeEnum.UNDISCLOSED), GENERAL("", "General", LashPayerTypeEnum.UNDISCLOSED, LashPlanTypeEnum.UNDISCLOSED), LITIGATION("LT", "Litigation", LashPayerTypeEnum.UNDISCLOSED, LashPlanTypeEnum.UNDISCLOSED), OTHER("OT", "Other", LashPayerTypeEnum.UNDISCLOSED, LashPlanTypeEnum.UNDISCLOSED), PERSONAL_PAYMENT_CASH_NO_INSURANCE("PP", "Personal Payment (Cash - No Insurance)", LashPayerTypeEnum.UNDISCLOSED, LashPlanTypeEnum.UNDISCLOSED), TAX_EQUITY_FISCAL_RESPONSIBILITY_ACT_TEFRA_("TF", "Tax Equity Fiscal Responsibility Act (Tefra)", LashPayerTypeEnum.UNDISCLOSED, LashPlanTypeEnum.UNDISCLOSED), WORKERS_COMPENSATION("WX", "Workers Compensation", LashPayerTypeEnum.UNDISCLOSED, LashPlanTypeEnum.UNDISCLOSED), WRAP_UP_POLICY("WU", "Wrap Up Policy", LashPayerTypeEnum.UNDISCLOSED, LashPlanTypeEnum.UNDISCLOSED),

		MEDICAID("MC", "Medicaid", LashPayerTypeEnum.MEDICAID, LashPlanTypeEnum.MEDICAID),

		MEDIGAP_PART_A("MH", "Medigap Part A", LashPayerTypeEnum.MEDICARE_SUPPLEMENT, LashPlanTypeEnum.SPECIAL_PLAN_TYPE_LOGIC), MEDIGAP_PART_B("MI", "Medigap Part B", LashPayerTypeEnum.MEDICARE_SUPPLEMENT, LashPlanTypeEnum.SPECIAL_PLAN_TYPE_LOGIC), SUPPLEMENTAL_POLICY("SP", "Supplemental Policy", LashPayerTypeEnum.MEDICARE_SUPPLEMENT, LashPlanTypeEnum.SPECIAL_PLAN_TYPE_LOGIC),

		INDEMNITY("IN", "Indemnity", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.INDEMNITY), POS("PS", "POS", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.POS), PPO("PR", "PPO", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.PPO), EXCLUSIVE_PROVIDER_ORGANIZATION("EP", "Exclusive Provider Organization", LashPayerTypeEnum.PRIVATE_COMMERCIAL, LashPlanTypeEnum.EPO),

		NOT_MATCH("NOT_MATCH", "NOT_MATCH", LashPayerTypeEnum.UNDISCLOSED, LashPlanTypeEnum.UNDISCLOSED), ;

		private String EB04Code;
		private String EB04Text;
		private LashPlanTypeEnum planType;
		private LashPayerTypeEnum payerType;

		public static PayerTypeCrossWalkEnum getPayerTypeCrossWalkByEB04Text(
				String EB04Text) {
			PayerTypeCrossWalkEnum returnEnum = NOT_MATCH;
			for (PayerTypeCrossWalkEnum e : PayerTypeCrossWalkEnum.values()) {

				if (StringUtils.equalsIgnoreCase(e.getEB04Code(), EB04Text)) {
					returnEnum = e;
					break;
				}
			}

			return returnEnum;
		}

		public static PayerTypeCrossWalkEnum getPayerTypeCrossWalkByEB04Code(
				String EB04Code) {
			PayerTypeCrossWalkEnum returnEnum = NOT_MATCH;
			for (PayerTypeCrossWalkEnum e : PayerTypeCrossWalkEnum.values()) {

				if (StringUtils.equalsIgnoreCase(e.getEB04Code(), EB04Code)) {
					returnEnum = e;
					break;
				}
			}
			return returnEnum;
		}

		PayerTypeCrossWalkEnum(String EB04Code, String EB04Text,
				LashPayerTypeEnum payerType, LashPlanTypeEnum planType) {
			this.EB04Text = EB04Text;
			this.EB04Code = EB04Code;
			this.planType = planType;
			this.payerType = payerType;
		}

		public String getEB04Code() {
			return EB04Code;
		}

		public String getEB04Text() {
			return EB04Text;
		}

		public LashPlanTypeEnum getPlanType() {
			return planType;
		}

		public LashPayerTypeEnum getPayerType() {
			return payerType;
		}
	}

	public static LashPayerTypeEnum payerType;

	public static String[] plansExcludedFromMedicareSupplemental = {
			"0001P Options A and C Medicare Supplemental High O",
			"HRLY-SAL RET SPON DEP SCN - BCBS H - AMENDED BLUE",
			"IL/I/PPO/AB33 POS60-GOLD/0200/BC/MCMED/INF/OBES (",
			"MED A 2000 FAM", "MEDICARE PART B", "MEDICAID/NJ FAMILYCARE A",
			"MEDICFIL COMPREHENSIVE - SPECIAL FOR STATE OF DE A",
			"MS BASIC,A+B,HHC,EX CHG,FT", "NJ MEDICAID/FAMILY CARE PLAN A",
			"PL104.POST65.P&M", "PLAN 121 CHCBP",
			"PPO Plan B - Salaried Non Union Hourly Retirees",
			"TRADITIONAL BLUE 901 PLAN A - SELF FUNDED ASO/NSO",
			"MEDIGAP PLAN N,UP TO A $20 COPAY", "MEDICARE PLAN A",
			"PLAN A WITH DDD", "CHEMTURA CORP. PLAN A" };

	public static final boolean safeLetterCheck(String letter, String expected) {
		return letter != null && expected.equalsIgnoreCase(expected);
	}

	public static final boolean safeLetterCheck(String letter, Collection<String> expected) {
		return letter != null && expected.contains(letter.toUpperCase());
	}

	public static boolean isMedSuppPlanNHandling(String clientName,
			String productName) throws HibernateException, SQLException {
		Session session = Tier4SessionFactory.getInstance().openSession();
		try {
			ClientProductCustomizationEntityAPI clientProductCustomizationEntityAPI = new ClientProductCustomizationEntityAPI(session);
			ClientProductCustomizationEntity clientProductCustomizationEntity = clientProductCustomizationEntityAPI.queryByClientAndProduct(clientName, productName);

			if (null == clientProductCustomizationEntity) {
				clientProductCustomizationEntity = clientProductCustomizationEntityAPI.fetchDefaultConfiguration();
			}
			return clientProductCustomizationEntity.isMedSuppPlanNHandlingApplies();
		} finally {
			session.close();
		}
	}

	public static double safeParseDecimal(String numberString) {
		return safeParseNumber(numberString).doubleValue();
	}

	public static final Number safeParseNumber(String stringValue) {
		try {
			return NumberFormat.getNumberInstance(java.util.Locale.US).parse(stringValue.trim());
		} catch (Exception e) {
			return 0;
		}
	}

	public static final long safeParseLong(String stringValue) {
		Matcher integerMatcher = INTEGER_MATCHER_PATTERN.matcher(StringUtils.trimToEmpty(stringValue));
		if (integerMatcher.matches()) {
			String integerString = integerMatcher.group(1);
			return Long.parseLong(integerString);
		} else {
			return 0;
		}
	}

	public static String formatNumber(String amount) {
		return formatNumber(amount, false);
	}

	public static String formatNumber(String amount, boolean blankShouldNotBecomeZero) {
		String formattedValue = "";
		if (StringUtils.isBlank(amount)) {
			if (!blankShouldNotBecomeZero) {
				formattedValue = "0";
			}
		} else {
			double dvalue = safeParseDecimal(amount);
			formattedValue = Long.toString(Math.round(dvalue));
		}
		return formattedValue;
	}


	public static String formatConditionalNumber(String amount,
			Boolean applies) {
		// return blank if applies != Yes, otherwise parse number & round to
		// nearest integer
		if (!applies) {
			return "";
		}
		return formatNumber(amount, false);
	}

	public static String formatConditionalNumber(String amount,
			Boolean applies, boolean blankShouldNotBecomeZero) {
		// return blank if applies != Yes, otherwise parse number & round to
		// nearest integer
		if (!applies) {
			return "";
		}
		return formatNumber(amount, blankShouldNotBecomeZero);
	}

	public static String[] determinePlanTypeAndPayerType(String payerId,
			String planName, String planTypeCode) {
		String[] planAndPayerType = new String[2];
		if (("10189".equalsIgnoreCase(payerId) || "10879".equalsIgnoreCase(payerId) || "10947".equalsIgnoreCase(payerId))) {
			log.info("In Method Payer Id match with 10189,10879,10947 For Plan Name:" + planName + ", payerId:" + payerId);
			if (StringUtils.isNotBlank(planName)) {
				log.info("In Method Plan Name not Blank");
				planName = planName.toLowerCase();
				if (planName.contains("standard")) {
					planAndPayerType[0] = "Standard";
					planAndPayerType[1] = "Tricare";
				} else if (planName.contains("prime")) {
					planAndPayerType[0] = "Prime";
					planAndPayerType[1] = "Tricare";
				} else if (planName.contains("for life")) {
					planAndPayerType[0] = "Tricare For Life";
					planAndPayerType[1] = "Medicare Supplement";
				} else {
					planAndPayerType[0] = "Undisclosed";
					planAndPayerType[1] = "Tricare";
				}
			} else {
				log.info("In Method else Plan Name Blank : determinePlanTypeAndPayerType - For Plan Name:" + planName + ", payerId:" + payerId);
				planAndPayerType[0] = "Undisclosed";
				planAndPayerType[1] = "Tricare";
			}
		} else if ("10196".equalsIgnoreCase(payerId) || "10850".equalsIgnoreCase(payerId) || "10956".equalsIgnoreCase(payerId) || "10061".equalsIgnoreCase(payerId)) {
			log.info("In Method else if case 10196 ,10850, 10956,10061 : determinePlanTypeAndPayerType - For Plan Name:" + planName + ", payerId:" + payerId);
			planAndPayerType[0] = "VA Benefits";
			planAndPayerType[1] = "VA";

		} else {
			log.info("In Method else case : determinePlanTypeAndPayerType - For Plan Type:" + planTypeCode);
			PayerTypeCrossWalkEnum payerTypeCrossWalkEnum = PayerTypeCrossWalkEnum.getPayerTypeCrossWalkByEB04Text(planTypeCode);
			planAndPayerType[0] = payerTypeCrossWalkEnum.getPlanType().getName();
			planAndPayerType[1] = payerTypeCrossWalkEnum.getPayerType().getName();
		}

		log.info("In Method : determinePlanTypeAndPayerType - For Plan Name:" + planName + " , planType:" + planAndPayerType[0] + " , payerType:" + planAndPayerType[1]);

		return planAndPayerType;
	}

	public static boolean isTricarePlan(String payerName) {
		return payerName != null && payerName.toLowerCase().contains("tricare");
	}


	public static boolean isMedSuppPlanKandLCoinsurance(String clientName,
			String productName) throws HibernateException, SQLException {

		Session session = Tier4SessionFactory.getInstance().openSession();
		try {
			ClientProductCustomizationEntityAPI clientProductCustomizationEntityAPI = new ClientProductCustomizationEntityAPI(session);
			ClientProductCustomizationEntity clientProductCustomizationEntity = clientProductCustomizationEntityAPI.queryByClientAndProduct(clientName, productName);

			if (null == clientProductCustomizationEntity) {
				clientProductCustomizationEntity = clientProductCustomizationEntityAPI.fetchDefaultConfiguration();
			}
			return clientProductCustomizationEntity.isMedSuppKandLCoinsuranceCoordinationApplies();
		} finally {
			session.close();
		}
	}

	public static boolean isMedSuppKandLOOPMetMissingMeansIncompleteApplies(String clientName,
			String productName) throws HibernateException, SQLException {

		Session session = Tier4SessionFactory.getInstance().openSession();
		try {
			ClientProductCustomizationEntityAPI clientProductCustomizationEntityAPI = new ClientProductCustomizationEntityAPI(session);
			ClientProductCustomizationEntity clientProductCustomizationEntity = clientProductCustomizationEntityAPI.queryByClientAndProduct(clientName, productName);

			if (null == clientProductCustomizationEntity) {
				clientProductCustomizationEntity = clientProductCustomizationEntityAPI.fetchDefaultConfiguration();
			}
			return clientProductCustomizationEntity.isMedSuppKandLOOPMetMissingMeansIncompleteApplies();
		} finally {
			session.close();
		}
	}

	public static MedSuppDataEntity getMedSuppDataEntityByPlanLetter(String planLetter) throws HibernateException, SQLException {
		Session session = Tier4SessionFactory.getInstance().openSession();
		try {
			MedSuppDataEntityAPI medSuppDataEntityAPI = new MedSuppDataEntityAPI(session);
			return medSuppDataEntityAPI.queryByPlanLetter(planLetter);
		} finally {
			session.close();
		}
	}

	public static int calculateMedSuppDataAmount(int tableValue) {
		int calulcatedPercentage = (int) (tableValue * (20 / 100.0f));
		return Math.round(calulcatedPercentage * 100);
	}

	public static boolean isMedSuppPlanFCompleteWithoutDeducMet(String clientName,
			String productName) throws HibernateException, SQLException {
		Session session = Tier4SessionFactory.getInstance().openSession();
		try {
			ClientProductCustomizationEntityAPI clientProductCustomizationEntityAPI = new ClientProductCustomizationEntityAPI(session);
			ClientProductCustomizationEntity clientProductCustomizationEntity = clientProductCustomizationEntityAPI.queryByClientAndProduct(clientName, productName);

			if (null == clientProductCustomizationEntity) {
				clientProductCustomizationEntity = clientProductCustomizationEntityAPI.fetchDefaultConfiguration();
			}
			return clientProductCustomizationEntity.isMedSuppPlanFCompleteWithoutDeductibleMetApplies();
		} finally {
			session.close();
		}
	}

	
	public static boolean isSanfordHealthPlan(String payerName) {
		return payerName != null && payerName.toLowerCase().contains("sanford health");
	}

	public static ArrayList<String> getDefaultCopayProviderTypes() {
		ArrayList<String> defaultCopyProviderTypes = new ArrayList<String>(3);
		defaultCopyProviderTypes.add("General Practitioner");
		defaultCopyProviderTypes.add("Family Practitioner");
		defaultCopyProviderTypes.add("Internal Medicine");
		return defaultCopyProviderTypes;
	}

	public static String computeCoinsurance(String adminAmount, String coinsSymbol) {
		double medicarePercent = 20;
		double supplementalPercent = safeParseDecimal(adminAmount) / 100;
		if (StringUtils.isBlank(adminAmount) || adminAmount.equals("100")) {
			// Do nothing percent is standard 80%
		} else if ("$".equals(coinsSymbol)) {
			medicarePercent = supplementalPercent;
		} else {
			medicarePercent *= supplementalPercent;
		}

		return ("$".equals(coinsSymbol) ? coinsSymbol : "") + Long.toString(Math.round(medicarePercent)) + ("%".equals(coinsSymbol) ? coinsSymbol : "");
	}

	public static boolean supplementalPlanCoversDeductible(String medicareSupplementalLetter) {
		return medicareSupplementalLetter != null && (medicareSupplementalLetter.equalsIgnoreCase("C") || medicareSupplementalLetter.equalsIgnoreCase("F"));
	}

	public static boolean isSecondaryMedicareSupplemental(String planName, String secondaryPayerName,
			String planLetter) {
		boolean isSecondaryMedSupp = false;
		if (secondaryPayerName != null && !StringUtils.containsIgnoreCase(secondaryPayerName, "Medicaid")) {

			if (planLetter != null) {
				isSecondaryMedSupp = true;
			} else if (isTricarePlan(secondaryPayerName)) {
				isSecondaryMedSupp = true;
			} else if (isSanfordHealthPlan(secondaryPayerName)) {
				isSecondaryMedSupp = true;
			}

			for (String excludedPlan : plansExcludedFromMedicareSupplemental) {
				if (StringUtils.startsWithIgnoreCase(planName, excludedPlan)) {
					isSecondaryMedSupp = false;
				}
			}
		}
		return isSecondaryMedSupp;
	}

	public static boolean isUnsupportedMedicareState(String treatingState) {
		return UNSUPPORTED_MEDICARE_STATES.contains(treatingState.toUpperCase());
	}
	
enum LashPayerTypeEnum {

		TRICARE("Tricare"), MEDICARE("Medicare"), MEDICAID("Medicaid"), MEDICARE_SUPPLEMENT("Medicare Supplement"), VA("VA"), PRIVATE_COMMERCIAL("Private Commercial"), UNDISCLOSED("Undisclosed");

		private String name;

		public String getName() {
			return name;
		}

		LashPayerTypeEnum(String name) {
			this.name = name;

		}

}


}