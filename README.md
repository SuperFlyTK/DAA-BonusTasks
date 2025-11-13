# MST Edge Replacement - DAA Bonus Task

## Project Description
This Java implementation demonstrates efficient edge replacement in Minimum Spanning Trees (MSTs) using Kruskal's algorithm. The program builds an MST from a given graph, removes an edge, identifies disconnected components, and finds the optimal replacement edge to restore the MST property.

## Implementation Requirements Met

### 1. MST Construction
- **Algorithm**: Kruskal's algorithm with Union-Find optimization
- **Efficiency**: O(E log E) time complexity with path compression and union by rank
- **Output**: Displays all MST edges with their weights and total MST weight

### 2. Edge Removal and Replacement
- **Edge Removal**: Selects and removes one edge from the MST
- **Component Analysis**: Uses DFS to identify connected components after removal
- **Replacement Finding**: Efficiently locates minimum-weight edge to reconnect components
- **MST Restoration**: Adds replacement edge and displays updated MST

### 3. Program Output
The program clearly shows:
- Original graph structure
- Initial MST edges and total weight
- Removed edge details
- Resulting components after removal
- Replacement edge selected
- Updated MST with new total weight

## Project Structure

```
DAA-BonusTask/
├── src/
│   ├── main/java/
│   │   ├── graph/
│   │   │   ├── Edge.java              # Weighted edge representation
│   │   │   ├── Graph.java             # Graph data structure
│   │   │   └── UnionFind.java         # Union-Find for cycle detection
│   │   ├── mst/
│   │   │   ├── KruskalMST.java        # MST construction algorithm
│   │   │   └── MSTReplacer.java       # Edge replacement logic
│   │   ├── util/
│   │   │   └── ComponentFinder.java   # Connected components detection
│   │   ├── Main.java                  # Demonstration program
│   │   └── CSVGenerator.java          # Performance analysis
│   └── test/java/                     # Unit test suite
├── results/
│   └── performance_data.csv           # Generated performance reports
├── pom.xml                            # Maven configuration
└── README.md
```

## How to Run the Program

### Prerequisites
- Java JDK 11 or higher
- Maven 3.6 or higher

### Step-by-Step Instructions

1. **Clone and setup the project:**
   ```bash
   git clone <repository-url>
   cd DAA-BonusTask
   ```

2. **Compile the project:**
   ```bash
   mvn compile
   ```

3. **Run the main demonstration:**
   ```bash
   mvn exec:java -Dexec.mainClass="Main"
   ```

4. **Generate performance reports:**
   ```bash
   mvn exec:java -Dexec.mainClass="CSVGenerator"
   ```

5. **Run unit tests:**
   ```bash
   mvn test
   ```

### Alternative Execution Methods

**Using direct Java commands:**
```bash
# Compile all source files
javac -d target/classes src/main/java/**/*.java src/main/java/*.java

# Run main program
java -cp target/classes Main

# Run CSV generator
java -cp target/classes CSVGenerator
```

## Algorithm Demonstration

### MST Construction Process
The program demonstrates Kruskal's algorithm:
1. Sorts all edges by weight
2. Uses Union-Find to detect cycles
3. Builds MST by adding edges in sorted order
4. Validates the resulting spanning tree

### Edge Replacement Process
When an edge is removed:
1. The MST splits into connected components
2. DFS identifies all components
3. The algorithm scans original graph edges
4. Selects minimum-weight edge connecting different components
5. Adds replacement edge to restore MST

### Efficiency Demonstration
- **MST Construction**: O(E log E) with efficient sorting and Union-Find
- **Component Detection**: O(V + E) using DFS
- **Replacement Search**: O(E) by examining candidate edges
- **Overall Approach**: Avoids complete MST reconstruction

## Performance Analysis

The project includes comprehensive performance testing across multiple graph configurations:

### Test Results
```
Test Case,Vertices,Edges,Original MST Weight,Removed Edge,Replacement Edge,New MST Weight,Weight Change,Components After Removal,Execution Time (ms)
Basic Graph,4,5,7,1-2 (weight: 2),0-2 (weight: 3),8,1,2,24
Sample Graph,6,9,9,1-2 (weight: 1),0-1 (weight: 4),12,3,2,2
Large Graph,8,11,17,3-4 (weight: 2),3-5 (weight: 5),20,3,2,1
Bridge Removal,5,5,10,0-1 (weight: 1),0-4 (weight: 8),17,7,2,1
Complex Graph,7,9,13,5-6 (weight: 2),4-6 (weight: 4),15,2,2,1
```

