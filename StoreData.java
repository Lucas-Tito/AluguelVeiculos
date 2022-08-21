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

  public void CliWriter(ArrayList<Cliente> clientesToWrite){

    for(Cliente c : clientes){           
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


    public void VeicWriter(){
    for(Veiculo v : veiculos){
      
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


  public void updateIds(){
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
      updateCli();
    if(veicModified)  
      updateVeic();

  }
}



}