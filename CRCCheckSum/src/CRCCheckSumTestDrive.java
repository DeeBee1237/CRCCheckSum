
public class CRCCheckSumTestDrive {

	public static void main (String [] args) {
		
		String data = "01111"; // data to be sent in a packet
		
		System.out.println("Data to be sent in packet: " + data);
		
		CyclicRedundencyCheck crcSender = new CyclicRedundencyCheck();
		
		String checkSum = crcSender.calculateCRC(data); // the checksum
		System.out.println("CheckSum : " + checkSum);
		
		String dataSent = data + checkSum; // the data that the sender sends out
		
		System.out.println("Data sent with checkSum: " + dataSent);
		
		CyclicRedundencyCheck crcReceiver = new CyclicRedundencyCheck();
		
		System.out.println("Receivers Calculation: " + crcReceiver.calculateCRC(dataSent));
	}
}
