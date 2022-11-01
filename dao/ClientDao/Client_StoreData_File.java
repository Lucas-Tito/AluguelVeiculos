package dao.ClientDao;

import Client.Client;
import Vehicle.Bike;
import Vehicle.Car;
import Vehicle.Vehicle;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Client_StoreData_File {

  File fileCli = new File("Client/clientes.txt");

   public boolean nameExists (String nomeToSearch){
    
    boolean nameExists = false;

    try (
          /* now create a Scanner object to wrap around carFile
          this allows us to user high-level functions such as nextLine */
          Scanner clientStream = new Scanner(fileCli);
        )
      {
        while(clientStream.hasNext()) { 
        String nome = clientStream.next();
        String idade = clientStream.next();
        String tipo = clientStream.next();
        String idCarro = clientStream.next();
        String hasReboque = clientStream.next();
        String saldo = clientStream.next(); 

        if(nome.equals(nomeToSearch)){
          nameExists = true;
            break;
        }             
      clientStream.nextLine(); // clear buffer before next readLine
        }
      }
      catch(InputMismatchException e){
        System.out.println("Invalid Input");
      }
      catch(FileNotFoundException e){
        //System.out.println("\nAviso: Não há dados de cliente");
      }

    return nameExists;
  }



  public void CliWriter(ArrayList<Client> clientesToWrite){

    for(Client c : clientesToWrite){
      try (
          FileWriter fstream = new FileWriter(fileCli, StandardCharsets.UTF_8, true);
          PrintWriter outputFile = new PrintWriter(fstream, true); // using autoflushing
          )
        {
          if(!nameExists(c.getName())){
              
            outputFile.println(c.getName());
            outputFile.println(c.getAge());
            outputFile.println(c.getLicenseType());
            outputFile.println(c.getRentedVehicle());
            outputFile.println(c.getHasInsurance());
            outputFile.println(c.getAccount().getBalance());
              
          }       
        }
        catch(InputMismatchException e) {
          System.err.println("Invalid input: " + e.getMessage());
        }
        catch(IOException e) {
          System.out.println("Error opening the file: " + e.getMessage());
        }
    }

  }



  public void updateCli(ArrayList<Client> clientesToUpdate){
    File novo = new File("novo.txt");
    for(Client c : clientesToUpdate){
           
    try (
          FileWriter fstream = 
          new FileWriter(novo, StandardCharsets.UTF_8, true);
          PrintWriter outputFile = 
          new PrintWriter(fstream, true); // using autoflushing
        )
        {        
          outputFile.println(c.getName());
          outputFile.println(c.getAge());
          outputFile.println(c.getLicenseType());
          outputFile.println(c.getRentedVehicle());
          outputFile.println(c.getHasInsurance());
          outputFile.println(c.getAccount().getBalance());
        }
        catch(InputMismatchException e) {
          System.err.println("Invalid input: " + e.getMessage());
        }
        catch(IOException e) {
          System.out.println("Error opening the file: " + e.getMessage());
        }
    }
    fileCli.delete();
    novo.renameTo(new File("Client/clientes.txt"));
  }

 

/*  //this is a global method
  public void updateIds(ArrayList<Vehicle> vehicles, ArrayList<Client> clientes){
    boolean cliModified = false;
    boolean veicModified = false;

    for(int i = 0; i< vehicles.size(); i++){

      if(vehicles.get(i).getIsRented() == true){
        for(int j=0; j<clientes.size(); j++){
          if(clientes.get(i).getRentedVehicle() == vehicles.get(i).getId()){
            cliModified = true;
            veicModified = true;
            clientes.get(i).setRentedVehicleId(i);
            vehicles.get(i).setId(i);
          }
        }
      }
      else{
        cliModified = true;
        vehicles.get(i).setId(i);
      }

    }
    if(cliModified)
      updateCli(clientes);
    if(veicModified)
     updateVeic(vehicles);

  }*/

  //arquivo pro array
  public void fileDataToArray(ArrayList<Client> clientsIn){
    File fileCli = new File("Client/clientes.txt");

    try (Scanner clientStream = new Scanner(fileCli);){
          while(clientStream.hasNext()) { 
            String nome = clientStream.next();
            int idade = clientStream.nextInt();
            String tipo = clientStream.next();
            int idCarro = clientStream.nextInt();
            boolean hasReboque = clientStream.nextBoolean();
            String saldo = clientStream.next();           
            
            clientsIn.add(new Client(nome, idade, tipo, idCarro, hasReboque, Float.valueOf(saldo)));
            clientStream.nextLine(); // clear buffer before next readLine
          }
    }
        catch(InputMismatchException e) {
          System.out.println("Invalid Input");
        }
        catch(FileNotFoundException e) {
          System.out.println("\nAviso: Não há dados de cliente");
        }
  }
}


