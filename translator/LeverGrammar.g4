///////////
/* LEVER */
///////////

grammar LeverGrammar;

//////////////////
/* PARSER RULES */
//////////////////

lever
	: methodDefinition* PROGRAM block methodDefinition*
	;
    

memberDeclaration
    :   fieldDeclaration
    ;

methodDefinition
	: Identifier formalParameters methodBody
    ;
    
fieldDeclaration
    :   type declarationList ';'
    ;

declarationList
	: variableDeclarator (',' variableDeclarator)*
	;

variableDeclarator
	: variableId ('=' variableInit)?
	;

variableId
    :   Identifier
    ;

variableInit
    :   arrayInit
    |   expression
    ;

arrayInit
    :   '[' (variableInit (',' variableInit)* (',')? )? ']'
    ;

type
    :   primitiveType
    ;

primitiveType
    :   'var'
    ;

formalParameters
    :   '(' formalParameterList? ')'
    ;

formalParameterList
    :   formalParameter (',' formalParameter)* (',' lastFormalParameter)?
    |   lastFormalParameter
    ;

formalParameter
    :   type variableId
    ;

lastFormalParameter
    :   type '...' variableId
    ;

methodBody
    :   block
    ;

literal
    :   NumberLiteral
    |   StringLiteral
    |   BooleanLiteral
    |   'null'
    ;

// STATEMENTS / BLOCKS

block
    :   '{' blockStatement* '}'
    ;

blockStatement
    :   localVariableDeclarationStatement
    |   statement
    ;

localVariableDeclarationStatement
    :    localVariableDeclaration ';'
    ;

localVariableDeclaration
    :   type declarationList
    ;

statement
    :   block
    |   'if' parExpression statement ('else' statement)?
    |	'for' Identifier 'in' '(' NumberLiteral ',' NumberLiteral ')' statement
    |	'for' 'each' '(' TypeSymbol 'in' Identifier ')' statement
    |   'while' parExpression statement
    |   'return' expression? ';'
    |   'break' Identifier? ';'
    |   'continue' Identifier? ';'
    |   ';'
    |   statementExpression ';'
    ;

// EXPRESSIONS

parExpression
    :   '(' expression ')'
    ;

expressionList
    :   expression (',' expression)*
    ;

statementExpression
    :   expression
    ;

expression
    :   primary
    |   expression '.' Identifier
    |   expression '.' 'this'
    |	Identifier expressionList?
    |   expression '[' expression ']'
    |   expression '(' expressionList? ')'
    |   '(' type ')' expression
    |   expression ('++' | '--')
    |   ('+'|'-'|'++'|'--') expression
    |   ('!') expression
    |   expression ('*'|'/'|'%') expression
    |   expression ('+'|'-') expression
    |   expression ('<=' | '>=' | '>' | '<') expression
    |   expression ('==' | '!=') expression
    |   expression 'AND' expression
    |   expression 'OR' expression
    |   expression '?' expression ':' expression
    |   <assoc=right> expression
        (   '='
        |   '+='
        |   '-='
        |   '*='
        |   '/='
        |   '%='
        )
        expression
    ;

primary
    :   '(' expression ')'
    |   'this'
    |   literal
    |   Identifier
    ;

/////////////////
/* LEXER RULES */
/////////////////

// KEYWORDS

PROGRAM		: 'program';
VAR			: 'var';
BREAK       : 'break';
CONTINUE	: 'continue';
EACH		: 'each';
ELSE		: 'else';
FOR			: 'for';
IF			: 'if';
IN			: 'in';
RETURN		: 'return';
THIS		: 'this';
VOID		: 'void';
WHILE		: 'while';

// LEVER SYMBOLS

AT			: '@';
HASHTAG		: '#';

// LITERALS

TypeSymbol
	: '@'
    | '#'
    ; 

NumberLiteral
	: '-'?[0-9]+('.'[0-9]+)?
    ;

// §3.10.3 Boolean Literals

BooleanLiteral
    :   'true'
    |   'false'
    | 	'yes'
    |	'no'
    ;

// §3.10.5 String Literals

StringLiteral
    :   '"' StringCharacters? '"'
    ;

fragment
StringCharacters
    :   StringCharacter+
    ;

fragment
StringCharacter
    :   ~["\\]
    |   EscapeSequence
    ;

// §3.10.6 Escape Sequences for Character and String Literals

fragment
EscapeSequence
    :   '\\' [btnfr"'\\]
    ;

// §3.10.7 The Null Literal

NullLiteral
    :   'null'
    ;

// §3.11 Separators

LPAREN          : '(';
RPAREN          : ')';
LBRACE          : '{';
RBRACE          : '}';
LBRACK          : '[';
RBRACK          : ']';
SEMI            : ';';
COMMA           : ',';
DOT             : '.';

// §3.12 Operators

ASSIGN          : '=';
GT              : '>';
LT              : '<';
BANG            : '!';
TILDE           : '~';
QUESTION        : '?';
COLON           : ':';
EQUAL           : '==';
LE              : '<=';
GE              : '>=';
NOTEQUAL        : '!=';
AND             : 'AND';
OR              : 'OR';
INC             : '++';
DEC             : '--';
ADD             : '+';
SUB             : '-';
MUL             : '*';
DIV             : '/';
MOD             : '%';
ADD_ASSIGN      : '+=';
SUB_ASSIGN      : '-=';
MUL_ASSIGN      : '*=';
DIV_ASSIGN      : '/=';
MOD_ASSIGN      : '%=';

// §3.8 Identifiers (must appear after all keywords in the grammar)

Identifier
    :   JavaLetter JavaLetterOrDigit*
    ;

fragment
JavaLetter
    :   [a-zA-Z$_] // these are the "java letters" below 0xFF
    |   // covers all characters above 0xFF which are not a surrogate
        ~[\u0000-\u00FF\uD800-\uDBFF]
        {Character.isJavaIdentifierStart(_input.LA(-1))}?
    |   // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
        [\uD800-\uDBFF] [\uDC00-\uDFFF]
        {Character.isJavaIdentifierStart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
    ;

fragment
JavaLetterOrDigit
    :   [a-zA-Z0-9$_] // "java letters or digits" below 0xFF
    |   // covers all characters above 0xFF which are not a surrogate
        ~[\u0000-\u00FF\uD800-\uDBFF]
        {Character.isJavaIdentifierPart(_input.LA(-1))}?
    |   // covers UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
        [\uD800-\uDBFF] [\uDC00-\uDFFF]
        {Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
    ;

//
// Whitespace and comments
//

WS  :  [ \t\r\n\u000C]+ -> skip
    ;

COMMENT
    :   '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip
    ;
