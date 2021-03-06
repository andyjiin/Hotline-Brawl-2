import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {

 Socket mySocket; //socket for connection
 BufferedReader input;
 PrintWriter output;

 public void go(){
  System.out.println("Attempting to make connection...");

  try{
   mySocket = new Socket ("127.0.0.1",5000);
   InputStreamReader stream = new InputStreamReader(mySocket.getInputStream());
   input = new BufferedReader(stream);
   output = new PrintWriter(mySocket.getOutputStream());

  } catch (IOException e){
   System.out.println("Connection to Server Failed");
   e.printStackTrace();
  }

  System.out.println("Connection made.");
  String msg;

  try {   
   msg = input.readLine();  
   System.out.println("msg from server: " + msg); 
  }catch (IOException e) { 
   System.out.println("Failed to receive msg from the server");
   e.printStackTrace();
  }
  // transmit a message to the server
  msg = "HelloWorld!";
  System.out.println("Sending message to server: " + msg);
  output.println(msg);
  output.flush();

  try {
   input.close();
   output.close();
   mySocket.close();
  }catch (Exception e) { 
   System.out.println("Failed to close socket");
  }
 }

}


