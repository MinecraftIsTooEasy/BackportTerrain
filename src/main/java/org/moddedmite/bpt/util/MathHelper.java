package org.moddedmite.bpt.util;

public class MathHelper {
	public static double parseDoubleWithDefaultAndMax(String p_82713_0_, double p_82713_1_, double p_82713_3_) {
		double d2 = p_82713_1_;
		
		try {
			d2 = Double.parseDouble(p_82713_0_);
		} catch (Throwable throwable) {
		}
		
		if (d2 < p_82713_3_) {
			d2 = p_82713_3_;
		}
		
		return d2;
	}
}
