grammar Example;

options {
  superClass=MultiChannelBaseParser;
}

@lexer::members {
    public static final int WHITESPACE = 1;
}

template
  :  '{' {enable(ExampleLexer.WHITESPACE);} templateBody {disable(ExampleLexer.WHITESPACE);} '}'
  ;

templateBody
  : templateChunk*
  ;

templateChunk
  : code # codeChunk // dsl code, ignore whitespace
  | text # textChunk // any text
  ;
 

code
  :  {disable(ExampleLexer.WHITESPACE);}'@' function{enable(ExampleLexer.WHITESPACE);} 
  ;

function
  : Identifier '(' argument ')'
  ;

argument
  : function
  | template
  ;

text
  : texttoken+
  ;
  
texttoken
  : Identifier
  | Whitespace+
  | ~('}' |'@')+
  ;

Identifier
  : LETTER (LETTER|DIGIT)*
  ;

Whitespace
  : [ \t\n\r] -> channel(WHITESPACE)
  ;

fragment LETTER
    : [a-zA-Z]
    ;

fragment DIGIT
    : [0-9]
    ;