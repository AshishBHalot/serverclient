import java.net.*;
import java.io.*;
public class Client {
                  
    Socket socket;
     
    BufferedReader br;
    PrintWriter out;

    public Client(){
        try{
            System.out.println(" Sending request to server");
            socket=new Socket("127.0.0.1",7777);  //ip adress if another computer and port
            System.out.println("connection done");


            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream());
    
            startReading();
            startWriting();

              



        }catch(Exception e){

        }

    }

    public void startReading() 
    {
         Runnable r1=()->    // here lamda exp with runnable class used for threading to read data
         {
             
            System.out.println("reader started ...");
            try{
                    while(true)    // for reading data again and again
               {   
                String msg= br.readLine();
                if(msg.equals("exit"))
                {
                    System.out.println(" Server terminated the chat");
                    break;
                }

                System.out.println("Server  : "+msg);
            
                }
            }catch(Exception e){
                e.printStackTrace();
                 }
                
            

         };
         new Thread(r1).start();

    }

    public void startWriting()
    {
        Runnable r2=()->     //thread to read and write to client
        {  
            System.out.println("writer started ..");
            try{
            while(true)
            {
                  
                          
                       BufferedReader br1=new BufferedReader( new InputStreamReader(System.in));// to read data from keyboard
                       String content= br1.readLine();
                       out.println(content);
                       out.flush();  //sometime data not sent

                       if(content.equals("end"))
                       {
                          socket.close();
                          break;
                       }

                      
            }}catch(Exception e)
            {
                e.printStackTrace();
            }

        };

        new Thread(r2).start();


    }
    public static void main(String[] args)
    {    

        System.out.println(" thid is Client...goint to launch");

        new Client();
    }
    
}
    
