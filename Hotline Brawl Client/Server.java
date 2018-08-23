import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
 ServerSocket serverSock;
 PrintWriter output;
 BufferedReader input;
 
 public void go(){
  System.out.println("Waiting for client connection...");
  try {
   serverSock=new ServerSocket(5000);
   Socket client = serverSock.accept();
   output = new PrintWriter(client.getOutputStream());
   InputStreamReader stream = new InputStreamReader(client.getInputStream());
   input = new BufferedReader(stream);
  } catch (Exception e){
   System.out.println("Error accepting connection");
   e.printStackTrace();
  }

  System.out.println("Client Connected");

  String msg;
  try{
   msg=input.readLine();
   System.out.println("msg from client: " + msg);
  } catch (Exception e){
   System.out.println("Failed to close socket");
  }
 }

}
 
 

