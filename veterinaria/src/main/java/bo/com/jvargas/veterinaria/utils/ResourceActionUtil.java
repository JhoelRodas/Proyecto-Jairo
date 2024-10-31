package bo.com.jvargas.veterinaria.utils;


import bo.com.jvargas.veterinaria.datos.model.sistema.AuthAction;

import java.util.ArrayList;
import java.util.List;

public class ResourceActionUtil {
    public static final String LIST_ACTION_CODE = "listAction";
    public static final String RECORD_ACTION_CODE = "createAction";
    public static final String UPDATE_ACTION_CODE = "updateAction";
    public static final String DELETE_ACTION_CODE = "deleteAction";
    public static final String ENABLE_DISABLE_ACTION = "enableDisableAction";
    public static final String TO_EXCEL_ACTION = "toExcelAction";
    public static final String CONFIG_ACCESS_ACTION = "configAccessAction";
    public static final String VIEW_LOG_ACTION = "viewLogAction";
    public static final String PAUSE_ACTION_CODE = "pauseAction";
    public static final String RESTART_ACTION_CODE = "restartAction";
    public static final String EXECNOW_ACTION_CODE = "execNowAction";
    public static final String REPAIR_ACTION_CODE = "repairAction";
    public static final String CONTACTAR_ACTION_CODE = "contactarAction";
    public static final String VER_DIRECCIONES_ACTION_CODE = "verDireccionesAction";
    public static final String CONSULTAR_SEGUIMIENTO_ACTION_CODE = "consultarSeguimientoAction";
    public static final String VER_OBSERVACIONES_ACTION_CODE = "verObservacionesAction";

    public static List<AuthAction> accionesBaseList() {
        List<AuthAction> authActionList = new ArrayList<>();
        authActionList.add(AuthAction.builder()
                .code(LIST_ACTION_CODE)
                .action("Listar")
                .baseAction(true)
                .build());

        authActionList.add(AuthAction.builder()
                .code(RECORD_ACTION_CODE)
                .action("Registrar")
                .baseAction(true)
                .build());

        authActionList.add(AuthAction.builder()
                .code(UPDATE_ACTION_CODE)
                .action("Actualizar")
                .baseAction(true)
                .build());

        authActionList.add(AuthAction.builder()
                .code(DELETE_ACTION_CODE)
                .action("Eliminar")
                .baseAction(true)
                .build());

        authActionList.add(AuthAction.builder()
                .code(ENABLE_DISABLE_ACTION)
                .action("Habilitar/Inhabilitar")
                .baseAction(true)
                .build());

        authActionList.add(AuthAction.builder()
                .code(TO_EXCEL_ACTION)
                .action("Exportar a Excel")
                .baseAction(true)
                .build());

        authActionList.add(AuthAction.builder()
                .code(CONFIG_ACCESS_ACTION)
                .action("Configurar Acciones")
                .baseAction(true)
                .build());

        authActionList.add(AuthAction.builder()
                .code(VIEW_LOG_ACTION)
                .action("Ver Log")
                .baseAction(true)
                .build());

        authActionList.add(AuthAction.builder()
                .code(PAUSE_ACTION_CODE)
                .action("Pausar Job")
                .baseAction(true)
                .build());

        authActionList.add(AuthAction.builder()
                .code(RESTART_ACTION_CODE)
                .action("Reiniciar Job")
                .baseAction(true)
                .build());

        authActionList.add(AuthAction.builder()
                .code(EXECNOW_ACTION_CODE)
                .action("Ejecutar Job")
                .baseAction(true)
                .build());

        authActionList.add(AuthAction.builder()
                .code(REPAIR_ACTION_CODE)
                .action("Reparar Jobs")
                .baseAction(true)
                .build());

        authActionList.add(AuthAction.builder()
                .code(CONTACTAR_ACTION_CODE)
                .action("Contactar")
                .baseAction(true)
                .build());

        authActionList.add(AuthAction.builder()
                .code(VER_DIRECCIONES_ACTION_CODE)
                .action("Ver Direcciones")
                .baseAction(true)
                .build());

        authActionList.add(AuthAction.builder()
                .code(CONSULTAR_SEGUIMIENTO_ACTION_CODE)
                .action("Consultar Seguimiento")
                .baseAction(true)
                .build());

        authActionList.add(AuthAction.builder()
                .code(VER_OBSERVACIONES_ACTION_CODE)
                .action("Ver Observaciones")
                .baseAction(true)
                .build());
        return authActionList;
    }

