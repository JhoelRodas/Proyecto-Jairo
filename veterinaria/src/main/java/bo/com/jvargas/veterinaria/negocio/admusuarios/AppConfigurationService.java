package bo.com.jvargas.veterinaria.negocio.admusuarios;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthAppConfiguration;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.AppConfigurationDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.ApplicationType;

public interface AppConfigurationService {

    AuthAppConfiguration findAppTheme(String username, ApplicationType applicationType);

    void updateAppTheme(String username, ApplicationType applicationType, AppConfigurationDto appConfigurationDto);
}
