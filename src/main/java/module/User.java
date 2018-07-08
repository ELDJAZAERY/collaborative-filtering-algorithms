package module;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.cache.annotation.Cacheable;

import service.Methods;

@Entity
@Table(name = "user")
public class User implements java.io.Serializable {

	@Id
	@Column(name = "user_id", unique = true, nullable = false)
	private int user_Id;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
	private List<ItemRating> evaluation = new ArrayList<>();
	@Transient
	private final ArrayList<Sim<User>> Voisinage = new ArrayList<>();
	@Transient
	private List<ItemRating> evaluationPredite = new ArrayList<ItemRating>();
	@Transient
	private List<ItemRating> evaluationPredite2 = new ArrayList<ItemRating>();

	public User() {
	}

	public User(int userId, int age) {
		this.user_Id = userId;
	}

	public User(int userId, ArrayList<ItemRating> itemRatings) {
		this.user_Id = userId;
		this.evaluation = itemRatings;
	}

	public int getUserId() {
		return this.user_Id;
	}

	public void setUserId(int userId) {
		this.user_Id = userId;
	}

	@Cacheable(value="cache_objet")
	public List<ItemRating> getEvaluation() {
		return this.evaluation;
	}

	@Cacheable(value="cache_objet")
	public List<ItemRating> getEvaluationPredite() {
		return this.evaluationPredite;
	}

	public void setEvaluation(ArrayList<ItemRating> evaluation) {
		this.evaluation = evaluation;
	}

	public void setEvaluationPredite(ArrayList<ItemRating> evaluationPredite) {
		this.evaluationPredite = evaluationPredite;
	}

	@Cacheable(value="cache_objet")
	public List<ItemRating> getEvaluationPredite2() {
		return evaluationPredite2;
	}

	@Cacheable(value="cache_objet")
	public ArrayList<Sim<User>> getVoisinage() {
		return Voisinage;
	}

	@Override
	public String toString() {
		return "User [user_Id=" + user_Id + ", evaluation=" + evaluation + "]";
	}

	// Similarity COS
	public double simUsingCos(User u) {
		ArrayList<ItemRating> eva_u1 = new ArrayList<ItemRating>();
		ArrayList<ItemRating> eva_u2 = new ArrayList<ItemRating>();
		for (ItemRating e : this.getEvaluation()) {
			if (u.getEvaluation().contains(e)) {
				eva_u1.add(e);
				eva_u2.add(u.get_item_rating(e.getItemId()));
			}
		}
		if (eva_u1.size() < 5)
			return 0 ;
		float sim =(float) Methods.cos(eva_u1, eva_u2);
		if (sim >= Methods.seuil_actuel) {
			getVoisinage().add(new Sim<User>(u, sim));
			u.getVoisinage().add(new Sim<User>(this, sim));
		}
return sim;
	}

	/* Similarity person */
	public double simUsingPerson(User u) {
		ArrayList<ItemRating> eva_u1 = new ArrayList<ItemRating>();
		ArrayList<ItemRating> eva_u2 = new ArrayList<ItemRating>();
		double m1 = 0;
		double m2 = 0;
		double a = 0;
		double b = 0;
		// retainall;
		for (ItemRating e : this.evaluation) {
			if (u.evaluation.contains(e)) {
				eva_u1.add(e);
				eva_u2.add(u.get_item_rating(e.getItemId()));
			}
		}
		if (eva_u1.size() < 10)
			return -1;
		// Moyenne m1
		for (ItemRating it : this.evaluation) {
			a += it.getRating();
		}
		m1 = a / evaluation.size();
		// Moyenne m2
		for (ItemRating it : u.evaluation) {
			b += it.getRating();
		}
		m2 = b / u.getEvaluation().size();
		float sim = (float) Methods.person(eva_u1, eva_u2, m1, m2);
		if (sim >= Methods.seuil_actuel) {
			getVoisinage().add(new Sim<User>(u, sim));
			u.getVoisinage().add(new Sim<User>(this, sim));
		}
	return sim;
	}

	// Prediction
	public void prediction(ItemRating i) {
		double S = 0;
		double S1 = 0;
		float rat = 0;
		
		ArrayList<Sim<User>> voisins = getVoisinage();
		for (Sim<User> v : voisins) {
			double vote = ((User) v.getPar()).getVote(i);
			// verfier que le Voisin v a bien evaluée pour l'item i
			if (vote != -1) {
				double sim = v.getSim();
				S = S + sim * (vote - ((User) v.getPar()).avgNote());
				S1 = S1 + sim;
			}
		}
		rat =(float) (avgNote() + (S / S1));
		if (S1 != 0) {
			getEvaluationPredite2().add(new ItemRating(i.getId(), rat));
		}
	}

