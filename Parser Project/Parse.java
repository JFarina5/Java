package parserPackage;

/* Name: John Farina
 * Date: 29 October 2017
 * Class: CSCI 4200-DB, Fall 2017
 * 
 * The following is a recursive-descent parser program, which is a 
 * continuation of the chapter four, lexical analyzer, program.
 */

import java.io.*;

public class Parse
{
	static final int MAX_LEXEME_LENGTH = 100;
	static final int MAX_TOKEN_LENGTH = 100;
	static int charClass;
	static char[] lexeme = new char[MAX_LEXEME_LENGTH];
	static char nextChar;
	static int lexLen;
	static int token;
	static String thisLine;
	static int nextToken;
	static int read;
	static char endChar;
	static char[] newChar = new char[100];
	static String nextLine;
	static FileReader file;
	static FileReader file2;
	static BufferedReader reader;
	static BufferedReader r2;
	
	/*Character classes*/
	static final int LETTER = 0;
	static final int DIGIT = 1;
	static final int UNKNOWN = 99;
	
	/*Token codes*/
	static String[] token_dict = new String[MAX_TOKEN_LENGTH];
	static final int INT_LIT = 10;
	static final int IDENT = 11;
	static final int ASSIGN_OP = 20;
	static final int ADD_OP = 21;
	static final int SUB_OP = 22;
	static final int MULT_OP = 23;
	static final int DIV_OP = 24;
	static final int LEFT_PAREN = 25;
	static final int RIGHT_PAREN = 26;
	static final int END_OF_FILE = 98;
	
/********************************************************************/
	
	/*Beginning of the main method, which will run the provided methods.*/
	
