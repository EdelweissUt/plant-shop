package com.plant.plantshopbe.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS(1009, "Invalid credentials, please try again.", HttpStatus.BAD_REQUEST),
    PASSWORD_EXISTED(1010, "Password existed", HttpStatus.BAD_REQUEST),
    FILE_MAX(1011, "Max file size is 2MB", HttpStatus.BAD_REQUEST),
    FILE_NOT_SUPPORT(1012, "File type not supported", HttpStatus.BAD_REQUEST),
    UPLOAD_FAIL(1013, "Failed to upload file", HttpStatus.BAD_REQUEST),
    DELETE_FILE_FAIL(1014, "Failed to delete file", HttpStatus.BAD_REQUEST),
    NOT_FOUND(1015, "Resource not found", HttpStatus.NOT_FOUND),
    MEDIA_NOT_EXISTED(1016, "Media not existed", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_EXISTED(1017, "Category not existed", HttpStatus.BAD_REQUEST),

    BANK_NOT_EXITED(1018,"Bank not existed", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(1019,"Token not valid", HttpStatus.BAD_REQUEST ),
    TOKEN_EXPIRED(1020,"Token expired" ,HttpStatus.BAD_REQUEST  ),
    PRODUCT_NOT_FOUND(1021, "product not existed",HttpStatus.BAD_REQUEST  ),
    INVALID_QUANTITY(1022, "inValid quatity", HttpStatus.BAD_REQUEST ),
    INSUFFICIENT_STOCK(1023,"insufficient stock" , HttpStatus.BAD_REQUEST ),
    DISCOUNT_NOT_FOUND(1024,"Discount code not existed" ,HttpStatus.BAD_REQUEST),
    DISCOUNT_INVALID(1025,"Discount invalid" ,HttpStatus.BAD_REQUEST ),
    ORDER_CANNOT_BE_CANCELLED(1026,"Order cannot be cancelled" , HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(1027,"Order not found" ,HttpStatus.BAD_REQUEST ),
    ALREADY_EXIST(1028,"Already exist" ,HttpStatus.BAD_REQUEST  ),
    REVIEW_ALREADY_EXISTS(1029,"Review already exist" , HttpStatus.BAD_REQUEST ),
    ORDER_ITEM_NOT_FOUND(1030, "Order item not found",  HttpStatus.BAD_REQUEST ),
    REVIEW_NOT_EXISTED(1031,"Review not existed" , HttpStatus.BAD_REQUEST ),
    NOT_ENOUGH_POINTS(1032,"Not enough points" , HttpStatus.BAD_REQUEST);


    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}