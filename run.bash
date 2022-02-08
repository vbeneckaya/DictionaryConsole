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

#find ./src/main -name "*.java" > sources.txt
#javac @sources.txt -d ./target
#
#find ./src/test -name "*.java" > sources.txt
#javac @sources.txt -d ./target

javac  -sourcepath src/main/java   src/main/java/com/dict/app/Word.java -d ./target -cp ./target || exit
cp ./src/main/res/DictionaryTypes.txt ./target/res/ || exit

cp ./src/test/res/DictionaryTypes.txt ./target/test/res || exit
javac   ./src/test/MyTest.java -d ./target/test -cp ./target || exit

#cd ./target || exit
#
java org.junit.runner.JUnitCore ./target/test/MyTest.java
#junit