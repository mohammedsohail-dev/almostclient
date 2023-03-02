package org.example.test;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


 class threaddetails{

    public static Integer numberofthreads=5;
    public static Integer numberofpostsperthread=15;
    public static Integer counter=0;
    public static ArrayList<String[]> data= new ArrayList<>();
    


}





class post extends Thread{
    @Override 
       public void run(){
    
       for(int j=1;j<=threaddetails.numberofpostsperthread;j++){
            try {
                TestPost.testLiftRidePost();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    
    }
       }
    
    
    






class TestPost {


   
/// do get and do post for random gen data
    public static void testLiftRidePost() throws Exception {
        // int x = threaddetails.numberofpostsperthread*threaddetails.numberofthreads -10;
        threaddetails.counter=1+threaddetails.counter;

        if (threaddetails.counter<=8){


            long begin = System.currentTimeMillis(); 


        Integer minskierID=1;
        Integer maxskierID=100000;

        Random random = new Random();
        Integer skierID=random.nextInt(maxskierID-minskierID+1)+minskierID;

        Integer minresortID=1;
        Integer maxresortID=10;

        
        Integer resortID=random.nextInt(maxresortID-minresortID+1)+minresortID;
        
        Integer minliftID=1;
        Integer maxliftID=40;

        
        Integer liftID=random.nextInt(maxliftID-minliftID+1)+minliftID;


                
        Integer mintime=1;
        Integer maxtime=360;

        
        Integer time=random.nextInt(maxtime-mintime+1)+mintime;


        String jsonPayload = String.format("{\"time\": %d, \"liftID\": %d}", time,liftID);

        HttpClient httpClient = HttpClient.newHttpClient();


        String seasonID = "2022";
        String dayID = "1";

        URI uri = URI.create(String.format("http://168.138.69.171:8093/skiers/%d/seasons/%s/days/%s/skiers/%d", resortID,seasonID,dayID,skierID));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();
        String responseBody = response.body();

        long end = System.currentTimeMillis();
        
         long dt = end - begin;

        String[] row = {
            Long.toString(dt), Integer.toString(statusCode), responseBody 
        };

        threaddetails.data.add(row);


        
        
        // Write the data to a CSV file
        try {
            FileWriter writer = new FileWriter("data.csv");
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            for (String[] rowData : threaddetails.data) {
                bufferedWriter.write(String.join(",", rowData));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
            System.out.println("Data saved to data.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
 
          


        //System.out.println("Status code: " + statusCode + "Response body: " + responseBody+dt+"ms");
        

        

        }
        

       
		


	
	
}


}



class AudioClientTest{


    

    public static void main( String arg [] ) throws Exception{

        String[] heading = {
            "time taken","status code","responsebody"
        };
        
        threaddetails.data.add(heading);
         

   
        
        
ArrayList<post> list = new ArrayList<post>();

     for(int i=1;i<=threaddetails.numberofthreads;i++){  // 32 threads are created
        
   
     post posttest = new post();
     posttest.start();

     list.add(posttest);





     }

     int flag=0;

     while(true){

    
        for(int i=0;i<list.size();i++)    //check if thread is alive
        {
            if(list.get(i).isAlive()){
                continue;
            }
            else{
                flag=1;  // if not alive, break
                break;
            }
        }

        if(flag==1){
            break;  // break
        }

        
     }

     for(int i=1;i<=10;i++){  // creating extra threads
        
   
        post posttest = new post();
        posttest.start();
   
        list.add(posttest);
   
   
   
   
   
        }

        for(int i=0;i<list.size();i++){ //  join it to the main thread
        
   
            
            list.get(i).join();
       
       
       
       
       
            }



   


   
  

   

    
    


     



}






}
