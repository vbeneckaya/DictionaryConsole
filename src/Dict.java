public class Dict {
    public Dict(String file, String wordPattern){
        this.File = file;
        this.WordPattern = wordPattern;
    }
    public String WordPattern;
    public String File;

    public void Add(DictConfig data){

    }


    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        }

        Dict d = (Dict) o;

        return (this.File.equals(d.File) && this.WordPattern.equals(d.WordPattern));
    }
}
