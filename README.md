# 깃모지 설명
| 아이콘   | 코드        | 설명                                  | 원문                             |
|----------|-------------|---------------------------------------|----------------------------------|
| 🎨       | `:art:`     | 코드의 구조/형태 개선                   | Improve structure / format of the code. |
| ⚡️       | `:zap:`     | 성능 개선                              | Improve performance.              |
| 🔥       | `:fire:`    | 코드/파일 삭제                         | Remove code or files.            |
| 🐛       | `:bug:`     | 버그 수정                              | Fix a bug.                        |
| 🚑       | `:ambulance:` | 긴급 수정                          | Critical hotfix.                  |
| ✨       | `:sparkles:` | 새 기능                                | Introduce new features.           |
| 💄       | `:lipstick:` | UI/스타일 파일 추가/수정               | Add or update the UI and style files. |
| 🎉       | `:tada:`    | 프로젝트 시작                          | Begin a project.                  |
|🚀        | `:rocket:`    | CI/CD                         | Deploying stuff                 |
| ✅       | `:white_check_mark:` | 테스트 추가/수정                  | Add or update tests.              |
| 💚       | `:green_heart:` | CI 빌드 수정                         | Fix CI Build.                     |
| ♻️       | `:recycle:` | 코드 리팩토링                           | Refactor code.                    |
| 🔨       | `:hammer:`  | 개발 스크립트 추가/수정                | Add or update development scripts. |
| 🔀       | `:twisted_rightwards_arrows:` | 브랜치 합병                   | Merge branches.                  |


## 로컬에서 개인 브랜치 생성하기

local workspace에 'feature'라는 이름으로 브랜치 생성 <br>
<code>git branch -b feature</code>
<br><br>

## 로컬에서 브랜치 작업후 원격저장소에 반영하기

로컬 브랜치가 있는 폴더에서 개인작업을 마친 후 공동 저장소에 반영한다.
<br><br>

### main브랜치에 develop브랜치 작업 반영하기

1. <code>git checkout develop</code> - develop브랜치로 전환
2. <code>git add .</code>
3. <code>git commit -m "message"</code>
4. <code>git pull origin develop</code> - 원격저장소 develop의 최신 정보를 로컬에 업데이트
5. <code>git checkout main</code> - main브랜치로 전환
6. <code>git pull origin main</code> - 원격저장소 main의 최신 정보를 로컬에 업데이트
7. <code>git merge develop</code> - main브랜치에 develop브랜치를 병합
8. <code>git push origin main</code> - main브랜치의 변경 사항을 원격 저장소에 반영
<br><br>
