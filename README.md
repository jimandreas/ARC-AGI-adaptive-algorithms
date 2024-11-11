# ARC AGI database analysis and graphical representation experiments

## ARC AGI repo location

https://github.com/fchollet/ARC-AGI

## what this section does:

It parses the JSON tasks into an SQLite database.  Note that training data ONLY is downloaded.

Various examinations of the data are now underway.  Stay tuned.

Early result: (10 Nov 2024)

In the training database, the number of Tasks where the size of the 
matrix is "smaller" versus "bigger" versus the "same size" from "input" to "output" 
is:

A few statistical details on the ARC-AGI corpus.

There are 400 training Tasks. And multiple examples for each.  
Of these the cell count goes like this: smaller size 102 bigger 
36 same size 263 total 401.  Somehow 1 task had perhaps a example 
that fit in more than one category.

Further digging was successful.  The Task "ff28f65a" has one 
example where the input size was the same size as the output size, 
while *all* the other examples had a reduced cell count.

All other Tasks appear to be consistent - they either have examples 
where they have the same cell count input to output (the majority at 
263 / 400 = 66%) or they get smaller (102 / 400 = 25.5%) or bigger (36 / 400 = 9%).

## Part 2

A graphical representation of the training examples is now implemented.  This work is continuing.


![LOGO HERE](/docs/images/logo.png)

[^ARCAGI] Abstraction and Reasoning Corpus for Artificial General Intelligence (ARC-AGI)