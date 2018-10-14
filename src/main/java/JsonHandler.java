import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;
import java.util.*;

/**
 * This class implements logic related to json conversion
 */
public class JsonHandler {

    private static final Gson PRETTY_PRINT_GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * converts parsed tree to a JSON string
     * @param tree - parsed tree, result of a parser generated by antlr4
     * @return JSON string
     */
    static String toJson(ParseTree tree) {
        return PRETTY_PRINT_GSON.toJson(toMap(tree));
    }

    /**
     * converts tree to Java HashMap
     * @param tree - parsed tree, result of a parser generated by antlr4
     * @return a map representing tree
     */
    private static Map<String, Object> toMap(ParseTree tree) {
        Map<String, Object> map = new LinkedHashMap<>();
        traverse(tree, map);
        return map;
    }

    /**
     * recursively traverses a tree, creating an analogous map representation of the tree
     * @param tree parsed tree, result of a parser generated by antlr4
     * @param map reference to the object that should be built
     */
    private static void traverse(ParseTree tree, Map<String, Object> map) {

        if (tree instanceof TerminalNodeImpl) { //in case a terminal node is encountered
            Token token = ((TerminalNodeImpl) tree).getSymbol();
            //save the type and value of the node
            map.put("type", token.getType());
            map.put("Value", token.getText());

        } else { //otherwise it is an intermediate node
            List<Map<String, Object>> children = new ArrayList<>();
            String name = tree.getClass().getSimpleName().replaceAll("Context$", "");
            map.put(name, children); // add node itself
            for (int i = 0; i < tree.getChildCount(); i++) { // add children
                Map<String, Object> nested = new LinkedHashMap<>();
                children.add(nested);
                traverse(tree.getChild(i), nested);
            }
        }
    }
}


