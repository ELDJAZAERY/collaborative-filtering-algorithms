package util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

/********************************************
***** @author EL DJAZAERY AZZOUZ TEAM	*****
*********************************************/

public class utilHibernate {
	
   private static final SessionFactory  factory ;
   
	static {
			factory = new AnnotationConfiguration().configure().buildSessionFactory();			
			System.out.println("\n\n\n\n\n Session Factory prete !!! \n\n\n\n\n");
		}

	public static final Session getSession(){
			return factory.openSession();			
	}
	
	public static Configuration getConfig(){
		Configuration   conf = new AnnotationConfiguration().configure();
		return conf;
	}
	
	public static Session getSessionCloned(Configuration conf){
		SessionFactory  factoryCloned = conf.buildSessionFactory();
		return factoryCloned.openSession();			
	}

}
