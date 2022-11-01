import Client.Client;
import dao.Management;
import dao.StoreData;

import java.util.Scanner;

class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Management g = new Management();
    StoreData mngData = new StoreData();
    mngData.fileDataToArray(g.getClients(),g.getVeiculos());

    while (true) {
        String line = scanner.nextLine();
        System.out.println("$" + line);
        String ui[] = line.split(" ");
        if (line.equals("end")) {
          break;
        } else if (ui[0].equals("addCli")) {//nome, idade, tipoCarteira
          g.addCliente(new Client(ui[1], Integer.parseInt(ui[2]), ui[3]));
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
          g.deposit(ui[1],Float.parseFloat(ui[2]));
        }else if (ui[0].equals("diaria")){
          g.dailyRateUpdate();
        }else if (ui[0].equals("devolve")){// nome
          g.returnVehicle(ui[1]);
        }else if (ui[0].equals("reboque")){// nome
          g.tow(ui[1]);
        }else if (ui[0].equals("listAlugados")){
          g.listClientsRenting();
        }        
        else {
          System.out.println("fail: comando invalido");
        }
    }
    scanner.close();
  }

}