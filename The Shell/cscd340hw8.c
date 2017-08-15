#include "./alias/alias.h"
#include "./cd/cd.h"
#include "./history/history.h"
#include "./path/path.h"
#include "./redirect/redirect.h"
#include "./pipes/pipes.h"
#include "./linkedList/listUtils.h"
#include "./process/process.h"


int main(int argc, char ** argv)
{
  int argc2, pipeCount, histCount = 100, histFileCount = 1000, count = 0, preCount = 0, redirects = 0; 
  int postCount = 0, outRedirects = 0, inRedirects = 0, res = -1, aliasCount = 0, pathCount = 0;	
  char ** prePipe = NULL, ** postPipe = NULL, ** alias = NULL, temp[MAX], **argv2 = NULL, s[MAX];
  FILE * msshrc = NULL, * mssh_history = NULL;

  
  //check if passed .msshrc file and .msshrc_history
  if(argc <= 1)//neither .msshrc or history
  {
  	msshrc = fopen(".msshrc", "ab+");
  	fputs("HISTCOUNT=100\n", msshrc);
  	fputs("HISTFILECOUNT=1000\n\n", msshrc); 
  	mssh_history = fopen(".mssh_history", "ab+");
  }		
  //read from it
  else if(argc == 2 && strcmp(argv[1], ".msshrc") == 0) //passed only msshrc
  {
        msshrc = fopen(argv[1], "r");
  	buildRc(&msshrc, &alias, &histCount, &histFileCount, &aliasCount, &pathCount);
  }
  else if(argc == 2 && strcmp(argv[1], ".mssh_history") == 0)//passed only history
  {
  	mssh_history = fopen(argv[1], "w");
  }
  else if(argc == 3)//passed msshrc and history
  {
  	msshrc = fopen(argv[1], "r");
  	buildRc(&msshrc, &alias, &histCount, &histFileCount, &aliasCount, &pathCount);
  	mssh_history = fopen(argv[2], "w");
  }
  
  LinkedList * historyList = linkedList();
  LinkedList * aliasList = linkedList();
  
  
  printf("command?: ");
  fgets(s, MAX, stdin);
  strip(s);
  while(strcmp(s, "exit") != 0)
  {
     if(strlen(s) > 1)
     {
  	res = -1;
	pipeCount = containsPipe(s);
	outRedirects = containsOut(s);
	inRedirects = containsIn(s);
	if(pipeCount > 0)//have a pipe
	{
		prePipe = parsePrePipe(s, &preCount);
		postPipe = parsePostPipe(s, &postCount);
		pipeIt(prePipe, postPipe);
		makeargs(s, &argv2, &argc2);
		addLast(historyList, buildNode_Type(buildType_Args(argc2, argv2, historyList->size)));
		clean(preCount, prePipe);
        	clean(postCount, postPipe);
	}// end if pipeCount
		  
	
	if(strcmp(s, "history") == 0)//printHistory
        {
		history *h;
		if(historyList->size > 0)//check if last command was history if not dont store
		{
			Node * cur = historyList->head->next;
			while(cur->next != NULL)
				cur = cur->next;
			h = (history *)cur->data;	
		}
		
		if(strcmp(h->argv[0], "history") == 0)
			printListHistory(historyList, printHistory, histCount);
		else
		{	
		  makeargs(s, &argv2, &argc2);
		  addLast(historyList, buildNode_Type(buildType_Args(argc2, argv2, historyList->size)));
        	  	printListHistory(historyList, printHistory, histCount);
        	}  
        }
        
        else if(s[0] == '!')
       	{
		if(s[0] == '!' && s[1] == '!')//execute previous command by id
		{
			Node * last = lastNode(historyList);
			if(last != NULL)
			{
				executeLast(last->data);
			}

			makeargs(s, &argv2, &argc2);
		  addLast(historyList, buildNode_Type(buildType_Args(argc2, argv2, historyList->size)));
		}//store command
		else
		{
			int x = atoi(&s[1]);
		        Node * n = idNode(x-1, historyList);
		        executeLast(n->data);
		        makeargs(s, &argv2, &argc2);
		  addLast(historyList, buildNode_Type(buildType_Args(argc2, argv2, historyList->size)));
		}
	}
	else if(pipeCount < 1 && s[0] != '!')//store and execute command
	{
		if(s[0] == 'c' && s[1] == 'd' && s[2] == ' ')//cd command handling
			res = changeDirectory(s);
		
		makeargs(s, &argv2, &argc2);
		addLast(historyList, buildNode_Type(buildType_Args(argc2, argv2, historyList->size)));
		
		//else if(strcmp(s, "alias"))
	  	if(argc2 != -1 && res != 0)//generic command handling
	  		forkIt(argv2);  	  	
	}
      }//end if strlen
	printf("command?: ");
	fgets(s, MAX, stdin);
      	strip(s);

  }// end while

  //write history to .mssh_history
        fillHistory(historyList, histFileCount, &mssh_history); 
        
  //memory cleanup!
  	clean(argc2, argv2);
  	argv2 = NULL;
  	clearList(historyList, clearHistory);
  	free(historyList);
  	historyList = NULL;
  	clean(aliasCount, alias);
	clearList(aliasList, clearAlias);
	free(aliasList);
	aliasList = NULL;


	fclose(msshrc);
	fclose(mssh_history);
	
  return 0;

}// end main
