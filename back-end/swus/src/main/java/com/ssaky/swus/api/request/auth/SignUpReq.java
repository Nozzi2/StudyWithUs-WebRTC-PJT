package com.ssaky.swus.api.request.auth;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class SignUpReq {

    private String email;
    private String password;
    private String nickname;

    private int questionId;
    private String answer;

    @Builder
    public SignUpReq(String email, String password, String nickname, int questionId, String answer){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.questionId = questionId;
        this.answer = answer;
    }



}
