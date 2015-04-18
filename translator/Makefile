JCC = javac
  2 JFLAGS = -g
  3 
  4 SOURCEFILES = \
  5     LeverGrammarLexer.java \
  6     LeverGrammarParser.java \
  7     LeverGrammarListener.java \
  8     LeverGrammarBaseListener.java \
  9     LeverToJavaListener.java \
 10     Leverc.java \
 11     LeverAPI.java
 12 
 13 CLASSFILES = $(SOURCEFILES:.java=.class)
 14 
 15 classes: $(CLASSFILES)
 16 %.class: %.java
 17     $(JCC) $(JFLAGS) $<
 18 
 19 .PHONY: antlr all test
 20 antlr:
 21     java -Xmx500M org.antlr.v4.Tool LeverGrammar.g4
 22 
 23 all: antlr $(CLASSFILES)
 24 
 25 test: classes
 26     java Leverc ../leverSamples/HelloWorld.lever
 27     
 28 .PHONY: clean cleanall cleanant
 clean:
 30     rm *.class
 31 
 32 cleanall: clean
 33     rm LeverGrammarLexer.java LeverGrammarParser.java LeverGrammarListener.java LeverGrammarBaseListener.java
 34     rm LeverGrammar.tokens LeverGrammarLexer.tokens
 35 
 36 cleanant:
 37     rm LeverGrammarLexer.java LeverGrammarParser.java LeverGrammarListener.java LeverGrammarBaseListener.java
 38     rm LeverGrammar.tokens LeverGrammarLexer.tokens
 
 
