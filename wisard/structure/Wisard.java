package wisard.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import wisard.utils.Numbers;

/**
 * An implementation of WiSARD
 * 
 * @author Evandro Macedo
 * 
 */
public class Wisard {

	/**
	 * The list of discriminators
	 */
	private Map<String, Discriminator> listDiscriminator;

	/**
	 * The classes that the WiSARD knows
	 */
	private List<Enum> knownClasses;

	public Wisard() {
		knownClasses = new ArrayList<Enum>();
		listDiscriminator = new HashMap<String, Discriminator>();
	}

	/**
	 * Supervised method to teach the WiSARD
	 * 
	 * @param binaryImage
	 * @param clazz
	 */
	public void learn(List<Boolean> binaryImage, Enum clazz, int bits) {

		Discriminator discr;

		if (knownClasses.contains(clazz)) {
			discr = listDiscriminator.get(clazz.name());
		} else {
			discr = new Discriminator(clazz.name(), bits);
			discr.initialize(binaryImage);
		}

		discr.learn(binaryImage);

		if (!knownClasses.contains(clazz)) {
			listDiscriminator.put(clazz.name(), discr);
			knownClasses.add(clazz);
		}
	}

	/**
	 * Method to recognize a pattern
	 * 
	 * @param binaryImage
	 * @return
	 */
	public String recognize(List<Boolean> binaryImage) {

		String clazz = "";
		String result = "#";
		double bestProb = 0.0;
		double tempProb = 0.0;

		for (Discriminator discr : listDiscriminator.values()) {

			tempProb = discr.recognize(binaryImage);
			if (bestProb < tempProb) {
				bestProb = tempProb;
				clazz = discr.getClazz();
			}

		}

		if (!clazz.equals("")) {
			result = String.valueOf(Numbers.valueOf(clazz).ordinal());
		}
		
		// return "The class of this input is " + clazz + " with probability " +
		// bestProb;
		return result + ":" + bestProb;
	}

	/**
	 * Return the number of discriminators that there are in the WiSARD
	 * 
	 * @return
	 */
	public int numberOfDiscriminators() {
		return listDiscriminator.size();
	}

	public Map<String, Discriminator> getListDiscriminator() {
		return listDiscriminator;
	}
	
	
}
