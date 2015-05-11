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
public class LeverToJavaListener extends LeverBaseListener {
	LeverParser parser;

	public boolean getFlag;
	
	private String fileName;
	private File targetFile;
	private BufferedWriter bw;

	private int indents;

	private HashSet<String> leverConstructs = new HashSet<String>();
	private HashSet<String> leverTerminals = new HashSet<String>();
	private HashSet<String> leverAPIfunctions = new HashSet<String>();
    private HashSet<String> spaceAfter = new HashSet<String>();
    private Boolean hold = false;
    private String currentMethod = "";
    public static HashMap<String,String> funcDefinitions = new HashMap<String, String>();

	private static String userKey = "uSeR";

	private HashMap<String, VariableCheckingListener.LType> symbolTable;
	
	public LeverToJavaListener(LeverParser parser, String _fileName,
		HashMap<String, VariableCheckingListener.LType> _symbolTable) {

		this.parser = parser;
		this.symbolTable = _symbolTable;
		
		initConstructs();
		
		//String fileName = _fileName.split(".")[0];
		Path p = Paths.get(_fileName);
		fileName = p.getFileName().toString();
		int pos = fileName.lastIndexOf(".");
		if (pos > 0) fileName = fileName.substring(0, pos);

		//javaFile = Paths.get(fileName + ".java");
		
		String path = _fileName.substring(0, _fileName.lastIndexOf(File.separator));
		path = path + File.separator + fileName + ".java";
		
		try {
			targetFile = new File(path);
			if (!targetFile.exists()) {
				targetFile.createNewFile();
			}

			FileWriter fw = new FileWriter(targetFile);
			bw = new BufferedWriter(fw);
			System.out.println(path);
		} catch (IOException e) {
			System.out.println("error creating target file..");
			System.exit(1);
		}

		indents = 0;
	}
	private void initConstructs() {
		leverConstructs.add("for");
		leverConstructs.add("output");
		leverConstructs.add("get");

		leverTerminals.add("for");
		leverTerminals.add("each");
		leverTerminals.add("in");

        //don't keep (,), gets complicated for nested arithmetic etc
		leverTerminals.add("("); //do not remove, parenthesis in lever very different ordering
		leverTerminals.add(")");
		//leverTerminals.add(",");

		leverTerminals.add("yes");
		leverTerminals.add("no");
        leverTerminals.add("true"); //do not remove, handle the same way as yes/no
        leverTerminals.add("false");


		leverTerminals.add("var");
		leverTerminals.add("program");

        //initializing arrayList needs to remove brackets
        leverTerminals.add("[");
        leverTerminals.add("]");

        spaceAfter.add("return");
		
		leverAPIfunctions.add("get");
		leverAPIfunctions.add("output");
		leverAPIfunctions.add("input");
		leverAPIfunctions.add("graph");
	}
	private void openBraces() {
		indents++;
		printTarget(hold,"{\n");
	}
	private void closeBraces() {
		indents--;
		printTarget(hold,"\n");
		printTabs();
		printTarget(hold,"}\n");
	}
	private void printTabs() {
		int i = indents;
		String temp = "";
		while (i > 0) {
			printTarget(hold,"\t");
			i--;
		}
	}

	private void printTarget(Boolean hold, String s) {
		if (!hold) {
			try {
				bw.write(s);
			} catch (IOException e) {
				System.out.println("error writing to file..");
				System.exit(1);
			}
		} else {
			currentMethod += s;
		}
	}


	@Override public void enterLever(LeverParser.LeverContext ctx) {
		printTarget(hold, "import sun.jvm.hotspot.utilities.Interval;\n");
		printTarget(hold,"import twitter4j.*;\n");
		printTarget(hold,"import twitter4j.User;\n");
		printTarget(hold,"\n");
		printTarget(hold,"import java.util.*;\n\n");
		printTarget(hold, "import LeverAPIPackage.*;\n\n");

		printTarget(hold,"public class " + fileName + " ");
		openBraces();
		
	}

