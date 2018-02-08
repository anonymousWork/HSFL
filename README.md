# HSFL


This is the page for HSFL (Historical Spectrum based Fault Localization).
You can replicate the experimental results reported int the submitted paper.


It contains the following file and folders:


## ./Survey.pdf
This file contains the complete results of our empirical survey conducted at
Microsoft. 
We only reported partial results in the submitted paper due to page limit.


## ./HSFL
This folder contains the implementation of HSFL, and the scripts to run all the experiments.
Please go to this folder for further information.


## ./Chart
This folder contains the data for all the bugs in subject Chart.

#### ./Chart/Chart\_1
This subfolder contains the data for bug Chart 1

*./Chart/Chart\_1/HSFL*: it contains the results of HSFL and the component Histrum
for different techniques.
*./Chart/Chart\_1/SBFL*: it contains the results of conventional SBFL for
different techniques.
*./Chart/Chart\_1/a7cc1cf88cb014e937a55af53ea1f96407688a5e*: this is the inducing
change of this bug, and it contains the covered lines by the failing tests on
the bug-inducing version.
*./Chart/Chart\_1/fileindexes*: this folder contains file indexes for each
suspicious buggy source file. Specifically, it records all the versions starting
from the bug-inducing version to the target version for each suspicious buggy
source file.




## ./Closure:./Lang:./Math:./Time
The structures of these folders are the same as the structures of Chart as described above.
