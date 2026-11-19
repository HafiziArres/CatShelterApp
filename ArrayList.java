public class ArrayList
{
    private Object[] data;
    private int length, maxSize;
    
    //Default constructor
    public ArrayList()
    {
        maxSize = 0;
        data = null;
        length = 0;
    }
    
    //Normal constructor
    public ArrayList (int j)
    {
        maxSize = j;
        data = new Object[maxSize];
        length = 0;
    }
    
    //Return the size of the ArrayList
    public int getMaxSize()
    {
        return maxSize;
    }
    
    //Return number of elements in the ArrayList
    public int getNum()
    {
        return length;
    }
    
    //Insert element at the front of ArrayList
    public void insertAtFront(Object j) throws ArrayListException
    {
        add(0,j);
    }
    
    //Insert element at the back of ArrayList
    public void insertAtBack(Object j) throws ArrayListException
    {
        if(!isFull())
        {
            data[length] = j;
            length = length + 1;
        }
        else
            throw new ArrayListException("The ArrayList is full");
    }
    
    //Insert element at a specified index
    public void add(int i, Object j) throws ArrayListException
    {
        if(isFull())
            throw new ArrayListException("The ArrayList is full");
            
        else if ((i < 0) || (i > length))
            throw new ArrayListException("Index is out of bounds");
        else
        {
            for(int k = length-1; k >= i; --k)
                data[k+1] = data [k];
                
            length = length + 1;
            data[i] = j;
        }
    }
    
    //Return data at the specified location
    public Object getData(int i) throws ArrayListException
    {
        if (isEmpty())
            throw new ArrayListException("The ArrayList is empty");
        else if((i < 0) || (i > length - 1))
            throw new ArrayListException("Index is out of bounds");
            
        // FIX: was "data[i] = j;" (j does not exist, and nothing was returned)
        return data[i];
    }
    
    //Remove data at a specified location
    public Object removeAt(int i) throws ArrayListException
    {
        if(isEmpty())
            throw new ArrayListException("The ArrayList is empty");
        else if((i < 0) || (i > length - 1))
            throw new ArrayListException("Index is out of bounds");
            
        if (i == (length - 1))
        {
            length = length - 1;
            return data[i];
        }
        
        Object obj = data[i];
        for(int k = i; k < length -1; ++k)
        {
            data[k] = data[k+1];
        }
        
        length = length -1;
        return obj;
    }
    
    //Remove the first element
    public Object removeFirst() throws ArrayListException
    {
        return removeAt(0);
    }
    
    //Remove the last element
    public Object removeLast() throws ArrayListException
    {
        return removeAt(length-1);
    }
    
    //Clear all elements in ArrayList
    public void clearList()
    {
        length = 0;
    }
    
    //Return true if ArrayList is full, otherwise return false
    public boolean isFull()
    {
        return length == maxSize;
    }
    
    //Return true if ArrayList is empty, otherwise return false
    public boolean isEmpty()
    {
        return length == 0;
    }
    
    //Display element in the ArrayList
    public String print()
    {
        String s = "(";
        if (length == 0)
            return "( )";
        else
        {
            for(int i = 0; i < length; ++i)
            {
                s += data[i];
                if (i < length - 1)
                    s += " , ";
            }
            return s + ")";
        }
    }
}
