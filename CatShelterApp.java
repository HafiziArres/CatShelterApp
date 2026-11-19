import javax.swing.JOptionPane;

public class CatShelterApp
{
    private static final String TITLE = "Cat Shelter System";

    // The ArrayList can store up to 10 cats
    private static CatShelter shelter = new CatShelter(10);

    public static void main(String[] args)
    {
        loadSampleData();

        String menu = "========== CAT SHELTER SYSTEM ==========\n"
                + " 1. Add cat\n"
                + " 2. Delete cat\n"
                + " 3. Find cat by ID\n"
                + " 4. Search cat (name / breed)\n"
                + " 5. Display cats\n"
                + " 6. Statistics (Sum, Average, Min, Max)\n"
                + " 7. Adopt cat   (ArrayList -> LinkedList)\n"
                + " 8. Return cat  (LinkedList -> ArrayList)\n"
                + " 0. Exit\n"
                + "========================================\n"
                + "Enter your choice:";

        int choice = -1;

        // Show the menu again and again until the user chooses 0
        while (choice != 0)
        {
            String input = JOptionPane.showInputDialog(null, menu, TITLE, JOptionPane.QUESTION_MESSAGE);

            if (input == null)
            {
                choice = 0;     // user pressed Cancel, so exit
            }
            else
            {
                try
                {
                    choice = Integer.parseInt(input.trim());
                }
                catch (NumberFormatException e)
                {
                    choice = -1;    // not a number
                }

                switch (choice)
                {
                    case 1: 
                        addCat();         
                        break;
                    case 2: 
                        deleteCat();      
                        break;
                    case 3: 
                        findCat();        
                        break;
                    case 4: 
                        searchCats();     
                        break;
                    case 5: 
                        displayCats();    
                        break;
                    case 6: 
                        showStatistics(); 
                        break;
                    case 7: 
                        moveCat(CatShelter.AVAILABLE); 
                        break;   // adopt
                    case 8: 
                        moveCat(CatShelter.ADOPTED);   
                        break;   // return
                    case 0: 
                        break;
                    default: showError("Invalid option, try again.");
                }
            }
        }

        showInfo("Thank you. Goodbye!");
    }

    // ---------- 1. ADD ----------
    private static void addCat()
    {
        int which = chooseList("Add");
        if (which == 0) return;

        // Ask for the details. null means the user pressed Cancel.
        String id = readText("Cat ID:");
        if (id == null) return;
        String name = readText("Name:");
        if (name == null) return;
        String breed = readText("Breed:");
        if (breed == null) return;
        String ageText = readText("Age (years):");
        if (ageText == null) return;
        String weightText = readText("Weight (kg):");
        if (weightText == null) return;
        String feeText = readText("Adoption fee (RM):");
        if (feeText == null) return;

        try
        {
            // Change the text into numbers
            int age = Integer.parseInt(ageText);
            double weight = Double.parseDouble(weightText);
            double fee = Double.parseDouble(feeText);

            if (age < 0 || weight <= 0 || fee < 0)
            {
                showError("Invalid values. Cat not added.");
                return;
            }

            // Create the Cat object and store it
            Cat cat = new Cat(id, name, breed, age, weight, fee);
            if (shelter.addCat(which, cat))
                showInfo(name + " added to " + shelter.listName(which) + ".");
            else
                showError("This ID already exists.");
        }
        catch (NumberFormatException e)
        {
            showError("Age, weight and fee must be numbers.");
        }
        catch (ArrayListException e)
        {
            // The ArrayList has a fixed size, so this happens when it is full
            showError("Cannot add: " + e.getMessage());
        }
    }

    // ---------- 2. DELETE ----------
    private static void deleteCat()
    {
        int which = chooseList("Delete");
        if (which == 0) return;

        String id = readText("Enter cat ID to delete:");
        if (id == null) return;

        Cat cat = shelter.removeCat(which, id);
        if (cat == null)
            showError("Cat not found.");
        else
            showInfo(cat.getName() + " deleted.");
    }

    // ---------- 3. FIND ----------
    private static void findCat()
    {
        int which = chooseList("Find");
        if (which == 0) return;

        String id = readText("Enter cat ID:");
        if (id == null) return;

        Cat cat = shelter.findCat(which, id);
        if (cat == null)
            showError("Cat not found.");
        else
            showInfo("Cat found:\n\n" + cat);
    }

    // ---------- 4. SEARCH ----------
    private static void searchCats()
    {
        int which = chooseList("Search");
        if (which == 0) return;

        String keyword = readText("Keyword (name / breed):");
        if (keyword == null) return;

        String result = shelter.searchCats(which, keyword);
        if (result.equals(""))
            showInfo("No cat matches \"" + keyword + "\".");
        else
            showInfo("Search results:\n\n" + result);
    }

