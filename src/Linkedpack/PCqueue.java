package Linkedpack;

import java.util.Iterator;
import java.util.Map;

public class PCqueue<E> extends Queue<PC> implements Iterable<PC> {

    public void offer(PC element) {
        final int MAX_LENGTH = 250;
    
        String message = element.getMessage();
        int messageLength = message.length();
    
        if (messageLength > MAX_LENGTH) {
            int numSubstrings = (int) Math.ceil((double) messageLength / MAX_LENGTH);
    
            String[] substrings = new String[numSubstrings];
            int currentIndex = 0;
    
            for (int i = 0; i < numSubstrings; i++) {
                if (currentIndex + MAX_LENGTH < messageLength) {
                    substrings[i] = message.substring(currentIndex, currentIndex + MAX_LENGTH);
                    currentIndex += MAX_LENGTH;
                } else {
                    substrings[i] = message.substring(currentIndex);
                }
            }
    
            int messageId = element.getMessageId();
    
            for (int i = 0; i < numSubstrings; i++) {
                PC messagePart = new PC(element.getSourcePort(), element.getDestinationPort(), 
                                        element.getSequenceNumber(), element.getAcknowledgementNumber(), substrings[i]);
                messagePart.setMessageId(messageId); // set the message ID to the original message ID
                iNode<PC> newNode = new iNode<PC>(messagePart);
    
                if (this.head == null) {
                    this.head = newNode;
                } else {
                    iNode<PC> tail = this.head;
                    while(tail.next != null) {
                        tail = tail.next;
                    }
                    tail.next = newNode;
                }
                this.size++;
            }
        } else {
            iNode<PC> newNode = new iNode<PC>(element);
            if (this.head == null) {
                this.head = newNode;
            } else {
                iNode<PC> tail = this.head;
                while(tail.next != null) {
                    tail = tail.next;
                }
                tail.next = newNode;
            }
            this.size++;
        }
    }

    @Override
    public Iterator<PC> iterator() {
        return new Iterator<PC>() {
            private iNode<PC> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public PC next() {
                if (!hasNext()) {
                    throw new java.util.NoSuchElementException();
                }
                PC data = current.getData();
                current = current.getNext();
                return data;
            }
        };
    }

    public void transferMessages(PCqueue<PC> selectedQueue, Map<PCqueue<PC>, Integer> sourcePortMap, Map<String, PCqueue<PC>> queueMap) {
        // Transfer messages to queues with matching source and destination port
        for (PC message : selectedQueue) {
            int destPort = message.getDestinationPort();
            for (Map.Entry<String, PCqueue<PC>> entry : queueMap.entrySet()) {
                PCqueue<PC> queue = entry.getValue();
                int sourcePort = sourcePortMap.getOrDefault(queue, -1);
                if (sourcePort == destPort) {
                    // Check if there is an existing message with the same ID in the queue
                    PC existingMessage = null;
                    for (PC queuedMessage : queue) {
                        if (queuedMessage.getMessageId() == message.getMessageId()) {
                            existingMessage = queuedMessage;
                            break;
                        }
                    }
                    if (existingMessage != null) {
                        // Merge the messages
                        String mergedMessage = existingMessage.getMessage() + message.getMessage();
                        existingMessage.setMessage(mergedMessage);
                        selectedQueue.poll(message);
                    } else {
                        // Create new message with same data as original message but with other fields set to null or 0
                        PC newMessage = new PC(0, 0, 0, 0, message.getMessage());
                        newMessage.setMessageId(message.getMessageId());
                        queue.offer(newMessage);
                        selectedQueue.poll(message);
                    }
                    break;
                }
            }
        }
    }
}
