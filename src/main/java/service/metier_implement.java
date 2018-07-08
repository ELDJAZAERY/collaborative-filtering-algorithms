package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ant_Colony_Optimization.ACS;
import module.Clusters;
import module.Data_DAO;
import module.Info;
import module.Item;
import module.ItemRating;
import module.User;

@Service
public class metier_implement implements Data_metier {

	private Data_DAO data;

	public void setData(Data_DAO data) {
		this.data = data;
	}

	@Override
	public User getUser(int id) {
		return data.getUser(id);
	}

	@Override
	public Info getInfos() {
		return data.getInfos();
	}

	@Override
	public ArrayList<Integer> get_ids_Users() {
		return data.get_ids_Users();
	}

	@Override
	public ArrayList<User> getUsers() {
		return data.getUsers();
	}

	@Override
	public Item getItem(int id) {
		return data.getItem(id);
	}

	@Override
	public HashMap<Integer, HashMap<Integer, ItemRating>> getItemRating() {

		return data.getItemRating();
	}

	@Override
	public ArrayList<Integer> get_ids_Items() {
		return data.get_ids_Items();
	}

	@Override
	public ArrayList<Item> getItems() {
		return data.getItems();
	}

	@Override
	public void charger(MultipartFile[] files, String dataName) {
		data.charger(files, dataName);
	}

	@Override
	public void sessionMovieLens() {
		data.sessionMovieLens();
	}

	@Override
	public int getNbUsers() {
		return data.getNbUsers();
	}

	@Override
	public int getNbItems() {
		return data.getNbItems();
	}

	@Override
	public int getNbRatings() {
		return getNbRatings();
	}

	@Override
	public int getMaxRating() {
		return getMaxRating();
	}

	@Override
	public int getMinRating() {
		return getMinRating();
	}

	/*******
	 * User-based ************* Algorithms Methods
	 *****/

	public ArrayList<User> users_details(boolean COS) {

		double temps_debut = System.currentTimeMillis() / 1000;
		ArrayList<User> users = data.getUsers();

		System.err.print(" ----------- charger les Donnes !!   ");
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");

		temps_debut = System.currentTimeMillis() / 1000;

		// Similarity
		if (COS) {
			for (int i = 0; i < users.size() - 1; i++) {
				for (int j = i + 1; j < users.size(); j++) {
					users.get(i).simUsingCos(users.get(j));
				}
			}
		} else {
			for (int i = 0; i < users.size() - 1; i++) {
				for (int j = i + 1; j < users.size(); j++) {
					users.get(i).simUsingPerson(users.get(j));
				}
			}
		}

		System.out.print(" ----------- Creer Les Communate  ");
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");
		temps_debut = System.currentTimeMillis() / 1000;
		int nb = 0;
		// prediction for all the items
		for (User u : users) {
			for (ItemRating it : u.getEvaluationPredite()) {
				u.prediction(it);
			}
			nb = nb + 1;
		}

		System.out.print(" ----------- Predections  " + nb);
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");

		return users;
	}

	public HashMap<Integer, ArrayList<Double>> fcFormule_U_U(boolean COS, boolean seuil, String fc_form_type) {

		if (seuil) {
			return fcFormule_seuil_U_U(COS, fc_form_type);
		}
		
		if(fc_form_type.equals("KNN")) return approcheKpv_User(COS);
		
		/** /// else Echantillonage */

	
		Methods.seuil_actuel = Methods.seuil_fix;

		ArrayList<User> users_total = users_details(COS);

		/** calcul du temps */
		double temps_debut = System.currentTimeMillis() / 1000;

		// supprimer les echantillonage qui depasse le Nb max ou negatif
		int NbMaxUser = data.getNbUsers();
		for (int i = 0; i < Methods.list_echants.size(); i++) {
			if (Methods.list_echants.get(i) > NbMaxUser || Methods.list_echants.get(i) < 0) {
				Methods.list_echants.remove(i);
				i--;
				if (!Methods.list_echants.contains(NbMaxUser))
					Methods.list_echants.add(NbMaxUser);
			}
		}
		// key 1 pour Rappel 2 pour precision 3 pour F_Mesure 4 pour MAE
		HashMap<Integer, ArrayList<Double>> fc_formule = init_Fc_formule(true);

		for (int i = 1; i < Methods.list_echants.size(); i++) {
			ArrayList<User> users = new ArrayList<>();
			int is = 1;
			for (User u : users_total) {
				users.add(u);
				if (is == Methods.list_echants.get(i)) {
					break;
				}
				is++;
			}

			Methods.echant_actuel = Methods.list_echants.get(i);
			charger_valeur(fc_formule, users);
			users.clear();
		}

		System.err.print(" ----------- Calculs FC_formul  ");
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");

		users_total.clear();
		return fc_formule;
	}

