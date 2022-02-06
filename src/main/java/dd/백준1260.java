package dd;

import annotation.BOJ;
import annotation.BaekjoonTier;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

@BOJ(number = 1260, tier = BaekjoonTier.SILVER_III)
public class 백준1260 {

    /*
     * 첫째 줄에 정점의 개수 N, 간선의 개수 M, 탐색을 시작할 정점의 번호 V
     *
     */
    public static void main(String[] args) throws IOException {


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();




        int[] inputs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        final int POINT_COUNT = inputs[0]; //정점의 개수
        final int LINE_COUNT = inputs[1]; //간선의 개수
        final int START_POINT = inputs[2]; //탐색을 시작할 정점의 번호





        ArrayList<ArrayList<Integer>> map = makeAndSetMap(br, POINT_COUNT, LINE_COUNT);



        IntStream.rangeClosed(0, POINT_COUNT).forEach(i -> map.get(i).sort(Comparator.naturalOrder()));




        Graph graph = Graph.from(map);




        graph.dfs(START_POINT, sb);

        graph.resetVisiting();

        sb.append("\n");

        graph.bfs(START_POINT, sb);

        System.out.println(sb.toString());
    }



    private static ArrayList<ArrayList<Integer>> makeAndSetMap(BufferedReader br, int POINT_COUNT, int LINE_COUNT) throws IOException {
        ArrayList<ArrayList<Integer>> map = new ArrayList<>();

        IntStream.rangeClosed(0, POINT_COUNT).forEach(i -> map.add(new ArrayList<>()));

        for (int i = 0; i< LINE_COUNT; i ++){
            int[] lines = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int start = lines[0];
            int end = lines[1];

            map.get(start).add(end);//양방향이므로
            map.get(end).add(start);
        }
        return map;
    }
}


class Graph{

    private ArrayList<ArrayList<Integer>> map = new ArrayList<>();
    private boolean[] visited;

    private Graph(ArrayList<ArrayList<Integer>> map){
        this.map = map;
        visited = new boolean[map.size()];


    }


    public static Graph from(ArrayList<ArrayList<Integer>> map){
        return new Graph(map);
    }
    public void resetVisiting(){
        visited = new boolean[map.size()];
    }




    public String bfs(int startPoint, StringBuilder sb){
        //StringBuilder -> 단일 스레드에서 성능 가장 좋음
        Deque<Integer> deque = new ArrayDeque<>();
        deque.add(startPoint);
        visited[startPoint] = true;


        while (!deque.isEmpty()){
            Integer currentPoint = deque.poll();
            sb.append(currentPoint + " ");

            IntStream.range(0, map.get(currentPoint).size())
                    .forEach(i -> checkAndEnque(map.get(currentPoint).get(i), deque));

        }
        return sb.toString();
    }


    private void checkAndEnque(int endPoint, Deque<Integer> deque) {
        if (visited[endPoint]) return;

        deque.offer(endPoint);
        visited[endPoint] = true;
    }






    public String dfs(int startPoint, StringBuilder sb){
        if(visited[startPoint]) return null;

        visited[startPoint] = true;
        sb.append(startPoint + " ");

        IntStream.range(0, map.get(startPoint).size()).forEach(i -> {
            checkAndDfs(map.get(startPoint).get(i), sb);
        });

        return sb.toString();
    }

    private void checkAndDfs(int endPoint, StringBuilder sb) {
        if(visited[endPoint]) return;
        dfs(endPoint, sb);
    }
}