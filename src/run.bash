if [ ! -d ./target ]; then
  mkdir ./target
fi

javac ./word/Word.java -d ./target || exit
cp ./word/DictionaryTypes.txt ./target/word || exit

javac ./tests/MyTest.java -d ./target || exit
cp ./tests/DictionaryTypes.txt ./target/tests || exit
cd ./target || exit

java org.junit.runner.JUnitCore tests.MyTest
#junit