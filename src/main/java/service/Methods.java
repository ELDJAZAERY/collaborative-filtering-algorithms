package service;

import java.util.ArrayList;

import org.springframework.web.multipart.MultipartFile;

import module.Item;
import module.ItemRating;
import module.User;

/**
 *
 * @author EL DJAZAERY AZZOUZ TEAM
 */
public class Methods {

	public static Double seuil_actuel = 0.0 ;
	public static Integer echant_actuel = 1000;
	public static Double seuil_fix = 0.0 ;
	public static Integer echant_fix = 1000; 
	public static Integer echant_fix_KNN = 900;
	public static ArrayList<Integer> list_echants = new ArrayList<>();
	public static ArrayList<Double>  list_seuils = new ArrayList<>();
	public static ArrayList<Double> weight = new ArrayList<>();
	public static ArrayList<Integer> listk=new ArrayList<>();
	public static ArrayList<Integer> listCriteres=new ArrayList<>();

	public static int nb_partitions=2;
	public static int nb_voisins=2;
	
	static{
		list_echants.add(0);
		list_echants.add(1000);
		list_seuils.add(0.0);
		list_seuils.add(0.5);
		weight.add((double) 1);
		weight.add((double) 1);
		weight.add((double) 1);
		weight.add((double) 1);
	}
	
	public static String getExtention(MultipartFile file){
        String[] fileFrags = file.getOriginalFilename().toLowerCase().split("\\.");
        return fileFrags[fileFrags.length-1];
	}
	public static String getNom(MultipartFile file){
        String[] fileFrags = file.getOriginalFilename().toLowerCase().split("\\.");
        return fileFrags[0];
	}
	
	public static boolean extentionsValide(MultipartFile[] files){
		for(int i=0;i<files.length;i++){
			switch (getExtention(files[i])) {
			case "csv": break; case "xls": break; case "xlsx": break;
			default:
				return false;
			}
		}
		return true;
	}
	
	public static boolean nomsValide(MultipartFile[] files){
		for(int i=0;i<files.length;i++){
			if( !getNom(files[i]).contains("ser") &&
			    !getNom(files[i]).contains("tem") &&   
			    !getNom(files[i]).contains("ating") ) return false; 
		}
		return true;
	}


	
	/*************************************************************************************************************************/
	/** Mutli-creteria **/
	
	public static double mc_Cosine(ArrayList<ItemRating> ar1, ArrayList<ItemRating> ar2) {
		double module;
		double sum = 0;
		int prod = 0, som1 = 0, som2 = 0;
		for (int j = 1; j <= 4; j++) {
			for (int i = 0; i < ar1.size(); i++) {
				prod += (ar1.get(i).getCritereI(j) * ar2.get(i).getCritereI(j));
				som1 += (Math.pow(ar1.get(i).getCritereI(j), 2));
				som2 += (Math.pow(ar2.get(i).getCritereI(j), 2));
			}
			module = (Math.sqrt(som1) * Math.sqrt(som2));
			sum = sum + weight.get(j) * (prod / module);
		}
		return (double) (sum / 4);
	}

	public static double mc_Person(ArrayList<ItemRating> ar1, ArrayList<ItemRating> ar2, ArrayList<Double> m1,ArrayList<Double> m2) {
		double sum = 0;
		double som1 = 0, som2 = 0, som1_care = 0, som2_care = 0;
		double numerateur = 0, denominateur = 0;
		double prod = 0;
		for (int j = 1; j < 5; j++) {
			int i = 0;
			for (ItemRating it : ar1) {
				// numerateur
				som1 = it.getCritereI(j) - m1.get(j - 1);
				som2 = ar2.get(i).getCritereI(j) - m2.get(j - 1);
				numerateur += som1 * som2;

				// denominateur
				som1_care += (Math.pow(som1, 2));
				som2_care += (Math.pow(som2, 2));
				prod += som1_care * som2_care;
				i++;
			}

			// eviter de faire sqrt(-nbr)
			denominateur = (Math.sqrt(Math.abs(prod)));
			if (denominateur != 0)
				sum = sum + weight.get(j - 1) * (numerateur / denominateur);
		}
		return (double) (sum / 4);
	}

	// Method COS
	public static double cos(ArrayList<ItemRating> ar1, ArrayList<ItemRating> ar2) {
		double module;
		int prod = 0, som1 = 0, som2 = 0;

		for (int i = 0; i < ar1.size(); i++) {
			prod += (ar1.get(i).getRating() * ar2.get(i).getRating());
			som1 += (Math.pow(ar1.get(i).getRating(), 2));
			som2 += (Math.pow(ar2.get(i).getRating(), 2));
		}

		module = (Math.sqrt(som1) * Math.sqrt(som2));

		return prod / module;

	}

