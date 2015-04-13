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
	LeverAPI.java \

default: classes

antlr:
	java -Xmx500M org.antlr.v4.Tool LeverGrammar.g4
	
classes: $(CLASSES:.java=.class)

.PHONY: clean
clean:
	rm classes/*.class

.PHONY: cleanall
cleanall: clean
	rm LeverGrammarLexer.java LeverGrammarParser.java LeverGrammarListener.java LeverGrammarBaseListener.java
	rm LeverGrammar.tokens LeverGrammarLexer.tokens

.PHONY: cleanant
cleanant:
	rm LeverGrammarLexer.java LeverGrammarParser.java LeverGrammarListener.java LeverGrammarBaseListener.java
	rm LeverGrammar.tokens LeverGrammarLexer.tokens
