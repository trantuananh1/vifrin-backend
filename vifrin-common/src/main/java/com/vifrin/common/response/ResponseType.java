package com.vifrin.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseType {
    OK(200, "ok"),
    CREATED(201, "created"),
    ACCEPTED(202, "accepted"),
    NO_CONTENT(204, "no content"),
    BAD_REQUEST(400, "bad request"),
    UNAUTHORIZED(401, "unauthorized"),
    FORBIDDEN(403, "forbidden"),
    NOT_FOUND(404, "not found"),
    CONFLICT(409, "conflict"),

    SUCCESS(200, "success"),
    FAIL(400, "fail"),
    EMAIL_ALREADY_EXISTS(1001, "email already exists"),
    USERNAME_ALREADY_EXISTS(1002, "username already exists"),
    PASSWORD_IS_INCORRECT(1003, "password is incorrect"),
    EMAIL_IS_INCORRECT(1004, "email is incorrect"),
    USERNAME_NOT_EXISTS(1005, "username not exists");

    private final int code;
    private final String message;
}
