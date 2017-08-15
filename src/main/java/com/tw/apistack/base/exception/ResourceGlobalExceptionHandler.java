package com.tw.apistack.base.exception;

import com.tw.apistack.base.exception.config.ApiCommonProperties;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by jxzhong on 12/16/16.
 */
@ControllerAdvice
@Order(value = Ordered.LOWEST_PRECEDENCE)
@EnableConfigurationProperties({ApiCommonProperties.class})
public class ResourceGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApiCommonProperties apiCommonProperties;

    @Autowired
    public ResourceGlobalExceptionHandler(ApiCommonProperties apiCommonProperties) {
        this.apiCommonProperties = apiCommonProperties;
        logger.info("Resgitered");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleResourceGlobalException(Exception e, WebRequest request) {
        HttpStatus httpStatus = (e instanceof BadRequestException)
                ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR;
        return handleExceptionInternal(e, null, new HttpHeaders(), httpStatus, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        ResponseEntity<Object> responseEntity = super.handleExceptionInternal(e,
                createErrorResponseDTO(e, status), headers, status, request);
        logger.info("Response: " + responseEntity);
        return responseEntity;
    }

    private ErrorResponseDTO createErrorResponseDTO(Exception e, HttpStatus status) {
        ErrorResponseDTO errorResponseDTO;

        if (e instanceof MethodArgumentNotValidException) {
            errorResponseDTO = buildErrorResponseDTOForMethodArgumentNoteValidException((MethodArgumentNotValidException) e, status);
        } else if (e instanceof BindException) {
            errorResponseDTO = buildErrorResponseDTOForBindException((BindException) e, status);
        } else {
            errorResponseDTO = buildErrorResponseDTOForOtherException(e, status);
        }

        return errorResponseDTO;
    }

    private ErrorResponseDTO buildErrorResponseDTOForMethodArgumentNoteValidException(MethodArgumentNotValidException e, HttpStatus status) {
        BindingResult bindingResult = e.getBindingResult();
        return buildErrorResponseDTOFromBindingresult(status, bindingResult);
    }

    private ErrorResponseDTO buildErrorResponseDTOForBindException(BindException e, HttpStatus status) {
        BindingResult bindingResult = e.getBindingResult();
        return buildErrorResponseDTOFromBindingresult(status, bindingResult);
    }

    private ErrorResponseDTO buildErrorResponseDTOForOtherException(Exception e, HttpStatus status) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();

        String errorMsg = "Exception occurred: " + e.getMessage();
        Throwable rootCause = ExceptionUtils.getRootCause(e);
        String logErrorMsg = errorMsg;

        if (rootCause == null) {
            addErrorResponse(errorResponseDTO, status, e);
        } else {
            String rootCauseMsg = ExceptionUtils.getMessage(rootCause);

            if (rootCause instanceof HttpStatusCodeException) {
                String responseBody = ((HttpStatusCodeException) rootCause).getResponseBodyAsString();
                logErrorMsg += ". Caused by: " + rootCauseMsg + ", with response: ";
                addErrorResponse(errorResponseDTO, status, e, rootCause, responseBody);
            } else {
                logErrorMsg += ". Caused by: " + rootCauseMsg;
                addErrorResponse(errorResponseDTO, status, e, rootCause);
            }
        }

        logErrorIfIs5xxError(e, status, logErrorMsg);

        return errorResponseDTO;
    }

    private ErrorResponseDTO buildErrorResponseDTOFromBindingresult(HttpStatus status, BindingResult bingingResult) {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();

        bingingResult.getFieldErrors().forEach(fieldError -> {
            addErrorResponse(errorResponseDTO, status, fieldError);
        });
        return errorResponseDTO;
    }

    private void addErrorResponse(ErrorResponseDTO errorResponseDTO, HttpStatus status, FieldError fieldError) {
        StringBuilder sb = new StringBuilder();
        sb.append("Field '")
                .append(fieldError.getField())
                .append("'");

        if (isIncludeCause()) {
            sb.append(": ")
                    .append(fieldError.getDefaultMessage());

            errorResponseDTO.addError(status.value(), status.getReasonPhrase() + ": " + sb.toString(), fieldError.toString());
        } else {
            errorResponseDTO.addError(status.value(), status.getReasonPhrase() + ": " + sb.toString());
        }
    }

    private void addErrorResponse(ErrorResponseDTO errorResponseDTO, HttpStatus status, Exception e, Throwable rootCause, String response) {

        if (isIncludeCause()) {

            errorResponseDTO.addError(status.value(), status.getReasonPhrase() + ": " + e.getMessage(), "Caused by: "
                    + ExceptionUtils.getMessage(rootCause) + ", with response " + response);
        } else {
            errorResponseDTO.addError(status.value(), status.getReasonPhrase());
        }
    }

    private boolean isIncludeCause() {
        return apiCommonProperties.getGlobalErrorResponse().isIncludeCause();
    }

    private void addErrorResponse(ErrorResponseDTO errorResponseDTO, HttpStatus status, Exception e, Throwable rootCause) {
        if (isIncludeCause()) {

            errorResponseDTO.addError(status.value(), status.getReasonPhrase() + ": " + e.getMessage(), "Caused by: "
                    + ExceptionUtils.getMessage(rootCause));
        } else {
            errorResponseDTO.addError(status.value(), status.getReasonPhrase());
        }
    }

    private void addErrorResponse(ErrorResponseDTO errorResponseDTO, HttpStatus status, Exception e) {
        if (isIncludeCause()) {
            errorResponseDTO.addError(status.value(), status.getReasonPhrase() + ": " + e.getMessage());
        } else {
            errorResponseDTO.addError(status.value(), status.getReasonPhrase());
        }
    }

    private void logErrorIfIs5xxError(Exception e, HttpStatus status, String logErrorMsg) {
        if (status.is5xxServerError()) {
            logger.error(logErrorMsg, e);
        }
    }

}
