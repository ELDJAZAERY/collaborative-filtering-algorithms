package ant_Colony_Optimization;

import java.util.ArrayList;
import java.util.Random;

public class Ant {
	
	private Solution solution_actuel;



    private ArrayList<Solution> visitedSolution = new ArrayList<>();
	
	
	public Ant(){

	}
	
	public Solution getSolution_actuel() {
		return solution_actuel;
	}

	public void maj_local(){
		double pheromone = solution_actuel.getPheromone();
		pheromone = ((1-ACS.p) * pheromone) + (ACS.p * ACS.pheromone0) ;
		solution_actuel.setPheromone(pheromone);
	}
    public ArrayList<Solution> getVisitedSolution() {
        return visitedSolution;
    }
    private boolean solution_visite(Solution solution){
		for(Solution s:visitedSolution){
			if(s==solution)
				return true;
		}
		return false;
	}
	
	public void selection_solution(){
		Random rand= new Random();
		float q= rand.nextFloat();
		boolean e=false;
		if(q<=ACS.q0){
			select_par_Intensification();
		}
		else{
			select_par_diversification();
		}

	}
	
	private boolean select_par_Intensification(){
		Solution temp = null;
		double prob=0;
		for(Solution s:solution_actuel.getVoisins()){
			double deltPher=solution_actuel.getPheromone()-s.getPheromone();
			double nij=1/(solution_actuel.getMae()-s.getMae());
			double probTemp= Math.pow(nij,ACS.beta)*deltPher;
			if(probTemp>=prob && !(s.isExplored())  ){
				
				prob=probTemp;
				temp=s;
			}
		}
		 if(temp==null)
			{
				solution_actuel=solution_actuel.generer_voisin();

			}else{
	        this.solution_actuel=temp;
			}
			this.solution_actuel.setExplored();
			ACS.solution_explore.add(this.solution_actuel);
        return true;
        		}
	
	private void select_par_diversification(){
        double pherVisib=0;
	    for(Solution s: visitedSolution){
            double deltPher=solution_actuel.getPheromone()-s.getPheromone();
            double nij=1/(solution_actuel.getMae()-s.getMae());
            pherVisib  += Math.pow(nij,ACS.beta)*Math.pow(deltPher,ACS.alpha);
        }
	    Solution temp = null;
        double prob=0;
        for(Solution s:ACS.solution_explore){
            double deltPher = solution_actuel.getPheromone()-s.getPheromone();
            double nij=1/(solution_actuel.getMae()-s.getMae());
            double probTemp= (Math.pow(nij,ACS.beta)*Math.pow(deltPher,ACS.alpha))/pherVisib;
            if( probTemp>=prob && !(s.isExplored())){
                temp = s;
                prob=probTemp;
            }
        }
      
        if(temp!=null)
		{
            this.solution_actuel=temp;		
            this.solution_actuel.setExplored();
    		ACS.solution_explore.add(this.solution_actuel);
		}
		

	}

	public void randomSelection(){
		int rand;
	    do{rand=new Random().nextInt(ACS.population.size());}while (ACS.population.get(rand).isExplored());
	        this.solution_actuel=ACS.population.get(rand);
        ACS.solution_explore.add(this.solution_actuel);
    }
	


}
