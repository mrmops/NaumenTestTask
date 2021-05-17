public class SortedQueue<T, R extends Comparable<R>> {

    private QueueNode<T, R> head = null;

    private int count = 0;

    public void enqueue(T value, R cost){
        if(head == null)
            head = new QueueNode<>(value, cost, null);
        else{
            if(head.compare.compareTo(cost) >= 0)
            {
                head = new QueueNode<>(value, cost, head);
            }
            else{
                var current = head;
                for(; current.next != null; current = current.next){
                    if(current.next.compare.compareTo(cost) >= 0){
                        var newNode = new QueueNode<>(value, cost, current.next);
                        current.next = newNode;
                        break;
                    }
                }
                if(current.next == null){
                    var newNode = new QueueNode<>(value, cost, null);
                    current.next = newNode;
                }
            }
        }
        count++;
    }

    public T dequeue(){
        var value = head;
        count--;
        if(head != null) {
            head = head.next;
            return value.value;
        }

        return null;
    }

    public int size() { return count; }

    @Override
    public String toString() {
        return "size=" + count;
    }

    public class QueueNode<TValue, RCompare extends Comparable<RCompare>>
    {
        private TValue value;
        private RCompare compare;
        private QueueNode<TValue, RCompare> next;

        public QueueNode(TValue value, RCompare compare, QueueNode<TValue, RCompare> next) {
            this.value = value;
            this.compare = compare;
            this.next = next;
        }
    }
}