	// Method Person
	public static double person(ArrayList<ItemRating> ar1, ArrayList<ItemRating> ar2, double m1, double m2) {

		double som1 = 0, som2 = 0, som1_care = 0, som2_care = 0;
		double numerateur = 0, denominateur = 0;

		int i = 0;
		for (ItemRating it : ar1) {
			// numerateur
			som1 = it.getRating() - m1;
			som2 = ar2.get(i).getRating() - m2;
			numerateur += som1 * som2;

			// denominateur
			som1_care += (Math.pow(som1, 2));
			som2_care += (Math.pow(som2, 2));
			i++;
		}

		denominateur=Math.sqrt(som1_care) * Math.sqrt(som2_care) ;

		if (denominateur == 0)
			return 0;
		return (numerateur / denominateur);
	}

	/** Item-Based Algorithms Methods **/

	// precision for user-based algorithms
	public static double precision(ArrayList<User> user) {
		int j = 0;
		int i = 0;
		for (User uj : user) {
			for (ItemRating eva : uj.getEvaluationPredite2()) {
				if (eva.getRating() >= 3)
					i++;

				j++;
			}
		}
		return (double) i / j;
	}

	// Recall for user-based algorithms
	public static double Rappel(ArrayList<User> user) {
		int j = 0;
		int i = 0;
		for (User uj : user) {
			for (ItemRating eva : uj.getEvaluationPredite2()) {
				if (eva.getRating() >= 3)
					i++;
				// else if( uj.getVote(eva) >=3 ) j++;

			}
			for (ItemRating eva : uj.getEvaluationPredite())
				if (eva.getRating() >= 3 && uj.getEvaluationPredite2().contains(eva))
					j++;

		}

		return (double) i / (j + i);
	}

	// F_Mesure for user-based algorithms
	public static double F_Mesure(ArrayList<User> user) {
		double p = precision(user);
		double r = Rappel(user);
		return ((2 * p * r) / (p + r));
	}

	public static double F_Mesure(double r, double p) {
		return ((2 * p * r) / (p + r));
	}

	// MAE for user-based algorithms
	public static double MAE(ArrayList<User> user) {
		double S = 0;
		int j = 0;
		for (User uj : user) {
			for (ItemRating eva : uj.getEvaluationPredite2()) {
				double dif = (float) uj.getVote(eva) - (float) eva.getRating();
				S = S + Math.abs(dif);
				j++;
			}
		}
		return S/j;
	}

	// accuracy for user-based algorithms
	public static double accuray(ArrayList<User> user) {
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		for (User uj : user) {
			for (ItemRating eva : uj.getEvaluationPredite2()) {
				if (eva.getRating() >= 3)
					a++;
				if (eva.getRating() <= 3)
					b++;
			}

			for (ItemRating eva : uj.getEvaluationPredite()) {
				if (eva.getRating() >= 3)
					c++;
				if (eva.getRating() <= 3)
					d++;
			}
		}
		return ((double) (a + d) / (a + b + c + d));
	}

	// Coverage for User-based algorithms
	public static double coverage(ArrayList<User> users) {
		int a = 0;
		int b = 0;
		for (User u : users) {
			for (ItemRating eva : u.getEvaluationPredite2())
				if (eva.getRating() >= 3)
					a++;

			b = b + u.getEvaluationPredite().size();
		}
		return (double) a/b;

	}

	/** Item-Based Algorithms Methods **/

	// Recall for item-based algorithms
	public static double Rappel_I_I(ArrayList<Item> items) {
		int j = 0;
		int i = 0;
		for (Item uj : items) {
			for (ItemRating eva : uj.getEvaluationPredite2()) {
				if (eva.getRating() >= 3)
					i++;

			}

			for (ItemRating eva : uj.getEvaluationPredite()) {
				if (eva.getRating() >= 3)
					j++;
			}
		}

		return (double)i/(i+j);
	}

	// Precision for item-based algorithms
	public static double precision_I_I(ArrayList<Item> items) {
		int j = 0;
		int i = 0;
		for (Item uj : items) {
			for (ItemRating eva : uj.getEvaluationPredite2()) {
				if (eva.getRating() >= 3)
					i++;

				j++;
			}
		}
		return (double) i / j;

	}

	// MAE for item-based algorithms
	public static double MAE_I_I(ArrayList<Item> items) {
		double S = 0;
		int j = 0;
		for (Item uj : items) {
			for (ItemRating eva : uj.getEvaluationPredite2()) {
				S = S + Math.abs(uj.getVote(eva) - eva.getRating());
				j++;
			}
		}

		return (double) S/j;
	}

	// accuracy for item-based algorithms
	public static double accuray_I_I(ArrayList<Item> items) {
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		for (Item uj : items) {
			for (ItemRating eva : uj.getEvaluationPredite2()) {
				if (eva.getRating() >= 3)
					a++;
				if (eva.getRating() < 3)
					b++;
			}

			for (ItemRating eva : uj.getEvaluationPredite()) {
				if (eva.getRating() >= 3)
					c++;
				if (eva.getRating() < 3)
					d++;
			}
		}

		return (double) (a + d) / (a + b + c + d);
	}

	// Coverage for Item-based algorithms
	public static double coverage_I_I(ArrayList<Item> items) {
		int a = 0;
		int b = 0;
		for (Item it : items) {

			// if(eva.getRating() >=3) a++;
			a = a + it.getEvaluationPredite2().size();
			b = b + it.getEvaluationPredite().size();

		}
		return (double) a / b;

	}
}
