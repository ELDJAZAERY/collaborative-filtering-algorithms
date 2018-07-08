package module;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.web.multipart.MultipartFile;

public interface Data_DAO {
	
	public void charger(MultipartFile[] files,String dataName);
	
	public void sessionMovieLens();

	public User getUser(int id);
	public Item getItem(int id);
	
	
	public ArrayList<Integer> get_ids_Users();
	public ArrayList<Integer> get_ids_Items();
	public HashMap<Integer,HashMap<Integer,ItemRating>> getItemRating();
	
	public ArrayList<User> getUsers();
	public ArrayList<ArrayList<User>> getUsers80_20();
	
	public ArrayList<Item> getItems();
	public ArrayList<ArrayList<Item>> getItems80_20();

	public Info getInfos();
	
	public int getNbUsers();
	public int getNbItems();
	public int getNbRatings();
	public int getMaxRating();
	public int getMinRating();
	
}
