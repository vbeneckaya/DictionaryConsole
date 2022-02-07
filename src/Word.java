import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Word {
    enum Commands {EXIT, ALL, ADD, FIND, DELETE}

    private static List<Dict> _validDictionaries;
    private static String _dictionaryTypesFile = "./src/DictionaryTypes.txt";
    private static String _dictionaryRootPath = "./src/";

    public static void main() {
        help();

        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println(Messages.Welcome);
            String commandString = in.next();
            _validDictionaries = GetValidDictionaries(_dictionaryTypesFile);

            var config = new DictConfig(commandString, _validDictionaries);

            if (config.Valid) {

            }

        }
    }

    public static List<Dict> GetValidDictionaries(String dictionaryFile) {
        var path = Paths.get(dictionaryFile) ;
        var dict =  new ArrayList<Dict>();
        try {
            var s = Files.readAllLines(path);
            for (String s2 : s) {
                var s3 = s2.split("; ");
                dict.add(new Dict(s3[0].split("=")[1], s3[1].split("=")[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dict;
    }


    private static void help() {
        System.out.println(Messages.Help);
    }
}

