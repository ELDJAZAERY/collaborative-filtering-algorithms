package module;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import service.Methods;
import util.utilHibernate;

/********************************************
***** @author EL DJAZAERY AZZOUZ TEAM	*****
*********************************************/


@Repository //Data Access Object
public class Data_Implement implements Data_DAO {
	
	boolean movieLens=true;
	
	Session session ;
	public void openeSession(){
		session = utilHibernate.getSession();
	}
	
	private void beginTransaction(){
		if(movieLens){
			openeSession();			
		}else{
			prepearSession();
		}
		session.beginTransaction();
	}
	
	private void commitTransaction(){
		session.getTransaction().commit();
	}
	

	//select user_id from user;
	public ArrayList<Integer> get_ids_Users() {
		beginTransaction();
			Query query = session.createQuery("select user_Id from User");
			ArrayList<Integer> users_ids = (ArrayList<Integer>) query.list();
		commitTransaction();	
		return users_ids;
	}
	
	
	 // "select item_Id from Item"
	public ArrayList<Integer> get_ids_Items(){
		beginTransaction();
			Query query = session.createQuery("select item_Id from Item");
			ArrayList<Integer> item_ids = (ArrayList<Integer>) query.list();
		commitTransaction();
		return item_ids;
	}
	
	
	public ArrayList<Item> getItems(){
		ArrayList<Integer> items_ids = get_ids_Items();
		Collections.shuffle(items_ids);

		ArrayList<Item> its = new ArrayList<Item>();
		
	 beginTransaction();
	 //Pour optimizer l'espace memoir 
	 session.createCriteria(User.class).setFetchMode("evaluation",org.hibernate.FetchMode.LAZY);

		for(Integer id : items_ids){
			Item it= (Item) session.get(Item.class, id);
			Random rand= new Random();
			Random rand2= new Random();
			int k=(int)it.getEvaluation().size() * 20/100;
				for(int i=0;i<k;i++){
					rand.setSeed(rand2.nextInt(it.getEvaluation().size()-1));
					int r=rand.nextInt(it.getEvaluation().size()-1);
					Collections.shuffle(it.getEvaluation());
					it.getEvaluationPredite().add(it.getEvaluation().get(r));
					it.getEvaluation().remove(r);
				}
				its.add(it);
		}
     commitTransaction();	
        
       return its;
	}

	
	public ArrayList<ArrayList<Item>> getItems80_20() {

		ArrayList<ArrayList<Item>> resultat = new ArrayList<>();
		
		ArrayList<Item> its = getItems();
		
		ArrayList<Item> its_80 = new ArrayList<>();
		ArrayList<Item> its_20 = new ArrayList<>();

		int i;
		for (i = 0; i < (its.size() * 80 / 100); i++) {
			its_80.add(its.get(i));
		}
		resultat.add(its_80);
		
		// K=i 80% --> 100% = 20%
		for (int K = i; K < its.size(); K++) {
			its_20.add(its.get(K));
		}
		resultat.add(its_20);

		return resultat;
	}
	
	
	public ArrayList<User> getUsers(){
		ArrayList<Integer> users_ids = get_ids_Users();
		Collections.shuffle(users_ids);

		ArrayList<User> users = new ArrayList<User>();
		
	  beginTransaction();
	  //Pour optimizer l'espace memoir 
	  session.createCriteria(Item.class).setFetchMode("evaluation",org.hibernate.FetchMode.LAZY);
	  Collections.shuffle(users_ids);
		for(Integer id : users_ids){
			User it=(User) session.get(User.class, id);
			Random rand= new Random();
				int k=(int)it.getEvaluation().size() * 20/100;
			for(int i=0;i<k;i++){
				int r=rand.nextInt(it.getEvaluation().size()-1);
				Collections.shuffle(it.getEvaluation());
				it.getEvaluationPredite().add(it.getEvaluation().get(r));
				it.getEvaluation().remove(r);
				
			}
		    users.add(it);
		
		}
	  commitTransaction();
	 Collections.shuffle(users);
		return users;
	}
	

	public ArrayList<ArrayList<User>> getUsers80_20() {

		ArrayList<ArrayList<User>> resultat = new ArrayList<>();
		
		ArrayList<User> users = getUsers();
		
		ArrayList<User> users_80 = new ArrayList<>();
		ArrayList<User> users_20 = new ArrayList<>();

		int i;
		for (i = 0; i < (users.size() * 80 / 100); i++) {
			users_80.add(users.get(i));
		}
		resultat.add(users_80);
		
		// K=i 80% --> 100% = 20%
		for (int K = i; K < users.size(); K++) {
			users_20.add(users.get(K));
		}
		resultat.add(users_20);

		return resultat;
	}
	
	
	public User getUser(int id) {
		beginTransaction();
			User user = (User) session.get(User.class, id);
		commitTransaction();
		return user;
	}
	
	
	public Item getItem(int id) {
		beginTransaction();
			Item item = (Item) session.get(Item.class, id);
		commitTransaction();
		return item;
	}
	
