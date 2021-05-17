import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class BaseRouteFinder implements RouteFinder {

    @Override
    public char[][] findRoute(char[][] map) {
        Point start = getStart(map);
        PathElement path = findPath(map, start);
        if (path == null)
            return null;
        return addPathToMap(map, path);
    }

    private Point getStart(char[][] map) {
        for (int x = 0; x < map.length; x++) {
            char[] line = map[x];
            for (int y = 0; y < line.length; y++) {
                if (line[y] == '@')
                    return new Point(x, y);
            }
        }

        throw new IllegalArgumentException("В входных данных нет старта!");
    }

    private PathElement findPath(char[][] map, Point start) {
        //HashSet<Point> visited = new HashSet<>();
        int width = map.length;
        int height = map[0].length;
        PathElement startPath = new PathElement(start, null);
        Queue<PathElement> queue = new LinkedList<PathElement>();
        queue.offer(startPath);
        map[start.getX()][start.getY()] = ',';
        //visited.add(start);
        while (queue.size() != 0) {
            PathElement currentPath = queue.poll();
            Point location = currentPath.getLocation();
            for (var dy = -1; dy <= 1; dy++)
                for (var dx = -1; dx <= 1; dx++) {
                    if (Math.abs(dx) != Math.abs(dy)) {
                        int newX = location.getX() + dx;
                        int newY = location.getY() + dy;
                        if (InMap(width, height, newX, newY)) {
                            Point newPoint = new Point(newX, newY);
                            char mapElement = map[newX][newY];
                            if (mapElement != '#' && mapElement != ',') {
                                PathElement newPath = new PathElement(newPoint, currentPath);
                                if (mapElement == '.') {
                                    queue.offer(newPath);
                                    map[newX][newY] = ',';
                                } else {
                                    return newPath;
                                }
                            }
                        }
                    }
                }
        }

        return null;
    }

    private boolean InMap(int width, int height, int newX, int newY) {
        return !(newX < 0 || newY < 0 || newX >= width || newY >= height);
    }

    private char[][] addPathToMap(char[][] map, PathElement target) {
        PathElement previous = target.getFrom();
        while (previous.getFrom() != null) {
            Point location = previous.getLocation();
            map[location.getX()][location.getY()] = '+';
            previous = previous.getFrom();
        }

        return map;
    }

    private class PathElement {
        private Point location;
        private PathElement from;

        private PathElement(Point location, PathElement from) {
            this.location = location;
            this.from = from;
        }

        public PathElement getFrom() {
            return from;
        }

        public void setFrom(PathElement from) {
            this.from = from;
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
