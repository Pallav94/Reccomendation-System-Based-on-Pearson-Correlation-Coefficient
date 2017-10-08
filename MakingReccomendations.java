import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MakingReccomendations {
    HashMap<String,HashMap> MR=new HashMap<String,HashMap>();
    ArrayList<String> lineData=new ArrayList<String>();
    HashMap<String,String> movieList=new HashMap<String, String>();

    //Critics List
    HashMap<String, HashMap> criticsList(){
        return MR;
    }

    //Moviess List
    HashMap<String, String> moviesList(){
        return movieList;
    }

    //Function to access matrix.csv
    void accessFile(){
        try {
            File f = new File("/Users/pallavsaxena/Desktop/ratings.csv");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                lineData.add(readLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Adding Movie/Id in the Hashmap
    void movie(){
        ArrayList<String> lineData=new ArrayList<String>();
        try {
            int i;
            File f = new File("/Users/pallavsaxena/Desktop/movies.csv");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
                lineData.add(readLine);
            }
            for(i=1;i<lineData.size();i++){
                String line=lineData.get(i);
                String[] data=line.split(",");
                movieList.put(data[0],data[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //get movieid
    ArrayList<String> getId(){
        ArrayList<String> x=new ArrayList<String>();
        for(Map.Entry m:movieList.entrySet()){
            x.add((String)m.getKey());
        }
        return x;
    }
    //get user id
    ArrayList<String> userId(){
        ArrayList<String> x=new ArrayList<String>();
        for(Map.Entry m:MR.entrySet()){
            x.add((String)m.getKey());
        }
        return x;
    }
    //Store user rating of each user
    void userRating(){
        int i;
        for(i=1;i<lineData.size();i++){
            String line=lineData.get(i);
            String[] data=line.split(",");
            if(MR.containsKey(data[0])==false){
                HashMap<String,String> MovieRating=new HashMap<String,String>();
                MovieRating.put(data[1],data[2]);
                MR.put(data[0],MovieRating);
            }
            else{
                HashMap<String,String>x=MR.get(data[0]);
                x.put(data[1],data[2]);
                MR.put(data[0],x);
            }
        }
        i=0;
        for(Map.Entry m:MR.entrySet()){
            if(i<=5) {
                HashMap<String, String> x = (HashMap<String, String>) m.getValue();
                i++;
            }
        }
    }
    //create data.csv
    void createFile(ArrayList<String> x){
        try{
            FileWriter fout = new FileWriter("/Users/pallavsaxena/Desktop/data.csv");
            int i,j;
            for (i = 0; i < x.size(); i++) {
                fout.write("\t" + x.get(i));
            }
            fout.write("\n");
            for(Map.Entry data:MR.entrySet()){
                fout.write((String)data.getKey());
                HashMap<String,String> l= (HashMap<String, String>) data.getValue();
                for (i=0;i<x.size();i++){
                    if(l.containsKey(x.get(i))){
                        fout.write("\t"+l.get(x.get(i)));
                    }
                    else{
                        fout.write("\t0");
                    }
                }
                fout.write("\n");
            }
            fout.close();
        }
        catch (Exception e){
        }
    }
}