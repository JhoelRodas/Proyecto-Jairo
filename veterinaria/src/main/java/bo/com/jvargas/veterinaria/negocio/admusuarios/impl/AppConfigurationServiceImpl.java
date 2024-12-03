package bo.com.jvargas.veterinaria.negocio.admusuarios.impl;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthAppConfiguration;
import bo.com.jvargas.veterinaria.datos.model.sistema.AuthUser;
import bo.com.jvargas.veterinaria.datos.model.sistema.dto.AppConfigurationDto;
import bo.com.jvargas.veterinaria.datos.model.sistema.enums.ApplicationType;
import bo.com.jvargas.veterinaria.datos.repository.sistema.AuthAppConfigurationRepository;
import bo.com.jvargas.veterinaria.datos.repository.sistema.AuthUserRepository;
import bo.com.jvargas.veterinaria.negocio.admusuarios.AppConfigurationService;
import bo.com.jvargas.veterinaria.utils.FormatUtil;
import bo.com.jvargas.veterinaria.utils.OperationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("appConfigurationService")
public class AppConfigurationServiceImpl implements AppConfigurationService {
    private final AuthAppConfigurationRepository authAppConfigurationRepository;
    private final AuthUserRepository authUserRepository;

    public AppConfigurationServiceImpl(AuthAppConfigurationRepository authAppConfigurationRepository,
                                       AuthUserRepository authUserRepository) {
        this.authAppConfigurationRepository = authAppConfigurationRepository;
        this.authUserRepository = authUserRepository;
    }

    @Override
    public AuthAppConfiguration findAppTheme(String username, ApplicationType applicationType) {
        AuthUser authUser = this.authUserRepository.findByUsername(username).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado(AuthUser.class.getName(), "username", username)));
        return this.authAppConfigurationRepository.findByUserAndType(authUser, applicationType).orElse(null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateAppTheme(String username, ApplicationType applicationType, AppConfigurationDto appConfigurationDto) {
        AuthUser authUser = this.authUserRepository.findByUsername(username).orElseThrow(() -> new OperationException(FormatUtil.noRegistrado(AuthUser.class.getName(), "username", username)));
        AuthAppConfiguration authAppConfiguration = this.authAppConfigurationRepository.findByUserAndType(authUser, applicationType).orElse(null);
        if (authAppConfiguration != null) {
            authAppConfiguration.setJsonConfig(appConfigurationDto.getJsonConfig());
            this.authAppConfigurationRepository.save(authAppConfiguration);
        } else {
            this.authAppConfigurationRepository.save(AuthAppConfiguration.builder()
                    .applicationType(applicationType)
                    .jsonConfig(appConfigurationDto.getJsonConfig())
                    .idAuthUser(authUser)
                    .build());
        }
    }
}
