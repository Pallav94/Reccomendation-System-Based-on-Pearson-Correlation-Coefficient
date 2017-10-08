import java.util.ArrayList;

public class Critic implements Comparable<Critic> {
    String id;
    ArrayList<Double> x;
    double pearson;

    Critic(String a,ArrayList<Double> b){
        id=a;
        x=b;
        pearson=0;
    }

    @Override
    public int compareTo(Critic n) {
        return Double.compare(this.pearson,n.pearson);
    }
}
