JCC = javac
JFLAGS = -g -d $(CLASSDIR)
CLASSDIR = classes

SOURCEFILES = \
	LeverLexer.java \
	LeverParser.java \
	LeverListener.java \
	LeverBaseListener.java \
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
	java -Xmx500M org.antlr.v4.Tool Lever.g4

.PHONY: clean
clean:
	rm $(CLASSDIR)/*.class

.PHONY: cleanall
cleanall: clean
	rm LeverLexer.java LeverParser.java LeverListener.java LeverBaseListener.java
	rm Lever.tokens LeverLexer.tokens

.PHONY: cleanant
cleanant:
	rm LeverLexer.java LeverParser.java LeverListener.java LeverBaseListener.java
	rm Lever.tokens LeverLexer.tokens
