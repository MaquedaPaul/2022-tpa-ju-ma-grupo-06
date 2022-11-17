package controllers;

import cuenta.Cuenta;

import java.util.Objects;

public class Validador {

  public static boolean tieneAcceso(Cuenta cuenta, String accesoNecesario) {

    return Objects.equals(cuenta.tipoCuenta(), accesoNecesario) || Objects.equals(accesoNecesario, "");
  }
}
