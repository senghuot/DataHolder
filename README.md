#3a.
The tree has to form a straight chain in order to produce Omega(n) upon one bad operation.
A straight chain tree form by multiple insertions of nodes with keys arrange in sequential order.
ex: 1,2,...,n-1, n. lookup(1) will take Omega(n) time. Moreover, lookup(1) reduces the height to 
about half its original height. Interestingly, continuation of Lookup(2)...Lookup(n) will 
put the tree back to its original state.

#3b.
I inserted 2^n nodes where n ranges from 0 to 20. Each successful iteration of the 2^n of ascending keys
from 0 to 2^n will produce a straight chain tree then follow by 2^n lookups on random key in the tree. 
A straight line produce; y = 0.7535x + 3.2812. The constant C is about 0.7535.jhaja
