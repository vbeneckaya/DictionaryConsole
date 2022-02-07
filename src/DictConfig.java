import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class DictConfig {
    public DictConfig(String commandString, List<Dict> validDictionaries){
        var args = commandString.toLowerCase(Locale.ROOT).split(" ");
        Arrays.sort(args);

        if (args.length < 1 || args[0]=="") {
            Message = Messages.NotEnoughArguments;
            return;
        }
        for (String arg : args) {
            if (arg.matches("^exit$")) {
                this.Command = Word.Commands.EXIT;
            } else if (arg.matches("^all$")) {
                this.Command = Word.Commands.ALL;
            } else if (arg.matches("^add$")) {
                this.Command = Word.Commands.ADD;
            } else if (arg.matches("^find$")) {
                this.Command = Word.Commands.FIND;
            } else if (arg.matches("^delete$")) {
                this.Command = Word.Commands.DELETE;
            }
            else if (arg.matches("^--dn=.*")) {
                var argValues = arg.split("=");
                if (argValues.length != 2) {
                    Message = Messages.DnValueError;
                    this.Valid = false;
                } else {
                    var val = arg.split("=")[1];
                    var isValidDict = false;
                    for (Dict validDictionary : validDictionaries) {
                        if (validDictionary.File.matches("^" + val + ".txt$")) {
                            this.Dn = validDictionary.File;
                            isValidDict = true;
                        }
                        ;
                    }
                    if (isValidDict == false) {
                        Message = Messages.NoSuchDictionary;
                    }
                }
            }
            else if (arg.matches("^--key=.*")) {
                var argValues = arg.split("=");
                if (argValues.length != 2) {
                    this.Valid = false;
                    Message = Messages.KeyValueError;
                } else {
                    this.Key = arg.split("=")[1];
                }
            }
            else if (arg.matches("^--value=.*")) {
                var argValues = arg.split("=");
                if (argValues.length != 2) {
                    Message = Messages.ValueValueError;
                    this.Valid = false;
                } else {
                    var val = arg.split("=")[1];
                    if (validDictionaries.stream().filter(e->e.File == this.Dn && val.matches(e.WordPattern)).findFirst().isPresent()) {
                        this.Value = val;
                    }
                    else {
                        this.Valid = false;
                        Message = Messages.NotValidValue;
                    }
                }
            }
            else {
                Message = Messages.WrongArgument;
                this.Valid = false;
            }
        }
    }
    public String Dn;
    public String Key;
    public String Value;
    public Word.Commands Command;
    public boolean Valid = true;
    public String Message = "";
}
