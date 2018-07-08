package controler;


import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ant_Colony_Optimization.ACS;
import service.Data_metier;
import service.Methods;

/********************************************
***** @author EL DJAZAERY AZZOUZ TEAM	*****
*********************************************/

@Controller
public class Controler {
	
	@Autowired
	private Data_metier service;
	public void setData(Data_metier data) {
		this.service = data;
	}
	
	@ModelAttribute
	public void addObjects(Model model){
		ArrayList<Integer> list = new ArrayList<>();   
		
		model.addAttribute("F_mesure",list);  model.addAttribute("F_mesure_per",list);
		model.addAttribute("Rappel",list);    model.addAttribute("Rappel_per",list);
		model.addAttribute("Precision",list); model.addAttribute("Precision_per",list);
		model.addAttribute("MAE",list);       model.addAttribute("MAE_per",list); 
		model.addAttribute("Accuracy",list);  model.addAttribute("Accuracy_per",list);
		model.addAttribute("Coverage",list);  model.addAttribute("Coverage_per",list);
			
		
		model.addAttribute("data",0);
		model.addAttribute("graph",0);
		model.addAttribute("doub",0);
		model.addAttribute("cos",0);
		model.addAttribute("fc_type_based",0);
		model.addAttribute("bar_graph",0);
		
		ArrayList<String> vals = new ArrayList<>();
		vals.add("0"); vals.add("0"); vals.add("0"); vals.add("0");
		model.addAttribute("vals",vals);
		
		// seuils
		ArrayList<Double> seuils = new ArrayList<>();
		seuils.add((double)0); seuils.add(0.5);
		model.addAttribute("L_seuils",seuils);
	
		// echantillonnage
		ArrayList<Integer> echans = new ArrayList<>();
		echans.add(0); echans.add(1000);
		model.addAttribute("L_echantis",echans);

		// KNN
		ArrayList<Integer> L_KNN = new ArrayList<>();
		L_KNN.add(0); L_KNN.add(50);
		model.addAttribute("L_KNN",L_KNN);
		
		// nombre de users
		model.addAttribute("nb_users",service.getNbUsers());
		
		// description message
		String desc = ""; model.addAttribute("descriptiondescription",desc);
	}
	
	
	@RequestMapping(value="/") //@ModelAttribute("list1") String list1
	public String home(Model model){
		return "index";
	}
	
	
	public static String getClientIpAddress(HttpServletRequest request) {
	    String xForwardedForHeader = request.getHeader("X-Forwarded-For");
	    if (xForwardedForHeader == null) {
	        return request.getRemoteAddr();
	    } else {
	        return new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
	    }
	}
	
	
	public static String getClientComputerName(HttpServletRequest request) {
		 String computerName = null;
		 String remoteAddress = request.getRemoteAddr();
		 try {
		        InetAddress inetAddress = InetAddress.getByName(remoteAddress);
		        computerName = inetAddress.getHostName();
		        if (computerName.equalsIgnoreCase("0:0:0:0:0:0:0:1")) {
		            computerName = java.net.InetAddress.getLocalHost().getCanonicalHostName();
		        } 
		}catch (Exception e) {
	    	return "";
       }		
		   return computerName;
	}
	
	
	@RequestMapping(value="/valide")
	public String valide( Model model , HttpServletRequest request ,
						  @ModelAttribute("checkCOS") String checkCOS ,  // COS
						  @ModelAttribute("checkPER") String checkPERS , // PEARSON						  
						  @ModelAttribute("fc_form_type") String fc_form_type , // Seuil - Echantillonnage - KNN
                          @ModelAttribute("fc_type") String fc_type  ,    // User Based - Item Based - slope One
                          @ModelAttribute("List_S") String List_seuil , // List Seuils
                          @ModelAttribute("List_E") String List_Echants , // List d'Echantillonages
                          @ModelAttribute("List_K") String List_KNN , // List d'Echantillonages par le nombre du voisins 
                          @ModelAttribute("List_C") String List_C , // List de criteres 
                          @ModelAttribute("NB_Part") Integer NB_Part , // nombre de partitions
                          @ModelAttribute("NB_Voisins") Integer NB_Voisins , // 
                          @ModelAttribute("nbIter")     Integer nbIter ,
                          @ModelAttribute("pheromone0") double pheromone0 ,
                          @ModelAttribute("alpha") float alpha ,
                          @ModelAttribute("beta") float beta ,
                          @ModelAttribute("q0") float q0 ,
                          @ModelAttribute("p") float p ,
                          @ModelAttribute("page_acctuel") String page_acctuel ,
                          @RequestParam("file") MultipartFile[] files){  
   		
		
		System.out.println("\n\n\n ");  
		System.out.println("----------> Liste de KNN "+List_KNN);
		System.out.println("IP address    ------> "+getClientIpAddress(request));
		System.out.println("computer Name ------>"+getClientComputerName(request));

		synchroniser_seuils_Echants_KNN(model,List_seuil,List_Echants,List_KNN,List_C,NB_Part,NB_Voisins,nbIter,alpha,beta,q0,p,pheromone0);	
		
		//System.out.println(Methods.listk); if(true) return "index";
		
		// echant > nbMax
		if(fc_type.equals("User Based")) {
			int nbMAx = service.getNbUsers(); if (Methods.echant_fix > nbMAx || Methods.echant_fix < 0) Methods.echant_fix=nbMAx;
		}
		if(fc_type.equals("Item Based")) {
			int nbMAx = service.getNbItems(); if (Methods.echant_fix > nbMAx || Methods.echant_fix < 0) Methods.echant_fix=nbMAx;			
		}

		
		if(page_acctuel.equals("fc_mono")){
			return fc_monocritere(model, checkCOS, checkPERS, fc_form_type, fc_type, files, service, request,false);
		}else if(page_acctuel.equals("fc_multi")){
			return fc_monocritere(model, checkCOS, checkPERS, fc_form_type, fc_type, files, service, request,true);
		}
		
		// graph == 3 nb_users
		model.addAttribute("graph",3);
		model.addAttribute("bar_graph",1);
		
		String tr_base = Traitement_Base(files, service, request);
		if(tr_base.equals("index")) return "index";
		
		String desc="";
		
		if(page_acctuel.equals("fc_Kmean")){
			desc+="FC && K-Mean --- avec "+Methods.nb_partitions+" Partitions "+tr_base ;
			model.addAttribute("descriptiondescription",desc);
			
			boolean cos = checkCOS.equalsIgnoreCase("on");
			if(cos)  model.addAttribute("cos",1); 
			
			boolean doble = checkCOS.equalsIgnoreCase("on") && checkPERS.equalsIgnoreCase("on") ;
			
			
			return fc_Kmean(model,cos,doble);
		}
		
		if(page_acctuel.equals("fc_classif_KNN")){
			desc+="FC && K-Mean && K-NN --- avec "+Methods.nb_partitions+" Partitions et "+Methods.nb_voisins+" Voisins "+tr_base ;
			model.addAttribute("descriptiondescription",desc);

			boolean cos = checkCOS.equalsIgnoreCase("on");
			if(cos)  model.addAttribute("cos",1);
				
			boolean doble = checkCOS.equalsIgnoreCase("on") && checkPERS.equalsIgnoreCase("on") ;
			
			return fc_classif_KNN(model,cos,doble);
		}
		
		if(page_acctuel.equals("fc_Ant_Collony")){
			desc+=" --- Collony de Fourmis --- "+tr_base ;
			model.addAttribute("descriptiondescription",desc);
			
			
			return fc_Ant_Collony(model);
		}
		
		return "index";
	}
	
	
  private static String Traitement_Base(MultipartFile[] files , Data_metier service , HttpServletRequest request){
		/** traitment de base de Donnees parametrique 
		 * */
		
	   if(!files[0].getOriginalFilename().isEmpty()){
			
			if(!Methods.extentionsValide(files) || !Methods.nomsValide(files) || files.length!=3){
		         return "index";
			}
			service.charger(files,getClientIpAddress(request));
			return " --- Base de Données de "+getClientComputerName(request) ;
		}else{
			service.sessionMovieLens();
			return " --- Base de Données Movie Lens (100K)" ;
		}
		
		/**
		 * 
		 */
	  
  }
	
