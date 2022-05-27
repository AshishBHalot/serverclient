import java.net.*;
import java.io.*;
public class Server {

      ServerSocket server;
      Socket socket;    //for object from client..

      BufferedReader br;
      PrintWriter out;

    public Server()    //Constructor..
    {        
            
        try{
        server=new ServerSocket(7777);  //to speceify the socket to run the prgm bc there is also other pgms running at time
        System.out.println(" Server is ready to accept connection");
        System.out.println("waiting....");
        socket=server.accept();            //accept connection from socket & stores it's object

        br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out=new PrintWriter(socket.getOutputStream());

        startReading();
        startWriting();

    }
        catch (Exception e) {
            e.printStackTrace();  //?????? any problem should print on console
        }
    }


    // reading and writing again and again at same time so we have to use cocept multithreading
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
                    System.out.println(" Client terminated the chat");
                    socket.close();
                    break;
                }

                System.out.println("Client : "+msg);
            } }catch(Exception e)
            {
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
                

                      }
                    }catch(Exception e)
                    {
                       e.printStackTrace();
                    }

        };

        new Thread(r2).start();


    }

    public static void main(String[] args)
    {
        System.out.println(" thid is server...goint to launch");
        new Server();
    }
    
}
