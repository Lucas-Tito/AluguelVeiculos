import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.io.File;

class Gerencia {
  ArrayList<Cliente> clientes = new ArrayList<>();
  ArrayList<Veiculo> veiculos = new ArrayList<>();
  StoreData mngData = new StoreData();
  File fileCli = new File("clientes.txt");
  File fileVeic = new File("veiculos.txt");
  int nextVehicleId = 0;
  String escolha;

  Scanner scanner = new Scanner(System.in);
  // File fileCli = new File("clientes.txt");
  // File fileVeic = new File("veiculos.txt");

  public ArrayList<Cliente> getClientes(){
    return clientes;
  }
  public ArrayList<Veiculo> getVeiculos(){
    return veiculos;
  }
  
        /* FUNÇÕES DE CLIENTE */
  //se o cliente não existir e não for de menor
  //a conta faz parte dos atributos de clientToAdd
  //adiciona o cliente no arrayList de clientes
  public void addCliente(Cliente clientToAdd){
    
    boolean letrasAceitas = clientToAdd.getTipo().equals("a") || 
            clientToAdd.getTipo().equals("A") ||
            clientToAdd.getTipo().equals("b") ||
            clientToAdd.getTipo().equals("B") ||
            clientToAdd.getTipo().equals("ab") ||
            clientToAdd.getTipo().equals("AB");
    
    if(mngData.nameExists(clientToAdd.getNome()))
      System.out.println("fail: cliente já cadastrado");

    else if(clientToAdd.getIdade() < 18)
      System.out.println("fail: menores não podem pilotar");
    
    else if(!letrasAceitas)
      System.out.println("fail: tipo de carteira inválido");

    else{
      clientToAdd.setTipo(clientToAdd.getTipo().toUpperCase()); //padroniza o tipo como maiúsculo
      clientes.add(clientToAdd);
      mngData.CliWriter(clientes);//escreve o cliente no txt
    }
} 
  //Remove o primeiro cliente com nome igual ao contido em clientToRemove  
  public void removeCliente(String clientToRemove){

    int indiceCli = searchCli(clientToRemove);
    if(indiceCli != -1){

      clientes.remove(indiceCli);
      System.out.println("Removido com sucesso!");
      mngData.updateCli(clientes);  //deixa o txt de clientes igual ao vetor
      
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
        clientes.get(indiceCli).setName(nomeNovo);
        mngData.updateCli(clientes);
      }
      else if(opcao == 2){

        inputIsValid = true;    
        System.out.print("Digite a nova idade: ");
        int idadeToEdit = scanner.nextInt();
        clientes.get(indiceCli).setIdade(idadeToEdit);
        mngData.updateCli(clientes);

      }
      else if(opcao == 3){

        inputIsValid = true;    
        System.out.print("Digite o novo tipoCarteira: ");
        String tipoNovo = scanner.nextLine();   
        clientes.get(indiceCli).setTipo(tipoNovo);
        mngData.updateCli(clientes);
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
    for(int i = 0; i < clientes.size(); i++)
      System.out.println(clientes.get(i));
  }

              /* FUNÇÕES DE VEÍCULO */
  //como é normal uma locadora ter o mesmo veículo
  //não será feita uma verificação que compara veículos
  //a diferenciação será dada a partir dos ids que são incrementados automaticamente  
  public void addVeiculo(String tipoToAdd, String nomeToAdd, float diariaToAdd){

    int veichileId = nextId();  //chama função que retorna o último id incrementado
    
    //escreve veículo no txt se o tipo passado for válido
    if(tipoToAdd.equals("moto") || tipoToAdd.equals("Moto")){
      veiculos.add(new Moto(veichileId, nomeToAdd, diariaToAdd));
      mngData.VeicWriter(veiculos);
   } 
    else if(tipoToAdd.equals("carro") || tipoToAdd.equals("Carro")){
      veiculos.add(new Carro(veichileId, nomeToAdd, diariaToAdd));
      mngData.VeicWriter(veiculos);
    }
    else
      System.out.println("fail: tipo inválido");

  }

  //Remove o veículo que corresponder ao id contido em idToRemove  
  public void removeVeiculo(int idToRemove){

    int indiceVeic = searchVeic(idToRemove);
    if(indiceVeic != -1){
      veiculos.remove(indiceVeic);
      System.out.println("Removido com sucesso!");
      mngData.updateVeic(veiculos);
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
        veiculos.get(indiceVeic).setNome(nomeNovo);
        mngData.updateVeic(veiculos);

      }
      else if(opcao == 2){
            
        inputIsValid = true;
        System.out.print("Digite a nova diaria: ");
        int novaDiaria = scanner.nextInt();
        veiculos.get(indiceVeic).setDiaria(novaDiaria);
        mngData.updateVeic(veiculos);
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
    for(int i = 0; i < veiculos.size(); i++)
      System.out.println(veiculos.get(i));
  }

          /* FIM DAS FUNÇÕES DE VEÍCULO */
//o parêmetro isReboque é utilizado para não printir se é desejado contratar o seguro quando a função aluga() é reutilizada na função reboque()
public void aluga(String nome, int idCarro, boolean isReboque){

  //IMPLEMENTAR COBRAR UMA DIARIA NA HORA DE ALUGAR
  int indiceCliente = searchCli(nome);
  boolean tipoOk = false;
  if(indiceCliente != -1){  //verifica se o cliente existe

    if(clientes.get(indiceCliente).getIdCarroAlugado() == -1){  //verifica se o cliente já alugou algum veículo
    
        /*VERIFICAÇÃO DE COERENCIA DE CARTEIRA DE MOTORISTA*/
    if(clientes.get(indiceCliente).getTipo().equals("A") && veiculos.get(idCarro).getTipo().equals("Moto")){
      tipoOk = true;

    }else if(clientes.get(indiceCliente).getTipo().equals("B") && veiculos.get(idCarro).getTipo().equals("Carro")){
      tipoOk = true;

    }else if(clientes.get(indiceCliente).getTipo().equals("AB")){
      tipoOk = true;
    }
        /*FIM VERIFICAÇÃO DE COERENCIA DE CARTEIRA*/
    if(tipoOk){
      
      int indiceVeic = searchVeic(idCarro);
      if(indiceVeic != -1){

        if(veiculos.get(idCarro).getIsAlugado() == false){  //verifica se o carro já está alugado

          if(clientes.get(indiceCliente).getConta().getSaldo() >= veiculos.get(idCarro).getDiaria()){ //verifica se o cliente tem saldo para pagar a primeira diaria
            veiculos.get(indiceVeic).setIsAlugado(true); //seta como true a varíavel que diz se o veículo está alugado
            clientes.get(indiceCliente).setIdCarroAlugado(idCarro); //guarda o id do carro no objeto cliente
            
            if(!isReboque)  //isReboque é um parâmetro utilizado para saber se a função reboque() chamou a função aluga(), o que previne que seja perguntado se o cliente deseja o seguro reboque (já que, se a função reboque chama aluga() é porquê o cliente já possui o seguro)          
              if(clientes.get(indiceCliente).getReboque() == false){
                System.out.println("Deseja um seguro reboque por + 15 reais na diária?");
                escolha = scanner.next();
                System.out.println("Alugado com sucesso!");
                if(escolha.equals("s") || escolha.equals("sim") || escolha.equals("S") || escolha.equals("Sim"))
                  clientes.get(indiceCliente).setReboque(true);
              }
          
            mngData.updateVeic(veiculos);
            mngData.updateCli(clientes);
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
      clientes.get(indiceCli).getConta().setSaldo(saldo);
      mngData.updateCli(clientes);
    }
    else
      System.out.println("fail: cliente inexistente");
  }

}

//CONVERSAR SOBRE IMPLEMENTAR DE MANEIRA QUE SEJA POSSÍVEL FICAR COM CONTA NEGATIVA
public void diariaUpdate(){ 

  for(int i = 0; i < clientes.size();i++)
    if(clientes.get(i).getIdCarroAlugado() != -1){ //acha os clientes que já alugaram
      int indiceVeic = searchVeic(clientes.get(i).getIdCarroAlugado());

      if(escolha.equals("s"))
        clientes.get(i).getConta().setSaldo(-15);  
    
      clientes.get(i).getConta().setSaldo(veiculos.get(indiceVeic).getDiaria()*-1);   //realiza a cobrança
      mngData.updateCli(clientes);
    }
 }


  public int searchCli(String nomeToSearch){  //FUNÇÃO EM DESUSO

    int indiceCliente = -1; //é igual a -1 quando não encontrado
  
    for(int i = 0; i < clientes.size(); i++)
      if(nomeToSearch.equals(clientes.get(i).getNome())){
        indiceCliente = i;
        break;
    }
    
    return indiceCliente;

  }

  public int searchVeic(int idToSearch){  //FUNÇÃO EM DESUSO

    int indiceVeic = -1; //é igual a -1 quando não encontrado
  
    for(int i = 0; i < veiculos.size(); i++)
      if(idToSearch == veiculos.get(i).getId()){
        indiceVeic = i;
        break;
    }
    
    return indiceVeic;

  }

  public void listAlugados (){  // implementar: SE NÃO ACHOU NENHUM PRINTAR ERRO
    String show = ""; 
    
    for(int i=0; i < clientes.size(); i++){
      if(i != veiculos.size())
        show += "-";
      if(clientes.get(i).getIdCarroAlugado() != -1){  //acha os clientes que já alugaram
        show += clientes.get(i).getNome() + " [" + "id do veículo: " + clientes.get(i).getIdCarroAlugado(); 

      for(int j = 0; j < veiculos.size(); j++)
        if(veiculos.get(j).getId() == clientes.get(i).getIdCarroAlugado())
          show += " | nome do veículo: " + veiculos.get(i).getNome() + "]";

      show +="\n";
      }
    }
      
    System.out.println(show);
  }
  
  public void devolve(String nome){  //IMPLEMENTAR DE MANEIRA QUE SÓ É POSSÍVEL DEVOLVER SE TODAS AS DIÁRIAS TIVEREM QUITADAS (CLIENTE COM SALDO POSITIVO) E VERIFICAR SE O CLIENTE TEM CARRO PARA DEVOLVER

    int indiceCli = searchCli(nome);  
    int indiceVeic = searchVeic(clientes.get(indiceCli).getIdCarroAlugado());

    if(indiceCli == -1)
      System.out.println("fail: cliente inexistente");
    if(indiceVeic == -1)
      System.out.println("fail: veículo inexistente");
    else{
    clientes.get(indiceCli).setIdCarroAlugado(-1);
    veiculos.get(indiceVeic).setIsAlugado(false);
    mngData.updateCli(clientes);
    mngData.updateVeic(veiculos);
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
    int idVeic = clientes.get(indiceCli).getIdCarroAlugado();
    boolean carWasFound = false;

    if(clientes.get(indiceCli).getIdCarroAlugado() != -1){//se o cliente já alugou algum veículo

      if(clientes.get(indiceCli).getReboque() == true){// se o cliente possui plano de reboque

        float ressarcimento = veiculos.get(idVeic).getDiaria();

        for(int i = 0 ; i <veiculos.size(); i++)
          if(veiculos.get(i).getIsAlugado() == false && veiculos.get(i).getId() != idVeic){
            //*IMPLEMENTAR VERIFICAÇÃO SE O VEÍCULO É COMPATÍVEL COM A CARTEIRA DO CLIENTE
            carWasFound = true;
            devolve(nome);
            aluga(nome, veiculos.get(i).getId(),true);

            mngData.updateCli(clientes);
            mngData.updateVeic(veiculos);
            break;
          }


        if(!carWasFound){
          clientes.get(searchCli(nome)).getConta().setSaldo(ressarcimento);
          clientes.get(indiceCli).setIdCarroAlugado(-1);
          mngData.updateCli(clientes);
          mngData.updateVeic(veiculos);
        }

        removeVeiculo(idVeic);
        mngData.updateIds(veiculos, clientes);
      }
      else{
        System.out.println("fail: cliente não possui plano de reboque");
      }
    }
    else
      System.out.println("fail: cliente não possui veículo");
  }
}