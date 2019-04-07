package StaticValue;

import java.util.*;

/**
 * Created by garen on 2019/3/8.
 */
public class ParaTypeStaticValue {
   public static final Map<String,Integer> PARAMETERTYPE_INDEX = new HashMap<String, Integer>()
   {
       {
           put("int",1); put("float",1); put("double",1); put("short",1); put("long",1);
           put("byte",1);

           put("Integer",1); put("Float",1); put("Double",1); put("Short",1); put("Long",1);
           put("Byte",1);

           put("char",2); put("String",2);

           put("Character",2);

           put("boolean",3); put("Boolean",3);

           put("List<String>",14); put("List<char>",14); put("List<Character>",14);
           put("Set<String>",14); put("Set<char>",14); put("Set<Character>",14);
           put("ArrayList<String>",14); put("ArrayList<char>",14); put("ArrayList<Character>",14);
           put("HashSet<String>",14); put("HashSet<char>",14); put("HashSet<Character>",14);
           put("LinkedList<String>",14); put("LinkedList<char>",14); put("LinkedList<Character>",14);
           put("TreeSet<String>",14); put("TreeSet<char>",14); put("TreeSet<Character>",14);
           put("Vector<String>",14); put("Vector<char>",14); put("Vector<Character>",14);
           put("LinkedHashSet<String>",14); put("LinkedHashSet<char>",14); put("LinkedHashSet<Character>",14);

           put("List<boolean>",15); put("List<Boolean>",15);
           put("Set<boolean>",15); put("Set<Boolean>",15);
           put("ArrayList<boolean>",15); put("ArrayList<Boolean>",15);
           put("HashSet<boolean>",15); put("HashSet<Boolean>",15);
           put("LinkedList<boolean>",15); put("LinkedList<Boolean>",15);
           put("TreeSet<boolean>",15); put("TreeSet<Boolean>",15);
           put("Vector<boolean>",15); put("Vector<Boolean>",15);
           put("LinkedHashSet<boolean>",15); put("LinkedHashSet<Boolean>",15);

           put("List",16); put("ArrayList",16); put("LinkedList",16); put("Vector",16);

           put("Set",16); put("HashSet",16); put("TreeSet",16); put("LinkedHashSet",16);

           put("Map",17); put("HashMap",17); put("Hashtable",17); put("LinkedHashMap",17); put("TreeMap",17);
           put("ConcurrentHashMap",17);




       }
   };
   //Queue



   public static final List<String> PARAMETERTYPE = new ArrayList<String>()
   {
       {
           add("int"); add("float"); add("double");add("String"); add("char"); add("byte"); add("short");
           add("long"); add("boolean");

           add("Integer"); add("Float"); add("Double");add("Character");add("Byte");add("Short");
           add("Long"); add("Boolean");

           add("List"); add("ArrayList"); add("LinkedList"); add("Vector");

           add("Set"); add("HashSet"); add("TreeSet"); add("LinkedHashSet");

           add("Map"); add("HashMap"); add("Hashtable"); add("LinkedHashMap"); add("TreeMap");
           add("ConcurrentHashMap");

       }
   };


   public static final List<String> PARALISTNAME = new ArrayList<String>()
   {
       {
           add("List"); add("ArrayList"); add("LinkedList"); add("Vector");
       }
   };

   public static final List<String> PARASETNAME = new ArrayList<String>(){
       {
           add("Set"); add("HashSet"); add("TreeSet"); add("LinkedHashSet");
       }
   };

   public static final List<String> PARAMAPNAME = new ArrayList<String>(){
       {
           add("Map"); add("HashMap"); add("Hashtable"); add("LinkedHashMap"); add("TreeMap");
           add("ConcurrentHashMap");
       }
   };

    public static final List<String> PARAFIRSTINDEXNAME = new ArrayList<String>()
    {
        {
            add("int"); add("float"); add("double"); add("byte"); add("short");
            add("long");

            add("Integer"); add("Float"); add("Double");add("Byte");add("Short");
            add("Long");
        }
    };

}
