public class Formula {
    static double pearson(Critic a, Critic b){
        int i;
        double sum1=0,sum2=0;
        for(i=0;i<a.x.size();i++){
            sum1=sum1+a.x.get(i);
        }
        for(i=0;i<a.x.size();i++){
            sum2=sum2+b.x.get(i);
        }
        double sumsq1=0;
        double sumsq2=0;
        for(i=0;i<a.x.size();i++){
            sumsq1=sumsq1+(a.x.get(i)*a.x.get(i));
        }
        for(i=0;i<b.x.size();i++){
            sumsq2=sumsq2+(b.x.get(i)*b.x.get(i));
        }
        double pSum=0;
        for(i=0;i<a.x.size();i++){
            pSum=pSum+(a.x.get(i) * a.x.get(i));
        }
        double num=pSum-(sum1*sum2/a.x.size());

        double den=Math.sqrt((sumsq1-Math.pow(sum1,2)/a.x.size())*(sumsq2-Math.pow(sum2,2)/a.x.size()));
        if(den==0){
            return 0;
        }
        return 1-num/den;
    }
}