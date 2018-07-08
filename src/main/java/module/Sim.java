package module;

public class Sim <T> implements Comparable<Sim> {
	private T par;
	public T getPar() {
		return par;
	}
	private float sim;
	
	public double getSim() {
		return sim;
	}

	public Sim(T u,float s){
		par=u;
		sim=s;
	}

	@Override
	public int compareTo(Sim o) {
		if(this.sim < o.sim)
			return 1;
		else
		if(this.sim > o.sim)
			return -1;
		return 0;
	}

}
