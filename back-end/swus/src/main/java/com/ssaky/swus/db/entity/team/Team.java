package com.ssaky.swus.db.entity.team;

import com.ssaky.swus.api.request.team.UpdateBoardReq;
import com.ssaky.swus.api.request.team.WriteBoardReq;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@Entity
public class Team extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teamId;            // 그룹id

    private String teamName;       // 그룹명

    @Lob
    private String teamInfo;       // 그룹설명

    @NotBlank(message = "카테고리를 입력해주세요")
    private String category;        // 카테고리 "S"/"M"

    private LocalDate beginAt;      // 시작날짜

    private LocalDate endAt;        // 종료날짜

    @NotBlank(message = "요일을 입력해주세요")
    private String day;             // 요일 "0000000"

    @Column(nullable = false)
    private LocalTime startTime;    // 시작시간

    @Column(nullable = false)
    private LocalTime finishTime;   // 종료시간

    private int number;             // 현그룹인원(유동)

    private String team_done;      // 그룹 스터디완료여부

    @Builder
    public Team(int teamId) {
        this.teamId = teamId;
    }

    @Builder
    public Team(WriteBoardReq req) {
        this.category = req.getCategory();
        this.day = req.getDay();
        this.beginAt = req.getBeginAt();
        this.endAt = req.getEndAt();
        this.startTime = req.getStartTime();
        this.finishTime = req.getFinishTime();
    }

    @Builder
    public Team(int teamId, String teamName, String teamInfo, String category, LocalDate beginAt, LocalDate endAt, String day, LocalTime startTime, LocalTime finishTime, int number, String team_done) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamInfo = teamInfo;
        this.category = category;
        this.beginAt = beginAt;
        this.endAt = endAt;
        this.day = day;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.number = number;
        this.team_done = team_done;
    }

    public void update(UpdateBoardReq updateBoardReq) {
        this.category = updateBoardReq.getCategory();
        this.day = updateBoardReq.getDay();
        this.beginAt = updateBoardReq.getBeginAt();
        this.endAt = updateBoardReq.getEndAt();
        this.startTime = updateBoardReq.getStartTime();
        this.finishTime = updateBoardReq.getFinishTime();
    }

    //    // 일대일 양방향 관계 매핑, 읽기 전용 필드
//    @OneToOne(mappedBy = "team")
//    private Board board;

}
