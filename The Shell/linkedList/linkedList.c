#include "linkedList.h"


LinkedList * linkedList()
{
	LinkedList * myList = (LinkedList *) calloc(1, sizeof(LinkedList));
	myList->head = (Node *) calloc(1, sizeof(Node));
	myList->size = 0;
	return myList;
}

void addLast(LinkedList * theList, Node * nn)
{
	if(nn == NULL)
		exit(-99);

		Node * cur = theList->head;
		
		while(cur->next != NULL)
		{
		    cur = cur->next;
		}
		cur->next = nn;
		nn->prev = cur;
		theList->size = (theList->size + 1);	
}

void addFirst(LinkedList * theList, Node * nn)
{
	if(nn == NULL)
		exit(-99);
	
	nn->prev = theList->head;//nn->prev--dummy head
	nn->next = theList->head->next;//nn->next--dummy head's next	
	
	if(theList->size != 0)
		theList->head->next->prev = nn;//sets prev of first element to nn
	
	theList->head->next = nn;
	theList->size = (theList->size + 1);	
}		

void removeItem(LinkedList * theList, Node * nn, void (*removeData)(void *), int (*compare)(const void *, const void *))
{
	Node * cur = theList->head, *prev = NULL;
	while(compare(cur->data, nn->data) != 0 && cur != NULL)
	{
		prev = cur;
		cur = cur->next;
	}
	if(cur == NULL)
		return;
		
	if(prev == theList->head)
	{
		theList->head->next = cur->next;
		theList->size = theList->size - 1;
	}
	else if(cur == theList->head)
	{
		theList->head = theList->head->next;
		theList->size = theList->size - 1;
	}
	else
	{	
		prev->next = cur->next;
		theList->size = theList->size - 1;
	}	
	removeData(cur->data);//frees cur->data->word
	removeData(nn->data);//frees nn->data->word
	free(nn);//frees nn
	free(cur);//frees cur
}//end removeItem


void clearList(LinkedList * theList, void (*removeData)(void *))
{
	if(theList != NULL)
	{
		theList->size = 0;
		Node * cur = theList->head->next;
		while(cur != NULL)
		{
			free(theList->head);
			theList->head = cur;
			removeData(cur->data);
			cur = theList->head->next;
		}	
		free(theList->head);
	}
}

void printListHistory(const LinkedList * theList, void (*convertData)(void *), int histCount)
{
	if(theList->size == 0)
		printf("Empty List.\n");
	
	if(histCount < theList->size)
	{
		int x, diff = theList->size - histCount;
		Node * cur = theList->head->next;
		for(x = 0; x < diff; x++)
		{
			cur = cur->next;
		}//skip over commands we dont print
		while(cur != NULL)
		{
			convertData(cur->data);
			cur = cur->next;
		}
		printf("\n");
	}
		
	else
	{
		Node * cur = theList->head->next;
		while(cur != NULL)
		{
			convertData(cur->data);
			cur = cur->next;
		}
		printf("\n");
	}	
}

void printList(const LinkedList * theList, void (*convertData)(void *))
{
	if(theList->size == 0)
		printf("Empty List.\n");
	else
	{
		Node * cur = theList->head->next;//dummy head node!!
		while(cur != NULL)
		{
			convertData(cur->data);
			cur = cur->next;
		}
		printf("\n");
	}	
}


Node * lastNode(const LinkedList * theList)
{
	if(theList->size == 0)
		return NULL;
	Node * cur = theList->head->next;
	while(cur->next != NULL)
		cur = cur->next;
		
	return cur;	
		
}//end lastNode

void fillHistory(LinkedList * historyList, int histFileCount, FILE **mssh_history)
{
	int x, diff;
	if(historyList->size == 0)
		exit(-99);
		
	Node * cur = historyList->head->next;	      
	if(historyList->size <= histFileCount)
	{
	      while(cur != NULL)
	      {
		fill(cur->data, mssh_history);
		cur = cur->next;
	      }	
	}
	
	else if(historyList->size > histFileCount)
	{
		diff = (historyList->size - histFileCount);
		for(x = 0; x < diff; x++)
		{
		  cur = cur->next;
		}
		
		while(cur != NULL)
		{
		  fill(cur->data, mssh_history);
		  cur = cur->next;
		}
	}
}//end fillHistory

Node * idNode(int id, LinkedList * theList)
{
	int x;
	if(theList->size < 0)
		exit(-99);
	Node * cur = theList->head->next;
	for(x = 0; x < id; x++)
	{
	      if(cur->next != NULL)
		cur = cur->next;
	}
	return cur;
}//end idNode

void fillAliasList(LinkedList * aliasList, char **alias, int aliasCount)//each string is ALIAS="alias"
{
	int x;
	for(x = 0; x < aliasCount; x++)//tokenize alias
	{
	//	tokeAlias(alias[x],
	}	

}//end fillAliasList







/*
void printType(void * passedIn)
{
	history *h = (history *)passedIn;
	int x;
	for(x = 0; x < h->argc; x++)
	{
		printf("%s ", h->argv[x]);
	}
	printf("\n");
}//not generic



void * buildType(FILE * fin)//buildData(FILE * fin)
{
	if(fin == NULL)
		exit(-99);
	char temp[MAX];
	Book * b = (Book *) calloc(1, sizeof(Book *));
	int num = 0;
	fscanf(fin, "%d", &num);
	int x;
	b->authors.names = (Name *) calloc(num, sizeof(Name *));
	for(x = 0; x < num; x++)
	{
		fscanf(fin, "%s %s", b->authors.names[x].first, b->authors.names[x].last);
	}	
	fscanf(fin, "%s", temp);
	strip(temp);
	b->title = (char *) calloc(strlen(temp) +1, sizeof(char));
	strcpy(b->title, temp);
	
	fscanf(fin, "%s", temp);
	strip(temp);
	b->isbn = (char *) calloc(strlen(temp) + 1, sizeof(char));
	strcpy(b->isbn, temp);
	
	fscanf(fin, "%d", b->pages);
	fgets(temp, MAX, fin);
	strip(temp);
	b->pub.name = (char *) calloc(strlen(temp) + 1, sizeof(char));
	strcpy(b->pub.name, temp);
	
	fgets(temp, MAX, fin);
	strip(temp);
	b->pub.city = (char *) calloc(strlen(temp) + 1, sizeof(char));
	strcpy(b->pub.city, temp);
	
	return b;
	
}//not generic

void * buildType_Prompt(FILE * fin)
{
	if(fin == NULL)
		exit(-99);

	char temp[MAX];
	Book * type = (Book *) calloc(1, sizeof(Book *));
	
	printf("Enter the books title: ");
	fgets(temp, MAX, fin);
	strip(temp);
	type->title = (char *) calloc(strlen(temp) + 1, sizeof(char));
	strcpy(type->title, temp);
	
	printf("Enter the books isbn: ");
	scanf("%s", temp);
	strip(temp);
	type->isbn = (char *) calloc(strlen(temp) + 1, sizeof(char));
	strcpy(type->isbn, temp);
	
	printf("Enter how many pages: ");
	scanf("%d", &type->pages);
	
	return type;
	
}//not generic

 
int compare(const void * p1, const void * p2)
{
	if(p1 == NULL || p2 == NULL)
		exit(-99);
	const history* q1 = (const history *)p1;
	const history* q2 = (const history *)p2;
	return strcasecmp(q1->title, q2->title);
}//not generic



*/



