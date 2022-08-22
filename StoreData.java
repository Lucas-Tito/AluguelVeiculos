import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.io.File;



class StoreData{

  File fileCli = new File("clientes.txt");
  File fileVeic = new File("veiculos.txt");



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
            //System.out.println("\nAviso: Não há dados de veículo");
          }
  
      return idExists;
    }


  public void CliWriter(ArrayList<Cliente> clientesToWrite){

    for(Cliente c : clientesToWrite){           
      try (
          FileWriter fstream = new FileWriter(fileCli, StandardCharsets.UTF_8, true);
          PrintWriter outputFile = new PrintWriter(fstream, true); // using autoflushing
          )
        {
          if(!nameExists(c.getNome())){
              
            outputFile.println(c.getNome()); 
            outputFile.println(c.getIdade());
            outputFile.println(c.getTipo());
            outputFile.println(c.getIdCarroAlugado());
            outputFile.println(c.getReboque());
            outputFile.println(c.getConta().getSaldo());
              
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


    public void VeicWriter(ArrayList<Veiculo> veicsToWrite){
    for(Veiculo v : veicsToWrite){
      
      try (
            FileWriter fstream = 
            new FileWriter(fileVeic, StandardCharsets.UTF_8, true);
            PrintWriter outputFile = 
            new PrintWriter(fstream, true); // using autoflushing
          )
        {              
          if(!idExists(v.getId())){
            outputFile.println(v.getId());
            outputFile.println(v.getTipo());
            outputFile.println(v.getNome()); 
            outputFile.println(v.getDiaria());
            outputFile.println(v.getIsAlugado()); 
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


  public void updateCli(ArrayList<Cliente> clientesToUpdate){
    File novo = new File("novo.txt");
    for(Cliente c : clientesToUpdate){
           
    try (
          FileWriter fstream = 
          new FileWriter(novo, StandardCharsets.UTF_8, true);
          PrintWriter outputFile = 
          new PrintWriter(fstream, true); // using autoflushing
        )
        {        
          outputFile.println(c.getNome()); 
          outputFile.println(c.getIdade());
          outputFile.println(c.getTipo());
          outputFile.println(c.getIdCarroAlugado());
          outputFile.println(c.getReboque());
          outputFile.println(c.getConta().getSaldo());            
        }
        catch(InputMismatchException e) {
          System.err.println("Invalid input: " + e.getMessage());
        }
        catch(IOException e) {
          System.out.println("Error opening the file: " + e.getMessage());
        }
    }
    fileCli.delete();
    novo.renameTo(new File("clientes.txt"));
  }


  public void updateVeic(ArrayList<Veiculo> veicsToUpdate){

    File novo = new File("novo.txt");
    for(Veiculo c : veicsToUpdate){
           
      try(
            FileWriter fstream = 
            new FileWriter(novo, StandardCharsets.UTF_8, true);
            PrintWriter outputFile = 
              new PrintWriter(fstream, true); // using autoflushing
          )
        {          
          outputFile.println(c.getId());
          outputFile.println(c.getTipo()); 
          outputFile.println(c.getNome());
          outputFile.println(c.getDiaria());
          outputFile.println(c.getIsAlugado());    
        }
        catch(InputMismatchException e) {
          System.err.println("Invalid input: " + e.getMessage());
        }
        catch(IOException e) {
          System.out.println("Error opening the file: " + e.getMessage());
        }
    }
    fileVeic.delete();
    novo.renameTo(new File("veiculos.txt"));
    
  }
 


  public void updateIds(ArrayList<Veiculo> veiculos, ArrayList<Cliente> clientes){
    boolean cliModified = false;
    boolean veicModified = false;

    for(int i =0; i<veiculos.size(); i++){

      if(veiculos.get(i).getIsAlugado() == true){
        for(int j=0; j<clientes.size(); j++){
          if(clientes.get(i).getIdCarroAlugado() == veiculos.get(i).getId()){
            cliModified = true;
            veicModified = true;
            clientes.get(i).setIdCarroAlugado(i);
            veiculos.get(i).setId(i);
          }
        }
      }
      else{
        cliModified = true;
        veiculos.get(i).setId(i);
      }

    }
    if(cliModified)
      updateCli(clientes);
    if(veicModified)  
     updateVeic(veiculos);

  }


  public void fileDataToArray(ArrayList<Cliente> clientesIn,ArrayList<Veiculo> veiculosIn){//arquivo pro array
    File fileCli = new File("clientes.txt");

    try (Scanner clientStream = new Scanner(fileCli);){
          while(clientStream.hasNext()) { 
            String nome = clientStream.next();
            int idade = clientStream.nextInt();
            String tipo = clientStream.next();
            int idCarro = clientStream.nextInt();
            boolean hasReboque = clientStream.nextBoolean();
            String saldo = clientStream.next();           
            
            clientesIn.add(new Cliente(nome, idade, tipo, idCarro, hasReboque, Float.valueOf(saldo)));
            clientStream.nextLine(); // clear buffer before next readLine
          }
    }
        catch(InputMismatchException e) {
          System.out.println("Invalid Input");
        }
        catch(FileNotFoundException e) {
          System.out.println("\nAviso: Não há dados de cliente");
        }
        
        File fileVeic = new File("veiculos.txt");

    try (Scanner veicStream = new Scanner(fileVeic);){

      while(veicStream.hasNext()) { 
        int id = veicStream.nextInt();
        String tipo = veicStream.next();
        String nome = veicStream.next();
        String diaria = veicStream.next();
        boolean alugado = veicStream.nextBoolean();           
            
        if(tipo.equals("moto")|| tipo.equals("Moto"))
          veiculosIn.add(new Moto(id, nome, Float.valueOf(diaria), alugado));
        else
          veiculosIn.add(new Carro(id, nome, Float.valueOf(diaria), alugado));

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


