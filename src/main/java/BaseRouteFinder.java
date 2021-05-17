import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class BaseRouteFinder implements RouteFinder {

    @Override
    public char[][] findRoute(char[][] map) {
        Point[] startTarget = getStart(map);
        PathElement path = findPath(map, startTarget);
        if (path == null)
            return null;
        return addPathToMap(map, startTarget[0], path.location);
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

    private PathElement findPath(char[][] map, Point[] startTarget) {
        var start = startTarget[0];
        var target = startTarget[1];
        long usedArrayBytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println(usedArrayBytes / 1048576d);
        int width = map.length;
        int height = map[0].length;
        SortedQueue<PathElement, Double> queue = new SortedQueue<>();
        PathElement startPath = new PathElement(start, 0);
        queue.enqueue(startPath, getLength(startPath, target));
        map[start.getX()][start.getY()] = '@';
        while (queue.size() != 0) {
            PathElement current = queue.dequeue();
            Point currentPoint = current.getLocation();
            for (var dy = -1; dy <= 1; dy++)
                for (var dx = -1; dx <= 1; dx++) {
                    if (Math.abs(dx) != Math.abs(dy)) {
                        int newX = currentPoint.getX() + dx;
                        int newY = currentPoint.getY() + dy;
                        if (InMap(width, height, newX, newY)) {
                            Point newPoint = new Point(newX, newY);
                            char mapElement = map[newX][newY];
                            if (mapElement == '.') {
                                PathElement pathElement = new PathElement(newPoint, current.getCost() + 1);
                                queue.enqueue(pathElement, getLength(pathElement, target));
                                if (dx < 0)
                                    map[newX][newY] = '>';
                                else if (dx > 0)
                                    map[newX][newY] = '<';
                                else if (dy < 0)
                                    map[newX][newY] = '˅';
                                else
                                    map[newX][newY] = '^';
                            } else if (mapElement == 'X') {
                                return current;
                            }
                        }
                    }
                }
           /*long usedBytes2 = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - usedArrayBytes;
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

    private char[][] addPathToMap(char[][] map, Point start, Point target) {
        Point currentPoint = target;
        while (!currentPoint.equals(start)) {
            int newX = currentPoint.getX();
            int newY = currentPoint.getY();
            char symbol = map[newX][newY];
            map[newX][newY] = '+';
            if (symbol == '>') {
                newX += 1;
            } else if (symbol == '<') {
                newX -= 1;
            } else if (symbol == '^') {
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

        public void setY(int y) {
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }
    }
}
