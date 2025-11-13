package com.cometogo.ctg.group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 동호회 일정 정보를 담는 DTO.
 * 'schedule' 테이블의 정보를 매핑.
 */
@Data // getter, setter, equals, hashCode, toString 자동 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자 자동 생성
public class ScheduleDto {

    private Long scheduleId;    // 일정 고유 ID (schedule.schedule_id)
    private Long groupId;       // 소속 동호회 ID (schedule.group_id)
    private Long userId;        // 일정 등록 사용자 ID (schedule.user_id)
    private String title;       // 일정 제목 (schedule.title)
    private String content;     // 일정 내용 (schedule.content)
    private Timestamp startTime; // 일정 시작 일시 (schedule.start_time)
    private Timestamp endTime;   // 일정 종료 일시 (schedule.end_time)

    public Timestamp getDate() {
       return startTime;
    }
}


