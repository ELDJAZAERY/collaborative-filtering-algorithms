package module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

import service.Methods;

public class Clusters {
	private int nbCluster;
	ArrayList<User> center = new ArrayList<>();
	HashMap<Integer, ArrayList<User>> setOfClusters = new HashMap<>();
	
	HashMap<Integer, Integer> indexes1 = new HashMap<>();
	public Clusters(Clusters c,	HashMap<Integer, ArrayList<User>> s){
		this.nbCluster=c.nbCluster;
		this.center=c.center;
		this.setOfClusters=s;
	}

	public Clusters() {

	}

	public HashMap<Integer, ArrayList<User>> K_Means(ArrayList<User> u, boolean Cos) {
		nbCluster = 15;
		ArrayList<User> userPrime = new ArrayList<>();
		userPrime.addAll(u);

		ArrayList<User> center = new ArrayList<>();
		HashMap<Integer, ArrayList<User>> setClusters = new HashMap<>();
		HashMap<Integer, Double> indexes = new HashMap<>();
		HashMap<Integer, Integer> indexes1 = new HashMap<>();
		// initialization
		for (int i = 0; i < nbCluster; i++) {
			int rand = new Random().nextInt(userPrime.size());
			center.add(userPrime.get(rand));
			setClusters.put(i, new ArrayList<User>());
			indexes.put(userPrime.get(rand).getUserId(), -1.0);
			indexes1.put(userPrime.get(rand).getUserId(), i);
			userPrime.remove(rand);
		}
		for (int i = 0; i < userPrime.size(); i++) {
			double max = -1;
			int index = -1;
			User tempU = userPrime.get(i);
			for (int j = 0; j < nbCluster; j++) {
				double tmp = simDistance(center.get(j), tempU, Cos);
				if (tmp > max) {
					max = tmp;
					index = j;
				}
			}
			setClusters.get(index).add(tempU);
			indexes.put(tempU.getUserId(), max);
			indexes1.put(userPrime.get(i).getUserId(), index);
		}
		boolean changed = true;
		int m = 10;
		int nbit = 0;
		while (changed && nbit < m) {
			changed = false;
			
			for (int c = 0; c < nbCluster; c++) {
				if (!setClusters.get(c).isEmpty()) {
					User cen = UpdateCenter(setClusters.get(c));
					center.remove(c);
					center.add(c,cen);
				
				}
			}
		
			
			
			for (User user : u) {
				int userid = user.getUserId();
				double max = -1.0;
				int index = -1;
				int j1 = indexes1.get(userid);
//				for(int c=0;c<nbCluster;c++){
				int i=0;
				for (User ci : center) {
					double tmp = simDistance(ci, user, Cos);
					
					if (tmp > max) {
						max = tmp;
						index = i;
					}
					i++;
					
				}
				if (index != j1) {
					changed = true;	
					indexes1.replace(userid, index);
					indexes.replace(userid, max);
					setClusters.get(j1).remove(user);
					setClusters.get(index).add(user);
				}
				
			}

			
			
			nbit++;

		}
		this.indexes1= indexes1;
		this.center = center;
		this.setOfClusters = setClusters;
		System.out.println("fin");
		return setClusters;

	}

	public User getCenter(int i) {
		return center.get(i);
	}

	private User UpdateCenter(ArrayList<User> mean) {
		ArrayList<ItemRating> temp = new ArrayList<>();
	
		for(User u1:mean){
			for(ItemRating r:u1.getEvaluation()){
				if(!temp.contains(r)){
					temp.add(new ItemRating(r.getId(),0));
				}
			}
		}
		for (ItemRating it : temp) {
			for (User us : mean) {
				it.setRating((int) (it.getRating() + us.getVote(it)));
			}
			it.setRating((int) ((double) it.getRating() / mean.size()));
		}
		
		User u = new User();
		u.setEvaluation(temp);
		return u;
	}

	public void KNN(User u, int k, boolean Cos) {
		TreeMap<Sim<User>, Integer> Kvois = new TreeMap<Sim<User>, Integer>((o1, o2) -> o1.compareTo(o2));

		for (int j = 0; j < nbCluster; j++) {
			for (User uj : this.setOfClusters.get(j)) {
				Sim<User> s = new Sim<User>(uj, (float)simDistance(u, uj, Cos));
				Kvois.put(s, j);
				
			}
		}
		double poids = 0;
		int p = 0;
		for (int j = 0; j < nbCluster; j++) {
			double d = 0;
			double w = 0;
			int i=0;
			for (Sim<User> s : Kvois.keySet()) {
				if (Kvois.get(s) == j) {
					d = s.getSim();
					w = w +d;
				}
				if (i == k) {
					break;
				}
			i++;
			}
			if (w > poids) {
				p = j;
			}
		}

		this.setOfClusters.get(p).add(u);

	}

	private double simDistance(User u1, User u2, boolean Cos) {
		
			Methods.seuil_fix=0.0;

			return u1.disstanceCos(u2);

	
	}

	public int getNbCluster() {
		return nbCluster;
	}
	public void setSetOfClusters(HashMap<Integer, ArrayList<User>> setOfClusters) {
		this.setOfClusters = setOfClusters;
	}

	public HashMap<Integer, ArrayList<User>> getSetOfClusters() {
		return setOfClusters;
	}
	

}
