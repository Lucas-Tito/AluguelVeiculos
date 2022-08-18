class Carro extends Veiculo{
  
  Carro(int id,String nome, float diaria){

    super(id,nome, diaria);
    type = "Carro";
    
  }
  Carro(int id, String nome, float diaria, boolean alugado){
    super(id,nome, diaria, alugado);
  type = "Carro";
  }
}