    public static final String[] roleActionsCode = {
            LIST_ACTION_CODE,
            RECORD_ACTION_CODE,
            UPDATE_ACTION_CODE,
            DELETE_ACTION_CODE,
            ENABLE_DISABLE_ACTION
    };

    public static final String[] userActionsCode = {
            LIST_ACTION_CODE,
            RECORD_ACTION_CODE,
            UPDATE_ACTION_CODE,
            DELETE_ACTION_CODE,
            ENABLE_DISABLE_ACTION,
            TO_EXCEL_ACTION
    };

    public static final String[] resourceActionsCode = {
            LIST_ACTION_CODE,
            UPDATE_ACTION_CODE
    };

    public static final String[] accessActionsCode = {
            LIST_ACTION_CODE,
            CONFIG_ACCESS_ACTION
    };

    public static final String[] parameterActionsCode = {
            LIST_ACTION_CODE,
            UPDATE_ACTION_CODE,
            TO_EXCEL_ACTION
    };
    public static final String[] domainActionsCode = {
            LIST_ACTION_CODE,
            RECORD_ACTION_CODE,
            UPDATE_ACTION_CODE,
            DELETE_ACTION_CODE,
            TO_EXCEL_ACTION
    };
    public static final String[] commonsActionsCode = {
            LIST_ACTION_CODE,
            RECORD_ACTION_CODE,
            UPDATE_ACTION_CODE,
            DELETE_ACTION_CODE,
    };
    public static final String[] procesosActionsCode = {
            LIST_ACTION_CODE,
            PAUSE_ACTION_CODE,
            RESTART_ACTION_CODE,
            REPAIR_ACTION_CODE,
            EXECNOW_ACTION_CODE
    };

    public static final String[] logActionsCode = {
            LIST_ACTION_CODE,
            VIEW_LOG_ACTION
    };

    public static final String[] logExternoActionsCode = {
            LIST_ACTION_CODE
    };

    public static final String[] directorPanelActionsCode = {
            LIST_ACTION_CODE,
            CONTACTAR_ACTION_CODE,
            VER_DIRECCIONES_ACTION_CODE,
            CONSULTAR_SEGUIMIENTO_ACTION_CODE,
            TO_EXCEL_ACTION
    };

    public static final String[] callCenterPanelActionsCode = {
            LIST_ACTION_CODE,
            CONTACTAR_ACTION_CODE,
            VER_DIRECCIONES_ACTION_CODE,
            CONSULTAR_SEGUIMIENTO_ACTION_CODE,
            UPDATE_ACTION_CODE,
            TO_EXCEL_ACTION
    };

    public static final String[] collaboratorPanelActionsCode = {
            LIST_ACTION_CODE,
            CONTACTAR_ACTION_CODE,
            VER_DIRECCIONES_ACTION_CODE,
            CONSULTAR_SEGUIMIENTO_ACTION_CODE,
            VER_OBSERVACIONES_ACTION_CODE,
            TO_EXCEL_ACTION
    };
    public static final String[] gerentePanelActionsCode = {
            LIST_ACTION_CODE,
            TO_EXCEL_ACTION
    };

    public static final String[] regionActionsCode = {
            LIST_ACTION_CODE,
            UPDATE_ACTION_CODE,
            RECORD_ACTION_CODE
    };

    public static final String[] directoraActionsCode = {
            LIST_ACTION_CODE,
            UPDATE_ACTION_CODE,
            TO_EXCEL_ACTION,
    };

    public static final String[] serviciosActionsCode = {
            LIST_ACTION_CODE,
            UPDATE_ACTION_CODE
    };

    public static final String[] ruletaActionsCode = {
            LIST_ACTION_CODE,
            RECORD_ACTION_CODE,
            UPDATE_ACTION_CODE,
            DELETE_ACTION_CODE
    };

    public static final String[] logIntegracionActionsCode = {
            LIST_ACTION_CODE,
            VIEW_LOG_ACTION
    };

    public static final String[] reporteCreditosActionsCode = {
            LIST_ACTION_CODE,
            TO_EXCEL_ACTION
    };
}
