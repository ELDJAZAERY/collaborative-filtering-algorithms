package service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

import module.Info;
import module.Item;
import module.ItemRating;
import module.User;

public interface Data_metier {

	public void charger(MultipartFile[] files, String dataName);

	public void sessionMovieLens();

	public User getUser(int id);

	public Item getItem(int id);

	public ArrayList<Integer> get_ids_Users();

	public ArrayList<Integer> get_ids_Items();

	public ArrayList<User> getUsers();

	public ArrayList<Item> getItems();

	public Info getInfos();

	public ArrayList<User> users_details(boolean COS);

	public ArrayList<Item> items_details(boolean COS);

	public HashMap<Integer, HashMap<Integer, ItemRating>> getItemRating();

	public HashMap<Integer, ArrayList<Double>> fcFormule_U_U(boolean COS, boolean seuil, String fc_type);
	public HashMap<Integer, ArrayList<Double>> mc_UserBased(boolean COS, boolean seuil, String fc_type);

	public HashMap<Integer, ArrayList<Double>> fcFormule_I_I(boolean COS, boolean seuil, String fc_type);
	public HashMap<Integer, ArrayList<Double>> mc_ItemBased(boolean COS, boolean seuil, String fc_type);

	public HashMap<Integer, ArrayList<Double>> slopeOne();

	public HashMap<Integer, ArrayList<Double>> kMeans(boolean cos);
	
	public HashMap<Integer, ArrayList<Double>> KMeans_KNN(int k, boolean Cos);
	
	public int getNbUsers();
	public int getNbItems();
	public int getNbRatings();
	public int getMaxRating();
	public int getMinRating();
	public HashMap<Integer, ArrayList<Double>> ACs();
	
	
	public HashMap<Integer, ArrayList<Double>> approcheKpv_User(boolean COS);
	public HashMap<Integer, ArrayList<Double>> approcheKpv_Item(boolean COS); 


}
