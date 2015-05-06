NOTE: This program has JUnit tests, but they are not needed to run MainClass or to build the project. If running this program standalone, you should either add JUnit in your Eclipse settings, or just run with errors.

Our first idea was to speed up findTree, because it seemed like something easy and fast to implement that could potentially make big gains for large searches. While it wasn't as easy as it first appeared, Vinushka implemented this in about two hours, and it sped up the Kruskal part of the algorithm by about 10 times:

Benchmarks:

All of these were created clicking "New Random (1000)" and then "Add Random (1000)", such as to be on fair footing.

1000 random points, before map:

Edge creation O(f(nE,nV)???):42.96666
MST (Kruskal) time O(f(nE,nV)???):712.4754

Note: this runtime seemed to vary wildly. Sometimes it would reach 3000ms, but usually it stayed in this range.

2000 random points, before map:

Edge creation O(f(nE,nV)???):659.08966
MST (Kruskal) time O(f(nE,nV)???):5832.027

Note: this, too, varied wildly. Sometimes it hit 18K+ ms for Kruskal. We presume this has to do with the tree ArrayLists sometimes being more sorted.

1000 random points, treemap:

Edge creation O(f(nE,nV)???):99.30662
MST (Kruskal) time O(f(nE,nV)???):500.20996

2000 random points, treemap:

Edge creation O(f(nE,nV)???):183.77957
MST (Kruskal) time O(f(nE,nV)???):1414.2942

1000 random points, hashmap:

Edge creation O(f(nE,nV)???):97.68053
MST (Kruskal) time O(f(nE,nV)???):233.02719

2000 random points, hashmap:

Edge creation O(f(nE,nV)???):184.648
MST (Kruskal) time O(f(nE,nV)???):934.45233


However, adding 10000 points still eluded us and crashed the program. This is when we turned to the Delaunay triangulation. Delaunay was a complete group effort - Calvin wrote the initial code, but it was broken, so Vinushka and Dalen hacked at it for hours, making small improvements but still not figuring anything out. Calvin set up a unit test (see DelaunayTriangulationTest.java), and this helped a lot in figuring out what exactly we were doing wrong with the triangulation - turns out that 1. we forgot to add edge weight back in after the code design change Vinushka suggested to do with respect to ConnectAllVertices, 2. the circumcircle code was broken (it was not checking counterclockwise), and 3. the equals() method for edges was broken. Interestingly enough, each group member had a hand in each of those three, so overall the project has been a real group achievement and had a great group dynamic, and lots of collaboration via GitHub.

At this point, Delaunay worked, and in fact was generating much fewer edges, but there was a new issue:

Benchmarks (no TreeMap since we already concluded it's slower):

Before Delaunay:

1000 random points, hashmap:

Edge creation O(f(nE,nV)???):97.68053
MST (Kruskal) time O(f(nE,nV)???):233.02719

2000 random points, hashmap:

Edge creation O(f(nE,nV)???):184.648
MST (Kruskal) time O(f(nE,nV)???):934.45233

After Delaunay:

1000 random points, hashmap:

Edge creation O(f(nE,nV)???):368.80518
MST (Kruskal) time O(f(nE,nV)???):32.61159

2000 random points, hashmap:

Edge creation O(f(nE,nV)???):2004.2739
MST (Kruskal) time O(f(nE,nV)???):75.169846

The MST time was crazy fast, but Delaunay was now taking longer than Kruskal did before! Moreover, we didn't actually save enough memory or time for 10,000 points to be enough for us, so it was clear that a more efficient and less naive implementation of Delaunay (with fewer linear searches...) was in order. So Vinushka and Dalen tried different approaches to reducing how long it took to create the triangulations, and Vinushka's made things slower for the smaller cases, but then 10,000 was possible without OOM errors:

Edge creation O(f(nE,nV)???):38292.625
MST (Kruskal) time O(f(nE,nV)???):949.01526

But then Vinushka learned how to hash the edges instead of using a TreeMap, and we got the following results:

1000 random points, hashmap:

Edge creation O(f(nE,nV)???):247.50345
MST (Kruskal) time O(f(nE,nV)???):26.925589

2000 random points, hashmap:

Edge creation O(f(nE,nV)???):1194.156
MST (Kruskal) time O(f(nE,nV)???):39.305183

This is definitely faster for the small cases, but for the 10,000 point case:

Edge creation O(f(nE,nV)???):40186.773
MST (Kruskal) time O(f(nE,nV)???):671.7198

Clearly there are other problems in the Delaunay triangulation. That said, we have not figured out a way to optimize it further as of now, so this is what we're turning in.
