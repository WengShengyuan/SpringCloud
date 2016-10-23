package org.wsy.httploader;

import java.util.Date;
import java.util.Random;

import org.omg.CORBA.PRIVATE_MEMBER;

public class app {
	
	public static void main(String[] args) {
		
		
		
		while(true){
			try {
				new Thread(new Runnable() {
					
					public void run() {
						String[] flags = new String[]{"plus", "minus", "times", "div"};
						try {
							System.out.println("TIME:"+new Date()+" -> "+HTTP.get("http://localhost:8080/calgate/cal?a="+new Random().nextDouble()+"&b="+new Random().nextDouble()+"&calFlag="+flags[new Random().nextInt(4)]));
						} catch (Exception e) {
							e.printStackTrace();
						}	
					}
				}).start();
				Thread.sleep(3);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
