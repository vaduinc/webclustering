
WEB INTERFACE TO USE JSA clustering.jar

Notes:
	Allows to upload a plain data file with the data and select the type of algorithm to process
	(Jarvis or Leader), the type of the input data in the file to be uploaded (fp or mtx)
	and the particular input parameters needed to run the algorithm; minimum distance (leader),
	number of neighbors and common neighbors (jarvis)
	
 To compile:
	
	Unzip the file then just compile with regular javac command.
	
	
 To run:
	
 	java -jar clustering.jar [-mtxfile | -fpfile] myfile.txt -outpath [./output | @display ] -algorithm [leader | jarvis] -minDistance 0.5 -numNeighbors 2 -commonNeighbors 1
 
 		-algorithm must be one of the following values; leader or jarvis
 		-minDistance must be used with Leader algorithm
 		-numNeighbors AND -commonNeighbors must be used with Jarvis algorithm, and numNeighbors >= commonNeighbors
 
 	When @display is used in the -outpath input parameter the results are printout in the console instead of saving them into a file.
 
 
 Examples:
 
 	java -jar clustering.jar -mtxfile ./HW1CodeData/testdata/iris.txt -outpath @display -algorithm leader -minDistance 0.5 
  	
  	java -jar clustering.jar -mtxfile ./HW1CodeData/testdata/iris.txt -outpath @display -algorithm jarvis -numNeighbors 5 -commonNeighbors 1
    
    java -jar clustering.jar -fpfile ./HW1CodeData/testdata/bbb2_daylight.fp.txt -outpath @display -algorithm leader -minDistance 200 
  	
  	java -jar clustering.jar -fpfile ./HW1CodeData/testdata/bbb2_daylight.fp.txt -outpath @display -algorithm jarvis -numNeighbors 10 -commonNeighbors 3