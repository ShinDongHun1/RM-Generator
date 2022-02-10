package mapper;

import annotation.boj.BOJTier;
import gitrepourlparser.PersonalGitRepositoryUrlParser;
import problem.BOJProblem;
import readmegenerator.BOJReadmeGenerator;

import java.io.*;
import java.text.MessageFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class ReadmeMapper {


    public static List<BOJProblem> readFile(File file, Class<BOJProblem> clazz) {
        ArrayList<BOJProblem> existedLog = new ArrayList<>();
        MessageFormat mf = new MessageFormat("|{0}|" +   //풀이 날짜
                "{1}|" +//문제 번호, #을 안넣어주면 1000 -> 1,000으로 출력됨
                "{2}|" +        //문제 이름
                "<img src=\"{3}\" width=\"20\" height=\"20\" /> {4}|" +//문제 티어의 이미지 + 문제 티어
                "[풀이]({5})|" +//풀이 주소 (깃허브 주소)
                "[문제 주소]({6})|");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while (true) {
                String line = br.readLine();
                if (line == null) break;  // 더 이상 읽을 라인이 없을 경우 while 문을 빠져나간다.
                Object[] parse;
                try {
                    parse = mf.parse(line);
                } catch (ParseException e) {
                    continue;
                }
                String[] info = Arrays.copyOf(parse, parse.length, String[].class);
                String splitDate[] = info[0].split("-");
                LocalDate solvedDate = LocalDate.of(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[2]));
                existedLog.add(BOJProblem.builder()
                        .gitRepoUrl(info[5])
                        .tier(BOJTier.valueOf(info[4]))
                        .number(Integer.parseInt(info[1]))
                        .problemInfoUrl(info[6])
                        .solvedDate(solvedDate)
                        .name(info[2])
                        .build());


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existedLog;
    }



}
