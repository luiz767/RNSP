package wisard.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The class that represents a Discriminator
 * @author Evandro Macedo
 *
 */
public class Discriminator {

	/**
	 * The amount of RAMs
	 */
	private int numRAMs;
	
	/**
	 * The amount of a RAM's bits
	 */
	private int bits;
	
	private int bleaching;

	/**
	 * The list of RAMs
	 */
	private List<RAM> listRAM;
	
	/**
	 * The active RAMs
	 */
	private List<Integer> activeRAMs;
		
	/**
	 * The recognition result
	 */
	private double result;
	
	/**
	 * The class that this Discriminator represents
	 */
	private String clazz;
		
	private Random randomGenerator;
	
	
	public Discriminator(String clazz, int bits) {
		this.listRAM = new ArrayList<RAM>();
		this.activeRAMs = new ArrayList<Integer>();
		this.randomGenerator = new Random();
		this.result = 0.0;
		this.clazz = clazz;
		this.bits = bits;
		this.bleaching = 0;
	}
	
	/**
	 * Initialize the RAMs and binaryImage
	 * @param binaryImage
	 */
	public void initialize(List<Boolean> binaryImage){
				
		int totalSize = binaryImage.size();
		int random, temp;
		RAM ram;
		List<Integer> pixelsMapped = new ArrayList<Integer>();
		List<Integer> tempPixelsMapped = new ArrayList<Integer>();				
		
		for (int i = 0; i < totalSize; i++) {
			pixelsMapped.add(i, new Integer(i));
		}
		
		//hashing the list
		for (int i = 0; i < totalSize; i++) {
			random = randomGenerator.nextInt(totalSize);
			temp = pixelsMapped.get(i);
			pixelsMapped.set(i, pixelsMapped.get(random));
			pixelsMapped.set(random, temp);
		}
		
		for (int i = 0; i < totalSize; i++) {
			
			if (i % bits == 0 && i != 0) {
				ram = new RAM(bits);
				ram.setPixelsMapped(tempPixelsMapped);
				tempPixelsMapped = new ArrayList<Integer>();
				listRAM.add(ram);	
			}
			
			tempPixelsMapped.add(pixelsMapped.get(i));
		}
	}

	public void learn(List<Boolean> binaryImage) {
		
		StringBuffer stringPosition = new StringBuffer();
		Integer position;
		
		for (RAM ram : listRAM) {
			
			//mounting the address (position)
			for (Integer pixel : ram.getPixelsMapped()){		
				stringPosition.append(binaryImage.get(pixel) ? 1 : 0);					
			}
			
			position = Integer.parseInt(stringPosition.toString(), 2);
			
			ram.write(position, ram.read(position) + 1);
			
			stringPosition = new StringBuffer();
		}	
	}

	public double recognize(List<Boolean> binaryImage) {

		StringBuffer stringPosition = new StringBuffer();
		Integer position;		
		activeRAMs = new ArrayList<Integer>(numRAMs);

		for (RAM ram : listRAM) {
			
			//mounting the address (position)
			for (Integer pixel : ram.getPixelsMapped()){				
				stringPosition.append(binaryImage.get(pixel) ? 1 : 0);
			}
			
			position = Integer.parseInt(stringPosition.toString(), 2);
			
			if(ram.read(position) > bleaching) {
				activeRAMs.add(1);
			}
			stringPosition = new StringBuffer();
		}
		return computeResult();
	}

	private double computeResult() {
		result = (double) activeRAMs.size() / (double) listRAM.size();
		return result;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	
	public int numberOfRAMs() {
		return listRAM.size();
	}

}