### Performance Insights
- **Consistent Execution**: All test cases complete efficiently (1-24ms)
- **Optimal Replacements**: Weight increases minimized through careful edge selection
- **Component Accuracy**: Correctly identifies 2 components after each edge removal
- **Scalability**: Maintains performance across different graph sizes

## Code Organization

### Package Structure
- **graph**: Core data structures (Edge, Graph, UnionFind)
- **mst**: Algorithm implementations (KruskalMST, MSTReplacer)
- **util**: Utility classes (ComponentFinder)
- **test**: Comprehensive unit test suite

### Key Classes and Responsibilities

**Edge.java**
- Represents weighted undirected edges
- Implements Comparable for sorting
- Handles edge equality and hashing

**Graph.java**
- Manages graph structure with adjacency lists
- Provides graph creation and manipulation methods
- Includes sample graph for demonstration

**UnionFind.java**
- Implements disjoint set union with optimizations
- Provides cycle detection for Kruskal's algorithm
- Uses path compression and union by rank

**KruskalMST.java**
- Constructs MST using Kruskal's algorithm
- Validates MST properties
- Displays MST edges and total weight

**MSTReplacer.java**
- Handles edge removal and replacement
- Identifies connected components after removal
- Finds optimal replacement edges

**ComponentFinder.java**
- Detects connected components using DFS
- Provides component visualization
- Checks graph connectivity

## Testing

### Test Coverage
The project includes JUnit tests for:
- Graph construction and edge operations
- Union-Find connectivity and union operations
- MST construction correctness
- Component detection accuracy
- Edge replacement scenarios

### Running Tests
```bash
# Run all tests
mvn test

# Run specific test classes
mvn test -Dtest=TestKruskalMST
mvn test -Dtest=TestMSTReplacer
mvn test -Dtest=TestGraph
```

## Demonstration Output Example

```
==========================================
    MST Edge Replacement Demonstration
==========================================

Step 1: Creating and displaying the original graph...
=== ORIGINAL GRAPH ===
Vertices: 6
Edges: 9

All edges in the graph:
1. 0 -- 1 (weight: 4)
2. 0 -- 2 (weight: 3)
3. 1 -- 2 (weight: 1)
4. 1 -- 3 (weight: 2)
5. 2 -- 3 (weight: 4)
6. 2 -- 4 (weight: 5)
7. 3 -- 4 (weight: 1)
8. 3 -- 5 (weight: 6)
9. 4 -- 5 (weight: 2)
Total graph weight: 28

Step 2: Building Minimum Spanning Tree...
=== MINIMUM SPANNING TREE ===
1. 1 -- 2 (weight: 1)
2. 3 -- 4 (weight: 1)
3. 1 -- 3 (weight: 2)
4. 4 -- 5 (weight: 2)
5. 0 -- 2 (weight: 3)
Total Weight: 9
Edges: 5

Selected edge for removal: 1 -- 3 (weight: 2)

Step 3: Removing edge and finding replacement...
=== EDGE REMOVAL ===
Removed: 1 -- 3 (weight: 2)

=== CONNECTED COMPONENTS ===
Graph has 2 components
Component 1: [0, 1, 2]
Component 2: [3, 4, 5]

Replacement: 2 -- 3 (weight: 4)

Step 4: Final Result...
=== UPDATED MST ===
1. 1 -- 2 (weight: 1)
2. 3 -- 4 (weight: 1)
3. 4 -- 5 (weight: 2)
4. 0 -- 2 (weight: 3)
5. 2 -- 3 (weight: 4)
Total Weight: 11

==========================================
               SUMMARY
==========================================
Original MST Weight: 9
Removed Edge: 1 -- 3 (weight: 2)
Replacement Edge: 2 -- 3 (weight: 4)
New MST Weight: 11
Weight Change: 2
==========================================
```

## Conclusion

This project successfully demonstrates:
1. **Efficient MST Implementation**: Kruskal's algorithm with Union-Find optimizations
2. **Dynamic Edge Handling**: Effective removal and replacement without full reconstruction
3. **Component Analysis**: Accurate identification of disconnected components
4. **Optimal Reconnection**: Minimum-weight edge selection for MST restoration
5. **Performance Measurement**: Comprehensive timing and metric collection

The implementation clearly shows which edges are added to reconnect the tree, fulfilling all project requirements while maintaining algorithmic efficiency and code quality.

## Academic Context
This project fulfills the requirements for the Design and Analysis of Algorithms course bonus task, demonstrating proficiency in graph algorithms, data structures, and efficient algorithm design.
