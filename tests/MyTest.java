import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static org.junit.Assert.*;

public class MyTest {
    enum Commands {EXIT, ALL, ADD, FIND, DELETE}

    private static List<Dict> _validDictionaries;
    private static String _dictionaryTypesFilePath = "./tests/DictionaryTypes.txt";
    private static String _dictionaryRootPath = "./src/";

    @Before
    public void Init() {
        _validDictionaries = new ArrayList<Dict>();
        _validDictionaries.add(new Dict("d1.txt", "^[0-9]{4}$"));
        _validDictionaries.add(new Dict("d2.txt", "^[a-zA-Z]{5}$"));
    }

    @Test
    public void TestCommands() {
        assertEquals(Commands.EXIT.name(), Word.Commands.EXIT.name());
        assertEquals(Commands.ALL.name(), Word.Commands.ALL.name());
        assertEquals(Commands.ADD.name(), Word.Commands.ADD.name());
        assertEquals(Commands.FIND.name(), Word.Commands.FIND.name());
        assertEquals(Commands.DELETE.name(), Word.Commands.DELETE.name());
    }


    @Test
    public void TestAdd() {
        var config = new DictConfig("add --dn=d1 --key=123 --value=1234", _validDictionaries);
        var shouldBeAdded = false;
        if (config.Valid)
            try {
                if (!Files.exists(Paths.get(_dictionaryRootPath, config.Dn))) {
                    shouldBeAdded = true;
                } else {
                    var file = new String(Files.readString(Paths.get(_dictionaryRootPath, config.Dn)));
                    var matchesKey = file.matches(config.Key);
                    var matchesKeyValue = file.matches(config.Key + " " + config.Value);

                    if (!matchesKey && !matchesKeyValue) {
                        shouldBeAdded = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        _validDictionaries.stream().filter(e -> e.File == config.Dn).findFirst().get().Add(config);

        try {
            boolean matchesKeyValue;
            if (!Files.exists(Paths.get(_dictionaryRootPath, config.Dn))) {
                matchesKeyValue = false;
            } else {
                var file = new String(Files.readString(Paths.get(_dictionaryRootPath, config.Dn)));
                matchesKeyValue = file.matches(config.Key + " " + config.Value);
            }
            System.out.println(shouldBeAdded);
            System.out.println(matchesKeyValue);
            assertEquals(shouldBeAdded, matchesKeyValue);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    @Ignore
    @Test
    public void T() {
        System.out.println("1234".matches("^[0-9]{4}$"));
        //System.out.println("--dn=".matches("^--dn=.*"));
    }

    @Test
    public void GetValidDictionaries() {
        var actual = Word.GetValidDictionaries(_dictionaryTypesFilePath);

        assertArrayEquals(_validDictionaries.toArray(), actual.toArray());
    }

    @Test
    public void ParseInputString() {
        var strings = new HashMap<String, String>();
        strings.put("", Messages.NotEnoughArguments);
        strings.put("all", "");
        strings.put("all --dn=", Messages.DnValueError);
        strings.put("all --dn=e", Messages.NoSuchDictionary);
        strings.put("all --dn=d1", "");
        strings.put("all --dn=d2", "");
        strings.put("all --dn=d3", Messages.NoSuchDictionary);


        strings.put("add --dn=d1 --key=123 --value=123", Messages.NotValidValue);
        strings.put("add --dn=d1 --key=123 --value=1234", "");
        strings.put("add --dn=d1 --key=123 --value=123456", Messages.NotValidValue);
        strings.put("add --dn=d2 --key=12345 --value=werty", "");


        strings.forEach((k, v) -> {
                    System.out.println(k);
                    var actual = new DictConfig(k, _validDictionaries);
                    assertEquals(v, actual.Message);
                }
        );

        var stringsCommand = new HashMap<String, Enum>();
        stringsCommand.put("exit", Commands.EXIT);
        stringsCommand.put("exit ssdf", Commands.EXIT);
        stringsCommand.put("exit --dn", Commands.EXIT);
        stringsCommand.put("exit --dn=", Commands.EXIT);
        stringsCommand.put("exit --dn=34", Commands.EXIT);
        stringsCommand.put("exit --dn=34 dflkvln", Commands.EXIT);
        stringsCommand.put("23424 exit --dn=34 dflkvln", Commands.EXIT);

        stringsCommand.put("all", Commands.ALL);
        stringsCommand.put("all ssdf", Commands.ALL);
        stringsCommand.put("all --dn", Commands.ALL);
        stringsCommand.put("all --dn=", Commands.ALL);
        stringsCommand.put("all --dn=34", Commands.ALL);
        stringsCommand.put("all --dn=34 dflkvln", Commands.ALL);
        stringsCommand.put("23424 all --dn=34 dflkvln --key=sbsdcj --value=sjhdb", Commands.ALL);

        stringsCommand.put("add", Commands.ADD);
        stringsCommand.put("add ssdf", Commands.ADD);
        stringsCommand.put("add --dn", Commands.ADD);
        stringsCommand.put("add --dn=", Commands.ADD);
        stringsCommand.put("add --dn=34", Commands.ADD);
        stringsCommand.put("add --dn=34 dflkvln", Commands.ADD);
        stringsCommand.put("23424 add --dn=34 dflkvln --key=sbsdcj --value=sjhdb", Commands.ADD);

        stringsCommand.put("find", Commands.FIND);
        stringsCommand.put("find ssdf", Commands.FIND);
        stringsCommand.put("find --dn", Commands.FIND);
        stringsCommand.put("find --dn=", Commands.FIND);
        stringsCommand.put("find --dn=34", Commands.FIND);
        stringsCommand.put("find --dn=34 dflkvln", Commands.FIND);
        stringsCommand.put("23424 find --dn=34 dflkvln --key=sbsdcj --value=sjhdb", Commands.FIND);

        stringsCommand.put("delete", Commands.DELETE);
        stringsCommand.put("delete ssdf", Commands.DELETE);
        stringsCommand.put("delete --dn", Commands.DELETE);
        stringsCommand.put("delete --dn=", Commands.DELETE);
        stringsCommand.put("delete --dn=34", Commands.DELETE);
        stringsCommand.put("delete --dn=34 dflkvln", Commands.DELETE);
        stringsCommand.put("23424 Delete --dn=34 dflkvln --key=sbsdcj --value=sjhdb", Commands.DELETE);

        stringsCommand.forEach((k, v) -> {
                    System.out.println(k);
                    var actual = new DictConfig(k, _validDictionaries);
                    System.out.println(actual.Message);
                    System.out.println(actual.Valid);
                    System.out.println(actual.Command);
                    assertEquals(v.name(), actual.Command.name());
                }
        );

        var stringsDn = new HashMap<String, String>();
        stringsDn.put("exit", null);
        stringsDn.put("exit ssdf", null);
        stringsDn.put("exit --dn", null);
        stringsDn.put("exit --dn=", null);
        stringsDn.put("exit --dn=34", null);
        stringsDn.put("exit --dn=d1", "d1.txt");
        stringsDn.put("exit --dn=d2", "d2.txt");
        stringsDn.put("exit --dn=d3", null);
        stringsDn.put("exit --dn=344 dflkvln", null);
        stringsDn.put("23424 exit --dn=34 dflkvln", null);
        stringsDn.forEach((k, v) -> {
                    System.out.println(k);
                    var actual = new DictConfig(k, _validDictionaries);
                    assertEquals(v, actual.Dn);
                }
        );

        var stringsKey = new HashMap<String, String>();
        stringsKey.put("exit", null);
        stringsKey.put("exit ssdf", null);
        stringsKey.put("exit --dn", null);
        stringsKey.put("exit key", null);
        stringsKey.put("exit --key", null);
        stringsKey.put("exit --key=", null);
        stringsKey.put("exit --dn=d2", null);
        stringsKey.put("exit --dn=d3", null);
        stringsKey.put("exit --dn=344 --key=dflkvln", "dflkvln");
        stringsKey.put("23424 exit --dn=34 dflkvln", null);
        stringsKey.forEach((k, v) -> {
                    System.out.println(k);
                    var actual = new DictConfig(k, _validDictionaries);
                    assertEquals(v, actual.Key);
                }
        );

        var stringsValue = new HashMap<String, String>();
        stringsValue.put("exit", null);
        stringsValue.put("exit ssdf", null);
        stringsValue.put("exit --dn", null);
        stringsValue.put("exit value", null);
        stringsValue.put("exit --value", null);
        stringsValue.put("exit --value=", null);
        stringsValue.put("exit --dn=d2", null);
        stringsValue.put("exit --dn=d3", null);
        stringsValue.put("exit --dn=344 --value=dflkvln", null);
        stringsValue.put("23424 exit --dn=d1 --value=123456", null);
        stringsValue.put("23424 exit --dn=d1 --value=1234", "1234");
        stringsValue.put("23424 exit --dn=d1 --value=12", null);
        stringsValue.put("23424 exit --dn=34 --value=1234", null);

        stringsValue.put("23424 exit --dn=d2 --value=qw", null);
        stringsValue.put("23424 exit --dn=d2 --value=qqwwe", "qqwwe");
        stringsValue.put("23424 exit --dn=d2 --value=eerrttyy", null);
        stringsValue.put("23424 exit --dn=34 --value=eeeee", null);
        stringsValue.forEach((k, v) -> {
                    System.out.println(k);
                    var actual = new DictConfig(k, _validDictionaries);
                    assertEquals(v, actual.Value);
                }
        );
    }

    @Test
    public void MessagesClass() {
        assertEquals(Messages.NotEnoughArguments, "Not enough arguments");
        assertEquals(Messages.DnValueError, "--dn=  value error");
        assertEquals(Messages.NoSuchDictionary, "No such dictionary");
        assertEquals(Messages.KeyValueError, "--key=  value error");
        assertEquals(Messages.ValueValueError, "--value=  value error");
        assertEquals(Messages.NotValidValue, "Not valid value");
        assertEquals(Messages.Help, "try:\n1. all --dn=\n2. add --dn= --key= --value=\n3. find --dn= --key=\n4. delete --dn= --key=\n5. exit");
    }
}
