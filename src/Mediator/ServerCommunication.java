package Mediator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;


public class ServerCommunication implements Runnable
{
   private ObjectInputStream inFromClient;
   private ObjectOutputStream outToClient;
   private ServerModel model;

   public ServerCommunication(Socket clientSocket, ServerModel model)
         throws IOException
   {
      this.model = model;

      outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
      inFromClient = new ObjectInputStream(clientSocket.getInputStream());
   }
   
   @Override
public void run()
   {
	   System.out.println("Client Has Connectd");
      try
      {
         

         while (true)
         {
        	Map map=(Map) inFromClient.readObject();
        	Map tmp = RequestHandler.processReq(map,model);
            outToClient.writeObject(tmp);
  
         }
      }
      catch (SocketException e)
      {
         // ok
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

   
   

}
