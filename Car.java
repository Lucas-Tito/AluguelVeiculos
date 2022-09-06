class Car extends Vehicle {
  
  Car(int id, String nome, float diaria){

    super(id,nome, diaria);
    type = "Carro";
    
  }
  Car(int id, String nome, float diaria, boolean alugado){
    super(id,nome, diaria, alugado);
  type = "Carro";
  }
}