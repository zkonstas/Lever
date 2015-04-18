JCC = javac
JFLAGS = -g

SOURCEFILES = \
	LeverGrammarLexer.java \
	LeverGrammarParser.java \
	LeverGrammarListener.java \
	LeverGrammarBaseListener.java \
	LeverToJavaListener.java \
	Leverc.java \
	LeverAPI.java

CLASSFILES = $(SOURCEFILES:.java=.class)

classes: $(CLASSFILES)
%.class: %.java
	$(JCC) $(JFLAGS) $<

.PHONY: antlr all test
antlr:
	java -Xmx500M org.antlr.v4.Tool LeverGrammar.g4

all: antlr $(CLASSFILES)

test: classes
	java Leverc ../leverSamples/HelloWorld.lever
	
.PHONY: clean cleanall cleanant
clean:
	rm *.class

cleanall: clean
	rm LeverGrammarLexer.java LeverGrammarParser.java LeverGrammarListener.java LeverGrammarBaseListener.java
	rm LeverGrammar.tokens LeverGrammarLexer.tokens

cleanant:
	rm LeverGrammarLexer.java LeverGrammarParser.java LeverGrammarListener.java LeverGrammarBaseListener.java
	rm LeverGrammar.tokens LeverGrammarLexer.tokens