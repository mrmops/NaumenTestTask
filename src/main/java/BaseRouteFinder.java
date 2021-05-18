import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class BaseRouteFinder implements RouteFinder {

    private Point[] shifts = new Point[]{
            new Point(0, 1),
            new Point(0, -1),
            new Point(1, 0),
            new Point(-1, 0)
    };

    @Override
    public char[][] findRoute(char[][] map) {
        Point[] startTarget = getStart(map);
        return findPath(map, startTarget);
    }

    private Point[] getStart(char[][] map) {
        Point target = null;
        Point start = null;
        for (int x = 0; x < map.length; x++) {
            char[] line = map[x];
            for (int y = 0; y < line.length; y++) {
                if (line[y] == '@')
                    start = new Point(x, y);
                if (line[y] == 'X')
                    target = new Point(x, y);
                if (target != null && start != null)
                    return new Point[]{start, target};
            }
        }

        throw new IllegalArgumentException("В входных данных нет старта!");
    }

    private char[][] findPath(char[][] map, Point[] startTarget) {
        var start = startTarget[0];
        var target = startTarget[1];
        int width = map.length;
        int height = map[0].length;

        /*long usedArrayBytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println(usedArrayBytes / 1048576d);*/

        byte[][] directions = new byte[width][];
        for (int i = 0; i < width; i++)
            directions[i] = new byte[height];

        SortedQueue<PathElement, Double> queue = new SortedQueue<>();
        PathElement startPath = new PathElement(start, 0);
        queue.enqueue(startPath, getLength(startPath, target));

        while (queue.size() != 0) {
            PathElement current = queue.dequeue();
            Point currentPoint = current.getLocation();
            for (Point shift : shifts) {
                int newX = currentPoint.getX() + shift.getX();
                int newY = currentPoint.getY() + shift.getY();
                if (InMap(width, height, newX, newY)) {
                    Point newPoint = new Point(newX, newY);
                    char mapElement = map[newX][newY];
                    if (mapElement == '.' && directions[newX][newY] == 0) {
                        PathElement pathElement = new PathElement(newPoint, current.getCost() + 1);
                        queue.enqueue(pathElement, getLength(pathElement, target));
                        if (shift.getX() < 0)
                            directions[newX][newY] = 6;
                        else if (shift.getX() > 0)
                            directions[newX][newY] = 4;
                        else if (shift.getY() < 0)
                            directions[newX][newY] = 2;
                        else
                            directions[newX][newY] = 8;
                    } else if (mapElement == 'X') {
                        return addPathToMap(map, directions, start, currentPoint);
                    }
                }
            }
            /*usedBytes2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usedArrayBytes;
            System.out.println(usedBytes2 / 1048576d);*/
            //Main.printMap(map);
        }

        return null;
    }

    private boolean InMap(int width, int height, int newX, int newY) {
        return !(newX < 0 || newY < 0 || newX >= width || newY >= height);
    }

    private double getLength(PathElement startPath, Point target) {
        Point start = startPath.location;
        int dx = start.x - target.x;
        int dy = start.y - target.y;
        return Math.abs(dx) + Math.abs(dy) + startPath.cost;
    }

    private char[][] addPathToMap(char[][] map, byte[][] directions, Point start, Point pathStart) {
        Point currentPoint = pathStart;
        while (!currentPoint.equals(start)) {
            int newX = currentPoint.getX();
            int newY = currentPoint.getY();
            byte symbol = directions[newX][newY];
            map[newX][newY] = '+';
            if (symbol == 6) {
                newX += 1;
            } else if (symbol == 4) {
                newX -= 1;
            } else if (symbol == 8) {
                newY -= 1;
            } else {
                newY += 1;
            }
            currentPoint = new Point(newX, newY);
        }

        return map;
    }

    private class PathElement {
        private Point location;
        private int cost;

        private PathElement(Point location, int cost) {
            this.location = location;
            this.cost = cost;
        }

        public int getCost() {
            return cost;
        }

        public void setCost(int cost) {
            this.cost = cost;
        }

        public Point getLocation() {
            return location;
        }

        public void setLocation(Point location) {
            this.location = location;
        }
    }

    private class Point {
        private int x;
        private int y;


        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public int getY() {
            return y;
        }

        public int getX() {
            return x;
        }

    }
}
