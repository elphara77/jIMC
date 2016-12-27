package be.rl.j.imc.utils;

import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author rl
 */
public final class ImcOperatorUtils {

	public static DoubleBinaryOperator imcOperator = (weight, height) -> weight / (height * height);

	public static DoubleBinaryOperator idealWeightOperator = (idealImc, height) -> idealImc * height * height;

	public static Function<Double, Function<Double, UnaryOperator<Double>>> overloadFunction = imc -> weight -> height -> weight
			- (imc * height * height);

	public static Double getOverload(Double actualWeight, ImcRef imcRef, Double ownHeight) {
		return actualWeight - (imcRef.getMin() * ownHeight * ownHeight);
	}

	public static void main(String[] args) {
		Double surcharge = ImcOperatorUtils.overloadFunction.apply(25.).apply(90.).apply(1.91);
		System.out.println(surcharge);
	}
}