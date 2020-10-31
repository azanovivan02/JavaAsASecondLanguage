# Stream processing graph

## What it is?

## Main concepts

### Record, Stream and Dataset

**Record** is a group of key-value pairs. It represents a single unit of data - info about one person, one document, etc. In our project it is implemented by [Record](src/main/java/io/github/javaasasecondlanguage/homework01/Record.java) class. 

![record](pics/record.png)

A sequence of records is called a **Stream**. Processing graph can accept stream of records as input, transform records one by one and then output them as a new stream.  

Streams can represent one of the two different concepts:
* Infinite sequence of records, which are generated in a real time. For example, clicks on items in the online store. You can never hope to see "all" such records, because they continue to arrive again and again. 
* Finite sequence of records. For example, contents of all documents currently present in a library. We will call such finite sequence a **Dataset**. 

In order to mark the end of a dataset, we can insert a so called **terminal record**.     

![Terminal record](pics/terminal_record.png)

### Operator

**Operators** represent basic actions, which transform records in some way. They can be chained together in order to implement a graph with a desired logic. All operators must implement [Operator](src/main/java/io/github/javaasasecondlanguage/homework01/ops/Operator.java) interface.

Operator accepts records one at a time. Each time it can output either zero, one or more records (by passing them to `collector` object). Operator can store any info about previously seen rows inside itself.      

Operators can be roughly divided into three types: mappers, reducers and joiners. 

#### Mapper

Mapper operator accepts a single record and outputs one or more new records. 

![Mapper](pics/mapper.png)

#### Reducer

Reducer operator accepts a sorted sequence of records, groups them by certain keys and outputs one or more new records for each group.

![Reducer](pics/reducer.png)

#### Joiner

Reducer operator accepts records from two sorted streams and joins them on certain keys.

![Joiner](pics/joiner.png)

### Node

Each operator is contained inside a [Node](src/main/java/io/github/javaasasecondlanguage/homework01/CompNode.java) object. Node handles all communication with the outside world: 
* It accepts records from the previous nodes.
* Gives them to operator
* Collects its outputs
* Passes thse outputs to the next nodes.

Each node has several input **gates**, which allows it to accept different streams of records (this is used in join operations, for example).  
   
![Node](pics/node.png)

### Graph

**Graph** object represents the actual processing graph - a group of interconnected nodes. It exposes input nodes and output nodes. 
 
![Graph](pics/graph.png)

## Our project

### Visualization

## Your task

Your task can be divided into two parts:
* Develop a "library" of operators.
* Use these operators to assemble processing graphs for solving actual practical problems.

### Operators library

You must implement the following operators (and pass tests for them):
* Mappers
* Reducers
* Joiners
    - InnerJoiner
    
### Practical problems

#### Problem 1
#### Problem 2

## Score

## Build and test
 