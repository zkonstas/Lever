JCC = javac
JFLAGS = -g -d $(CLASSDIR)
CLASSDIR = classes

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

all: antlr classes

.PHONY: antlr
antlr:
	java -Xmx500M org.antlr.v4.Tool LeverGrammar.g4

.PHONY: clean
clean:
	rm $(CLASSDIR)/*.class

.PHONY: cleanall
cleanall: clean
	rm LeverGrammarLexer.java LeverGrammarParser.java LeverGrammarListener.java LeverGrammarBaseListener.java
	rm LeverGrammar.tokens LeverGrammarLexer.tokens

.PHONY: cleanant
cleanant:
	rm LeverGrammarLexer.java LeverGrammarParser.java LeverGrammarListener.java LeverGrammarBaseListener.java
	rm LeverGrammar.tokens LeverGrammarLexer.tokens
