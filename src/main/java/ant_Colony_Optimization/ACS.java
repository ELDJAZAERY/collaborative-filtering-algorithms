package ant_Colony_Optimization;

import module.*;

import java.util.ArrayList;

public class ACS {
	public static int nbIter=200;
	public static double pheromone0 = 0.1;
	public static float alpha=(float) 0.1;
    public static float beta=(float) 2;
    public static float q0=(float) 0.9;
	public static float p=(float) 0.1;
	protected static ArrayList<Solution> population =new ArrayList<>();
	public static Solution bestSolution;
	protected static ArrayList<Solution> solution_explore = new ArrayList<>();

	private ArrayList<Ant> Ants = new ArrayList<>();

	
	public ACS(){

	}
	
	private void init_Ants(){

		for(int i=0;i<10;i++){
			Ant f = new Ant();
			f.randomSelection();
			Ants.add(f);
		}
	}
	
	private void maj_global() {
		Solution best= bestSolution;
		double delta = 0;
		
	
		
		if(bestSolution !=null){
			double pheromone = best.getPheromone();
			delta = bestSolution.getPheromone();
			pheromone = ((1 - ACS.p) *pheromone ) + (ACS.p*delta);
			bestSolution.setPheromone(pheromone);
		}
			
			for( Solution s:solution_explore ){
				if(best==null)
					best=s;
				if(s.getMae()<best.getMae())
					best=s;
			}
			bestSolution =best;
		
	}
	public static boolean wasVisited(Solution solution){
			for(Solution s:solution_explore){
				if(s.getSol().equals(solution))
					return true;
			}
			return false;
	}
	private  void initialisation(ArrayList<User> users){
	    for(int i=0;i<10;i++){
	    	Clusters c=new Clusters();
	    	c.K_Means(users,true);
	        Solution s =new Solution(c);
	        population.add(s);
        }
        init_Ants();
    }

	public void ACS_Algorithme(ArrayList<User> users) {
        double temp =System.currentTimeMillis()/1000;  
		initialisation(users);
		for(int i = 0; i < nbIter; i++) {
			for (Ant a : Ants) {
				a.selection_solution();
				a.maj_local();
			}
			maj_global();
		}
		
		
		System.out.println("------------------");
		System.out.println(System.currentTimeMillis()-temp);
	}
	
	

}
