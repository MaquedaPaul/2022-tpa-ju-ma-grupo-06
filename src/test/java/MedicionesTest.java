public class MedicionesTest {
  // El lector falla si la ruta no es valida
/*
  Organizacion mockOrg = mock(Organizacion.class);

  Medicion medicion = new Medicion(mock(TipoConsumo.class),
      Perioricidad.ANUAL,
      20D, 2020,
      12
      ,
      mock(Organizacion.class));

  Medicion medicionAnual = new Medicion(mock(TipoConsumo.class),
      Perioricidad.ANUAL,
      20000D, 2020, 0
      ,
      mock(Organizacion.class));

  Medicion medicionMensual = new Medicion(mock(TipoConsumo.class),
      Perioricidad.ANUAL,
      232D, 2020, 12
      ,
      mock(Organizacion.class));

  @Test
  public void calcularHc() {
    Organizacion onu = new Organizacion("texto1", TipoOrganizacion.INSTITUCION, "texto2", "texto3", new ArrayList<>());
    FactorEmision nuevoFActor = new FactorEmision(300, Unidad.LTS);
    TipoConsumo nuevoConsumo = new TipoConsumo("text", Unidad.LTS, TipoActividad.COMBUSTION_MOVIL, TipoAlcance.EMISION_DIRECTA);
    nuevoConsumo.setFactorEmision(nuevoFActor);
    Medicion nuevaMedicion = new Medicion(nuevoConsumo, Perioricidad.ANUAL, 150, 2020, 0, onu);
    assertEquals(nuevaMedicion.calcularHc(), 450);
  }

  @Test
  public void unaMedicionDeDIC_2020EsDel2020() {

    assertTrue(medicion.esDelAnio(2020));
  }

  @Test
  public void unaMedicionDeDIC_2020EsDeDiciembre() {

    assertTrue(medicion.esDelMes(12));
  }

  @Test
  public void siConsultoLaMedicionMensualDeUnaMedicionAnualMeDevuelveUnDoceavoDelValor() {

    assertEquals(medicionAnual.getValor() / 12, medicionAnual.getValorSegun(Perioricidad.MENSUAL));
  }

  @Test
  public void siConsultoLaMedicionMensualesAnualDeUnaMedicionMensualMeDevuelveElValorMensual() {

    assertEquals(medicionMensual.getValor(), medicionMensual.getValorSegun(Perioricidad.ANUAL));
  }


  private void agregarTiposDeConsumoDePrueba() {
    TipoConsumo gas = new TipoConsumo("Gas Natural", Unidad.CM3, TipoActividad.COMBUSTION_MOVIL, TipoAlcance.EMISION_DIRECTA);
    TipoConsumo nafta = new TipoConsumo("Nafta", Unidad.LTS, TipoActividad.COMBUSTION_MOVIL, TipoAlcance.EMISION_DIRECTA);
    RepoTipoDeConsumo.getInstance().agregarNuevoTipoDeConsumo(gas);
    RepoTipoDeConsumo.getInstance().agregarNuevoTipoDeConsumo(nafta);
  }

 */
}
