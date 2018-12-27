package test;

public class Test {
	public static void main(String[] args) {
		int n=5;
		String arr1="9,20,28,18,11";
		String arr2="30,1,21,17,28";
		String[] arr1Split = arr1.split(",");
		String[] arr2Split = arr2.split(",");
		
		for (int i=0 ;i<arr1Split.length;i++ ) {
			int arr1Num= Integer.parseInt(arr1Split[i]);
			int arr2Num= Integer.parseInt(arr2Split[i]);
			int arrRes= arr1Num| arr2Num;
			String decode = Integer.toBinaryString(arrRes);
			decode=decode.replaceAll("1", "#");
			decode=decode.replaceAll("0", " ");
			
			int end= n-decode.length();
			for (int j=0 ; j<end;j++) {
				decode=" "+decode;
			}
			System.out.println(decode);			
		}
	}
}
