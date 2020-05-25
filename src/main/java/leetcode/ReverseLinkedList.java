package leetcode;

public class ReverseLinkedList {

    static class Node {
        String data;
        Node next;
        public Node(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {

        Node heart = new Node("0");
        Node tmp = heart;
        for (int i = 1; i < 6 ; i++) {
            Node node = new Node(String.valueOf(i));
            tmp.setNext(node);
            tmp = node;
        }

        heart = reverseNode1(heart);

        while (heart != null) {
            System.out.printf(heart.getData());
            System.out.printf("\n");
            heart = heart.getNext();
        }

    }

    private static Node reverseNode(Node heart) {
        Node one = null;
        Node two = heart;
        while (two != null) {
            Node temp = two.getNext();
            if (one == null) {
                two.setNext(null);
            } else {
                two.setNext(one);
            }
            one = two;
            two = temp;
        }
        return one;
    }

    private static Node reverseNode1(Node heart) {
        if (heart == null || heart.getNext() == null) {
            return heart;
        } else {
            Node temp = heart.getNext();
            Node node = reverseNode1(heart.getNext());
            temp.setNext(heart);
            heart.setNext(null);
            return node;
        }
    }

}
