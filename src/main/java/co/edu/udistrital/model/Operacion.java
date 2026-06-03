package co.edu.udistrital.model;


public abstract class Operacion {
    public abstract void deshacer(Sistema sistema);
    @Override
    public abstract String toString();
}
