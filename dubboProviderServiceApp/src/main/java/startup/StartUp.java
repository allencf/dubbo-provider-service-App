package startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartUp {
	
	private final static Logger logger = LoggerFactory.getLogger(StartUp.class);
	
	public static void start(){
		//启动服务
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
		context.start();
		System.out.println("service startup successs ……");
		logger.info("dubboProviderServiceApp start success !!!");
		
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				if(context != null){
					context.stop();
					context.close();
					logger.info("dubboProviderServiceApp is stop !!!");
				}
				synchronized (StartUp.class) {
					StartUp.class.notify();
				}
			}
		});
			
		synchronized (StartUp.class) {
			try {
				StartUp.class.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//while(true);
	}
	
	
	public static void main(String[] args) {
		start();
	}

}
