#include "history.h"
 
void makeargs(char *s, char *** argv, int * argc)
{
	if(s == NULL || argv == NULL)
		exit(-99);
	
	int count = 0, count2 = 0;
	char *token;
	char word[MAX];
	strcpy(word, s);
	token = strtok(word, "\n\t ");
	while(token != NULL)
	{
		token = strtok(NULL, "\n\t ");
		count++;
	}
	//now have count of word total
        char *token2;
        char *temp;
       
       if(*argv != NULL)
       {
       	  clean(*argc, *argv);
       	  *argv = NULL;
       }
       
       
        *argv = (char **) calloc(count + 1, sizeof(char *));
        token2 = strtok_r(s, "\n\t ", &temp);
        while(token2 != NULL)
        {
        	(*argv)[count2] = (char *) calloc(strlen(token2)+1, sizeof(char));
        	strcpy((*argv)[count2], token2);
        	token2 = strtok_r(NULL, " ", &temp);
                count2++;
        }
	*argc = count2;
       	
}// end makeArgs
 
void * buildType_Args(int argc, char **argv, int listSize)
{
	history *h = (history *) calloc(1, sizeof(history));
	h->argc = argc;
	h->argv = (char **) calloc(argc, sizeof(char*));
	int x;
	for(x = 0; x < argc; x++)
	{
		h->argv[x] = (char *)calloc(strlen(argv[x])+1, sizeof(char));
		strcpy(h->argv[x], argv[x]);
	}
	h->index = listSize + 1;
	return h;
}//end buildType_Args

void printHistory(void * passedIn)
{
	history *h = (history *)passedIn;
	printf("%d  ", h->index);
	int x;
	for(x = 0; x < h->argc; x++)
	{
		printf("%s ", h->argv[x]);
	}
	printf("\n");
}//end printHistory

void executeLast(void * passedIn)
{
	history *h = (history *)passedIn;
	int x, y, preCount = 0, postCount = 0, flag = -1;
	char temp[MAX], **prePipe, **postPipe;
	for(x = 0; x < h->argc; x++)
	{
	    	if(strcmp(h->argv[x], "|") == 0)
	    	{
	    	       strcpy(temp, h->argv[0]);
	    	       for(y = 1; y < h->argc; y++)
	    	       {
	    	       	   strcat(temp, h->argv[y]);
	    	       	   strcat(temp, " ");
	    	       }	
	    	       strip(temp);//pull off that extra space
	    	       prePipe = parsePrePipe(temp, &preCount);
	    	       postPipe = parsePostPipe(temp, &postCount);
		       pipeIt(prePipe, postPipe);
		       clean(preCount, prePipe);
        	       clean(postCount, postPipe);
        	       flag = 0;
	    	}
	}//check for pipes
	if(flag == -1)
		forkIt(h->argv);
}//end executeLast

void fill(void * passedIn, FILE **mssh_history)
{
	history *h = (history *)passedIn;
	int x;
	for(x = 0; x < h->argc; x++)
	{
		fprintf(*mssh_history, "%s ", h->argv[x]);
	}
	fprintf(*mssh_history, "\n");
}

void clearHistory(void * passedIn)
{
	history *h = (history *)passedIn;
	int x;
	for(x = 0; x < h->argc; x++)
		free(h->argv[x]);
	free(h->argv);
	free(h);
}//end clearHistory








