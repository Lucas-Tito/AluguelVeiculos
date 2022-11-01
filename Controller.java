import Client.Client;
import Vehicle.Vehicle;
import dao.StoreData;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
  ArrayList<Client> clients = new ArrayList<>();
  ArrayList<Vehicle> vehicles = new ArrayList<>();
  StoreData mngData = new StoreData();
  File fileCli = new File("clients.txt");
  File fileVeic = new File("vehicles.txt");
  int nextVehicleId = 0;
  String escolha;

  Scanner scanner = new Scanner(System.in);
  // File fileCli = new File("clientes.txt");
  // File fileVeic = new File("veiculos.txt");

  public ArrayList<Client> getClients(){
    return clients;
  }
  public ArrayList<Vehicle> getVeiculos(){
    return vehicles;
  }
  
        /* FUNÇÕES DE CLIENTE */
  //se o cliente não existir e não for de menor
  //a conta faz parte dos atributos de clientToAdd
  //adiciona o cliente no arrayList de clientes
  public void addCliente(Client clientToAdd){
    

      
  }

  public void editCliente(){


          
  }


  public void listCli(){

  }

  //Remove o veículo que corresponder ao id contido em idToRemove  
  public void removeVeiculo(int idToRemove){



  }

  public void listVeic(){



}
        

public void depositar(String nome, float saldo){

  if(saldo < 1)
    System.out.println("fail: valor inválido");
  else{
    int indiceCli = searchCli(nome);
    if(indiceCli != -1){
      clients.get(indiceCli).getAccount().setBalance(saldo);
      mngData.updateCli(clients);
    }
    else
      System.out.println("fail: cliente inexistente");
  }

}

//CONVERSAR SOBRE IMPLEMENTAR DE MANEIRA QUE SEJA POSSÍVEL FICAR COM CONTA NEGATIVA
public void diariaUpdate(){ 

  for(int i = 0; i < clients.size(); i++)
    if(clients.get(i).getRentedVehicle() != -1){ //acha os clientes que já alugaram
      int indiceVeic = searchVeic(clients.get(i).getRentedVehicle());

      if(escolha.equals("s"))
        clients.get(i).getAccount().setBalance(-15);

      clients.get(i).getAccount().setBalance(vehicles.get(indiceVeic).getDailyRate()*-1);   //realiza a cobrança
      mngData.updateCli(clients);
    }
 }


  public int searchCli(String nomeToSearch){  



  }

  public int searchVeic(int idToSearch){  



  }

  public void listAlugados (){  // implementar: SE NÃO ACHOU NENHUM PRINTAR ERRO
    String show = ""; 
    
    for(int i = 0; i < clients.size(); i++){
      if(i != vehicles.size())
        show += "-";
      if(clients.get(i).getRentedVehicle() != -1){  //acha os clientes que já alugaram
        show += clients.get(i).getName() + " [" + "id do veículo: " + clients.get(i).getRentedVehicle();

      for(int j = 0; j < vehicles.size(); j++)
        if(vehicles.get(j).getId() == clients.get(i).getRentedVehicle())
          show += " | nome do veículo: " + vehicles.get(i).getName() + "]";

      show +="\n";
      }
    }
      
    System.out.println(show);
  }
  
  public void devolve(String nome){  //IMPLEMENTAR DE MANEIRA QUE SÓ É POSSÍVEL DEVOLVER SE TODAS AS DIÁRIAS TIVEREM QUITADAS (CLIENTE COM SALDO POSITIVO) E VERIFICAR SE O CLIENTE TEM CARRO PARA DEVOLVER

    int indiceCli = searchCli(nome);  
    int indiceVeic = searchVeic(clients.get(indiceCli).getRentedVehicle());

    if(indiceCli == -1)
      System.out.println("fail: cliente inexistente");
    if(indiceVeic == -1)
      System.out.println("fail: veículo inexistente");
    else{
    clients.get(indiceCli).setRentedVehicleId(-1);
    vehicles.get(indiceVeic).setIsRented(false);
    mngData.updateCli(clients);
    mngData.updateVeic(vehicles);
    }  
    
  }
                         
  public int nextId (){
    

  }
  
public void reboque(String nome){
    
    int indiceCli = searchCli(nome);
    int idVeic = clients.get(indiceCli).getRentedVehicle();
    boolean carWasFound = false;

    if(clients.get(indiceCli).getRentedVehicle() != -1){//se o cliente já alugou algum veículo

      if(clients.get(indiceCli).getHasInsurance() == true){// se o cliente possui plano de reboque

        float ressarcimento = vehicles.get(idVeic).getDailyRate();

        for(int i = 0; i < vehicles.size(); i++)
          if(vehicles.get(i).getIsRented() == false && vehicles.get(i).getId() != idVeic){
            //*IMPLEMENTAR VERIFICAÇÃO SE O VEÍCULO É COMPATÍVEL COM A CARTEIRA DO CLIENTE
            carWasFound = true;
            devolve(nome);
            aluga(nome, vehicles.get(i).getId(),true);

            mngData.updateCli(clients);
            mngData.updateVeic(vehicles);
            break;
          }


        if(!carWasFound){
          clients.get(searchCli(nome)).getAccount().setBalance(ressarcimento);
          clients.get(indiceCli).setRentedVehicleId(-1);
          mngData.updateCli(clients);
          mngData.updateVeic(vehicles);
        }

        removeVeiculo(idVeic);
        mngData.updateIds(vehicles, clients);
      }
      else{
        System.out.println("fail: cliente não possui plano de reboque");
      }
    }
    else
      System.out.println("fail: cliente não possui veículo");
  }
}