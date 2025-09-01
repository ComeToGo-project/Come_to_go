# 오며가며 (ComeToGo) – 작업용(develop)

이 브랜치는 팀원들의 작업/공유 공간이다.

## 폴더 구조
- `members/` : 개인별 작업 공간 (날짜/기능 단위로 하위폴더 생성)
- `weekly_updates/` : 주간 합본 임시 저장 → main으로 머지하기 전 단계
- `shared/` : 기획서, 발표자료, 공용 에셋
- `tools/` : DB 초기화 스크립트, 빌드/배포 도구

## 작업 규칙
- 각자 `members/이름` 안에서만 작업 업로드
- 공용 자료는 `shared/`에 업로드
- 주간 합본은 담당자가 `weekly_updates/`에 모은 뒤 main으로 머지
- 커밋 메시지 컨벤션: feat, fix, docs, style, refactor, test, chore
