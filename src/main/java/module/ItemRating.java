package module;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.cache.annotation.Cacheable;

@Entity
@Table(name = "item_rating")
public class ItemRating implements java.io.Serializable {

	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "item_Id", column = @Column(name = "item_id", nullable = false)),
			@AttributeOverride(name = "user_Id", column = @Column(name = "user_id", nullable = false)) })
	private ItemRatingId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
	private Item item;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	private User user;

	@Column(name = "rating", nullable = false)
	private float rating;
	@Column(name = "c1")
	private Integer c1;
	@Column(name = "c2")
	private Integer c2;
	@Column(name = "c3")
	private Integer c3;
	@Column(name = "c4")
	private Integer c4;

	public ItemRating(ItemRatingId id, float d) {
		this.id = id;
		rating = d;
	}

	public ItemRating() {
	}

	public ItemRating(Item item2, float d) {
		this.item = item2;
		this.rating = d;
	}

	public ItemRatingId getId() {
		return this.id;
	}

	public Item getItem() {
		return this.item;
	}

	@Cacheable(value="cache_objet")
	public User getUser() {
		return this.user;
	}

	public double getRating() {
		return this.rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setId(ItemRatingId id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ItemRating [ item=" + item.getItemId() + ", user=" + user.getUserId() + ", rating=" + rating + "]";
	}

	public int getItemId() {
		return this.getId().getItemId();
	}

	public int getUserId() {
		return this.getId().getUserId();
	}

	@Override
	public boolean equals(Object ob) {
		if (ob == null)
			return false;

		if (!(ob instanceof ItemRating))
			return false;
		ItemRating it = (ItemRating) ob;
		if (it.getItemId() == this.getItemId())
			return true;

		return false;
	}

	public void setC1(int c1) {
		this.c1 = c1;
	}

	public void setC2(int c2) {
		this.c2 = c2;
	}

	public void setC3(int c3) {
		this.c3 = c3;
	}

	public void setC4(int c4) {
		this.c4 = c4;
	}

	public int getCritereI(int i) {
		switch (i) {
		case 1:
			return c1;
		case 2:
			return c2;
		case 3:
			return c3;
		case 4:
			return c4;

		default:
			return -1;
		}
	}

}
