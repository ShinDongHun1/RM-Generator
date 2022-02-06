# 깃허브 ReadMe 자동 생성기

<br/>
<br/>

### (적용 사진)
![ㅇ](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_16.png)

<br/>

<br/>



## 사용 방법

<br/>

아래 jar 파일을 다운로드 해주세요. (경로는 상관 없으나, 기억은 해주셔야 합니다.)

**ReadMeGenerator-1.0-SNAPSHOT.jar**
![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_15.png)

<br/>

<br/>

### (인텔리제이일 경우 설정)

적용을 원하시는 프로젝트에 들어가서

**File ->** 

**Project Structure ->**

**Modules ->**

**프로젝트 선택 ->**

**Dependencies ->**

**+ 버튼 클릭 ->**

**JARs or Directories... ->**

**위의 jar 파일을 추가 ->**

**OK 클릭**

<br/>

![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img.png)

<br/><br/>



![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_1.png)
(저는 ReadMeGenerator-1.0-SNAPSHOT.jar를 이미 추가를 해두었기 때문에 존재하는 모습이고, 지금 추가가 안되있어야 정상입니다.)

<br/><br/>

![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_2.png)

<br/><br/>

![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_3.png)

<br/><br/><br/><br/>





## 어노테이션 적용

아래와 같이 문제 상단에 @BOJ 어노테이션을 적용해주세요.

<br/>

![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_4.png)

<br/>
<br/>
<br/>

## ReadMe 파일 생성
<br/>
<br/>

### 1. 자신만의 Repo에 적용

자신만의 리포지토리에 저장하는 방법은 두가지입니다.

<br/>

<br/>

### 1 - 1. PersonalGitRepositoryUrlParser 사용

Username과, 레포지토리의 이름, 그리고 메인 브랜치의 이름을 아래와 같이 설정 후 실행해주시면 끝납니다.<br/>
setTitle()은 README 의 제일 위에 쓰여질 제목을 지정해주는 메서드입니다.

![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_5.png)



<br/><br/>

그런데 해당 클래스는 아래와 같이 src등의 중간 다른 폴더가 있으면 적용되지 않습니다.<br/>

![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_6.png)

<br/><br/>

다음과 같은 구조에서만 적용됩니다. (흰색은 제 REPO가 아니라 가렸습니다.)<br/>

![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_7.png)





<br/>만약 위의 클래스로 적용이 안되면 다음 방법을 사용하시면 됩니다.

<br/><br/>

### 1 - 2. AbsolutePathBeforePackageNameParser 사용



이는 패키지 이름 전까지의 경로를 직접 지정해주어 주소를 반환해줍니다.<br/>




<br/><br/>아래와 같이 package전까지의 주소를 복사한 후 setAbsolutePathBeforePackageName를 통해 설정해주시면 됩니다.<br/>

![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_8.png)
<br/>
<br/>
![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_9.png)




<br/>
<br/>
<br/>

### 2. 하루하나 알고리즘 Repo에 적용

<br/>

하루하나 알고리즘에 적용할 때는 다음과 같은 형식으로 문제 코드를 저장해주셔야만 사용 가능합니다.<br/>

![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_10.png)

<br/>위 사진과 같이, 하루하나 알고리즘 Repo에 들어오자마자 자신의 이름이 있는 폴더가 있어야 하며,<br/><br/>

![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_11.png)

<br/>폴더에 들어갔을 때, 추가적인 폴더 없이 바로 문제 코드들이 나열되어 있어야 합니다.

<br/>

(추가적인 폴더가 있어도 아래와 같이 클래스 상단에 적혀있는 데로만 존재하며, 이외의 다른 폴더가 없는 경우라면 상관없습니다.)<br/>

![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_12.png)



<br/><br/>사용 방법은 아래와 같습니다.<br/>

![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_13.png)

<br/>setUsername 부분에는 자신의 폴더 이름을 입력해 주시면 됩니다.

만약 이 방식이 안된다면 위의 1-2 에서 언급한 **AbsolutePathBeforePackageNameParser** 를 사용해주세요.

<br/><br/>

생성되는 README.md 파일은 다음과 같습니다.<br/>

![image-20220206225110452](https://github.com/ShinDongHun1/RM-Generator/blob/main/image/img_14.png)