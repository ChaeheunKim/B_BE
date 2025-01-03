package org.example.global.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

        // External Server Error(400)
        EXTERNAL_SERVER_ERROR(HttpStatus.BAD_GATEWAY, "서버 외부 에러입니다."),
        // Invalid Argument Error(400)
        INVALID_SOCKET_ERROR(HttpStatus.BAD_REQUEST, "유효하지 않은 소켓입니다."),
        MISSING_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 누락되었습니다."),
        INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "요청에 유효하지 않은 인자입니다."),
        INVALID_PARAMETER_FORMAT(HttpStatus.BAD_REQUEST, "요청에 유효하지 않은 인자 형식입니다."),
        INVALID_HEADER_ERROR(HttpStatus.BAD_REQUEST, "유효하지 않은 헤더입니다."),
        MISSING_REQUEST_HEADER(HttpStatus.BAD_REQUEST, "필수 요청 헤더가 누락되었습니다."),
        BAD_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "잘못된 요청 파라미터입니다."),
        UNSUPPORTED_MEDIA_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 미디어 타입입니다."),
        BAD_REQUEST_JSON(HttpStatus.BAD_REQUEST, "잘못된 JSON 형식입니다."),
        DUPLICATED_RESOURCE(HttpStatus.BAD_REQUEST, "중복된 리소스입니다."),
        ENTITY_CREATION_FAILED(HttpStatus.BAD_REQUEST, "엔티티 생성에 실패했습니다."),




        // Unauthorized Error(401)
        FAILURE_LOGIN(HttpStatus.UNAUTHORIZED, "잘못된 아이디 또는 비밀번호입니다."),
        EXPIRED_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
        INVALID_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
        TOKEN_MALFORMED_ERROR(HttpStatus.UNAUTHORIZED, "토큰이 올바르지 않습니다."),
        TOKEN_TYPE_ERROR(HttpStatus.UNAUTHORIZED, "토큰 타입이 일치하지 않거나 비어있습니다."),
        TOKEN_UNSUPPORTED_ERROR(HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰입니다."),
        TOKEN_GENERATION_ERROR(HttpStatus.UNAUTHORIZED, "토큰 생성에 실패하였습니다."),
        TOKEN_UNKNOWN_ERROR(HttpStatus.UNAUTHORIZED, "알 수 없는 토큰입니다."),
        FAILURE_PASSWORD(HttpStatus.UNAUTHORIZED, "현재 비밀번호와 다릅니다"),




        // Access Denied Error(403)
        ACCESS_DENIED(HttpStatus.FORBIDDEN, "승인되지 않았습니다"),
        NOT_MATCH_USER(HttpStatus.FORBIDDEN, "해당 사용자가 일치하지 않습니다."),




        // Not Found Error(404)
        NOT_FOUND_END_POINT(HttpStatus.NOT_FOUND, "존재하지 않는 API 엔드포인트입니다."),
        NOT_FOUND_RESOURCE(HttpStatus.NOT_FOUND, "해당 리소스가 존재하지 않습니다."),
        NOT_FOUND_AUTHORIZATION_HEADER(HttpStatus.NOT_FOUND, "Authorization 헤더가 존재하지 않습니다."),
        NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자입니다."),
        NOT_FOUND_POST(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
        NOT_FOUND_ENTITY(HttpStatus.NOT_FOUND, "존재하지 않은 엔티티입니다."),
        NOT_FOUND_SCHEDULE(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다."),


        // Method Not Allowed Error(405)
        METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메소드입니다."),


        // Internal Server Error(500)
        INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러입니다."),
        INTERNAL_DATA_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 데이터 에러입니다."),
        UPLOAD_FILE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패하였습니다.");



        private final HttpStatus httpStatus;
        private final String message;
    }