	// used just in fcFormule()
	private HashMap<Integer, ArrayList<Double>> charger_valeur(HashMap<Integer, ArrayList<Double>> fc_formule, ArrayList<User> users) {

		double a = Methods.Rappel(users);
		double b = Methods.precision(users);
		double c = Methods.F_Mesure(a, b);

		fc_formule.get(1).add(a);
		fc_formule.get(2).add(b);
		fc_formule.get(3).add(c);
		fc_formule.get(4).add(Methods.MAE(users));
		fc_formule.get(5).add(Methods.accuray(users));
		fc_formule.get(6).add(Methods.coverage(users));

		return fc_formule;
	}

	public HashMap<Integer, ArrayList<Double>> fcFormule_seuil_U_U(boolean COS, String fc_form_type) {

		// key 1 pour Rapel 2 pour precision 3 pour F_Mesure 4 pour MAE
		HashMap<Integer, ArrayList<Double>> fc_formule = init_Fc_formule(false);

		for (int i = 0; i < Methods.list_seuils.size(); i++) {
			Methods.seuil_actuel = Methods.list_seuils.get(i);

			/******* Echantillonage variante !!! ******/
			ArrayList<User> users_total = users_details(COS);

			ArrayList<User> users = new ArrayList<>();

			// ou cas de depassement de Nb max ou negatif
			int nbMAxUser = data.getNbUsers();
			if (Methods.echant_fix > nbMAxUser || Methods.echant_fix < 0) {
				Methods.echant_fix = nbMAxUser;
				users = users_total;
			} else {
				int is = 1;
				for (User u : users_total) {
					users.add(u);
					if (is == Methods.echant_fix) {
						break;
					}
					is++;
				}
			}
			/******* Echantillonage variante !!! ******/

			charger_valeur(fc_formule, users);
			users.clear();
			users_total.clear();
		}

		return fc_formule;
	}

	/*******
	 * Item-based ************* Algorithms Methods
	 *****/

	public ArrayList<Item> items_details(boolean COS) {
		double temps_debut = System.currentTimeMillis() / 1000;
		ArrayList<Item> items = data.getItems();

		System.err.print(" ----------- charger les Donnes !!   ");
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");

		temps_debut = System.currentTimeMillis() / 1000;

		// Similarity
		if (COS) {
			for (int i = 0; i < items.size() - 1; i++) {
				for (int j = i + 1; j < items.size(); j++) {
					items.get(i).simUsingCos(items.get(j));
				}
			}
		} else {
			for (int i = 0; i < items.size() - 1; i++) {
				for (int j = i + 1; j < items.size(); j++) {
					items.get(i).simUsingPerson(items.get(j));

				}
			}
		}

		System.out.print(" ----------- Creer Les Communate  ");
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");
		temps_debut = System.currentTimeMillis() / 1000;

		// prediction for all the items
		for (Item u : items) {
			for (ItemRating it : u.getEvaluationPredite()) {
				u.prediction(it);
			}
		}

		System.out.print(" ----------- Predections   ");
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");

		return items;
	}

