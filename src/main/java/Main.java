import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        RouteFinder finder = new BaseRouteFinder();
        /*List<char[]> mapList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            String line = sc.nextLine();
            if (line.isEmpty())
                break;
            mapList.add(line.toCharArray());
        }
        mapList.toArray(map);*/

        int i = 10000;
        char[][] map = new char[i][];
        for (int x = 0; x < i; x++) {
            char[] line = new char[i];
            for (int y = 0; y < i; y++) {
                line[y] = '.';
            }
            map[x] = line;
        }

        map[0][0] ='@';
        map[i - 1][i - 1] ='X';

        /*int i = 10000;
        char[][] map = new char[i][];
        map[0] = "...@........................................................................####".toCharArray();
        map[1] = ".###########################################################################....".toCharArray();
        for (int x = 2; x < i; x++) {
            map[x] = ".............................................................................#..".toCharArray();
        }

        map[i - 2] = "###############################################################################.".toCharArray();
        map[i - 1] = ".X..............................................................................".toCharArray();*/

        char[][] map2 = finder.findRoute(map);
        //sc.nextLine();
        printMap(map2);
    }

    public static void printMap(char[][] map2) {
        if (map2 != null)
            for (char[] line : map2) {
                for (char symbol : line) {
                    System.out.print(symbol);
                }
                System.out.println();
            }
    }
}