	public HashMap<Integer,HashMap<Integer,ItemRating>> getItemRating() {
		double temp =System.currentTimeMillis()/1000;
		HashMap<Integer,HashMap<Integer,ItemRating>> itR=new HashMap<>();
		beginTransaction();
		List<ItemRating> list =  session.createCriteria(ItemRating.class).list();
		commitTransaction();
		for (ItemRating itemRating : list) {
			if(!(itR.containsKey(itemRating.getUserId())))
			{	HashMap<Integer,ItemRating > p=new HashMap<>();
				p.put(itemRating.getItemId(),itemRating);
				itR.put(itemRating.getUserId(),p);}
			else{
				itR.get(itemRating.getUserId()).put(itemRating.getItemId(),itemRating);
			}
			
		}
		System.out.println(System.currentTimeMillis()/1000 - temp);
		return itR;
	}
	
	public Info getInfos() {
		beginTransaction();
			Info infos = (Info) session.get(Info.class ,1);
		commitTransaction();
		return infos;
	}
	
	
	/**
	 * 
	 * */
	
	/****** Traitement Base de données Parametrique ***********/ 
	
	
	String database = "user";
	Session sessionCloned = null ;
	
	private void creatData(){
		 beginTransaction();
		 
		    String dropDataBase  = "DROP DATABASE "+database ;
		    String creatDataBase = "CREATE DATABASE "+database;
		    
		    String creatTableInfo = "CREATE TABLE "+database+".info ("
				    + " id INT(11) NOT NULL,"
				    + " nb_items INT(11) NOT NULL,"
				    + " nb_ratings INT(11) NOT NULL,"
				    + " nb_users INT(11) NOT NULL," 
				    + " PRIMARY KEY (id));";			
		 
			String creatTableUser = "CREATE TABLE "+database+".user ("
				    + " user_id INT(11) NOT NULL,"
				    + " PRIMARY KEY (user_id));";
			
			String creatTableItem = "CREATE TABLE "+database+".item ("
				    + " item_id INT(11) NOT NULL,"
				    + " PRIMARY KEY (item_id));";		
			
			String creatTableRating = "CREATE TABLE "+database+".item_rating ("
				    + " item_id INT(11) NOT NULL,"
				    + " rating INT(11) NOT NULL,"
				    + " user_id INT(11) NOT NULL,"
				    
				    + " c1 INT(11) ,"
				    + " c2 INT(11) ,"
				    + " c3 INT(11) ,"
				    + " c4 INT(11) ,"
				    
				    + " PRIMARY KEY (item_id,user_id));";	
			
			
			try{
				 Query query1  = session.createSQLQuery(creatDataBase)   ; query1.executeUpdate();
			}catch(Exception e){
				 Query query0  = session.createSQLQuery(dropDataBase)    ; query0.executeUpdate();
				 Query query1  = session.createSQLQuery(creatDataBase)   ; query1.executeUpdate(); 
			}
				 Query query2  = session.createSQLQuery(creatTableInfo)  ; query2.executeUpdate();
				 Query query3  = session.createSQLQuery(creatTableItem)  ; query3.executeUpdate();
				 Query query4  = session.createSQLQuery(creatTableUser)  ; query4.executeUpdate();
				 Query query5  = session.createSQLQuery(creatTableRating); query5.executeUpdate();
		 
	  commitTransaction();
	}
	
	private void prepearSession(){
		session = utilHibernate.getSessionCloned(utilHibernate.getConfig().setProperty
				("hibernate.connection.url","jdbc:mysql://localhost:3306/"+database));
	}
	
	public void charger(MultipartFile[] files,String dataName){
	//  database=dataName;
	  creatData();
			for(int i=0;i<files.length;i++){
				if( files[i].getOriginalFilename().contains("ser") )  chargerTables(files[i],"user");
				if( files[i].getOriginalFilename().contains("tem"))   chargerTables(files[i],"item");
				if( files[i].getOriginalFilename().contains("ating")) chargerTables(files[i],"rating");
	 		}
	  prepearSession();
	  movieLens=false;
	}
	
	private void chargerTables(MultipartFile file,String table){
		if(file!=null && Methods.getExtention(file).equalsIgnoreCase("xls")){
			Excel2003(file,table);			
		}
		if(file!=null && Methods.getExtention(file).equalsIgnoreCase("xlsx")){
			Excel2007(file,table);			
		}		
		if(file!=null && Methods.getExtention(file).equalsIgnoreCase("csv")){
			CSV(file,table);			
		}		
	}
	