	public static void main(String[] args) throws FileNotFoundException {

		token_dict[INT_LIT] = "INT_LIT\t";
		token_dict[IDENT] = "IDENT\t";
		token_dict[ASSIGN_OP] = "ASSIGN_OP";
		token_dict[ADD_OP] = "ADD_OP\t";
		token_dict[SUB_OP] = "SUB_OP\t";
		token_dict[MULT_OP] = "MULT_OP\t";
		token_dict[DIV_OP] = "DIV_OP\t";
		token_dict[LEFT_PAREN] = "LEFT_PAREN";
		token_dict[RIGHT_PAREN] = "RIGHT_PAREN";
		token_dict[END_OF_FILE] = "END_OF_FILE";
		
		file = new FileReader("src\\parserPackage\\statements.txt");
		file2 = new FileReader("src\\parserPackage\\statements.txt");
		reader = new BufferedReader(file);
		r2 = new BufferedReader(file2);
		
		if (file == null){
			System.out.println("ERROR - cannot open statements \n");
		}else{
			try {
				while(reader.ready()){
					read = 0;
					nextLine = r2.readLine();
					String notLine = nextLine;
					for(int i = 0; i<notLine.length() ; i++) 
					{
						newChar[i] = notLine.charAt(i);
						if (i== notLine.length()-1)
							endChar = notLine.charAt(i);
					}
					System.out.println("*********************************************************************");
					System.out.println("Parsing the statement: " + nextLine + "\n");
					getChar();
					do
					{
						lex();
						read++;
					}		
					while(nextToken != END_OF_FILE);
					System.out.println("");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Next token is: END_OF_FILE     Next lexeme is EOF");
	}
	
/********************************************************************/
	
	/* The following looks up the operators and parentheses with in the
	 provided "statements.txt file and returns their token value*/
	 
	static int lookup(char ch){
		
		switch (ch) {
		case '(':
			addChar();
			nextToken = LEFT_PAREN;
			break;
			
		case ')':
			addChar();
			nextToken = RIGHT_PAREN;
			break;
			
		case '+':
			addChar();
			nextToken = ADD_OP;
			break;
			
		case '-':
			addChar();
			nextToken = SUB_OP;
			break;
			
		case '*':
			addChar();
			nextToken = MULT_OP;
			break;
			
		case '/':
			addChar();
			nextToken = DIV_OP;
			break;
			
		case '=':
			addChar();
			System.out.println("Enter <assign>");
			nextToken = ASSIGN_OP;
			break;
			
		default:
			lexeme[0] = 'E';
			lexeme[1] = 'O';
			lexeme[2] = 'F';
			lexeme[3] = 0;
			lexLen = 4;
			nextToken = END_OF_FILE;
			break;
		}
		return nextToken;
	}
	
/********************************************************************/
	
	/*adds next char to lexeme*/
	static void addChar(){
		
		if(lexLen <= (MAX_LEXEME_LENGTH-2)){
			lexeme[lexLen++] = nextChar;
			lexeme[lexLen] = 0;
		}else{
			System.out.println("ERROR - lexeme is too long \n");
		}
	}
	
/********************************************************************/
	
	/*The following gets the next char of an input
	and determines its character class*/
	
	static void getChar(){
		
		char c = 0;
		try {
			c = (char)reader.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		nextChar = c;
		if((int)nextChar != 0){
			if(isalpha(nextChar)){
				charClass = LETTER;
			}else if(isdigit(nextChar)){
				charClass = DIGIT;
			}else{ charClass = UNKNOWN;}
		}else{
			charClass = END_OF_FILE;
		}
	}
	
/********************************************************************/
	
	/* The following calls the getChar function 
	 until it returns a non-whitespace character */
	
	static void getNonBlank(){
		
		while(isspace(nextChar)){
			getChar();
		}
	}
	
/********************************************************************/
	
	/* The following is a lexical analyzer method
	  for arithmetic expressions */
	
	static int lex(){
		
		lexLen = 0;
		getNonBlank();
		switch (charClass){
		
		/* Determines parse identifiers */
		case LETTER:
			addChar();
			getChar();
			while(charClass == LETTER || charClass == DIGIT){
				addChar();
				getChar();
			}
			nextToken = IDENT;
			break;
			
		/* Determines parse integer literals */
		case DIGIT:
			addChar();
			getChar();
			while(charClass == DIGIT){
				addChar();
				getChar();
			}
			nextToken = INT_LIT;
			break;
			
		/* Determines parentheses and operators */
		case UNKNOWN:
			lookup(nextChar);
			getChar();
			break;
			
		/* Determines the end of the file*/
		case END_OF_FILE:
			nextToken = END_OF_FILE;;
			lexeme[0] = 'E';
			lexeme[1] = 'O';
			lexeme[2] = 'F';
			lexeme[3] = 0;
			lexLen = 4;
			break;
		}
		
		/* The following is the output calls for parser,
		 * which in turn, defines when to insert either 
		 * <assign> <expr> <term> and/or <factor> */
		
		String s = new String(lexeme);
		s = s.substring(0,lexLen);
		char lexLast = s.charAt(s.length()-1);
		if(nextToken != 98)
		System.out.printf("Next token is: %s\t\t Next lexeme is %s\n", token_dict[nextToken], s);
		if(token_dict[nextToken].equals("LEFT_PAREN"))
		{
			System.out.println("Enter <expr>");
			System.out.println("Enter <term>");
			System.out.println("Enter <factor>");
		}
		
		if(nextToken == 11 && read !=0 && lexLast != endChar )
		{
			System.out.println("Enter <expr>");
			System.out.println("Enter <term>");
			System.out.println("Enter <factor>");
		}
		
		if(nextToken == 21 && lexLast != endChar)
		{
			System.out.println("Exit <factor>");
			System.out.println("Exit <term>");
		}
		
		if(nextToken == 10 && lexLast != endChar)
		{
			System.out.println("Enter <term>");
			System.out.println("Enter <factor>");
		}
		
		if(nextToken == 26 && lexLast != endChar)
		{
			System.out.println("Exit <factor>");
			System.out.println("Exit <term>");
			System.out.println("Exit <expr>");
		}
		
		if(nextToken == 24 && lexLast != endChar)
		{
			System.out.println("Exit <factor>");
		}
		if(nextToken == 98)
		{
			System.out.println("Enter <factor>");
			System.out.println("Exit <factor>");
			System.out.println("Exit <term>");
			System.out.println("Exit <expr>");
			System.out.println("Exit <assign>");
		}
		return nextToken;
	}
	
/********************************************************************/
	
	/* The following returns true if the char is a letter */
	
	static boolean isalpha(char c){
		
		int ascii = (int) c;
		if((ascii > 64 && ascii < 91) || (ascii > 96 && ascii < 123)){
			return true;
		}else {return false;}
	}
	
/********************************************************************/
	
	/* The following returns true if the char is a digit */
	
	static boolean isdigit(char c){
		
		int ascii = (int) c;
		if(ascii > 47 && ascii < 58){
			return true;
		}else {return false;}
	}
	
/********************************************************************/
	
	/* The following returns true if the char is a space*/
	
	static boolean isspace(char c){
		
		int ascii = (int) c;
		if(ascii == 32){
			return true;
		}else {return false;}
	}
}
	
/********************************************************************/
