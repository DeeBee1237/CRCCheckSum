/**
 * The algorithm for a 3-bit CRC CheckSum
 * 
 * @author Dragos
 *
 */
public class CyclicRedundencyCheck {

	// either one of the binary numbers 0000 or 1011, are used in every division
	// for each step in the CRC algorithm
	private final int [] allZeros = new int [] {0,0,0,0}; 
	private final int [] fourBits = new int [] {1,0,1,1}; // for a 3-bit CRC
	
	/**
	 * Take in a string, representing the data to be sent across the packet
	 * and return an integer array containing every single bit, as well as the three 
	 * extra '0' bits at the end.
	 * 
	 * @param data
	 * @return
	 */
	public int [] convertDataToIntArray (String data) {
		int [] arrayForData = new int [data.length() + 3];

		char [] dataAsCharArray = data.toCharArray();

		for (int i = 0; i < arrayForData.length; i++) {
			if (i >= dataAsCharArray.length)
				arrayForData[i] = 0;
			else{
				int currentBit = Integer.parseInt(String.valueOf(dataAsCharArray[i]));
				arrayForData[i] = currentBit;
			}
		}

		return arrayForData;
	}

	/**
	 * Take in an integer array representing the final remainder of the CRC 
	 * calculation, and return a string representation of that binary number 
	 * as the final result of the checksum:
	 * 
	 * @param binaryNumber
	 * @return
	 */
	public String convertArrayBackToData (int [] binaryNumber) {
		String result = "";

		// the final binary string returned must be 3 bits long (one less than the bits 
		// in the divisor)
		for (int i = 1; i < binaryNumber.length; i++) 
			result += binaryNumber[i];

		return result;
	}

	/**
	 * return the left most bit in a dataArray representing a binary value
	 * @param dataArray
	 * @return
	 */
	public int leftMostBit (int [] dataArray) {
		return dataArray[0];
	}

	/**
	 * Perform an XOR binary division on two binary numbers 
	 * (represented with two integer arrays) and return the result
	 * as an integer array. Note: this method will return a new array 
	 * of size 4, but the last index will be blank, because it will 
	 * be filled in later by the next bit that you 'pull' in the 
	 * binary division:
	 * 
	 * @param divisor: the binary number that you're dividing by
	 * @param dividened: the binary number that is being divided
	 * @return
	 */
	public int[] divideBinaryNumbers (int [] divisor, int [] dividened, boolean lastStep) {
		int [] result = new int [4];

		if (!lastStep)
			for (int i = 1; i < divisor.length; i++) 
				result[i-1] = divisor[i] ^ dividened[i];
		else
			for (int i = 1; i < divisor.length; i++) 
				result[i] = divisor[i] ^ dividened[i];

		return result;
	}


	/**
	 * The Algorithm
	 * 
	 * @param data: A binary string, the data to be sent
	 * @return: a binary string that is the checksum to be sent with the data, in the packet
	 */
	public String calculateCRC (String data) {

		// convert the data into an array of integers, with three 0's at the end:
		int [] dataArray = convertDataToIntArray(data);

		// the position of the next bit to pull down in the binary division:
		int positionOfNextBit = 4;

		// perform the binary division to find the remainder:
		int [] divisor;
		int [] dividened = dataArray; // to begin with

		while (positionOfNextBit <= dataArray.length) { 

			// check the left most bit of the binary number you are dividing:
			if (leftMostBit(dividened) == 0) 
				divisor = this.allZeros;
			else 
				divisor = this.fourBits;

			int [] resultOfDivision;

			// in the last division step, there are no bits to pull down, so the division
			// is done slightly differently:
			if (positionOfNextBit == dataArray.length) 
				resultOfDivision = divideBinaryNumbers(divisor,dividened,true);

			else {
				resultOfDivision = divideBinaryNumbers(divisor,dividened,false);
				resultOfDivision[resultOfDivision.length - 1] = dataArray[positionOfNextBit];
			}

			dividened = resultOfDivision;
			positionOfNextBit++;
		}

		// at the end of the algorithm, the final state of the 'dividened' array will 
		// be the remainder, which is the final result!

		return convertArrayBackToData(dividened);
	}




}
