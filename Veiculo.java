abstract class Veiculo{
  private int id;
  private String nome;
  protected String type;
  private float diaria;
  private boolean isAlugado;

  Veiculo(int id, String nome, float diaria){
    this.id = id;
    this.nome = nome;
    this.diaria = diaria;
    this.isAlugado = false;
  }
  
  //construtor usado pela fileDataToArray() para colocar os dados do arquivo txt para dentro do vetor
  Veiculo(int id,String nome, float diaria, boolean isAlugado){ 
    this.id = id;
    this.nome = nome;
    this.diaria = diaria;
    this.isAlugado = isAlugado;
  }

  public int getId(){
    return id;
  }

  public void setId(int idToSet){
    this.id = idToSet;
  }

  public String getNome(){
    return nome;
  }

  public void setNome(String nome){
    this.nome = nome;
  }

  public float getDiaria(){
    return diaria;
  }

  public void setDiaria(float diaria){
    this.diaria = diaria;
  }

  public String getTipo(){
    return type;
  }

  public boolean getIsAlugado(){
    return isAlugado;
  }

  public void setIsAlugado(boolean estado){
    this.isAlugado = estado;
  }

  public String toString(){

    return "-" + id +" ["+"tipo: "+type+ " | nome: " +nome + " | diaria: " + diaria +"]";

  }
}