	@Override
	public void enterMainProgram(LeverParser.MainProgramContext ctx) {
		printTarget(hold,"\tpublic static void main(String[] args) ");
	}

	@Override
	public void exitMainProgram(LeverParser.MainProgramContext ctx) {

	}
	@Override public void exitLever(LeverParser.LeverContext ctx) {
		for(Map.Entry<String, String> entry : funcDefinitions.entrySet()){

			printTabs();
			String myFunction = entry.getValue();
			int index = myFunction.indexOf(")");
			String temp = myFunction.substring(0, index+1);
			index = myFunction.indexOf("{");
			myFunction = temp+myFunction.substring(index);
		    printTarget(hold, myFunction);
		}

		closeBraces();
		//printTarget(hold,"exit lever");

		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void enterBlock(LeverParser.BlockContext ctx) {
	}
	@Override
	public void exitBlock(LeverParser.BlockContext ctx) {
	}
	@Override public void enterBlockStatement(LeverParser.BlockStatementContext ctx) {
		
		
		
	}

	@Override public void enterNonBlockStatement(LeverParser.NonBlockStatementContext ctx) {
		//printTarget(hold,"\n");
		printTabs();	
	}

	@Override
	public void enterStatement(LeverParser.StatementContext ctx) {
				
		//printTabs();

		//TokenStream tokens = parser.getTokenStream();
		//String i = "blah";
		//if (ctx.IF() != null) {
			//i = tokens.getText(ctx.IF());
			//i = ctx.IF().toString();
			//System.out.println("asdf " + i);
		//	printTarget(hold,"if ");
		//}
	}

	@Override public void exitStatement(LeverParser.StatementContext ctx) {
		//printTarget(hold,"\n");	
	}
	
	@Override
	public void enterForIn(LeverParser.ForInContext ctx) {
		//TokenStream tokens = parser.getTokenStream();

		TerminalNode begin = ctx.getToken(LeverLexer.NumberLiteral, 0);
		TerminalNode end = ctx.getToken(LeverLexer.NumberLiteral, 1);

		printTarget(hold,"for (int " + ctx.Identifier() + " = " + begin + "; ");
		printTarget(hold,ctx.Identifier() + " < " + end + "; " + ctx.Identifier() + "++) ");
	}
	@Override
	public void exitForIn(LeverParser.ForInContext ctx) {
	}

	@Override
	public void enterForEach(LeverParser.ForEachContext ctx) {
		
		printTarget(hold,"for (String ");

		TerminalNode userTerminal = ctx.getToken(LeverLexer.AT, 0);
		if (userTerminal != null) {
			printTarget(hold,userKey + " : ");
			printTarget(hold,ctx.getToken(LeverLexer.Identifier, 0) + ".user) ");

		} else {
			printTarget(hold,ctx.getToken(LeverLexer.Identifier, 0) + " : ");
			printTarget(hold,ctx.getToken(LeverLexer.Identifier, 1) + ") ");
		}
	}

	@Override
	public void enterParExpression(LeverParser.ParExpressionContext ctx) {
		
		printTarget(hold,"(");
	}
	@Override
	public void exitParExpression(LeverParser.ParExpressionContext ctx) {
		printTarget(hold,") ");
		
	}

	@Override public void enterExpression(LeverParser.ExpressionContext ctx) {

		if (ctx.resultUserAccess() != null) {
			// int i = ;

			//printTarget(false, "LeverAPI.getInfo(result.get(i), \"user\")");
			return;
		}

	}

	@Override public void enterResultUserAccess(LeverParser.ResultUserAccessContext ctx) {
		hold = true;
		printTarget(false, "LeverAPI.getInfo(result.get(i), \"user\")");

	}

	@Override public void exitResultUserAccess(LeverParser.ResultUserAccessContext ctx) {
		hold = false;
	}
	

	@Override
	public void enterExpressionList(LeverParser.ExpressionListContext ctx) {
		if (!leverTerminals.contains("dontPrintParams"))
			printTarget(hold,"(");

	}
	@Override
	public void exitExpressionList(LeverParser.ExpressionListContext ctx) {
		if (!leverTerminals.contains("dontPrintParams"))
			printTarget(hold,")");

	}

	@Override
	public void enterStatementExpression(LeverParser.StatementExpressionContext ctx) {

		LeverParser.ExpressionContext expCtx = ctx.expression();

		if (expCtx != null && expCtx.getToken(LeverLexer.ASSIGN, 0) != null) {

			if (expCtx.expression().get(0) != null && expCtx.expression().get(0).arrayAccess() != null) {
				leverTerminals.add("=");
			}	
		}
		
			
	}
	@Override
	public void exitStatementExpression(LeverParser.StatementExpressionContext ctx) {
		if (leverTerminals.contains("=")) {
			printTarget(hold, ")");
			leverTerminals.remove("=");
		}
	}
	
	@Override
	public void enterLiteral(LeverParser.LiteralContext ctx) {
		//printTarget(hold,ctx.StringLiteral().getText());
		if (ctx.BooleanLiteral() != null) {
			String lit = ctx.BooleanLiteral().toString();
			if (lit.equals("yes") || lit.equals("true")) {
				printTarget(hold,"true");
			} else if (lit.equals("no") || lit.equals("false")) {
				printTarget(hold,"false");
			}
		}
	}
	@Override
	public void enterPrimary(LeverParser.PrimaryContext ctx) {
		
	}

	@Override public void enterVariableDeclarator(LeverParser.VariableDeclaratorContext ctx) {
	}

	@Override public void exitVariableDeclarator(LeverParser.VariableDeclaratorContext ctx) {
		
	}
	@Override public void enterMethodDefinition(LeverParser.MethodDefinitionContext ctx) {

		hold = true;

		// String varId = ctx.Identifier().getText();
		// VariableCheckingListener.LType type = symbolTable.get(varId);

		// printTabs();
		// printTarget("public static ");

		// if (type != null) {
		// 	printTarget(getJavaType(type) + " ");			
		// }
		// else {
		// 	printTarget("void ");
		// }
	}
		
	@Override public void exitMethodDefinition(LeverParser.MethodDefinitionContext ctx) {
		
		funcDefinitions.put(ctx.Identifier().getText(), currentMethod);
		currentMethod = "";
		hold = false;
	}

	@Override public void enterFormalParameterList(LeverParser.FormalParameterListContext ctx) { 
		printTarget(hold,"(");
	}

	@Override public void exitFormalParameterList(LeverParser.FormalParameterListContext ctx) { 
		printTarget(hold,")");
	}


	@Override public void enterIdentifierVar(LeverParser.IdentifierVarContext ctx) {
		printTabs();

		VariableCheckingListener.LType _type = symbolTable.get(ctx.Identifier().getText());
		if (_type != null)
			printTarget(hold,getJavaType(_type) + " ");	
	}

	@Override public void exitIdentifierVar(LeverParser.IdentifierVarContext ctx) {
			
	}

	private String getJavaType(VariableCheckingListener.LType _type) {
		if (_type != null)
		switch (_type) {
			case LString:
				return "String";
			case LInteger:
				return "int";
			case LDouble:
				return "double";
			case LBoolean:
				return "boolean";
			case LDictionary:
				return "dictionary";


			case LUser:
				return "String";
			case LTopic:
				return "String";

			case LResult:
				return "Result";

            case LListInteger:
                return "ArrayList<Integer>";
            case LListDouble:
                return "ArrayList<Double>";
            case LListString:
                return "ArrayList<String>";
            case LListBoolean:
                return "ArrayList<Boolean>";

			default:
				return "???";


		}
		else
			return null;

		}

	@Override public void enterMethodCall(LeverParser.MethodCallContext ctx) {

		if (ctx.objectMethodCall() != null) {
			return;
		}

		if (ctx.Identifier().getText().equals("get")) {
			leverTerminals.add("dontPrintParams");
			String text = ctx.getText();

            String tmp = text.substring(text.indexOf("get")+3, text.length());
            String[] tmp2 = tmp.split("\\[");
            if (tmp2.length > 1) {
                String[] tmp3 = tmp2[1].split("\"");
                
                String params = "";

                for (int i=1; i<tmp3.length; i++) {
                	params+="\\\"";
                	params+=tmp3[i];
                }

                String str0 = tmp2[0].replace("\"", "");

                printTarget(hold,"LeverAPI.get(\"" + str0 + "[" + params + "\")");
            } else {
                printTarget(hold,"LeverAPI.get(" + tmp + ")");
            }
		}

        String funcId = ctx.Identifier().getText();

		if (!leverAPIfunctions.contains(funcId)) {

			LeverParser.MethodDefinitionContext funcDefCtx = VariableCheckingListener.functionTable.get(funcId);
			
			// //assign parameter types
			if (funcDefCtx != null && funcDefCtx.formalParameterList() != null) {
				List<TerminalNode> parameterIds = funcDefCtx.formalParameterList().Identifier();

				LeverParser.ExpressionListContext expLCtx = ctx.expressionList();

				List<LeverParser.ExpressionContext> arguments = null;

				if (expLCtx != null) {
					 arguments =  expLCtx.expression();
				}
				
				
				String params = "";

				for (int i=0; i< parameterIds.size(); i++) {

					TerminalNode termIds = parameterIds.get(i);
					String argId = termIds.getText();

					LeverParser.ExpressionContext arg = arguments.get(i);
					VariableCheckingListener.LType type = VariableCheckingListener.getExpressionType(arg);

					VariableCheckingListener.initializeVarIdType(argId, type);
					if (params.equals("")) {
						params+= getJavaType(type)+" "+argId;

					}
					else {
						params+= ", " +getJavaType(type)+" "+argId;	
					}
					

				}
				StringBuffer buf = new StringBuffer();
				buf.append("public static ");

				VariableCheckingListener.LType returnType = VariableCheckingListener.getMethodCallType(funcId);
				if (returnType == null) {
					buf.append("void ");
				}
				else {
					buf.append(getJavaType(returnType) + " ");
				}

				buf.append(funcId+"("+params+")");
				// System.out.println(buf.toString());
				//Place func signature to body
				String body = funcDefinitions.get(funcId);
				funcDefinitions.put(funcId, buf.toString()+body);

				// System.out.println(funcDefinitions.get(funcId));
			}
		}
	}

	@Override public void enterZeroArgumentMethodCall(LeverParser.ZeroArgumentMethodCallContext ctx) {
		String funcId = ctx.Identifier().getText();

		StringBuffer buf = new StringBuffer();
				buf.append("public static ");

		VariableCheckingListener.LType returnType = VariableCheckingListener.getMethodCallType(funcId);
				if (returnType == null) {
					buf.append("void ");
				}
				else {
					buf.append(getJavaType(returnType) + " ");
				}

		String body = funcDefinitions.get(funcId);
			
		funcDefinitions.put(funcId, buf.toString()+funcId+"()"+body);

	}

	@Override public void exitZeroArgumentMethodCall(LeverParser.ZeroArgumentMethodCallContext ctx) {
		printTarget(hold, "()");

	}

	@Override public void exitMethodCall(LeverParser.MethodCallContext ctx) {

		if (ctx.objectMethodCall() != null) {
			return;
		}

		if (ctx.Identifier() != null && ctx.Identifier().getText().equals("get")) {
			leverTerminals.remove("dontPrintParams");
		}
	}
	@Override public void enterInitialization(LeverParser.InitializationContext ctx) {

		//LeverParser.VariableDeclaratorContext parent = (LeverParser.VariableDeclaratorContext)ctx.getParent();
		//TerminalNode id = parent.identifierVar().Identifier();

		//Get type and assign it to the appropriate member of LeverVar

		//printTabs();
		//printTarget(hold,id.getText() + ".val ");
	}

	@Override public void exitInitialization(LeverParser.InitializationContext ctx) {
		//printTarget(hold,";\n");
	}
    @Override public void enterArrayInit(LeverParser.ArrayInitContext ctx) {
    	List<LeverParser.ExpressionContext> exps = ctx.expression();

    	VariableCheckingListener.LType type = null;

    	if (exps.size() == 1) {
    		type = VariableCheckingListener.getExpressionType( exps.get(0) );

    		if (type==VariableCheckingListener.LType.LInteger) {
    			printTarget(hold,"new ArrayList<>(Collections.nCopies(");
    			return;
    		}
    	}
        printTarget(hold,"new ArrayList<>(Arrays.asList(");
    }
    @Override public void exitArrayInit(LeverParser.ArrayInitContext ctx) {
    	List<LeverParser.ExpressionContext> exps = ctx.expression();

    	VariableCheckingListener.LType type = null;

    	if (exps.size() == 1) {
    		type = VariableCheckingListener.getExpressionType( exps.get(0) );

    		if (type==VariableCheckingListener.LType.LInteger) {
    			printTarget(hold,",0))");
    			return;
    		}
    	}
        printTarget(hold,"))");
    }
    @Override public void enterArrayAccess(LeverParser.ArrayAccessContext ctx) {
       	printTarget(hold,".set(");
    }
    @Override public void exitArrayAccess(LeverParser.ArrayAccessContext ctx) {
        printTarget(hold,",");
    }
	@Override
	public void visitTerminal(TerminalNode node) {

		Token token = node.getSymbol();
		int type = token.getType();
		String id = token.getText();

		String tmp;
		switch(type) {
			case LeverLexer.Identifier:
				if (id.equals("output")) {
					printTarget(hold,"LeverAPI.output");
					
				} else if (id.equals("input")) {
					printTarget(hold,"LeverAPI.input()");
				
				
				} else if (id.equals("graph")) {
					printTarget(hold,"LeverAPI.graph");

				} else if (id.equals("get")) {

				} else {
					tmp = node.getParent().getChild(0).toString();
					if (leverConstructs.contains(tmp)  || (leverTerminals.contains("dontPrintParams"))) {

					} else {


                        ParserRuleContext pNode = (ParserRuleContext)node.getParent();
                        if (pNode instanceof LeverParser.MethodCallContext) {
                            printTarget(hold,id);
                        } else {
                            printTarget(hold,id);
                            //space?
                        }


					}
				}
				
				break;

			case LeverLexer.SEMI:
				printTarget(hold,";\n");
				break;

			case LeverLexer.NumberLiteral:
				tmp = node.getParent().getChild(0).toString();
				if (leverConstructs.contains(tmp)) { //numbers in 'for loop'
					break;
				}
				//printTarget(hold,id);
				//break;

			case LeverLexer.StringLiteral:
                if (!leverTerminals.contains("dontPrintParams")) {
                    printTarget(hold,id);
                }

				break;

			case LeverLexer.AND:
				printTarget(hold," && ");
				break;
			case LeverLexer.OR:
				printTarget(hold," || ");
				break;
			case LeverLexer.IF:
				printTarget(hold,"if ");
				break;
			case LeverLexer.ELSE:
				printTarget(hold," else ");
				break;
			case LeverLexer.WHILE:
				printTarget(hold,"while ");
				break;

			case LeverLexer.LBRACE:
				openBraces();
				break;

			case LeverLexer.RBRACE:
				closeBraces();
				break;

			case LeverLexer.AT:
				tmp = node.getParent().getChild(0).toString();
				if (leverConstructs.contains(tmp)) {

				} else {
					printTarget(hold,userKey);
				}
				break;

			default:

				if (!(leverTerminals.contains(id) || (leverTerminals.contains("dontPrintParams")))) {
                    if (spaceAfter.contains(id)) {
                        printTarget(hold,id + " ");
                    } else {



                        tmp = node.getParent().getChild(0).toString();
                        if (tmp.equals("for") && id.equals(",")) {

                        } else {
                            printTarget(hold,id);
                        }

                    }
				}
		}
	}
}
