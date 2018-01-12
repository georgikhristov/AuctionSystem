package Mediator;

import java.io.ObjectInputStream;
import java.net.SocketException;


import java.util.Map;

public class ClientReceiver implements Runnable
{
   private ObjectInputStream inFromServer;
   private ClientModel model;

   public ClientReceiver(ObjectInputStream inFromServer, ClientModel model) 
   {
      this.inFromServer = inFromServer;
      this.model = model;
   }

   @Override
public void run()
   {
      try
      {
         while (true)
         {
        	
           Map obj =  (Map) inFromServer.readObject();
           ResponseHandler.processResponse(obj, ClientModelManager.getInstance());
         
           
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
