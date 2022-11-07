package dao;

import Client.Client;
import Vehicle.Vehicle;
import dao.ClientDao.Client_StoreData_Memory;
import dao.VehicleDao.Vehicle_StoreData_Memory;

import java.util.ArrayList;
import java.util.Scanner;

public class Management {
  Scanner scanner = new Scanner(System.in);
  Client_StoreData_Memory client_memory = new Client_StoreData_Memory();

  Vehicle_StoreData_Memory vehicle_memory = new Vehicle_StoreData_Memory();

  StoreData global_file_management = new StoreData();

  String choice;


public void deposit(String clientName, float balance){

    if(balance < 1)
      System.out.println("fail: invalid value");

    else{
      int CliId = client_memory.searchCli(clientName);
      if(CliId != -1){
        Client updatedClient = client_memory.getClient(CliId);
        updatedClient.getAccount().setBalance(balance);
        client_memory.updateClient(updatedClient, CliId);
    }
    else
      System.out.println("fail: client doesn't exist");
    }

}

//CONVERSAR SOBRE IMPLEMENTAR DE MANEIRA QUE SEJA POSSÍVEL FICAR COM CONTA NEGATIVA
public void dailyRateUpdate(){

  ArrayList<Client> clientsRenting = client_memory.getClientsRenting();

  for(int i = 0; i < clientsRenting.size(); i++){
    int indexRentedVehicle = vehicle_memory.searchVeic(clientsRenting.get(i).getRentedVehicle());

    if(choice.equals("s"))
      clientsRenting.get(i).getAccount().setBalance(-15);

    clientsRenting.get(i).getAccount().setBalance(vehicle_memory.getVehicle(indexRentedVehicle).getDailyRate()*-1);   //realiza a cobrança
    client_memory.updateClient(clientsRenting.get(i), i);
  }

 }


  public void listClientsRenting(){  // implementar: SE NÃO ACHOU NENHUM PRINTAR ERRO
    String show = "";

    ArrayList<Client> clientsRenting = client_memory.getClientsRenting();

    for(int i = 0; i < clientsRenting.size(); i++){
      show += "-";
      show += clientsRenting.get(i).getName() + " [" + "vehicle id: " + clientsRenting.get(i).getRentedVehicle();

      show += " | vehicle name: " + vehicle_memory.getVehicle(clientsRenting.get(i).getRentedVehicle()).getName() + "]";

      show +="\n";
      }
      
    System.out.println(show);
  }
  
  public void returnVehicle(String clientName){  //IMPLEMENTAR DE MANEIRA QUE SÓ É POSSÍVEL DEVOLVER SE TODAS AS DIÁRIAS TIVEREM QUITADAS (CLIENTE COM SALDO POSITIVO) E VERIFICAR SE O CLIENTE TEM CARRO PARA DEVOLVER

    int clientId = client_memory.searchCli(clientName);
    int vehicleId = vehicle_memory.searchVeic(client_memory.getClient(clientId).getRentedVehicle());

    if(clientId == -1)
      System.out.println("fail: client doesn't exists");
    if(vehicleId == -1)
      System.out.println("fail: vehicle doesn't exists");

    else{
      Client updatedClient = client_memory.getClient(clientId);
      updatedClient.setRentedVehicleId(-1);
      client_memory.updateClient(updatedClient, clientId);

      Vehicle updatedVehicle = vehicle_memory.getVehicle(vehicleId);
      updatedVehicle.setIsRented(false);
      vehicle_memory.updateVehicle(updatedVehicle, vehicleId);
    }  
    
  }
                         

  //searches for a new not rented vehicle, if not found client receives refund
  public void tow(String clientName){
    
    int clientId = client_memory.searchCli(clientName);
    int vehicleId = client_memory.getClient(clientId).getRentedVehicle();

    if(client_memory.getClient(clientId).getRentedVehicle() != -1){//if client is currently renting
      if(client_memory.getClient(clientId).getHasInsurance()){//if client has a tow plan
        float refund = vehicle_memory.getVehicle(vehicleId).getDailyRate();

        int newVehicleID = vehicle_memory.searchFreeVehicle();
        if(newVehicleID != -1){
          returnVehicle(clientName);
          rent(clientName,newVehicleID,true);
        }

        else {
          Client updatedClient = client_memory.getClient(clientId);
          updatedClient.getAccount().setBalance(refund);
          updatedClient.setRentedVehicleId(-1);
          client_memory.updateClient(updatedClient, clientId);
        }

        vehicle_memory.removeVehicle(vehicleId);
        //Try not to use getAll
        global_file_management.updateIds(vehicle_memory.getVeiculos(), client_memory.getClients());
      }
      else
        System.out.println("fail: client don't have tow plan");

    }
    else
      System.out.println("fail: client isn't renting a vehicle");
  }


  public void rent(String clientName, int carId, boolean hasTowPlan){

    //IMPLEMENT DAILY RATE CHARGE WHEN RENTING
    int clientId = client_memory.searchCli(clientName);
    boolean driverLicenseOk = false;

    if(clientId != -1){  //verify if client exists
      String clientDriverLicense = client_memory.getClient(clientId).getLicenseType();
      String vehicleType = vehicle_memory.getVehicle(carId).getType();

      if(client_memory.getClient(clientId).getRentedVehicle() == -1){  //verify if client already has a vehicle

        /*DRIVER LICENSE VERIFICATIONS*/
        if(clientDriverLicense.equals("A") && vehicleType.equals("Moto")){
          driverLicenseOk = true;

        }else if(clientDriverLicense.equals("B") && vehicleType.equals("Carro")){
          driverLicenseOk = true;

        }else if(clientDriverLicense.equals("AB")){
          driverLicenseOk = true;
        }
        /*end of DRIVER LICENSE VERIFICATIONS*/

        if(driverLicenseOk){

          int vehicleId = vehicle_memory.searchVeic(carId);
          if(vehicleId != -1){
            Vehicle vehicleToRent = vehicle_memory.getVehicle(carId);
            Client clientRenting = client_memory.getClient(clientId);

            if(!vehicleToRent.getIsRented()){  //verify if car is already rented
              //verify if client has balance to pay first daily rate
              if(clientRenting.getAccount().getBalance() >= vehicleToRent.getDailyRate()){
                vehicleToRent.setIsRented(true);
                clientRenting.setRentedVehicleId(carId);

                if(!hasTowPlan)  //isReboque é um parâmetro utilizado para saber se a função reboque() chamou a função aluga(), o que previne que seja perguntado se o cliente deseja o seguro reboque (já que, se a função reboque chama aluga() é porquê o cliente já possui o seguro)
                  if(!clientRenting.getHasInsurance()){
                    System.out.println("Do you want a town plan for plus 15 on daily rate?");
                    choice = scanner.next();
                    System.out.println("Rented successfully!");
                    if(choice.equals("s") || choice.equals("sim") || choice.equals("S") || choice.equals("Sim"))
                      clientRenting.setHasInsurance(true);
                  }

                client_memory.updateClient(clientRenting, clientId);
                vehicle_memory.updateVehicle(vehicleToRent, vehicleId);
              }

              else
                System.out.println("fail: not enough balance for the daily rate");
            }
            else
              System.out.println("fail: car already taken");
          }
          else
            System.out.println("fail: car doesn't exists");
        }
        else
          System.out.println("fail: invalid driver license");
      }
      else
        System.out.println("fail: client already renting");
    }
    else
      System.out.println("fail: client doesn't exists");
  }

}