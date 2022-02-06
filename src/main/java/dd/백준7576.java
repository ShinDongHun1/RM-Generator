package dd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class 백준7576 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


    public static void main(String[] args) throws IOException {
        int[] inputs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int col = inputs[0];//가로길이
        int row = inputs[1];//세로길이

        TomatoBox tomatoBox = TomatoBox.emptyBox(row);

        for (int y =0; y< row; y++){
            List<String> strings = Arrays.stream(br.readLine().split(" ")).collect(Collectors.toList());//16버전 이후부터는 바로 toList();
            for(int x =0; x<strings.size(); x++){
                tomatoBox.addTomato(y,Tomato.ofNullable(x, y, strings.get(x)));
            }
        }

        System.out.println(tomatoBox.getAllRipedDay());
    }
}


class TomatoBox {

    private ArrayList<ArrayList<Tomato>> box = new ArrayList<>();

    private TomatoBox(int rowSize){
        IntStream.range(0, rowSize).forEach(i -> box.add(new ArrayList<>()));
    }

    public static TomatoBox emptyBox(int rowSize){
        return new TomatoBox(rowSize);
    }

    public void addTomato(int y,Tomato tomato){
        box.get(y).add(tomato);
    }


   public void print(){
       for (ArrayList<Tomato> tomatoes : box) {
           for (Tomato tomato : tomatoes) {
               if(tomato == null) System.out.print("null ");
               else System.out.print(tomato.isRiped()+" ");
           }
           System.out.println();
       }
   }

    public int getAllRipedDay(){
        int result = 0;

        List<Tomato> ripedTomatos = new ArrayList<>();

        for(int y = 0; y<box.size(); y++){
            for (int x = 0; x< box.get(0).size(); x++){
                Tomato tomato = box.get(y).get(x);;
                if(tomato != null && tomato.isRiped()){
                    ripedTomatos.add(tomato);
                }
            }
        }

        ripeAround(ripedTomatos);

        int max = 0;

        for(int y = 0; y<box.size(); y++){
            for (int x = 0; x< box.get(0).size(); x++){

                Tomato tomato = box.get(y).get(x);

                if(tomato == null) continue;
                if(!tomato.isRiped()) return -1;//하나라도 안 익었으면 -1

                int temp = tomato.getRipeDay();
                if(temp > max) max = temp;
            }
        }

        return max;
    }

    /**
     * BFS 수행
     */
    private void ripeAround(List<Tomato> tomato) {
        int[][] moves = new int[][]{{1,0},{-1,0},{0,1},{0,-1}};

        Deque<Tomato> deque = new ArrayDeque<>();

        deque.addAll(tomato);


        while (!deque.isEmpty()){
            Tomato current = deque.poll();
            current.riping();
            final int X = current.getX();
            final int Y = current.getY();

            for(int i =0; i< 4; i++){
                checkRangeAndSetRipeDayAndEnque(deque, current.getRipeDay(), X+moves[i][0], Y+moves[i][1]);
            }
        }

    }

    private void checkRangeAndSetRipeDayAndEnque(Deque<Tomato> deque, int prevRipeDay, int X, int Y) {
        if(X > -1 && Y > -1 && Y < box.size() && X < box.get(0).size()){

            Tomato next = box.get(Y).get(X);

            if(next != null && !next.isRiped()){

                next.riping();
                next.setRipeDay(prevRipeDay+1);
                deque.offer(next);
            }
        }
    }
}


class Tomato{
    private int ripeDay; //익기까지 걸리는 시간
    private boolean isRiped;
    private int x;
    private int y;

    private Tomato(int x, int y, boolean isRiped) {
        this.x = x;
        this.y = y;
        this.isRiped = isRiped;

    }
    public static Tomato ofNullable(int x, int y, String state){

        if(state.equals("-1")) return null;


        boolean isRiped = switch (state){
            case "1" -> true;
            default -> false;
        };

        return new Tomato(x,y, isRiped);
    }

    public Tomato(int ripeDay) {
        this.ripeDay = ripeDay;
    }

    public int getRipeDay() {
        return ripeDay;
    }
    public void setRipeDay(int ripeDay){
        this.ripeDay = ripeDay;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isRiped() {
        return isRiped;
    }

    public void riping() {
        isRiped = true;
    }


}
