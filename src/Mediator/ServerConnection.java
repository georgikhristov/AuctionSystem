package Mediator;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection implements Runnable
{
   private static final int PORT = 2910;
   private ServerModel model;

   public ServerConnection(ServerModel model)
   {
      this.model = model;
   }

   @Override
public void run()
   {
      int count = 1;
      try
      {
         System.out.println("Starting Server...");

         // create welcoming socket at specified port
         ServerSocket welcomeSocket = new ServerSocket(PORT);
         System.out.println("Waiting for a client...");
         while (true)
         {
           

            // Wait, on welcoming socket for contact by client
            Socket connectionSocket = welcomeSocket.accept();

            // Start a thread with the client communication
            ServerCommunication c = new ServerCommunication(
                  connectionSocket, model);
            new Thread(c, "Communication #" + count).start();
            count++;

         }
      }
      catch (Exception e)
      {
         System.out.println("Exception in connection to server: "
               + e.getMessage());
         // e.printStackTrace();
      }
   }

}