#!/opt/homebrew/bin/fish
mvn clean compile exec:java -Dexec.mainClass=com.itba.eda.ProductSearch.ProductSearch -Dexec.args="$argv"
