# antlr4multichannel

A small demonstration how to write antlr4 grammars that allow white space aware sections:

 - [MultiChannelBaseParser](https://github.com/almondtools/antlr4multichannel/blob/master/src/main/java/com/almondtools/antlr4multichannel/MultiChannelBaseParser.java) allows enabling/disabling channels from within the grammar
 - [MultiChannelTokenStream](https://github.com/almondtools/antlr4multichannel/blob/master/src/main/java/com/almondtools/antlr4multichannel/MultiChannelTokenStream.java) is a token stream that can enable/disable token channels to read from
 - [Example.g4](https://github.com/almondtools/antlr4multichannel/blob/master/src/main/antlr4/com/almondtools/antlr4multichannel/Example.g4) is a grammar for demonstrating the feature
 - [ExampleTest](https://github.com/almondtools/antlr4multichannel/blob/master/src/test/java/com/almondtools/antlr4multichannel/ExampleTest.java) is a unit test for demonstration