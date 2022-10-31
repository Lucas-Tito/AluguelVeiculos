package dao;

import Client.Client;
import Vehicle.Bike;
import Vehicle.Car;
import Vehicle.Vehicle;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.io.File;

public class Management {
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

              /* FUNÇÕES DE VEÍCULO */
  //como é normal uma locadora ter o mesmo veículo
  //não será feita uma verificação que compara veículos
  //a diferenciação será dada a partir dos ids que são incrementados automaticamente  
  public void addVeiculo(String tipoToAdd, String nomeToAdd, float diariaToAdd){

    int veichileId = nextId();  //chama função que retorna o último id incrementado
    
    //escreve veículo no txt se o tipo passado for válido
    if(tipoToAdd.equals("moto") || tipoToAdd.equals("Moto")){
      vehicles.add(new Bike(veichileId, nomeToAdd, diariaToAdd));
      mngData.VeicWriter(vehicles);
   } 
    else if(tipoToAdd.equals("carro") || tipoToAdd.equals("Carro")){
      vehicles.add(new Car(veichileId, nomeToAdd, diariaToAdd));
      mngData.VeicWriter(vehicles);
    }
    else
      System.out.println("fail: tipo inválido");

  }

  //Remove o veículo que corresponder ao id contido em idToRemove  
  public void removeVeiculo(int idToRemove){

    int indiceVeic = searchVeic(idToRemove);
    if(indiceVeic != -1){
      vehicles.remove(indiceVeic);
      System.out.println("Removido com sucesso!");
      mngData.updateVeic(vehicles);
    }
    else
      System.out.println("fail: veículo inexistente");       
      
  }

  public void editVeiculo(){

    //Printa as perguntas para o usuário
    //Verifica se o veículo escolhido existe
    //modifica o atributo escolhido
    System.out.println("Qual o Id do veículo que você deseja editar?");
    int idToVerify = scanner.nextInt();
    int indiceVeic = searchVeic(idToVerify);
    if(indiceVeic != -1){   
      
      System.out.println("Escolha o campo que deseja alterar:\n[1]Nome\n[2]Diaria");
      int opcao = scanner.nextInt();
      String espaco = scanner.nextLine(); //solução para scanner anterior não captar o enter

      boolean inputIsValid = false; //varíavel para saber se a opção escolhida é válida    
      if(opcao == 1){

        inputIsValid = true;      
        System.out.print("Digite o novo nome: ");
        String nomeNovo = scanner.nextLine();
        vehicles.get(indiceVeic).setName(nomeNovo);
        mngData.updateVeic(vehicles);

      }
      else if(opcao == 2){
            
        inputIsValid = true;
        System.out.print("Digite a nova diaria: ");
        int novaDiaria = scanner.nextInt();
        vehicles.get(indiceVeic).setDailyRate(novaDiaria);
        mngData.updateVeic(vehicles);
      }
      else
        System.out.println("fail: input inválido");
      
      if(inputIsValid)
        System.out.println("Alterado com sucesso!");
    }    
    else
      System.out.println("fail: veículo não existe");

  }

  public void listVeic(){
    for(int i = 0; i < vehicles.size(); i++)
      System.out.println(vehicles.get(i));
  }

          /* FIM DAS FUNÇÕES DE VEÍCULO */
//o parêmetro isReboque é utilizado para não printir se é desejado contratar o seguro quando a função aluga() é reutilizada na função reboque()
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

    int indiceCliente = -1; //é igual a -1 quando não encontrado
  
    for(int i = 0; i < clients.size(); i++)
      if(nomeToSearch.equals(clients.get(i).getName())){
        indiceCliente = i;
        break;
    }
    
    return indiceCliente;

  }

  public int searchVeic(int idToSearch){  

    int indiceVeic = -1; //é igual a -1 quando não encontrado
  
    for(int i = 0; i < vehicles.size(); i++)
      if(idToSearch == vehicles.get(i).getId()){
        indiceVeic = i;
        break;
    }
    
    return indiceVeic;

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
    
    int nextId = -1;
    
    try (Scanner veicStream = new Scanner(fileVeic);)
        {
          while(veicStream.hasNext()) { 
            nextId = veicStream.nextInt();
            String tipo = veicStream.nextLine();
            String nome = veicStream.nextLine();
            String diaria = veicStream.nextLine();
            String alugado = veicStream.nextLine();              

            veicStream.nextLine(); // clear buffer before next readLine
          }
        }
        catch(InputMismatchException e) {
          System.out.println("Invalid Input");
        }
        catch(FileNotFoundException e) {
          //System.out.println("\nAviso: Não há dados de veículo");
        }

    return ++nextId;
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