	public void predictionKNN(ItemRating i, int k) {
		int j = 0;
		double S = 0;
		double S1 = 0;
		float rat;
		ArrayList<Sim<User>> voisins = getVoisinage();
		for (Sim<User> v : voisins) {
			double vote = ((User) v.getPar()).getVote(i);
			// verfier que le Voisin v a bien evaluer pour l'item i
			if (vote != -1) {
				double sim = v.getSim();
				S = S + sim * (vote-v.getPar().avgNote());
				S1 = S1 + sim;
			}
			if (j == k) {
				break;
			}
			j++;
		}
		rat=(float)(avgNote()+(S/S1));
		if (S1 != 0 && (S / S1) >= 1) {
			evaluationPredite2.add(new ItemRating(i.getId(), rat));
		}
	}
	
	
	// useless
	public void prediction_all(User v) {
		double S = 0;
		double S1 = 0;
		float rat = 0;
		for (ItemRating it : evaluation) {
			double vote = v.getVote(it);
			// verfier que le Voisin v a bien evaluée pour l'item i
			if (vote != -1) {
				double sim = v.simUsingCos(v);
				S = S + sim * (vote - v.avgNote());
				S1 = S1 + sim;
			}
		
		rat = (float)(avgNote() + (S / S1));
			if (S1 != 0) {
				evaluationPredite2.add(new ItemRating(it.getId(), rat));
			}
		}
	}

	public void prediction_all(ArrayList<User> vois) {
		for (ItemRating it : evaluationPredite) {
			double S = 0;
			double S1 = 0;
			float rat = 0;
			for (User v : vois) {
				double vote = v.getVote(it);
				// verfier que le Voisin v a bien evaluée pour l'item i
				if (vote != -1) {
					double sim = v.disstanceCos(v);
					S = S + sim * (vote - v.avgNote());
					S1 = S1 + sim;
				}
			}
			rat = (float)(avgNote() + (S / S1));
			if (S1 != 0) {
				evaluationPredite2.add(new ItemRating(it.getId(), rat));
			}
		}
	}

	public double personMc(User u) {
		ArrayList<Double> avg = new ArrayList<>();
		ArrayList<Double> avg2 = new ArrayList<>();
		ArrayList<ItemRating> eva_u1 = new ArrayList<>();
		ArrayList<ItemRating> eva_u2 = new ArrayList<>();
		double m1 = 0;
		double a;
		for (ItemRating e : this.evaluation) {
			if (u.evaluation.contains(e)) {
				eva_u1.add(e);
				eva_u2.add(u.get_item_rating(e.getItemId()));
			}
		}
		for (int i = 1; i < 5; i++) {
			a = 0;
			for (ItemRating it : this.evaluation) {
				a += it.getCritereI(i);
			}
			m1 = a / evaluation.size();
			avg.add(m1);
		}
		for (int i = 1; i < 5; i++) {
			a = 0;
			for (ItemRating it : this.evaluation) {
				a += it.getCritereI(i);
			}
			m1 = a / evaluation.size();
			avg2.add(m1);
		}
		float sim =(float) Methods.mc_Person(eva_u1, eva_u2, avg, avg2);
		eva_u1.clear();
		eva_u2.clear();
		avg.clear();
		avg2.clear();
		if (sim >= Methods.seuil_actuel) {
			getVoisinage().add(new Sim<User>(u, sim));
			u.getVoisinage().add(new Sim<User>(this, sim));
		}
		return sim;
	}

	public double cosineMc(User u) {
		ArrayList<ItemRating> eva_u1 = new ArrayList<>();
		ArrayList<ItemRating> eva_u2 = new ArrayList<>();
		for (ItemRating e : this.evaluation) {
			if (u.evaluation.contains(e)) {
				eva_u1.add(e);
				eva_u2.add(u.get_item_rating(e.getItemId()));
			}
		}
		float sim =(float) Methods.mc_Cosine(eva_u1, eva_u2);
		eva_u1.clear();
		eva_u2.clear();
		if (sim >= Methods.seuil_actuel) {
			getVoisinage().add(new Sim<User>(u, sim));
			u.getVoisinage().add(new Sim<User>(this, sim));
		}
		return sim;
	}

	@Transient
	@Cacheable(value="cache_objet")
	public ItemRating get_item_rating(int item_id) {
		for (int i=0 ; i< getEvaluation().size();i++) {
			ItemRating it = getEvaluation().get(i);
			if (it.getItemId() == item_id) {
				return it;
			}
		}
		return null;
	}

	// get Rating of item
	@Transient
	@Cacheable(value="cache_objet")
	public double getVote(ItemRating item) {
		// evaluation's type = Collection
		for (int i =0 ; i < getEvaluationPredite().size();i++) {
			ItemRating e = getEvaluationPredite().get(i);
			if (e.getItemId() == item.getItemId())
				return e.getRating();
		}
		return -1;
	}

	public double hasEvaluated(Item item) {
		for (int i=0 ; i< getEvaluation().size();i++) {
			ItemRating it = getEvaluation().get(i);
			if (it.getItemId() == item.getItemId()) {
				return it.getRating();
			}
		}
		return -1;
	}

	public double avgNote() {
		int s = 0;
		for (ItemRating it : this.evaluation) {
			s = s + (int) it.getRating();
		}
		return (double) s / evaluation.size();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (user_Id != other.user_Id)
			return false;
		return true;
	}
	
	public double disstanceCos(User u) {
		ArrayList<ItemRating> eva_u1 = new ArrayList<ItemRating>();
		ArrayList<ItemRating> eva_u2 = new ArrayList<ItemRating>();
		for (ItemRating e : this.getEvaluation()) {
			if (u.getEvaluation().contains(e)) {
				eva_u1.add(e);
				eva_u2.add(u.get_item_rating(e.getItemId()));
			}
		}
		
		if (eva_u1.size() < 5) return Math.abs(0.0);
		
		double sim = Methods.cos(eva_u1, eva_u2);
		
		return sim;
	}
}
