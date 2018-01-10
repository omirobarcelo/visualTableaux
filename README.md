VisualTableaux
===============
This tool allows the user to visually expand a tableau graph to test the satisfiability of a given concept and TBox.

Motivation
==========
Most of the work in the description logics field consists in developing new ways or standards to express these logics, or in optimizing the work of the reasoners. But there is none, or very limited, work done in the field of education. Nowadays, to teach how the tableau algorithm functions, the professor draws the complete tableau procedure, with all its ramifications, on the board, or develops time-consuming graphics to show the different cases. The objective of this project is to develop a visual application that allows the user to apply the tableau algorithm _step by step_ to a given ontology. This program will cover an existing gap and will aid the teaching of the tableau algorithm. Both the student and the professor may interact with it, and will avoid the repeated draw-and- erase procedures that usually accompany the presentation of this algorithm.

Tech/framework used
===================
This project uses [OWL API](https://github.com/owlcs/owlapi/wiki/Documentation) to read the input concept and TBox files.

Features
========
Supports TBox reasoning of ALC Description Logic.

How to use?
===========
Open and execute the project with an IDE or execute the JAR file located inside the source directory.
The fonts directory should be at the source level or at the same location as the JAR file to properly use them (macOS uses the system fonts).
Upon launching the application, select a concept file and a TBox file. You can later select others with File>Open.
Select a node by clicking on it. The applicable operations will appear inside the right panel.
You can restart the tableau using File>Reset.
You can save and load specific states using User state.

License
=======
Apache license
