package wisard.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a class that represents a RAM
 * @author Evandro Macedo
 *
 */
public class RAM {

	/**
	 * The content of the RAM
	 */
	private List<Integer> ram;
	
	/**
	 * The amount of bits
	 */
	private int bits;
	
	/**
	 * The size of the RAM
	 */
	private int size;

	/**
	 * The pixels that are mapped by this RAM
	 */
	private List<Integer> pixelsMapped;

	public RAM() {
		this.ram = new ArrayList<Integer>();
		this.pixelsMapped = new ArrayList<Integer>();
	}

	public RAM(Integer bits) {
		this.bits = bits;	
		this.size = (int) Math.pow(2, bits);
		this.ram = new ArrayList<Integer>(size);
		this.pixelsMapped = new ArrayList<Integer>();
		initialize();
	}
	
	private void initialize() {
		for (int i = 0; i < size; i++) {
			ram.add(i, 0);
		}
	}

	/**
	 * Write the value in a specific position (or address)
	 * @param position the position
	 * @param value the value
	 */
	public void write(Integer position, Integer value) {
		ram.add(position, value);
	}

	/**
	 * Read the content of the specific position
	 * @param position
	 * @return the content
	 */
	public Integer read(Integer position) {
		
		//System.out.println(ram);
		return ram.get(position);
	}

	public int getBits() {
		return bits;
	}

	public void setBits(int bits) {
		this.bits = bits;
	}

	public List<Integer> getRam() {
		return ram;
	}

	public void setRam(List<Integer> ram) {
		this.ram = ram;
	}

	public List<Integer> getPixelsMapped() {
		
		Collections.sort(pixelsMapped);
		
		return pixelsMapped;
	}

	public void setPixelsMapped(List<Integer> pixelsMapped) {
		this.pixelsMapped = pixelsMapped;
	}

}
