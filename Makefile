all: Main.class

run:
	for a in *in; do b=$${a%%.in}.nico; java Main < $$a > $$b; done

test: run
	for a in *out; do b=$${a%%.out}.nico; diff -u $$a $$b; done


clean:
	find . -name \*.class -exec rm {} \;

%.class: *.java
	javac $<
