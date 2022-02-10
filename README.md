## RM - Generator

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FShinDongHun1%2FRM-Generator%2Ftree%2Fmain&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)





### ⭐ @애노테이션을 통한 알고리즘 풀이 README 자동 생성기



![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img.png)









## ❔ HOW TO USE



### ✔ Java version :  11버전 이상





### ✔ Download jar file

[다운로드 링크](https://github.com/ShinDongHun1/RM-Generator/blob/main/ReadMeGenerator-1.0.jar)

저장 경로는 상관없습니다.





### ✔ Settings (인텔리제이)

**File( 맥의 경우 Preferences) ➡ Project Structure ➡ Modules ➡ 적용할 프로젝트 선택 ➡ Dependencies ➡ ➕ 버튼 클릭 ➡ Jar or Directories ➡ 다운받은 jar파일 적용 ➡ OK**





### ✔ 적용



### 🔍 클래스 이름 규칙

- [ **XXX문제번호** ] 의 형식으로 만들어 주셔야 합니다. (EX : 백준1000, Q1000 등)
- 이게 싫으시다면, @BOJ의 number 속성에 문제 번호를 직접 입력해 주셔야 합니다.



#### 예시

![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_1.png)



![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_2.png)





### 🔍 속성

- **number** : 문제의 번호를 입력합니다. 입력하지 않으면 클래스의 이름에서 문제 번호를 추출합니다. 만약 클래스의 이름 형식이 올바르지 않다면 예외를 발생시킵니다.
- **solveDate** : 문제를 푼 날짜를 입력할 수 있습니다. 입력하지 않는다면 오늘 날짜로 자동 저장됩니다. 
  - solveDate는 애노테이션으로, @SolveDate 애노테이션 속에는 **year**, **month**, **day** 속성이 존재합니다.
  - 이중 하나라도 입력하지 않을 시, 다른 속성에 무엇을 입력했든 오늘 날짜로 자동 저장됩니다.











## ❔ 궁금할만한 사항



### ❓ README 파일은 어디에 생성되나요?

####     ✔ 현재 프로젝트의 최상단에 생성됩니다

![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_3.png)





### ❓ 이미 README에 작성된 문제의 풀이 날짜를 수정할 방법은 없나요?

####    ✔ 애노테이션을 제거한 후, RM-Generator를 실행시키면 README 파일에서 문제가 삭제됩니다. 이후 다시 @BOJ를 붙이고 solveDate 속성을 통해 원하는 날짜를 입력해주세요!







## 📫 Contact Me



Kakao : huipulco



