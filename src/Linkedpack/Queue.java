package Linkedpack;

public class Queue<E> {
    static class iNode<E> {
        E element;
        iNode<E> next;

        public iNode(E element) {
            this.element = element;
        }

        public iNode<E> getNext() {
            return next;
        }

        public E getData() {
            return element;
        }
    }
    protected iNode<E> head;
    protected iNode<E> last;
    protected int size;

    public Queue() {
        head = null;
        size = 0;
    }
    
    public void showQueue() {
        iNode<E> currentNode;

        currentNode = this.head;
        while (currentNode != null) {
            System.out.println(currentNode.element);
            currentNode = currentNode.next;
        }
    }

    public int size() {
		return this.size;
	}

	public E peek() {
		if (this.head != null)
			return this.head.element;
		else
			return null;
	}

	public boolean isEmpty() {
		return (this.size <= 0);
	}

    public void poll(E element) {
        iNode<E> currentNode = this.head;
        iNode<E> previousNode = null;
    
        // Traverse the linked list to find the node that contains the transferred message
        while (currentNode != null) {
            if (currentNode.element.equals(element)) {
                break;
            }
            previousNode = currentNode;
            currentNode = currentNode.next;
        }
    
        // Remove the node that contains the transferred message
        if (currentNode != null) {
            if (previousNode == null) {
                // The head node contains the transferred message
                this.head = this.head.next;
            } else {
                previousNode.next = currentNode.next;
            }
            this.size--;
            System.out.println("Message transferred and deleted from queue: " + element);
        }
    }
}
