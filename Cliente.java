class Cliente{
  private String nome;
  private int idade;
  private String tipoCarteira;
  private Conta conta;
  private int carroAlugado;   //recebe o id do veículo que o cliente alugou se não há nenhum carro alugado o atributo recebe -1 (*mudar nome para veiculoAlugado)
  private boolean hasReboque; //mudar nome para hasSeguro
                          

  Cliente(String nome, int idade, String tipo){
    this.nome = nome;
    this.idade = idade;
    this.tipoCarteira = tipo;
    this.conta = new Conta();
    this.carroAlugado = -1;
  }

  Cliente(String nome, int idade, String tipo, int idCarro, boolean hasReboque,float saldo){  //construtor usado pela fileDataToArray() para colocar os dados do arquivo txt para dentro do vetor
    this.nome = nome;
    this.idade = idade;
    this.tipoCarteira = tipo;
    this.conta = new Conta();
    this.conta.setSaldo(saldo);
    this.carroAlugado = idCarro;
    this.hasReboque = hasReboque;
  }

  public String getNome(){
    return nome;
  }

  public void setName(String nome){
    this.nome = nome;
  }

  public int getIdade(){
    return idade;
  }

  public void setIdade(int idade){
    this.idade = idade;
  } 

  public String getTipo(){
    return tipoCarteira;
  }

  public void setTipo(String tipoCarteira){
    this.tipoCarteira = tipoCarteira;
  }
  
  public Conta getConta(){
    return conta;
  }

  public int getIdCarroAlugado(){
    return carroAlugado;
  }

  public void setIdCarroAlugado(int id){
    this.carroAlugado = id;
  }

  public boolean getReboque(){
    return hasReboque;
  }
  
  public void setReboque(boolean estado){
    this.hasReboque = estado;
  }

  public String toString(){

    String show = "-" + nome +" ["+ "idade: " +idade +"| carteira: "+ tipoCarteira  + "| saldo: " + conta + " Seguro: ";

    if(hasReboque)
      show += "sim]";
    else
      show+= "não]";

    return show;
    
  }
  
}