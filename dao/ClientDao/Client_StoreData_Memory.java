package dao.ClientDao;

import Client.Client;
import Vehicle.Vehicle;
import dao.StoreData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client_StoreData_Memory {
  ArrayList<Client> clients = new ArrayList<>();
  StoreData mngData = new StoreData();

  Scanner scanner = new Scanner(System.in);
  // File fileCli = new File("clientes.txt");
  // File fileVeic = new File("veiculos.txt");

  public ArrayList<Client> getClients(){
    return clients;
  }

  //se o cliente não existir e não for de menor
  //a conta faz parte dos atributos de clientToAdd
  //adiciona o cliente no arrayList de clientes
  public void addCliente(Client clientToAdd){
    
    boolean letrasAceitas = clientToAdd.getLicenseType().equals("a") ||
            clientToAdd.getLicenseType().equals("A") ||
            clientToAdd.getLicenseType().equals("b") ||
            clientToAdd.getLicenseType().equals("B") ||
            clientToAdd.getLicenseType().equals("ab") ||
            clientToAdd.getLicenseType().equals("AB");
    
    if(mngData.nameExists(clientToAdd.getName()))
      System.out.println("fail: cliente já cadastrado");

    else if(clientToAdd.getAge() < 18)
      System.out.println("fail: menores não podem pilotar");
    
    else if(!letrasAceitas)
      System.out.println("fail: tipo de carteira inválido");

    else{
      clientToAdd.setLicenseType(clientToAdd.getLicenseType().toUpperCase()); //padroniza o tipo como maiúsculo
      clients.add(clientToAdd);
      mngData.CliWriter(clients);//escreve o cliente no txt
    }
} 
  //Remove o primeiro cliente com nome igual ao contido em clientToRemove  
  public void removeCliente(String clientToRemove){

    int indiceCli = searchCli(clientToRemove);
    if(indiceCli != -1){

      clients.remove(indiceCli);
      System.out.println("Removido com sucesso!");
      mngData.updateCli(clients);  //deixa o txt de clientes igual ao vetor
      
    }
    else
      System.out.println("fail: cliente inexistente");
      
  }


  public Client getClient(int index){
    return clients.get(index);
  }

  public void editClient(Client updatedClient, int indexToUpdate){

    clients.set(indexToUpdate, updatedClient);
    mngData.updateCli(clients);

  }

  public ArrayList<Client> getClientsRenting(){

    ArrayList<Client> clientsRenting = new ArrayList<>();

    for(int i = 0; i < clients.size(); i++)
      if(clients.get(i).getRentedVehicle() != -1) //finds clients that are renting currently
        clientsRenting.add(clients.get(i));

    return clientsRenting;
  }


  //INTERFACE NOJENTA
  public void editCliente(){

    //Printa as perguntas para o usuário
    //Verifica se o cliente escolhido existe
    //modifica o atributo escolhido
    System.out.println("Qual cliente você deseja editar?");
    String nomeToVerify = scanner.nextLine();
    int indiceCli = searchCli(nomeToVerify);
    if(indiceCli != -1){
      
      System.out.println("Escolha o campo que deseja alterar:\n[1]Nome\n[2]Idade\n[3]TipoCarteira");
      int opcao = scanner.nextInt();
      String espaco = scanner.nextLine(); //solução para scanner anterior não captar o enter

      boolean inputIsValid = false; //varíavel para saber se a opção escolhida é válida    
      if(opcao == 1){
        
        inputIsValid = true;
        System.out.print("Digite o novo nome: ");
        String nomeNovo = scanner.nextLine();
        clients.get(indiceCli).setName(nomeNovo);
        mngData.updateCli(clients);
      }
      else if(opcao == 2){

        inputIsValid = true;    
        System.out.print("Digite a nova idade: ");
        int idadeToEdit = scanner.nextInt();
        clients.get(indiceCli).setAge(idadeToEdit);
        mngData.updateCli(clients);

      }
      else if(opcao == 3){

        inputIsValid = true;    
        System.out.print("Digite o novo tipoCarteira: ");
        String tipoNovo = scanner.nextLine();   
        clients.get(indiceCli).setLicenseType(tipoNovo);
        mngData.updateCli(clients);
      }
      else
        System.out.println("fail: input inválido");    
          
      if(inputIsValid)
        System.out.println("Alterado com sucesso!");   
    }   
    else
      System.out.println("fail: cliente não existe"); 
          
  }


  public void listCli(){  //lista os clientes
    for(int i = 0; i < clients.size(); i++)
      System.out.println(clients.get(i));
  }




  public int searchCli(String nomeToSearch){  

    int indiceCliente = -1; //é igual a -1 quando não encontrado
  
    for(int i = 0; i < clients.size(); i++)
      if(nomeToSearch.equals(clients.get(i).getName())){
        indiceCliente = i;
        break;
    }
    
    return indiceCliente;

  }



}