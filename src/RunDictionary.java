/**
 * This program is taking two filenames as arguments. The first one as input, second one as output file.
 * This software support three operations: 'insert n', 'delete n', and 'lookup n'. The main goal of this
 * program is to test the implementation of the our SplayTree implementation.
 * @author Senghuot Lim
 */
public class RunDictionary {
   
   public static void main(String[] args) {
      SplayTree t = new SplayTree();
      t.insert(10);
      t.insert(5);
      t.insert(15);
      t.insert(20);
      t.insert(12);
      t.insert(11);
      t.insert(13);
      t.insert(9);
      t.insert(10);
      t.insert(14);
      
      t.lookup(10);
      t.lookup(11);
      
      t.delete(11);
      t.lookup(14);
      t.delete(14);
   }  
}