package ant_Colony_Optimization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.apache.catalina.Cluster;

import module.*;
import service.Methods;

public class Solution {



   
    private Clusters clu;
	private double mae;
    private ArrayList<Solution> voisins =new ArrayList<>();
    private double pheromone;




    private boolean explored ;
    public Solution(Clusters c) {
        this.clu = c;
        qualité();
            generer_Voisinage();
            pheromone=ACS.pheromone0;
        explored =false;
    }


//    public Solution(HashMap<Integer,ArrayList<User>> sol) {
//        this.sol = sol;
////        calcul_MAE();
//     Wc();
//            generer_Voisinage();
//            pheromone=ACS.pheromone0;
//        explored =false;
//    }
    public Solution(Clusters c,HashMap<Integer,ArrayList<User>>  sol,ArrayList<Solution> vois){
        this.clu=new Clusters(c,sol);
       qualité();
        this.voisins=vois;
        pheromone=ACS.pheromone0;
        explored =false;
    }
    public boolean isExplored() {
        return explored;
    }
    public void setExplored() {
        this.explored = !explored;
    }
    public HashMap<Integer, ArrayList<User>> getSol() {
		return clu.getSetOfClusters();
	}
    public double getMae() {
        return mae;
    }

    public double getMae_double() {
        return Double.valueOf(mae).doubleValue();
    }

    public void setMae(double mae) {
        this.mae = mae;
    }

    public ArrayList<Solution> getVoisins() {
        return voisins;
    }

    public void setVoisins(ArrayList<Solution> voisins) {
        this.voisins = voisins;
    }

    public double getPheromone() {
        return pheromone;
    }

    public void setPheromone(double pheromone) {
        this.pheromone = pheromone;
    }


    public void calcul_MAE(){
      double temp =System.currentTimeMillis()/1000;
    	ArrayList<User> users=new ArrayList<>();
        for(int i=0;i<clu.getNbCluster();i++){
          ArrayList<User> us=clu.getSetOfClusters().get(i);
        	for(User u : us){
                u.prediction_all(us);
                users.add(u);
            }
        }
        System.out.println(System.currentTimeMillis()/1000 - temp);
    }




    public boolean equals(Object obj) {
        Solution other = (Solution) obj;
        if (Double.valueOf(mae).floatValue() != Double.valueOf(other.mae).floatValue())
            return false;
        return true;
    }

    private void generer_Voisinage(){

        for(int j=0;j<10;j++){
        generer_voisin();
        }

    }

    protected Solution generer_voisin() {
        HashMap<Integer,ArrayList<User>> s= (HashMap<Integer,ArrayList<User>>)this.clu.getSetOfClusters().clone();
        for(int i=0;i<clu.getNbCluster();i++){
   		 for(int j=0;j<clu.getSetOfClusters().get(i).size();j++){
   			User u=clu.getSetOfClusters().get(i).get(j);
   			 if(proba()==1){
   			 int rand=new Random().nextInt(s.size());
   			 s.get(i).remove(j);
        s.get(rand).add(u);
        j--;
        
   			 }}}
        
       Solution solution= new Solution(this.clu,s,new ArrayList<>());
       
        return solution;
    }

    private double wc(){
    	double wc= 0.0;
    	 for(int i=0;i<clu.getNbCluster();i++){
    		 for(int j=0;j<clu.getSetOfClusters().get(i).size();j++){
    			 
    			 double d= clu.getCenter(i).disstanceCos(clu.getSetOfClusters().get(i).get(j));
    			d= Math.abs(d);
    			 if(!Double.isNaN(d) && d>0.0 )
    			 wc+=(1/d);
    			 
    		 }
    	 }
    	return wc;
    }
    
    private double bc(){
    	double bc=0;
    	 for(int i=0;i<clu.getNbCluster();i++){
    		 for(int j=i+1;j<clu.getNbCluster();j++){
    			 double d=clu.getCenter(i).disstanceCos(clu.getCenter(j));
    			 if(!Double.isNaN(d) && d!=0)
    			 bc=bc+(1/d);
    		 }
    	 }
    	 return bc;
    }
    private void qualité(){
    	double b=bc();
    	double w = wc();
    	
    	if(b!=0 ){
    		mae= w/b;
    
    	}
     
    }
    
   private int proba(){
	   double p0=0.8;
	   double p=new Random().nextDouble();
	   if(p>p0) return 1 ; 
	   return 0;
	   
   }
   
    	
    
}
