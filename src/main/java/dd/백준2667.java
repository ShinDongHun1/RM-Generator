package dd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class 백준2667 {


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        final int MAP_SIZE = Integer.parseInt(br.readLine());//지도 크기

        List<ApartmentComplex> result = ApartmentMap.from(inputAndMakeApartmentMap(br, MAP_SIZE))
                                                      .grouping();//지도에 존재하는 아파트를 단지별로 묶기 -> BFS 수행

        //== 결과 출력 ==//
        System.out.println(result.size());
        result.stream()
                .map(ApartmentComplex::getApartmentCount)//아파트 단지에 속하는 아파트 개수로 변환
                .sorted(Comparator.naturalOrder())//오름차순 정렬
                .forEach(System.out::println);//출력
    }


    /**
     * 입력을 통해 아파트 지도 생성
     */
    private static ArrayList<ArrayList<Optional<Apartment>>> inputAndMakeApartmentMap(BufferedReader br, int MAP_SIZE) throws IOException {
        ArrayList<ArrayList<Optional<Apartment>>> tempMap = new ArrayList<>();//임지 지도

        for(int i = 0; i< MAP_SIZE; i++){
            tempMap.add(new ArrayList<Optional<Apartment>>(
                            Arrays.stream(br.readLine().split(""))
                                    .map(Apartment::checkAndMakeApartment)//1이면 아파트를 만들고, 1이 아니라면 null을 반환
                                    .collect(Collectors.toList())
                    //자바 16 버전 이상이라면 .collect(Collectors.toList()) -> .toList()로 변경 가능
                    )
            );
        }

        return tempMap;
    }
}


/**
 * 아파트 정보를 담은 지도
 */
class ApartmentMap {

    private final ArrayList<ArrayList<Optional<Apartment>>> apartmentMap;

    //== 생성자 ==//
    private ApartmentMap(ArrayList<ArrayList<Optional<Apartment>>> apartmentMap) {
        this.apartmentMap = apartmentMap;
    }

    //== 정적 팩터리 메서드 ==//
    public static ApartmentMap from(ArrayList<ArrayList<Optional<Apartment>>> apartmentMap){
        final int ROW_COUNT = apartmentMap.size();
        final int COLUMN_COUNT = apartmentMap.get(0).size();

        IntStream.range(0, ROW_COUNT).forEach(y ->{
            IntStream.range(0, COLUMN_COUNT).forEach(x ->{
                getOptApartmentByPosition((ArrayList<ArrayList<Optional<Apartment>>>) apartmentMap, y, x)
                        .ifPresent(apartment -> apartment.setPosition(x,y));
            });
        });

        return new ApartmentMap(apartmentMap);
    }



    private static Optional<Apartment> getOptApartmentByPosition(ArrayList<ArrayList<Optional<Apartment>>> apartmentMap, int y, int x) {
        return apartmentMap.get(y).get(x);
    }


    /**
     * 단지별로 아파트 묶기
     * @return 생성된 아파트 단지 List
     */
    public List<ApartmentComplex> grouping(){
        List<ApartmentComplex> result = new ArrayList<>();

        final int ROW_COUNT = apartmentMap.size();
        final int COLUMN_COUNT = apartmentMap.get(0).size();

        IntStream.range(0, ROW_COUNT).forEach(y -> {
            IntStream.range(0, COLUMN_COUNT).forEach(x -> {

                //주변에 존재하는 아파트를 찾아온 후, 있다면 result 에 해당 아파트 추가
                getOptApartmentByPosition(apartmentMap, y, x)
                        .flatMap(this::groupingApartments)
                        .ifPresent(result::add);//혹은 groupingApartments의 반환타입을 ApartmentComplex로 바꿔준 후 flatMap 대신 map을 쓸 수도 있음
            });
        });
        return result;
    }



    //주변 아파트 찾기 -> BFS
    private Optional<ApartmentComplex> groupingApartments(Apartment apartment) {
        if(apartment.isHasComplex()) return Optional.empty();

        ApartmentComplex apartmentComplex = ApartmentComplex.makeEmptyApartmentComplex();
        Deque<Apartment> deque = new ArrayDeque<>();

        apartmentComplex.addApartment(apartment);
        apartment.belongComplex();

        deque.offer(apartment);

        while (!deque.isEmpty()){
            Apartment current = deque.poll();
            final int X = current.getX();
            final int Y = current.getY();

            checkRangeAndGetApartment(X+1,Y).ifPresent(apart -> checkAndEnque(apart, deque, apartmentComplex));
            checkRangeAndGetApartment(X-1,Y).ifPresent(apart -> checkAndEnque(apart, deque, apartmentComplex));
            checkRangeAndGetApartment(X,Y+1).ifPresent(apart -> checkAndEnque(apart, deque, apartmentComplex));
            checkRangeAndGetApartment(X,Y-1).ifPresent(apart -> checkAndEnque(apart, deque, apartmentComplex));

        }
        return Optional.of(apartmentComplex);
    }

    private void checkAndEnque(Apartment apartment, Deque<Apartment> deque, ApartmentComplex apartmentComplex) {
        if(apartment.isHasComplex()) return;

        apartment.belongComplex();
        apartmentComplex.addApartment(apartment);
        deque.offer(apartment);
    }


    //== 범위 체크해서 반환 ==//
    private Optional<Apartment> checkRangeAndGetApartment(int x, int y){

        return   isProperRange(x, y)
                ? getOptApartmentByPosition(apartmentMap, y, x) //범위가 올바를 경우
                : Optional.empty();//범위가 올바르지 않을 경우
    }

    private boolean isProperRange(int x, int y) {
        return (x >=0 && y >=0)
                && y < apartmentMap.size()
                && x < apartmentMap.get(y).size();
    }
}


/**
 * 아파트
 */
class Apartment {
    private boolean hasComplex = false;//단지에 속해있는지 여부

    private int x;//X좌표
    private int y;//Y좌표


    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHasComplex() {
        return hasComplex;
    }
    public void belongComplex(){
        this.hasComplex = true;
    }



    /**
     * @param str -> "1"일 경우 아파트를 만들고, 그렇지 않다면 null을 반환
     */
    public static Optional<Apartment> checkAndMakeApartment(String str){

        return str.equals("1")
                ? Optional.of(new Apartment())
                : Optional.empty();
    }
}


/**
 * 아파트 단지
 */
class ApartmentComplex {

    List<Apartment> apartmentList = new ArrayList<>();


    private ApartmentComplex(){}

    public static ApartmentComplex makeEmptyApartmentComplex(){
        return new ApartmentComplex();
    }



    public void addApartment(Apartment apartment){
        apartmentList.add(apartment);
    }


    public int getApartmentCount(){
        return apartmentList.size();
    }
}