import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.io.FileNotFoundException;
import java.io.File;

class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Gerencia g = new Gerencia();
    StoreData mngData = new StoreData();
    mngData.fileDataToArray(g.getClientes(),g.getVeiculos());

    while (true) {
        String line = scanner.nextLine();
        System.out.println("$" + line);
        String ui[] = line.split(" ");
        if (line.equals("end")) {
          break;
        } else if (ui[0].equals("addCli")) {//nome, idade, tipoCarteira
          g.addCliente(new Cliente(ui[1], Integer.parseInt(ui[2]), ui[3]));
        } else if (ui[0].equals("removeCli")){// nome
          g.removeCliente(ui[1]);
        } else if (ui[0].equals("listCli")) {
          g.listCli();
        } else if (ui[0].equals("editCli")){
          g.editCliente();
        } else if (ui[0].equals("listVeic")){
          g.listVeic();
        } else if (ui[0].equals("removeVeic")) {//id
          g.removeVeiculo(Integer.parseInt(ui[1]));
        } else if (ui[0].equals("addVeic")){//tipo do veiculo, nome, diaria
          g.addVeiculo(ui[1],ui[2],Float.parseFloat(ui[3]));
        } else if (ui[0].equals("editVeic")) {
          g.editVeiculo();
        } else if (ui[0].equals("aluga")) {// nome e indice do veiculo
          g.aluga(ui[1], Integer.parseInt(ui[2]), false);
        } else if (ui[0].equals("deposito")){// nome e valor
          g.depositar(ui[1],Float.parseFloat(ui[2]));
        }else if (ui[0].equals("diaria")){
          g.diariaUpdate();
        }else if (ui[0].equals("devolve")){// nome
          g.devolve(ui[1]);
        }else if (ui[0].equals("reboque")){// nome
          g.reboque(ui[1]);
        }else if (ui[0].equals("listAlugados")){
          g.listAlugados();
        }        
        else {
          System.out.println("fail: comando invalido");
        }
    }
    scanner.close();
  }

  
                //will be removed
//   public static void fileDataToArray(ArrayList<Cliente> clientesIn,ArrayList<Veiculo> veiculosIn){//arquivo pro array
//     File fileCli = new File("clientes.txt");

//     try (Scanner clientStream = new Scanner(fileCli);){
//           while(clientStream.hasNext()) { 
//             String nome = clientStream.next();
//             int idade = clientStream.nextInt();
//             String tipo = clientStream.next();
//             int idCarro = clientStream.nextInt();
//             boolean hasReboque = clientStream.nextBoolean();
//             String saldo = clientStream.next();           
            
//             clientesIn.add(new Cliente(nome, idade, tipo, idCarro, hasReboque, Float.valueOf(saldo)));
//             clientStream.nextLine(); // clear buffer before next readLine
//           }
//     }
//         catch(InputMismatchException e) {
//           System.out.println("Invalid Input");
//         }
//         catch(FileNotFoundException e) {
//           System.out.println("\nAviso: Não há dados de cliente");
//         }
        
//         File fileVeic = new File("veiculos.txt");

//     try (Scanner veicStream = new Scanner(fileVeic);){

//       while(veicStream.hasNext()) { 
//         int id = veicStream.nextInt();
//         String tipo = veicStream.next();
//         String nome = veicStream.next();
//         String diaria = veicStream.next();
//         boolean alugado = veicStream.nextBoolean();           
            
//         if(tipo.equals("moto")|| tipo.equals("Moto"))
//           veiculosIn.add(new Moto(id, nome, Float.valueOf(diaria), alugado));
//         else
//           veiculosIn.add(new Carro(id, nome, Float.valueOf(diaria), alugado));

//           veicStream.nextLine(); // clear buffer before next readLine
//       }
//     }
//     catch(InputMismatchException e){
//       System.out.println("Invalid Input");
//     }
//     catch(FileNotFoundException e){
//       System.out.println("\nAviso: Não há dados de veículo");
//     }
//   }
}