   private static String fc_monocritere(Model model ,String checkCOS, String checkPERS,String fc_form_type,String fc_type,
		                                MultipartFile[] files , Data_metier service , HttpServletRequest request , boolean Multi){

	   
		double temps_debut = System.currentTimeMillis() / 1000 ;
        
		String desc = "";
		if(Multi) desc="-- Multi Critères --";

		// Multi critères sans charger une Base
		if( Multi && files[0].getOriginalFilename().isEmpty() ){
			desc = "La Base de Données MovieLens n'est pas Multi-Critères !!! ";
			model.addAttribute("description",desc);		
			return "index";
		}
		
		
		// desc = ' echa --> user / item '
		String echan = "";
		if(fc_type.equals("User Based")) echan="User"; 
		if(fc_type.equals("Item Based")) echan="Item";
		
		
		// seuil
		boolean seuil=false;
		if(fc_form_type.equalsIgnoreCase("Seuil")){
			model.addAttribute("graph",1);
			seuil=true;
			desc ="Variant de seuil avec " +Methods.echant_fix +" "+echan+" --- "+fc_type;
		}else if(fc_form_type.equalsIgnoreCase("Echantillonnage")){
			// convert double to int value
			if(Methods.seuil_fix==0 || Math.abs(Methods.seuil_fix) == 1) {
				desc=" Echantillionage  de seuil "+Methods.seuil_fix.intValue()+" --- "+fc_type;
			}else{
				desc=" Echantillionage  de seuil "+Methods.seuil_fix +" --- "+fc_type;				
			}
		}else{
			// KNN
			model.addAttribute("graph",2);
			desc ="Variant de voisin avec " +Methods.echant_fix +" "+echan+"  --- "+fc_type;		
		}
		
		// slop One description
		if(fc_type.equals("slope One")) {
			desc = " --- Slope One --- ";
			model.addAttribute("bar_graph",1);
			model.addAttribute("graph",3);
		}
		
		// similarity type COS / PERSON Methods 	
		if(!checkCOS.equalsIgnoreCase("on") && !checkPERS.equalsIgnoreCase("on") && !fc_type.equals("slope One")) { return "index"; }
		boolean cos=false; boolean pearson=false;
		if(checkCOS.equalsIgnoreCase("on")) { cos=true; model.addAttribute("cos",1); }
		if(checkPERS.equalsIgnoreCase("on")) pearson=true;
		
		String tr_base = Traitement_Base(files, service, request);
		if( tr_base.equals("index")) return "index";
		
		desc+=tr_base;
    	model.addAttribute("description",desc);		

		fc_formule(model, cos ,pearson, seuil,fc_form_type,fc_type,service,Multi);
		
		System.out.print(" -----------   ");
		System.out.println( ( System.currentTimeMillis() / 1000 )-temps_debut+" sec ---------");	 
		
		return "index";
   }
    
   
	private static void fc_formule(Model model , boolean cos , boolean pearson , boolean seuil ,String fc_form_type ,String fc_type , Data_metier service,boolean Multi){
	 
		HashMap<Integer,ArrayList<Double>> fc_formul;
		
		// FC Type // Items User Slope based 
		if(fc_type.equalsIgnoreCase("Item Based")){
			if(Multi) fc_formul = service.mc_ItemBased(cos, seuil, fc_form_type);
			else fc_formul=service.fcFormule_I_I(cos, seuil, fc_form_type);
			model.addAttribute("fc_type_based",1);
		}
		else if(fc_type.equalsIgnoreCase("User Based")){
			if(Multi) fc_formul = service.mc_UserBased(cos, seuil, fc_form_type);
			else fc_formul = service.fcFormule_U_U(cos , seuil , fc_form_type);			
			model.addAttribute("fc_type_based",0);
		}
		else {
			System.out.println("Slop One");
			fc_formul = service.slopeOne();			
			model.addAttribute("fc_type_based",2);
		}
		
		
		System.out.println("resultat final -->"+fc_formul);
		
		model.addAttribute("Rappel"	  ,fc_formul.get(1));
		model.addAttribute("Precision",fc_formul.get(2));
		model.addAttribute("F_mesure" ,fc_formul.get(3));
		model.addAttribute("MAE"	  ,fc_formul.get(4));  
		model.addAttribute("Accuracy" ,fc_formul.get(5)); 
		model.addAttribute("Coverage" ,fc_formul.get(6));

		
		if( (cos && pearson) ){
			model.addAttribute("doub",1);
			cos = ! cos;
		
			// FC Type // Items User Slope based 
			if(fc_type.equalsIgnoreCase("Item Based"))
				fc_formul=service.fcFormule_I_I(cos, seuil, fc_form_type);
			else if(fc_type.equalsIgnoreCase("User Based"))
				fc_formul = service.fcFormule_U_U(cos , seuil , fc_form_type);
			else 
				fc_formul = service.slopeOne();
				
			
			model.addAttribute("Rappel_per"   ,fc_formul.get(1));
			model.addAttribute("Precision_per",fc_formul.get(2));
			model.addAttribute("F_mesure_per" ,fc_formul.get(3));
			model.addAttribute("MAE_per"      ,fc_formul.get(4));
			model.addAttribute("Accuracy_per" ,fc_formul.get(5)); 
			model.addAttribute("Coverage_per" ,fc_formul.get(6));

			System.out.println("resultat final -->"+fc_formul);
	
		}

		
		// bar graph
		if(fc_formul.get(1).size()==1) model.addAttribute("bar_graph",1);
		
		// seuils
		model.addAttribute("L_seuils",Methods.list_seuils);
		
		// echantillonnage
		model.addAttribute("L_echantis",Methods.list_echants);

		// KNN
		model.addAttribute("L_KNN",Methods.listk);

		// Data is charged !!
		model.addAttribute("data",1);
	}
    
    
	private String fc_Kmean(Model model,boolean cos,boolean doble){
		HashMap<Integer,ArrayList<Double>> fc_formul;
	
		fc_formul = service.kMeans(cos);
		
		model.addAttribute("Rappel"	  ,fc_formul.get(1));
		model.addAttribute("Precision",fc_formul.get(2));
		model.addAttribute("F_mesure" ,fc_formul.get(3));
		model.addAttribute("MAE"	  ,fc_formul.get(4));  
		model.addAttribute("Accuracy" ,fc_formul.get(5)); 
		model.addAttribute("Coverage" ,fc_formul.get(6));

		System.out.println("resultat final fc_Kmean -->"+fc_formul);
		
		if(doble){ cos = !cos;
		   model.addAttribute("doub",1);	
		   
			fc_formul = service.kMeans(cos);
			
			model.addAttribute("Rappel_per"   ,fc_formul.get(1));
			model.addAttribute("Precision_per",fc_formul.get(2));
			model.addAttribute("F_mesure_per" ,fc_formul.get(3));
			model.addAttribute("MAE_per"      ,fc_formul.get(4));
			model.addAttribute("Accuracy_per" ,fc_formul.get(5)); 
			model.addAttribute("Coverage_per" ,fc_formul.get(6));

			System.out.println("resultat final fc_Kmean double-->"+fc_formul);
			
		}
		
		model.addAttribute("data",1);
		return "index";
	}
	