	public HashMap<Integer, ArrayList<Double>> fcFormule_I_I(boolean COS, boolean seuil, String fc_form_type) {

		if (seuil) {
			return fcFormule_seuil_I_I(COS, fc_form_type);
		}
		
		if(fc_form_type.equals("KNN")) return approcheKpv_Item(COS);

		ArrayList<Item> items_total = items_details(COS);

		/** calcul de temps */
		double temps_debut = System.currentTimeMillis() / 1000;

		HashMap<Integer, ArrayList<Double>> fc_formule = init_Fc_formule(true);

		// supprimer les echantillonage qui depasse le Nb max ou negatif
		int NbMaxItem = data.getNbItems();
		for (int i = 0; i < Methods.list_echants.size(); i++) {
			if (Methods.list_echants.get(i) > NbMaxItem || Methods.list_echants.get(i) < 0) {
				Methods.list_echants.remove(i);
				i--;
				if (!Methods.list_echants.contains(NbMaxItem))
					Methods.list_echants.add(NbMaxItem);
			}
		}

		// varier les echantillonages
		for (int i = 1; i < Methods.list_echants.size(); i++) {
			ArrayList<Item> items = new ArrayList<>();
			int is = 1;
			for (Item it : items_total) {
				items.add(it);
				if (is == Methods.list_echants.get(i)) {
					break;
				}
				is++;
			}

			Methods.echant_actuel = Methods.list_echants.get(i);
			charger_valeur_I_I(fc_formule, items);
			items.clear();
		}

		System.out.print(" ----------- Calculs FC_formul  ");
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");

		items_total.clear();
		return fc_formule;
	}

	private HashMap<Integer, ArrayList<Double>> fcFormule_seuil_I_I(boolean COS, String fc_form_type) {

		// key 1 pour Rapel 2 pour precision 3 pour F_Mesure
		HashMap<Integer, ArrayList<Double>> fc_formule = init_Fc_formule(false);

		/***** seuils variants *****/
		for (int i = 0; i < Methods.list_seuils.size(); i++) {
			Methods.seuil_actuel = Methods.list_seuils.get(i);

			/******* Echantillonage variante !!! ******/
			ArrayList<Item> items_total = items_details(COS);

			ArrayList<Item> items = new ArrayList<>();

			// ou cas de depassement de Nb max ou negatif
			int nbMAxItem = data.getNbItems();
			if (Methods.echant_fix > nbMAxItem || Methods.echant_fix < 0) {
				Methods.echant_fix = nbMAxItem;
				items = items_total;
			} else {
				int is = 1;
				for (Item u : items_total) {
					items.add(u);
					if (is == Methods.echant_fix) {
						break;
					}
					is++;
				}
			}
			/******* Echantillonage variante !!! ******/

			charger_valeur_I_I(fc_formule, items);
			items.clear();
			items_total.clear();
		}
		/***** seuils variants *****/

		return fc_formule;
	}

	private HashMap<Integer, ArrayList<Double>> charger_valeur_I_I(HashMap<Integer, ArrayList<Double>> fc_formule,
			ArrayList<Item> items) {
		double a = Methods.Rappel_I_I(items);
		double b = Methods.precision_I_I(items);
		double c = Methods.F_Mesure(a, b);

		fc_formule.get(1).add(a);
		fc_formule.get(2).add(b);
		fc_formule.get(3).add(c);
		fc_formule.get(4).add(Methods.MAE_I_I(items));
		fc_formule.get(5).add(Methods.accuray_I_I(items));
		fc_formule.get(6).add(Methods.coverage_I_I(items));

		return fc_formule;

	}

