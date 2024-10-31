package bo.com.jvargas.veterinaria.datos.model.sistema.dto;

import bo.com.jvargas.veterinaria.datos.model.sistema.AuthAction;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActionDto implements Serializable {
    private Long id;
    private String action;
    private String code;
    private boolean baseAction;

    public static ActionDto buildFromAuthAction(AuthAction authAction) {
        return ActionDto.builder()
                .id(authAction.getId())
                .action(authAction.getAction())
                .code(authAction.getCode())
                .baseAction(authAction.isBaseAction())
                .build();
    }
}
