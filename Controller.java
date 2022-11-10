import Client.Client;
import Vehicle.Vehicle;
import dao.ClientDao.Client_StoreData_Memory;
import dao.Management;
import dao.VehicleDao.Vehicle_StoreData_Memory;

public class Controller {
  Management management = new Management();
  Client_StoreData_Memory client_memory = new Client_StoreData_Memory();
  Vehicle_StoreData_Memory vehicle_memory = new Vehicle_StoreData_Memory();
  StoreData mngData = new StoreData();

  public void addClient(Client clientToAdd){
    client_memory.addClient(clientToAdd);
  }

  public void removeClient(String clientToRemove){
    client_memory.removeClient(clientToRemove);
  }

  public void listClients(){
    client_memory.listClients();
  }

  public void updateClient(Client updatedClient, int clientId){
    client_memory.updateClient(updatedClient, clientId);
  }





  public void listVehicles(){
    vehicle_memory.listVehicles();
  }

  public void removeVehicle(int vehicleId){
    vehicle_memory.removeVehicle(vehicleId);
  }

  public void addVehicle(String type, String name, float dailyRate){
    vehicle_memory.addVehicle(type, name, dailyRate);
  }

  public void updateVehicle(Vehicle updateVehicle, int vehicleId){
    vehicle_memory.updateVehicle(updateVehicle, vehicleId);
  }





  public void rent(String clientName, int carId, boolean hasTowPlan){
    management.rent(clientName, carId, hasTowPlan);
  }

  public void deposit(String clientName, float balance){
    management.deposit(clientName, balance);
  }

  public void dailyRateUpdate(){
    management.dailyRateUpdate();
  }

  public void returnVehicle(String clientName){
    management.returnVehicle(clientName);
  }

  public void tow(String clientName){
    management.tow(clientName);
  }

  public void listClientsRenting(){
    management.listClientsRenting();
  }



  public void fileDataToMemory(){
    //mngData.fileDataToMemory();
  }
}