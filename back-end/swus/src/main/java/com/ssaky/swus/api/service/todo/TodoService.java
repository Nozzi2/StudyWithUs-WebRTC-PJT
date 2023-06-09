package com.ssaky.swus.api.service.todo;

import com.ssaky.swus.api.request.todo.TodoCreateReq;
import com.ssaky.swus.api.request.todo.TodoUpdateReq;
import com.ssaky.swus.api.response.todo.DailyTodoResp;
import com.ssaky.swus.api.response.todo.TodoGetResp;
import com.ssaky.swus.api.response.todo.TodoJandiResp;
import com.ssaky.swus.common.error.exception.InvalidValueException;
import com.ssaky.swus.common.utils.DateUtils;
import com.ssaky.swus.db.entity.todo.JandiTodo;
import com.ssaky.swus.db.entity.todo.TodoPrivate;
import com.ssaky.swus.db.repository.todo.JandiTodoRepository;
import com.ssaky.swus.db.repository.todo.MemberTodoCount;
import com.ssaky.swus.db.repository.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class TodoService {

    private final TodoRepository todoRepository;
    private final JandiTodoRepository jandiTodoRepository;

    @Transactional
    public int save(TodoCreateReq req, int memberId) {
        TodoPrivate todo = new TodoPrivate(req, memberId);
        return todoRepository.save(todo);
    }

    public List<TodoGetResp> getList(int memberId){
        return todoRepository.findList(memberId);
    }

    @Transactional
    public void update(int num, TodoUpdateReq req, int memberId) {
        Optional<TodoPrivate> todoPrivate = todoRepository.findOne(num, memberId);
        if (todoPrivate.isPresent()){
            todoPrivate.get().update(req);
        } else {
            throw new InvalidValueException("invalid num");
        }
    }

    @Transactional
    public void delete(int num, int memberId) {
        Optional<TodoPrivate> todoPrivate = todoRepository.findOne(num, memberId);
        if (todoPrivate.isPresent()) {
            todoRepository.delete(todoPrivate.get());
        } else{
            throw new InvalidValueException("invalid num");
        }
    }

    /**
     * 6시 기준 투두 초기화, Jandi 기록 입력
     */
    @Scheduled(cron = "0 0 06 * * ?", zone = "Asia/Seoul")
    @Transactional
    protected void dailyUpdate() {
        // 1. 멤버별로 Todo가 Y인 개수 구해오기
        List<MemberTodoCount> todoCountList = todoRepository.findTodoCountGroupByMember();

        // 2. todoDone이 Y인 애들 모두 날리기
        todoRepository.deleteAllDoneInQuery();

        // 3. Jandi 기록 입력
        saveAllDailyTodoCount(todoCountList);

    }

    /**
     * 모든 사용자의 투두 잔디 기록 저장하기
     * @param todoCountList
     */
    @Transactional
    protected void saveAllDailyTodoCount(List<MemberTodoCount> todoCountList) {
        Date yesterday = DateUtils.getYesterday();
        for (MemberTodoCount m: todoCountList) {
            JandiTodo jandiTodo = new JandiTodo(yesterday, m);
            jandiTodoRepository.save(jandiTodo);
        }
    }

    /**
     * 올해 잔디 결과 불러오기
     * @param memberId
     * @return
     */
    public TodoJandiResp getJandiRecords(int memberId) {
        
        // 서울 ZoneId로 가져오기
        LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
        // 1년 전 날짜 가져오기
        LocalDate before = now.minusYears(1);

        Date fromDate = Date.valueOf(before);
        Date toDate = Date.valueOf(now);

        List<DailyTodoResp> todoRecords = jandiTodoRepository.findByIdMemberIdAndIdStudyAtBetween(memberId, fromDate, toDate, DailyTodoResp.class);

        TodoJandiResp resp = TodoJandiResp.builder().todoRecords(todoRecords).build();

        return resp;
    }

}
