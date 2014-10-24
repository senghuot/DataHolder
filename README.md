#3a.
The tree has to form a straight chain in order to produce Omega(n) upon bad operations.
One way to get a straight chain tree, insert nodes with keys arrange in sequential order
ex: 1,2,...,n-1, n. Lookup(1) will take Omega(n). Lookup(1) reduces the height to about half 
its original height. However, continuation to Lookup(2)...Lookup(n) will put the tree back to
its original state before any Lookup() operations.

#3b.
After N insertion follow by M-N operations, a straight line produce; y = 0.7535x + 3.2812.
The constant slope is 0.7535.


