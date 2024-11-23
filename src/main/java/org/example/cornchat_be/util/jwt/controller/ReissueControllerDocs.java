package org.example.cornchat_be.util.jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "토큰", description = "RefreshToken 관련 API입니다.")
public interface ReissueControllerDocs {

    @Operation(summary = "토큰 재발급", description = "액세스 토큰과 refresh토큰을 재생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 재발급 성공"),
            @ApiResponse(responseCode = "400", description = "refresh토큰이 비어있습니다."),
            @ApiResponse(responseCode = "400", description = "refresh토큰이 만료되었습니다."),
            @ApiResponse(responseCode = "400", description = "refresh토큰이 유효하지 않습니다."),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 사용자 인증 정보입니다."),
            @ApiResponse(responseCode = "500", description = "서버에러"),
    })
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response);
}
