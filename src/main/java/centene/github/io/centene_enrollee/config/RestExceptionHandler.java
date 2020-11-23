package centene.github.io.centene_enrollee.config;

import centene.github.io.centene_enrollee.rest.EnrolleeController;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(CustomNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(final CustomNotFoundException exception) {
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setException(exception.getClass().getSimpleName());
        errorResponse.setMessage(exception.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValid(final MethodArgumentNotValidException exception) {
        final BindingResult bindingResult = exception.getBindingResult();
        final List<FieldErrorResponse> fieldErrorResponses = bindingResult.getFieldErrors()
                .stream()
                .map(error -> {
                    final FieldErrorResponse fieldError = new FieldErrorResponse();
                    fieldError.setErrorCode(error.getCode());
                    fieldError.setField(error.getField());
                    return fieldError;
                })
                .collect(Collectors.toList());
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setException(exception.getClass().getSimpleName());
        errorResponse.setFieldErrors(fieldErrorResponses);
        return errorResponse;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(final Throwable exception) {
        exception.printStackTrace();
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setException(exception.getClass().getSimpleName());
        return errorResponse;
    }

    @Getter
    @Setter
    public class FieldErrorResponse {

        private String field;
        private String errorCode;

    }

    @Getter
    @Setter
    public class ErrorResponse {

        private Integer httpStatus;
        private String exception;
        private List<FieldErrorResponse> fieldErrors;
        private String message;
    }

}
