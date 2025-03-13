package com.itba.eda;

public class Performance {
    public static final int N = 800000000;
	
    public static void main(String[] args) {
    	Timer t2;
    	try {
    		t2= new Timer();
    		t2.stop();
    	}
    	catch(Exception e) {}
        
        int[] myArray = new int[N];
        int rta;
        
    	   // generate array
        for (int rec = N; rec > 0; rec--)
            myArray[N - rec] = rec;

         
        t2= new Timer();
        rta = AlgoA.max(myArray);
        t2.stop();

        System.out.printf("max Algo A %d. %d ms\n", rta, t2.getElapsedMillis());
        
        // generate array
        for (int rec = N; rec > 0; rec--)
            myArray[N - rec] = rec;
        
        t2= new Timer();
        rta = AlgoB.max(myArray);
        t2.stop();
        System.out.printf("max Algo B %d. %d ms\n", rta, t2.getElapsedMillis());
    }
}