	public HashMap<Integer, ArrayList<Double>> slopeOne() {

		ArrayList<Integer> items_total = data.get_ids_Items();
		HashMap<Integer, Map<Integer, Float>> diff = new HashMap<>();

		
		for (int i = 0; i < 350; i++) {
			diff.put(items_total.get(i), new HashMap<Integer, Float>());
			HashMap<Integer, Float> p = new HashMap<Integer, Float>();
			for (int j = i + 1; j < items_total.size(); j++) {

				p.put(items_total.get(j), null);
			}
			diff.get(items_total.get(i)).putAll(p);
		}
		items_total.clear();

		/***** affichage *****/
		System.out.println("fin");

		// User_Id Item_id
		HashMap<Integer, HashMap<Integer, ItemRating>> itemRating = getItemRating();
		for (int m : diff.keySet()) {

			for (int m2 : diff.get(m).keySet()) {
				int c = 0;
				int nb1 = 0;
				for (Integer i : itemRating.keySet()) {
					if (itemRating.get(i).containsKey(m) && itemRating.get(i).containsKey(m2)) {
						c = (int) (c + (itemRating.get(i).get(m2).getRating() - itemRating.get(i).get(m).getRating()));
						nb1++;
					}
				}

				if (nb1 != 0)
					diff.get(m).put(m2, (float) c / nb1);
			}

		}

		itemRating.clear();
		/***** affichage *****/
		System.out.println("Prediction En cours");
		ArrayList<User> users = slopeOne_Prediction(diff);

		diff.clear();
		HashMap<Integer, ArrayList<Double>> fc_formule = init_Fc_formule(false);
		charger_valeur(fc_formule, users);
		return fc_formule;

	}

	private ArrayList<User> slopeOne_Prediction(HashMap<Integer, Map<Integer, Float>> diff) {
		ArrayList<User> users = data.getUsers();
		for (User u : users) {
			for (ItemRating itR : u.getEvaluationPredite()) {
				float temp = 0;
				int nb = 0;
				for (ItemRating itR2 : u.getEvaluation()) {
					if (diff.get(itR.getItemId()) != null) {
						if (diff.get(itR.getItemId()).get(itR2.getItemId()) != null
								&& diff.get(itR.getItemId()).get(itR2.getItemId()) != null) {
							temp = (float) (temp + itR2.getRating()
									+ diff.get(itR.getItemId()).get(itR2.getItemId()).floatValue());
							nb++;
						}
					}
				}
				if (temp != 0 && nb != 0)
					u.getEvaluationPredite2().add(new ItemRating(itR.getId(), (int) (temp / nb)));
			}

		}
		return users;
	}

