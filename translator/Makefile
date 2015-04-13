JFLAGS = -g -d classes
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	LeverGrammarLexer.java \
	LeverGrammarParser.java \
	LeverGrammarListener.java \
	LeverGrammarBaseListener.java \
	LeverToJavaListener.java \
	Leverc.java \

default: classes

antlr:
	java -Xmx500M org.antlr.v4.Tool LeverGrammar.g4
	
classes: $(CLASSES:.java=.class)

.PHONY: clean
clean:
	rm *.class

.PHONY: cleanall
cleanall: clean
	rm LeverLexer.java LeverParser.java LeverListener.java LeverBaseListener.java
	rm Lever.tokens LeverLexer.tokens

