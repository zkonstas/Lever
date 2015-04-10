///////////
/* LEVER */
///////////

grammar LeverGrammar;

//////////////////
/* PARSER RULES */
//////////////////

lever
	: PROGRAM compoundStatement
	;

compoundStatement
	: '{' blockStatement* '}'
	;

blockStatement
	: memberDeclaration
	| statement
	;

statement
	: compoundStatement
	| statementExpression ';'
	;

memberDeclaration
	: fieldDeclaration
	;

fieldDeclaration
	: VAR declarationList ';'
	;

declarationList
	: declaration (declaration)*
	;

declaration
	: variableId ('=' variableInit)?
	;

variableId
	: Identifier ('[' ']')*
	;

variableInit
	: arrayInit
	| expression
	;

arrayInit
	: '{' (variableInit (',' variableInit)* (',')? )? '}'
	;

identifierList
	: Identifier
	| identifierList ',' Identifier
	;

statementExpression
	: expression
	;

/** dot notation? | expression '.' Identifier */
expression
	: primary
	| primary functionParams
	;

functionParams
	: expression (',' expression)*
	;

primaryList
	: primary (',' primary)*
	;

primary
	: '(' expression ')'
	| 'this'
	| literal
	| Identifier
	;
/*
postfixExpression
	: primaryExpression
	| postfixExpression '(' argumentExpressionList? ')'
	;

argumentExpressionList
	: assignmentExpression
	| argumentExpressionList ',' assignmentExpression
	;
*/

literal
	: StringLiteral
	;

StringLiteral
	: '"' StringCharacters? '"'
	;

fragment
StringCharacters
	: StringCharacter+
	;

fragment
StringCharacter
	:	~["\\]
	|	EscapeSequence
	;

fragment
EscapeSequence
	:	'\\' [btnfr"'\\]
	;

/////////////////
/* LEXER RULES */
/////////////////

// KEYWORDS

PROGRAM	: 'program' ;
VAR		: 'var' ;

Identifier
	: 	JavaLetter LetterOrDigit*
	;

fragment
JavaLetter
	:	[a-zA-Z$_]
	;

fragment
LetterOrDigit
	:	[a-zA-Z0-9$_]
	;


//INT		:	[0-9]+ ;

WS	:	[ \t\r\n]+ -> skip
	; //remove whitespace

BLOCK_COMMENT
	: '/*' .*? '*/' -> skip
	;

LINE_COMMENT
	: '//' ~[\r\n]* -> skip
	;