	private void Excel2003(MultipartFile file,String table){
	  beginTransaction();	
		 try {
			int i = 0;
			HSSFWorkbook workbook = new HSSFWorkbook(file.getInputStream());
			HSSFSheet worksheet = workbook.getSheetAt(0);
			while (i <= worksheet.getLastRowNum()) {
				HSSFRow row = worksheet.getRow(i++);
				if(table.equalsIgnoreCase("rating")){
					int item_id = (int) row.getCell(0).getNumericCellValue();
					int rating  = (int) row.getCell(1).getNumericCellValue();
					int user_id = (int) row.getCell(2).getNumericCellValue();
					
					session.createSQLQuery("INSERT INTO "+database+".item_rating (item_id, rating, user_id) VALUES("+item_id+","+rating+","+user_id+")").executeUpdate();
				}else{
				   int id = (int) row.getCell(0).getNumericCellValue();
				   if(table.equals("user")){
					   session.createSQLQuery("INSERT INTO "+database+".user VALUES("+id+")").executeUpdate();			   
				   }
				   if(table.equals("item")){
					    session.createSQLQuery("INSERT INTO "+database+".item VALUES("+id+")").executeUpdate();										   
				   }
				}
			}			
			workbook.close();
		 } 
		 catch (Exception e) {
		     e.printStackTrace();
		 }finally{
	  commitTransaction();		 
		 }
		
	}	
	
	private void Excel2007(MultipartFile file,String table){
	   beginTransaction();	
		  try {
			int i = 0;
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet worksheet = workbook.getSheetAt(0);
			
			while (i <= worksheet.getLastRowNum()) {
				XSSFRow row = worksheet.getRow(i++);				
				if(table.equalsIgnoreCase("rating")){
					int item_id = (int) row.getCell(0).getNumericCellValue();
					int rating  = (int) row.getCell(1).getNumericCellValue();
					int user_id = (int) row.getCell(2).getNumericCellValue();
					   session.createSQLQuery("INSERT INTO "+database+".item_rating (item_id, rating, user_id) VALUES("+item_id+","+rating+","+user_id+")").executeUpdate();						
				}else{
				   int id = (int) row.getCell(0).getNumericCellValue();
				   if(table.equals("user")){
					   session.createSQLQuery("INSERT INTO "+database+".user VALUES("+id+")").executeUpdate();			   
				   }
				   if(table.equals("item")){
					   session.createSQLQuery("INSERT INTO "+database+".item VALUES("+id+")").executeUpdate();										   
				   }
				}
			}			
			workbook.close();
		 } 
		 catch (Exception e) {
		     e.printStackTrace();
		 }finally{
	  commitTransaction();				 
		 }
	}

	private void CSV(MultipartFile file,String table){
		beginTransaction();	
		  try {
			  BufferedReader br = new BufferedReader(new FileReader(multipartToFile(file)));
			  String line = br.readLine();
			  
			  while(line!=null){
				  String[] b = line.split(",");
				  if(table.equalsIgnoreCase("rating")){
					  int item_id = Integer.parseInt(b[0]);
					  int rating  = Integer.parseInt(b[1]);
					  int user_id = Integer.parseInt(b[2]);
						session.createSQLQuery("INSERT INTO "+database+".item_rating (item_id, rating, user_id) VALUES("+item_id+","+rating+","+user_id+")").executeUpdate();
				  }else{
					  int id = Integer.parseInt(b[0]);
					  if(table.equals("user")){
						  session.createSQLQuery("INSERT INTO "+database+".user VALUES("+id+")").executeUpdate();			   
					  }
					  if(table.equals("item")){
						  session.createSQLQuery("INSERT INTO "+database+".item VALUES("+id+")").executeUpdate();										   
					  }
				  }			             
			  }
			  br.close();
		  }
		  catch (Exception e) {
			  e.printStackTrace();
		  }finally{
			  commitTransaction();				 
		  }	
	}
	
	
	public File multipartToFile(MultipartFile multipart) throws Exception {
	    File convFile = new File( multipart.getOriginalFilename());
	    multipart.transferTo(convFile);
	    return convFile;
	}
	
	@Override
	public void sessionMovieLens() {
		movieLens=true;
	}

	@Override
	public int getNbUsers() {
	  beginTransaction();
		int nbusers = ((Long)session.createQuery("select count(*) from User").uniqueResult()).intValue();
	  commitTransaction();	
		return nbusers;
	}

	@Override
	public int getNbItems() {
	  beginTransaction();
		int nbItems = ((Long)session.createQuery("select count(*) from Item").uniqueResult()).intValue();
   	  commitTransaction();	
		return nbItems;
	}

	@Override
	public int getNbRatings() {
	  beginTransaction();
		int nbusers = ((Long)session.createQuery("select count(*) from ItemRating").uniqueResult()).intValue();
	  commitTransaction();	
		return nbusers;
	}
	
	@Override
	public int getMaxRating(){
	  beginTransaction();
		 int nbusers = ((Long)session.createQuery("select max(rating) from ItemRating").uniqueResult()).intValue();
	   commitTransaction();	
		return nbusers;		
	}
	
	@Override
	public int getMinRating(){
	  beginTransaction();
		int nbusers = ((Long)session.createQuery("select min(rating) from ItemRating").uniqueResult()).intValue();
	  commitTransaction();	
        return nbusers;		
	}
		
}
