package dd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.IntStream;

public class 백준2606 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        final int COMPUTER_COUNT = Integer.parseInt(br.readLine());
        final int LINKED_COUNT = Integer.parseInt(br.readLine());


        //== 컴퓨터 개수만큼 초기화 ==//
        ArrayList<Computer> computerList = initComputers(COMPUTER_COUNT);


        //== 컴퓨터 연결관계 설정 ==//
        connectComputer(br, computerList, LINKED_COUNT);


        Network network = Network.from(computerList);

        network.spreadingVirus(1);

        System.out.println(network.getInfectedComputerCount() - 1);//1번 컴퓨터는 제외이므로


    }

    private static ArrayList<Computer> initComputers(int COMPUTER_COUNT) {
        ArrayList<Computer> computerList = new ArrayList<>();
        IntStream.rangeClosed(0, COMPUTER_COUNT)
                .forEach(i -> computerList.add(Computer.makeLinkedLessComputer(i)));
        return computerList;
    }


    /**
     * 컴퓨터 연결관계 설정
     */
    private static void connectComputer(BufferedReader br, ArrayList<Computer> computerList, int LINKED_COUNT) throws IOException {

        for (int i = 0; i < LINKED_COUNT; i++) {
            int[] inputs = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();

            int computerNum = inputs[0];
            int linkedComputerNum = inputs[1];

            Computer computer = Computer.makeLinkedLessComputer(computerNum);
            Computer linkedComputer = Computer.makeLinkedLessComputer(linkedComputerNum);

            computerList.get(computerNum).addLinkedComputer(computerList.get(linkedComputerNum));
            computerList.get(linkedComputerNum).addLinkedComputer(computerList.get(computerNum));
        }
    }
}

class Network {
    private ArrayList<Computer> network;

    //== 생성자 ==//
    private Network(ArrayList<Computer> network) {
        this.network = network;
    }

    //== 정적 팩터리 메서드 ==//
    public static Network from(ArrayList<Computer> network) {
        return new Network(network);
    }


    //== 바이러스 전파 ==//
    public void spreadingVirus(int startComputerNumber) {
        Deque<Computer> computerDeque = new ArrayDeque<>();

        computerDeque.add(network.get(startComputerNumber));


        while (!computerDeque.isEmpty()) {
            Computer currentComputer = computerDeque.poll();
            currentComputer.infect();

            IntStream.range(0, currentComputer.getLinkedComputer().size())
                    .forEach(i -> checkAndInfect(computerDeque, currentComputer.getLinkedComputer().get(i)));
        }
    }

    private void checkAndInfect(Deque<Computer> computerDeque, Computer computer) {
        if (!computer.isInfected()) {
            computer.infect();
            computerDeque.add(computer);
        }
    }


    //== 감염된 컴퓨터 개수 구하기 ==//
    public int getInfectedComputerCount() {
        int result = 0;
        for (Computer computer : network) {
            if(computer.isInfected()) result ++;
        }
        return result;
    }

}


class Computer {
    private int number;
    private List<Computer> linkedComputer;
    private boolean isInfected = false;


    //== 생성자 ==//
    private Computer(int number, List<Computer> linkedComputer) {
        this.number = number;
        this.linkedComputer = linkedComputer;
    }

    private Computer(int number) {
        this.number = number;
        this.linkedComputer = new LinkedList<>();
    }


    //== 정적 팩터리 메서드 ==//
    public static Computer of(int number, List<Computer> linkedComputer) {
        return new Computer(number, linkedComputer);
    }

    public static Computer makeLinkedLessComputer(int number) {
        return new Computer(number);
    }


    //== 컴퓨터 연결시키기 ==//
    public void addLinkedComputer(Computer computer) {
        linkedComputer.add(computer);
    }


    //== Getter ==//
    public int getNumber() {
        return number;
    }

    public List<Computer> getLinkedComputer() {
        return linkedComputer;
    }

    public boolean isInfected() {
        return isInfected;
    }

    public void infect() {
        isInfected = true;
    }
}