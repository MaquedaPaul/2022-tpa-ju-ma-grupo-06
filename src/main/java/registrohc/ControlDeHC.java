package registrohc;

import organizacion.Organizacion;
import organizacion.RepoOrganizacion;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

public class ControlDeHC {

  private final YearMonth mes;

  public ControlDeHC(YearMonth mes) {
    this.mes = mes;
  }

  public void persistirMedicionesDelMes() {
    List<Organizacion> organizaciones = RepoOrganizacion.getInstance().getOrganizaciones();

    List<RegistroHCOrganizacion> registros = organizaciones
        .stream()
        .map(this::crearRegistroDeMedicion)
        .collect(Collectors.toList());

    RepoMedicionesHCOrganizaciones.getInstance().persistirTodas(registros);
  }

  public RegistroHCOrganizacion crearRegistroDeMedicion(Organizacion organizacion) {
    return
        new RegistroHCOrganizacion(
            organizacion,
            (long) organizacion.calcularHcMedicionesMensual(mes),
            (long) organizacion.calcularHCTotalMensualDeMiembros(),
            mes.getYear(),
            mes.getMonthValue()
        );
  }
}
