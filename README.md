##Document Search

The goal of this exercise is to create a working program to search a set of documents for the given search term or phrase (single token), and return results in order of relevance.

####_Run a performance test that does 2M searches with random search terms, and measures execution time._

Indexed Search is ~60% faster than Regular Expression search, which is in turn ~50% faster than String Match search.

####_Results of performance test with 2000000 iterations_

- Time taken to run String Match search is : 318788 ms
- Time taken to run Regular Expression search is : 172345 ms
- Time taken to run Indexed search is : 104406 ms


####_Which approach is fastest? Why?_

Index search is the fastest as we preprocess the data into a map and reuse it for repeated searches. 
Also each unique word is compared with search term only once as duplicates count is stored in the preprocessed map and it is used for counting.


####_Provide some thoughts on what you would do on the software or hardware side to make this program scale to handle massive content and/or very large request volume (5000 requests/second or more)._
- If running in a cloud environment, a serverless event-driven compute service like AWS Lamda can be used to create a function, which can be scaled automatically with respect to the load at the moment.
- If running locally, having Nvme drives for file storage will increase performance, implementing multi threaded version of search can utilise maximum potential from multi-core processors.
Also load balancers can be used with multiple servers along with the above mentioned stratergies to improve performance.
  

###Setup
- Download or Clone the project from Github. (then unzip if downloaded)

- Open a new Maven project in an IDE like Intellij or Eclipse.

- In Intellij - File -> New -> Project From Existing Sources -> choose the pom.xml file in the downloaded directory.

- Then open DocumentSearcher.java file in src/main/java/com/shoaib/documentsearch package. 

- Then right click in the file and select "Run".

- To run the performance test, open DocumentSearchPerformanceTest.java file in the same package and right click in the file and select "Run".

- the constant TEST_ITERATIONS can be updated before running to change the number of iterations.