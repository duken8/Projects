#include "listUtils.h"

 
Node * buildNode(FILE * fin, void *(*buildData)(FILE * in) )
{
	Node * nn = (Node *) calloc(1, sizeof(Node));
	void * data = buildData(fin);
	nn->data = data;
	return nn;
}

void sort(LinkedList * theList, int (*compare)(const void *, const void *))
{
     Node * start, *search;
     for(start = theList->head; start->next != NULL; start = start->next)
     {
     	Node * min = start;
        for(search = start->next; search != NULL; search = search->next)
        {
            if((compare(search->data, min->data)) <= 0)
            {
 		min = search;
 	    }
        }
    	void *temp = min->data;
    	min->data = start->data;
    	start->data = temp;
    }//end sort operation
}
 
void buildList(LinkedList * myList, int total, FILE * fin, void * (*buildData)(FILE * in))
{
	int x;
	for(x = 0; x < total -1; x++)
	{
		void *z = buildData(fin);
		Node * nn = (Node *) calloc(1, sizeof(Node));
		nn->data = z;
		addFirst(myList, nn);
	}	
}//end buildList

Node * buildNode_Type(void * passedIn)
{
	Node *nn = (Node *) calloc(1, sizeof(Node));
	nn->data = passedIn;
	return nn;
}



