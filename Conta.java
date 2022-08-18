class Conta{
  private float saldo;

  public Conta(){
    saldo = 0;
  }

  public void setSaldo(float valueToSet){
      this.saldo += valueToSet;
  }

  public float getSaldo(){
    return saldo;
  }

public String toString(){
  return ""+saldo;
}
}