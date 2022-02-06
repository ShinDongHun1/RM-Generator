package dd;

import annotation.BOJ;
import annotation.BaekjoonTier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

@BOJ(number = 1012, tier = BaekjoonTier.SILVER_II)
public class 백준1012 {


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        final int TEST_CASE_COUNT = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int i =0; i< TEST_CASE_COUNT; i++) sb.append(execute(br)+"\n");

        System.out.println(sb);

    }

    private static String execute(BufferedReader br) throws IOException {
        int[] inputs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        final int COLUMN_SIZE = inputs[0];//가로길이
        final int ROW_SIZE = inputs[1];//세로길이
        final int CABBAGE_COUNT = inputs[2];



        Farmland farmland = Farmland.defaultFarmByColumnSize(COLUMN_SIZE, ROW_SIZE);


        for (int i = 0; i< CABBAGE_COUNT; i++){
            inputs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            final int X = inputs[0];
            final int Y = inputs[1];
            farmland.addCabbage(Cabbage.makeByPosition(X, Y));
        }

        return String.valueOf(farmland.getRequiredEarthwormCount());
    }
}


class Farmland {
    private ArrayList<ArrayList<Cabbage>> map;

    private Farmland(ArrayList<ArrayList<Cabbage>> map){
        this.map = map;
    }

    public static Farmland defaultFarmByColumnSize(final int COLUMN_SIZE, final int ROW_SIZE){
        ArrayList<ArrayList<Cabbage>> map = new ArrayList<>();

        IntStream.range(0, ROW_SIZE).forEach(y -> {
            ArrayList<Cabbage> cabbages = new ArrayList<>();

            IntStream.range(0, COLUMN_SIZE).forEach(x -> cabbages.add(null));

            map.add(cabbages);
        });

        return new Farmland(map);
    }


    public void addCabbage(Cabbage cabbage){
        final int X = cabbage.getX();
        final int Y = cabbage.getY();

        map.get(Y).set(X, cabbage);
    }



    public int getRequiredEarthwormCount(){
        int result = 0;

        final int ROW_COUNT = map.size();
        final int COLUMN_COUNT = map.get(0).size();



        for (int y=0; y< ROW_COUNT; y ++)
            for (int x=0; x< COLUMN_COUNT; x++)
                if(isGrouping(getCabbage(x,y))) result ++;

        return result;
    }


    private boolean isGrouping(Cabbage cabbage) {
        if (cabbage == null || cabbage.isGrouped()) return false;

        Deque<Cabbage> deque = new ArrayDeque<>();

        deque.offer(cabbage);

        while (!deque.isEmpty()){
            Cabbage current = deque.poll();
            current.grouped();

            getAroundCabbages(current).forEach(deque::offer);
        }
        return true;
    }

    private List<Cabbage> getAroundCabbages(Cabbage cabbage) {
        List<Cabbage> result = new ArrayList<>();
        final int X = cabbage.getX();
        final int Y = cabbage.getY();

        checkAndEnque(result, X+1, Y);
        checkAndEnque(result, X-1, Y);
        checkAndEnque(result, X, Y+1);
        checkAndEnque(result, X, Y-1);

        return result;
    }

    private void checkAndEnque(List<Cabbage> result, int x, int y) {
        if(isProperRange(x, y)) getOptCabbage(x, y).ifPresent(cabbage1 -> {
            if(!cabbage1.isGrouped()) {
                cabbage1.grouped();
                result.add(cabbage1);
            }
        });
    }

    private boolean isProperRange(int x, int y){
        return (x > -1) && (y > -1) && y < map.size() && x < map.get(y).size();//순서 이대로

    }

    private Optional<Cabbage> getOptCabbage(int x, int y){
        return Optional.ofNullable(map.get(y).get(x));
    }

    private Cabbage getCabbage(int x, int y){
        return map.get(y).get(x);
    }
}

class Cabbage {
    private int x;
    private int y;
    private boolean isGrouped = false;

    private Cabbage(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public static Cabbage makeByPosition(int x, int y){
        return new Cabbage(x, y);
    }

    public boolean isGrouped() {
        return isGrouped;
    }

    public void grouped() {
        isGrouped = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}