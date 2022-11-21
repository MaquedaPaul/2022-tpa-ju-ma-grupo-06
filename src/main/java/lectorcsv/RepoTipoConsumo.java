package lectorcsv;

import utils.tipoconsumo.TipoConsumo;

public interface RepoTipoConsumo {

  boolean existeElTipoDeConsumo(String tipoConsumo);

  TipoConsumo getTipoConsumo(String tipoConsumo);

}
