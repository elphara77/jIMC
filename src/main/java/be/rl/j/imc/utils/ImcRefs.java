package be.rl.j.imc.utils;

public enum ImcRefs {

	DENUTRITION_0(0., 16.5, "dénutrition", "en"), //
	MAIGREUR_1(16.5, 18.5, "maigreur", "en"), //
	NORMAL_2(18.5, 25., "corpulence normale", "de"), //
	SURPOIDS_3(25., 30., "surpoids", "en"), //
	MODEREE_4(30., 35., "obésité modérée", "en"), //
	SEVERE_5(35., 40., "obésité sévère", "en"), //
	MASSIVE_6(40., Double.MAX_VALUE, "obésité massive", "en");

	private Double max = 0.;
	private Double min = 0.;
	private String description = "à plat :P !";
	private String prepoDescription = "en";

	private ImcRefs(Double imcMin, Double imcMax, String description, String prepo) {
		this.min = imcMin;
		this.max = imcMax;
		this.description = description;
		this.prepoDescription = prepo;
	}

	public static ImcRefs getImcRefs(Double imc) {
		for (ImcRefs imcRef : ImcRefs.values()) {
			if (imc >= imcRef.min && imc <= imcRef.max) {
				return imcRef;
			}
		}
		throw new RuntimeException("IMC incalculable !");
	}

	public static Double getOverload(Double overload, ImcRefs ideal) {
		switch (ideal) {
		case DENUTRITION_0:
			return overload;
		case MAIGREUR_1:
			return overload;
		case NORMAL_2:
			return 0.;
		case SURPOIDS_3:
			return overload;
		case MODEREE_4:
			return overload;
		case SEVERE_5:
			return overload;
		case MASSIVE_6:
			return overload;
		default:
			throw new RuntimeException("IMC incalculable !");
		}
	}

	public static String getDescription(Double imc) {
		for (ImcRefs imcRef : ImcRefs.values()) {
			if (imc >= imcRef.min && imc < imcRef.max) {
				return imcRef.description;
			}
		}
		throw new RuntimeException("IMC incalculable !");
	}

	public static String getPrepoDescription(Double imc) {
		for (ImcRefs imcRef : ImcRefs.values()) {
			if (imc >= imcRef.min && imc < imcRef.max) {
				return imcRef.prepoDescription;
			}
		}
		throw new RuntimeException("IMC incalculable !");
	}

	public static String getDescriptionAsValues(ImcRefs ideal) {
		if (ImcRefs.MASSIVE_6.equals(ideal)) {
			return String.format("au-dessus de %.2f", ideal.min);
		} else {
			return String.format("entre %.2f et %.2f", ideal.min, ideal.max);
		}
	}

	public static String getDescriptionAsSmiley(Double imc) {
		for (ImcRefs imcRef : ImcRefs.values()) {
			if (imc >= imcRef.min && imc < imcRef.max) {
				switch (imcRef) {
				case DENUTRITION_0:
					return ":-S CRITIQUE !";
				case MAIGREUR_1:
					return ":-(";
				case NORMAL_2:
					return "Vous êtes en forme :-)";
				case SURPOIDS_3:
					return ":-(";
				case MODEREE_4:
					return ":-S ATTENTION !";
				case SEVERE_5:
					return ":-S SEVERE !!!";
				case MASSIVE_6:
					return ":-( CRITIQUE !!!";
				default:
					throw new RuntimeException("IMC incalculable !");
				}
			}
		}
		throw new RuntimeException("IMC incalculable !");
	}

	public static String getDescriptionSurchargeAsSmiley(Double imc, Double surcharge) {
		for (ImcRefs imcRef : ImcRefs.values()) {
			if (imc >= imcRef.min && imc < imcRef.max) {
				switch (imcRef) {
				case DENUTRITION_0:
					return ":-S CRITIQUE !";
				case MAIGREUR_1:
					return ":-(";
				case NORMAL_2:
					return "Vous êtes en forme :-)";
				case SURPOIDS_3:
					return ":-(";
				case MODEREE_4:
					return ":-S ATTENTION !";
				case SEVERE_5:
					return ":-S SEVERE !!!";
				case MASSIVE_6:
					return ":-( CRITIQUE !!!";
				default:
					throw new RuntimeException("IMC incalculable !");
				}
			}
		}
		throw new RuntimeException("IMC incalculable !");
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