	private String fc_classif_KNN(Model model , boolean cos, boolean doble){
		HashMap<Integer,ArrayList<Double>> fc_formul;
		
		fc_formul = service.KMeans_KNN(Methods.nb_partitions, cos);
		
		model.addAttribute("Rappel"	  ,fc_formul.get(1));
		model.addAttribute("Precision",fc_formul.get(2));
		model.addAttribute("F_mesure" ,fc_formul.get(3));
		model.addAttribute("MAE"	  ,fc_formul.get(4));  
		model.addAttribute("Accuracy" ,fc_formul.get(5)); 
		model.addAttribute("Coverage" ,fc_formul.get(6));
		
		System.out.println("resultat final fc_classif_KNN -->"+fc_formul);

		if(doble){ cos = !cos;
		   model.addAttribute("doub",1);	
		   
		    fc_formul = service.KMeans_KNN(Methods.nb_partitions, cos);
			
			model.addAttribute("Rappel_per"   ,fc_formul.get(1));
			model.addAttribute("Precision_per",fc_formul.get(2));
			model.addAttribute("F_mesure_per" ,fc_formul.get(3));
			model.addAttribute("MAE_per"      ,fc_formul.get(4));
			model.addAttribute("Accuracy_per" ,fc_formul.get(5)); 
			model.addAttribute("Coverage_per" ,fc_formul.get(6));
			
			System.out.println("resultat final fc_classif_KNN double-->"+fc_formul);
		}
		
		
		model.addAttribute("data",1);
		return "index";
	}
	
