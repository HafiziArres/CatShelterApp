/*
 * CatShelter handles all the work (store, delete, find, sum, move ...).
 *   available = ArrayList  (cats waiting for adoption)
 *   adopted   = LinkedList (cats that already have a home)
 */
public class CatShelter
{
    // Used to choose which list we want to work with
    public static final int AVAILABLE = 1;   // the ArrayList
    public static final int ADOPTED = 2;     // the LinkedList

    private ArrayList available;
    private LinkedList adopted;

    public CatShelter(int capacity)
    {
        available = new ArrayList(capacity);
        adopted = new LinkedList();
    }

    // Name of the list, used in messages
    public String listName(int which)
    {
        if (which == AVAILABLE)
            return "Available cats (ArrayList)";
        else
            return "Adopted cats (LinkedList)";
    }

    // Number of cats in the list
    public int count(int which)
    {
        if (which == AVAILABLE)
            return available.getNum();
        else
            return adopted.size();
    }

    // Get the cat at a position in the list (first cat is position 0)
    public Cat getCat(int which, int index)
    {
        if (which == AVAILABLE)
            return (Cat) available.getData(index);
        else
            return (Cat) adopted.getAt(index);
    }

    // ---------- STORE ----------
    // Returns false if the ID is already used.
    // The ArrayList throws ArrayListException if it is full.
    public boolean addCat(int which, Cat cat)
    {
        if (indexOf(AVAILABLE, cat.getId()) != -1 || indexOf(ADOPTED, cat.getId()) != -1)
            return false;

        if (which == AVAILABLE)
            available.insertAtBack(cat);
        else
            adopted.insertAtBack(cat);
        return true;
    }

    // ---------- DELETE ----------
    // Returns the deleted cat, or null if the ID is not found
    public Cat removeCat(int which, String id)
    {
        int index = indexOf(which, id);
        if (index == -1)
            return null;

        if (which == AVAILABLE)
            return (Cat) available.removeAt(index);
        else
            return (Cat) adopted.removeAt(index);
    }

    // ---------- FIND ----------
    // Returns the position of the cat with this ID, or -1 if not found
    public int indexOf(int which, String id)
    {
        for (int i = 0; i < count(which); i++)
        {
            if (getCat(which, i).getId().equalsIgnoreCase(id))
                return i;
        }
        return -1;
    }

    // Returns the cat with this ID, or null if not found
    public Cat findCat(int which, String id)
    {
        int index = indexOf(which, id);
        if (index == -1)
            return null;
        else
            return getCat(which, index);
    }

    // ---------- SEARCH ----------
    // Returns all cats whose name or breed contains the keyword ("" if none)
    public String searchCats(int which, String keyword)
    {
        String key = keyword.toLowerCase();
        String result = "";

        for (int i = 0; i < count(which); i++)
        {
            Cat cat = getCat(which, i);
            if (cat.getName().toLowerCase().contains(key)
                    || cat.getBreed().toLowerCase().contains(key))
            {
                result = result + cat + "\n";
            }
        }
        return result;
    }

    // ---------- DISPLAY ----------
    // Returns all cats in the list as text
    public String getTableText(int which)
    {
        String text = listName(which) + " - " + count(which) + " cat(s)\n\n";

        if (count(which) == 0)
            return text + "(empty)";

        for (int i = 0; i < count(which); i++)
            text = text + getCat(which, i) + "\n";
        return text;
    }

    // ---------- SUM, AVERAGE, MIN, MAX ----------
    // Sum: total of all adoption fees
    public double sumFees(int which)
    {
        double sum = 0;
        for (int i = 0; i < count(which); i++)
            sum = sum + getCat(which, i).getAdoptionFee();
        return sum;
    }

    // Average: average age of the cats
    public double averageAge(int which)
    {
        if (count(which) == 0)
            return 0;

        double total = 0;
        for (int i = 0; i < count(which); i++)
            total = total + getCat(which, i).getAge();
        return total / count(which);
    }

    // Min: the cat with the lowest weight
    public Cat findLightest(int which)
    {
        Cat min = getCat(which, 0);
        for (int i = 1; i < count(which); i++)
        {
            if (getCat(which, i).getWeight() < min.getWeight())
                min = getCat(which, i);
        }
        return min;
    }

    // Max: the cat with the highest weight
    public Cat findHeaviest(int which)
    {
        Cat max = getCat(which, 0);
        for (int i = 1; i < count(which); i++)
        {
            if (getCat(which, i).getWeight() > max.getWeight())
                max = getCat(which, i);
        }
        return max;
    }

    // ---------- MOVE between ArrayList and LinkedList ----------
    // from = AVAILABLE : move the cat from the ArrayList to the LinkedList (adopt)
    // from = ADOPTED   : move the cat from the LinkedList to the ArrayList (return)
    // Returns true if the cat was moved.
    public boolean moveCat(int from, String id)
    {
        // cat is not in the list
        if (indexOf(from, id) == -1)
            return false;

        // the ArrayList has a fixed size, so check there is space first
        if (from == ADOPTED && available.isFull())
            return false;

        Cat cat = removeCat(from, id);

        if (from == AVAILABLE)
            adopted.insertAtBack(cat);
        else
            available.insertAtBack(cat);
        return true;
    }
}
