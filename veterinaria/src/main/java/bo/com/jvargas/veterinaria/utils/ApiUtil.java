package bo.com.jvargas.veterinaria.utils;

import bo.com.jvargas.veterinaria.datos.model.dto.ResponseBody;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class ApiUtil {

    public static <T> ResponseBody<T> buildResponseWithDefaults(T data) {
        return ResponseBody.<T>builder()
                .code("OK_CODE")
                .message("OK_MESSAGE")
                .data(data)
                .build();
    }

    public static <T> ResponseBody<T> buildResponseWithoutBody() {
        return ResponseBody.<T>builder()
                .code("OK_CODE")
                .message("OK_MESSAGE")
                .build();
    }

    public static <T> ResponseBody<T> buildSuccessResponse(T data, String message) {
        return ResponseBody.<T>builder()
                .code("OK_CODE")
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ResponseBody<T> buildResponse(T data, String code, String message) {
        return ResponseBody.<T>builder()
                .code(code)
                .message(message)
                .data(data)
                .build();
    }
}
