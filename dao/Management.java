package dao;

import Client.Client;
import Vehicle.Vehicle;
import dao.ClientDao.Client_StoreData_File;
import dao.ClientDao.Client_StoreData_Memory;
import dao.VehicleDao.Vehicle_StoreData_File;
import dao.VehicleDao.Vehicle_StoreData_Memory;

import java.util.ArrayList;

public class Management {

  Client_StoreData_Memory client_memory = new Client_StoreData_Memory();

  Vehicle_StoreData_Memory vehicle_memory = new Vehicle_StoreData_Memory();

  StoreData global_file_managment = new StoreData();

  String choice;


public void deposit(String name, float balance){

    if(balance < 1)
      System.out.println("fail: invalid value");

    else{
      int CliId = client_memory.searchCli(name);
      if(CliId != -1){
        Client updatedClient = client_memory.getClient(CliId);
        updatedClient.getAccount().setBalance(balance);
        client_memory.editClient(updatedClient, CliId);
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
    client_memory.editClient(clientsRenting.get(i), i);
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
  
  public void returnVehicle(String nome){  //IMPLEMENTAR DE MANEIRA QUE SÓ É POSSÍVEL DEVOLVER SE TODAS AS DIÁRIAS TIVEREM QUITADAS (CLIENTE COM SALDO POSITIVO) E VERIFICAR SE O CLIENTE TEM CARRO PARA DEVOLVER

    int clientId = client_memory.searchCli(nome);
    int vehicleId = vehicle_memory.searchVeic(client_memory.getClient(clientId).getRentedVehicle());

    if(clientId == -1)
      System.out.println("fail: client doesn't exists");
    if(vehicleId == -1)
      System.out.println("fail: vehicle doesn't exists");

    else{
      Client updatedClient = client_memory.getClient(clientId);
      updatedClient.setRentedVehicleId(-1);
      client_memory.editClient(updatedClient, clientId);

      Vehicle updatedVehicle = vehicle_memory.getVehicle(vehicleId);
      updatedVehicle.setIsRented(false);
      vehicle_memory.editVehicle(updatedVehicle, vehicleId);
    }  
    
  }
                         

  //searches for a new not rented vehicle, if not found client receives refund
  public void tow(String nome){
    
    int clientId = client_memory.searchCli(nome);
    int vehicleId = client_memory.getClient(clientId).getRentedVehicle();

    if(client_memory.getClient(clientId).getRentedVehicle() != -1){//if client is currently renting
      if(client_memory.getClient(clientId).getHasInsurance()){//if client has a tow plan
        float refund = vehicle_memory.getVehicle(vehicleId).getDailyRate();

        if(vehicle_memory.searchFreeVehicle() != -1){
          returnVehicle(nome);
          aluga(nome, vehicles.get(i).getId(),true);
        }

        else {
          Client updatedClient = client_memory.getClient(clientId);
          updatedClient.getAccount().setBalance(refund);
          updatedClient.setRentedVehicleId(-1);
          client_memory.editClient(updatedClient, clientId);
        }

        vehicle_memory.removeVeiculo(vehicleId);
        global_file_managment.updateIds(vehicles, clients);
      }
      else
        System.out.println("fail: client don't have tow plan");

    }
    else
      System.out.println("fail: client isn't renting a vehicle");
  }


  public void aluga(String nome, int idCarro, boolean isReboque){

    //IMPLEMENTAR COBRAR UMA DIARIA NA HORA DE ALUGAR
    int indiceCliente = searchCli(nome);
    boolean tipoOk = false;
    if(indiceCliente != -1){  //verifica se o cliente existe

      if(clients.get(indiceCliente).getRentedVehicle() == -1){  //verifica se o cliente já alugou algum veículo

        /*VERIFICAÇÃO DE COERENCIA DE CARTEIRA DE MOTORISTA*/
        if(clients.get(indiceCliente).getLicenseType().equals("A") && vehicles.get(idCarro).getType().equals("Moto")){
          tipoOk = true;

        }else if(clients.get(indiceCliente).getLicenseType().equals("B") && vehicles.get(idCarro).getType().equals("Carro")){
          tipoOk = true;

        }else if(clients.get(indiceCliente).getLicenseType().equals("AB")){
          tipoOk = true;
        }
        /*FIM VERIFICAÇÃO DE COERENCIA DE CARTEIRA*/
        if(tipoOk){

          int indiceVeic = searchVeic(idCarro);
          if(indiceVeic != -1){

            if(vehicles.get(idCarro).getIsRented() == false){  //verifica se o carro já está alugado

              if(clients.get(indiceCliente).getAccount().getBalance() >= vehicles.get(idCarro).getDailyRate()){ //verifica se o cliente tem saldo para pagar a primeira diaria
                vehicles.get(indiceVeic).setIsRented(true); //seta como true a varíavel que diz se o veículo está alugado
                clients.get(indiceCliente).setRentedVehicleId(idCarro); //guarda o id do carro no objeto cliente

                if(!isReboque)  //isReboque é um parâmetro utilizado para saber se a função reboque() chamou a função aluga(), o que previne que seja perguntado se o cliente deseja o seguro reboque (já que, se a função reboque chama aluga() é porquê o cliente já possui o seguro)
                  if(clients.get(indiceCliente).getHasInsurance() == false){
                    System.out.println("Deseja um seguro reboque por + 15 reais na diária?");
                    escolha = scanner.next();
                    System.out.println("Alugado com sucesso!");
                    if(escolha.equals("s") || escolha.equals("sim") || escolha.equals("S") || escolha.equals("Sim"))
                      clients.get(indiceCliente).setHasInsurance(true);
                  }

                mngData.updateVeic(vehicles);
                mngData.updateCli(clients);
              }

              else
                System.out.println("fail: saldo insuficiente para primeira diária");
            }
            else
              System.out.println("fail: carro já alugado");
          }
          else
            System.out.println("fail: carro inexistente");
        }
        else
          System.out.println("fail: tipo de carteira incoerente");
      }
      else
        System.out.println("fail: apenas um veículo por vez é permitido");
    }
    else
      System.out.println("fail: cliente inexistente");
  }

}