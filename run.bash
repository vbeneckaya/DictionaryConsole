if [ ! -d ./target ]; then
  mkdir ./target
fi

if [ ! -d ./target/res ]; then
  mkdir ./target/res
fi

if [ ! -d ./target/test ]; then
  mkdir ./target/test
fi

if [ ! -d ./target/test/res ]; then
  mkdir ./target/test/res
fi

if [  -d ./target/com ]; then
  echo "delete ./target/com"
  rm -rf ./target/com
fi

#find ./src/main -name "*.java" > sources.txt
#javac @sources.txt -d ./target
#
#find ./src/test -name "*.java" > sources.txt
#javac @sources.txt -d ./target

javac  -sourcepath src/main/java   src/main/java/com/dict/app/Word.java -d ./target -cp ./target || exit
cp ./src/main/res/DictionaryTypes.txt ./target/res/ || exit
cp ./src/main/res/ConnectionString.txt ./target/res/ || exit

#jar cvf word.jar ./target


java -cp ./target    com.dict.app.Word

#cp ./src/test/res/DictionaryTypes.txt ./target/test/res || exit
#echo $CLASSPATH
#javac   ./src/test/MyTest.java -d ./target/test -cp ./target:/Library/Java/Extensions/junit-4.12.jar:/Library/Java/Extensions/hamcrest-core-1.3.jar || exit
#
#cd ./target/test || exit

#java org.junit.runner.JUnitCore MyTest
#junit