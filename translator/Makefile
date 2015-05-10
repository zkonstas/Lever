JCC = javac
JFLAGS = -g

SOURCEFILES = \
	LeverLexer.java \
	LeverParser.java \
	*Listener.java \
	Leverc.java

CLASSFILES = $(SOURCEFILES:.java=.class)

classes: $(CLASSFILES)
%.class: %.java
	$(JCC) $(JFLAGS) $<

.PHONY: antlr all test
antlr:
	java -Xmx500M org.antlr.v4.Tool Lever.g4

all: antlr $(CLASSFILES)

test: classes
	java Leverc ../leverSamples/HelloWorld.lever
	
.PHONY: clean cleanall cleanant
clean:
	rm *.class

cleanall: clean
	rm LeverLexer.java LeverParser.java LeverListener.java LeverBaseListener.java
	rm Lever.tokens LeverLexer.tokens

cleanant:
	rm LeverLexer.java LeverParser.java LeverListener.java LeverBaseListener.java
	rm Lever.tokens LeverLexer.tokens
