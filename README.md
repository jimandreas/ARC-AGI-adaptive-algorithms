This repo downloads the ARC-AGI corpus located at:

https://github.com/fchollet/ARC-AGI

into an SQLite database.  Note that training data ONLY is downloaded.

Various examinations of the data are now underway.  Stay tuned.

Early result: (10 Nov 2024)

In the training database, the number of Tasks where the size of the 
matrix is "smaller" versus "bigger" versus the "same size" from "input" to "output" 
is:

smaller size 83 bigger 25 same size 263 total 371

as there are 400 training Tasks, this result indicates that 29 fall out.  As expected the "same size"
category dominates.  Have to look at the characteristics of the 29.

![LOGO HERE](/docs/images/logo.png)

