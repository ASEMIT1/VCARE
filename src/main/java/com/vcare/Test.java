package com.vcare;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int [] ar = {2,3,4,6,3,5,7,8,9,11,7,6,5,3};
		
		boolean decreasing=false,increasing = false;
		int i_start =0, i_end = 0,start=0;
		int max_len = 0;
		for (int i = i_start; i < ar.length-1; i++) {
			if(!decreasing && ar[i]<ar[i+1]) {
				increasing = true;
				i_end=i;
			}else if(increasing && ar[i]>ar[i+1]) {
				decreasing = true;
				i_end=i;
			}else if((decreasing && (ar[i]<ar[i+1]))) {
				System.out.println(":::::::::::"+(i_end-start+2));
				i_start= i_end;
				start = i_end;
				increasing=decreasing=false;
				
			}
			if(i==ar.length-2){
				
				increasing=decreasing=false;
				System.out.println("::::::::888:::"+(i_end-start+1));
			}
					
			
			
		}

	}

}
