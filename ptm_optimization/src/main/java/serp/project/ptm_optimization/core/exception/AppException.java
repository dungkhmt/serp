/**
 * Author: QuanTuanHuy
 * Description: Part of Serp Project
 */

package serp.project.ptm_optimization.core.exception;

import lombok.Getter;
import lombok.Setter;
import serp.project.ptm_optimization.core.domain.constant.Constants;
import serp.project.ptm_optimization.core.domain.constant.ErrorMessage;

@Getter
@Setter
public class AppException extends RuntimeException {
    private Integer code;

    public AppException(String message) {
        super(message);
        this.code = getCodeBaseOnMessage(message);
    }

    public AppException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    private Integer getCodeBaseOnMessage(String message) {
        return switch (message) {
            case ErrorMessage.UNAUTHORIZED -> Constants.HttpStatusCode.UNAUTHORIZED;
            case ErrorMessage.FORBIDDEN -> Constants.HttpStatusCode.FORBIDDEN;
            case ErrorMessage.NOT_FOUND -> Constants.HttpStatusCode.NOT_FOUND;
            case ErrorMessage.INTERNAL_SERVER_ERROR -> Constants.HttpStatusCode.INTERNAL_SERVER_ERROR;
            default -> Constants.HttpStatusCode.BAD_REQUEST;
        };
    }
}