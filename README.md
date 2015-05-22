# Lever
Social Querying Language

## Overview

LEVER is a programming language that enables the easy retrieval, processing and displaying of information retrieved from social networks. The social network aggregation of information from multiple sources allows for:

- Easy formulation of queries that can work universally along social networks.
- Analyzing social media trends and patterns. 
- Cross-Data analysis and other various information insights.

Therefore Lever, being a simple, easy to learn programming language that can be used by business analysts and marketers, can have enormous value for organizations and individuals.

## Installation

To compile the Lever translator navigate into the translator directory and enter the following commands:

	make antlr
	make

## Language Tutorial

To demonstrate the use, functionality and power of Lever two sample programs are presented below. Discussion and explanation for each program is also presented.

### Hello, World!

For your first Lever program, create a text file named hello.lever and add the following to the file:

	program {
		output “hello world!\n”;
	}

That’s it! When compiled and run, this will print the words “hello world” to standard output. To compile the program, open up your UNIX shell, navigate to your Lever file, and enter the command:

	./leverc hello.lever

This should compile your program and create an executable file named hello. To run the program, simply type the command:

	./lever hello

which should print to standard output:

	hello world!

All lever source files must end with “.lever” to be recognized by the compiler. Lever programs consist mainly of statements, variables, and functions that specify what commands should be executed. The first statements that are run must be within the block (within the curly braces):

	program {

	}

In our hello world program, the only statement that we have is:

	output “hello world\n”;

This calls the built in Lever command output with the argument “hello world\n”, which is a string of characters surrounded by double quotes. output will print its arguments to standard output, which in our case is “hello world\n”. The \n represents a newline character, which causes the output to advance to the next line (like hitting the return key in any text editor). Numbers may also be printed, but text should always be surrounded by double quotes. We talk more about input and output later in the tutorial.

Lastly, statements must end with a semicolon “;”, which functions like a period in a sentence.

## Project Details

This programming language was designed and developed as a project for the class "Programming Languages and Translators - 4115" taught by Prof. Alfred Aho in Spring 2015 at Columbia University.

The project contributors along with their respective roles are listed below:

- [Roy Hermann](https://www.linkedin.com/in/royhermann): Project Manager
- [Chaiwen Chou](https://github.com/chaiwen): Language Guru
- [Mahd Tauseef](https://github.com/mahdt): Systems Architect
- [Zissis Konstas](https://github.com/zkonstas): Systems Engineer
- [Eden Dolev](https://github.com/edolev89): Verification and Validation

