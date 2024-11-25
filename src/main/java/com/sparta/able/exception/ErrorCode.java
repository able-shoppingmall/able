package com.sparta.able.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_FORMAT(HttpStatus.BAD_REQUEST, "ERR001","요청 값의 형식이 맞지 않습니다."),
    DELETED_USER(HttpStatus.UNAUTHORIZED, "ERR002", "삭제된 유저입니다.."),
    PRESENT_USER(HttpStatus.UNPROCESSABLE_ENTITY, "ERR003", "이미 등록된 사용자입니다."),
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "ERR004", "유저가 올바르지 않습니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND,"ERR005","유저를 찾을 수 없습니다."),
    NONE_PERMISSION(HttpStatus.UNAUTHORIZED,"ERR006","권한이 없습니다."),
    NOT_FOUND_STORE(HttpStatus.NOT_FOUND,"ERR007","가게를 찾을 수 없습니다."),
    CLOSED_STORE(HttpStatus.BAD_REQUEST,"ERR008","가게의 오픈 시간이 아닙니다."),
    MINIMUM_AMOUNT_REQUIRED(HttpStatus.BAD_REQUEST, "ERR009", "최소 주문 금액을 충족하지 않습니다."),
    NOT_FOUND_MENU(HttpStatus.NOT_FOUND,"ERR010","메뉴를 찾을 수 없습니다."),
    NOT_FOUND_ORDER(HttpStatus.NOT_FOUND,"ERR011","주문을 찾을 수 없습니다."),
    ORDER_NOT_COMPLETED(HttpStatus.BAD_REQUEST, "ERR012", "리뷰는 배달 완료된 주문에 대해서만 작성할 수 있습니다."),
    UNAUTHORIZED_REVIEWER(HttpStatus.FORBIDDEN, "ERR013", "주문한 사용자만 리뷰를 작성할 수 있습니다."),
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND,"ERR014","주문을 찾을 수 없습니다."),
    UNAUTHORIZED_REVIEW_EDIT(HttpStatus.FORBIDDEN, "ERR015", "자신의 리뷰만 수정/삭제할 수 있습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND,"ERR016","값을 찾을 수 없습니다."),
    STORE_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "ERR017", "소유할 수 있는 가게의 최대 개수를 초과했습니다."),

    NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, "ERR018", "상품을 찾을 수 없습니다."),
    ;


    private final HttpStatus httpStatus;
    private final String status;
    private final String message;
}
