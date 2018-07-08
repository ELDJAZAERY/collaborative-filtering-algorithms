package module;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class ItemRatingId implements java.io.Serializable {

	
	private int item_Id;
	private int user_Id;

	public ItemRatingId() {
	}

	public ItemRatingId(int itemId, int userId) {
		this.item_Id = itemId;
		this.user_Id = userId;
	}

	@Column(name = "item_id", nullable = false)
	public int getItemId() {
		return this.item_Id;
	}


	@Column(name = "user_id", nullable = false)
	public int getUserId() {
		return this.user_Id;
	}


	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ItemRatingId))
			return false;
		ItemRatingId castOther = (ItemRatingId) other;

		return (this.getItemId() == castOther.getItemId()) && (this.getUserId() == castOther.getUserId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getItemId();
		result = 37 * result + this.getUserId();
		return result;
	}

	public void setitemId(int itemId) {
		this.item_Id = itemId;
	}

	public void setUserId(int userId) {
		this.user_Id = userId;
	}

        
}
