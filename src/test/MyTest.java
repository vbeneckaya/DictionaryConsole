public class MyTest {
//    enum Commands {ALL, ADD, FIND, DELETE}
//
//    private static String dictionaryTypesFilePath = "./src/test/res/DictionaryTypes.txt";
//    private static String dictionaryRootPath;
//
//    @Rule
//    public TemporaryFolder folder = new TemporaryFolder();
//
//
//    @Before
//    public void Init() {
//  //      DictionaryRootPath = folder.getRoot().getPath();
//        dictionaryRootPath = "./src/test";
//
//
//        var validDictionaries = new ArrayList<Dict>();
//        validDictionaries.add(new Dict("d1.txt", "^[0-9]{4}$", dictionaryRootPath));
//        validDictionaries.add(new Dict("d2.txt", "^[a-zA-Z]{5}$", dictionaryRootPath));
//        ConsoleService.init(validDictionaries);
//
//
//    }
//
//    @Test
//    public void TestCommands() {
//        assertEquals(Commands.ALL.name(), Commands.ALL.name());
//        assertEquals(Commands.ADD.name(), Commands.ADD.name());
//        assertEquals(Commands.FIND.name(), Commands.FIND.name());
//        assertEquals(Commands.DELETE.name(), Commands.DELETE.name());
//    }
//
//    @Test
//    public void MessagesClass() {
//        assertEquals(Messages.NotEnoughArguments, "Not enough arguments");
//        assertEquals(Messages.DnValueError, "--dn=  value error");
//        assertEquals(Messages.NoSuchDictionary, "No such dictionary");
//        assertEquals(Messages.KeyValueError, "--key=  value error");
//        assertEquals(Messages.ValueValueError, "--value=  value error");
//        assertEquals(Messages.NotValidValue, "Not valid value");
//        assertEquals(Messages.Help, "try:\n1. all --dn=\n2. add --dn= --key= --value=\n3. find --dn= --key=\n4. delete --dn= --key=\n5. exit");
//    }
//
//    @Test
//    public void GetValidDictionaries() {
//        var expected = DictService.getValidDictionaries();
//        DictService.init(dictionaryTypesFilePath);
//        var actual = DictService.getValidDictionaries();
//
//        assertArrayEquals(expected.toArray(), actual.toArray());
//    }
//
//    @Test
//    public void TestAll(){
//        var consoleCommand = "all --dn=d1 --key=123";
//        var dictActionRequest = ConsoleService.createActionRequestFromCommandLine(consoleCommand);
//        var resValue = DictService.getDictionaryByFileName(dictActionRequest.dn).all();
//        System.out.println(resValue);
//    }
//
//    @Test
//    public void TestFind(){
//
//        var consoleCommand = "delete --dn=d1 --key=123";
//        var dictActionRequest = ConsoleService.createActionRequestFromCommandLine(consoleCommand);
//        DictService.getDictionaryByFileName(dictActionRequest.dn).delete(dictActionRequest.key);
//        consoleCommand = "find --dn=d1 --key=123";
//        dictActionRequest = ConsoleService.createActionRequestFromCommandLine(consoleCommand);
//        var resValue = DictService.getDictionaryByFileName(dictActionRequest.dn).find(dictActionRequest.key);
//        System.out.println(resValue);
//        assertEquals("", resValue);
//
//
//        singleAdd("add --dn=d1 --key=123 --value=1234");
//         consoleCommand = "find --dn=d1 --key=123";
//         dictActionRequest = ConsoleService.createActionRequestFromCommandLine(consoleCommand);
//         resValue = DictService.getDictionaryByFileName(dictActionRequest.dn).find(dictActionRequest.key);
//        System.out.println(resValue);
//        assertEquals("123 1234", resValue);
//    }
//
//    @Test
//    public void TestDelete(){
//        singleAdd("add --dn=d1 --key=123 --value=1234");
//        singleAdd("add --dn=d1 --key=124 --value=1235");
//        singleAdd("add --dn=d1 --key=125 --value=1236");
//
//        var consoleCommand = "delete --dn=d1 --key=123";
//        var dictActionRequest = ConsoleService.createActionRequestFromCommandLine(consoleCommand);
//        DictService.getDictionaryByFileName(dictActionRequest.dn).delete(dictActionRequest.key);
//
//        consoleCommand = "find --dn=d1 --key=123";
//        dictActionRequest = ConsoleService.createActionRequestFromCommandLine(consoleCommand);
//        var resValue = DictService.getDictionaryByFileName(dictActionRequest.dn).find(dictActionRequest.key);
//        System.out.println(resValue);
//        assertEquals("", resValue);
//    }
//
//
//    @Test
//    public void TestAdd() {
//        singleAdd("add --dn=d1 --key=123 --value=1234");
//        singleAdd("add --dn=d1 --key=124 --value=1235");
//        singleAdd("add --dn=d1 --key=125 --value=1236");
//    }
//
//    private void singleAdd(String consoleCommand){
//        var dictActionRequest = ConsoleService.createActionRequestFromCommandLine(consoleCommand);
//        var shouldBeAdded = false;
//        var initialLinesCount = 0;
//
//            try {
//                if (!Files.exists(Paths.get(dictionaryRootPath, dictActionRequest.dn))) {
//                    shouldBeAdded = dictActionRequest.valid;
//
//                } else {
//                    var fileLines = Files.readAllLines(Paths.get(dictionaryRootPath, dictActionRequest.dn));
//                    initialLinesCount = fileLines.size();
//                    shouldBeAdded = fileLines.stream().filter(l -> l.matches("^" + dictActionRequest.key + " .*")).findFirst().isEmpty() && dictActionRequest.valid;
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        DictService.getDictionaryByFileName(dictActionRequest.dn).add(dictActionRequest.key, dictActionRequest.value);
//
//        var newLinesCount = 0;
//        var matchesKeyAfter = false;
//        try {
//            if (Files.exists(Paths.get(dictionaryRootPath, dictActionRequest.dn))) {
//                var fLines = Files.readAllLines(Paths.get(dictionaryRootPath, dictActionRequest.dn));
//                matchesKeyAfter = fLines.stream().anyMatch(l -> l.matches("^" + dictActionRequest.key + " .*"));
//                newLinesCount = fLines.size();
//            }
//
//            if (shouldBeAdded) {
//                System.out.println("should be added");
//                assertEquals(true, matchesKeyAfter);
//                assertNotEquals(initialLinesCount, newLinesCount);
//            } else {
//                System.out.println("should not be added");
//                assertEquals(initialLinesCount, newLinesCount);
//            }
//        } catch (
//                IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Ignore
//    @Test
//    public void T() {
//        //System.out.println("1234".matches("^[0-9]{4}$"));
//        var a = "d1.txt".split("\\.");
//
//        for (String s: a
//             ) {
//            System.out.println(s);
//        }
//      //  System.out.println("d1.txt".split("."));
//        //System.out.println("--dn=".matches("^--dn=.*"));
//    }
//
//    @Test
//    public void parseInputString() {
//        var strings = new HashMap<String, String>();
//        strings.put("", Messages.NotEnoughArguments);
//        strings.put("all", "");
//        strings.put("all --dn=", Messages.DnValueError);
//        strings.put("all --dn=e", Messages.NoSuchDictionary);
//        strings.put("all --dn=d1", "");
//        strings.put("all --dn=d2", "");
//        strings.put("all --dn=d3", Messages.NoSuchDictionary);
//
//
//        strings.put("add --dn=d1 --key=123 --value=123", Messages.NotValidValue);
//        strings.put("add --dn=d1 --key=123 --value=1234", "");
//        strings.put("add --dn=d1 --key=123 --value=123456", Messages.NotValidValue);
//        strings.put("add --dn=d2 --key=12345 --value=werty", "");
//
//
//        strings.forEach((k, v) -> {
//                    var actual = ConsoleService.createActionRequestFromCommandLine(k);
//                    assertEquals(v, actual.message);
//                }
//        );
//
//        var stringsCommand = new HashMap<String, Enum>();
////        stringsCommand.put("exit", Commands.EXIT);
////        stringsCommand.put("exit ssdf", Commands.EXIT);
////        stringsCommand.put("exit --dn", Commands.EXIT);
////        stringsCommand.put("exit --dn=", Commands.EXIT);
////        stringsCommand.put("exit --dn=34", Commands.EXIT);
////        stringsCommand.put("exit --dn=34 dflkvln", Commands.EXIT);
////        stringsCommand.put("23424 exit --dn=34 dflkvln", Commands.EXIT);
//
//        stringsCommand.put("all", Commands.ALL);
//        stringsCommand.put("all ssdf", Commands.ALL);
//        stringsCommand.put("all --dn", Commands.ALL);
//        stringsCommand.put("all --dn=", Commands.ALL);
//        stringsCommand.put("all --dn=34", Commands.ALL);
//        stringsCommand.put("all --dn=34 dflkvln", Commands.ALL);
//        stringsCommand.put("23424 all --dn=34 dflkvln --key=sbsdcj --value=sjhdb", Commands.ALL);
//
//        stringsCommand.put("add", Commands.ADD);
//        stringsCommand.put("add ssdf", Commands.ADD);
//        stringsCommand.put("add --dn", Commands.ADD);
//        stringsCommand.put("add --dn=", Commands.ADD);
//        stringsCommand.put("add --dn=34", Commands.ADD);
//        stringsCommand.put("add --dn=34 dflkvln", Commands.ADD);
//        stringsCommand.put("23424 add --dn=34 dflkvln --key=sbsdcj --value=sjhdb", Commands.ADD);
//
//        stringsCommand.put("find", Commands.FIND);
//        stringsCommand.put("find ssdf", Commands.FIND);
//        stringsCommand.put("find --dn", Commands.FIND);
//        stringsCommand.put("find --dn=", Commands.FIND);
//        stringsCommand.put("find --dn=34", Commands.FIND);
//        stringsCommand.put("find --dn=34 dflkvln", Commands.FIND);
//        stringsCommand.put("23424 find --dn=34 dflkvln --key=sbsdcj --value=sjhdb", Commands.FIND);
//
//        stringsCommand.put("delete", Commands.DELETE);
//        stringsCommand.put("delete ssdf", Commands.DELETE);
//        stringsCommand.put("delete --dn", Commands.DELETE);
//        stringsCommand.put("delete --dn=", Commands.DELETE);
//        stringsCommand.put("delete --dn=34", Commands.DELETE);
//        stringsCommand.put("delete --dn=34 dflkvln", Commands.DELETE);
//        stringsCommand.put("23424 Delete --dn=34 dflkvln --key=sbsdcj --value=sjhdb", Commands.DELETE);
//
//        stringsCommand.forEach((k, v) -> {
//                    System.out.println(k);
//                    var actual = ConsoleService.createActionRequestFromCommandLine(k);
//                    System.out.println(actual.message);
//                    System.out.println(actual.valid);
//                    System.out.println(actual.command);
//                    assertEquals(v.name(), actual.command.name());
//                }
//        );
//
//        var stringsDn = new HashMap<String, String>();
//        stringsDn.put("exit", null);
//        stringsDn.put("exit ssdf", null);
//        stringsDn.put("exit --dn", null);
//        stringsDn.put("exit --dn=", null);
//        stringsDn.put("exit --dn=34", null);
//        stringsDn.put("exit --dn=d1", "d1.txt");
//        stringsDn.put("exit --dn=d2", "d2.txt");
//        stringsDn.put("exit --dn=d3", null);
//        stringsDn.put("exit --dn=344 dflkvln", null);
//        stringsDn.put("23424 exit --dn=34 dflkvln", null);
//        stringsDn.forEach((k, v) -> {
//                    System.out.println(k);
//                    var actual = ConsoleService.createActionRequestFromCommandLine(k);
//                    assertEquals(v, actual.dn);
//                }
//        );
//
//        var stringsKey = new HashMap<String, String>();
//        stringsKey.put("exit", null);
//        stringsKey.put("exit ssdf", null);
//        stringsKey.put("exit --dn", null);
//        stringsKey.put("exit key", null);
//        stringsKey.put("exit --key", null);
//        stringsKey.put("exit --key=", null);
//        stringsKey.put("exit --dn=d2", null);
//        stringsKey.put("exit --dn=d3", null);
//        stringsKey.put("exit --dn=344 --key=dflkvln", "dflkvln");
//        stringsKey.put("23424 exit --dn=34 dflkvln", null);
//        stringsKey.forEach((k, v) -> {
//                    System.out.println(k);
//                    var actual = ConsoleService.createActionRequestFromCommandLine(k);
//                    assertEquals(v, actual.key);
//                }
//        );
//
//        var stringsValue = new HashMap<String, String>();
//        stringsValue.put("exit", null);
//        stringsValue.put("exit ssdf", null);
//        stringsValue.put("exit --dn", null);
//        stringsValue.put("exit value", null);
//        stringsValue.put("exit --value", null);
//        stringsValue.put("exit --value=", null);
//        stringsValue.put("exit --dn=d2", null);
//        stringsValue.put("exit --dn=d3", null);
//        stringsValue.put("exit --dn=344 --value=dflkvln", null);
//        stringsValue.put("23424 exit --dn=d1 --value=123456", null);
//        stringsValue.put("23424 exit --dn=d1 --value=1234", "1234");
//        stringsValue.put("23424 exit --dn=d1 --value=12", null);
//        stringsValue.put("23424 exit --dn=34 --value=1234", null);
//
//        stringsValue.put("23424 exit --dn=d2 --value=qw", null);
//        stringsValue.put("23424 exit --dn=d2 --value=qqwwe", "qqwwe");
//        stringsValue.put("23424 exit --dn=d2 --value=eerrttyy", null);
//        stringsValue.put("23424 exit --dn=34 --value=eeeee", null);
//        stringsValue.forEach((k, v) -> {
//                    System.out.println(k);
//                    var actual = ConsoleService.createActionRequestFromCommandLine(k);
//                    assertEquals(v, actual.value);
//                }
//        );
//    }
}
