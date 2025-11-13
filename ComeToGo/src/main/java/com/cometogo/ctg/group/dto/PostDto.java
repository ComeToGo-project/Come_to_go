package com.cometogo.ctg.group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 동호회 게시글 정보를 담는 DTO.
 * 'group_board' 테이블의 정보를 매핑합니다.
 */
@Data // getter, setter, equals, hashCode, toString 자동 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자 자동 생성
public class PostDto {
    private Long boardId;       // 게시글 고유 ID (group_board.board_id)
    private Long groupId;       // 소속 동호회 ID (group_board.group_id)
    private Long userId;        // 게시글 작성자 ID (group_board.user_id)
    private String title;       // 게시글 제목 (group_board.title)
    private String content;     // 게시글 내용 (group_board.content)
    private Timestamp createdAt; // 게시글 생성일시 (group_board.created_at)
    private int viewCount;      // 게시글 조회수 (group_board.view_count)


    public String getSummary() {
        if (content == null) return "";
        // 내용이 50자보다 길면 50자까지 자르고 "..." 추가
        return content.length() > 50 ? content.substring(0, 50) + "..." : content;
    }

}
