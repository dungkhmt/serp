package serp.project.account.core.domain.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {

    @UtilityClass
    public static class HttpStatus {
        public static final String ERROR = "error";
        public static final String SUCCESS = "success";
    }

    @UtilityClass
    public static class ErrorMessage {
        public static final String OK = "OK";
        public static final String INVALID = "Invalid";
        public static final String NOT_FOUND = "Not Found";
        public static final String ALREADY_EXISTS = "Already Exists";
        public static final String UNAUTHORIZED = "Unauthorized";
        public static final String FORBIDDEN = "Forbidden";
        public static final String BAD_REQUEST = "Bad Request";
        public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    }

}
