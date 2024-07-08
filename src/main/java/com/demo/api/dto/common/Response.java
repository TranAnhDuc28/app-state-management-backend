package com.demo.api.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.*;

@Getter
@Setter
@SuperBuilder
@JsonInclude(NON_NULL) //  chỉ thị cho Jackson bỏ qua (không bao gồm) các trường có giá trị null khi chuyển đổi đối tượng thành JSON.
public class Response {

    protected LocalDateTime timeStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String reason; // lý do
    protected String message;
    protected String developerMessage;
    protected Map<?, ?> data;

}
