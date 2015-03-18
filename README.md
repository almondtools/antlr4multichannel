# antlr4multichannel

A small demonstration how to write antlr4 grammars that allow white space aware sections:

 - MultiChannelBaseParser allows enabling/disabling channels from within the grammar
 - MultiChannelTokenStream is a token stream that can enable/disable token channels to read from
 - Example.g4 is a grammar for demonstrating the feature
 - ExampleTest is a unit test for demonstration