import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.*;

//don't need to override every enter/exit method
public class VariableCheckingListener extends LeverBaseListener {

	public enum LType {
    	LString, LInteger, LDouble, LBoolean,
    	LList, LDictionary, LUser, LTopic, LResult 
	}

	public HashMap<String, LType> symbolTable = new HashMap<String, LType>();

	public VariableCheckingListener(LeverParser parser) {


	}



}
