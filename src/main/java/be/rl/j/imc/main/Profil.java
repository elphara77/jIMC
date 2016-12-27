package be.rl.j.imc.main;

import be.rl.j.imc.utils.ImcOperatorUtils;
import be.rl.j.imc.utils.ImcRef;

public class Profil {

	// Dédicace à mon ami Jamal Melhaoui (triple Champion
	// Boxe/Full-Contact/KickBoxing @ Morocco) 1997

	private String name = "Vous";

	private Double weight = 0.;
	private Double targetWeight = 0.;
	private Double overload = 0.;
	private Double height = 0.;
	private Double imc = 0.;
	private Double targetImc = 0.;

	public void calculate() {
		calculateImc();
		calculateOverload();
		calculateTargetImc();
	}

	private void calculateImc() {
		if (weight > 0. && height > 0.) {
			imc = ImcOperatorUtils.imcOperator.applyAsDouble(weight, height);
		} else {
			throw new RuntimeException("Erreur : c'est impossible suivant votre profil de calculer votre IMC !");
		}
	}

	private void calculateTargetImc() {
		if (targetWeight > 0.) {
			targetImc = ImcOperatorUtils.imcOperator.applyAsDouble(targetWeight, height);
		} else {
			throw new RuntimeException("Erreur : c'est impossible suivant votre profil de calculer votre IMC ciblé !");
		}
	}

	private void calculateOverload() {
		// overload =
		// ImcOperatorUtils.overloadFunction.apply(imc).apply(weight).apply(height);
		overload = ImcOperatorUtils.getOverload(weight, ImcRef.getImcRefs(imc), height);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getImc() {
		return imc;
	}

	public Double getTargetWeight() {
		return targetWeight;
	}

	public void setTargetWeight(Double targetWeight) {
		this.targetWeight = targetWeight;
	}

	public Double getTargetImc() {
		return targetImc;
	}

	public Double getOverload() {
		return overload;
	}
}