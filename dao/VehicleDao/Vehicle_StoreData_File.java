package dao.VehicleDao;

import Client.Client;
import Vehicle.Bike;
import Vehicle.Car;
import Vehicle.Vehicle;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Vehicle_StoreData_File {

  File fileVeic = new File("Vehicle/veiculos.txt");


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
            //System.out.println("\nAviso: Não há dados de veículo");
          }
  
      return idExists;
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


  public void fileDataToArray(ArrayList<Client> clientesIn, ArrayList<Vehicle> veiculosIn){//arquivo pro array

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


