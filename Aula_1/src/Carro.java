public class Carro {

    private String modelo;
    private Motor motor;
    private TanqueCombustivel tanque;
    private TipoCombustivel combustivelMotor;

    public Carro(String modelo, TipoCombustivel tipoCombustivel, TipoCombustivel combustivelMotor, int consumoMotor,
            int capacidadeTanque) {
        this.modelo = modelo;
        this.combustivelMotor = combustivelMotor;
        motor = new Motor(tipoCombustivel, consumoMotor);
        tanque = new TanqueCombustivel(tipoCombustivel, capacidadeTanque);
    }

    public String getModelo() {
        return modelo;
    }

    public int getCombustivelDisponivel() {
        return tanque.getCombustivelDisponivel();
    }

    public TipoCombustivel getCombustivelMotor() {
        return combustivelMotor;
    }

    public void setCombustivelMotor(TipoCombustivel combustivelMotor) {
        this.combustivelMotor = combustivelMotor;
    }

    // Retorna a quantidade efetivamente abastecida
    public int abastece(TipoCombustivel tipoCombustivel, int quantidade) {

        int capacidadeLivre = tanque.getCapacidade() - tanque.getCombustivelDisponivel();
        if (capacidadeLivre < quantidade) {
            tanque.abastece(tipoCombustivel, capacidadeLivre);
            return capacidadeLivre;
        } else {
            tanque.abastece(tipoCombustivel, quantidade);
        }
        return quantidade;
    }

    public void abastecer(TipoCombustivel tanque, int quantidade) {
        if (tanque == getCombustivelMotor()) {
            abastece(tanque, quantidade);
        }
        else if ((tanque == TipoCombustivel.ALCOOL
                || tanque == TipoCombustivel.GASOLINA) && combustivelMotor == TipoCombustivel.FLEX) {
            abastece(tanque, quantidade);
        } else {
            System.out.println("Combustivel nao compativel.");
        }
    }

    // Retorna a distancia que consegue viajar com o combustivel remanescente
    public int verificaSePodeViajar(int distancia) {
        int combustivelNecessario = motor.combustivelNecessario(distancia);
        if (tanque.getCombustivelDisponivel() >= combustivelNecessario) {
            return distancia;
        } else {
            return tanque.getCombustivelDisponivel() * motor.getConsumo();
        }
    }

    // Retorna true se conseguiu viajar
    public boolean viaja(int distancia) {
        if (verificaSePodeViajar(distancia) >= distancia) {
            motor.percorre(distancia);
            tanque.gasta(motor.combustivelNecessario(distancia));
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Carro:\n  Modelo=" + modelo + "\n  Motor=" + motor + "\n  Tanque=" + tanque;
    }
}
