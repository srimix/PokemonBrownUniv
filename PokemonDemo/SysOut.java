package PokemonDemo;

public class SysOut {

	private static long lastCall = 0;
	private static Thread t;
	
	public static void print(final String s){
//		if(t != null){
//			try {
//				t.join();
//			} catch (InterruptedException e) {}
		
//		t = new Thread(){
//			public void run(){
//				long currTime = System.currentTimeMillis();
//				if(lastCall == 0) lastCall = currTime;
//				System.out.println("(TIME: " + (currTime-lastCall) + "): " +s);
//				lastCall = currTime;
//			}
//		};
//		t.start();
	}
	
	public static void print(){
		print("");
	}
}
