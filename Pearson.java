import java.io.*;
import java.util.*;

public class Pearson {
    ArrayList<String> lineData = new ArrayList<String>();
    ArrayList<Critic> criticList = new ArrayList<Critic>();

    void getData() {
        try {
            String s = "";
            File f = new File("/Users/pallavsaxena/Desktop/data.csv");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                lineData.add(readLine);
            }
            for (int i = 1; i < lineData.size(); i++) {
                s = lineData.get(i);
                String[] temp = s.split("\\s");
                ArrayList<Double> numbers = new ArrayList<Double>();
                for (int j = 1; j < temp.length; j++) {
                    numbers.add(Double.parseDouble(temp[j]));
                }
                criticList.add(new Critic(temp[0], numbers));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    HashMap<String, Double> pearsonCoeffiecient() {
        HashMap<String, Double> x = new HashMap<String, Double>();
        Critic user = criticList.get(0);
        for (int i = 1; i < criticList.size(); i++) {
            double ans = Formula.pearson(user, criticList.get(i));
            criticList.get(i).pearson=ans;
            x.put(criticList.get(i).id, ans);
        }
        return x;
    }

    HashMap<String, HashMap> modifiedScore(HashMap<String, HashMap> x, HashMap<String, Double> y, ArrayList<String> z) {
        double score;
        double similarityTotal;
        double total;
        double similarity;
        HashMap<String, String> user1 = x.get("1");
        for (int i = 0; i < z.size(); i++) {
            similarityTotal = 0;
            total = 0;
            for (Map.Entry m : x.entrySet()) {
                if ((String)m.getKey() != "1") {
                    HashMap<String, String> c = (HashMap<String, String>) m.getValue();
                    if (c.containsKey(z.get(i))) {
                        score = Double.parseDouble(c.get(z.get(i)));
                        similarity = y.get((String) m.getKey());
                        total = total + (score * similarity);
                        similarityTotal = similarityTotal + similarity;
                    }
                }
                user1.put(z.get(i), String.valueOf(total / similarityTotal));
            }
        }
        x.put("1",user1);
        return x;
    }

    //create modified data.csv
    void createFile(ArrayList<String> x, HashMap<String, HashMap> MR) {
        try {
            FileWriter fout = new FileWriter("/Users/pallavsaxena/Desktop/data.csv");
            int i, j;
            for (i = 0; i < x.size(); i++) {
                fout.write("\t" + x.get(i));
            }
            fout.write("\n");
            for (Map.Entry data : MR.entrySet()) {
                fout.write((String) data.getKey());
                HashMap<String, String> l = (HashMap<String, String>) data.getValue();
                for (i = 0; i < x.size(); i++) {
                    if (l.containsKey(x.get(i))) {
                        fout.write("\t" + l.get(x.get(i)));
                    } else {
                        fout.write("\t0");
                    }
                }
                fout.write("\n");
            }
            fout.close();
        } catch (Exception e) {
        }
    }

    //Movies not rated by user 1
    ArrayList<String> notRatedByUser(HashMap<String, String> moviesList,HashMap<String, HashMap> MR){
        HashMap<String,String> x=MR.get("1");
        ArrayList<String> y=new ArrayList<String>();
        for(Map.Entry m:moviesList.entrySet()){
            if(x.containsKey((String) m.getKey())==false){
                y.add((String) m.getKey());
            }
        }
        return y;
    }
    void modifiedpearsonCoeffiecient() {
        Critic user = criticList.get(0);
        for (int i = 1; i < criticList.size(); i++) {
            double ans = Formula.pearson(user, criticList.get(i));
            System.out.println(user.id + " - " + criticList.get(i).id + " = " + ans);
        }

    }
    void sortedCriticsScore(){
        PriorityQueue<Critic> queue = new PriorityQueue<>(5, Collections.reverseOrder());
        for (int i=0;i<criticList.size();i++){
            queue.add(criticList.get(i));
        }
        System.out.println("Top 5 Sorted Critics:");
        for(int i=1;i<=5;i++){
            Critic c=queue.poll();
            System.out.println(c.id+":"+c.pearson);
        }
    }
    public static void main(String[] args) {
        MakingReccomendations MK = new MakingReccomendations();
        MK.accessFile();
        MK.userRating();
        MK.movie();
        ArrayList<String> x = MK.getId();
        MK.createFile(x);
        HashMap<String, HashMap> UserList = MK.criticsList();
        HashMap<String, String> moviesList = MK.moviesList();
        Pearson p = new Pearson();
        p.getData();
        HashMap<String, Double> y = p.pearsonCoeffiecient();
        ArrayList<String> list = p.notRatedByUser(moviesList,UserList);
        HashMap<String, HashMap> modifiedUserList = p.modifiedScore(UserList, y, list);
        HashMap<String,String> b=modifiedUserList.get("1");
        p.createFile(x, modifiedUserList);
        p.getData();
        p.modifiedpearsonCoeffiecient();
        p.sortedCriticsScore();
    }
}