	private String fc_Ant_Collony(Model model){		
		
		/** calcul de temps */ System.out.println("collony !!!! ----> ");
		double temps_debut = System.currentTimeMillis() / 1000;

		HashMap<Integer,ArrayList<Double>> fc_formul;
		
		fc_formul = service.ACs();
		
		model.addAttribute("Rappel"	  ,fc_formul.get(1));
		model.addAttribute("Precision",fc_formul.get(2));
		model.addAttribute("F_mesure" ,fc_formul.get(3));
		model.addAttribute("MAE"	  ,fc_formul.get(4));  
		model.addAttribute("Accuracy" ,fc_formul.get(5)); 
		model.addAttribute("Coverage" ,fc_formul.get(6));
		
		System.out.println("resultat final ANT COLLONY-->"+fc_formul);

		model.addAttribute("data",1);
		
		System.out.print(" ----------- Calculs Collony !!!  ");
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");
		return "index";
	}
	
	
   private void synchroniser_seuils_Echants_KNN(Model model , String List_seuil , String List_Echants,String List_KNN,String List_C,
		   										Integer NB_Part,Integer NB_Voisins,Integer nbIter,float alpha,float beta,float q0,float p,double pheromone0){
	   
	   /** Seuils  */
	   if(!List_seuil.isEmpty()){
		   String[] seuils = List_seuil.split(",");

		   ArrayList<Double> seuils_vals = new ArrayList<>();

		   Integer echant_fix = Integer.parseInt(seuils[seuils.length-1]);
		   Methods.echant_fix = echant_fix;
		   
		   // vals de seuils  
		   for(int i=0;i<seuils.length-1;i++){
			   if(!seuils[i].isEmpty())
			       seuils_vals.add(Double.parseDouble(seuils[i]));
		   }
		   Collections.sort(seuils_vals);
		   Methods.list_seuils = seuils_vals;
	   }
	   
	   /** Echantillonage  */
	   if(!List_Echants.isEmpty()){
		   String[] Echantils = List_Echants.split(",");
		   
		   ArrayList<Integer> Echants_vals = new ArrayList<>();

		   Double seuil_fix =  Double.parseDouble(Echantils[Echantils.length-1]);
		   Methods.seuil_fix = seuil_fix;
		   
		   
		   // vals de Echants
		   for(int i=0;i<Echantils.length-1;i++){
			   if(!Echantils[i].isEmpty())
			      Echants_vals.add(Integer.parseInt(Echantils[i]));
		   }
		   Collections.sort(Echants_vals);
		   Methods.list_echants = Echants_vals;
	   }
	   
	   /** KNN  */
	   if(!List_KNN.isEmpty()){
		   String[] LK = List_KNN.split(",");
		   
		   ArrayList<Integer> KNN_vals = new ArrayList<>();
		   
		   Integer echant_fix_KNN = Integer.parseInt(LK[LK.length-1]);
		   Methods.echant_fix_KNN = echant_fix_KNN;
	   
		   // vals de KNN
		   for(int i=0;i<LK.length-1;i++){
			   if(!LK[i].isEmpty())
				   KNN_vals.add(Integer.parseInt(LK[i]));
		   }
		   Collections.sort(KNN_vals);
		   Methods.listk = KNN_vals;
	   }
	   
	   
	   // priorité de criteres
	   if(!List_C.isEmpty()){
		   Methods.listCriteres.clear();
		   String[] LC = List_C.split(",");
		   for(int i=0;i<LC.length;i++){
			   Methods.listCriteres.add(Integer.parseInt(LC[i]));
		   }
	   }
	   
	   // nombre de partitions
	   Methods.nb_partitions=NB_Part;
	   
	   // nombre de voisins classification KNN
	   Methods.nb_voisins = NB_Voisins;
	   
	   

	   
	   
   }

    
}