	/************* K-Means *******************/
	public HashMap<Integer, ArrayList<Double>> kMeans(boolean Cos) {
		double tmp = System.currentTimeMillis() / 1000;
		ArrayList<User> users = getUsers();
		Clusters c = new Clusters();
		Map<Integer, ArrayList<User>> a = c.K_Means(users, Cos);
		System.out.println(System.currentTimeMillis() / 1000 - tmp);
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < a.get(i).size(); j++) {
//				a.get(i).get(j).prediction_all(a.get(i));
			}
		}
		HashMap<Integer, ArrayList<Double>> fc_formule = init_Fc_formule(false);
		charger_valeur(fc_formule, users);
		users.clear();
		return fc_formule;
	}


	/************** KNN-KMeans ****************/
	public HashMap<Integer, ArrayList<Double>> KMeans_KNN(int k, boolean Cos) {
		
		ArrayList<ArrayList<User>> users = data.getUsers80_20();
		Clusters c = new Clusters();
		c.K_Means(users.get(0), Cos);
		ArrayList<User> us=new ArrayList<>();
		ArrayList<User> usersPrime = users.get(1);
		for (User u : usersPrime) {
			c.KNN(u, k, Cos);
		}
		Map<Integer, ArrayList<User>> a = c.getSetOfClusters();
		users.clear();
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < a.get(i).size(); j++) {
//				a.get(i).get(j).prediction_all(a.get(i));
			}
			us.addAll(a.get(i));
		}
		HashMap<Integer, ArrayList<Double>> fc_formule = init_Fc_formule(false);
		charger_valeur(fc_formule, us);
		users.clear();
		/// ??????

		return fc_formule;
	}
	
	/**************** Approche Kpv ***************************/
	public HashMap<Integer, ArrayList<Double>> approcheKpv_User(boolean COS) {
		ArrayList<User> users = data.getUsers();
		Methods.seuil_fix=0.0;
		if (COS) {
			for (int i = 0; i < users.size() - 1; i++) {
				for (int j = i + 1; j < users.size(); j++) {
					users.get(i).simUsingCos(users.get(j));
				}
			}
		} else {
			Methods.seuil_fix=-1.0;
			for (int i = 0; i < users.size() - 1; i++) {
				for (int j = i + 1; j < users.size(); j++) {
					users.get(i).simUsingPerson(users.get(j));
				}
			}
		}
		HashMap<Integer, ArrayList<Double>> fc_formule = init_Fc_formule(true);
		for (Integer k : Methods.listk) {
			if(k!=0){
				for (User u : users) {
					u.getEvaluationPredite2().clear();
					for (ItemRating it : u.getEvaluationPredite()) {
						u.predictionKNN(it, k);
					}
				}
				charger_valeur(fc_formule, users);				
			}
		}
		return fc_formule;
	}

	public HashMap<Integer, ArrayList<Double>> approcheKpv_Item(boolean COS) {
		Methods.seuil_fix=0.0;
		ArrayList<Item> items = data.getItems();
		if (COS) {
			for (int i = 0; i < items.size() - 1; i++) {
				for (int j = i + 1; j < items.size(); j++) {
					items.get(i).simUsingCos(items.get(j));
				}
			}
		} else {
			Methods.seuil_fix=-1.0;
			for (int i = 0; i < items.size() - 1; i++) {
				for (int j = i + 1; j < items.size(); j++) {
					items.get(i).simUsingPerson(items.get(j));
				}
			}
		}
		HashMap<Integer, ArrayList<Double>> fc_formule = init_Fc_formule(true);
		for (int k : Methods.listk) {
			if(k!=0){
				for (Item u : items) {
					u.getEvaluationPredite2().clear();
					for (ItemRating it : u.getEvaluationPredite()) {
						u.predictionKNN(it, k);
					}
				}
				charger_valeur_I_I(fc_formule, items);
			}
				
		}
		return fc_formule;
	}

	/**********************************************************************************************************************************************/
	/*********
	 * Multi-Creteria User-Based***************** Algorithm Methods
	 *********************/
	public HashMap<Integer, ArrayList<Double>> mc_UserBased(boolean COS, boolean seuil, String fc_form_type) {

		if (seuil) {
			return mc_UserBased_seuil_U(COS, fc_form_type);
		}

		if(fc_form_type.equals("KNN")) return approcheKpv_User(COS);
		
		// Else Echantillonage

		// key 1 pour Rapel 2 pour precision 3 pour F_Mesure 4 pour MAE
		HashMap<Integer, ArrayList<Double>> fc_formule = init_Fc_formule(true);

		Methods.seuil_actuel = Methods.seuil_fix;

		ArrayList<User> users_total = mc_Users_details(COS);

		/** calcul du temps */
		double temps_debut = System.currentTimeMillis() / 1000;

		// supprimer les echantillonage qui depasse le Nb max ou negatif
		int MaxNbUsers = data.getNbUsers();
		for (int i = 0; i < Methods.list_echants.size(); i++) {
			if (Methods.list_echants.get(i) > MaxNbUsers || Methods.list_echants.get(i) < 0) {
				if (Methods.list_echants.contains(MaxNbUsers)) {
					Methods.list_echants.remove(i);
					i--;
				} else {
					Methods.list_echants.set(i, MaxNbUsers);
				}
			}
		}

		for (int i = 0; i < Methods.list_echants.size(); i++) {
			ArrayList<User> users = new ArrayList<>();
			int is = 1;
			for (User u : users_total) {
				users.add(u);
				if (is == Methods.list_echants.get(i)) {
					break;
				}
				is++;
			}

			Methods.echant_actuel = Methods.list_echants.get(i);
			charger_valeur(fc_formule, users);
		}

		System.out.print(" ----------- Calculs FC_formul  ");
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");

		return fc_formule;

	}

	private HashMap<Integer, ArrayList<Double>> mc_UserBased_seuil_U(boolean COS, String fc_form_type) {
		// key 1 pour Rapel 2 pour precision 3 pour F_Mesure 4 pour MAE
		HashMap<Integer, ArrayList<Double>> fc_formule = init_Fc_formule(false);

		// negatif ou sup du max
		int MaxNbUser = data.getNbUsers();
		if (Methods.echant_fix > MaxNbUser || Methods.echant_fix < 0)
			Methods.echant_fix = MaxNbUser;

		for (int i = 0; i < Methods.list_seuils.size(); i++) {
			Methods.seuil_actuel = Methods.list_seuils.get(i);
			ArrayList<User> users_total = mc_Users_details(COS);
			/******* Echantillonage variante !!! ******/
			ArrayList<User> users = new ArrayList<>();
			int is = 1;
			for (User u : users_total) {
				users.add(u);
				if (is == Methods.echant_fix) {
					break;
				}
				is++;
			}
			/******* Echantillonage variante !!! ******/

			charger_valeur(fc_formule, users);
			users_total.clear();
			users.clear();
		}

		return fc_formule;

	}

	// pas encore terminer
	public ArrayList<User> mc_Users_details(boolean COS) {

		double temps_debut = System.currentTimeMillis() / 1000;

		ArrayList<User> users = data.getUsers();
		/***** affichage *****/
		System.out.print(" ----------- charger les Donnes !!   ");
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");

		temps_debut = System.currentTimeMillis() / 1000;

		// Similarity
		if (COS) {
			for (int i = 0; i < users.size() - 1; i++) {
				for (int j = i + 1; j < users.size(); j++) {
					users.get(i).cosineMc(users.get(j));
				}
				users.get(i).getEvaluation().clear();
			}
		} else {
			for (int i = 0; i < users.size() - 1; i++) {
				for (int j = i + 1; j < users.size(); j++) {
					users.get(i).personMc(users.get(j));
				}
				users.get(i).getEvaluation().clear();
			}
		}

		/***** affichage calcule le temps *****/
		System.out.print(" ----------- Creer Les Communate  ");
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");
		temps_debut = System.currentTimeMillis() / 1000;

		// prediction for all the items
		for (User u : users) {
			for (ItemRating it : u.getEvaluationPredite()) {
				u.prediction(it);
			}
		}
		/***** affichage *****/
		System.out.print(" ----------- Predections   ");
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");

		return users;
	}

	/*********
	 * Multi-Creteria Algorithm Methods ***************** Item-Based
	 **************************/
	public HashMap<Integer, ArrayList<Double>> mc_ItemBased(boolean COS, boolean seuil, String fc_form_type) {

		if (seuil) {
			return mc_ItemBased_seuil(COS, fc_form_type);
		}
		
		if(fc_form_type.equals("KNN")) return approcheKpv_Item(COS);

		// Else Echantillonage

		Methods.seuil_actuel = Methods.seuil_fix;

		ArrayList<Item> items_total = mc_Items_details(COS);
		// key 1 pour Rapel 2 pour precision 3 pour F_Mesure 4 pour MAE
		HashMap<Integer, ArrayList<Double>> fc_formule = init_Fc_formule(true);

		/** calcul du temps */
		double temps_debut = System.currentTimeMillis() / 1000;

		for (int i = 0; i < Methods.list_echants.size(); i++) {
			ArrayList<Item> items = new ArrayList<>();
			int is = 1;
			for (Item u : items_total) {
				items.add(u);
				if (is == Methods.list_echants.get(i)) {
					break;
				}
				is++;
			}

			Methods.echant_actuel = Methods.list_echants.get(i);
			charger_valeur_I_I(fc_formule, items);
		}

		System.out.print(" ----------- Calculs FC_formul  ");
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");

		return fc_formule;

	}

	private HashMap<Integer, ArrayList<Double>> mc_ItemBased_seuil(boolean COS, String fc_form_type) {
		// key 1 pour Rapel 2 pour precision 3 pour F_Mesure 4 pour MAE
		HashMap<Integer, ArrayList<Double>> fc_formule = init_Fc_formule(false);

		for (int i = 0; i < Methods.list_seuils.size(); i++) {
			Methods.seuil_actuel = Methods.list_seuils.get(i);
			ArrayList<User> users_total = mc_Users_details(COS);
			/******* Echantillonage variante !!! ******/
			ArrayList<User> users = new ArrayList<>();
			int is = 1;
			for (User u : users_total) {
				users.add(u);
				if (is == Methods.echant_fix) {
					break;
				}
				is++;
			}
			/******* Echantillonage variante !!! ******/

			charger_valeur(fc_formule, users);
			users_total.clear();
			users.clear();
		}

		return fc_formule;

	}

	public ArrayList<Item> mc_Items_details(boolean COS) {
		double temps_debut = System.currentTimeMillis() / 1000;
		ArrayList<Item> items = data.getItems();

		System.out.print(" ----------- charger les Donnes !!   ");
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");

		temps_debut = System.currentTimeMillis() / 1000;

		// Similarity
		if (COS) {
			for (int i = 0; i < items.size() - 1; i++) {
				for (int j = i + 1; j < items.size(); j++) {
					items.get(i).cosineMc(items.get(j));
				}
			}
		} else {
			for (int i = 0; i < items.size() - 1; i++) {
				for (int j = i + 1; j < items.size(); j++) {
					items.get(i).personMc(items.get(j));
				}
			}
		}

		System.out.print(" ----------- Creer Les Communate  ");
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");
		temps_debut = System.currentTimeMillis() / 1000;

		// prediction for all the items
		for (Item u : items) {
			for (ItemRating it : u.getEvaluationPredite()) {
				u.prediction(it);
			}
		}

		System.out.print(" ----------- Predections   ");
		System.out.println((System.currentTimeMillis() / 1000) - temps_debut + " sec ---------");

		return items;
	}


	/****** init fc_Formule **************/
	private HashMap<Integer, ArrayList<Double>> init_Fc_formule(boolean echant) {

		HashMap<Integer, ArrayList<Double>> fc_formule = new HashMap<Integer, ArrayList<Double>>();

		ArrayList<Double> val_Rapel = new ArrayList<Double>();
		ArrayList<Double> val_precision = new ArrayList<Double>();
		ArrayList<Double> val_F_mesure = new ArrayList<Double>();
		ArrayList<Double> val_MAE = new ArrayList<Double>();
		ArrayList<Double> val_Accuracy = new ArrayList<Double>();
		ArrayList<Double> val_Coverage = new ArrayList<Double>();

		if (echant) {
			val_Rapel.add((double) 0);
			val_precision.add((double) 0);
			val_F_mesure.add((double) 0);
			val_MAE.add((double) 0);
			val_Accuracy.add((double) 0);
			val_Coverage.add((double) 0);
		}

		fc_formule.put(1, val_Rapel);
		fc_formule.put(2, val_precision);
		fc_formule.put(3, val_F_mesure);
		fc_formule.put(4, val_MAE);
		fc_formule.put(5, val_Accuracy);
		fc_formule.put(6, val_Coverage);

		return fc_formule;
	}
	
	public HashMap<Integer, ArrayList<Double>> ACs(){
		ArrayList<User> users =data.getUsers();
		new ACS().ACS_Algorithme(users);
		
		
		HashMap<Integer, ArrayList<User>> sol = ACS.bestSolution.getSol();
		
    	users.clear();
        for(int i=0;i<sol.size();i++){
          ArrayList<User> us=sol.get(i);
        	for(User u : us){
                u.prediction_all(us);
            }
           
        }
        
        for(int i=0;i<sol.size();i++){
        	 users.addAll(sol.get(i));
        }
   
		HashMap<Integer, ArrayList<Double>> fc_formule = init_Fc_formule(false);

		charger_valeur(fc_formule, users);
		users.clear();
    
		return fc_formule;
	}

}
