package dao;

import Client.Client;
import Vehicle.Bike;
import Vehicle.Car;
import Vehicle.Vehicle;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.io.File;



public class StoreData{

  File fileCli = new File("Client/clientes.txt");
  File fileVeic = new File("Vehicle/veiculos.txt");



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



  public boolean idExists (int idToSearch){
    
      boolean idExists = false;
  
      try (
            /* now create a Scanner object to wrap around carFile
            this allows us to user high-level functions such as nextLine */
            Scanner veicStream = new Scanner(fileVeic);
              )
        {
          while(veicStream.hasNext()) { 
  
            int id = veicStream.nextInt();
            String tipo = veicStream.nextLine();
            String nome = veicStream.nextLine();
            String diaria = veicStream.nextLine();
            String alugado = veicStream.nextLine();
  
            if(id == idToSearch){
              idExists = true;
              break;
            }             
  
            veicStream.nextLine(); // clear buffer before next readLine
            }
          }
          catch(InputMismatchException e){
            System.out.println("Invalid Input");
          }
          catch(FileNotFoundException e){
            //System.out.println("\nAviso: Não há dados de vehicle");
          }
  
      return idExists;
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


    public void VeicWriter(ArrayList<Vehicle> veicsToWrite){
    for(Vehicle v : veicsToWrite){
      
      try (
            FileWriter fstream = 
            new FileWriter(fileVeic, StandardCharsets.UTF_8, true);
            PrintWriter outputFile = 
            new PrintWriter(fstream, true); // using autoflushing
          )
        {              
          if(!idExists(v.getId())){
            outputFile.println(v.getId());
            outputFile.println(v.getType());
            outputFile.println(v.getName());
            outputFile.println(v.getDailyRate());
            outputFile.println(v.getIsRented());
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


  public void updateVeic(ArrayList<Vehicle> veicsToUpdate){

    File novo = new File("novo.txt");
    for(Vehicle c : veicsToUpdate){
           
      try(
            FileWriter fstream = 
            new FileWriter(novo, StandardCharsets.UTF_8, true);
            PrintWriter outputFile = 
              new PrintWriter(fstream, true); // using autoflushing
          )
        {          
          outputFile.println(c.getId());
          outputFile.println(c.getType());
          outputFile.println(c.getName());
          outputFile.println(c.getDailyRate());
          outputFile.println(c.getIsRented());
        }
        catch(InputMismatchException e) {
          System.err.println("Invalid input: " + e.getMessage());
        }
        catch(IOException e) {
          System.out.println("Error opening the file: " + e.getMessage());
        }
    }
    fileVeic.delete();
    novo.renameTo(new File("Vehicle/veiculos.txt"));
    
  }
 


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

  }


  public void fileDataToArray(ArrayList<Client> clientesIn, ArrayList<Vehicle> veiculosIn){//arquivo pro array
    File fileCli = new File("Client/clientes.txt");

    try (Scanner clientStream = new Scanner(fileCli);){
          while(clientStream.hasNext()) { 
            String nome = clientStream.next();
            int idade = clientStream.nextInt();
            String tipo = clientStream.next();
            int idCarro = clientStream.nextInt();
            boolean hasReboque = clientStream.nextBoolean();
            String saldo = clientStream.next();           
            
            clientesIn.add(new Client(nome, idade, tipo, idCarro, hasReboque, Float.valueOf(saldo)));
            clientStream.nextLine(); // clear buffer before next readLine
          }
    }
        catch(InputMismatchException e) {
          System.out.println("Invalid Input");
        }
        catch(FileNotFoundException e) {
          System.out.println("\nAviso: Não há dados de cliente");
        }
        
        File fileVeic = new File("Vehicle/veiculos.txt");

    try (Scanner veicStream = new Scanner(fileVeic);){

      while(veicStream.hasNext()) { 
        int id = veicStream.nextInt();
        String tipo = veicStream.next();
        String nome = veicStream.next();
        String diaria = veicStream.next();
        boolean alugado = veicStream.nextBoolean();           
            
        if(tipo.equals("moto")|| tipo.equals("Moto"))
          veiculosIn.add(new Bike(id, nome, Float.valueOf(diaria), alugado));
        else
          veiculosIn.add(new Car(id, nome, Float.valueOf(diaria), alugado));

          veicStream.nextLine(); // clear buffer before next readLine
      }
    }
    catch(InputMismatchException e){
      System.out.println("Invalid Input");
    }
    catch(FileNotFoundException e){
      System.out.println("\nAviso: Não há dados de veículo");
    }
  }
}


