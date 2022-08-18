class Moto extends Veiculo{
  
  Moto(int id,String nome, float diaria){

    super(id, nome, diaria);
    type = "Moto";

  }
  Moto(int id,String nome, float diaria,boolean alugado){
    super(id, nome, diaria,alugado);
    type = "Moto";
  }
}