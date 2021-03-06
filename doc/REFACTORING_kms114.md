# cellsociety 
Name: Kayla Schulz
NetID: kms114

*Relevant Design Problems: I was previously throwing a new exception that I had created, but it came out of left field. The exception was for opening the file and sending the file into the XML parser. Instead of having a class dedicated to that exception, I created it on the fly when the exception occurred. The exception message was also hard-coded in, instead of being in a resource file.

*Why This is Better: This new design is better because it allows for easier access to the exception. Instead of potentially throwing weird/bad exceptions, there is now a centralized class that takes care of the exception and would allow for other methods/classes to access the same exception with consistency. I modeled the exception after the one Duvall had shown us in class last week (the BrowserException). As I continue to work on Cell Society, there will be more calls to this exception, making it a central location for handling file and XML errors.

*Link to Design Changes: https://git.cs.duke.edu/CompSci308_2016Fall/cellsociety_team05/commit/67eac4b9b806367b3f8be39ced26ec97f8447b18 