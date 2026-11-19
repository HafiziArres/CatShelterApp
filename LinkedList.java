public class LinkedList
{
    private Node head, tail, current;
    
    //Default LinkedList Class
    public LinkedList()
    {
        head = tail = current = null;
    }
    
    //Check if LinkedList is empty
    public boolean isEmpty()
    {
        return head == null;
    }
    
    //Print data
    public String print()
    {
        if(isEmpty())
            return ("empty list");
        else
            return ("" + head + "");
    }
    
    //Add new Object data at the front of the LinkedList
    public void insertAtFront(Object data)
    {
        if(isEmpty())
            head = tail = new Node(data);
        else
            head = new Node(data, head);
    }
    
    //Return number of elements in the list
    public int size()
    {
        int count = 0;
        Node temp = head;
        while(temp != null)
        {
            ++count;
            temp = temp.next;
        }
        return count;
    }
    
    //Add new Object data at the back of the LinkedList
    public void insertAtBack(Object data)
    {
        if(isEmpty())
            head = tail = new Node(data);
        else
            tail = tail.next = new Node(data);
    }
    
    //Return the first element in the LinkedList
    public Object getFirst() throws EmptyListException
    {
        if(isEmpty())
            throw new EmptyListException();
        current = head;
        return current.data;
    }
    
    //Return the next element in the LinkedList
    public Object getNext()
    {
        if(current!=tail)
        {
            current = current.next;
            return current.data;
        }
        else
            return null;
    }
    
    //Return the last element in the LinkedList
    public Object getLast() throws EmptyListException
    {
        if(isEmpty())
            throw new EmptyListException();
            
        return tail.data;
    }
    
    //Remove the first element in the LinkedList
    public Object removeFromFront() throws EmptyListException
    {
        if(isEmpty())
            throw new EmptyListException();
            
        Object d = head.data;
        
        if (head == tail)
            head = tail = null;
        else
        {
            Node current = head;
            head = head.next;
            current.next = null;
        }
        return d;
    }
    
    //Remove the last element in the LinkedList
    public Object removeFromBack() throws EmptyListException
    {
        if(isEmpty())
            throw new EmptyListException();
            
        Object d = tail.data;
        
        if (head == tail)
            head = tail = null;
        else
        {
            Node current = head;
            while(current.next != tail)
                current = current.next;
                
            tail = current;
            current.next = null;
        }
        return d;
    }
    
    // ===== NEW METHODS (needed to find and delete an item anywhere in the list) =====
    
    //Return data at the specified index (0 = first)
    public Object getAt(int index) throws EmptyListException
    {
        if(isEmpty())
            throw new EmptyListException();
        if(index < 0 || index >= size())
            throw new IndexOutOfBoundsException("Index is out of bounds");
            
        Node temp = head;
        for(int i = 0; i < index; i++)
            temp = temp.next;
        return temp.data;
    }
    
    //Remove and return data at the specified index
    public Object removeAt(int index) throws EmptyListException
    {
        if(isEmpty())
            throw new EmptyListException();
        if(index < 0 || index >= size())
            throw new IndexOutOfBoundsException("Index is out of bounds");
            
        if(index == 0)
            return removeFromFront();
            
        Node prev = head;
        for(int i = 0; i < index - 1; i++)
            prev = prev.next;
            
        Node target = prev.next;
        prev.next = target.next;
        if(target == tail)
            tail = prev;
        target.next = null;
        return target.data;
    }
    
    //Remove all elements
    public void clearList()
    {
        head = tail = current = null;
    }
}
