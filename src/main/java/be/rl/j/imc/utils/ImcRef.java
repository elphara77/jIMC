package be.rl.j.imc.utils;

public enum ImcRef {

	DENUTRITION_0(0., 16.5, "dénutrition", "en"), //
	MAIGREUR_1(16.5, 18.5, "maigreur", "en"), //
	NORMAL_2(18.5, 25., "corpulence normale", "de"), //
	SURPOIDS_3(25., 30., "surpoids", "en"), //
	MODEREE_4(30., 35., "obésité modérée", "en"), //
	SEVERE_5(35., 40., "obésité sévère", "en"), //
	MASSIVE_6(40., Double.MAX_VALUE, "obésité massive", "en");

	private static final Double OVERLOAD_MAX = .1;

	private Double max = 0.;
	private Double min = 0.;
	private String description = "à plat :P !";
	private String prepoDescription = "en";

	private ImcRef(Double imcMin, Double imcMax, String description, String prepo) {
		this.min = imcMin;
		this.max = imcMax;
		this.description = description;
		this.prepoDescription = prepo;
	}

	public static ImcRef getImcRefs(Double imc) {
		for (ImcRef imcRef : ImcRef.values()) {
			if (imc >= imcRef.min && imc <= imcRef.max) {
				return imcRef;
			}
		}
		throw new RuntimeException("unknown reference IMC for this IMC precised !");
	}

	public static boolean isOverloadMax(Double overload) {
		return Math.abs(overload) <= OVERLOAD_MAX;
	}

	public static int getOverloadSign(Double overload) {
		if (overload > 0.) {
			return 1;
		} else if (overload < 0.) {
			return -1;
		}
		return 0;
	}

	public static String getDescription(Double imc) {
		for (ImcRef imcRef : ImcRef.values()) {
			if (imc >= imcRef.min && imc < imcRef.max) {
				return imcRef.description;
			}
		}
		throw new RuntimeException("unknown description for this IMC !");
	}

	public static String getPrepoDescription(Double imc) {
		for (ImcRef imcRef : ImcRef.values()) {
			if (imc >= imcRef.min && imc < imcRef.max) {
				return imcRef.prepoDescription;
			}
		}
		throw new RuntimeException("unknown prepo for this IMC !");
	}

	public static String getIntervalDescriptionAsValues(ImcRef imcRef) {
		if (ImcRef.MASSIVE_6.equals(imcRef) || ImcRef.DENUTRITION_0.equals(imcRef)) {
			return String.format("au-dessus de %.2f", imcRef.min);
		} else {
			return String.format("entre %.2f et %.2f", imcRef.min, imcRef.max);
		}
	}

	public static String getImcDescriptionAsSmiley(Double imc) {
		switch (getImcRefs(imc)) {
		case NORMAL_2:
			return "Vous êtes en forme :-)";
		case MAIGREUR_1:
		case SURPOIDS_3:
			return ":-(";
		case MODEREE_4:
		case SEVERE_5:
			return ":-S ATTENTION !";
		case DENUTRITION_0:
		case MASSIVE_6:
			return ":-S CRITIQUE !!!";
		default:
			throw new RuntimeException("unknown description smiley for this IMC !");
		}
	}

	public static String getOverloadDescriptionAsSmiley(Double imc, Double overload) {
		switch (getImcRefs(imc)) {
		case NORMAL_2:
			return ":-)";
		case MAIGREUR_1:
		case SURPOIDS_3:
			return ":-(";
		case MODEREE_4:
		case SEVERE_5:
			return ":-S !";
		case DENUTRITION_0:
		case MASSIVE_6:
			return ":-S !!!";
		default:
			throw new RuntimeException("unknown overload description smiley for this IMC !");
		}

	}

	public Double getMax() {
		return max;
	}

	public Double getMin() {
		return min;
	}

	public String getDescription() {
		return description;
	}

	public String getPrepoDescription() {
		return prepoDescription;
	}
}