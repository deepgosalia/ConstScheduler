JFLAGS = -g
JC = javac
JVM = java
.SUFFIXES: .java .class
.java.class: 
	$(JC) $(JFLAGS) $*.java

CLASSES = / risingCity.java / RedBlack.java / RedBlackNode.java / Heap.java / HeapNode.java / Color.java

default: classes

classes: $(CLASSES:.java=.class)

clean: 
	$(RM) *.class