import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        RouteFinder finder = new BaseRouteFinder();
        List<char[]> mapList = new ArrayList<>();
//        Scanner sc = new Scanner(System.in);
//        while (true) {
//            String line = sc.nextLine();
//            if (line.isEmpty())
//                break;
//            mapList.add(line.toCharArray());
//        }
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

//        mapList.toArray(map);

        char[][] map2 = finder.findRoute(map);
        if (map2 != null)
            for (char[] line : map2) {
                for (char symbol : line) {
                    System.out.print(symbol);
                }
                System.out.println();
            }
    }
}
