package com.ssaky.swus.api.domain.user;

import com.ssaky.swus.api.controller.auth.SignUpDTO;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private int id;

    private String email;
    private String password;
    private String nickname;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    private String answer;

    private String token;

    public void signup(SignUpDTO form){
        this.email = form.getEmail();
        this.password = form.getPassword();
        this.nickname = form.getNickname();
        this.answer = form.getAnswer();

    }
}
