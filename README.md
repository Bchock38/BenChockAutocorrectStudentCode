# Autocorrect
####  A Java program created by Benjamin Chock for Adventures in Algorithms at Menlo School in Atherton, CA. Based off starter code given by Zach Blick 
## Description 
An autocorrect tool that runs continuously in the IntelliJ terminal. Once the program begins, it waits for the user to type a word into the console. If the word matches a dictionary word, nothing happens. But if the word is misspelled, the program will return the closest matching words. If no matching candidates can be found, the program prints "No matches found."
The program utilizes Levenshtein Distance to find similarity between words and weigh how similiar a word is to another. 
The program runs through a dictionary of words and skips any word too big or too small until it finds the words that match within a threshold of 2 in terms of Levenshtein Distance