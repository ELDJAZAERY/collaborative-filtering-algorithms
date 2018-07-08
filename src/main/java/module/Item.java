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

import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.Cacheable;

import service.Methods;

@Entity
@Table(name = "item")
public class Item implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "item_id", unique = true, nullable = false)
	private int item_Id;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
	private List<ItemRating> evaluation = new ArrayList<>();

	@Transient
	private final TreeMap<Sim<Item>, Byte> Voisinage = new TreeMap<>((o1, o2) -> o1.compareTo(o2));
	@Transient
	private List<ItemRating> evaluationPredite = new ArrayList<ItemRating>();
	
	@Transient
	private List<ItemRating> evaluationPredite2 = new ArrayList<ItemRating>();

	@Cacheable(value="cache_objet")
	public List<ItemRating> getEvaluation() {
		return this.evaluation;
	}

	@Cacheable(value="cache_objet")
	public List<ItemRating> getEvaluationPredite() {
		return this.evaluationPredite;
	}

	@Cacheable(value="cache_objet")
	public List<ItemRating> getEvaluationPredite2() {
		return evaluationPredite2;
	}

	public void setEvaluation(ArrayList<ItemRating> evaluation) {
		this.evaluation = evaluation;
	}

	public void setEvaluationPredite(ArrayList<ItemRating> evaluationPredite) {
		this.evaluationPredite = evaluationPredite;
	}

	@Cacheable(value="cache_objet")
	public TreeMap<Sim<Item>, Byte> getVoisinage() {
		return Voisinage;
	}

	public Item() {
	}

	public Item(int itemId, ArrayList<ItemRating> itemRatings) {
		this.item_Id = itemId;
		this.evaluation = itemRatings;
	}

	public int getItemId() {
		return this.item_Id;
	}

	public void setItemId(int itemId) {
		this.item_Id = itemId;
	}

	@Transient
	@Cacheable(value="cache_objet")
	public ItemRating getItemRating(int user_id) {
		for (ItemRating it : evaluation) {
			if (it.getUserId() == user_id) {
				return it;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "Item [item_Id=" + item_Id + ", evaluation=" + evaluation + "]";
	}

	// Similarity COS
	public double simUsingCos(Item u) {
		ArrayList<ItemRating> eva_u1 = new ArrayList<ItemRating>();
		ArrayList<ItemRating> eva_u2 = new ArrayList<ItemRating>();

		for (ItemRating e : this.evaluation) {
			if (u.cont(e)) {
				eva_u1.add(e);
				eva_u2.add(u.getItemRating(e.getUser().getUserId()));
			}
		}

		if (eva_u1.size() < 10)
			return 0;
		else {
			float sim =(float) Methods.cos(eva_u1, eva_u2);
			if (sim >= Methods.seuil_actuel) {
				Voisinage.put(new Sim<Item>(u, sim), new Byte((byte) 0));
				u.Voisinage.put(new Sim<Item>(this, sim), new Byte((byte) 0));
			}
			return sim;
		}

	}

	// Similarity Person
	public double simUsingPerson(Item u) {
		ArrayList<ItemRating> eva_u1 = new ArrayList<ItemRating>();
		ArrayList<ItemRating> eva_u2 = new ArrayList<ItemRating>();

		double m1 = 0;
		double m2 = 0;
		double a = 0;
		double b = 0;

		// Les Items communs
		for (ItemRating e : this.evaluation) {
			if (u.cont(e)) {
				eva_u1.add(e);
				eva_u2.add(u.getItemRating(e.getUserId()));
			}
		}

		if (eva_u1.size() < 10)
			return 0;
		else {
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

			float sim =(float) Methods.person(eva_u1, eva_u2, m1, m2);

			if (sim >= Methods.seuil_actuel) {
				Voisinage.put(new Sim<Item>(u, sim), new Byte((byte) 0));
				u.Voisinage.put(new Sim<Item>(this, sim), new Byte((byte) 0));
			}
			return sim;
		}
	}

	public void personMc(Item i) {
		ArrayList<Double> avg = new ArrayList<>();
		ArrayList<Double> avg2 = new ArrayList<>();
		ArrayList<ItemRating> eva_u1 = new ArrayList<>();
		ArrayList<ItemRating> eva_u2 = new ArrayList<>();
		double m1 = 0;
		double a;

		for (ItemRating e : this.evaluation) {
			if (i.evaluation.contains(e)) {
				eva_u1.add(e);
				eva_u2.add(i.getItemRating(e.getItemId()));
			}

		}

		for (int i1 = 1; i1 < 5; i1++) {
			a = 0;
			for (ItemRating it : this.evaluation) {
				a += it.getCritereI(i1);

			}
			m1 = a / evaluation.size();
			avg.add(m1);

		}
		for (int i1 = 1; i1 < 5; i1++) {
			a = 0;
			for (ItemRating it : this.evaluation) {
				a += it.getCritereI(i1);

			}
			m1 = a / evaluation.size();
			avg2.add(m1);

		}
		float sim =(float) Methods.mc_Person(eva_u1, eva_u2, avg, avg2);

		if (sim >= Methods.seuil_actuel) {
			Voisinage.put(new Sim<Item>(i, sim), new Byte((byte) 0));
			i.Voisinage.put(new Sim<Item>(this, sim), new Byte((byte) 0));
		}

	}

	public void cosineMc(Item u) {

		ArrayList<ItemRating> eva_u1 = new ArrayList<>();
		ArrayList<ItemRating> eva_u2 = new ArrayList<>();

		for (ItemRating e : this.evaluation) {
			if (u.evaluation.contains(e)) {
				eva_u1.add(e);
				eva_u2.add(u.getItemRating(e.getItemId()));
			}
		}

		float sim =(float) Methods.mc_Cosine(eva_u1, eva_u2);
		eva_u1.clear();
		eva_u2.clear();
		if (sim >= 0.1) {
			Voisinage.put(new Sim<Item>(u, sim), new Byte((byte) 0));
			u.Voisinage.put(new Sim<Item>(this, sim), new Byte((byte) 0));
		}

	}

	// Prediction
	public void prediction(ItemRating i) {

		double S = 0;
		double S1 = 0;
		float rat=0;
		Set<Sim<Item>> voisins = Voisinage.keySet();

		for (Sim<Item> v : voisins) {

			double vote = ((Item) v.getPar()).getVote(i);
			if (vote != -1) {// c.a.d que le voisin a bien evaluer pour l'item i

				double sim = v.getSim();
				S = S + sim * (vote-v.getPar().avgNote());
				S1 = S1 + Math.abs(sim);
			}

		}
		rat=(float)(avgNote()+(S/S1));
		if (S1 != 0) {
			evaluationPredite2.add(new ItemRating(i.getId(), (rat)));

		}

	}

	public boolean cont(ItemRating o) {

		for (ItemRating it : evaluation)
			if (it.getUserId() == o.getUserId())
				return true;
		return false;
	}

	@Transient
	@Cacheable(value="cache_objet")
	public double getVote(ItemRating item) {
		// evaluation's type = Collection

		for (ItemRating e : evaluationPredite) {
			if (e.getUserId() == item.getUserId())
				return e.getRating();
		}
		return -1;
	}

	public void removeAllEval(List<ItemRating> evaluationPredite3) {
		for (ItemRating itemRating : evaluationPredite3) {
			for (int i = 0; i < evaluation.size(); i++) {
				if (itemRating.getUserId() == evaluation.get(i).getUserId())
					evaluation.remove(i);
			}
		}

	}

	public void predictionKNN(ItemRating it, int k) {
		int j = 0;
		double S = 0;
		double S1 = 0; 
		float rat;
		Set<Sim<Item>> voisins = Voisinage.keySet();
		for (Sim<Item> v : voisins) {

			double vote = ((Item) v.getPar()).getVote(it);
			// verfier que le Voisin v a bien evaluer pour l'item i
			if (vote != -1) {

				double sim = v.getSim();
				S = S + sim * (vote-v.getPar().avgNote());
				S1 = S1 + Math.abs(sim);
			}
			if (j == k) {
				break;
			}
			j++;

		}
		rat=(float)(avgNote()+(S/S1));
		if (S1 != 0 && (S / S1) >= 1) {
			evaluationPredite2.add(new ItemRating(it.getId(), rat));
		}

	}
	
	
	public double avgNote(){
	int s=0;
		for(ItemRating it:evaluation){
			s=s+(int)it.getRating();
		}
		return (double)s/evaluation.size();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (item_Id != other.item_Id)
			return false;
		return true;
	}

}