    // ---------- 5. DISPLAY ----------
    private static void displayCats()
    {
        int which = chooseList("Display");
        if (which == 0) return;

        showInfo(shelter.getTableText(which));
    }

    // ---------- 6. STATISTICS (Sum, Average, Min, Max) ----------
    private static void showStatistics()
    {
        int which = chooseList("Statistics");
        if (which == 0) return;

        if (shelter.count(which) == 0)
        {
            showInfo("The list is empty, no statistics.");
            return;
        }

        Cat lightest = shelter.findLightest(which);
        Cat heaviest = shelter.findHeaviest(which);

        String message = shelter.listName(which) + "\n\n"
                + "Total adoption fee : RM " + String.format("%.2f", shelter.sumFees(which)) + "\n"
                + "Average age : " + String.format("%.1f", shelter.averageAge(which)) + " years\n"
                + "Lightest cat : " + lightest.getName() + " (" + lightest.getWeight() + " kg)\n"
                + "Heaviest cat : " + heaviest.getName() + " (" + heaviest.getWeight() + " kg)";
        showInfo(message);
    }

    // ---------- 7 and 8. MOVE a cat to the other list ----------
    // from = AVAILABLE : adopt (ArrayList -> LinkedList)
    // from = ADOPTED   : return to shelter (LinkedList -> ArrayList)
    private static void moveCat(int from)
    {
        String id;
        if (from == CatShelter.AVAILABLE)
            id = readText("Enter ID of cat to adopt:");
        else
            id = readText("Enter ID of cat to return:");
        if (id == null) return;

        if (shelter.findCat(from, id) == null)
        {
            showError("Cat not found in that list.");
        }
        else if (shelter.moveCat(from, id))
        {
            if (from == CatShelter.AVAILABLE)
                showInfo("Cat moved from ArrayList to LinkedList (adopted).");
            else
                showInfo("Cat moved from LinkedList to ArrayList (back in shelter).");
        }
        else
        {
            showError("Cannot move: the ArrayList is full.");
        }
    }

    // ---------- HELPER METHODS ----------

    // Ask which list to use. Returns 1 (ArrayList), 2 (LinkedList) or 0 if cancelled / invalid.
    private static int chooseList(String action)
    {
        String input = readText(action + " - which list?\n"
                + "1 = Available cats (ArrayList)\n"
                + "2 = Adopted cats (LinkedList)\n"
                + "Enter your choice:");

        if (input == null)
            return 0;
        if (input.equals("1"))
            return CatShelter.AVAILABLE;
        if (input.equals("2"))
            return CatShelter.ADOPTED;

        showError("Invalid list choice.");
        return 0;
    }

    // Ask the user to type something. Returns null if Cancel is pressed.
    private static String readText(String prompt)
    {
        String text = JOptionPane.showInputDialog(null, prompt, TITLE, JOptionPane.QUESTION_MESSAGE);

        // keep asking if the user types nothing
        while (text != null && text.trim().equals(""))
        {
            showError("This field cannot be empty.");
            text = JOptionPane.showInputDialog(null, prompt, TITLE, JOptionPane.QUESTION_MESSAGE);
        }

        if (text == null)
            return null;
        return text.trim();
    }

    private static void showInfo(String message)
    {
        JOptionPane.showMessageDialog(null, message, TITLE, JOptionPane.INFORMATION_MESSAGE);
    }

    private static void showError(String message)
    {
        JOptionPane.showMessageDialog(null, message, TITLE, JOptionPane.ERROR_MESSAGE);
    }

    // ---------- SAMPLE DATA ----------
    private static void loadSampleData()
    {
        // 5 cats in the ArrayList (available)
        shelter.addCat(CatShelter.AVAILABLE, new Cat("C001", "Comot", "Domestic Shorthair", 2, 3.8, 50.00));
        shelter.addCat(CatShelter.AVAILABLE, new Cat("C002", "Oyen", "Orange Tabby", 1, 3.2, 30.00));
        shelter.addCat(CatShelter.AVAILABLE, new Cat("C003", "Mochi", "Persian", 3, 4.5, 250.00));
        shelter.addCat(CatShelter.AVAILABLE, new Cat("C004", "Snowy", "British Shorthair", 4, 5.1, 300.00));
        shelter.addCat(CatShelter.AVAILABLE, new Cat("C005", "Tompok", "Siamese", 6, 4.2, 180.00));

        // 2 cats in the LinkedList (adopted)
        shelter.addCat(CatShelter.ADOPTED, new Cat("C006", "Belang", "Bengal", 5, 5.8, 350.00));
        shelter.addCat(CatShelter.ADOPTED, new Cat("C007", "Kiko", "Scottish Fold", 2, 4.0, 280.00));
    }
}
