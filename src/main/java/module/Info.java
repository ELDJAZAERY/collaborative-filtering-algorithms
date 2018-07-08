package module;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "info")
public class Info implements java.io.Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1704375782740376774L;
	private int id;
	private int nbItems;
	private int nbRatings;
	private int nbUsers;

	public Info() {
	}

	
	public Info(int id, int nbItems, int nbRatings, int nbUsers) {
		this.id = id;
		this.nbItems = nbItems;
		this.nbRatings = nbRatings;
		this.nbUsers = nbUsers;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "nb_items", nullable = false)
	public int getNbItems() {
		return this.nbItems;
	}

	public void setNbItems(int nbItems) {
		this.nbItems = nbItems;
	}

	@Column(name = "nb_ratings", nullable = false)
	public int getNbRatings() {
		return this.nbRatings;
	}

	public void setNbRatings(int nbRatings) {
		this.nbRatings = nbRatings;
	}

	@Column(name = "nb_users", nullable = false)
	public int getNbUsers() {
		return this.nbUsers;
	}

	public void setNbUsers(int nbUsers) {
		this.nbUsers = nbUsers;
	}

}
